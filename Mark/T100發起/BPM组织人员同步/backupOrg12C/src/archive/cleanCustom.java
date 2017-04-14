package archive;

import java.sql.*;
import java.util.*;
import com.inet.tds.PDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class cleanCustom {
    private static final String PROPERTY_PATH_KEY_VALUE = "archive.home";
    private static final String SUBDIR = "conf";
    private static final String OUT_SUBDIR = "deletelog";
    private JDBCHelper jdbcHelper = null;
    private List columnName = new ArrayList();
    private Map deleteProcessPackage = new HashMap();
    //Key 是PreparedStatement, 值為該table insertPreparedStatement
    private Map preparedStatements = new HashMap();

    public cleanCustom(JDBCHelper pJDBCHelper) {
        jdbcHelper = pJDBCHelper;
    }
    public static void main(String[] args) {
        JDBCHelper tJDBCHelper = null;

        try{
            tJDBCHelper = cleanCustom.getJDBCHelper(args[0]);
        } catch (FileNotFoundException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not found, please add this file.");
        } catch (IOException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not loaded, please check format of this file.");
        }
        cleanCustom tCleanCustom1 = new cleanCustom(tJDBCHelper);

        if (args.length > 1)
        {
            try{
                //載入流程實例的OID資訊
                long tBeginTime = System.currentTimeMillis();
                tCleanCustom1.loadProcessPackageOID(args[1]);
                tCleanCustom1.deleteData();
                try{
                    tJDBCHelper.getSourceConnection().commit();
                } catch (SQLException e1){
                    System.out.println("Commit Error.ErrMsg : " + e1.getMessage());
                }
                long tEndTime = System.currentTimeMillis();
                long tSpentSeconds = tEndTime - tBeginTime;
                System.out.println("Spent " + tCleanCustom1.tranTime(tSpentSeconds) + " to trans ProcessInstance.");

            } catch (FileNotFoundException e) {
                //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
                System.out.println("Process instance OID File " + args[1] +
                                   " not found, please add this file.");
            } catch (IOException e) {
                //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
                System.out.println("Process instance OID File " + args[1] +
                                   " not loaded, please check format of this file.");
            } catch (SQLException e){
                System.out.println("SQLException : " + e.getMessage());
                try{
                    tJDBCHelper.getSourceConnection().rollback();
                } catch (SQLException e1){
                    System.out.println("Rollback Error.");
                }
            } finally {
                tJDBCHelper.releaseSourceResource();
            }
        } else{
            try{
                long tBeginTime = System.currentTimeMillis();
                tCleanCustom1.loadNonUsedCusomProcessPackage();
                tCleanCustom1.deleteData();
                try{
                    tJDBCHelper.getSourceConnection().commit();
                } catch (SQLException e1){
                    System.out.println("Commit Error.ErrMsg : " + e1.getMessage());
                }
                long tEndTime = System.currentTimeMillis();
                long tSpentSeconds = tEndTime - tBeginTime;
                System.out.println("Spent " + tCleanCustom1.tranTime(tSpentSeconds) + " to trans ProcessInstance.");

            } catch (SQLException e){
                System.out.println("SQLException : " + e.getMessage());
                try{
                    tJDBCHelper.getSourceConnection().rollback();
                } catch (SQLException e1){
                    System.out.println("Rollback Error.");
                }
            } finally {
                tJDBCHelper.releaseSourceResource();
            }
        }
    }
    private static JDBCHelper getJDBCHelper(String pFileName) throws FileNotFoundException, IOException{
        return new JDBCHelper(pFileName);
    }
    private void logDeleteCommand(String pFileName, StringBuffer pCommands) {
        String tFullPropertyFileName = null;
        if (System.getProperty(PROPERTY_PATH_KEY_VALUE) != null) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            tFullPropertyFileName = System.getProperty(PROPERTY_PATH_KEY_VALUE) + "/" + OUT_SUBDIR + "/DeleteSource" +
                                    "/" + pFileName + ".sql";
        }
        File tFile = new File(tFullPropertyFileName);
        try{
            FileUtils.writeStringToFile(tFile, pCommands.toString(), "UTF-8");
        } catch (Exception e) {
            System.out.println("Log delete custom ProcessPackage commands Error. OID = " + pFileName);
            System.out.println("Log delete custom ProcessPackage commands Error. Message = " + e.getMessage());
        }
    }
    private void loadProcessPackageOID(String pFileName) throws FileNotFoundException, IOException, SQLException{
        String tFullPropertyFileName = null;
        if (System.getProperty(PROPERTY_PATH_KEY_VALUE) != null) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            tFullPropertyFileName = System.getProperty(PROPERTY_PATH_KEY_VALUE) + "/" + SUBDIR +
                                    "/" + pFileName;
        }
        System.out.println("file name " + tFullPropertyFileName);
        File tFile = new File(tFullPropertyFileName);
        String tProcessInstanceOIDs = FileUtils.readFileToString(tFile,"UTF-8");
        String[] tArrayOfProcessInstanceOIDs = tProcessInstanceOIDs.split(",");
        ProcessInstance tProcessInstance = null;
        String tOID = null;
        String tSQLSelectCustomProcessPackage= "Select count(OID) from " +
                CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME +
                " where OID = ?";
        String tSQLSelectProcessPackage= "Select count(OID) from " +
                ProcessPackage.PROCESS_PACKAGE_TABLE_NAME +
                " where OID = ?";
        PreparedStatement tSelectCustomProcessPackagePS = null;
        try{
            tSelectCustomProcessPackagePS = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCustomProcessPackage);
        } catch (SQLException e){
            System.out.println("Prepare select " + CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
        ResultSet tCustomProcessPackageRS = null;
        PreparedStatement tSelectProcessPackagePS = null;
        try{
            tSelectProcessPackagePS = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectProcessPackage);
        } catch (SQLException e){
            System.out.println("Prepare select " + ProcessPackage.PROCESS_PACKAGE_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
        ResultSet tProcessPackageRS = null;
        try{
            ProcessPackage tProcessPackage = null;
            for(int i=0; i < tArrayOfProcessInstanceOIDs.length; i++){
                tOID = tArrayOfProcessInstanceOIDs[i].trim();
                tSelectCustomProcessPackagePS.setString(1, tOID);
                tCustomProcessPackageRS = tSelectCustomProcessPackagePS.executeQuery();
                tCustomProcessPackageRS.next();
                if (tCustomProcessPackageRS.getInt(1) == 1){
                    tProcessPackage = new CustomProcessPackage(tOID);
                    this.deleteProcessPackage.put(tOID, tProcessPackage);
                } else {
                    tSelectProcessPackagePS.setString(1, tOID);
                    tProcessPackageRS = tSelectProcessPackagePS.executeQuery();
                    tProcessPackageRS.next();
                    if (tProcessPackageRS.getInt(1) == 1){
                        tProcessPackage = new ProcessPackage(tOID);
                        this.deleteProcessPackage.put(tOID, tProcessPackage);
                    }
                    tProcessPackageRS.close();
                }
                tCustomProcessPackageRS.close();
            }
        } catch (SQLException e){
            System.out.println("Excute query " + CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME +
                               " statement error." + e.getMessage());
            //
            //this.rollbackDesConnection();
            throw e;
        } finally{
            tSelectProcessPackagePS.close();
            tSelectCustomProcessPackagePS.close();
        }
    }
    public void loadNonUsedCusomProcessPackage() throws SQLException{
        String tSQLSelectCustomProcessPackage= "Select OID from " +
                CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME +
                " where OID not in (Select processPackageOID from ProcessContext)";
        Statement tSelectCustomProcessPackageStat = null;
        try{
            tSelectCustomProcessPackageStat = this.jdbcHelper.createSourceStatement();
        } catch (SQLException e){
            System.out.println("select " + CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
        ResultSet tCustomProcessPackageRS = null;
        try{
            tCustomProcessPackageRS = tSelectCustomProcessPackageStat.executeQuery(tSQLSelectCustomProcessPackage);
            CustomProcessPackage tCustomProcessPackage = null;
            String tOID = null;
            while (tCustomProcessPackageRS.next()){
                tOID = tCustomProcessPackageRS.getString("OID");
                tCustomProcessPackage = new CustomProcessPackage(tOID);
                this.deleteProcessPackage.put(tOID, tCustomProcessPackage);
            }
            tCustomProcessPackageRS.close();
            this.jdbcHelper.releaseStatement(tSelectCustomProcessPackageStat);
        } catch (SQLException e){
            System.out.println("Excute query " + CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME +
                               " statement error." + e.getMessage());
            //
            //this.rollbackDesConnection();
            throw e;
        }
        System.out.println("Size of deleted Custom : " + this.deleteProcessPackage.size());
    }
    public void deleteData() throws SQLException{
        try {
            this.jdbcHelper.getSourceConnection().setAutoCommit(false);
        } catch (SQLException e){
            System.out.println("Source connection set AutoCommit false error.");
        }
        Iterator tIterProcessPackageOIDs = this.deleteProcessPackage.keySet().iterator();
        String tOID = null;
        int tSize = this.deleteProcessPackage.size();
        int tCount = 0;
        Statement tDeleteST = this.jdbcHelper.createSourceStatement();
        StringBuffer tStringBuffer = new StringBuffer();
        long tBeginTime = 0;
        long tEndTime = 0;
        StringBuffer tMessageBuffer = new StringBuffer();
        while (tIterProcessPackageOIDs.hasNext()){

            tBeginTime = System.currentTimeMillis();
            tCount ++;
            tOID = (String)tIterProcessPackageOIDs.next();
            this.collecteRemovedProcessPackage(tOID);
            try{
                ProcessPackage tProcessPackage = (ProcessPackage)this.deleteProcessPackage.get(tOID);

                Iterator tIterDeleteCommands = tProcessPackage.generateDeleteCommands().iterator();
                String tCommand = null;
                while (tIterDeleteCommands.hasNext()){
                    tCommand = (String)tIterDeleteCommands.next();
                    tStringBuffer.append(tCommand).append("\n");
                    tDeleteST.addBatch(tCommand);
                }
            } catch (SQLException e) {
                //
                throw e;
            }
            tEndTime = System.currentTimeMillis();
            tMessageBuffer = new StringBuffer();
            tMessageBuffer.append("Delete ProcessPackage OID:").append(tOID).append(
                    ". Spent ").append(((tEndTime-tBeginTime)/1000)).append(" s.(").append(
                    tCount).append("/").append(tSize).append(")");
            System.out.println(tMessageBuffer);
        }
        try{
            logDeleteCommand(tOID, tStringBuffer);
            tDeleteST.executeBatch();
        } catch (SQLException e) {
            //
            throw e;
        }

    }
//    private void collecteRemoved() throws SQLException{
//        Iterator tIterProcessPackageOIDs = this.deleteProcessPackage.keySet().iterator();
//        String tOID = null;
//        while (tIterProcessPackageOIDs.hasNext()){
//            tOID = (String)tIterProcessPackageOIDs.next();
//            this.collecteRemovedProcessPackage(tOID);
//        }
//    }
    private void collecteRemovedProcessPackage(String pOID) throws SQLException{
        try{
            ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
            String tSQLSelectProcessDefOID = "Select * from " + ProcessPackage.PACKAGE_B_DEFINITION_TABLE_NAME +
                    " where ProcessPackageOID = ?";
            PreparedStatement tSelectProcessDefOIDStat = (PreparedStatement)this.preparedStatements.get(tSQLSelectProcessDefOID);
            try{
                if (tSelectProcessDefOIDStat == null){
                    tSelectProcessDefOIDStat = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectProcessDefOID);
                    this.preparedStatements.put(tSQLSelectProcessDefOID, tSelectProcessDefOIDStat);
                }
            } catch (SQLException e){
                System.out.println("Create " + ProcessPackage.PACKAGE_B_DEFINITION_TABLE_NAME +
                                   " preparedStatement error." + e.getMessage());
                throw e;
            }
            //先找流程定義的OID
            ResultSet tProcessDefOIDRS = null;
            try{
                tSelectProcessDefOIDStat.setString(1, pOID);
                tProcessDefOIDRS = tSelectProcessDefOIDStat.executeQuery();
                while (tProcessDefOIDRS.next()){
                    tProcessPackage.addProcessDefOID(tProcessDefOIDRS.getString("ProcessDefinitionOID"));
                }
                tProcessDefOIDRS.close();
                //tSelectProcessDefOIDStat.close();
                this.deleteProcessPackage.put(pOID, tProcessPackage);
            } catch (SQLException e){
                System.out.println("Excute query " + ProcessPackage.PACKAGE_B_DEFINITION_TABLE_NAME +
                                   " statement error." + e.getMessage());
                throw e;
            }
            this.collecteRemovedDef(pOID);
            this.collecteRemovedActSet(pOID);
            this.collecteRemovedAct(pOID);
            this.collecteRemovedProcessViewInformation(pOID);
            //在collecteRemovedAct 與 collecteRemovedProcessViewInformation 已經把BoundViewInformation 填入
            //只要檢查是否有被不需移除的物件參考即可
            this.collecteRemovedBoundViewInformation(pOID);
            this.collecteRemovedDecisionRuleList(pOID);
            this.collecteRemovedFormFieldAccessDefinition(pOID);
            this.collecteRemovedActType(pOID);
            this.collecteRemovedDecisionRule(pOID);
            this.collecteRemovedTool(pOID);
            this.collecteRemovedFormOperationDefinition(pOID);
            this.collecteRemovedRelevantDataDefinition(pOID);
            this.collecteRemovedApplicationDefinition(pOID);
            this.collecteRemovedFormalParameter(pOID);
            this.collecteRemovedDataType(pOID);
            this.collecteRemovedTransitionDefinition(pOID);
            this.collecteRemovedConditionDefinition(pOID);
            this.collecteRemovedTransitionRestriction(pOID);
        } catch (SQLException e){
            throw e;
        }
    }
    private void collecteRemovedDef(String pOID) throws SQLException{
        //先收集ProcessViewInformation
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        //if (tProcessPackage.getStringTypeProcessDefOIDs() != null){
        String tSQLSelectProcessViewInformationOID = "Select processViewInformationOID, actionAfterAbortedOID, actionAfterTerminatedOID, actionAfterCompletedOID from " + ProcessPackage.PROCESS_DEFINITION_TABLE_NAME +
                " where OID = ?";
        PreparedStatement tSelectProcessViewInformationOIDStat =
                (PreparedStatement)this.preparedStatements.get(tSQLSelectProcessViewInformationOID);
        try{
            if (tSelectProcessViewInformationOIDStat == null){
                tSelectProcessViewInformationOIDStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectProcessViewInformationOID);
                this.preparedStatements.put(tSQLSelectProcessViewInformationOID, tSelectProcessViewInformationOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.PROCESS_DEFINITION_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        //先找ProcessViewInformation定義的OID
        ResultSet tProcessViewInformationOIDRS = null;
        String tProcessDefOID = null;
        String tProcessViewInformationOID = null;
        Iterator tIterProcessDefsOID = tProcessPackage.getProcessDefOIDs().iterator();
        while (tIterProcessDefsOID.hasNext()){
            tProcessDefOID = (String)tIterProcessDefsOID.next();
            try{
                tSelectProcessViewInformationOIDStat.setString(1, tProcessDefOID);
                tProcessViewInformationOIDRS = tSelectProcessViewInformationOIDStat.executeQuery();
                if (tProcessViewInformationOIDRS.next()){
                    tProcessViewInformationOID = tProcessViewInformationOIDRS.getString("processViewInformationOID");
                    if (tProcessViewInformationOID != null && !"".equals(tProcessViewInformationOID)){
                        if(! this.checkRefByAnotherObject(ProcessPackage.PROCESS_DEFINITION_TABLE_NAME,
                                "processViewInformationOID", tProcessViewInformationOID, tProcessPackage.getStringTypeProcessDefOIDs(), "OID"))
                            tProcessPackage.addProcessViewInformationOID(tProcessViewInformationOID);
                    }
                    tProcessPackage.addActivityTypeOID(tProcessViewInformationOIDRS.getString("actionAfterAbortedOID"));
                    tProcessPackage.addActivityTypeOID(tProcessViewInformationOIDRS.getString("actionAfterTerminatedOID"));
                    tProcessPackage.addActivityTypeOID(tProcessViewInformationOIDRS.getString("actionAfterCompletedOID"));
                } else {
                    System.out.println("Can not find ProcessDefinition by OID. OID='" + tProcessDefOID + "'");
                }
                tProcessViewInformationOIDRS.close();
                //tSelectProcessViewInformationOIDStat.close();
                this.deleteProcessPackage.put(pOID, tProcessPackage);
            } catch (SQLException e){
                System.out.println("Excute query " + ProcessPackage.PACKAGE_B_DEFINITION_TABLE_NAME +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
        //}
    }
    private void collecteRemovedActSet(String pOID) throws SQLException{
        //先收集ActivitySetDefinition
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        //if (tProcessPackage.getStringTypeProcessDefOIDs() != null){
        String tSQLSelectActivitySetDefinitionOID = "Select OID from " + ProcessPackage.ACTIVITY_SET_TABLE_NAME +
                " where containerOID = ? ";
        PreparedStatement tSelectActivitySetDefinitionOIDStat = (PreparedStatement)this.preparedStatements.get(tSQLSelectActivitySetDefinitionOID);
        try{
            if (tSelectActivitySetDefinitionOIDStat == null){
                tSelectActivitySetDefinitionOIDStat = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectActivitySetDefinitionOID);
                this.preparedStatements.put(tSQLSelectActivitySetDefinitionOID, tSelectActivitySetDefinitionOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.ACTIVITY_SET_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tActivitySetDefinitionOIDRS = null;
        String tProcessDefOID = null;
        Iterator tIterProcessDefsOID = tProcessPackage.getProcessDefOIDs().iterator();
        String tActivitySetDefinitionOID = null;
        while (tIterProcessDefsOID.hasNext()){
            tProcessDefOID = (String)tIterProcessDefsOID.next();
            try{
                tSelectActivitySetDefinitionOIDStat.setString(1, tProcessDefOID);
                tActivitySetDefinitionOIDRS = tSelectActivitySetDefinitionOIDStat.executeQuery();
                //先找ActivitySetDefinition定義的OID
                while (tActivitySetDefinitionOIDRS.next()){
                    tActivitySetDefinitionOID = tActivitySetDefinitionOIDRS.getString("OID");
                    tProcessPackage.addActivitySetDefinitionOID(tActivitySetDefinitionOID);
                }
                this.jdbcHelper.releaseResultSet(tActivitySetDefinitionOIDRS);
            } catch (SQLException e){
                System.out.println("Execute " + tSQLSelectActivitySetDefinitionOID +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
        this.deleteProcessPackage.put(pOID, tProcessPackage);
        //}
    }
    private void collecteRemovedAct(String pOID) throws SQLException{
        //先收集ActivitySetDefinition
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);

        String tSQLSelectActivityDefinitionOID = "Select OID, activityTypeOID, decisionRuleListOID, boundViewInformationOID, formFieldAccessDefinitionOID from " + ProcessPackage.ACTIVITY_DEF_TABLE_NAME +
                " where containerOID = ? ";
        PreparedStatement tSelectActivityDefinitionOIDStat = (PreparedStatement)this.preparedStatements.get(tSQLSelectActivityDefinitionOID);
        try{
            if (tSelectActivityDefinitionOIDStat == null){
                tSelectActivityDefinitionOIDStat = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectActivityDefinitionOID);
                this.preparedStatements.put(tSQLSelectActivityDefinitionOID, tSelectActivityDefinitionOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.ACTIVITY_DEF_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        //先找ActivityDefinition定義的OID
        ResultSet tActivityDefinitionOIDRS = null;
        String tActDefContainerOID = null;
        String tActivityDefinitionOID = null;
        try{
            Iterator tIterActDefContainerOIDs = tProcessPackage.getActDefContainerOIDs().iterator();
            while (tIterActDefContainerOIDs.hasNext()){
                tActDefContainerOID = (String)tIterActDefContainerOIDs.next();
                tSelectActivityDefinitionOIDStat.setString(1, tActDefContainerOID);
                tActivityDefinitionOIDRS = tSelectActivityDefinitionOIDStat.executeQuery();
                while (tActivityDefinitionOIDRS.next()){
                    tActivityDefinitionOID = tActivityDefinitionOIDRS.getString("OID");
                    tProcessPackage.addActivityDefinitionOID(tActivityDefinitionOID);
                    tProcessPackage.addActivityTypeOID(tActivityDefinitionOIDRS.getString("activityTypeOID"));
                    tProcessPackage.addBoundViewInformationOID(tActivityDefinitionOIDRS.getString("boundViewInformationOID"));
                    tProcessPackage.addDecisionRuleListOID(tActivityDefinitionOIDRS.getString("decisionRuleListOID"));
                    tProcessPackage.addFormFieldAccessDefinitionOID(tActivityDefinitionOIDRS.getString("formFieldAccessDefinitionOID"));
                }
                this.jdbcHelper.releaseResultSet(tActivityDefinitionOIDRS);
            }
            this.deleteProcessPackage.put(pOID, tProcessPackage);
        } catch (SQLException e){
            System.out.println("Excute query '" + tSQLSelectActivityDefinitionOID +
                               "' statement error." + e.getMessage());
            throw e;
        }
    }
    private void collecteRemovedTransitionDefinition(String pOID) throws SQLException{
        //先收集TransitionDefinition
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        String tSQLSelectTransitionDefinitionOID = "Select OID, conditionOID from " + ProcessPackage.TRANSITION_DEF_TABLE_NAME +
                " where containerOID = ? ";
        PreparedStatement tSelectTransitionDefinitionOIDStat =
                (PreparedStatement)this.preparedStatements.get(tSQLSelectTransitionDefinitionOID);
        try{
            if (tSelectTransitionDefinitionOIDStat == null){
                tSelectTransitionDefinitionOIDStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectTransitionDefinitionOID);
                this.preparedStatements.put(tSQLSelectTransitionDefinitionOID, tSelectTransitionDefinitionOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.TRANSITION_DEF_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        //先找TransitionDefinition定義的OID
        ResultSet tTransitionDefinitionOIDRS = null;
        String tTransitionDefinitionOID = null;
        try{
            String tActDefContainerOID = null;
            Iterator tIterActDefContainerOIDs = tProcessPackage.getActDefContainerOIDs().iterator();
            while (tIterActDefContainerOIDs.hasNext()){
                tActDefContainerOID = (String)tIterActDefContainerOIDs.next();
                tSelectTransitionDefinitionOIDStat.setString(1, tActDefContainerOID);
                tTransitionDefinitionOIDRS = tSelectTransitionDefinitionOIDStat.executeQuery();
                while (tTransitionDefinitionOIDRS.next()){
                    tTransitionDefinitionOID = tTransitionDefinitionOIDRS.getString("OID");
                    tProcessPackage.addTransitionDefinitionOID(tTransitionDefinitionOID);
                    tProcessPackage.addConditionDefinitionOID(tTransitionDefinitionOIDRS.getString("conditionOID"));
                }
                tTransitionDefinitionOIDRS.close();
            }
            this.deleteProcessPackage.put(pOID, tProcessPackage);
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessPackage.TRANSITION_DEF_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void collecteRemovedProcessViewInformation(String pOID) throws SQLException{
        //先收集ActivitySetDefinition
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);

        String tSQLSelectProcessViewInfoOID = "Select startActivityBoundInfoOID, endActivityBoundInfoOID from " + ProcessPackage.PROCESS_BOUND_VIEW_TABLE_NAME +
                " where OID = ? ";
        PreparedStatement tSelectProcessViewInfoOIDStat =
                (PreparedStatement)this.preparedStatements.get(tSQLSelectProcessViewInfoOID);
        try{
            if (tSelectProcessViewInfoOIDStat == null){
                tSelectProcessViewInfoOIDStat = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectProcessViewInfoOID);
                this.preparedStatements.put(
                        tSQLSelectProcessViewInfoOID, tSelectProcessViewInfoOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.PROCESS_BOUND_VIEW_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        //先找ProcessViewInfo定義的OID
        ResultSet tProcessViewInfoOIDRS = null;
        try{
            Iterator tIterProcessViewInfoOIDs = tProcessPackage.getProcessViewInformationOIDs().iterator();
            String tProcessViewInfoOID = null;
            while (tIterProcessViewInfoOIDs.hasNext()){
                tProcessViewInfoOID = (String)tIterProcessViewInfoOIDs.next();
                tSelectProcessViewInfoOIDStat.setString(1, tProcessViewInfoOID);
                tProcessViewInfoOIDRS = tSelectProcessViewInfoOIDStat.executeQuery();
                while (tProcessViewInfoOIDRS.next()){
                    tProcessPackage.addBoundViewInformationOID(tProcessViewInfoOIDRS.getString("startActivityBoundInfoOID"));
                    tProcessPackage.addBoundViewInformationOID(tProcessViewInfoOIDRS.getString("endActivityBoundInfoOID"));
                }
                this.jdbcHelper.releaseResultSet(tProcessViewInfoOIDRS);
            }
            this.deleteProcessPackage.put(pOID, tProcessPackage);
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessPackage.PACKAGE_B_DEFINITION_TABLE_NAME +
                               " statement error." + e.getMessage());

            throw e;
        }
    }
    private void collecteRemovedBoundViewInformation(String pOID) throws SQLException{
        //在collecteRemovedAct 與 collecteRemovedProcessViewInformation 已經把BoundViewInformation 填入
        //只要檢查是否有被不需移除的物件參考即可
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        if (tProcessPackage.getBoundViewInformationOIDs().size() > 0){
            Iterator tIterBoundViewInformationOIDs = tProcessPackage.getBoundViewInformationOIDs().iterator();
            String tBoundViewInformationOID = null;
            Set tRemove = new HashSet();
            while (tIterBoundViewInformationOIDs.hasNext()){
                tBoundViewInformationOID = (String)tIterBoundViewInformationOIDs.next();
                if (this.checkRefByAnotherObject(ProcessPackage.PROCESS_BOUND_VIEW_TABLE_NAME,
                        "startActivityBoundInfoOID", tBoundViewInformationOID,
                        tProcessPackage.getStringTypeProcessViewInformationOIDs(), "OID")){
                    tRemove.add(tBoundViewInformationOID);
                    continue;
                }
                if (this.checkRefByAnotherObject(ProcessPackage.PROCESS_BOUND_VIEW_TABLE_NAME,
                        "endActivityBoundInfoOID", tBoundViewInformationOID,
                        tProcessPackage.getStringTypeProcessViewInformationOIDs(), "OID")){
                    tRemove.add(tBoundViewInformationOID);
                    continue;
                }
                if (this.checkRefByAnotherObject(ProcessPackage.ACTIVITY_DEF_TABLE_NAME,
                        "boundViewInformationOID", tBoundViewInformationOID,
                        tProcessPackage.getStringTypeActivityDefOIDs(), "OID")){
                    tRemove.add(tBoundViewInformationOID);
                }
            }
            if (tRemove.size() > 0){
                tProcessPackage.getBoundViewInformationOIDs().removeAll(tRemove);
            }
        }
    }
    private void collecteRemovedDecisionRuleList(String pOID) throws SQLException{
        //在collecteRemovedAct 已經把DecisionRuleList 填入
        //只要檢查是否有被不需移除的物件參考即可
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        if (tProcessPackage.getDecisionRuleListOIDs().size() > 0){
            Iterator tIterDecisionRuleListOIDs = tProcessPackage.getDecisionRuleListOIDs().iterator();
            String tDecisionRuleListOID = null;
            Set tRemove = new HashSet();
            while (tIterDecisionRuleListOIDs.hasNext()){
                tDecisionRuleListOID = (String)tIterDecisionRuleListOIDs.next();
                if (this.checkRefByAnotherObject(ProcessPackage.ACTIVITY_DEF_TABLE_NAME,
                        "decisionRuleListOID", tDecisionRuleListOID,
                        tProcessPackage.getStringTypeActivityDefOIDs(), "OID")){
                    tRemove.add(tDecisionRuleListOID);
                }
            }
            if (tRemove.size() > 0){
                tProcessPackage.getDecisionRuleListOIDs().removeAll(tRemove);
            }


            try{
                String tQueryDecisionCondition =
                        "Select OID from " +  ProcessPackage.DECISION_COND_TABLE_NAME + " where containerOID = ? ";
                String tQueryDecisionLevel =
                        "Select OID from  " +  ProcessPackage.DECISION_LEVEL_TABLE_NAME + " where containerOID = ? ";
                PreparedStatement tDecisionConditionPS =
                        (PreparedStatement)this.preparedStatements.get(tQueryDecisionCondition);
                try{
                    if (tDecisionConditionPS == null){
                        tDecisionConditionPS = this.jdbcHelper.createSourcePrepareStatement(tQueryDecisionCondition);
                        this.preparedStatements.put(tQueryDecisionCondition, tDecisionConditionPS);
                    }
                } catch (SQLException e){
                    System.out.println("Create " + ProcessPackage.DECISION_COND_TABLE_NAME +
                                       " preparedStatement error." + e.getMessage());
                    throw e;
                }
                PreparedStatement tDecisionLevelPS =
                        (PreparedStatement)this.preparedStatements.get(tQueryDecisionLevel);
                try{
                    if (tDecisionLevelPS == null){
                        tDecisionLevelPS = this.jdbcHelper.createSourcePrepareStatement(tQueryDecisionLevel);
                        this.preparedStatements.put(tQueryDecisionLevel, tDecisionLevelPS);
                    }
                } catch (SQLException e){
                    System.out.println("Create " + ProcessPackage.DECISION_LEVEL_TABLE_NAME +
                                       " preparedStatement error." + e.getMessage());
                    throw e;
                }
                tDecisionRuleListOID = null;
                ResultSet tTempRS = null;

                Iterator tIterNewDecisionRuleListOIDs = tProcessPackage.getDecisionRuleListOIDs().iterator();
                while (tIterNewDecisionRuleListOIDs.hasNext()){
                    tDecisionRuleListOID = (String)tIterNewDecisionRuleListOIDs.next();
                    tDecisionConditionPS.setString(1, tDecisionRuleListOID);
                    tTempRS = tDecisionConditionPS.executeQuery();
                    while (tTempRS.next()){
                        tProcessPackage.addDecisionConditionOID(tTempRS.getString("OID"));
                    }
                    this.jdbcHelper.releaseResultSet(tTempRS);
                    tDecisionLevelPS.setString(1, tDecisionRuleListOID);
                    tTempRS = tDecisionLevelPS.executeQuery();
                    while (tTempRS.next()){
                        tProcessPackage.addDecisionLevelOID(tTempRS.getString("OID"));
                    }
                    this.jdbcHelper.releaseResultSet(tTempRS);
                }
            } catch (SQLException e){
                System.out.println("Excute query activity type " +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void collecteRemovedConditionDefinition(String pOID) throws SQLException{
        //在collecteRemovedTransicition 已經把ConditionDefinition OID 填入
        //只要檢查是否有被不需移除的物件參考即可
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        if (tProcessPackage.getConditionDefinitionOIDs().size() > 0){
            Iterator tIterConditionDefinitionOIDs = tProcessPackage.getConditionDefinitionOIDs().iterator();
            String tConditionDefinitionOID = null;
            Set tRemove = new HashSet();
            while (tIterConditionDefinitionOIDs.hasNext()){
                tConditionDefinitionOID = (String)tIterConditionDefinitionOIDs.next();
                if (this.checkRefByAnotherObject(ProcessPackage.TRANSITION_DEF_TABLE_NAME,
                        "conditionOID", tConditionDefinitionOID,
                        tProcessPackage.getStringTypeTransitionDefinitionOIDs(), "OID")){
                    tRemove.add(tConditionDefinitionOID);
                }
            }
            if (tRemove.size() > 0){
                tProcessPackage.getConditionDefinitionOIDs().removeAll(tRemove);
            }
        }
    }
    private void collecteRemovedFormFieldAccessDefinition(String pOID) throws SQLException{
        //在collecteRemovedAct 已經把FormFieldAccessDefinition 填入
        //只要檢查是否有被不需移除的物件參考即可
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        if (tProcessPackage.getFormFieldAccessDefinitionOIDs().size() > 0){
            Iterator tIterFormFieldAccessDefinitionOIDs = tProcessPackage.getFormFieldAccessDefinitionOIDs().iterator();
            String tFormFieldAccessDefinitionOID = null;
            List tRemove = new ArrayList();
            while (tIterFormFieldAccessDefinitionOIDs.hasNext()){
                tFormFieldAccessDefinitionOID = (String)tIterFormFieldAccessDefinitionOIDs.next();
                if (this.checkRefByAnotherObject(ProcessPackage.ACTIVITY_DEF_TABLE_NAME,
                        "formFieldAccessDefinitionOID", tFormFieldAccessDefinitionOID,
                        tProcessPackage.getStringTypeActivityDefOIDs(), "OID")){
                    tRemove.add(tFormFieldAccessDefinitionOID);
                }
            }
            if (tRemove.size() > 0){
                tProcessPackage.getFormFieldAccessDefinitionOIDs().removeAll(tRemove);
            }
        }
    }
    private void collecteRemovedActType(String pOID) throws SQLException{
        //在collecteRemovedAct 已經把Activity Type 填入
        //只要檢查將之分類
        try{
            ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
            String tSelectImpl = "select count(OID) from " + ProcessPackage.IMPLEMENTATION_TABLE_NAME + " where OID = ?";
            String tSelectBlockAct = "select count(OID) from " + ProcessPackage.BLOCK_ACT_TABLE_NAME + " where OID = ?";
            String tSelectRoute = "select count(OID) from " + ProcessPackage.ROUTE_TABLE_NAME + " where OID = ?";
            PreparedStatement tImplPS =
                    (PreparedStatement)this.preparedStatements.get(tSelectImpl);
            try{
                if (tImplPS == null){
                    tImplPS = this.jdbcHelper.createSourcePrepareStatement(tSelectImpl);
                    this.preparedStatements.put(tSelectImpl, tImplPS);
                }
            } catch (SQLException e){
                System.out.println("Create " + ProcessPackage.IMPLEMENTATION_TABLE_NAME +
                                   " preparedStatement error." + e.getMessage());
                throw e;
            }
            PreparedStatement tBlockActPS =
                    (PreparedStatement)this.preparedStatements.get(tSelectBlockAct);
            try{
                if (tBlockActPS == null){
                    tBlockActPS = this.jdbcHelper.createSourcePrepareStatement(tSelectBlockAct);
                    this.preparedStatements.put(tSelectBlockAct, tBlockActPS);
                }
            } catch (SQLException e){
                System.out.println("Create " + ProcessPackage.BLOCK_ACT_TABLE_NAME +
                                   " preparedStatement error." + e.getMessage());
                throw e;
            }
            PreparedStatement tRoutePS =
                    (PreparedStatement)this.preparedStatements.get(tSelectRoute);
            try{
                if (tRoutePS == null){
                    tRoutePS = this.jdbcHelper.createSourcePrepareStatement(tSelectRoute);
                    this.preparedStatements.put(tSelectRoute, tRoutePS);
                }
            } catch (SQLException e){
                System.out.println("Create " + ProcessPackage.ROUTE_TABLE_NAME +
                                   " preparedStatement error." + e.getMessage());
                throw e;
            }

            if (tProcessPackage.getActivityTypeOIDs().size() > 0){
                ResultSet tTempRS = null;
                Iterator tIterActivityTypeOIDs = tProcessPackage.getActivityTypeOIDs().iterator();
                String tActivityTypeOID = null;
                while (tIterActivityTypeOIDs.hasNext()){
                    tActivityTypeOID = (String)tIterActivityTypeOIDs.next();
                    tBlockActPS.setString(1, tActivityTypeOID);
                    tTempRS = tBlockActPS.executeQuery();
                    tTempRS.next();
                    if (tTempRS.getInt(1) == 1){
                        //這個tActivityTypeOID 是BlockAct
                        if (!this.checkRefByAnotherObject(ProcessPackage.ACTIVITY_DEF_TABLE_NAME,
                                "activityTypeOID", tActivityTypeOID,
                                tProcessPackage.getStringTypeActivityDefOIDs(), "OID")){
                            tProcessPackage.addBlockActivityOID(tActivityTypeOID);
                        }
                    }
                    tTempRS.close();
                    tRoutePS.setString(1, tActivityTypeOID);
                    tTempRS = tRoutePS.executeQuery();
                    tTempRS.next();
                    if (tTempRS.getInt(1) == 1){
                        //這個tActivityTypeOID 是Route
                        if (!this.checkRefByAnotherObject(ProcessPackage.ACTIVITY_DEF_TABLE_NAME,
                                "activityTypeOID", tActivityTypeOID,
                                tProcessPackage.getStringTypeActivityDefOIDs(), "OID")){
                            tProcessPackage.addRouteOID(tActivityTypeOID);
                        }
                    }
                    tTempRS.close();
                    tImplPS.setString(1, tActivityTypeOID);
                    tTempRS = tImplPS.executeQuery();
                    tTempRS.next();
                    if (tTempRS.getInt(1) == 1){
                        //這個tActivityTypeOID 是Implementation
                        if (this.checkRefByAnotherObject(ProcessPackage.ACTIVITY_DEF_TABLE_NAME,
                                "activityTypeOID", tActivityTypeOID,
                                tProcessPackage.getStringTypeActivityDefOIDs())){
                            tTempRS.close();
                            continue;
                        }
                        if (this.checkRefByAnotherObject(ProcessPackage.PROCESS_DEFINITION_TABLE_NAME,
                                "actionAfterAbortedOID", tActivityTypeOID, tProcessPackage.getStringTypeProcessDefOIDs())){
                            tTempRS.close();
                            continue;
                        }
                        if (this.checkRefByAnotherObject(ProcessPackage.PROCESS_DEFINITION_TABLE_NAME,
                                "actionAfterTerminatedOID", tActivityTypeOID, tProcessPackage.getStringTypeProcessDefOIDs())){
                            tTempRS.close();
                            continue;
                        }
                        if (this.checkRefByAnotherObject(ProcessPackage.PROCESS_DEFINITION_TABLE_NAME,
                                "actionAfterCompletedOID", tActivityTypeOID, tProcessPackage.getStringTypeProcessDefOIDs())){
                            tTempRS.close();
                            continue;
                        }
                        tProcessPackage.addImplementationOID(tActivityTypeOID);
                    }
                    tTempRS.close();
                }
            }
        } catch (SQLException e){
            System.out.println("Excute query activity type " +
                               " statement error." + e.getMessage());

            throw e;
        }
    }
    private void collecteRemovedDecisionRule(String pOID) throws SQLException{
        //先收集DecisionRule
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        ResultSet tDecisionRuleOIDRS = null;
        String tDecisionRuleOID = null;
        if (tProcessPackage.getDecisionConditionOIDs().size() > 0){
            String tSQLSelectDecisionRuleByCond = "Select OID from " +
                    ProcessPackage.DECISION_RULE_TABLE_NAME + " where decisionConditionOID = ? ";
            PreparedStatement tSelectDecisionRuleOIDByCondStat =
                    (PreparedStatement)this.preparedStatements.get(tSQLSelectDecisionRuleByCond);
            try{
                if (tSelectDecisionRuleOIDByCondStat == null){
                    tSelectDecisionRuleOIDByCondStat = this.jdbcHelper.createSourcePrepareStatement(
                            tSQLSelectDecisionRuleByCond);
                    this.preparedStatements.put(tSQLSelectDecisionRuleByCond,
                            tSelectDecisionRuleOIDByCondStat);
                }
            } catch (SQLException e){
                System.out.println("Create " + ProcessPackage.DECISION_RULE_TABLE_NAME +
                                   " preparedStatement error." + e.getMessage());
                throw e;
            }
            //先找DecisionRule定義的OID
            try{
                Iterator tIterDecisionConditionOIDs = tProcessPackage.getDecisionConditionOIDs().iterator();
                String tDecisionConditionOID = null;
                while (tIterDecisionConditionOIDs.hasNext()){
                    tDecisionConditionOID = (String)tIterDecisionConditionOIDs.next();
                    tSelectDecisionRuleOIDByCondStat.setString(1, tDecisionConditionOID);
                    tDecisionRuleOIDRS = tSelectDecisionRuleOIDByCondStat.executeQuery();
                    while (tDecisionRuleOIDRS.next()){
                        tDecisionRuleOID = tDecisionRuleOIDRS.getString("OID");
                        tProcessPackage.addDecisionRuleOID(tDecisionRuleOID);
                    }
                    tDecisionRuleOIDRS.close();
                }
            } catch (SQLException e){
                System.out.println("Excute query " + ProcessPackage.DECISION_RULE_TABLE_NAME +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
        if (tProcessPackage.getDecisionLevelOIDs().size() > 0){
            String tSQLSelectDecisionRuleByLevel = "Select OID from " +
                    ProcessPackage.DECISION_RULE_TABLE_NAME + " where decisionLevelOID = ? ";
            PreparedStatement tSelectDecisionRuleOIDByLevelStat =
                    (PreparedStatement)this.preparedStatements.get(tSQLSelectDecisionRuleByLevel);
            try{
                if (tSelectDecisionRuleOIDByLevelStat == null){
                    tSelectDecisionRuleOIDByLevelStat = this.jdbcHelper.createSourcePrepareStatement(
                            tSQLSelectDecisionRuleByLevel);
                    this.preparedStatements.put(tSQLSelectDecisionRuleByLevel,
                            tSelectDecisionRuleOIDByLevelStat);
                }
            } catch (SQLException e){
                System.out.println("Create " + ProcessPackage.DECISION_RULE_TABLE_NAME +
                                   " preparedStatement error." + e.getMessage());
                throw e;
            }
            //先找DecisionRule定義的OID
            try{
                Iterator tIterDecisionLevelOIDs =
                        tProcessPackage.getDecisionLevelOIDs().iterator();
                String tDecisionLevelOID = null;
                while (tIterDecisionLevelOIDs.hasNext()){
                    tDecisionLevelOID = (String)tIterDecisionLevelOIDs.next();
                    tSelectDecisionRuleOIDByLevelStat.setString(1, tDecisionLevelOID);
                    tDecisionRuleOIDRS = tSelectDecisionRuleOIDByLevelStat.executeQuery();
                    while (tDecisionRuleOIDRS.next()){
                        tDecisionRuleOID = tDecisionRuleOIDRS.getString("OID");
                        tProcessPackage.addDecisionRuleOID(tDecisionRuleOID);
                    }
                    tDecisionRuleOIDRS.close();
                }
            } catch (SQLException e){
                System.out.println("Excute query " + ProcessPackage.DECISION_RULE_TABLE_NAME +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
        this.deleteProcessPackage.put(pOID, tProcessPackage);
    }
    private void collecteRemovedTool(String pOID) throws SQLException{
        //先收集Tool
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        String tSQLSelectToolOID = "Select OID, applicationModeOID from " + ProcessPackage.TOOL_TABLE_NAME +
                                   " where containerOID = ? ";
        PreparedStatement tSelectToolOIDStat =
                (PreparedStatement)this.preparedStatements.get(tSQLSelectToolOID);
        try{
            if (tSelectToolOIDStat == null){
                tSelectToolOIDStat = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectToolOID);
                this.preparedStatements.put(tSQLSelectToolOID, tSelectToolOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.TOOL_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        //先找Tool定義的OID
        ResultSet tToolOIDRS = null;
        try{
            Iterator tIterImplementationOIDs = tProcessPackage.getImplementationOIDs().iterator();
            String tToolContainerOID = null;
            while (tIterImplementationOIDs.hasNext()){
                tToolContainerOID = (String)tIterImplementationOIDs.next();
                tSelectToolOIDStat.setString(1, tToolContainerOID);
                tToolOIDRS = tSelectToolOIDStat.executeQuery();
                while (tToolOIDRS.next()){
                    tProcessPackage.addToolOID(tToolOIDRS.getString("OID"));
                    tProcessPackage.addFormOperationDefinitionOID(
                            tToolOIDRS.getString("applicationModeOID"));
                }
                tToolOIDRS.close();
            }
            this.deleteProcessPackage.put(pOID, tProcessPackage);
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessPackage.TOOL_TABLE_NAME +
                               " statement error." + e.getMessage());

            throw e;
        }
    }
    private void collecteRemovedFormOperationDefinition(String pOID) throws SQLException{
        //在collecteRemovedAct 已經把FormOperationDefinition 填入
        //只要檢查是否有被不需移除的物件參考即可
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        if (tProcessPackage.getFormOperationDefinitionOIDs().size() > 0){
            Iterator tIterFormOperationDefinitionOIDs = tProcessPackage.getFormOperationDefinitionOIDs().iterator();
            String tFormOperationDefinitionOID = null;
            List tRemove = new ArrayList();
            while (tIterFormOperationDefinitionOIDs.hasNext()){
                tFormOperationDefinitionOID = (String)tIterFormOperationDefinitionOIDs.next();
                if (this.checkRefByAnotherObject(ProcessPackage.TOOL_TABLE_NAME,
                        "applicationModeOID", tFormOperationDefinitionOID, tProcessPackage.getStringTypeToolOIDs())){
                    tRemove.add(tFormOperationDefinitionOID);
                }
            }
            if (tRemove.size() > 0){
                tProcessPackage.getFormOperationDefinitionOIDs().removeAll(tRemove);
            }
        }
    }
    private void collecteRemovedRelevantDataDefinition(String pOID) throws SQLException{
        //先收集RelevantDataDefinition
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        //if (tProcessPackage.getStringTypePackageAndDefOIDs() != null){
        String tSQLSelectRelevantDataDefinitionOID = "Select OID, dataTypeOID from " +
                ProcessPackage.RELEVANT_DATA_DEF_TABLE_NAME + " where containerOID = ? ";
        PreparedStatement tSelectRelevantDataDefinitionStat =
                (PreparedStatement)this.preparedStatements.get(tSQLSelectRelevantDataDefinitionOID);
        try{
            if (tSelectRelevantDataDefinitionStat == null){
                tSelectRelevantDataDefinitionStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectRelevantDataDefinitionOID);
                this.preparedStatements.put(tSQLSelectRelevantDataDefinitionOID, tSelectRelevantDataDefinitionStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.RELEVANT_DATA_DEF_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        //先找RelevantDataDefinition定義的OID
        ResultSet tRelevantDataDefinitionOIDRS = null;
        String tRelevantDataDefinitionOID = null;
        try{
            Iterator tIterRelevantDataDefContainerOIDs =
                    tProcessPackage.getPackageAndDefOIDs().iterator();
            String tRelevantDataDefContainerOID = null;
            while (tIterRelevantDataDefContainerOIDs.hasNext()){
                tRelevantDataDefContainerOID =
                        (String)tIterRelevantDataDefContainerOIDs.next();
                tSelectRelevantDataDefinitionStat.setString(1, tRelevantDataDefContainerOID);
                tRelevantDataDefinitionOIDRS = tSelectRelevantDataDefinitionStat.executeQuery();
                while (tRelevantDataDefinitionOIDRS.next()){
                    tRelevantDataDefinitionOID = tRelevantDataDefinitionOIDRS.getString("OID");
                    tProcessPackage.addRelevantDataDefinitionOID(tRelevantDataDefinitionOID);
                    tProcessPackage.addDataTypeOID(tRelevantDataDefinitionOIDRS.getString("dataTypeOID"));
                }
                tRelevantDataDefinitionOIDRS.close();
            }
            this.deleteProcessPackage.put(pOID, tProcessPackage);
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessPackage.RELEVANT_DATA_DEF_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
    }

    private void collecteRemovedApplicationDefinition(String pOID) throws SQLException{
        //先收集ApplicationDefinition
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        String tSQLSelectApplicationDefinitionOID = "Select ApplicationDefinitionOID from " + ProcessPackage.APPLICATION_CONTAINER_B_APPLICATION_TABLE_NAME +
                " where IAppDefContainerOID = ? ";
        PreparedStatement tSelectApplicationDefinitionOIDStat =
                (PreparedStatement)this.preparedStatements.get(tSQLSelectApplicationDefinitionOID);
        try{
            if (tSelectApplicationDefinitionOIDStat == null){
                tSelectApplicationDefinitionOIDStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectApplicationDefinitionOID);
                this.preparedStatements.put(tSQLSelectApplicationDefinitionOID, tSelectApplicationDefinitionOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.APPLICATION_CONTAINER_B_APPLICATION_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }

        //先找ApplicationDefinition定義的OID
        ResultSet tApplicationDefinitionOIDRS = null;
        String tApplicationDefinitionOID = null;
        try{
            Iterator tIterApplicationDefContainerOIDs =
                    tProcessPackage.getPackageAndDefOIDs().iterator();
            String tApplicationDefContainerOID = null;
            while (tIterApplicationDefContainerOIDs.hasNext()){
                tApplicationDefContainerOID =
                        (String)tIterApplicationDefContainerOIDs.next();
                tSelectApplicationDefinitionOIDStat.setString(1, tApplicationDefContainerOID);
                tApplicationDefinitionOIDRS = tSelectApplicationDefinitionOIDStat.executeQuery();
                while (tApplicationDefinitionOIDRS.next()){
                    tApplicationDefinitionOID = tApplicationDefinitionOIDRS.getString("ApplicationDefinitionOID");
                    if (tApplicationDefinitionOID != null && !"".equals(tApplicationDefinitionOID)){
                        if(! this.checkRefByAnotherObject(ProcessPackage.APPLICATION_CONTAINER_B_APPLICATION_TABLE_NAME,
                                "ApplicationDefinitionOID", tApplicationDefinitionOID,
                                tProcessPackage.getStringTypePackageAndDefOIDs(), "IAppDefContainerOID"))
                            tProcessPackage.addApplicationDefinitionOID(tApplicationDefinitionOID);
                    }
                }
                tApplicationDefinitionOIDRS.close();
            }
            this.deleteProcessPackage.put(pOID, tProcessPackage);
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessPackage.APPLICATION_CONTAINER_B_APPLICATION_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void collecteRemovedFormalParameter(String pOID) throws SQLException{
        //先收集ApplicationDefinition
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        String tSQLSelectFormalParameterOID = "Select OID, dataTypeOID from " + ProcessPackage.FORMALPARAMETER_TABLE_NAME +
                " where containerOID = ? ";
        PreparedStatement tSelectFormalParameterOIDStat =
                (PreparedStatement)this.preparedStatements.get(tSQLSelectFormalParameterOID);
        try{
            if (tSelectFormalParameterOIDStat == null){
                tSelectFormalParameterOIDStat = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectFormalParameterOID);
                this.preparedStatements.put(tSQLSelectFormalParameterOID, tSelectFormalParameterOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.FORMALPARAMETER_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        //先找ApplicationDefinition定義的OID
        ResultSet tFormalParameterOIDRS = null;
        String tFormalParameterOID = null;
        try{
            Iterator tIterFormalParameterContainerOIDs =
                    tProcessPackage.getFormalParameterContainerOIDs().iterator();
            String tFormalParameterContainerOID = null;
            while (tIterFormalParameterContainerOIDs.hasNext()){
                tFormalParameterContainerOID =
                        (String)tIterFormalParameterContainerOIDs.next();
                tSelectFormalParameterOIDStat.setString(1, tFormalParameterContainerOID);
                tFormalParameterOIDRS = tSelectFormalParameterOIDStat.executeQuery();
                while (tFormalParameterOIDRS.next()){
                    tFormalParameterOID = tFormalParameterOIDRS.getString("OID");
                    tProcessPackage.addFormalParameterOID(tFormalParameterOID);
                    tProcessPackage.addDataTypeOID(tFormalParameterOIDRS.getString("dataTypeOID"));
                }
                tFormalParameterOIDRS.close();
            }
            this.deleteProcessPackage.put(pOID, tProcessPackage);
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessPackage.FORMALPARAMETER_TABLE_NAME +
                               " statement error." + e.getMessage());

            throw e;
        }
    }
    private void collecteRemovedDataType(String pOID) throws SQLException{
        //在collecte FormalParameter 與 RelevantDataDefinition 已經把DataType 填入
        //只要檢查是否有被不需移除的物件參考即可
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        if (tProcessPackage.getDataTypeOIDs().size() > 0){
            Iterator tIterDataTypeOIDs = tProcessPackage.getDataTypeOIDs().iterator();
            String tDataTypeOID = null;
            Set tRemove = new HashSet();
            while (tIterDataTypeOIDs.hasNext()){
                tDataTypeOID = (String)tIterDataTypeOIDs.next();
                if (this.checkRefByAnotherObject(ProcessPackage.FORMALPARAMETER_TABLE_NAME,
                        "dataTypeOID", tDataTypeOID, tProcessPackage.getStringTypeDataTypeOIDs())){
                    tRemove.add(tDataTypeOID);
                    continue;
                }
                if (this.checkRefByAnotherObject(ProcessPackage.RELEVANT_DATA_DEF_TABLE_NAME,
                        "dataTypeOID", tDataTypeOID, tProcessPackage.getStringTypeDataTypeOIDs())){
                    tRemove.add(tDataTypeOID);
                    continue;
                }
                if (this.checkRefByAnotherObject("ByValueParameter",
                        "dataTypeOID", tDataTypeOID, tProcessPackage.getStringTypeDataTypeOIDs())){
                    tRemove.add(tDataTypeOID);
                    continue;
                }
                if (this.checkRefByAnotherObject("GlobalRelevantData",
                        "dataTypeOID", tDataTypeOID, tProcessPackage.getStringTypeDataTypeOIDs())){
                    tRemove.add(tDataTypeOID);
                    continue;
                }
                if (this.checkRefByAnotherObject("LocalRelevantData",
                        "dataTypeOID", tDataTypeOID, tProcessPackage.getStringTypeDataTypeOIDs())){
                    tRemove.add(tDataTypeOID);
                    continue;
                }
                if (this.checkRefByAnotherObject("TypeDefinition",
                        "contentOID", tDataTypeOID, tProcessPackage.getStringTypeDataTypeOIDs())){
                    tRemove.add(tDataTypeOID);
                }
            }
            if (tRemove.size() > 0){
                tProcessPackage.getDataTypeOIDs().removeAll(tRemove);
            }
        }
    }
    private void collecteRemovedTransitionRestriction(String pOID) throws SQLException{
        //先收集TransitionRestriction
        ProcessPackage tProcessPackage = (ProcessPackage) this.deleteProcessPackage.get(pOID);
        String tSQLSelectTransitionRestrictionOID = "Select OID from " + ProcessPackage.TRANSITION_RESTRICTION_TABLE_NAME +
                " where containerOID = ? ";
        PreparedStatement tSelectTransitionRestrictionOIDStat =
                (PreparedStatement)this.preparedStatements.get(tSQLSelectTransitionRestrictionOID);
        try{
            if (tSelectTransitionRestrictionOIDStat == null){
                tSelectTransitionRestrictionOIDStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectTransitionRestrictionOID);
                this.preparedStatements.put(tSQLSelectTransitionRestrictionOID, tSelectTransitionRestrictionOIDStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.TRANSITION_RESTRICTION_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        //先找TransitionRestriction定義的OID
        ResultSet tTransitionRestrictionOIDRS = null;
        String tTransitionRestrictionOID = null;
        try{
            String tTransitionRestrictionContainerOID = null;
            Iterator tIterTransitionRestrictionContainerOIDs = tProcessPackage.getActivityDefinitionOIDs().iterator();
            while (tIterTransitionRestrictionContainerOIDs.hasNext()){
                tTransitionRestrictionContainerOID = (String)tIterTransitionRestrictionContainerOIDs.next();
                tSelectTransitionRestrictionOIDStat.setString(1, tTransitionRestrictionContainerOID);
                tTransitionRestrictionOIDRS = tSelectTransitionRestrictionOIDStat.executeQuery();
                while (tTransitionRestrictionOIDRS.next()){
                    tTransitionRestrictionOID = tTransitionRestrictionOIDRS.getString("OID");
                    tProcessPackage.addTransitionRestrictionOID(tTransitionRestrictionOID);
                }
                tTransitionRestrictionOIDRS.close();
            }
            this.deleteProcessPackage.put(pOID, tProcessPackage);
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessPackage.TRANSITION_RESTRICTION_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private boolean checkRefByAnotherObject(String pTableName, String pRefColumnName,
            String pRefValue, String pExcludeOIDs) throws SQLException{

        return this.checkRefByAnotherObject(pTableName, pRefColumnName, pRefValue, pExcludeOIDs, "OID");
    }
    private boolean checkRefByAnotherObject(String pTableName, String pRefColumnName,
            String pRefValue, String pExcludeOIDs, String pExcludeColumnName) throws SQLException{
        boolean tRtn = false;
        String tSQLSelect = null;
        //檢查是否有其他的table用到這些OID

        if (pExcludeColumnName == null || pExcludeColumnName.equals("")) {
//                tSQLSelect = "Select OID from " + pTableName +
//                             " where " + pRefColumnName + " = '" + pRefValue + "' and OID not in (" + pExcludeOIDs + ")";
            throw new IllegalArgumentException("pExcludeColumnName can be null or empty.");
        } else {
//                tSQLSelect = "Select pExcludeColumnName from " + pTableName +
//                             " where " + pRefColumnName + " = '" + pRefValue + "' and "+ pExcludeColumnName +" not in (" + pExcludeOIDs + ")";
            tSQLSelect = "Select " + pExcludeColumnName + " from " + pTableName +
                         " where " + pRefColumnName + " = ? ";
        }

        PreparedStatement tSelectRefOIDPreparedStat = (PreparedStatement)this.preparedStatements.get(tSQLSelect);
        try{
            if (tSelectRefOIDPreparedStat == null){
                tSelectRefOIDPreparedStat = this.jdbcHelper.createSourcePrepareStatement(tSQLSelect);
                this.preparedStatements.put(tSQLSelect, tSelectRefOIDPreparedStat);
            }
        } catch (SQLException e){
            System.out.println("Create '" + tSQLSelect +
                               "' preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tResultSet = null;
        try{
            tSelectRefOIDPreparedStat.setString(1, pRefValue);
            tResultSet = tSelectRefOIDPreparedStat.executeQuery();
            String tValue = null;
            while (tResultSet.next() && !tRtn){
                tValue = tResultSet.getString(1);
                if (pExcludeOIDs == null || pExcludeOIDs.equals("")){
                    tRtn =  true;
                } else {
                    //不在pExcludeOIDs 內的物件參考到它
                    if (pExcludeOIDs.indexOf(tValue) < 0)
                        tRtn =  true;
                }
            }
        } catch (SQLException e){
            System.out.println("checkRefByAnotherObject SQL command : '" +  tSQLSelect +
                               " statement error." + e.getMessage());
            throw e;
        } finally{
            tResultSet.close();
        }
        return tRtn;
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