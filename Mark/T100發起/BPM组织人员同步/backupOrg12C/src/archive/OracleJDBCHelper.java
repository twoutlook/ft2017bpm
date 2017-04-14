package archive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;


public class OracleJDBCHelper {

    private static final String PROPERTY_PATH_KEY_VALUE = "archive.home";
    private static final String SUBDIR = "conf";
    private Properties databaseProperties = new Properties();
    private Connection sourceConnection;
    private Connection desConnection;
    private Collection statments; //存放建立過的Statement


    public Connection getSourceConnection() throws SQLException {
        if (this.sourceConnection == null ) {
            OracleDataSource tDataSourceImpl = new OracleDataSource();

            tDataSourceImpl.setURL(databaseProperties.getProperty("sourceConnectionURL"));
            tDataSourceImpl.setUser(databaseProperties.getProperty("sourceUserId"));
            tDataSourceImpl.setPassword(databaseProperties.getProperty("sourcePassword"));
            this.sourceConnection = tDataSourceImpl.getConnection();
            this.sourceConnection.setAutoCommit(true);
        } else {
            try {
                if (this.sourceConnection.isClosed()) {
                    this.sourceConnection = null;
                    OracleDataSource tDataSourceImpl = new OracleDataSource();

                    tDataSourceImpl.setURL(databaseProperties.getProperty("sourceConnectionURL"));
                    tDataSourceImpl.setUser(databaseProperties.getProperty("sourceUserId"));
                    tDataSourceImpl.setPassword(databaseProperties.getProperty("sourcePassword"));
                    this.sourceConnection = tDataSourceImpl.getConnection();
                    this.sourceConnection.setAutoCommit(true);
                }
            } catch (Exception e) {
                throw new SQLException("Fail to get jdbc connection.ErrMsg:" + e.getMessage());
            }
        }
        return this.sourceConnection;
    }
    public Connection getDesConnection() throws SQLException {
        if (this.desConnection == null ) {
            OracleDataSource tDataSourceImpl = new OracleDataSource();

            tDataSourceImpl.setURL(databaseProperties.getProperty("desConnectionURL"));
            tDataSourceImpl.setUser(databaseProperties.getProperty("desUserId"));
            tDataSourceImpl.setPassword(databaseProperties.getProperty("desPassword"));
            this.desConnection = tDataSourceImpl.getConnection();
            this.desConnection.setAutoCommit(true);
        } else {
            try {
                if (this.desConnection.isClosed()) {
                    this.desConnection = null;
                    OracleDataSource tDataSourceImpl = new OracleDataSource();

                    tDataSourceImpl.setURL(databaseProperties.getProperty("sourceConnectionURL"));
                    tDataSourceImpl.setUser(databaseProperties.getProperty("sourceUserId"));
                    tDataSourceImpl.setPassword(databaseProperties.getProperty("sourcePassword"));
                    this.sourceConnection = tDataSourceImpl.getConnection();
                    this.sourceConnection.setAutoCommit(true);
                }
            } catch (Exception e) {
                throw new SQLException("Fail to get jdbc connection.ErrMsg:" + e.getMessage());
            }
        }
        return this.desConnection;
    }
    public void rollbackSourceConnection(){
        if (this.sourceConnection != null){
            try{
                this.sourceConnection.rollback();
                this.sourceConnection.close();
            } catch (SQLException e){
                System.out.println("Error message : " + e.getMessage());
            }
        }
        this.sourceConnection = null;
    }

    public void rollbackDesConnection(){
        if (this.desConnection != null){
            try{
                this.desConnection.rollback();
                this.desConnection.close();
            } catch (SQLException e){
                System.out.println("Error message : " + e.getMessage());
            }
        }
        this.desConnection = null;
    }
    private Collection getStatements() {
        if (this.statments == null) {
            this.statments = new HashSet();
        }
        return this.statments;
    }

