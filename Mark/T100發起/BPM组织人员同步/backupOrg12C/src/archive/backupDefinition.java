package archive;
import java.sql.*;
import com.inet.tds.PDataSource;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import java.util.ArrayList;
import java.util.List;


public class backupDefinition {
    private static final String PROPERTY_PATH_KEY_VALUE = "archive.home";
    private static final String SUBDIR = "conf";
    private JDBCHelper jdbcHelper = null;
    private List columnName = new ArrayList();
//    //只要複製新的資料就好, 一般是會被移除流程定義資料
//    private String[] justInsertNewDataTablesName = {"ProcessPackage", "CustomProcessPackage"
//        , "ParticipantDefinition", "ProcessDefinition", "ActivitySetDefinition", "ActivityDefinition"
//        , "ProcessViewInformation", "BoundViewInformation", "DecisionRuleList", "DecisionCondition"
//        , "DecisionLevel", "DecisionRule", "FormFieldAccessDefinition", "BlockActivity", "Implementation"
//        , "Nos", "SubFlow", "Route", "ActualParameter", "Tool", "FormOperationDefinition", "RelevantDataDefinition"
//        , "FormalParameter", "ScriptingApplication", "SessionBeanApplication", "WebApplication"
//        , "WebServicesApplication", "MailApplication", "ConditionDefinition", "TransitionDefinition", "TransitionReference"
//        , "TransitionRestriction", "FormType", "BasicType", "AttachmentType"};
    private String[] generalTablesName = {"AssignmentNoPerPerson", "StrategyAssignInstance",
        "AttachmentAuthority", "AttachmentDefinition", "CmDocument", "CompositeType",
        "ConformanceClass", "DataAccessDefinition", "Deadline", "DeclaredType", "DocCmItem",
        "DocMetadataDef", "DocMetadataInst", "DocType", "ExceptionNotificationDef",
        "ExceptionRetryDef", "ExternalPackage", "ExternalReference", "Mails",
        "NoCmDocument", "PackageInvokeAuthority", "PercentagePerPerson", "ProcessDefinitionHeader",
        "ProcessDefTemplate", "ProcessDefTemplateCmItem","ProcessPackageCategory",
        "ProcessPackageCmItem", "ProcessPackageHeader", "RedefinableHeader",
        "ResponsibleDefinition", "SchemaType", "ScriptDefinition", "StrategyAssignDefinition",
        "TimeEstimation", "TimerWorkSchedule", "TypeDefinition", "WizardAuthority",
        "XpressionDefinition", "NotificationContent", "AutoAgent", "Employee",
        "DefaultSubstituteDefinition", "FormDefinitionCmItem", "FormDefinition",
        "FormCategory", "FunctionDefinition", "FunctionLevel", "Functions",
        "Groups","Group_User", "OrgUnit_OrgUnitProperty", "ObjectIdentity",
        "Organization", "OrganizationUnit", "OrganizationUnitLevel",
        "OrganizationUnitProperty", "ParticularRecord", "ParticularRule",
        "ProcessSubstituteDefinition", "Relationship", "Resources", "Role",
        "RoleDefinition", "Title", "TitleDefinition", "Users", "WorkCalendar",
        "WorkingHour", "RsrcBundleValue", "RsrcBundleValue", "SysLanguage", "DocServer_IDocument"};
    //private String[] procBridgeTablesName = {"IAppDefContainer_AppDef", "ProcessPackage_ProcessDef"};
    private String[] systemTablesName = {"DocServer", "SystemConfig", "WorkflowServer"};

