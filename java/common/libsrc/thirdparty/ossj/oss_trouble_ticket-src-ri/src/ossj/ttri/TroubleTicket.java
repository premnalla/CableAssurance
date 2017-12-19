package ossj.ttri;

import javax.ejb.EJBObject;
import javax.oss.trouble.*;
import java.rmi.RemoteException;

/**
 * TroubleTicket Entity Remote interface
 * Defines the business methods for the TroubleTicket Entity Bean
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public interface TroubleTicket extends EJBObject {

    //bulk accessor/mutator
    public TroubleTicketValue getAttributes(String[] attributeNames)
            throws java.rmi.RemoteException,
            javax.oss.IllegalArgumentException;


    public void setAttributes(TroubleTicketValue ttValIn, boolean resyncRequired)
            throws java.rmi.RemoteException,
            javax.oss.IllegalArgumentException,
            javax.oss.IllegalAttributeValueException,
            javax.oss.ResyncRequiredException;


    //--------------------------------------------------------------------------------------------------------------------
    // simple accessors
    //--------------------------------------------------------------------------------------------------------------------
    public TroubleTicketKey getTroubleTicketKey() throws java.rmi.RemoteException;

    public long getLastUpdateVersionNumber() throws java.rmi.RemoteException;

    public ActivityDurationList getActivityDurationList() throws java.rmi.RemoteException;

    public AlarmValueList getRelatedAlarmList() throws java.rmi.RemoteException;

    public String[] getAdditionalTroubleInfoList() throws java.rmi.RemoteException;

    public String getCloseOutNarr() throws java.rmi.RemoteException;

    public java.util.Date getReceivedTime() throws java.rmi.RemoteException;

    public TroubleTicketKey[] getRelatedTroubleTicketKeyList() throws java.rmi.RemoteException;

    public RepairActivityList getRepairActivityList() throws java.rmi.RemoteException;

    public java.util.Date getRestoredTime() throws java.rmi.RemoteException;

    public SPRoleAssignment[] getSPRoleAssignmentList() throws java.rmi.RemoteException;

    public String getTroubleDescription() throws java.rmi.RemoteException;

    public PersonReach getClearancePerson() throws java.rmi.RemoteException;

    public int getTroubleFound() throws java.rmi.RemoteException;

    public String getTroubleLocation() throws java.rmi.RemoteException;

    public String[] getTroubleNumList() throws java.rmi.RemoteException;

    public String getTroubledObject() throws java.rmi.RemoteException;

    public int getTroubleType() throws java.rmi.RemoteException;

    public int getTroubleState() throws java.rmi.RemoteException;

    public int getTroubleStatus() throws java.rmi.RemoteException;

    public java.util.Date getTroubleStatusTime() throws java.rmi.RemoteException;

    public boolean getAfterHoursRepairAuthority() throws java.rmi.RemoteException;

    public Authorization[] getAuthorizationList() throws java.rmi.RemoteException;

    public boolean getCancelRequestedByCustomer() throws java.rmi.RemoteException;

    public int getCloseOutVerification() throws java.rmi.RemoteException;

    public java.util.Date getCommitmentTime() throws java.rmi.RemoteException;

    public java.util.Date getCommitmentTimeRequested() throws java.rmi.RemoteException;

    public CustomerRoleAssignment[] getCustomerRoleAssignmentList() throws java.rmi.RemoteException;

    public String getCustomerTroubleNum() throws java.rmi.RemoteException;

    public String getDialog() throws java.rmi.RemoteException;

    public EscalationList getEscalationList() throws java.rmi.RemoteException;

    public int getInitiatingMode() throws java.rmi.RemoteException;

    public java.util.Date getLastUpdateTime() throws java.rmi.RemoteException;

    public boolean getMaintServiceCharge() throws java.rmi.RemoteException;

    public TimeLength getOutageDuration() throws java.rmi.RemoteException;

    public int getPerceivedTroubleSeverity() throws java.rmi.RemoteException;

    public int getPreferredPriority() throws java.rmi.RemoteException;

    public int getRepeatReport() throws java.rmi.RemoteException;

    public SuspectObject[] getSuspectObjectList() throws java.rmi.RemoteException;

    public java.util.Date getTroubleDetectionTime() throws java.rmi.RemoteException;

    public TroubleLocationInfo[] getTroubleLocationInfoList() throws java.rmi.RemoteException;

    public java.util.Date getTroubledObjectAccessFromTime() throws java.rmi.RemoteException;

    public AccessHours[] getTroubledObjectAccessHoursList() throws java.rmi.RemoteException;

    public java.util.Date getTroubledObjectAccessToTime() throws java.rmi.RemoteException;

    public java.util.Date getServiceUnavailableBeginTime() throws java.rmi.RemoteException;

    public java.util.Date getServiceUnavailableEndTime() throws java.rmi.RemoteException;

    public String getOriginator() throws java.rmi.RemoteException;

    public String getTroubleSystemDN() throws java.rmi.RemoteException;

    public PersonReach getCustomer() throws java.rmi.RemoteException;

    public PersonReach getAccountOwner() throws java.rmi.RemoteException;


    //------------------------------------------------------------------------------
    // simple mutators
    //------------------------------------------------------------------------------
    public void setTroubleTicketKey(TroubleTicketKey value) throws java.rmi.RemoteException;

    public void setActivityDurationList(ActivityDurationList value) throws java.rmi.RemoteException;

    public void setRelatedAlarmList(AlarmValueList value) throws java.rmi.RemoteException;

    public void setAdditionalTroubleInfoList(String[] value) throws java.rmi.RemoteException;

    public void setCloseOutNarr(String value) throws java.rmi.RemoteException;

    public void setReceivedTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setRelatedTroubleTicketKeyList(TroubleTicketKey[] value) throws java.rmi.RemoteException;

    public void setRepairActivityList(RepairActivityList value) throws java.rmi.RemoteException;

    public void setRestoredTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setSPRoleAssignmentList(SPRoleAssignment[] value) throws java.rmi.RemoteException;

    public void setTroubleDescription(String value) throws java.rmi.RemoteException;

    public void setClearancePerson(PersonReach value) throws java.rmi.RemoteException;

    public void setTroubleFound(int value) throws java.rmi.RemoteException;

    public void setTroubleLocation(String value) throws java.rmi.RemoteException;

    public void setTroubleNumList(String[] value) throws java.rmi.RemoteException;

    public void setTroubledObject(String value) throws java.rmi.RemoteException;

    public void setTroubleType(int value) throws java.rmi.RemoteException;

    public void setTroubleState(int value) throws java.rmi.RemoteException;

    public void setTroubleStatus(int value) throws java.rmi.RemoteException;

    public void setTroubleStatusTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setAfterHoursRepairAuthority(boolean value) throws java.rmi.RemoteException;

    public void setAuthorizationList(Authorization[] value) throws java.rmi.RemoteException;

    public void setCancelRequestedByCustomer(boolean value) throws java.rmi.RemoteException;

    public void setCloseOutVerification(int value) throws java.rmi.RemoteException;

    public void setCommitmentTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setCommitmentTimeRequested(java.util.Date value) throws java.rmi.RemoteException;

    public void setCustomerRoleAssignmentList(CustomerRoleAssignment[] value) throws java.rmi.RemoteException;

    public void setCustomerTroubleNum(String value) throws java.rmi.RemoteException;

    public void setDialog(String value) throws java.rmi.RemoteException;

    public void setEscalationList(EscalationList value) throws java.rmi.RemoteException;

    public void setInitiatingMode(int value) throws java.rmi.RemoteException;

    public void setLastUpdateTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setMaintServiceCharge(boolean value) throws java.rmi.RemoteException;

    public void setOutageDuration(TimeLength value) throws java.rmi.RemoteException;

    public void setPerceivedTroubleSeverity(int value) throws java.rmi.RemoteException;

    public void setPreferredPriority(int value) throws java.rmi.RemoteException;

    public void setRepeatReport(int value) throws java.rmi.RemoteException;

    public void setSuspectObjectList(SuspectObject[] value) throws java.rmi.RemoteException;

    public void setTroubleDetectionTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setTroubleLocationInfoList(TroubleLocationInfo[] value) throws java.rmi.RemoteException;

    public void setTroubledObjectAccessFromTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setTroubledObjectAccessHoursList(AccessHours[] value) throws java.rmi.RemoteException;

    public void setTroubledObjectAccessToTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setServiceUnavailableBeginTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setServiceUnavailableEndTime(java.util.Date value) throws java.rmi.RemoteException;

    public void setOriginator(String value) throws java.rmi.RemoteException;

    public void setTroubleSystemDN(String value) throws java.rmi.RemoteException;

    public void setCustomer(PersonReach value) throws java.rmi.RemoteException;

    public void setAccountOwner(PersonReach value) throws java.rmi.RemoteException;

    //--------------------------------------------------------------
    // Business methods
    //--------------------------------------------------------------
    public void closeTroubleTicket(int closeOutStatus, String closeOutNarr)
            throws RemoteException,
            CloseException;

    public void cancelTroubleTicket(int closeOutStatus, String closeOutNarr)
            throws RemoteException,
            CancelException;

    public void escalateTroubleTicket(EscalationList escalation)
            throws RemoteException,
            EscalateException;

}