    public OracleJDBCHelper(String pFileName) throws FileNotFoundException, IOException{
        String tFullPropertyFileName = null;
        if (System.getProperty(PROPERTY_PATH_KEY_VALUE) != null) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            tFullPropertyFileName = System.getProperty(PROPERTY_PATH_KEY_VALUE) + "/" + SUBDIR +
                                    "/" + pFileName;
        }
        System.out.println("file name " + tFullPropertyFileName);
        File tFile = new File(tFullPropertyFileName);
        FileInputStream tPropertyStream = new FileInputStream(tFile);
        this.databaseProperties.load(tPropertyStream);
    }

    /**
     * 建立 PrepareStatement
     * @author 王舜民
     * @since NaNa 1.0
     * @param pSqlString 傳入 Prepared SQL 指令
     * @throws PersistenceException 遇到 DB 層次的例外時丟出此例外
     * @return 建立好的 PrepareStatement
     */
    public PreparedStatement createSourcePrepareStatement(String pSqlString) throws SQLException {
        if (pSqlString == null || "".equals(pSqlString)) {
            throw new IllegalArgumentException("Argument pSqlString cannot be null or empty string");
        }
        PreparedStatement tStatement = null;
        try {
            tStatement = this.getSourceConnection().prepareStatement(pSqlString);
        } catch (SQLException e) {
            throw new SQLException("Error occurred while invoking SourceConnection.prepareStatement(String).ErrMsg:" + e.getMessage());
        }
        this.getStatements().add(tStatement);
        return tStatement;
    }

    /**
     * 建立 Statement
     * @author 王舜民
     * @since NaNa 1.0
     * @param pSqlString 傳入 Prepared SQL 指令
     * @throws PersistenceException 遇到 DB 層次的例外時丟出此例外
     * @return 建立好的 Statement
     */
    public Statement createSourceStatement() throws SQLException {
        Statement tStatement = null;
        try {
            tStatement = this.getSourceConnection().createStatement();
        } catch (SQLException e) {
            throw new SQLException("Error occurred while invoking SourceConnection.createStatement().ErrMsg:" + e.getMessage());
        }
        this.getStatements().add(tStatement);
        return tStatement;
    }
    /**
     * 建立 PrepareStatement
     * @author 王舜民
     * @since NaNa 1.0
     * @param pSqlString 傳入 Prepared SQL 指令
     * @throws PersistenceException 遇到 DB 層次的例外時丟出此例外
     * @return 建立好的 PrepareStatement
     */
    public PreparedStatement createDesPrepareStatement(String pSqlString) throws SQLException {
        if (pSqlString == null || "".equals(pSqlString)) {
            throw new IllegalArgumentException("Argument pSqlString cannot be null or empty string");
        }
        PreparedStatement tStatement = null;
        try {
            tStatement = this.getDesConnection().prepareStatement(pSqlString);
        } catch (SQLException e) {
            throw new SQLException("Error occurred while invoking DesConnection.prepareStatement(String).ErrMsg:" + e.getMessage());
        }
        this.getStatements().add(tStatement);
        return tStatement;
    }

    /**
     * 建立 Statement
     * @author 王舜民
     * @since NaNa 1.0
     * @param pSqlString 傳入 Prepared SQL 指令
     * @throws PersistenceException 遇到 DB 層次的例外時丟出此例外
     * @return 建立好的 Statement
     */
    public Statement createDesStatement() throws SQLException {
        Statement tStatement = null;
        try {
            tStatement = this.getDesConnection().createStatement();
        } catch (SQLException e) {
            throw new SQLException("Error occurred while invoking DesConnection.createStatement().ErrMsg:" + e.getMessage());
        }
        this.getStatements().add(tStatement);
        return tStatement;
    }
    /**
     * 釋放 JDBCReadingHelper 所佔據的 JDBC 資源, 包括 Connection, Statement 及 ResultSet
     */
    public void releaseResource() {
        this.releaseResource(true);
    }

    /**
     * 釋放 JDBCReadingHelper 所佔據的 JDBC 資源
     * @param pReleaseConn 是否要將 Connection 釋放
     */
    public void releaseResource(boolean pReleaseConn) {
        Iterator tIterStatements = this.getStatements().iterator();
        Statement tStatement = null;
        System.out.println("There are " + this.getStatements().size() +
                           " statements are ready to release");
        while (tIterStatements.hasNext()) {
            tStatement = (Statement)tIterStatements.next();
            this.releaseStatement(tStatement);
        }
        this.statments = null;

        if (pReleaseConn == true) {
            try {
                this.releaseConnection(this.sourceConnection);
            } catch (Exception e) {
                System.out.println("Error occurred while invoking release source connection. ErrMsg:" + e.getMessage());
            }
            this.sourceConnection = null;
            try {
                this.releaseConnection(this.desConnection);
            } catch (Exception e) {
                System.out.println("Error occurred while invoking release des connection. ErrMsg:" + e.getMessage());
            }
            this.desConnection = null;
        }
    }
    /**
     * 釋放 JDBCReadingHelper 所佔據的 JDBC 資源, 包括 Connection, Statement 及 ResultSet
     */
    public void releaseSourceResource() {
        this.releaseSourceResource(true);
    }

    /**
     * 釋放 JDBCReadingHelper 所佔據的 JDBC 資源
     * @param pReleaseConn 是否要將 Connection 釋放
     */
    public void releaseSourceResource(boolean pReleaseConn) {
        Iterator tIterStatements = this.getStatements().iterator();
        Statement tStatement = null;
        System.out.println("There are " + this.getStatements().size() +
                           " statements are ready to release");
        while (tIterStatements.hasNext()) {
            tStatement = (Statement)tIterStatements.next();
            this.releaseStatement(tStatement);
        }
        this.statments = null;

        if (pReleaseConn == true) {
            try {
                this.releaseConnection(this.sourceConnection);
            } catch (Exception e) {
                System.out.println("Error occurred while invoking release source connection. ErrMsg:" + e.getMessage());
            }
            this.sourceConnection = null;
        }
    }
    static public void releaseConnection(Connection pConn){
        try {
            if (pConn != null) {
                pConn.close();
            }
        } catch (SQLException e) {
            System.out.println("Fail to close jdbc connection." + e.getMessage());
        }
    }

    static public void releaseStatement(Statement pStmt){
        try {
            if (pStmt != null) {
                pStmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Fail to close jdbc statement." + e.getMessage());
        }
    }

    static public void releaseResultSet(ResultSet pResultSet){
        try {
            if (pResultSet != null) {
                pResultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Fail to close jdbc resultset." + e.getMessage());
        }
    }
}
