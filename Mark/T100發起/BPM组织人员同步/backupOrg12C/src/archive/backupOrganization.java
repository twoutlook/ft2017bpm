package archive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class backupOrganization {
    private static final String PROPERTY_PATH_KEY_VALUE = "archive.home";
    private static final String SUBDIR = "conf";
    private JDBCHelper jdbcHelper = null;
    private List columnName = new ArrayList();
    private String[] orgTablesName = {"AbsenceRecord", "Employee",
        "DefaultSubstituteDefinition", "FunctionDefinition", "FunctionLevel", "Functions",
        "Groups","Group_User", "OrgUnit_OrgUnitProperty",
        "Organization", "OrganizationUnit", "OrganizationUnitLevel",
        "OrganizationUnitProperty", "ParticularRecord", "ParticularRule",
        "ProcessSubstituteDefinition", "Relationship", "Role",
        "RoleDefinition", "Title", "TitleDefinition", "Users", "WorkCalendar",
        "WorkingHour"};
    
    public backupOrganization(JDBCHelper pJDBCHelper) {
        jdbcHelper = pJDBCHelper;
    }
    public static void main(String[] args) {
        JDBCHelper tJDBCHelper = null;

        try{
            tJDBCHelper = backupOrganization.getJDBCHelper(args[0]);
        } catch (FileNotFoundException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not found, please add this file.");
        } catch (IOException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not loaded, please check format of this file.");
        }

        backupOrganization backupOrganization1 = new backupOrganization(tJDBCHelper);
        if (args.length > 1){
        	//有傳table的值進來
        	backupOrganization1.loadTableName(args[1]);
        }
        try{
            long tBeginTime = System.currentTimeMillis();
            backupOrganization1.backupDefinition();
            try{
                tJDBCHelper.getSourceConnection().commit();
                tJDBCHelper.getDesConnection().commit();
            } catch (SQLException e1){
                System.out.println("Commit Error.ErrMsg : " + e1.getMessage());
            }
            long tEndTime = System.currentTimeMillis();
            long tSpentSeconds = tEndTime - tBeginTime;
            System.out.println("Spent " + backupOrganization1.tranTime(tSpentSeconds) + " to backup data.");

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getMessage());
            try{
                tJDBCHelper.getSourceConnection().rollback();
                tJDBCHelper.getDesConnection().rollback();
            } catch (SQLException e1){
                System.out.println("Rollback Error.");
            }
        } finally {
            tJDBCHelper.releaseResource();
        }

    }
    private static JDBCHelper getJDBCHelper(String pFileName) throws FileNotFoundException, IOException{
        return new JDBCHelper(pFileName);
    }
    private void loadTableName(String pFileName){
        String tFullFileName = null;
        if (System.getProperty(PROPERTY_PATH_KEY_VALUE) != null) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
        	tFullFileName = System.getProperty(PROPERTY_PATH_KEY_VALUE) + "/" + SUBDIR +
                                    "/" + pFileName;
        }
        System.out.println("file name " + tFullFileName);
        String tTableNames = null;
        try {
        	File tFile = new File(tFullFileName);
        	tTableNames = FileUtils.readFileToString(tFile,"UTF-8");
        } catch (Exception e){
        	System.out.println("Read file error.ErrMsg:" + e.getMessage());
        }
        if (tTableNames != null && !"".equals(tTableNames.trim()))
        	this.orgTablesName = tTableNames.split(",");
    }
    private void cleanDest(String[] pTableNames)throws SQLException{
        StringBuffer tSQL;
        Statement tDestStatement = null;
        tDestStatement = this.jdbcHelper.createDesStatement();
        for (int i =0 ;i < pTableNames.length ; i++){
            System.out.println("delete Table name " + pTableNames[i].trim());
            tSQL = new StringBuffer();
            tSQL = tSQL.append("Delete from ").append(pTableNames[i].trim());
            tDestStatement.addBatch(tSQL.toString());

        }
        System.out.println("Total clean " + pTableNames.length + " table(s).");
        tDestStatement.executeBatch();
        this.jdbcHelper.releaseStatement(tDestStatement);
    }

    public void backupDefinition() throws SQLException{
        StringBuffer tStringBuffer = new StringBuffer();
        tStringBuffer.append("select * from ");
        StringBuffer tSQL = new StringBuffer();
        Statement tSourceStatement = null;
        tSourceStatement = this.jdbcHelper.createSourceStatement();
        ResultSet tResultSet = null;
        //複製一般的定義table
        this.cleanDest(this.orgTablesName);
        for (int i =0 ;i < this.orgTablesName.length ; i++){
            System.out.println("Backup general table, name " + this.orgTablesName[i].trim() +
            "(" + (i+1) + "/" + this.orgTablesName.length + ")");
            tSQL = new StringBuffer();
            tSQL = tSQL.append(tStringBuffer).append(this.orgTablesName[i].trim());
            tResultSet = tSourceStatement.executeQuery(tSQL.toString());

            this.copyOrgBridgeData(tResultSet, this.orgTablesName[i].trim());
            tResultSet.close();
        }
    }
    private void copyOrgBridgeData(ResultSet pResultSet, String pTableName) throws SQLException {
        ResultSetMetaData tMeta = pResultSet.getMetaData();
        String tQuerySchemaString = "select * from " + pTableName;
        Statement tDesSchema = this.jdbcHelper.createDesPrepareStatement(tQuerySchemaString);
        ResultSet tDesSchemaRS = tDesSchema.executeQuery(tQuerySchemaString);
        String tInsertSQLCommand = this.generateInsertString(tDesSchemaRS, pTableName);
        tDesSchemaRS.close();
        tDesSchema.close();
        //System.out.println(tInsertSQLCommand);
        PreparedStatement tInsertDes = this.jdbcHelper.createDesPrepareStatement(tInsertSQLCommand);
        String tColumnName = null;
        while (pResultSet.next()) {
            for (int i = 0; i < this.columnName.size(); i++)
            {
                tColumnName =  (String)this.columnName.get(i);
                setQueryParam(pResultSet, tMeta, tInsertDes, tColumnName, i+1);
            }
            tInsertDes.execute();
        }
        tInsertDes.close();
    }
    private void copyProcBridgeData(ResultSet pResultSet, String pTableName) throws SQLException {
        ResultSetMetaData tMeta = pResultSet.getMetaData();
        String tQuerySchemaString = "select * from " + pTableName;
        Statement tDesSchema = this.jdbcHelper.createDesPrepareStatement(tQuerySchemaString);
        ResultSet tDesSchemaRS = tDesSchema.executeQuery(tQuerySchemaString);
        tDesSchemaRS.close();
        tDesSchema.close();
        String tInsertSQLCommand = this.generateInsertString(tDesSchemaRS, pTableName);
        System.out.println("Insert command = " + tInsertSQLCommand);
        PreparedStatement tInsertDes = this.jdbcHelper.createDesPrepareStatement(tInsertSQLCommand);
        StringBuffer tCheckExist = new StringBuffer();
        tCheckExist.append("Select count(*) From ").append(pTableName).append(" Where ");
        String tColumnName = null;
        for (int i = 0; i < this.columnName.size(); i++)
        {
            tColumnName =  (String)this.columnName.get(i);
            tCheckExist.append(tColumnName).append(" = ? ");
            if (i !=  (this.columnName.size()-1))
            {
                tCheckExist.append(" and ");
            }
        }
        System.out.println("CheckExist command = " + tCheckExist.toString());
        PreparedStatement tCheckDesPS = this.jdbcHelper.createDesPrepareStatement(tCheckExist.toString());
        ResultSet tCheckExistRS = null;

        //String tOID = null;
        while (pResultSet.next()) {
            //先檢查此筆有沒在Dest內
            //tOID = pResultSet.getString("OID");
            for (int i = 0; i < this.columnName.size(); i++)
            {
                tColumnName =  (String)this.columnName.get(i);
                setQueryParam(pResultSet, tMeta, tCheckDesPS, tColumnName, i+1);
            }
            tCheckExistRS = tCheckDesPS.executeQuery();
            tCheckExistRS.next();
            if (tCheckExistRS.getInt(1) == 0){
                tCheckExistRS.close();
                for (int i = 0; i < this.columnName.size(); i++)
                {
                    tColumnName =  (String)this.columnName.get(i);
                    setQueryParam(pResultSet, tMeta, tInsertDes, tColumnName, i+1);
                }
                tInsertDes.execute();
            } else {
                tCheckExistRS.close();
            }

        }
        tCheckDesPS.close();
        tInsertDes.close();
    }
    private void copyJustInsertNewData(ResultSet pResultSet, String pTableName) throws SQLException {
        ResultSetMetaData tMeta = pResultSet.getMetaData();
        String tQuerySchemaString = "select * from " + pTableName;
        Statement tDesSchema = this.jdbcHelper.createDesPrepareStatement(tQuerySchemaString);
        ResultSet tDesSchemaRS = tDesSchema.executeQuery(tQuerySchemaString);
        tDesSchemaRS.close();
        tDesSchema.close();
        String tInsertSQLCommand = this.generateInsertString(tDesSchemaRS, pTableName);
        //System.out.println(tInsertSQLCommand);
        PreparedStatement tInsertDes = this.jdbcHelper.createDesPrepareStatement(tInsertSQLCommand);
        String tCheckExist = "Select count(OID) From " + pTableName + " Where OID = ?";
        PreparedStatement tCheckDesPS = this.jdbcHelper.createDesPrepareStatement(tCheckExist);
        ResultSet tCheckExistRS = null;
        String tColumnName = null;
        String tOID = null;
        while (pResultSet.next()) {
            //先檢查此筆有沒在Dest內
            tOID = pResultSet.getString("OID");
            tCheckDesPS.setString(1, tOID);
            tCheckExistRS = tCheckDesPS.executeQuery();
            tCheckExistRS.next();
            if (tCheckExistRS.getInt(1) == 0){
                for (int i = 0; i < this.columnName.size(); i++)
                {
                    tColumnName =  (String)this.columnName.get(i);
                    setQueryParam(pResultSet, tMeta, tInsertDes, tColumnName, i+1);
                }
                tInsertDes.execute();
            }
            tCheckExistRS.close();
        }
        tCheckDesPS.close();
        tInsertDes.close();
    }

    private String generateInsertString(ResultSet pResultSet, String pTableName) throws SQLException
    {
        ResultSetMetaData tMeta = pResultSet.getMetaData();
        String tQueryString = "insert into " + pTableName + "(";
        String tColumnName = null;
        this.columnName = new ArrayList();
        for (int i = 1; i <= tMeta.getColumnCount(); i++){
            tColumnName = tMeta.getColumnName(i);
            tQueryString = tQueryString + tColumnName;
            this.columnName.add(i-1, tColumnName);
            if (i != tMeta.getColumnCount())
            {
                tQueryString = tQueryString + ",";
            }
        }
        tQueryString = tQueryString + ") values (";

        for (int i = 1; i <= tMeta.getColumnCount(); i++)
        {
            tQueryString += "?";
            if (i != tMeta.getColumnCount())
            {
                tQueryString += ",";
            }
        }
        tQueryString += ")";
        return tQueryString;
    }
    private String generateUpdateString(ResultSet pResultSet, String pTableName) throws SQLException
    {
        ResultSetMetaData tMeta = pResultSet.getMetaData();

        StringBuffer tQueryString = new StringBuffer();
        tQueryString.append("Update " + pTableName + " set ");
        String tColumnName = null;
        this.columnName = new ArrayList();
        for (int i = 1; i <= tMeta.getColumnCount(); i++){
            tColumnName = tMeta.getColumnName(i);
            tQueryString.append(tColumnName).append(" = ? ");
            this.columnName.add(i-1, tColumnName);
            if (i != tMeta.getColumnCount())
            {
                tQueryString.append(",");
            }
        }
        this.columnName.add(tMeta.getColumnCount(), "OID");
        tQueryString.append(" where OID = ?");
        return tQueryString.toString();
    }
    private void setQueryParam(ResultSet pResultSet, ResultSetMetaData pMeta,
            PreparedStatement pQuery, String pColumnName, int pIndex) throws SQLException
    {
        int tColumnIndex = pResultSet.findColumn(pColumnName);
        String tColumnTypeName = pMeta.getColumnTypeName(tColumnIndex);
        int tColumnType = pMeta.getColumnType(tColumnIndex);
        //System.out.println("Column :" + pColumnName + ", TypeName :" + tColumnTypeName + ", ColumnType: " + tColumnType);
        // String columnName = pMeta.getColumnName(pIndex);
        if (tColumnType == Types.BIGINT){
            pQuery.setLong(pIndex, pResultSet.getLong(tColumnIndex));
        } else if (tColumnType == Types.INTEGER){
            pQuery.setInt(pIndex, pResultSet.getInt(tColumnIndex));
        } else if (tColumnType == Types.SMALLINT) {
            pQuery.setShort(pIndex, pResultSet.getShort(tColumnIndex));
        } else if ((tColumnType == Types.BOOLEAN) || (tColumnType == Types.BIT)){
            pQuery.setBoolean(pIndex, pResultSet.getBoolean(tColumnIndex));
        } else if ((tColumnType == Types.DECIMAL)
                || (tColumnType == Types.NUMERIC)){
            pQuery.setBigDecimal(pIndex, pResultSet.getBigDecimal(tColumnIndex));
        } else if (tColumnType == Types.DOUBLE) {
            pQuery.setDouble(pIndex, pResultSet.getDouble(tColumnIndex));
        } else if (tColumnType == Types.REAL || tColumnType == Types.FLOAT) {
            pQuery.setFloat(pIndex, pResultSet.getFloat(tColumnIndex));
        } else if (tColumnType == Types.TIMESTAMP || tColumnType == Types.DATE) {
            pQuery.setTimestamp(pIndex, pResultSet.getTimestamp(tColumnIndex));
        } else if ((tColumnType == Types.CHAR) || (tColumnType == Types.VARCHAR)
                || (tColumnType == Types.LONGVARCHAR)){
            pQuery.setString(pIndex, pResultSet.getString(tColumnIndex));
        } else if ((tColumnType == Types.BINARY)
                || (tColumnType == Types.VARBINARY)
                || (tColumnType == Types.LONGVARBINARY)){
            pQuery.setBytes(pIndex, pResultSet.getBytes(tColumnIndex));
        } else if (tColumnType == -9){
            //nvarchar, ntext
            pQuery.setString(pIndex, pResultSet.getString(tColumnIndex));
        } else if (tColumnType == -10){
            //nvarchar, ntext
            //pQuery.setClob(pIndex, pResultSet.getClob(tColumnIndex));
            pQuery.setString(pIndex, pResultSet.getString(tColumnIndex));
        }

        // else if (tColumnType == Types.ARRAY)
        // {
        // pQuery.setArray(tColumnIndex, resultSet.getArray(tColumnIndex));
        // pQuery.setArray(i, resultSet.getArray(columnName));
        // }
        // else if (tColumnType == Types.BLOB)
        // {
        // pQuery.setBlob(tColumnIndex, resultSet.getBlob(tColumnIndex));
        // }
         else if (tColumnType == Types.CLOB)
         {
         pQuery.setClob(tColumnIndex, pResultSet.getClob(tColumnIndex));
         }
        // else if (tColumnType == Types.DATALINK)
        // {
        // pQuery.set?(tColumnIndex, resultSet.get?(tColumnIndex));
        // }
//         else if (tColumnType == Types.DATE)
//         {
//         pQuery.setDate(tColumnIndex, pResultSet.getDate(tColumnIndex));
//         }
        // else if (tColumnType == Types.DISTINCT)
        // {
        // pQuery.set?(tColumnIndex, resultSet.get?(tColumnIndex));
        // }
        // else if (tColumnType == Types.FLOAT)
        // {
        // pQuery.setFloat(tColumnIndex, resultSet.getFloat(tColumnIndex));
        // }
        // else if (tColumnType == Types.JAVA_OBJECT)
        // {
        // pQuery.set?(tColumnIndex, resultSet.get?(tColumnIndex));
        // }
        // else if (tColumnType == Types.NULL)
        // {
        // pQuery.set?(tColumnIndex, resultSet.get?(tColumnIndex));
        // }
        // else if (tColumnType == Types.OTHER)
        // {
        // pQuery.set?(tColumnIndex, resultSet.get?(tColumnIndex));
        // }
        // else if (tColumnType == Types.REF)
        // {
        // pQuery.set?(tColumnIndex, resultSet.get?(tColumnIndex));
        // }
        // else if (tColumnType == Types.STRUCT)
        // {
        // pQuery.set?(tColumnIndex, resultSet.get?(tColumnIndex));
        // }
        // else if (tColumnType == Types.TIME)
        // {
        // pQuery.setTime(tColumnIndex, resultSet.getTime(tColumnIndex));
        // }
        // else if (tColumnType == Types.TINYINT)
        // {
        // pQuery.set?(tColumnIndex, resultSet.get?(tColumnIndex));
        // }
        else
        {
            throw new SQLException("Cannot handle " + "Column :" + pColumnName + ", TypeName :" + tColumnTypeName + ", ColumnType: " + tColumnType);
        }

    }
    private String tranTime(long pMinSecond){
        long tMinSecond = pMinSecond;
        long tHours = tMinSecond/(1000*60*60);
        tMinSecond = tMinSecond - (tHours*1000*60*60);
        long tMinutes = tMinSecond/(1000*60);
        tMinSecond = tMinSecond - (tMinutes*1000*60);
        long tSeconds = tMinSecond/1000;
        StringBuffer tTime = new StringBuffer();
        if (tHours<10){
            tTime.append("0").append(tHours);
        } else {
            tTime.append(tHours);
        }
        tTime.append(":");
        if (tMinutes<10){
            tTime.append("0").append(tMinutes);
        } else {
            tTime.append(tMinutes);
        }
        tTime.append(":");
        if (tSeconds<10){
            tTime.append("0").append(tSeconds);
        } else {
            tTime.append(tSeconds);
        }
        return tTime.toString();
    }

}
