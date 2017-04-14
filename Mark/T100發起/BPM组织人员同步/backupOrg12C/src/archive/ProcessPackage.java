package archive;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class ProcessPackage {
    public static String PROCESS_PACKAGE_TABLE_NAME = "ProcessPackage";
    public static String PACKAGE_B_DEFINITION_TABLE_NAME = "ProcessPackage_ProcessDef";
    public static String PROCESS_DEFINITION_TABLE_NAME = "ProcessDefinition";
    public static String ACTIVITY_SET_TABLE_NAME = "ActivitySetDefinition";
    public static String ACTIVITY_DEF_TABLE_NAME = "ActivityDefinition";
    public static String PROCESS_BOUND_VIEW_TABLE_NAME = "ProcessViewInformation";
    public static String BOUND_VIEW_INFO_TABLE_NAME = "BoundViewInformation";
    public static String DECISION_RULE_LIST_TABLE_NAME = "DecisionRuleList";
    public static String FORM_FIELD_ACCESS_DEF_TABLE_NAME = "FormFieldAccessDefinition";
    public static String BLOCK_ACT_TABLE_NAME = "BlockActivity";
    public static String IMPLEMENTATION_TABLE_NAME = "Implementation";
    public static String ROUTE_TABLE_NAME = "Route";
    public static String DECISION_COND_TABLE_NAME = "DecisionCondition";
    public static String DECISION_LEVEL_TABLE_NAME = "DecisionLevel";
    public static String DECISION_RULE_TABLE_NAME = "DecisionRule";
    public static String NO_TABLE_NAME = "Nos";
    public static String SUBFLOW_TABLE_NAME = "SubFlow";
    public static String PARTICIPANT_DEF_TABLE_NAME = "ParticipantDefinition";
    public static String TOOL_TABLE_NAME = "Tool";
    public static String ACTUAL_PARAMETER_TABLE_NAME = "ActualParameter";
    public static String FORM_OPER_DEF_TABLE_NAME = "FormOperationDefinition";
    public static String RELEVANT_DATA_DEF_TABLE_NAME = "RelevantDataDefinition";
    public static String APPLICATION_CONTAINER_B_APPLICATION_TABLE_NAME = "IAppDefContainer_AppDef";
    public static String FORMALPARAMETER_TABLE_NAME = "FormalParameter";
    public static String CONDITION_DEF_TABLE_NAME = "ConditionDefinition";
    public static String TRANSITION_DEF_TABLE_NAME = "TransitionDefinition";
    public static String TRANSITION_RESTRICTION_TABLE_NAME = "TransitionRestriction";
    public static String TRANSITION_REFERENCE_TABLE_NAME = "TransitionReference";
    public static String WEB_APP_TABLE_NAME = "WebApplication";
    public static String WEBSERVICES_APP_TABLE_NAME = "WebServicesApplication";
    public static String SESSIONBEAN_APP_TABLE_NAME = "SessionBeanApplication";
    public static String SCRIPTING_APP_TABLE_NAME = "ScriptingApplication";
    public static String MAIL_APP_TABLE_NAME = "MailApplication";
    public static String BASIC_TYPE_TABLE_NAME = "BasicType";
    public static String ATTACHMENT_TYPE_TABLE_NAME = "AttachmentType";
    public static String FORM_TYPE_TABLE_NAME = "FormType";

    public String getTableName(){
        return ProcessPackage.PROCESS_PACKAGE_TABLE_NAME;
    }
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
    private Collection processDefOIDs;
    public Collection getProcessDefOIDs(){
        if (this.processDefOIDs == null){
            this.processDefOIDs = new HashSet();
        }
        return this.processDefOIDs;
    }
    public void setProcessDefOIDs(Collection pNewValue){
        this.processDefOIDs = pNewValue;
    }
    public void addProcessDefOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getProcessDefOIDs().add(pNewValue);
    }
    private Collection processViewInformationOIDs;
    public Collection getProcessViewInformationOIDs(){
        if (this.processViewInformationOIDs == null){
            this.processViewInformationOIDs = new HashSet();
        }
        return this.processViewInformationOIDs;
    }
    public void setProcessViewInformationOIDs(Collection pNewValue){
        this.processViewInformationOIDs = pNewValue;
    }
    public void addProcessViewInformationOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getProcessViewInformationOIDs().add(pNewValue);
    }
    private Collection activitySetDefinitionOIDs;
    public Collection getActivitySetDefinitionOIDs(){
        if (this.activitySetDefinitionOIDs == null){
            this.activitySetDefinitionOIDs = new HashSet();
        }
        return this.activitySetDefinitionOIDs;
    }
    public void setActivitySetDefinitionOIDs(Collection pNewValue){
        this.activitySetDefinitionOIDs = pNewValue;
    }
    public void addActivitySetDefinitionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getActivitySetDefinitionOIDs().add(pNewValue);
    }
    private Collection activityDefinitionOIDs;
    public Collection getActivityDefinitionOIDs(){
        if (this.activityDefinitionOIDs == null){
            this.activityDefinitionOIDs = new HashSet();
        }
        return this.activityDefinitionOIDs;
    }
    public void setActivityDefinitionOIDs(Collection pNewValue){
        this.activityDefinitionOIDs = pNewValue;
    }
    public void addActivityDefinitionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getActivityDefinitionOIDs().add(pNewValue);
    }
    private Collection activityTypeOIDs;
    public Collection getActivityTypeOIDs(){
        if (this.activityTypeOIDs == null){
            this.activityTypeOIDs = new HashSet();
        }
        return this.activityTypeOIDs;
    }
    public void setActivityTypeOIDs(Collection pNewValue){
        this.activityTypeOIDs = pNewValue;
    }
    public void addActivityTypeOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getActivityTypeOIDs().add(pNewValue);
    }
    private Collection decisionRuleListOIDs;
    public Collection getDecisionRuleListOIDs(){
        if (this.decisionRuleListOIDs == null){
            this.decisionRuleListOIDs = new HashSet();
        }
        return this.decisionRuleListOIDs;
    }
    public void setDecisionRuleListOIDs(Collection pNewValue){
        this.decisionRuleListOIDs = pNewValue;
    }
    public void addDecisionRuleListOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getDecisionRuleListOIDs().add(pNewValue);
    }
    private Collection boundViewInformationOIDs;
    public Collection getBoundViewInformationOIDs(){
        if (this.boundViewInformationOIDs == null){
            this.boundViewInformationOIDs = new HashSet();
        }
        return this.boundViewInformationOIDs;
    }
    public void setBoundViewInformationOIDs(Collection pNewValue){
        this.boundViewInformationOIDs = pNewValue;
    }
    public void addBoundViewInformationOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getBoundViewInformationOIDs().add(pNewValue);
    }
    private Collection formFieldAccessDefinitionOIDs;
    public Collection getFormFieldAccessDefinitionOIDs(){
        if (this.formFieldAccessDefinitionOIDs == null){
            this.formFieldAccessDefinitionOIDs = new HashSet();
        }
        return this.formFieldAccessDefinitionOIDs;
    }
    public void setFormFieldAccessDefinitionOIDs(Collection pNewValue){
        this.formFieldAccessDefinitionOIDs = pNewValue;
    }
    public void addFormFieldAccessDefinitionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getFormFieldAccessDefinitionOIDs().add(pNewValue);
    }
    private Collection blockActivityOIDs;
    public Collection getBlockActivityOIDs(){
        if (this.blockActivityOIDs == null){
            this.blockActivityOIDs = new HashSet();
        }
        return this.blockActivityOIDs;
    }
    public void setBlockActivityOIDs(Collection pNewValue){
        this.blockActivityOIDs = pNewValue;
    }
    public void addBlockActivityOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getBlockActivityOIDs().add(pNewValue);
    }
    private Collection routeOIDs;
    public Collection getRouteOIDs(){
        if (this.routeOIDs == null){
            this.routeOIDs = new HashSet();
        }
        return this.routeOIDs;
    }
    public void setRouteOIDs(Collection pNewValue){
        this.routeOIDs = pNewValue;
    }
    public void addRouteOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getRouteOIDs().add(pNewValue);
    }
    private Collection implementationOIDs;
    public Collection getImplementationOIDs(){
        if (this.implementationOIDs == null){
            this.implementationOIDs = new HashSet();
        }
        return this.implementationOIDs;
    }
    public void setImplementationOIDs(Collection pNewValue){
        this.implementationOIDs = pNewValue;
    }
    public void addImplementationOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getImplementationOIDs().add(pNewValue);
    }
    private Collection decisionConditionOIDs;
    public Collection getDecisionConditionOIDs(){
        if (this.decisionConditionOIDs == null){
            this.decisionConditionOIDs = new HashSet();
        }
        return this.decisionConditionOIDs;
    }
    public void setDecisionConditionOIDs(Collection pNewValue){
        this.decisionConditionOIDs = pNewValue;
    }
    public void addDecisionConditionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getDecisionConditionOIDs().add(pNewValue);
    }
    private Collection decisionLevelOIDs;
    public Collection getDecisionLevelOIDs(){
        if (this.decisionLevelOIDs == null){
            this.decisionLevelOIDs = new HashSet();
        }
        return this.decisionLevelOIDs;
    }
    public void setDecisionLevelOIDs(Collection pNewValue){
        this.decisionLevelOIDs = pNewValue;
    }
    public void addDecisionLevelOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getDecisionLevelOIDs().add(pNewValue);
    }
    private Collection decisionRuleOIDs;
    public Collection getDecisionRuleOIDs(){
        if (this.decisionRuleOIDs == null){
            this.decisionRuleOIDs = new HashSet();
        }
        return this.decisionRuleOIDs;
    }
    public void setDecisionRuleOIDs(Collection pNewValue){
        this.decisionRuleOIDs = pNewValue;
    }
    public void addDecisionRuleOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getDecisionRuleOIDs().add(pNewValue);
    }
    private Collection toolOIDs;
    public Collection getToolOIDs(){
        if (this.toolOIDs == null){
            this.toolOIDs = new HashSet();
        }
        return this.toolOIDs;
    }
    public void setToolOIDs(Collection pNewValue){
        this.toolOIDs = pNewValue;
    }
    public void addToolOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getToolOIDs().add(pNewValue);
    }
    private Collection formOperationDefinitionOIDs;
    public Collection getFormOperationDefinitionOIDs(){
        if (this.formOperationDefinitionOIDs == null){
            this.formOperationDefinitionOIDs = new HashSet();
        }
        return this.formOperationDefinitionOIDs;
    }
    public void setFormOperationDefinitionOIDs(Collection pNewValue){
        this.formOperationDefinitionOIDs = pNewValue;
    }
    public void addFormOperationDefinitionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getFormOperationDefinitionOIDs().add(pNewValue);
    }
    private Collection relevantDataDefinitionOIDs;
    public Collection getRelevantDataDefinitionOIDs(){
        if (this.relevantDataDefinitionOIDs == null){
            this.relevantDataDefinitionOIDs = new HashSet();
        }
        return this.relevantDataDefinitionOIDs;
    }
    public void setRelevantDataDefinitionOIDs(Collection pNewValue){
        this.relevantDataDefinitionOIDs = pNewValue;
    }
    public void addRelevantDataDefinitionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getRelevantDataDefinitionOIDs().add(pNewValue);
    }
    private Collection dataTypeOIDs;
    public Collection getDataTypeOIDs(){
        if (this.dataTypeOIDs == null){
            this.dataTypeOIDs = new HashSet();
        }
        return this.dataTypeOIDs;
    }
    public void setDataTypeOIDs(Collection pNewValue){
        this.dataTypeOIDs = pNewValue;
    }
    public void addDataTypeOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getDataTypeOIDs().add(pNewValue);
    }
    private Collection applicationDefinitionOIDs;
    public Collection getApplicationDefinitionOIDs(){
        if (this.applicationDefinitionOIDs == null){
            this.applicationDefinitionOIDs = new HashSet();
        }
        return this.applicationDefinitionOIDs;
    }
    public void setApplicationDefinitionOIDs(Collection pNewValue){
        this.applicationDefinitionOIDs = pNewValue;
    }
    public void addApplicationDefinitionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getApplicationDefinitionOIDs().add(pNewValue);
    }
    private Collection formalParameterOIDs;
    public Collection getFormalParameterOIDs(){
        if (this.formalParameterOIDs == null){
            this.formalParameterOIDs = new HashSet();
        }
        return this.formalParameterOIDs;
    }
    public void setFormalParameterOIDs(Collection pNewValue){
        this.formalParameterOIDs = pNewValue;
    }
    public void addFormalParameterOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getFormalParameterOIDs().add(pNewValue);
    }
    private Collection transitionDefinitionOIDs;
    public Collection getTransitionDefinitionOIDs(){
        if (this.transitionDefinitionOIDs == null){
            this.transitionDefinitionOIDs = new HashSet();
        }
        return this.transitionDefinitionOIDs;
    }
    public void setTransitionDefinitionOIDs(Collection pNewValue){
        this.transitionDefinitionOIDs = pNewValue;
    }
    public void addTransitionDefinitionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getTransitionDefinitionOIDs().add(pNewValue);
    }
    private Collection conditionDefinitionOIDs;
    public Collection getConditionDefinitionOIDs(){
        if (this.conditionDefinitionOIDs == null){
            this.conditionDefinitionOIDs = new HashSet();
        }
        return this.conditionDefinitionOIDs;
    }
    public void setConditionDefinitionOIDs(Collection pNewValue){
        this.conditionDefinitionOIDs = pNewValue;
    }
    public void addConditionDefinitionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getConditionDefinitionOIDs().add(pNewValue);
    }
    private Collection transitionRestrictionOIDs;
    public Collection getTransitionRestrictionOIDs(){
        if (this.transitionRestrictionOIDs == null){
            this.transitionRestrictionOIDs = new HashSet();
        }
        return this.transitionRestrictionOIDs;
    }
    public void setTransitionRestrictionOIDs(Collection pNewValue){
        this.transitionRestrictionOIDs = pNewValue;
    }
    public void addTransitionRestrictionOID(String pNewValue){
        if (pNewValue != null && !"".equals(pNewValue))
            this.getTransitionRestrictionOIDs().add(pNewValue);
    }

    public ProcessPackage(String pOID) {
        this.setOID(pOID);
    }
    /**
     * 取回ProcessPackage 和ProcessDefinition OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypePackageAndDefOIDs(){
        StringBuffer tRtn = new StringBuffer();

        Iterator tIterOIDs = this.getProcessDefOIDs().iterator();
        while (tIterOIDs.hasNext()){
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            tRtn.append(",");
        }
        //package OID
        tRtn.append("'").append(this.getOID()).append("'");
        if (tRtn.length() != 0){
            return tRtn.toString();
        } else {
            return null;
        }
    }
    public Set getPackageAndDefOIDs(){
        Set tRtn = new HashSet();
        tRtn.addAll(this.getProcessDefOIDs());
        //package OID
        tRtn.add(this.getOID());
         return tRtn;

    }
    /**
     * 取回ProcessDefinition OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeProcessDefOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getProcessDefOIDs().size();
        Iterator tIterOIDs = this.getProcessDefOIDs().iterator();
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
     * 取回TransitionDefinition OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeTransitionDefinitionOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getTransitionDefinitionOIDs().size();
        Iterator tIterOIDs = this.getTransitionDefinitionOIDs().iterator();
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
     * 取回ProcessDefinition 和 ActivitySetDefinition OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeActDefContainerOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getActivitySetDefinitionOIDs().size();
        Iterator tIterOIDs = this.getActivitySetDefinitionOIDs().iterator();
        int tCount = 0;
        while (tIterOIDs.hasNext()){
            tCount ++;
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            if (tCount != tSize){
                tRtn.append(",");
            }
        }
        tSize = this.getProcessDefOIDs().size();
        tIterOIDs = this.getProcessDefOIDs().iterator();
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
     * 取回ProcessDefinition 和 ActivitySetDefinition OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public Set getActDefContainerOIDs(){
        Set tRtn = new HashSet();
        tRtn.addAll(this.getActivitySetDefinitionOIDs());
        tRtn.addAll(this.getProcessDefOIDs());
        return tRtn;
    }
    /**
     * 取回ProcessDefinition 和 ApplicationDefinition OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeFormalParameterContainerOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getApplicationDefinitionOIDs().size();
        Iterator tIterOIDs = this.getApplicationDefinitionOIDs().iterator();
        int tCount = 0;
        while (tIterOIDs.hasNext()){
            tCount ++;
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            if (tCount != tSize){
                tRtn.append(",");
            }
        }
        tSize = this.getProcessDefOIDs().size();
        tIterOIDs = this.getProcessDefOIDs().iterator();
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
    public Set getFormalParameterContainerOIDs(){
        Set tRtn = new HashSet();
        tRtn.addAll(this.getApplicationDefinitionOIDs());
        tRtn.addAll(this.getProcessDefOIDs());
        return tRtn;
    }
    /**
     * 取回FormalParameter 和 RelevantDataDefinition OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeDataTyeContainerOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getFormalParameterOIDs().size();
        Iterator tIterOIDs = this.getFormalParameterOIDs().iterator();
        int tCount = 0;
        while (tIterOIDs.hasNext()){
            tCount ++;
            tRtn.append("'").append((String)tIterOIDs.next()).append("'");
            if (tCount != tSize){
                tRtn.append(",");
            }
        }
        tSize = this.getRelevantDataDefinitionOIDs().size();
        tIterOIDs = this.getRelevantDataDefinitionOIDs().iterator();
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
     * 取回ProcessViewInformation OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeProcessViewInformationOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getProcessViewInformationOIDs().size();
        Iterator tIterOIDs = this.getProcessViewInformationOIDs().iterator();
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
     * 取回TransitionRestriction OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeTransitionRestrictionOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getTransitionRestrictionOIDs().size();
        Iterator tIterOIDs = this.getTransitionRestrictionOIDs().iterator();
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
     * 取回DataType OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeDataTypeOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getDataTypeOIDs().size();
        Iterator tIterOIDs = this.getDataTypeOIDs().iterator();
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
     * 取回ActivityDef OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeActivityDefOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getActivityDefinitionOIDs().size();
        Iterator tIterOIDs = this.getActivityDefinitionOIDs().iterator();
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
     * 取回DecisionCondition OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeDecisionConditionOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getDecisionConditionOIDs().size();
        Iterator tIterOIDs = this.getDecisionConditionOIDs().iterator();
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
     * 取回DecisionLevel OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeDecisionLevelOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getDecisionLevelOIDs().size();
        Iterator tIterOIDs = this.getDecisionLevelOIDs().iterator();
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
     * 取回Implementation OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeImplementationOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getImplementationOIDs().size();
        Iterator tIterOIDs = this.getImplementationOIDs().iterator();
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
     * 取回Tool OID 用字串組成的型態 如 'OID1', 'OID2', 主要是用來組SQL用的
     */
    public String getStringTypeToolOIDs(){
        StringBuffer tRtn = new StringBuffer();
        int tSize = this.getToolOIDs().size();
        Iterator tIterOIDs = this.getToolOIDs().iterator();
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
    public List generateDeleteCommandsWithoutPackage(){
        List tRtn = new ArrayList();
        StringBuffer tDeleteCommand = new StringBuffer();
        tDeleteCommand.append("Delete From ").append(this.PACKAGE_B_DEFINITION_TABLE_NAME).append(" Where ProcessPackageOID = '").append(
                this.getOID()).append("';");
        tRtn.add(tDeleteCommand.toString());
        StringBuffer tDeleteParticipantDefCommand = new StringBuffer();
        tDeleteParticipantDefCommand.append("Delete From ").append(this.PARTICIPANT_DEF_TABLE_NAME).append(" Where containerOID = '").append(
                this.getOID()).append("';");
        tRtn.add(tDeleteParticipantDefCommand.toString());
        int tSize = this.getProcessDefOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getProcessDefOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteParticipantDefCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.PROCESS_DEFINITION_TABLE_NAME).append(" Where OID in (");
            tDeleteParticipantDefCommand.append("Delete From ").append(this.PARTICIPANT_DEF_TABLE_NAME).append(" Where containerOID in (");
            String tOID = null;
            while (tIterOIDs.hasNext()){
                tCount ++;
                tOID = (String)tIterOIDs.next();
                tDeleteCommand.append("'").append(tOID).append("'");
                tDeleteParticipantDefCommand.append("'").append(tOID).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                    tDeleteParticipantDefCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tDeleteParticipantDefCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
            tRtn.add(tDeleteParticipantDefCommand.toString());
        }
        tSize = this.getActivityDefinitionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getActivityDefinitionOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.ACTIVITY_DEF_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getActivitySetDefinitionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getActivitySetDefinitionOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.ACTIVITY_SET_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getProcessViewInformationOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getProcessViewInformationOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.PROCESS_BOUND_VIEW_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getBoundViewInformationOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getBoundViewInformationOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.BOUND_VIEW_INFO_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getDecisionRuleListOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getDecisionRuleListOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.DECISION_RULE_LIST_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getFormFieldAccessDefinitionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getFormFieldAccessDefinitionOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.FORM_FIELD_ACCESS_DEF_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getBlockActivityOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getBlockActivityOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.BLOCK_ACT_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getImplementationOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getImplementationOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            StringBuffer tDeleteNosCommand = new StringBuffer();
            StringBuffer tDeleteSubflowCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.IMPLEMENTATION_TABLE_NAME).append(" Where OID in (");
            tDeleteNosCommand.append("Delete From ").append(this.NO_TABLE_NAME).append(" Where containerOID in (");
            tDeleteSubflowCommand.append("Delete From ").append(this.SUBFLOW_TABLE_NAME).append(" Where containerOID in (");
            String tOID = null;
            while (tIterOIDs.hasNext()){
                tCount ++;
                tOID = (String)tIterOIDs.next();
                tDeleteCommand.append("'").append(tOID).append("'");
                tDeleteNosCommand.append("'").append(tOID).append("'");
                tDeleteSubflowCommand.append("'").append(tOID).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                    tDeleteNosCommand.append(",");
                    tDeleteSubflowCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tDeleteNosCommand.append(");");
            tDeleteSubflowCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
            tRtn.add(tDeleteNosCommand.toString());
            tRtn.add(tDeleteSubflowCommand.toString());
        }
        tSize = this.getRouteOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getRouteOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.ROUTE_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getDecisionConditionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getDecisionConditionOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.DECISION_COND_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getDecisionLevelOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getDecisionLevelOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.DECISION_LEVEL_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getDecisionRuleOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getDecisionRuleOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.DECISION_RULE_TABLE_NAME).append(" Where OID in (");
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
        tSize = this.getToolOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getToolOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            StringBuffer tDeleteActualParameterCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.TOOL_TABLE_NAME).append(" Where OID in (");
            tDeleteActualParameterCommand.append("Delete From ").append(this.ACTUAL_PARAMETER_TABLE_NAME).append(" Where containerOID in (");
            String tOID = null;
            while (tIterOIDs.hasNext()){
                tCount ++;
                tOID = (String)tIterOIDs.next();
                tDeleteCommand.append("'").append(tOID).append("'");
                tDeleteActualParameterCommand.append("'").append(tOID).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                    tDeleteActualParameterCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tDeleteActualParameterCommand.append(");");

            tRtn.add(tDeleteCommand.toString());
            tRtn.add(tDeleteActualParameterCommand.toString());
        }
        tSize = this.getFormOperationDefinitionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getFormOperationDefinitionOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.FORM_OPER_DEF_TABLE_NAME).append(" Where OID in (");
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
        //產生RelevantDataDef的指令
        tSize = this.getRelevantDataDefinitionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getRelevantDataDefinitionOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.RELEVANT_DATA_DEF_TABLE_NAME).append(" Where OID in (");
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
        //FormalParameter
        tSize = this.getFormalParameterOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getFormalParameterOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.FORMALPARAMETER_TABLE_NAME).append(" Where OID in (");
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
        //產生Application的指令
        tSize = this.getApplicationDefinitionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getApplicationDefinitionOIDs().iterator();
            int tCount = 0;
            StringBuffer tDeleteWebApplicationCommand = new StringBuffer();
            StringBuffer tDeleteWebServicesApplicationCommand = new StringBuffer();
            StringBuffer tDeleteSessionBeanApplicationCommand = new StringBuffer();
            StringBuffer tDeleteScriptingApplicationCommand = new StringBuffer();
            StringBuffer tDeleteMailApplicationCommand = new StringBuffer();
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.APPLICATION_CONTAINER_B_APPLICATION_TABLE_NAME).append(" Where ApplicationDefinitionOID in (");
            tDeleteWebApplicationCommand.append("Delete From ").append(this.WEB_APP_TABLE_NAME).append(" Where OID in (");
            tDeleteWebServicesApplicationCommand.append("Delete From ").append(this.WEBSERVICES_APP_TABLE_NAME).append(" Where OID in (");
            tDeleteSessionBeanApplicationCommand.append("Delete From ").append(this.SESSIONBEAN_APP_TABLE_NAME).append(" Where OID in (");
            tDeleteScriptingApplicationCommand.append("Delete From ").append(this.SCRIPTING_APP_TABLE_NAME).append(" Where OID in (");
            tDeleteMailApplicationCommand.append("Delete From ").append(this.MAIL_APP_TABLE_NAME).append(" Where OID in (");
            String tOID = null;
            while (tIterOIDs.hasNext()){
                tCount ++;
                tOID = (String)tIterOIDs.next();
                tDeleteCommand.append("'").append(tOID).append("'");
                tDeleteWebApplicationCommand.append("'").append(tOID).append("'");
                tDeleteWebServicesApplicationCommand.append("'").append(tOID).append("'");
                tDeleteSessionBeanApplicationCommand.append("'").append(tOID).append("'");
                tDeleteScriptingApplicationCommand.append("'").append(tOID).append("'");
                tDeleteMailApplicationCommand.append("'").append(tOID).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                    tDeleteWebApplicationCommand.append(",");
                    tDeleteWebServicesApplicationCommand.append(",");
                    tDeleteSessionBeanApplicationCommand.append(",");
                    tDeleteScriptingApplicationCommand.append(",");
                    tDeleteMailApplicationCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tDeleteWebApplicationCommand.append(");");
            tDeleteWebServicesApplicationCommand.append(");");
            tDeleteSessionBeanApplicationCommand.append(");");
            tDeleteScriptingApplicationCommand.append(");");
            tDeleteMailApplicationCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
            tRtn.add(tDeleteWebApplicationCommand.toString());
            tRtn.add(tDeleteWebServicesApplicationCommand.toString());
            tRtn.add(tDeleteSessionBeanApplicationCommand.toString());
            tRtn.add(tDeleteScriptingApplicationCommand.toString());
            tRtn.add(tDeleteMailApplicationCommand.toString());
        }
        //產生 ConditionDefinition
        tSize = this.getConditionDefinitionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getConditionDefinitionOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.CONDITION_DEF_TABLE_NAME).append(" Where OID in (");
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
        //產生TransitionDefinition
        tSize = this.getTransitionDefinitionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getTransitionDefinitionOIDs().iterator();
            int tCount = 0;
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.TRANSITION_DEF_TABLE_NAME).append(" Where OID in (");
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
        //產生TransitionRestriction
        tSize = this.getTransitionRestrictionOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getTransitionRestrictionOIDs().iterator();
            int tCount = 0;
            StringBuffer tDeleteTransitionReferenceCommand = new StringBuffer();
            tDeleteCommand = new StringBuffer();
            tDeleteCommand.append("Delete From ").append(this.TRANSITION_RESTRICTION_TABLE_NAME).append(" Where OID in (");
            tDeleteTransitionReferenceCommand.append("Delete From ").append(this.TRANSITION_REFERENCE_TABLE_NAME).append(" Where containerOID in (");
            String tOID = null;
            while (tIterOIDs.hasNext()){
                tCount ++;
                tOID = (String)tIterOIDs.next();
                tDeleteCommand.append("'").append(tOID).append("'");
                tDeleteTransitionReferenceCommand.append("'").append(tOID).append("'");
                if (tCount != tSize){
                    tDeleteCommand.append(",");
                    tDeleteTransitionReferenceCommand.append(",");
                }
            }
            tDeleteCommand.append(");");
            tDeleteTransitionReferenceCommand.append(");");
            tRtn.add(tDeleteCommand.toString());
            tRtn.add(tDeleteTransitionReferenceCommand.toString());
        }
        //產生DataType
        tSize = this.getDataTypeOIDs().size();
        if (tSize > 0){
            Iterator tIterOIDs = this.getDataTypeOIDs().iterator();
            int tCount = 0;
            StringBuffer tDeleteBasicTypeCommand = new StringBuffer();
            StringBuffer tDeleteAttachmentTypeCommand = new StringBuffer();
            StringBuffer tDeleteFormTypeCommand = new StringBuffer();
            tDeleteBasicTypeCommand.append("Delete From ").append(this.BASIC_TYPE_TABLE_NAME).append(" Where OID in (");
            tDeleteAttachmentTypeCommand.append("Delete From ").append(this.ATTACHMENT_TYPE_TABLE_NAME).append(" Where OID in (");
            tDeleteFormTypeCommand.append("Delete From ").append(this.FORM_TYPE_TABLE_NAME).append(" Where OID in (");
            String tOID = null;
            while (tIterOIDs.hasNext()){
                tCount ++;
                tOID = (String)tIterOIDs.next();
                tDeleteBasicTypeCommand.append("'").append(tOID).append("'");
                tDeleteAttachmentTypeCommand.append("'").append(tOID).append("'");
                tDeleteFormTypeCommand.append("'").append(tOID).append("'");
                if (tCount != tSize){
                    tDeleteBasicTypeCommand.append(",");
                    tDeleteAttachmentTypeCommand.append(",");
                    tDeleteFormTypeCommand.append(",");
                }
            }
            tDeleteBasicTypeCommand.append(");");
            tDeleteAttachmentTypeCommand.append(");");
            tDeleteFormTypeCommand.append(");");
            tRtn.add(tDeleteBasicTypeCommand.toString());
            tRtn.add(tDeleteAttachmentTypeCommand.toString());
            tRtn.add(tDeleteFormTypeCommand.toString());
        }
        return tRtn;
    }
    public List generateDeleteCommands(){
        List tRtn = new ArrayList();
        StringBuffer tDeleteCommand = new StringBuffer();
        tDeleteCommand.append("Delete From ").append(this.PROCESS_PACKAGE_TABLE_NAME).append(" Where OID = '").append(
                this.getOID()).append("';");
        tRtn.add(tDeleteCommand.toString());
        tRtn.addAll(this.generateDeleteCommandsWithoutPackage());
        return tRtn;
    }
}