    public backupDefinition(JDBCHelper pJDBCHelper) {
        jdbcHelper = pJDBCHelper;
    }
    public static void main(String[] args) {
        JDBCHelper tJDBCHelper = null;

        try{
            tJDBCHelper = backupDefinition.getJDBCHelper(args[0]);
        } catch (FileNotFoundException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not found, please add this file.");
        } catch (IOException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not loaded, please check format of this file.");
        }
        backupDefinition backupDefinition1 = new backupDefinition(tJDBCHelper);
        try{
            long tBeginTime = System.currentTimeMillis();
            backupDefinition1.backupDefinition();
            try{
                tJDBCHelper.getSourceConnection().commit();
                tJDBCHelper.getDesConnection().commit();
            } catch (SQLException e1){
                System.out.println("Commit Error.ErrMsg : " + e1.getMessage());
            }
            long tEndTime = System.currentTimeMillis();
            long tSpentSeconds = tEndTime - tBeginTime;
            System.out.println("Spent " + backupDefinition1.tranTime(tSpentSeconds) + " to backup data.");

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
    private void cleanDest(String[] pTableNames)throws SQLException{
        StringBuffer tSQL;
        Statement tDestStatement = null;
        tDestStatement = this.jdbcHelper.createDesStatement();
        for (int i =0 ;i < pTableNames.length ; i++){
            System.out.println("delete Table name " + pTableNames[i]);
            tSQL = new StringBuffer();
            tSQL = tSQL.append("Delete from ").append(pTableNames[i]);
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
        this.cleanDest(this.generalTablesName);
        for (int i =0 ;i < this.generalTablesName.length ; i++){
            System.out.println("Backup general table, name " + this.generalTablesName[i] +
            "(" + (i+1) + "/" + this.generalTablesName.length + ")");
            tSQL = new StringBuffer();
            tSQL = tSQL.append(tStringBuffer).append(this.generalTablesName[i]);
            tResultSet = tSourceStatement.executeQuery(tSQL.toString());

            this.copyOrgBridgeData(tResultSet, this.generalTablesName[i]);
            tResultSet.close();
        }
        //複製會刪除的定義table
//        for (int i =0 ;i < this.justInsertNewDataTablesName.length ; i++){
//            System.out.println("Table name " + this.justInsertNewDataTablesName[i]);
//            tSQL = new StringBuffer();
//            tSQL = tSQL.append(tStringBuffer).append(this.justInsertNewDataTablesName[i]);
//            tResultSet = tSourceStatement.executeQuery(tSQL.toString());
//            this.copyJustInsertNewData(tResultSet, this.justInsertNewDataTablesName[i]);
//            tResultSet.close();
//        }
        //複製系統的table
        for (int i =0 ;i < this.systemTablesName.length ; i++){
            System.out.println("Backup system table, name " + this.systemTablesName[i] +
            "(" + (i+1) + "/" + this.systemTablesName.length + ")");
            tSQL = new StringBuffer();
            tSQL = tSQL.append(tStringBuffer).append(this.systemTablesName[i]);
            tResultSet = tSourceStatement.executeQuery(tSQL.toString());
            this.copyJustInsertNewData(tResultSet, this.systemTablesName[i]);
            tResultSet.close();
        }

//        for (int i =0 ;i < this.procBridgeTablesName.length ; i++){
//            System.out.println("Table name " + this.procBridgeTablesName[i]);
//            tSQL = new StringBuffer();
//            tSQL = tSQL.append(tStringBuffer).append(this.procBridgeTablesName[i]);
//            tResultSet = tSourceStatement.executeQuery(tSQL.toString());
//            this.copyProcBridgeData(tResultSet, this.procBridgeTablesName[i]);
//            tResultSet.close();
//        }
        //先清除這些組織資料
//        this.cleanDest(this.orgTablesName);
//        for (int i =0 ;i < this.orgTablesName.length ; i++){
//            System.out.println("Table name " + this.orgTablesName[i]);
//            tSQL = new StringBuffer();
//            tSQL = tSQL.append(tStringBuffer).append(this.orgTablesName[i]);
//            tResultSet = tSourceStatement.executeQuery(tSQL.toString());
//            this.copyOrgBridgeData(tResultSet, this.orgTablesName[i]);
//            tResultSet.close();
//        }
//        tSourceStatement.close();
//        this.releaseSourceConnection();
//        this.releaseDesConnection();
    }
    private void copyOrgBridgeData(ResultSet pResultSet, String pTableName) throws SQLException {
        ResultSetMetaData tMeta = pResultSet.getMetaData();
        String tQuerySchemaString = "select * from " + pTableName;
        Statement tDesSchema = this.jdbcHelper.createDesPrepareStatement(tQuerySchemaString);
        ResultSet tDesSchemaRS = tDesSchema.executeQuery(tQuerySchemaString);
        tDesSchemaRS.close();
        tDesSchema.close();
        String tInsertSQLCommand = this.generateInsertString(tDesSchemaRS, pTableName);
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
//    private void copyGeneralData(ResultSet pResultSet, String pTableName) throws SQLException {
//        ResultSetMetaData tMeta = pResultSet.getMetaData();
//        String tQuerySchemaString = "select * from " + pTableName;
//        Statement tDesSchema = this.jdbcHelper.createDesPrepareStatement(tQuerySchemaString);
//        ResultSet tDesSchemaRS = tDesSchema.executeQuery(tQuerySchemaString);
//        //這是insert指令的準備
//        String tInsertSQLCommand = this.generateInsertString(tDesSchemaRS, pTableName);
//        System.out.println("Insert SQL : " + tInsertSQLCommand);
//        PreparedStatement tInsertDes = this.jdbcHelper.createDesPrepareStatement(tInsertSQLCommand);
//        //這是update指令的準備
//        String tUpdateSQLCommand = this.generateUpdateString(tDesSchemaRS, pTableName);
//        System.out.println("Update SQL :" + tUpdateSQLCommand);
//        PreparedStatement tUpdateDes = this.jdbcHelper.createDesPrepareStatement(tUpdateSQLCommand);
//        System.out.println("success preparestate :");
//        tDesSchemaRS.close();
//        tDesSchema.close();
//        //檢查是否存在
//        String tCheckExist = "Select count(OID) From " + pTableName + " Where OID = ?";
//        PreparedStatement tCheckDesPS = this.jdbcHelper.createDesPrepareStatement(tCheckExist);
//        ResultSet tCheckExistRS = null;
//        String tColumnName = null;
//        String tOID = null;
//        boolean tIsExist = true;
//        while (pResultSet.next()) {
//            //先檢查此筆有沒在Dest內
//            tOID = pResultSet.getString("OID");
//            tCheckDesPS.setString(1, tOID);
//            tCheckExistRS = tCheckDesPS.executeQuery();
//            tCheckExistRS.next();
//            if (tCheckExistRS.getInt(1) == 0){
//                tIsExist = false;
//            } else {
//                tIsExist = true;
//            }
//            tCheckExistRS.close();
//            if (!tIsExist){
//                //不存在, 所以用Insert
//                for (int i = 0; i < this.columnName.size() - 1; i++){
//                    tColumnName =  (String)this.columnName.get(i);
//                    if (pTableName.equals("RedefinableHeader") && tColumnName.equals("publicationStatus")){
//                        tInsertDes.setString(i + 1, "UNDER_REVISION");
//                    } else if (pTableName.equals("WizardAuthority") &&
//                               (tColumnName.equals("groupList") || tColumnName.equals("userList") || tColumnName.equals("organizationUnitList") )){
//
//                        tInsertDes.setString(i + 1, "");
//                    } else {
//                        setQueryParam(pResultSet, tMeta, tInsertDes, tColumnName, i + 1);
//                    }
//                }
//                System.out.println(pTableName + " OID :" + tOID + " excute Insert.");
//                tInsertDes.execute();
//            }
//            else {
//                for (int i = 0; i < this.columnName.size(); i++){
//                    tColumnName =  (String)this.columnName.get(i);
//                    if (pTableName.equals("RedefinableHeader") && tColumnName.equals("publicationStatus")){
//                        tUpdateDes.setString(i + 1, "UNDER_REVISION");
//
//                    } else if (pTableName.equals("WizardAuthority") &&
//                                   (tColumnName.equals("groupList") || tColumnName.equals("userList") || tColumnName.equals("organizationUnitList") )){
//                        tUpdateDes.setString(i + 1, "");
//                    } else {
//
//                        setQueryParam(pResultSet, tMeta, tUpdateDes, tColumnName, i + 1);
//
//                    }
//                }
//                System.out.println(pTableName + " OID :" + tOID + " excute Update.");
//                tUpdateDes.execute();
//            }
//        }
//        tCheckDesPS.close();
//        tUpdateDes.close();
//        tInsertDes.close();
//    }
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