package archive;

import java.util.Properties;
import java.sql.*;
import com.inet.tds.PDataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import java.io.FileReader;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

public class transInstance {
    private static final String PROPERTY_PATH_KEY_VALUE = "archive.home";
    private static final String SUBDIR = "conf";
    //private List columnName = new ArrayList();
    //Key 是table name, 值為該table columnsName 陣列
    private Map columnsNames = new HashMap();
    private Map deleteProcessInstance = new HashMap();
    private Map transProcessPackage = new HashMap();
    //Key 是table name, 值為該table MetaData
    private Map resultSetMetaDatas = new HashMap();
    //Key 是PreparedStatement, 值為該preparedStatement
    private Map sourcePreparedStatements = new HashMap();
    //Key 是PreparedStatement, 值為該preparedStatement
    private Map desPreparedStatements = new HashMap();
    private Properties databaseProperties = new Properties();
    private JDBCHelper jdbcHelper = null;
    public transInstance(JDBCHelper pJDBCHelper) {
        jdbcHelper = pJDBCHelper;
    }
    public static void main(String[] args) {
        JDBCHelper tJDBCHelper = null;

        try{
            tJDBCHelper = transInstance.getJDBCHelper(args[0]);
        } catch (FileNotFoundException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not found, please add this file.");
        } catch (IOException e) {
            //在此不要用log,因為當log要constructor時,會透過此物件,於是就會有NullPointException跑出來
            System.out.println("Connection File " + args[0] +
                               " not loaded, please check format of this file.");
        }
        transInstance transInstance1 = new transInstance(tJDBCHelper);
        if (args.length > 1)
        {
            try{
                //載入流程實例的OID資訊
                long tBeginTime = System.currentTimeMillis();
                transInstance1.loadProcessInstanceOID(args[1]);
                transInstance1.transProcessInstance();
                transInstance1.loadProcessPackage();
                transInstance1.transProcessPackage();
                try{
                    tJDBCHelper.getSourceConnection().commit();
                    tJDBCHelper.getDesConnection().commit();
                } catch (SQLException e1){
                    System.out.println("Commit Error.ErrMsg : " + e1.getMessage());
                }
                long tEndTime = System.currentTimeMillis();
                long tSpentSeconds = tEndTime - tBeginTime;
                System.out.println("Spent " + transInstance1.tranTime(tSpentSeconds) + " to trans ProcessInstance.");
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
                    tJDBCHelper.getDesConnection().rollback();
                } catch (SQLException e1){
                    System.out.println("Rollback Error.");
                }
            } finally {
                tJDBCHelper.releaseResource();
            }
        } else{
            try{
                //Timestamp tNow = new Timestamp(System.currentTimeMillis());
                long tBeginTime = System.currentTimeMillis();
                //System.out.println("Begin archiving ProcessInstance: " + tNow.toString());
                transInstance1.loadClosedProcessInstance();
                transInstance1.transProcessInstance();
                transInstance1.loadProcessPackage();
                transInstance1.transProcessPackage();
                try{
                    tJDBCHelper.getSourceConnection().commit();
                    tJDBCHelper.getDesConnection().commit();
                } catch (SQLException e1){
                    System.out.println("Commit Error.ErrMsg : " + e1.getMessage());
                }
                long tEndTime = System.currentTimeMillis();
                long tSpentSeconds = tEndTime - tBeginTime;
                System.out.println("Spent " + transInstance1.tranTime(tSpentSeconds) + " to trans ProcessInstance.");
            } catch (SQLException e){
                System.out.println("SQLException : " + e.getMessage());
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
    }
    public void loadClosedProcessInstance() throws SQLException {
        String tSQLSelectProcessInstance = "Select OID from " + ProcessInstance.PROCESS_INST_TABLE_NAME +
                " where currentState = 3 or currentState = 4 or currentState = 5";
        Statement tSelectProcessInstanceStat = null;
        try{
            tSelectProcessInstanceStat = this.jdbcHelper.createSourceStatement();
        } catch (SQLException e){
            System.out.println("select " + ProcessInstance.PROCESS_INST_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
        ResultSet tProcessInstanceRS = null;
        try{
            tProcessInstanceRS = tSelectProcessInstanceStat.executeQuery(tSQLSelectProcessInstance);
            ProcessInstance tProcessInstance = null;
            int tCount = 0;
            while (tProcessInstanceRS.next()){
                tCount ++;
                String tOID = tProcessInstanceRS.getString("OID");
                tProcessInstance = new ProcessInstance(tOID);
                this.deleteProcessInstance.put(tOID, tProcessInstance);
            }
            System.out.println("Archive " + tCount + " ProcessInstance(s).");
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessInstance.PROCESS_INST_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        } finally{
             this.jdbcHelper.releaseResultSet(tProcessInstanceRS);
             this.jdbcHelper.releaseStatement(tSelectProcessInstanceStat);
        }

    }
    private static JDBCHelper getJDBCHelper(String pFileName) throws FileNotFoundException, IOException{
        return new JDBCHelper(pFileName);
    }

    private void loadProcessInstanceOID(String pFileName) throws FileNotFoundException, IOException{
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
        for(int i=0; i < tArrayOfProcessInstanceOIDs.length; i++){
            tOID = tArrayOfProcessInstanceOIDs[i].trim();
            tProcessInstance = new ProcessInstance(tOID);
            this.deleteProcessInstance.put(tOID, tProcessInstance);
        }
    }
    public void loadProcessPackage() throws SQLException{
        String tSQLSelectPPCommand = "Select count(OID) From " +
                                     ProcessPackage.PROCESS_PACKAGE_TABLE_NAME + " Where OID = ? ";
        PreparedStatement tSelectedPPStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectPPCommand);
        try{
            if (tSelectedPPStat == null){
                tSelectedPPStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectPPCommand);
                this.sourcePreparedStatements.put(tSQLSelectPPCommand, tSelectedPPStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessPackage.PROCESS_PACKAGE_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        String tSQLSelectCPPCommand = "Select count(OID) From " +
                                     CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME + " Where OID = ? ";
        PreparedStatement tSelectedCPPStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCPPCommand);
        try{
            if (tSelectedCPPStat == null){
                tSelectedCPPStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCPPCommand);
                this.sourcePreparedStatements.put(tSQLSelectCPPCommand, tSelectedCPPStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        Set tProcessPackageOIDs = this.transProcessPackage.keySet();
        System.out.println("Trans " + tProcessPackageOIDs.size() + " ProcessPackage(s).");
        Object[] tArrayOfProcessPackageOIDs = tProcessPackageOIDs.toArray();
        String tProcessPackageOID = null;
        ResultSet tRS = null;
        ProcessPackage tProcessPackage = null;
        for(int i=0; i < tArrayOfProcessPackageOIDs.length; i++){
            tProcessPackageOID = (String)tArrayOfProcessPackageOIDs[i];
            tSelectedPPStat.setString(1, tProcessPackageOID);
            tRS = tSelectedPPStat.executeQuery();
            tRS.next();
            if (tRS.getInt(1) == 1){
                //這是ProcessPackage
                System.out.println("ProcessPackageOID : '" +
                                   tProcessPackageOID + "' is not Custom.");
                this.transProcessPackage.put(tProcessPackageOID,
                        new ProcessPackage(tProcessPackageOID));
                tRS.close();
                continue;
            } else {
                tRS.close();
            }
            tSelectedCPPStat.setString(1, tProcessPackageOID);
            tRS = tSelectedCPPStat.executeQuery();
            tRS.next();
            if (tRS.getInt(1) == 1){
                //這是ProcessPackage
                System.out.println("ProcessPackageOID : '" +
                                   tProcessPackageOID + "' is Custom.");
                this.transProcessPackage.put(tProcessPackageOID,
                        new CustomProcessPackage(tProcessPackageOID));
                tRS.close();
                continue;
            } else {
                tRS.close();
                throw new IllegalArgumentException(
                    "Can not find ProcessPackage or CustomProcessPackage by OID." +
                    "OID = '" + tProcessPackageOID + "'");
            }
        }

    }
    public void transProcessInstance() throws SQLException{
        int tTotal = this.deleteProcessInstance.size();
        Iterator tIterProcessInstanceOIDs = this.deleteProcessInstance.keySet().iterator();
        String tOID = null;
        int tCount = 0;
        StringBuffer tStringBuffer = new StringBuffer();
        long tBeginTime = 0;
        long tEndTime = 0;
        try{
            while (tIterProcessInstanceOIDs.hasNext()){
                tCount ++;
                tOID = (String)tIterProcessInstanceOIDs.next();
                tStringBuffer = new StringBuffer();
                tStringBuffer.append("Begin archiveProcessInstance,OID:").append(tOID).append(
                        "(").append(tCount).append("/").append(tTotal).append(")");
                System.out.println(tStringBuffer);
                tBeginTime = System.currentTimeMillis();
                this.archiveProcessInstance(tOID);
                tEndTime = System.currentTimeMillis();
                tStringBuffer = new StringBuffer();
                tStringBuffer.append("End archiveProcessInstance,OID:").append(tOID).append(
                        ". Spent ").append(((tEndTime-tBeginTime)/1000)).append(" seconds.(").append(tCount).append("/").append(tTotal).append(")");
                System.out.println(tStringBuffer);
            }
            //釋放所有的PreparedStatement, 會在pJDBCHelper releaseSource中被release掉
//            Iterator tIterInserState = this.insertPreparedStatements.values().iterator();
//            while (tIterInserState.hasNext()){
//                ((PreparedStatement)tIterInserState.next()).close();
//            }
        } catch (SQLException e){
            throw e;
        }
    }
    public void transProcessPackage() throws SQLException{
        Iterator tIterProcessPackageOIDs = this.transProcessPackage.keySet().iterator();
        String tOID = null;
        try{
            while (tIterProcessPackageOIDs.hasNext()){
                tOID = (String)tIterProcessPackageOIDs.next();
                this.archiveProcessPackage(tOID);

            }
            //釋放所有的PreparedStatement, 會在pJDBCHelper releaseSource中被release掉
//            Iterator tIterInserState = this.insertPreparedStatements.values().iterator();
//            while (tIterInserState.hasNext()){
//                ((PreparedStatement)tIterInserState.next()).close();
//            }
        } catch (SQLException e){
            throw e;
        }
    }
    public void archiveProcessInstance(String pOID) throws SQLException{
        try{
            try{
                this.jdbcHelper.getSourceConnection().setAutoCommit(false);
            } catch (SQLException e){
                System.out.println("Source connection set AutoCommit false error.");
            }
            try{
                this.jdbcHelper.getDesConnection().setAutoCommit(false);
            } catch (SQLException e){
                System.out.println("Des connection set AutoCommit false error.");
            }
            if (this.deleteProcessInstance.get(pOID) == null){
                ProcessInstance tProcessInstance = new ProcessInstance(pOID);
                this.deleteProcessInstance.put(pOID, tProcessInstance);
            }
            this.archiveProcessInstanceTable(pOID);
            this.archiveProcessContextTable(pOID);
            this.archiveBlockActInstanceTable(pOID);
            this.archiveParticipantActivityInstanceTable(pOID);
            this.archiveSubflowActivityInstanceTable(pOID);
            this.archiveWorkItemTable(pOID);
            this.archiveWorkAssignmentTable(pOID);
            this.archiveWorkStepTable(pOID);
            this.archiveActivityNotificationTable(pOID);
            this.archiveLocalRelevantDataTable(pOID);
            this.archiveByValueParameterTable(pOID);
            this.archiveChangeActivityStateAuditTable(pOID);
            this.archiveChangeProcessStateAuditTable(pOID);
            this.archiveProcessNotificationTable(pOID);
            this.archiveReassignWorkItemAuditDataTable(pOID);
            this.archiveChangeWorkItemStateAuditTable(pOID);
            this.archiveFormInstanceTable(pOID);
            this.archiveAttachmentInstanceTable(pOID);
            this.archiveStringWorkflowRuntimeValueTable(pOID);
            ProcessInstance tProcessInstance = (ProcessInstance)this.deleteProcessInstance.get(pOID);
            Statement tDeleteST = this.jdbcHelper.createSourceStatement();
            Iterator tIterDeleteCommands = tProcessInstance.generateDeleteCommands().iterator();
            String tCommand = null;
            while (tIterDeleteCommands.hasNext()){
                tCommand = (String)tIterDeleteCommands.next();
                tDeleteST.addBatch(tCommand);
            }
            tDeleteST.executeBatch();
        } catch (SQLException e){
            throw e;
        }

    }
    public void archiveProcessPackage(String pOID) throws SQLException{
        try{
            try{
                this.jdbcHelper.getSourceConnection().setAutoCommit(false);
            } catch (SQLException e){
                System.out.println("Source connection set AutoCommit false error.");
            }
            try{
                this.jdbcHelper.getDesConnection().setAutoCommit(false);
            } catch (SQLException e){
                System.out.println("Des connection set AutoCommit false error.");
            }
            ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pOID);
            if (tProcessPackage == null){
                throw new IllegalArgumentException(
                    "Can not find ProcessPackage object by OID. OID:" + pOID);
            }
            if (tProcessPackage instanceof CustomProcessPackage){
                this.archiveCustomProcessPackageTable(pOID);
            } else if(tProcessPackage instanceof ProcessPackage){
                this.archiveProcessPackageTable(pOID);
            } else {
                throw new IllegalArgumentException(
                    "Can not archive " + tProcessPackage.getClass().getName() + " object.");
            }
            this.archivePackage_DefTable(pOID);
            this.archiveProcessDefinition(pOID);
            this.archiveActivitySetDefinition(pOID);
            this.archiveActivityDefinition(pOID);
            this.archiveProcessViewInformationTable(pOID);
            this.archiveBoundViewInformationTable(pOID);
            this.archiveDecisionRuleListTable(pOID);
            this.archiveFormFieldAccessDefTable(pOID);
            this.archiveRouteTable(pOID);
            this.archiveBlockActivityTable(pOID);
            this.archiveImplementationTable(pOID);
            this.archiveDecisionRuleTable(pOID);
            this.archiveToolTable(pOID);
            this.archiveFormOperationDefTable(pOID);
            this.archiveRelevantDataDefTable(pOID);
            this.archiveContainer_ApplicationDefTable(pOID);
            this.archiveFormalParameterTable(pOID);
            this.archiveBasicTypeTable(pOID);
            this.archiveAttachmentTypeTable(pOID);
            this.archiveFormTypeTable(pOID);
            this.archiveTransitionDefinitionTable(pOID);
            this.archiveConditionDefinitionTable(pOID);
            this.archiveTransitionRestrictionTable(pOID);
            this.archiveDecisionConditionTable(pOID);
            this.archiveDecisionLevelTable(pOID);
            this.archiveNosTable(pOID);
            this.archiveSubFlowTable(pOID);
            this.archiveParticipantDefinitionTable(pOID);
            this.archiveActualParameterTable(pOID);
            this.archiveTransitionReferenceTable(pOID);
            this.archiveWebServicesApplicationTable(pOID);
            this.archiveWebApplicationTable(pOID);
            this.archiveSessionBeanApplicationTable(pOID);
            this.archiveScriptingApplicationTable(pOID);
            this.archiveMailApplicationTable(pOID);
        } catch (SQLException e){
            throw e;
        }
    }
    private void archiveStringWorkflowRuntimeValueTable(String pProcessInstanceOID)throws SQLException{
        Collection tRuntimevalueOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getRuntimeValueOIDs();
        System.out.println("Begin archiveStringWorkflowRuntimeValueTable of ProcessInsatanceOID:" + pProcessInstanceOID);
        String tTableName = ProcessInstance.STRING_RUNTIME_VALUE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterRuntimevalueOIDs = tRuntimevalueOIDs.iterator();
        String tRuntimevalueOID = null;
        while (tIterRuntimevalueOIDs.hasNext()){
            tRuntimevalueOID = (String)tIterRuntimevalueOIDs.next();
            try{
                tSelectedStat.setString(1, tRuntimevalueOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
        System.out.println("End archiveStringWorkflowRuntimeValueTable of ProcessInsatanceOID:" + pProcessInstanceOID);
    }
    private void archiveAttachmentInstanceTable(String pProcessInstanceOID)throws SQLException{
        Collection tRuntimevalueOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getRuntimeValueOIDs();

        String tTableName = ProcessInstance.ATTACH_INST_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterRuntimevalueOIDs = tRuntimevalueOIDs.iterator();
        String tRuntimevalueOID = null;
        while (tIterRuntimevalueOIDs.hasNext()){
            tRuntimevalueOID = (String)tIterRuntimevalueOIDs.next();
            try{
                tSelectedStat.setString(1, tRuntimevalueOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveFormInstanceTable(String pProcessInstanceOID)throws SQLException{
        Collection tRuntimevalueOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getRuntimeValueOIDs();

        String tTableName = ProcessInstance.FROM_INSTANCE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterRuntimevalueOIDs = tRuntimevalueOIDs.iterator();
        String tRuntimevalueOID = null;
        while (tIterRuntimevalueOIDs.hasNext()){
            tRuntimevalueOID = (String)tIterRuntimevalueOIDs.next();
            try{
                tSelectedStat.setString(1, tRuntimevalueOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveChangeWorkItemStateAuditTable(String pProcessInstanceOID)throws SQLException{
        Collection tWorkItemOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getWorkItemOIDs();
        String tTableName = ProcessInstance.CHANGE_WORKITEM_STATE_AUDIT_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where sourceOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterWorkItemOIDs = tWorkItemOIDs.iterator();
        String tWorkItemOID = null;
        while (tIterWorkItemOIDs.hasNext()){
            tWorkItemOID = (String)tIterWorkItemOIDs.next();
            try{
                tSelectedStat.setString(1, tWorkItemOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveReassignWorkItemAuditDataTable(String pProcessInstanceOID)throws SQLException{
        Collection tWorkItemOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getWorkItemOIDs();

        String tTableName = ProcessInstance.REASSIGN_WORKITEM_DATE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where sourceOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterWorkItemOIDs = tWorkItemOIDs.iterator();
        String tWorkItemOID = null;
        while (tIterWorkItemOIDs.hasNext()){
            tWorkItemOID = (String)tIterWorkItemOIDs.next();
            try{
                tSelectedStat.setString(1, tWorkItemOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }

    private void archiveProcessNotificationTable(String pProcessInstanceOID)throws SQLException{
        String tTableName = ProcessInstance.PROCESS_NOTICE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where processInstanceOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, pProcessInstanceOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveChangeProcessStateAuditTable(String pProcessInstanceOID)throws SQLException{
        String tTableName = ProcessInstance.CHANGE_PROCESS_STATE_AUDIT_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where sourceOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, pProcessInstanceOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveChangeActivityStateAuditTable(String pProcessInstanceOID)throws SQLException{
        Set tActInstOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getActInstOIDs();

        String tTableName = ProcessInstance.CHANGE_ACT_STATE_AUDIT_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where sourceOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterActInstOIDs = tActInstOIDs.iterator();
        String tActInstOID = null;
        while (tIterActInstOIDs.hasNext()){
            tActInstOID = (String)tIterActInstOIDs.next();
            try{
                tSelectedStat.setString(1, tActInstOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveByValueParameterTable(String pProcessInstanceOID)throws SQLException{
        String tContextOID = ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getContextOID();
        String tTableName = ProcessInstance.BY_VALUE_PAR_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, tContextOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveLocalRelevantDataTable(String pProcessInstanceOID)throws SQLException{
        String tContextOID = ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getContextOID();
        String tTableName = ProcessInstance.LOCAL_RELEVANT_DATA_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, tContextOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveActivityNotificationTable(String pProcessInstanceOID)throws SQLException{
        Set tActInstOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getActInstOIDs();
        String tTableName = ProcessInstance.ACT_NOTICE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where activityInstanceOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterActInstOIDs = tActInstOIDs.iterator();
        String tActInstOID = null;
        while (tIterActInstOIDs.hasNext()){
            tActInstOID = (String)tIterActInstOIDs.next();
            try{
                tSelectedStat.setString(1, tActInstOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveWorkStepTable(String pProcessInstanceOID)throws SQLException{
        Collection tWorkItemOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getWorkItemOIDs();
        String tTableName = ProcessInstance.WORKSTEP_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterWorkItemOIDs = tWorkItemOIDs.iterator();
        String tWorkItemOID = null;
        while (tIterWorkItemOIDs.hasNext()){
            tWorkItemOID = (String)tIterWorkItemOIDs.next();
            try{
                tSelectedStat.setString(1, tWorkItemOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveWorkAssignmentTable(String pProcessInstanceOID)throws SQLException{
        Collection tWorkItemOIDs =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getWorkItemOIDs();
        String tTableName = ProcessInstance.WORKASSIGNMENT_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where workItemOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterWorkItemOIDs = tWorkItemOIDs.iterator();
        String tWorkItemOID = null;
        while (tIterWorkItemOIDs.hasNext()){
            tWorkItemOID = (String)tIterWorkItemOIDs.next();
            try{
                tSelectedStat.setString(1, tWorkItemOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveWorkItemTable(String pProcessInstanceOID)throws SQLException{
        String tContextOID = ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getContextOID();
        String tTableName = ProcessInstance.WORKITEM_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where contextOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, tContextOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveSubflowActivityInstanceTable(String pProcessInstanceOID)throws SQLException{
        String tContextOID = ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getContextOID();
        String tTableName = ProcessInstance.SUBFLOW_INST_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where contextOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, tContextOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveParticipantActivityInstanceTable(String pProcessInstanceOID)throws SQLException{
        String tContextOID = ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getContextOID();
        String tTableName = ProcessInstance.PAR_ACT_INST_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where contextOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, tContextOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveBlockActInstanceTable(String pProcessInstanceOID)throws SQLException{
        String tContextOID = ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getContextOID();
        String tTableName = ProcessInstance.BLOCK_ACT_INST_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where contextOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, tContextOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveProcessContextTable(String pProcessInstanceOID)throws SQLException{
        String tContextOID =
                ((ProcessInstance)this.deleteProcessInstance.get(pProcessInstanceOID)).getContextOID();
        String tTableName = ProcessInstance.PROCESS_CONTEXT_TABLE_NAME;
//        String tSQLSelectCommand = "Select * from " + tTableName +
//                                   " where OID = '" + tContextOID + "'";
        String tSQLSelectCommand = "Select * from " + tTableName +
                                   " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }

        ResultSet tRS = null;
        try{
            tSelectedStat.setString(1, tContextOID);
            tRS = tSelectedStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tRS, tTableName);
            this.jdbcHelper.releaseResultSet(tRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveProcessInstanceTable(String pProcessInstanceOID)throws SQLException{
        String tSQLSelectProcessInstance = "Select * from " + ProcessInstance.PROCESS_INST_TABLE_NAME +
                " where OID = ? ";
        PreparedStatement tSelectProcessInstanceStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectProcessInstance);
        try{
            if (tSelectProcessInstanceStat == null){
                tSelectProcessInstanceStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectProcessInstance);
                this.sourcePreparedStatements.put(tSQLSelectProcessInstance, tSelectProcessInstanceStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + ProcessInstance.PROCESS_INST_TABLE_NAME +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tProcessInstanceRS = null;
        try{
            tSelectProcessInstanceStat.setString(1, pProcessInstanceOID);
            tProcessInstanceRS = tSelectProcessInstanceStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + ProcessInstance.PROCESS_INST_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessInstanceOID, tProcessInstanceRS, ProcessInstance.PROCESS_INST_TABLE_NAME);
            this.jdbcHelper.releaseResultSet(tProcessInstanceRS);
        } catch (SQLException e){
            System.out.println("Inserte " + ProcessInstance.PROCESS_INST_TABLE_NAME +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void checkThenCopyData(String pTargetOID,
                                   ResultSet pResultSet,
                                   String pTableName) throws SQLException {
        ResultSetMetaData tMeta = (ResultSetMetaData)this.resultSetMetaDatas.get(pTableName);
        if (tMeta == null){
            tMeta = pResultSet.getMetaData();
            this.resultSetMetaDatas.put(pTableName, tMeta);
        }
        PreparedStatement tInsertDes = (PreparedStatement)this.desPreparedStatements.get(pTableName);
        if (tInsertDes == null){
            StringBuffer tInsertString = this.generateInsertString(tMeta, pTableName);
            System.out.println(tInsertString);
            tInsertDes = this.jdbcHelper.createDesPrepareStatement(tInsertString.toString());
            this.desPreparedStatements.put(pTableName, tInsertDes);
        }
        String tCheckExistCmd = "Select count(OID) From " + pTableName + " Where OID = ? ";
        PreparedStatement tCheckDesPS = (PreparedStatement)this.desPreparedStatements.get(tCheckExistCmd);
        if (tCheckDesPS == null){
            tCheckDesPS = this.jdbcHelper.createDesPrepareStatement(tCheckExistCmd);
            this.desPreparedStatements.put(tCheckExistCmd, tCheckDesPS);
        }
        ResultSet tCheckExistRS = null;
        String tColumnName = null;
        String tOID = null;

        while (pResultSet.next()) {
            //先檢查此筆有沒在Dest內
            tOID = pResultSet.getString("OID");
            if (pTableName.equals(ProcessInstance.PROCESS_INST_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.setContextOID(pResultSet.getString("contextOID"));
            } else if (pTableName.equals(ProcessInstance.BLOCK_ACT_INST_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addBlockActivityInstanceOID(tOID);
            } else if (pTableName.equals(ProcessInstance.PAR_ACT_INST_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addParticipantActivityInstanceOID(tOID);
            } else if (pTableName.equals(ProcessInstance.SUBFLOW_INST_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addSubFlowActivityInstanceOID(tOID);
            } else if (pTableName.equals(ProcessInstance.WORKITEM_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addWorkItemOID(tOID);
            } else if (pTableName.equals(ProcessInstance.WORKASSIGNMENT_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addWorkAssignmentOID(tOID);
            } else if (pTableName.equals(ProcessInstance.WORKSTEP_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addWorkStepOID(tOID);
            } else if (pTableName.equals(ProcessInstance.ACT_NOTICE_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addActivityNotificationOID(tOID);
            } else if (pTableName.equals(ProcessInstance.LOCAL_RELEVANT_DATA_TABLE_NAME)
                           || pTableName.equals(ProcessInstance.BY_VALUE_PAR_TABLE_NAME)
                           || pTableName.equals(ProcessInstance.GLOBAL_RELEVANT_DATA_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                if (pTableName.equals(ProcessInstance.LOCAL_RELEVANT_DATA_TABLE_NAME))
                        tProcessInstance.addLocalRelevantDataOID(tOID);
                if (pTableName.equals(ProcessInstance.BY_VALUE_PAR_TABLE_NAME))
                        tProcessInstance.addByValueParameterOID(tOID);
                if (pTableName.equals(ProcessInstance.GLOBAL_RELEVANT_DATA_TABLE_NAME))
                        tProcessInstance.addGlobalRelevantDataOID(tOID);
                if (pResultSet.getString("valueOID") != null && !"".equals(pResultSet.getString("valueOID")))
                        tProcessInstance.addRuntimeValueOID(pResultSet.getString("valueOID"));
            } else if (pTableName.equals(ProcessInstance.CHANGE_ACT_STATE_AUDIT_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                    tProcessInstance.addChangeActivityStateAuditOID(tOID);
            } else if (pTableName.equals(ProcessInstance.CHANGE_PROCESS_STATE_AUDIT_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addChangeProcessStateAuditOID(tOID);
            } else if (pTableName.equals(ProcessInstance.CHANGE_WORKITEM_STATE_AUDIT_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addChangeWorkItemStateAuditOID(tOID);
            } else if (pTableName.equals(ProcessInstance.PROCESS_NOTICE_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addProcessNotificationOID(tOID);
            } else if (pTableName.equals(ProcessInstance.REASSIGN_WORKITEM_DATE_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addReassignWorkItemAuditDataOID(tOID);
            } else if (pTableName.equals(ProcessInstance.FROM_INSTANCE_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addFormInstanceOID(tOID);
            } else if (pTableName.equals(ProcessInstance.ATTACH_INST_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addAttachmentInstanceOID(tOID);
            } else if (pTableName.equals(ProcessInstance.STRING_RUNTIME_VALUE_TABLE_NAME)){
                ProcessInstance tProcessInstance =
                        (ProcessInstance)this.deleteProcessInstance.get(pTargetOID);
                tProcessInstance.addStringWorkflowRuntimeValueOID(tOID);
            } else if (pTableName.equals(ProcessInstance.PROCESS_CONTEXT_TABLE_NAME)){
                String tProcessPackageOID = pResultSet.getString("processPackageOID");
                this.transProcessPackage.put(tProcessPackageOID, null);
            } else if (pTableName.equals(ProcessPackage.PROCESS_DEFINITION_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addProcessDefOID(tOID);
                tProcessPackage.addProcessViewInformationOID(
                        pResultSet.getString("processViewInformationOID"));
                tProcessPackage.addActivityTypeOID(pResultSet.getString("actionAfterAbortedOID"));
                tProcessPackage.addActivityTypeOID(pResultSet.getString("actionAfterTerminatedOID"));
                tProcessPackage.addActivityTypeOID(pResultSet.getString("actionAfterCompletedOID"));
            } else if (pTableName.equals(ProcessPackage.ACTIVITY_SET_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addActivitySetDefinitionOID(tOID);
            } else if (pTableName.equals(ProcessPackage.ACTIVITY_DEF_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addActivityDefinitionOID(tOID);
                tProcessPackage.addActivityTypeOID(pResultSet.getString("activityTypeOID"));
                tProcessPackage.addBoundViewInformationOID(pResultSet.getString("boundViewInformationOID"));
                tProcessPackage.addDecisionRuleListOID(pResultSet.getString("decisionRuleListOID"));
                tProcessPackage.addFormFieldAccessDefinitionOID(pResultSet.getString("formFieldAccessDefinitionOID"));
            } else if (pTableName.equals(ProcessPackage.PROCESS_BOUND_VIEW_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addBoundViewInformationOID(pResultSet.getString("startActivityBoundInfoOID"));
                tProcessPackage.addBoundViewInformationOID(pResultSet.getString("endActivityBoundInfoOID"));
            } else if (pTableName.equals(ProcessPackage.DECISION_RULE_LIST_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.getDecisionConditionOIDs().addAll(this.getDecisionConditionOIDs(tOID));
                tProcessPackage.getDecisionLevelOIDs().addAll(this.getDecisionLevelOIDs(tOID));
            } else if (pTableName.equals(ProcessPackage.BLOCK_ACT_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addBlockActivityOID(tOID);
            } else if (pTableName.equals(ProcessPackage.IMPLEMENTATION_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addImplementationOID(tOID);
            } else if (pTableName.equals(ProcessPackage.ROUTE_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addRouteOID(tOID);
            } else if (pTableName.equals(ProcessPackage.TOOL_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addToolOID(tOID);
                tProcessPackage.addFormOperationDefinitionOID(
                            pResultSet.getString("applicationModeOID"));
            } else if (pTableName.equals(ProcessPackage.RELEVANT_DATA_DEF_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addRelevantDataDefinitionOID(tOID);
                tProcessPackage.addDataTypeOID(pResultSet.getString("dataTypeOID"));
            } else if (pTableName.equals(ProcessPackage.FORMALPARAMETER_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addFormalParameterOID(tOID);
                tProcessPackage.addDataTypeOID(pResultSet.getString("dataTypeOID"));
            } else if (pTableName.equals(ProcessPackage.TRANSITION_DEF_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addTransitionDefinitionOID(tOID);
                tProcessPackage.addConditionDefinitionOID(pResultSet.getString("conditionOID"));
            } else if (pTableName.equals(ProcessPackage.TRANSITION_RESTRICTION_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pTargetOID);
                tProcessPackage.addTransitionRestrictionOID(tOID);
            }
            tCheckDesPS.setString(1, tOID);
            tCheckExistRS = tCheckDesPS.executeQuery();
            tCheckExistRS.next();
            List tColumnsName = (List)this.columnsNames.get(pTableName);
            if (tCheckExistRS.getInt(1) == 0){
                for (int i = 0; i < tColumnsName.size(); i++)
                {
                    tColumnName = (String)tColumnsName.get(i);
                    setQueryParam(pResultSet, tMeta, tInsertDes, tColumnName, i+1);
                }
                tInsertDes.execute();
            }
            tCheckExistRS.close();
        }
    }
    private void checkThenCopyBridgeData(String pProcessPackageOID,
            ResultSet pResultSet, String pTableName) throws SQLException {
        ResultSetMetaData tMeta = (ResultSetMetaData)this.resultSetMetaDatas.get(pTableName);
        if (tMeta == null){
            tMeta = pResultSet.getMetaData();
            this.resultSetMetaDatas.put(pTableName, tMeta);
        }
        PreparedStatement tInsertDes = (PreparedStatement)this.desPreparedStatements.get(pTableName);
        if (tInsertDes == null){
            StringBuffer tInsertString = this.generateInsertString(tMeta, pTableName);
            System.out.println(tInsertString);
            tInsertDes = this.jdbcHelper.createDesPrepareStatement(tInsertString.toString());
            this.desPreparedStatements.put(pTableName, tInsertDes);
        }
        StringBuffer tCheckExist = new StringBuffer();
        tCheckExist.append("Select count(*) From ").append(pTableName).append(" Where ");
        List tColumnsName = (List)this.columnsNames.get(pTableName);
        String tColumnName = null;
        for (int i = 0; i < tColumnsName.size(); i++)
        {
            tColumnName =  (String)tColumnsName.get(i);
            tCheckExist.append(tColumnName).append(" = ? ");
            if (i !=  (tColumnsName.size()-1))
            {
                tCheckExist.append(" and ");
            }
        }
        System.out.println("CheckExist command = " + tCheckExist.toString());
        PreparedStatement tCheckDesPS = (PreparedStatement)this.desPreparedStatements.get(tCheckExist.toString());
        if (tCheckDesPS == null){
            tCheckDesPS = this.jdbcHelper.createDesPrepareStatement(tCheckExist.toString());
            this.desPreparedStatements.put(tCheckExist.toString(), tCheckDesPS);
        }
        ResultSet tCheckExistRS = null;
        while (pResultSet.next()) {
            //先檢查此筆有沒在Dest內
            if (pTableName.equals(ProcessPackage.PACKAGE_B_DEFINITION_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pProcessPackageOID);
                //取得流程定義的OID
                tProcessPackage.addProcessDefOID(pResultSet.getString("ProcessDefinitionOID"));
            } else if (pTableName.equals(ProcessPackage.APPLICATION_CONTAINER_B_APPLICATION_TABLE_NAME)){
                ProcessPackage tProcessPackage = (ProcessPackage)this.transProcessPackage.get(pProcessPackageOID);
                tProcessPackage.addApplicationDefinitionOID(
                        pResultSet.getString("ApplicationDefinitionOID"));
            }
            for (int i = 0; i < tColumnsName.size(); i++)
            {
                tColumnName =  (String)tColumnsName.get(i);
                setQueryParam(pResultSet, tMeta, tCheckDesPS, tColumnName, i+1);
            }
            tCheckExistRS = tCheckDesPS.executeQuery();
            tCheckExistRS.next();
            if (tCheckExistRS.getInt(1) == 0){
                for (int i = 0; i < tColumnsName.size(); i++)
                {
                    tColumnName =  (String)tColumnsName.get(i);
                    setQueryParam(pResultSet, tMeta, tInsertDes, tColumnName, i+1);
                }
                tInsertDes.execute();
            }
            tCheckExistRS.close();
        }
    }
    private StringBuffer generateInsertString(ResultSetMetaData pMeta, String pTableName) throws SQLException{
        String tQuerySchemaString = "select TOP 1 * from " + pTableName;
        Statement tDesSchema = this.jdbcHelper.createDesPrepareStatement(tQuerySchemaString);
        ResultSet tDesSchemaRS = tDesSchema.executeQuery(tQuerySchemaString);
        StringBuffer tQueryString = new StringBuffer();
        tQueryString.append("insert into ").append(pTableName).append("(");
        String tColumnName = null;
        List tColumnsName = new ArrayList();
        for (int i = 1; i <= pMeta.getColumnCount(); i++){
            tColumnName = pMeta.getColumnName(i);
            tQueryString.append(tColumnName);
            tColumnsName.add(i-1, tColumnName);
            if (i != pMeta.getColumnCount())
            {
                tQueryString.append(",");
            }
        }
        this.columnsNames.put(pTableName, tColumnsName);
        tQueryString.append(") values (");
        for (int i = 1; i <= pMeta.getColumnCount(); i++)
        {
            tQueryString.append("?");
            if (i != pMeta.getColumnCount())
            {
                tQueryString.append(", ");
            }
        }
        tQueryString.append(")");
        return tQueryString;
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
        } else if (tColumnType == Types.TIMESTAMP) {
            pQuery.setTimestamp(pIndex, pResultSet.getTimestamp(tColumnIndex));
        } else if ((tColumnType == Types.CHAR) || (tColumnType == Types.VARCHAR)
                           || (tColumnType == Types.LONGVARCHAR)){
            pQuery.setString(pIndex, pResultSet.getString(tColumnIndex));
        } else if ((tColumnType == Types.BINARY)
                               || (tColumnType == Types.VARBINARY)
                               || (tColumnType == Types.LONGVARBINARY)){
            pQuery.setBytes(pIndex, pResultSet.getBytes(tColumnIndex));
        } else if (tColumnType == -9 || tColumnType == -10){
                        //nvarchar, ntext
            pQuery.setString(pIndex, pResultSet.getString(tColumnIndex));
        } else {
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
    /**************************************************************備份定義*********************/
    private void archiveMailApplicationTable(String pProcessPackageOID) throws SQLException{
        Collection tApplicationOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getApplicationDefinitionOIDs();
        String tTableName = ProcessPackage.MAIL_APP_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterApplicationOIDs = tApplicationOIDs.iterator();
        String tApplicationOID = null;
        while (tIterApplicationOIDs.hasNext()){
            tApplicationOID = (String)tIterApplicationOIDs.next();
            try{
                tSelectedStat.setString(1, tApplicationOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveScriptingApplicationTable(String pProcessPackageOID) throws SQLException{
        Collection tApplicationOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getApplicationDefinitionOIDs();
        String tTableName = ProcessPackage.SCRIPTING_APP_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterApplicationOIDs = tApplicationOIDs.iterator();
        String tApplicationOID = null;
        while (tIterApplicationOIDs.hasNext()){
            tApplicationOID = (String)tIterApplicationOIDs.next();
            try{
                tSelectedStat.setString(1, tApplicationOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveSessionBeanApplicationTable(String pProcessPackageOID) throws SQLException{
        Collection tApplicationOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getApplicationDefinitionOIDs();
        String tTableName = ProcessPackage.SESSIONBEAN_APP_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterApplicationOIDs = tApplicationOIDs.iterator();
        String tApplicationOID = null;
        while (tIterApplicationOIDs.hasNext()){
            tApplicationOID = (String)tIterApplicationOIDs.next();
            try{
                tSelectedStat.setString(1, tApplicationOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveWebApplicationTable(String pProcessPackageOID) throws SQLException{
        Collection tApplicationOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getApplicationDefinitionOIDs();
        String tTableName = ProcessPackage.WEB_APP_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterApplicationOIDs = tApplicationOIDs.iterator();
        String tApplicationOID = null;
        while (tIterApplicationOIDs.hasNext()){
            tApplicationOID = (String)tIterApplicationOIDs.next();
            try{
                tSelectedStat.setString(1, tApplicationOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveWebServicesApplicationTable(String pProcessPackageOID) throws SQLException{
        Collection tApplicationOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getApplicationDefinitionOIDs();
        String tTableName = ProcessPackage.WEBSERVICES_APP_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterApplicationOIDs = tApplicationOIDs.iterator();
        String tApplicationOID = null;
        while (tIterApplicationOIDs.hasNext()){
            tApplicationOID = (String)tIterApplicationOIDs.next();
            try{
                tSelectedStat.setString(1, tApplicationOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveTransitionReferenceTable(String pProcessPackageOID) throws SQLException{
        Collection tTransitionReferenceContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getTransitionRestrictionOIDs();
        String tTableName = ProcessPackage.TRANSITION_REFERENCE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterTransitionReferenceContainerOIDs = tTransitionReferenceContainerOIDs.iterator();
        String tTransitionReferenceContainerOID = null;
        while (tIterTransitionReferenceContainerOIDs.hasNext()){
            tTransitionReferenceContainerOID = (String)tIterTransitionReferenceContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tTransitionReferenceContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveActualParameterTable(String pProcessPackageOID) throws SQLException{
        Collection tActualParameterContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getToolOIDs();
        String tTableName = ProcessPackage.ACTUAL_PARAMETER_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterActualParameterContainerOIDs = tActualParameterContainerOIDs.iterator();
        String tActualParameterContainerOID = null;
        while (tIterActualParameterContainerOIDs.hasNext()){
            tActualParameterContainerOID = (String)tIterActualParameterContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tActualParameterContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveParticipantDefinitionTable(String pProcessPackageOID) throws SQLException{
        Collection tParticipantDefContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getPackageAndDefOIDs();
        String tTableName = ProcessPackage.PARTICIPANT_DEF_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterParticipantDefContainerOIDs = tParticipantDefContainerOIDs.iterator();
        String tParticipantDefContainerOID = null;
        while (tIterParticipantDefContainerOIDs.hasNext()){
            tParticipantDefContainerOID = (String)tIterParticipantDefContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tParticipantDefContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveSubFlowTable(String pProcessPackageOID) throws SQLException{
        Collection tSubFlowContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getImplementationOIDs();
        String tTableName = ProcessPackage.SUBFLOW_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterSubFlowContainerOIDs = tSubFlowContainerOIDs.iterator();
        String tSubFlowContainerOID = null;
        while (tIterSubFlowContainerOIDs.hasNext()){
            tSubFlowContainerOID = (String)tIterSubFlowContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tSubFlowContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveNosTable(String pProcessPackageOID) throws SQLException{
        Collection tNosContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getImplementationOIDs();
        String tTableName = ProcessPackage.NO_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterNosContainerOIDs = tNosContainerOIDs.iterator();
        String tNosContainerOID = null;
        while (tIterNosContainerOIDs.hasNext()){
            tNosContainerOID = (String)tIterNosContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tNosContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveDecisionLevelTable(String pProcessPackageOID) throws SQLException{
        Collection tDecisionLevelOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getDecisionLevelOIDs();
        String tTableName = ProcessPackage.DECISION_LEVEL_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterDecisionLevelOIDs = tDecisionLevelOIDs.iterator();
        String tDecisionLevelOID = null;
        while (tIterDecisionLevelOIDs.hasNext()){
            tDecisionLevelOID = (String)tIterDecisionLevelOIDs.next();
            try{
                tSelectedStat.setString(1, tDecisionLevelOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveDecisionConditionTable(String pProcessPackageOID) throws SQLException{
        Collection tDecisionConditionOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getDecisionConditionOIDs();
        String tTableName = ProcessPackage.DECISION_COND_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterDecisionConditionOIDs = tDecisionConditionOIDs.iterator();
        String tDecisionConditionOID = null;
        while (tIterDecisionConditionOIDs.hasNext()){
            tDecisionConditionOID = (String)tIterDecisionConditionOIDs.next();
            try{
                tSelectedStat.setString(1, tDecisionConditionOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveTransitionRestrictionTable(String pProcessPackageOID) throws SQLException{
        Collection tTransitionRestrictionContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getActivityDefinitionOIDs();
        String tTableName = ProcessPackage.TRANSITION_RESTRICTION_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterTransitionRestrictionContainerOIDs = tTransitionRestrictionContainerOIDs.iterator();
        String tTransitionRestrictionContainerOID = null;
        while (tIterTransitionRestrictionContainerOIDs.hasNext()){
            tTransitionRestrictionContainerOID = (String)tIterTransitionRestrictionContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tTransitionRestrictionContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveConditionDefinitionTable(String pProcessPackageOID) throws SQLException{
        Collection tConditionDefinitionOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getConditionDefinitionOIDs();
        String tTableName = ProcessPackage.CONDITION_DEF_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterConditionDefinitionOIDs = tConditionDefinitionOIDs.iterator();
        String tConditionDefinitionOID = null;
        while (tIterConditionDefinitionOIDs.hasNext()){
            tConditionDefinitionOID = (String)tIterConditionDefinitionOIDs.next();
            try{
                tSelectedStat.setString(1, tConditionDefinitionOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveTransitionDefinitionTable(String pProcessPackageOID) throws SQLException{
        Collection tTransitionDefContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getActDefContainerOIDs();
        String tTableName = ProcessPackage.TRANSITION_DEF_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterTransitionDefContainerOIDs = tTransitionDefContainerOIDs.iterator();
        String tTransitionDefContainerOID = null;
        while (tIterTransitionDefContainerOIDs.hasNext()){
            tTransitionDefContainerOID = (String)tIterTransitionDefContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tTransitionDefContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveBasicTypeTable(String pProcessPackageOID) throws SQLException{
        Collection tDataTypeOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getDataTypeOIDs();
        String tTableName = ProcessPackage.BASIC_TYPE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterDataTypeOIDs = tDataTypeOIDs.iterator();
        String tDataTypeOID = null;
        while (tIterDataTypeOIDs.hasNext()){
            tDataTypeOID = (String)tIterDataTypeOIDs.next();
            try{
                tSelectedStat.setString(1, tDataTypeOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveAttachmentTypeTable(String pProcessPackageOID) throws SQLException{
        Collection tDataTypeOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getDataTypeOIDs();
        String tTableName = ProcessPackage.ATTACHMENT_TYPE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterDataTypeOIDs = tDataTypeOIDs.iterator();
        String tDataTypeOID = null;
        while (tIterDataTypeOIDs.hasNext()){
            tDataTypeOID = (String)tIterDataTypeOIDs.next();
            try{
                tSelectedStat.setString(1, tDataTypeOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveFormTypeTable(String pProcessPackageOID) throws SQLException{
        Collection tDataTypeOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getDataTypeOIDs();
        String tTableName = ProcessPackage.FORM_TYPE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterDataTypeOIDs = tDataTypeOIDs.iterator();
        String tDataTypeOID = null;
        while (tIterDataTypeOIDs.hasNext()){
            tDataTypeOID = (String)tIterDataTypeOIDs.next();
            try{
                tSelectedStat.setString(1, tDataTypeOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveFormalParameterTable(String pProcessPackageOID) throws SQLException{
        Collection tFormalParameterContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getFormalParameterContainerOIDs();
        String tTableName = ProcessPackage.FORMALPARAMETER_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterFormalParameterContainerOIDs = tFormalParameterContainerOIDs.iterator();
        String tFormalParameterContainerOID = null;
        while (tIterFormalParameterContainerOIDs.hasNext()){
            tFormalParameterContainerOID = (String)tIterFormalParameterContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tFormalParameterContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveContainer_ApplicationDefTable(String pProcessPackageOID) throws SQLException{
        Collection tApplicationDefContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getPackageAndDefOIDs();
        String tTableName = ProcessPackage.APPLICATION_CONTAINER_B_APPLICATION_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where IAppDefContainerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterApplicationDefContainerOIDs = tApplicationDefContainerOIDs.iterator();
        String tApplicationDefContainerOID = null;
        while (tIterApplicationDefContainerOIDs.hasNext()){
            tApplicationDefContainerOID = (String)tIterApplicationDefContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tApplicationDefContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyBridgeData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveRelevantDataDefTable(String pProcessPackageOID) throws SQLException{
        Collection tRelevantDataDefContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getPackageAndDefOIDs();
        String tTableName = ProcessPackage.RELEVANT_DATA_DEF_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterRelevantDataDefContainerOIDs = tRelevantDataDefContainerOIDs.iterator();
        String tRelevantDataDefContainerOID = null;
        while (tIterRelevantDataDefContainerOIDs.hasNext()){
            tRelevantDataDefContainerOID = (String)tIterRelevantDataDefContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tRelevantDataDefContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveFormOperationDefTable(String pProcessPackageOID) throws SQLException{
        Collection tFormOperationDefinitionOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getFormOperationDefinitionOIDs();
        String tTableName = ProcessPackage.FORM_OPER_DEF_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterFormOperationDefinitionOIDs = tFormOperationDefinitionOIDs.iterator();
        String tFormOperationDefinitionOID = null;
        while (tIterFormOperationDefinitionOIDs.hasNext()){
            tFormOperationDefinitionOID = (String)tIterFormOperationDefinitionOIDs.next();
            try{
                tSelectedStat.setString(1, tFormOperationDefinitionOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveToolTable(String pProcessPackageOID) throws SQLException{
        Collection tImplementationOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getImplementationOIDs();
        String tTableName = ProcessPackage.TOOL_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterImplementationOIDs = tImplementationOIDs.iterator();
        String tImplementationOID = null;
        while (tIterImplementationOIDs.hasNext()){
            tImplementationOID = (String)tIterImplementationOIDs.next();
            try{
                tSelectedStat.setString(1, tImplementationOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveDecisionRuleTable(String pProcessPackageOID) throws SQLException{
        //先收集DecisionRule OID
        this.collecteDecisionRule(pProcessPackageOID);
        Collection tDecisionRuleOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getDecisionRuleOIDs();
        String tTableName = ProcessPackage.DECISION_RULE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterDecisionRuleOIDs = tDecisionRuleOIDs.iterator();
        String tDecisionRuleOID = null;
        while (tIterDecisionRuleOIDs.hasNext()){
            tDecisionRuleOID = (String)tIterDecisionRuleOIDs.next();
            try{
                tSelectedStat.setString(1, tDecisionRuleOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveImplementationTable(String pProcessPackageOID) throws SQLException{
        Collection tActivityTypeOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getActivityTypeOIDs();
        String tTableName = ProcessPackage.IMPLEMENTATION_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterActivityTypeOIDs = tActivityTypeOIDs.iterator();
        String tActivityTypeOID = null;
        while (tIterActivityTypeOIDs.hasNext()){
            tActivityTypeOID = (String)tIterActivityTypeOIDs.next();
            try{
                tSelectedStat.setString(1, tActivityTypeOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveRouteTable(String pProcessPackageOID) throws SQLException{
        Collection tActivityTypeOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getActivityTypeOIDs();
        String tTableName = ProcessPackage.ROUTE_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterActivityTypeOIDs = tActivityTypeOIDs.iterator();
        String tActivityTypeOID = null;
        while (tIterActivityTypeOIDs.hasNext()){
            tActivityTypeOID = (String)tIterActivityTypeOIDs.next();
            try{
                tSelectedStat.setString(1, tActivityTypeOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveBlockActivityTable(String pProcessPackageOID) throws SQLException{
        Collection tActivityTypeOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getActivityTypeOIDs();
        String tTableName = ProcessPackage.BLOCK_ACT_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterActivityTypeOIDs = tActivityTypeOIDs.iterator();
        String tActivityTypeOID = null;
        while (tIterActivityTypeOIDs.hasNext()){
            tActivityTypeOID = (String)tIterActivityTypeOIDs.next();
            try{
                tSelectedStat.setString(1, tActivityTypeOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveFormFieldAccessDefTable(String pProcessPackageOID) throws SQLException{
        Collection tFormFieldAccessDefOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getFormFieldAccessDefinitionOIDs();
        String tTableName = ProcessPackage.FORM_FIELD_ACCESS_DEF_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterFormFieldAccessDefOIDs = tFormFieldAccessDefOIDs.iterator();
        String tFormFieldAccessDefOID = null;
        while (tIterFormFieldAccessDefOIDs.hasNext()){
            tFormFieldAccessDefOID = (String)tIterFormFieldAccessDefOIDs.next();
            try{
                tSelectedStat.setString(1, tFormFieldAccessDefOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveDecisionRuleListTable(String pProcessPackageOID) throws SQLException{
        Collection tDecisionRuleListOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getDecisionRuleListOIDs();
        String tTableName = ProcessPackage.DECISION_RULE_LIST_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterDecisionRuleListOIDs = tDecisionRuleListOIDs.iterator();
        String tDecisionRuleListOID = null;
        while (tIterDecisionRuleListOIDs.hasNext()){
            tDecisionRuleListOID = (String)tIterDecisionRuleListOIDs.next();
            try{
                tSelectedStat.setString(1, tDecisionRuleListOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveBoundViewInformationTable(String pProcessPackageOID) throws SQLException{
        Collection tBoundViewInfoOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getBoundViewInformationOIDs();
        String tTableName = ProcessPackage.BOUND_VIEW_INFO_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterBoundViewInfoOIDs = tBoundViewInfoOIDs.iterator();
        String tBoundViewInfoOID = null;
        while (tIterBoundViewInfoOIDs.hasNext()){
            tBoundViewInfoOID = (String)tIterBoundViewInfoOIDs.next();
            try{
                tSelectedStat.setString(1, tBoundViewInfoOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveProcessViewInformationTable(String pProcessPackageOID) throws SQLException{
        Collection tProcessViewInfoOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getProcessViewInformationOIDs();
        String tTableName = ProcessPackage.PROCESS_BOUND_VIEW_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterProcessViewInfoOIDs = tProcessViewInfoOIDs.iterator();
        String tProcessViewInfoOID = null;
        while (tIterProcessViewInfoOIDs.hasNext()){
            tProcessViewInfoOID = (String)tIterProcessViewInfoOIDs.next();
            try{
                tSelectedStat.setString(1, tProcessViewInfoOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveActivityDefinition(String pProcessPackageOID) throws SQLException{
        Set tActDefContainerOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getActDefContainerOIDs();
        String tTableName = ProcessPackage.ACTIVITY_DEF_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(
                tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterActDefContainerOIDs = tActDefContainerOIDs.iterator();
        String tActDefContainerOID = null;
        while (tIterActDefContainerOIDs.hasNext()){
            tActDefContainerOID = (String)tIterActDefContainerOIDs.next();
            try{
                tSelectedStat.setString(1, tActDefContainerOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveActivitySetDefinition(String pProcessPackageOID)throws SQLException{
        Collection tProcessDefOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getProcessDefOIDs();
        String tTableName = ProcessPackage.ACTIVITY_SET_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where containerOID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(
                tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterProcessDefOIDs = tProcessDefOIDs.iterator();
        String tProcessDefOID = null;
        while (tIterProcessDefOIDs.hasNext()){
            tProcessDefOID = (String)tIterProcessDefOIDs.next();
            try{
                tSelectedStat.setString(1, tProcessDefOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS, tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveProcessDefinition(String pProcessPackageOID)throws SQLException{
        Collection tProcessDefOIDs =
                ((ProcessPackage)this.transProcessPackage.get(pProcessPackageOID)).getProcessDefOIDs();
        String tTableName = ProcessPackage.PROCESS_DEFINITION_TABLE_NAME;
        String tSQLSelectCommand = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectedStat =
                (PreparedStatement)this.sourcePreparedStatements.get(
                tSQLSelectCommand);
        try{
            if (tSelectedStat == null){
                tSelectedStat = this.jdbcHelper.createSourcePrepareStatement(tSQLSelectCommand);
                this.sourcePreparedStatements.put(tSQLSelectCommand, tSelectedStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tRS = null;
        Iterator tIterProcessDefOIDs = tProcessDefOIDs.iterator();
        String tProcessDefOID = null;
        while (tIterProcessDefOIDs.hasNext()){
            tProcessDefOID = (String)tIterProcessDefOIDs.next();
            try{
                tSelectedStat.setString(1, tProcessDefOID);
                tRS = tSelectedStat.executeQuery();
            } catch (SQLException e){
                System.out.println("Excute query " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
            try{
                this.checkThenCopyData(pProcessPackageOID, tRS,
                                       tTableName);
                this.jdbcHelper.releaseResultSet(tRS);
            } catch (SQLException e){
                System.out.println("Inserte " + tTableName +
                                   " statement error." + e.getMessage());
                throw e;
            }
        }
    }
    private void archiveProcessPackageTable(String pProcessPackageOID)throws SQLException{
        String tTableName = ProcessPackage.PROCESS_PACKAGE_TABLE_NAME;
        String tSQLSelectProcessPackage = "Select * from " + tTableName +
                " where OID = ? ";
        PreparedStatement tSelectProcessPackageStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectProcessPackage);
        try{
            if (tSelectProcessPackageStat == null){
                tSelectProcessPackageStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectProcessPackage);
                this.sourcePreparedStatements.put(tSQLSelectProcessPackage, tSelectProcessPackageStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tProcessPackageRS = null;
        try{
            tSelectProcessPackageStat.setString(1, pProcessPackageOID);
            tProcessPackageRS = tSelectProcessPackageStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pProcessPackageOID, tProcessPackageRS, tTableName);
            this.jdbcHelper.releaseResultSet(tProcessPackageRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archiveCustomProcessPackageTable(String pCustomProcessPackageOID)throws SQLException{
        String tTableName = CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME;
        String tSQLSelectProcessPackage = "Select * from " + tTableName + " where OID = ? ";
        PreparedStatement tSelectProcessPackageStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectProcessPackage);
        try{
            if (tSelectProcessPackageStat == null){
                tSelectProcessPackageStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectProcessPackage);
                this.sourcePreparedStatements.put(tSQLSelectProcessPackage, tSelectProcessPackageStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tProcessPackageRS = null;
        try{
            tSelectProcessPackageStat.setString(1, pCustomProcessPackageOID);
            tProcessPackageRS = tSelectProcessPackageStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyData(pCustomProcessPackageOID, tProcessPackageRS,
                                   tTableName);
            this.jdbcHelper.releaseResultSet(tProcessPackageRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void archivePackage_DefTable(String pProcessPackageOID)throws SQLException{
        String tTableName = ProcessPackage.PACKAGE_B_DEFINITION_TABLE_NAME;
        String tSQLSelectPackage_Def = "Select * from " + tTableName + " where ProcessPackageOID = ? ";
        PreparedStatement tSelectPackage_DefStat =
                (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectPackage_Def);
        try{
            if (tSelectPackage_DefStat == null){
                tSelectPackage_DefStat =
                        this.jdbcHelper.createSourcePrepareStatement(tSQLSelectPackage_Def);
                this.sourcePreparedStatements.put(tSQLSelectPackage_Def, tSelectPackage_DefStat);
            }
        } catch (SQLException e){
            System.out.println("Create " + tTableName +
                               " preparedStatement error." + e.getMessage());
            throw e;
        }
        ResultSet tPackage_DefRS = null;
        try{
            tSelectPackage_DefStat.setString(1, pProcessPackageOID);
            tPackage_DefRS = tSelectPackage_DefStat.executeQuery();
        } catch (SQLException e){
            System.out.println("Excute query " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
        try{
            this.checkThenCopyBridgeData(pProcessPackageOID, tPackage_DefRS, tTableName);
            this.jdbcHelper.releaseResultSet(tPackage_DefRS);
        } catch (SQLException e){
            System.out.println("Inserte " + tTableName +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private Collection getDecisionConditionOIDs(String pContainerOID)throws SQLException{
        Set tRtn = new HashSet();
        try{
            String tQueryDecisionCondition =
                    "Select OID from " +  ProcessPackage.DECISION_COND_TABLE_NAME + " where containerOID = ? ";
            PreparedStatement tDecisionConditionPS =
                    (PreparedStatement)this.sourcePreparedStatements.get(tQueryDecisionCondition);
            try{
                if (tDecisionConditionPS == null){
                    tDecisionConditionPS = this.jdbcHelper.createSourcePrepareStatement(tQueryDecisionCondition);
                    this.sourcePreparedStatements.put(tQueryDecisionCondition, tDecisionConditionPS);
                }
            } catch (SQLException e){
                System.out.println("Create " + ProcessPackage.DECISION_COND_TABLE_NAME +
                                   " preparedStatement error." + e.getMessage());
                throw e;
            }
            ResultSet tTempRS = null;



            tDecisionConditionPS.setString(1, pContainerOID);
            tTempRS = tDecisionConditionPS.executeQuery();
            while (tTempRS.next()){
                tRtn.add(tTempRS.getString("OID"));
            }
            this.jdbcHelper.releaseResultSet(tTempRS);
            return tRtn;
        } catch (SQLException e){
            System.out.println("Excute query activity type " +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private Collection getDecisionLevelOIDs(String pContainerOID)throws SQLException{
        Set tRtn = new HashSet();
        try{
            String tQueryDecisionLevel =
                    "Select OID from " +  ProcessPackage.DECISION_LEVEL_TABLE_NAME + " where containerOID = ? ";
            PreparedStatement tDecisionLevelPS =
                    (PreparedStatement)this.sourcePreparedStatements.get(tQueryDecisionLevel);
            try{
                if (tDecisionLevelPS == null){
                    tDecisionLevelPS = this.jdbcHelper.createSourcePrepareStatement(tQueryDecisionLevel);
                    this.sourcePreparedStatements.put(tQueryDecisionLevel, tDecisionLevelPS);
                }
            } catch (SQLException e){
                System.out.println("Create " + ProcessPackage.DECISION_LEVEL_TABLE_NAME +
                                   " preparedStatement error." + e.getMessage());
                throw e;
            }
            ResultSet tTempRS = null;
            tDecisionLevelPS.setString(1, pContainerOID);
            tTempRS = tDecisionLevelPS.executeQuery();
            while (tTempRS.next()){
                tRtn.add(tTempRS.getString("OID"));
            }
            this.jdbcHelper.releaseResultSet(tTempRS);
            return tRtn;
        } catch (SQLException e){
            System.out.println("Excute query activity type " +
                               " statement error." + e.getMessage());
            throw e;
        }
    }
    private void collecteDecisionRule(String pOID) throws SQLException{
        //先收集DecisionRule
        ProcessPackage tProcessPackage = (ProcessPackage) this.transProcessPackage.get(pOID);
        ResultSet tDecisionRuleOIDRS = null;
        String tDecisionRuleOID = null;
        if (tProcessPackage.getDecisionConditionOIDs().size() > 0){
            String tSQLSelectDecisionRuleByCond = "Select OID from " +
                    ProcessPackage.DECISION_RULE_TABLE_NAME + " where decisionConditionOID = ? ";
            PreparedStatement tSelectDecisionRuleOIDByCondStat =
                    (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectDecisionRuleByCond);
            try{
                if (tSelectDecisionRuleOIDByCondStat == null){
                    tSelectDecisionRuleOIDByCondStat = this.jdbcHelper.createSourcePrepareStatement(
                            tSQLSelectDecisionRuleByCond);
                    this.sourcePreparedStatements.put(tSQLSelectDecisionRuleByCond,
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
                    (PreparedStatement)this.sourcePreparedStatements.get(tSQLSelectDecisionRuleByLevel);
            try{
                if (tSelectDecisionRuleOIDByLevelStat == null){
                    tSelectDecisionRuleOIDByLevelStat = this.jdbcHelper.createSourcePrepareStatement(
                            tSQLSelectDecisionRuleByLevel);
                    this.sourcePreparedStatements.put(tSQLSelectDecisionRuleByLevel,
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
        this.transProcessPackage.put(pOID, tProcessPackage);
    }
}