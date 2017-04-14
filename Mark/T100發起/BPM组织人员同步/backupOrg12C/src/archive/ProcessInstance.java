package archive;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class ProcessInstance {
    public static String PROCESS_INST_TABLE_NAME = "ProcessInstance";
    public static String PROCESS_CONTEXT_TABLE_NAME = "ProcessContext";
    public static String ACT_NOTICE_TABLE_NAME = "ActivityNotification";
    public static String ATTACH_INST_TABLE_NAME = "AttachmentInstance";
    public static String BLOCK_ACT_INST_TABLE_NAME = "BlockActivityInstance";
    public static String BY_REF_PAR_TABLE_NAME = "ByReferenceParameter";
    public static String BY_VALUE_PAR_TABLE_NAME = "ByValueParameter";
    public static String CHANGE_ACT_STATE_AUDIT_TABLE_NAME = "ChangeActivityStateAudit";
    public static String CHANGE_PROCESS_STATE_AUDIT_TABLE_NAME = "ChangeProcessStateAudit";
    public static String CHANGE_WORKITEM_STATE_AUDIT_TABLE_NAME = "ChangeWorkItemStateAudit";
    public static String FROM_INSTANCE_TABLE_NAME = "FormInstance";
    public static String GLOBAL_RELEVANT_DATA_TABLE_NAME = "GlobalRelevantData";
    public static String LOCAL_RELEVANT_DATA_TABLE_NAME = "LocalRelevantData";
    public static String PAR_ACT_INST_TABLE_NAME = "ParticipantActivityInstance";
    public static String PROCESS_NOTICE_TABLE_NAME = "ProcessNotification";
    public static String REASSIGN_WORKITEM_DATE_TABLE_NAME = "ReassignWorkItemAuditData";
    public static String STRING_RUNTIME_VALUE_TABLE_NAME = "StringWorkflowRuntimeValue";
    public static String SUBFLOW_INST_TABLE_NAME = "SubFlowActivityInstance";
    public static String WORKASSIGNMENT_TABLE_NAME = "WorkAssignment";
    public static String WORKITEM_TABLE_NAME = "WorkItem";
    public static String WORKSTEP_TABLE_NAME = "WorkStep";

    private String OID;
    public String getOID(){
        return OID;
    }
    public void setOID(String pNewValue){
        if (pNewValue == null || "".equals(pNewValue)){
            throw new IllegalArgumentException("OID is not null");
        }
        this.OID = pNewValue;
    }

    private String contextOID;
    public String getContextOID(){
        return contextOID;
    }
    public void setContextOID(String pNewValue){
        this.contextOID = pNewValue;
    }

    private Collection activityNotificationOIDs;
    public Collection getActivityNotificationOIDs(){
        if (this.activityNotificationOIDs == null){
            this.activityNotificationOIDs = new HashSet();
        }
        return this.activityNotificationOIDs;
    }
    public void setActivityNotificationOIDs(Collection pNewValue){
        this.activityNotificationOIDs = pNewValue;
    }
    public void addActivityNotificationOID(String pNewValue){
        this.getActivityNotificationOIDs().add(pNewValue);
    }
    private Collection attachmentInstanceOIDs;
    public Collection getAttachmentInstanceOIDs(){
        if (this.attachmentInstanceOIDs == null){
            this.attachmentInstanceOIDs = new HashSet();
        }
        return this.attachmentInstanceOIDs;
    }
    public void setAttachmentInstanceOIDs(Collection pNewValue){
        this.attachmentInstanceOIDs = pNewValue;
    }
    public void addAttachmentInstanceOID(String pNewValue){
        this.getAttachmentInstanceOIDs().add(pNewValue);
    }
    private Collection blockActivityInstanceOIDs;
    public Collection getBlockActivityInstanceOIDs(){
        if (this.blockActivityInstanceOIDs == null){
            this.blockActivityInstanceOIDs = new HashSet();
        }
        return this.blockActivityInstanceOIDs;
    }
    public void setBlockActivityInstanceOIDs(Collection pNewValue){
        this.blockActivityInstanceOIDs = pNewValue;
    }
    public void addBlockActivityInstanceOID(String pNewValue){
        this.getBlockActivityInstanceOIDs().add(pNewValue);
    }
    private Collection byReferenceParameterOIDs;
    public Collection getByReferenceParameterOIDs(){
        if (this.byReferenceParameterOIDs == null){
            this.byReferenceParameterOIDs = new HashSet();
        }
        return this.byReferenceParameterOIDs;
    }
    public void setByReferenceParameterOIDs(Collection pNewValue){
        this.byReferenceParameterOIDs = pNewValue;
    }
    public void addByReferenceParameterOID(String pNewValue){
        this.getByReferenceParameterOIDs().add(pNewValue);
    }
    private Collection byValueParameterOIDs;
    public Collection getByValueParameterOIDs(){
        if (this.byValueParameterOIDs == null){
            this.byValueParameterOIDs = new HashSet();
        }
        return this.byValueParameterOIDs;
    }
    public void setByValueParameterOIDs(Collection pNewValue){
        this.byValueParameterOIDs = pNewValue;
    }
    public void addByValueParameterOID(String pNewValue){
        this.getByValueParameterOIDs().add(pNewValue);
    }
    private Collection changeActivityStateAuditOIDs;
    public Collection getChangeActivityStateAuditOIDs(){
        if (this.changeActivityStateAuditOIDs == null){
            this.changeActivityStateAuditOIDs = new HashSet();
        }
        return this.changeActivityStateAuditOIDs;
    }
    public void setChangeActivityStateAuditOIDs(Collection pNewValue){
        this.changeActivityStateAuditOIDs = pNewValue;
    }
    public void addChangeActivityStateAuditOID(String pNewValue){
        this.getChangeActivityStateAuditOIDs().add(pNewValue);
    }
    private Collection changeProcessStateAuditOIDs;
    public Collection getChangeProcessStateAuditOIDs(){
        if (this.changeProcessStateAuditOIDs == null){
            this.changeProcessStateAuditOIDs = new HashSet();
        }
        return this.changeProcessStateAuditOIDs;
    }
    public void setChangeProcessStateAuditOIDs(Collection pNewValue){
        this.changeProcessStateAuditOIDs = pNewValue;
    }
    public void addChangeProcessStateAuditOID(String pNewValue){
        this.getChangeProcessStateAuditOIDs().add(pNewValue);
    }

    private Collection changeWorkItemStateAuditOIDs;
    public Collection getChangeWorkItemStateAuditOIDs(){
        if (this.changeWorkItemStateAuditOIDs == null){
            this.changeWorkItemStateAuditOIDs = new HashSet();
        }
        return this.changeWorkItemStateAuditOIDs;
    }
    public void setChangeWorkItemStateAuditOIDs(Collection pNewValue){
        this.changeWorkItemStateAuditOIDs = pNewValue;
    }
    public void addChangeWorkItemStateAuditOID(String pNewValue){
        this.getChangeWorkItemStateAuditOIDs().add(pNewValue);
    }
    //private Collection createProcessAuditOIDs;
//    public Collection getCreateProcessAuditAuditOIDs(){
//        if (this.createProcessAuditOIDs == null){
//            this.createProcessAuditOIDs = new HashSet();
//        }
//        return this.createProcessAuditOIDs;
//    }
//    public void setCreateProcessAuditOIDs(Collection pNewValue){
//        this.createProcessAuditOIDs = pNewValue;
//    }
//    public void addCreateProcessAuditOID(String pNewValue){
//        this.getCreateProcessAuditAuditOIDs().add(pNewValue);
//    }
    private Collection formInstanceOIDs;
    public Collection getFormInstanceOIDs(){
        if (this.formInstanceOIDs == null){
            this.formInstanceOIDs = new HashSet();
        }
        return this.formInstanceOIDs;
    }
    public void setFormInstanceOIDs(Collection pNewValue){
        this.formInstanceOIDs = pNewValue;
    }
    public void addFormInstanceOID(String pNewValue){
        this.getFormInstanceOIDs().add(pNewValue);
    }
    private Collection globalRelevantDataOIDs;
    public Collection getGlobalRelevantDataOIDs(){
        if (this.globalRelevantDataOIDs == null){
            this.globalRelevantDataOIDs = new HashSet();
        }
        return this.globalRelevantDataOIDs;
    }
    public void setGlobalRelevantDataOIDs(Collection pNewValue){
        this.globalRelevantDataOIDs = pNewValue;
    }
    public void addGlobalRelevantDataOID(String pNewValue){
        this.getGlobalRelevantDataOIDs().add(pNewValue);
    }
    private Collection localRelevantDataOIDs;
    public Collection getLocalRelevantDataOIDs(){
        if (this.localRelevantDataOIDs == null){
            this.localRelevantDataOIDs = new HashSet();
        }
        return this.localRelevantDataOIDs;
    }
    public void setLocalRelevantDataOIDs(Collection pNewValue){
        this.localRelevantDataOIDs = pNewValue;
    }
    public void addLocalRelevantDataOID(String pNewValue){
        this.getLocalRelevantDataOIDs().add(pNewValue);
    }
    private Collection participantActivityInstanceOIDs;
    public Collection getParticipantActivityInstanceOIDs(){
        if (this.participantActivityInstanceOIDs == null){
            this.participantActivityInstanceOIDs = new HashSet();
        }
        return this.participantActivityInstanceOIDs;
    }
    public void setParticipantActivityInstanceOIDs(Collection pNewValue){
        this.participantActivityInstanceOIDs = pNewValue;
    }
    public void addParticipantActivityInstanceOID(String pNewValue){
        this.getParticipantActivityInstanceOIDs().add(pNewValue);
    }
    private Collection processNotificationOIDs;
    public Collection getProcessNotificationOIDs(){
        if (this.processNotificationOIDs == null){
            this.processNotificationOIDs = new HashSet();
        }
        return this.processNotificationOIDs;
    }
    public void setProcessNotificationOIDs(Collection pNewValue){
        this.processNotificationOIDs = pNewValue;
    }
    public void addProcessNotificationOID(String pNewValue){
        this.getProcessNotificationOIDs().add(pNewValue);
    }
    private Collection reassignWorkItemAuditDataOIDs;
    public Collection getReassignWorkItemAuditDataOIDs(){
        if (this.reassignWorkItemAuditDataOIDs == null){
            this.reassignWorkItemAuditDataOIDs = new HashSet();
        }
        return this.reassignWorkItemAuditDataOIDs;
    }
    public void setReassignWorkItemAuditDataOIDs(Collection pNewValue){
        this.reassignWorkItemAuditDataOIDs = pNewValue;
    }
    public void addReassignWorkItemAuditDataOID(String pNewValue){
        this.getReassignWorkItemAuditDataOIDs().add(pNewValue);
    }
    private Collection stringWorkflowRuntimeValueOIDs;
    public Collection getStringWorkflowRuntimeValueOIDs(){
        if (this.stringWorkflowRuntimeValueOIDs == null){
            this.stringWorkflowRuntimeValueOIDs = new HashSet();
        }
        return this.stringWorkflowRuntimeValueOIDs;
    }
    public void setStringWorkflowRuntimeValueOIDs(Collection pNewValue){
        this.stringWorkflowRuntimeValueOIDs = pNewValue;
    }
    public void addStringWorkflowRuntimeValueOID(String pNewValue){
        this.getStringWorkflowRuntimeValueOIDs().add(pNewValue);
    }
    private Collection subFlowActivityInstanceOIDs;
    public Collection getSubFlowActivityInstanceOIDs(){
        if (this.subFlowActivityInstanceOIDs == null){
            this.subFlowActivityInstanceOIDs = new HashSet();
        }
        return this.subFlowActivityInstanceOIDs;
    }
    public void setSubFlowActivityInstanceOIDs(Collection pNewValue){
        this.subFlowActivityInstanceOIDs = pNewValue;
    }
    public void addSubFlowActivityInstanceOID(String pNewValue){
        this.getSubFlowActivityInstanceOIDs().add(pNewValue);
    }
    private Collection workAssignmentOIDs;
    public Collection getWorkAssignmentOIDs(){
        if (this.workAssignmentOIDs == null){
            this.workAssignmentOIDs = new HashSet();
        }
        return this.workAssignmentOIDs;
    }
    public void setWorkAssignmentOIDs(Collection pNewValue){
        this.workAssignmentOIDs = pNewValue;
    }
    public void addWorkAssignmentOID(String pNewValue){
        this.getWorkAssignmentOIDs().add(pNewValue);
    }
    private Collection workItemOIDs;
    public Collection getWorkItemOIDs(){
        if (this.workItemOIDs == null){
            this.workItemOIDs = new HashSet();
        }
        return this.workItemOIDs;
    }
    public void setWorkItemOIDs(Collection pNewValue){
        this.workItemOIDs = pNewValue;
    }
    public void addWorkItemOID(String pNewValue){
        this.getWorkItemOIDs().add(pNewValue);
    }
    private Collection workStepOIDs;
    public Collection getWorkStepOIDs(){
        if (this.workStepOIDs == null){
            this.workStepOIDs = new HashSet();
        }
        return this.workStepOIDs;
    }
    public void setWorkStepOIDs(Collection pNewValue){
        this.workStepOIDs = pNewValue;
    }
    public void addWorkStepOID(String pNewValue){
        this.getWorkStepOIDs().add(pNewValue);
    }
    //可能是formInstance, 可能是attachmentInstance, 或 StringRuntimeValue
    private Collection runtimeValueOIDs;
    public Collection getRuntimeValueOIDs(){
        if (this.runtimeValueOIDs == null){
            this.runtimeValueOIDs = new HashSet();
        }
        return this.runtimeValueOIDs;
    }
    public void setRuntimeValueOIDs(Collection pNewValue){
        this.runtimeValueOIDs = pNewValue;
    }
    public void addRuntimeValueOID(String pNewValue){
        this.getRuntimeValueOIDs().add(pNewValue);
    }
    public ProcessInstance(String pOID) {

        this.setOID(pOID);
    }
    /**
     * 取回workItem OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeWorkItemOIDs(){
        int tSize = this.getWorkItemOIDs().size() ;

        StringBuffer tRtn = new StringBuffer();
        Iterator tIterOIDs = this.getWorkItemOIDs().iterator();
        int tCount = 0;
        while (tIterOIDs.hasNext()){
            tCount ++;
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            if (tCount != tSize){
                tRtn.append(",");
            }
        }
        if (tRtn.length() != 0){
            return tRtn.toString();
        } else {
            return null;
        }
    }
    /**
     * 取回runtimeValue OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeRuntimeValueOIDs(){
        int tSize = this.getRuntimeValueOIDs().size();

        StringBuffer tRtn = new StringBuffer();
        Iterator tIterOIDs = this.getRuntimeValueOIDs().iterator();
        int tCount = 0;
        while (tIterOIDs.hasNext()){
            tCount ++;
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            if (tCount != tSize){
                tRtn.append(",");
            }
        }
        if (tRtn.length() != 0){
            return tRtn.toString();
        } else {
            return null;
        }
    }
    /**
     * 取回ActivityInstance OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeActInstOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getBlockActivityInstanceOIDs().size();
        Iterator tIterOIDs = this.getBlockActivityInstanceOIDs().iterator();
        int tCount = 0;
        while (tIterOIDs.hasNext()){
            tCount ++;
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            if (tCount != tSize){
                tRtn.append(",");
            }
        }
        tSize = this.getParticipantActivityInstanceOIDs().size();
        tIterOIDs = this.getParticipantActivityInstanceOIDs().iterator();
        tCount = 0;
        while (tIterOIDs.hasNext()){
            tCount ++;
            if (tCount == 1){
                if (tRtn.length() > 0){
                    tRtn.append(",");
                }
            }
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            if (tCount != tSize){
                tRtn.append(",");
            }
        }
        tSize = this.getSubFlowActivityInstanceOIDs().size();
        tIterOIDs = this.getSubFlowActivityInstanceOIDs().iterator();
        tCount = 0;
        while (tIterOIDs.hasNext()){
            tCount ++;
            if (tCount == 1){
                if (tRtn.length() > 0){
                    tRtn.append(",");
                }
            }
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            if (tCount != tSize){
                tRtn.append(",");
            }
        }
        if (tRtn.length() != 0){
            return tRtn.toString();
        } else {
            return null;
        }
    }
    /**
     * 取回ActivityInstance OID 集合
     */
    public Set getActInstOIDs(){
        Set tRtn = new HashSet();
        tRtn.addAll(this.getBlockActivityInstanceOIDs());
        tRtn.addAll(this.getParticipantActivityInstanceOIDs());
        tRtn.addAll(this.getSubFlowActivityInstanceOIDs());
        return tRtn;
    }
    public List generateDeleteCommands(){
        List tRtn = new ArrayList();
        StringBuffer tDeleteCommand = new StringBuffer();
        tDeleteCommand.append("Delete From ").append(this.PROCESS_INST_TABLE_NAME).append(" Where OID = '").append(
                this.getOID()).append("';");
        tRtn.add(tDeleteCommand.toString());
        tDeleteCommand = new StringBuffer();
        tDeleteCommand.append("Delete From ").append(this.PROCESS_CONTEXT_TABLE_NAME).append(" Where OID = '").append(
                this.getContextOID()).append("';");
        tRtn.add(tDeleteCommand.toString());

        int tSize = this.getActivityNotificationOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getActivityNotificationOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.ACT_NOTICE_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getAttachmentInstanceOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getAttachmentInstanceOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.ATTACH_INST_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getBlockActivityInstanceOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getBlockActivityInstanceOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.BLOCK_ACT_INST_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getByReferenceParameterOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getByReferenceParameterOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.BY_REF_PAR_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getByValueParameterOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getByValueParameterOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.BY_VALUE_PAR_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getChangeActivityStateAuditOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getChangeActivityStateAuditOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.CHANGE_ACT_STATE_AUDIT_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getChangeProcessStateAuditOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getChangeProcessStateAuditOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.CHANGE_PROCESS_STATE_AUDIT_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getChangeWorkItemStateAuditOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getChangeWorkItemStateAuditOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.CHANGE_WORKITEM_STATE_AUDIT_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
//        tSize = this.getCreateProcessAuditAuditOIDs().size();
//        if (tSize > 0){
//            Iterator tIterOIDs = this.getCreateProcessAuditAuditOIDs().iterator();
//            int tCount = 0;
//            tDeleteCommand = new StringBuffer();
//            tDeleteCommand.append("Delete From ").append(this.CREATE_PROCESS_AUDIT_TABLE_NAME).append(" Where OID in( ");
//            while (tIterOIDs.hasNext()){
//                tCount ++;
//                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
//                if (tCount != tSize){
//                    tDeleteCommand.append(",");
//                }
//            }
//            tDeleteCommand.append(");");
//            tRtn.add(tDeleteCommand.toString());
//        }
        tSize = this.getFormInstanceOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getFormInstanceOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.FROM_INSTANCE_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getGlobalRelevantDataOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getGlobalRelevantDataOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.GLOBAL_RELEVANT_DATA_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getLocalRelevantDataOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getLocalRelevantDataOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.LOCAL_RELEVANT_DATA_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getParticipantActivityInstanceOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getParticipantActivityInstanceOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.PAR_ACT_INST_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getProcessNotificationOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getProcessNotificationOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.PROCESS_NOTICE_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getReassignWorkItemAuditDataOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getReassignWorkItemAuditDataOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.REASSIGN_WORKITEM_DATE_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getStringWorkflowRuntimeValueOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getStringWorkflowRuntimeValueOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.STRING_RUNTIME_VALUE_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getSubFlowActivityInstanceOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getSubFlowActivityInstanceOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.SUBFLOW_INST_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getWorkAssignmentOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getWorkAssignmentOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.WORKASSIGNMENT_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getWorkItemOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getWorkItemOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.WORKITEM_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        tSize = this.getWorkStepOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getWorkStepOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.WORKSTEP_TABLE_NAME).append(" Where OID in( ");
            while (tIterOIDs.hasNext()){
                tCount ++;
                tDeleteCommand.append("'").append((String)tIterOIDs.next()).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
        }
        return tRtn;
    }
}