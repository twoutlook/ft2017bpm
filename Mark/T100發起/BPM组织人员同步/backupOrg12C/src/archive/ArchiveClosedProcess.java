package archive;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ArchiveClosedProcess {
    private JDBCHelper jdbcHelper = null;
    public ArchiveClosedProcess(JDBCHelper pJDBCHelper) {
        jdbcHelper = pJDBCHelper;
    }
    public static void main(String[] args) {
        JDBCHelper tJDBCHelper = null;

        try{
            tJDBCHelper = ArchiveClosedProcess.getJDBCHelper(args[0]);
        } catch (FileNotFoundException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not found, please add this file.");
        } catch (IOException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not loaded, please check format of this file.");
        }
        ArchiveClosedProcess tArchiveClosedProcess = new ArchiveClosedProcess(tJDBCHelper);
        try{
            long tBeginTime = System.currentTimeMillis();
            tArchiveClosedProcess.startArchive();
            try{
                tJDBCHelper.getSourceConnection().commit();
                tJDBCHelper.getDesConnection().commit();
            } catch (SQLException e1){
                System.out.println("Commit Error.ErrMsg : " + e1.getMessage());
            }
            long tEndTime = System.currentTimeMillis();
            long tSpentSeconds = tEndTime - tBeginTime;
            System.out.println("Spent " + tArchiveClosedProcess.tranTime(tSpentSeconds) + " to backup data.");
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
    private void startArchive() throws SQLException{
        backupDefinition backupDefinition1 = new backupDefinition(jdbcHelper);
        backupDefinition1.backupDefinition();
        transInstance transInstance1 = new transInstance(jdbcHelper);
        transInstance1.loadClosedProcessInstance();
        transInstance1.transProcessInstance();
        transInstance1.loadProcessPackage();
        transInstance1.transProcessPackage();
        cleanCustom tCleanCustom1 = new cleanCustom(jdbcHelper);
        tCleanCustom1.loadNonUsedCusomProcessPackage();
        tCleanCustom1.deleteData();
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