package archive;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PasswordTrans {
    private OracleJDBCHelper jdbcHelper = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OracleJDBCHelper tJDBCHelper = null;

        try{
            tJDBCHelper = PasswordTrans.getJDBCHelper(args[0]);
        } catch (FileNotFoundException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not found, please add this file.");
        } catch (IOException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not loaded, please check format of this file.");
        }
        PasswordTrans tPasswordTrans = new PasswordTrans(tJDBCHelper);
        try{
            long tBeginTime = System.currentTimeMillis();
            tPasswordTrans.trans();
            try{
                tJDBCHelper.getSourceConnection().commit();
                tJDBCHelper.getDesConnection().commit();
            } catch (SQLException e1){
                System.out.println("Commit Error.ErrMsg : " + e1.getMessage());
            }
            long tEndTime = System.currentTimeMillis();
            long tSpentSeconds = tEndTime - tBeginTime;
            System.out.println("Spent " + tPasswordTrans.tranTime(tSpentSeconds) + " to trans data.");

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
    private static OracleJDBCHelper getJDBCHelper(String pFileName) throws FileNotFoundException, IOException{
        return new OracleJDBCHelper(pFileName);
    }
    public PasswordTrans(OracleJDBCHelper pJDBCHelper) {
        jdbcHelper = pJDBCHelper;
    }
    public void trans()throws SQLException{
        StringBuffer tQueryStringBuffer = new StringBuffer();
        tQueryStringBuffer.append("SELECT zx01, zx10 FROM zx_file ");
        Statement tSourceStatement = null;
        tSourceStatement = this.jdbcHelper.createSourceStatement();
        ResultSet tResultSet = null;
        tResultSet = tSourceStatement.executeQuery(tQueryStringBuffer.toString());
        StringBuffer tUpdateSQL = new StringBuffer();
        tUpdateSQL.append("Update Users set password = ? where id = ?");
        PreparedStatement tPreparedStatement = 
        	this.jdbcHelper.createDesPrepareStatement(tUpdateSQL.toString());
        String tId = null;
        String tPassword = null;
        while (tResultSet.next()){
        	tId = tResultSet.getString("zx01").trim();
        	tPassword = tResultSet.getString("zx10").trim();
        	tPreparedStatement.setString(1, Crypto.hashSHA1String(tPassword));
        	tPreparedStatement.setString(2, tId);
        	tPreparedStatement.executeUpdate();
        	tPreparedStatement.clearParameters();
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
