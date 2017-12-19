package ossj.ttri;

import javax.oss.trouble.*;

/**
 * TroubleTicketAttributes Data Implementation Class
 * Used by the TroubleTicketValue class and the Trouble Ticket Entity Bean
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class TroubleTicketAttributes implements java.io.Serializable {
    //-----------------------------------------------------------------------
    // Note: following "List" types have Multi-Value Attributes semantics:
    //
    // ActivityDurationList
    // AlarmValueList
    // RepairActivityList
    // EscalationList
    //-----------------------------------------------------------------------
    private long lastUpdateNumber;
    private ActivityDurationList activityDurationList;
    private AlarmValueList relatedAlarmList;
    private String[] additionalTroubleInfoList;
    private String closeOutNarr;
    private java.util.Date receivedTime;
    private TroubleTicketKey[] relatedTroubleTicketKeyList;
    private RepairActivityList repairActivityList;
    private java.util.Date restoredTime;
    private SPRoleAssignment[] sPRoleAssignmentList;
    private String troubleDescription;
    private PersonReach clearancePerson;
    private int troubleFound;
    private String troubleLocation;
    private String[] troubleNumList;
    private String troubledObject;
    private int troubleType;
    private int troubleState;
    private int troubleStatus;
    private java.util.Date troubleStatusTime;
    private boolean afterHoursRepairAuthority;
    private Authorization[] authorizationList;
    private boolean cancelRequestedByCustomer;
    private int closeOutVerification;
    private java.util.Date commitmentTime;
    private java.util.Date commitmentTimeRequested;
    private CustomerRoleAssignment[] customerRoleAssignmentList;
    private String customerTroubleNum;
    private String dialog;
    private EscalationList escalationList;
    private int initiatingMode;
    private java.util.Date lastUpdateTime;
    private boolean maintServiceCharge;
    private TimeLength outageDuration;
    private int perceivedTroubleSeverity;
    private int preferredPriority;
    private int repeatReport;
    private SuspectObject[] suspectObjectList;
    private java.util.Date troubleDetectionTime;
    private TroubleLocationInfo[] troubleLocationInfoList;
    private java.util.Date troubledObjectAccessFromTime;
    private AccessHours[] troubledObjectAccessHoursList;
    private java.util.Date troubledObjectAccessToTime;
    private java.util.Date serviceUnavailableBeginTime;
    private java.util.Date serviceUnavailableEndTime;
    private String originator;
    private String troubleSystemDN;
    private PersonReach customer;
    private PersonReach accountOwner;


    //---------------------------------------------------------------
    // list of attribute names
    //---------------------------------------------------------------
    public static final String[] TTAttributeNames =
            {TroubleTicketValue.ACTIVITYDURATIONLIST,
             TroubleTicketValue.RELATEDALARMLIST,
             TroubleTicketValue.ADDITIONALTROUBLEINFOLIST,
             TroubleTicketValue.CLOSEOUTNARR,
             TroubleTicketValue.RECEIVEDTIME,
             TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST,
             TroubleTicketValue.REPAIRACTIVITYLIST,
             TroubleTicketValue.RESTOREDTIME,
             TroubleTicketValue.SPROLEASSIGNMENTLIST,
             TroubleTicketValue.TROUBLEDESCRIPTION,
             TroubleTicketValue.CLEARANCEPERSON,
             TroubleTicketValue.TROUBLEFOUND,
             TroubleTicketValue.TROUBLELOCATION,
             TroubleTicketValue.TROUBLENUMLIST,
             TroubleTicketValue.TROUBLEDOBJECT,
             TroubleTicketValue.TROUBLETYPE,
             TroubleTicketValue.TROUBLESTATE,
             TroubleTicketValue.TROUBLESTATUS,
             TroubleTicketValue.TROUBLESTATUSTIME,
             TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY,
             TroubleTicketValue.AUTHORIZATIONLIST,
             TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER,
             TroubleTicketValue.CLOSEOUTVERIFICATION,
             TroubleTicketValue.COMMITMENTTIME,
             TroubleTicketValue.COMMITMENTTIMEREQUESTED,
             TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST,
             TroubleTicketValue.CUSTOMERTROUBLENUM,
             TroubleTicketValue.DIALOG,
             TroubleTicketValue.ESCALATIONLIST,
             TroubleTicketValue.INITIATINGMODE,
             TroubleTicketValue.LASTUPDATETIME,
             TroubleTicketValue.MAINTSERVICECHARGE,
             TroubleTicketValue.OUTAGEDURATION,
             TroubleTicketValue.PERCEIVEDTROUBLESEVERITY,
             TroubleTicketValue.PREFERREDPRIORITY,
             TroubleTicketValue.REPEATREPORT,
             TroubleTicketValue.SUSPECTOBJECTLIST,
             TroubleTicketValue.TROUBLEDETECTIONTIME,
             TroubleTicketValue.TROUBLELOCATIONINFOLIST,
             TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME,
             TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST,
             TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME,
             TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME,
             TroubleTicketValue.SERVICEUNAVAILABLEENDTIME,
             TroubleTicketValue.ORIGINATOR,
             TroubleTicketValue.TROUBLESYSTEMDN,
             TroubleTicketValue.CUSTOMER,
             TroubleTicketValue.ACCOUNTOWNER
            };


    //Constructor
    public TroubleTicketAttributes() {
        this.lastUpdateNumber = -1;
        this.activityDurationList = null;
        this.relatedAlarmList = null;
        this.additionalTroubleInfoList = null;
        this.closeOutNarr = null;
        this.receivedTime = null;
        this.relatedTroubleTicketKeyList = null;
        this.repairActivityList = null;
        this.restoredTime = null;
        this.sPRoleAssignmentList = null;
        this.troubleDescription = null;
        this.clearancePerson = null;
        this.troubleFound = -1;
        this.troubleLocation = null;
        this.troubleNumList = null;
        this.troubledObject = null;
        this.troubleType = -1;
        this.troubleState = -1;
        this.troubleStatus = -1;
        this.troubleStatusTime = null;
        this.afterHoursRepairAuthority = false;
        this.authorizationList = null;
        this.cancelRequestedByCustomer = false;
        this.closeOutVerification = -1;
        this.commitmentTime = null;
        this.commitmentTimeRequested = null;
        this.customerRoleAssignmentList = null;
        this.customerTroubleNum = null;
        this.dialog = null;
        this.escalationList = null;
        this.initiatingMode = -1;
        this.lastUpdateTime = null;
        this.maintServiceCharge = false;
        this.outageDuration = null;
        this.perceivedTroubleSeverity = -1;
        this.preferredPriority = -1;
        this.repeatReport = -1;
        this.suspectObjectList = null;
        this.troubleDetectionTime = null;
        this.troubleLocationInfoList = null;
        this.troubledObjectAccessFromTime = null;
        this.troubledObjectAccessHoursList = null;
        this.troubledObjectAccessToTime = null;
        this.serviceUnavailableBeginTime = null;
        this.serviceUnavailableEndTime = null;
        this.originator = null;
        this.troubleSystemDN = null;
        this.customer = null;
        this.accountOwner = null;

    }

    //accessors
    public long getLastUpdateNumber() {
        return lastUpdateNumber;
    }

    public ActivityDurationList getActivityDurationList() {
        return activityDurationList;
    }

    public AlarmValueList getRelatedAlarmList() {
        return relatedAlarmList;
    }

    public String[] getAdditionalTroubleInfoList() {
        return additionalTroubleInfoList;
    }

    public String getCloseOutNarr() {
        return closeOutNarr;
    }

    public java.util.Date getReceivedTime() {
        return receivedTime;
    }

    public TroubleTicketKey[] getRelatedTroubleTicketKeyList() {
        return relatedTroubleTicketKeyList;
    }

    public RepairActivityList getRepairActivityList() {
        return repairActivityList;
    }

    public java.util.Date getRestoredTime() {
        return restoredTime;
    }

    public SPRoleAssignment[] getSPRoleAssignmentList() {
        return sPRoleAssignmentList;
    }

    public String getTroubleDescription() {
        return troubleDescription;
    }

    public PersonReach getClearancePerson() {
        return clearancePerson;
    }

    public int getTroubleFound() {
        return troubleFound;
    }

    public String getTroubleLocation() {
        return troubleLocation;
    }

    public String[] getTroubleNumList() {
        return troubleNumList;
    }

    public String getTroubledObject() {
        return troubledObject;
    }

    public int getTroubleType() {
        return troubleType;
    }

    public int getTroubleState() {
        return troubleState;
    }

    public int getTroubleStatus() {
        return troubleStatus;
    }

    public java.util.Date getTroubleStatusTime() {
        return troubleStatusTime;
    }

    public boolean getAfterHoursRepairAuthority() {
        return afterHoursRepairAuthority;
    }

    public Authorization[] getAuthorizationList() {
        return authorizationList;
    }

    public boolean getCancelRequestedByCustomer() {
        return cancelRequestedByCustomer;
    }

    public int getCloseOutVerification() {
        return closeOutVerification;
    }

    public java.util.Date getCommitmentTime() {
        return commitmentTime;
    }

    public java.util.Date getCommitmentTimeRequested() {
        return commitmentTimeRequested;
    }

    public CustomerRoleAssignment[] getCustomerRoleAssignmentList() {
        return customerRoleAssignmentList;
    }

    public String getCustomerTroubleNum() {
        return customerTroubleNum;
    }

    public String getDialog() {
        return dialog;
    }

    public EscalationList getEscalationList() {
        return escalationList;
    }

    public int getInitiatingMode() {
        return initiatingMode;
    }

    public java.util.Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public boolean getMaintServiceCharge() {
        return maintServiceCharge;
    }

    public TimeLength getOutageDuration() {
        return outageDuration;
    }

    public int getPerceivedTroubleSeverity() {
        return perceivedTroubleSeverity;
    }

    public int getPreferredPriority() {
        return preferredPriority;
    }

    public int getRepeatReport() {
        return repeatReport;
    }

    public SuspectObject[] getSuspectObjectList() {
        return suspectObjectList;
    }

    public java.util.Date getTroubleDetectionTime() {
        return troubleDetectionTime;
    }

    public TroubleLocationInfo[] getTroubleLocationInfoList() {
        return troubleLocationInfoList;
    }

    public java.util.Date getTroubledObjectAccessFromTime() {
        return troubledObjectAccessFromTime;
    }

    public AccessHours[] getTroubledObjectAccessHoursList() {
        return troubledObjectAccessHoursList;
    }

    public java.util.Date getTroubledObjectAccessToTime() {
        return troubledObjectAccessToTime;
    }

    public java.util.Date getServiceUnavailableBeginTime() {
        return serviceUnavailableBeginTime;
    }

    public java.util.Date getServiceUnavailableEndTime() {
        return serviceUnavailableEndTime;
    }

    public String getOriginator() {
        return originator;
    }

    public String getTroubleSystemDN() {
        return troubleSystemDN;
    }

    public PersonReach getCustomer() {
        return customer;
    }

    public PersonReach getAccountOwner() {
        return accountOwner;
    }


    //mutators
    public void setLastUpdateNumber(long value) {
        lastUpdateNumber = value;
    }

    public void setActivityDurationList(ActivityDurationList value) {
        activityDurationList = value;
    }

    public void setRelatedAlarmList(AlarmValueList value) {
        relatedAlarmList = value;
    }

    public void setAdditionalTroubleInfoList(String[] value) {
        additionalTroubleInfoList = value;
    }

    public void setCloseOutNarr(String value) {
        closeOutNarr = value;
    }

    public void setReceivedTime(java.util.Date value) {
        receivedTime = value;
    }

    public void setRelatedTroubleTicketKeyList(TroubleTicketKey[] value) {
        relatedTroubleTicketKeyList = value;
    }

    public void setRepairActivityList(RepairActivityList value) {
        repairActivityList = value;
    }

    public void setRestoredTime(java.util.Date value) {
        restoredTime = value;
    }

    public void setSPRoleAssignmentList(SPRoleAssignment[] value) {
        sPRoleAssignmentList = value;
    }

    public void setTroubleDescription(String value) {
        troubleDescription = value;
    }

    public void setClearancePerson(PersonReach value) {
        clearancePerson = value;
    }

    public void setTroubleFound(int value) {
        troubleFound = value;
    }

    public void setTroubleLocation(String value) {
        troubleLocation = value;
    }

    public void setTroubleNumList(String[] value) {
        troubleNumList = value;
    }

    public void setTroubledObject(String value) {
        troubledObject = value;
    }

    public void setTroubleType(int value) {
        troubleType = value;
    }

    public void setTroubleState(int value) {
        troubleState = value;
    }

    public void setTroubleStatus(int value) {
        troubleStatus = value;
    }

    public void setTroubleStatusTime(java.util.Date value) {
        troubleStatusTime = value;
    }

    public void setAfterHoursRepairAuthority(boolean value) {
        afterHoursRepairAuthority = value;
    }

    public void setAuthorizationList(Authorization[] value) {
        authorizationList = value;
    }

    public void setCancelRequestedByCustomer(boolean value) {
        cancelRequestedByCustomer = value;
    }

    public void setCloseOutVerification(int value) {
        closeOutVerification = value;
    }

    public void setCommitmentTime(java.util.Date value) {
        commitmentTime = value;
    }

    public void setCommitmentTimeRequested(java.util.Date value) {
        commitmentTimeRequested = value;
    }

    public void setCustomerRoleAssignmentList(CustomerRoleAssignment[] value) {
        customerRoleAssignmentList = value;
    }

    public void setCustomerTroubleNum(String value) {
        customerTroubleNum = value;
    }

    public void setDialog(String value) {
        dialog = value;
    }

    public void setEscalationList(EscalationList value) {
        escalationList = value;
    }

    public void setInitiatingMode(int value) {
        initiatingMode = value;
    }

    public void setLastUpdateTime(java.util.Date value) {
        lastUpdateTime = value;
    }

    public void setMaintServiceCharge(boolean value) {
        maintServiceCharge = value;
    }

    public void setOutageDuration(TimeLength value) {
        outageDuration = value;
    }

    public void setPerceivedTroubleSeverity(int value) {
        perceivedTroubleSeverity = value;
    }

    public void setPreferredPriority(int value) {
        preferredPriority = value;
    }

    public void setRepeatReport(int value) {
        repeatReport = value;
    }

    public void setSuspectObjectList(SuspectObject[] value) {
        suspectObjectList = value;
    }

    public void setTroubleDetectionTime(java.util.Date value) {
        troubleDetectionTime = value;
    }

    public void setTroubleLocationInfoList(TroubleLocationInfo[] value) {
        troubleLocationInfoList = value;
    }

    public void setTroubledObjectAccessFromTime(java.util.Date value) {
        troubledObjectAccessFromTime = value;
    }

    public void setTroubledObjectAccessHoursList(AccessHours[] value) {
        troubledObjectAccessHoursList = value;
    }

    public void setTroubledObjectAccessToTime(java.util.Date value) {
        troubledObjectAccessToTime = value;
    }

    public void setServiceUnavailableBeginTime(java.util.Date value) {
        serviceUnavailableBeginTime = value;
    }

    public void setServiceUnavailableEndTime(java.util.Date value) {
        serviceUnavailableEndTime = value;
    }

    public void setOriginator(String value) {
        originator = value;
    }

    public void setTroubleSystemDN(String value) {
        troubleSystemDN = value;
    }

    public void setCustomer(PersonReach value) {
        customer = value;
    }

    public void setAccountOwner(PersonReach value) {
        accountOwner = value;
    }

}














