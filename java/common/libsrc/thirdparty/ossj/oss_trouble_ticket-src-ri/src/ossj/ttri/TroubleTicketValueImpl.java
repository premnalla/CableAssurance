package ossj.ttri;

import ossj.fmri.AlarmValueImpl;

import javax.oss.IllegalArgumentException;
import javax.oss.*;
import javax.oss.IllegalStateException;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.trouble.*;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * TroubleTicketValue Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public class TroubleTicketValueImpl extends AttributeAccessImpl
        implements TroubleTicketValue,
        java.io.Serializable,
        Cloneable {

    public javax.oss.Serializer makeSerializer(String serializerType)
            throws java.lang.IllegalArgumentException {
        if (serializerType.equals(XmlSerializer.class.getName()))
            return new XmlSerializerImpl(TroubleTicketValue.class.getName());
        else
            throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");

    }

    public String[] getSupportedSerializerTypes() {
        String[] serializerTypes = new String[1];
        serializerTypes[0] = new String(XmlSerializer.class.getName());
        return serializerTypes;
    }

    public String[] getSupportedOptionalAttributeNames() {
        String[] optAttributes = new String[1];
        return optAttributes;
    }

    public String[] getSupportedAddressTypes() {

        String[] sat = new String[1];
        sat[0] = new String(Address.class.getName());
        return sat;
    }

    public static final boolean VERBOSE = true;

    protected TroubleTicketKey troubleTicketKey = null;
    protected long lastUpdateNumber = 0;

    //protected so TTBean can inherit
    protected TroubleTicketAttributes ttAttrs = new TroubleTicketAttributes();

    //MR XML - shouldn't we need this only on a "get"?
    private HashMap desiredAttributes;

    //single static instance (so it only initializes once)
    protected static TroubleTicketInterfaceReflector ttifReflector =
            new TroubleTicketInterfaceReflector();

    //-----------------------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------------------
    public TroubleTicketValueImpl() {
        super();
        desiredAttributes = new HashMap();

    }


    //-----------------------------------------------------------------------------------
    // Constructor taking a ResultSet.  Use for conversion from DB row to
    // TroubleTicketValue.
    //-----------------------------------------------------------------------------------
    public TroubleTicketValueImpl(ResultSet rs) {
        super();
        TroubleTicketKey ttKey = new TroubleTicketKeyImpl();

        try {
            String trid = rs.getString(1);
            log("TroubleTicketValueImpl ctor: Trid = " + trid);
            ttKey.setTroubleTicketPrimaryKey(trid);
            ttKey.setApplicationDN(TTConfig.getInstance().getApplicationTypeDN());
            ttKey.setApplicationContext(TTConfig.getInstance().getApplicationContext());
            this.setTroubleTicketKey(ttKey);
            DBAccess.populateTTValueFromResultSet(this, rs);
        } catch (java.sql.SQLException sqlex) {
            log(" SQLException caught in TroubleTicketValue conversion ctor");
            sqlex.printStackTrace();
        }


    }

    //That should return a deep copy
    public Object clone() {
        log("Cloning Trouble Ticket in BASE");
        TroubleTicketValueImpl newVal = new TroubleTicketValueImpl();
        try {
            newVal.deepCopyAttributes(this);
        } catch (Exception e) {
            log("got exception in cloning--->" + e.getMessage());
            newVal = null;
        }
        TroubleTicketKey key = null;
        if ((key = getTroubleTicketKey()) != null) {

            newVal.setTroubleTicketKey((TroubleTicketKey) key.clone());
        } else
            newVal.setTroubleTicketKey(null);


        return newVal;


    }


    //--------------------------------------------------------------------------------------------------------------------
    // simple accessors
    //--------------------------------------------------------------------------------------------------------------------
    public TroubleTicketKey getTroubleTicketKey() {
        return troubleTicketKey;
    }

    public long getLastUpdateVersionNumber() {
        return lastUpdateNumber;
    }

    public ActivityDurationList getActivityDurationList() {
        return ttAttrs.getActivityDurationList();
    }

    public AlarmValueList getRelatedAlarmList() {
        return ttAttrs.getRelatedAlarmList();
    }

    public String[] getAdditionalTroubleInfoList() {
        return ttAttrs.getAdditionalTroubleInfoList();
    }

    public String getCloseOutNarr() {
        return ttAttrs.getCloseOutNarr();
    }

    public java.util.Date getReceivedTime() {
        return ttAttrs.getReceivedTime();
    }

    public TroubleTicketKey[] getRelatedTroubleTicketKeyList() {
        return ttAttrs.getRelatedTroubleTicketKeyList();
    }

    public RepairActivityList getRepairActivityList() {
        return ttAttrs.getRepairActivityList();
    }

    public java.util.Date getRestoredTime() {
        return ttAttrs.getRestoredTime();
    }

    public SPRoleAssignment[] getSPRoleAssignmentList() {
        return ttAttrs.getSPRoleAssignmentList();
    }

    public String getTroubleDescription() {
        return ttAttrs.getTroubleDescription();
    }

    public PersonReach getClearancePerson() {
        return ttAttrs.getClearancePerson();
    }

    public int getTroubleFound() {
        return ttAttrs.getTroubleFound();
    }

    public String getTroubleLocation() {
        return ttAttrs.getTroubleLocation();
    }

    public String[] getTroubleNumList() {
        return ttAttrs.getTroubleNumList();
    }

    public String getTroubledObject() {
        return ttAttrs.getTroubledObject();
    }

    public int  /* enum */              getTroubleType() {
        return ttAttrs.getTroubleType();
    }

    public int  /* enum */              getTroubleState() {
        return ttAttrs.getTroubleState();
    }

    public int  /* enum */              getTroubleStatus() {
        return ttAttrs.getTroubleStatus();
    }

    public java.util.Date getTroubleStatusTime() {
        return ttAttrs.getTroubleStatusTime();
    }

    public boolean getAfterHoursRepairAuthority() {
        return ttAttrs.getAfterHoursRepairAuthority();
    }

    public Authorization[] getAuthorizationList() {
        return ttAttrs.getAuthorizationList();
    }

    public boolean getCancelRequestedByCustomer() {
        return ttAttrs.getCancelRequestedByCustomer();
    }

    public int getCloseOutVerification() {
        return ttAttrs.getCloseOutVerification();
    }

    public java.util.Date getCommitmentTime() {
        return ttAttrs.getCommitmentTime();
    }

    public java.util.Date getCommitmentTimeRequested() {
        return ttAttrs.getCommitmentTimeRequested();
    }

    public CustomerRoleAssignment[] getCustomerRoleAssignmentList() {
        return ttAttrs.getCustomerRoleAssignmentList();
    }

    public String getCustomerTroubleNum() {
        return ttAttrs.getCustomerTroubleNum();
    }

    public String getDialog() {
        return ttAttrs.getDialog();
    }

    public EscalationList getEscalationList() {
        return ttAttrs.getEscalationList();
    }

    public int  /* enum */              getInitiatingMode() {
        return ttAttrs.getInitiatingMode();
    }

    public java.util.Date getLastUpdateTime() {
        return ttAttrs.getLastUpdateTime();
    }

    public boolean getMaintServiceCharge() {
        return ttAttrs.getMaintServiceCharge();
    }

    public TimeLength getOutageDuration() {
        return ttAttrs.getOutageDuration();
    }

    public int  /* enum */              getPerceivedTroubleSeverity() {
        return ttAttrs.getPerceivedTroubleSeverity();
    }

    public int  /* enum */              getPreferredPriority() {
        return ttAttrs.getPreferredPriority();
    }

    public int  /* enum */              getRepeatReport() {
        return ttAttrs.getRepeatReport();
    }

    public SuspectObject[] getSuspectObjectList() {
        return ttAttrs.getSuspectObjectList();
    }

    public java.util.Date getTroubleDetectionTime() {
        return ttAttrs.getTroubleDetectionTime();
    }

    public TroubleLocationInfo[] getTroubleLocationInfoList() {
        return ttAttrs.getTroubleLocationInfoList();
    }

    public java.util.Date getTroubledObjectAccessFromTime() {
        return ttAttrs.getTroubledObjectAccessFromTime();
    }

    public AccessHours[] getTroubledObjectAccessHoursList() {
        return ttAttrs.getTroubledObjectAccessHoursList();
    }

    public java.util.Date getTroubledObjectAccessToTime() {
        return ttAttrs.getTroubledObjectAccessToTime();
    }

    public java.util.Date getServiceUnavailableBeginTime() {
        return ttAttrs.getServiceUnavailableBeginTime();
    }

    public java.util.Date getServiceUnavailableEndTime() {
        return ttAttrs.getServiceUnavailableEndTime();
    }

    public String getOriginator() {
        return ttAttrs.getOriginator();
    }

    public String getTroubleSystemDN() {
        return ttAttrs.getTroubleSystemDN();
    }

    public PersonReach getCustomer() {
        return ttAttrs.getCustomer();
    }

    public PersonReach getAccountOwner() {
        return ttAttrs.getAccountOwner();
    }


    public HashMap getDesiredAttributes() {
        return desiredAttributes;
    }


    public void setDesiredAttributes(String[] desiredAttrNames, boolean mode) {

        //selected subset of attributes
        if (desiredAttrNames != null) {
            for (int i = 0; i < desiredAttrNames.length; i++) {
                this.desiredAttributes.put(desiredAttrNames[i], null);
            }
        }

        // Set the full set of attributes
        else if ((desiredAttrNames == null) && (mode == true)) {

            java.util.Vector attVector = new java.util.Vector();

            attVector.addElement(TroubleTicketValue.ACTIVITYDURATIONLIST);
            attVector.addElement(TroubleTicketValue.RELATEDALARMLIST);
            attVector.addElement(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST);
            attVector.addElement(TroubleTicketValue.CLOSEOUTNARR);
            attVector.addElement(TroubleTicketValue.RECEIVEDTIME);
            attVector.addElement(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST);
            attVector.addElement(TroubleTicketValue.REPAIRACTIVITYLIST);
            attVector.addElement(TroubleTicketValue.RESTOREDTIME);
            attVector.addElement(TroubleTicketValue.SPROLEASSIGNMENTLIST);
            attVector.addElement(TroubleTicketValue.TROUBLEDESCRIPTION);
            attVector.addElement(TroubleTicketValue.CLEARANCEPERSON);
            attVector.addElement(TroubleTicketValue.TROUBLEFOUND);
            attVector.addElement(TroubleTicketValue.TROUBLELOCATION);
            attVector.addElement(TroubleTicketValue.TROUBLENUMLIST);
            attVector.addElement(TroubleTicketValue.TROUBLEDOBJECT);
            attVector.addElement(TroubleTicketValue.TROUBLETYPE);
            attVector.addElement(TroubleTicketValue.TROUBLESTATE);
            attVector.addElement(TroubleTicketValue.TROUBLESTATUS);
            attVector.addElement(TroubleTicketValue.TROUBLESTATUSTIME);
            attVector.addElement(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY);
            attVector.addElement(TroubleTicketValue.AUTHORIZATIONLIST);
            attVector.addElement(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER);
            attVector.addElement(TroubleTicketValue.CLOSEOUTVERIFICATION);
            attVector.addElement(TroubleTicketValue.COMMITMENTTIME);
            attVector.addElement(TroubleTicketValue.COMMITMENTTIMEREQUESTED);
            attVector.addElement(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST);
            attVector.addElement(TroubleTicketValue.CUSTOMERTROUBLENUM);
            attVector.addElement(TroubleTicketValue.DIALOG);
            attVector.addElement(TroubleTicketValue.ESCALATIONLIST);
            attVector.addElement(TroubleTicketValue.INITIATINGMODE);
            attVector.addElement(TroubleTicketValue.LASTUPDATETIME);
            attVector.addElement(TroubleTicketValue.MAINTSERVICECHARGE);
            attVector.addElement(TroubleTicketValue.OUTAGEDURATION);
            attVector.addElement(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY);
            attVector.addElement(TroubleTicketValue.PREFERREDPRIORITY);
            attVector.addElement(TroubleTicketValue.REPEATREPORT);
            attVector.addElement(TroubleTicketValue.SUSPECTOBJECTLIST);
            attVector.addElement(TroubleTicketValue.TROUBLEDETECTIONTIME);
            attVector.addElement(TroubleTicketValue.TROUBLELOCATIONINFOLIST);
            attVector.addElement(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME);
            attVector.addElement(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST);
            attVector.addElement(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME);
            attVector.addElement(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME);
            attVector.addElement(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME);
            attVector.addElement(TroubleTicketValue.ORIGINATOR);
            attVector.addElement(TroubleTicketValue.TROUBLESYSTEMDN);
            attVector.addElement(TroubleTicketValue.CUSTOMER);
            attVector.addElement(TroubleTicketValue.ACCOUNTOWNER);

            String[] currAttributes = (String[]) attVector.toArray(new String[0]);
            for (int j = 0; j < currAttributes.length; j++) {
                this.desiredAttributes.put(currAttributes[j], null);
            }
        }
        //no attributes selected
        else if ((desiredAttrNames == null) && (mode == false)) {
            desiredAttributes = null;
        }
    }


    //------------------------------------------------------------------------------
    // simple mutators
    //------------------------------------------------------------------------------
    public void setLastUpdateVersionNumber(long value) {
        lastUpdateNumber = value;
    }

    public void setTroubleTicketKey(TroubleTicketKey value) {
        troubleTicketKey = value;
    }

    public void setActivityDurationList(ActivityDurationList value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setActivityDurationList(value);
        setAttributePopulated(TroubleTicketValue.ACTIVITYDURATIONLIST);
    }

    public void setRelatedAlarmList(AlarmValueList value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setRelatedAlarmList(value);
        setAttributePopulated(TroubleTicketValue.RELATEDALARMLIST);
    }

    public void setAdditionalTroubleInfoList(String[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setAdditionalTroubleInfoList(value);
        setAttributePopulated(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST);
    }

    public void setCloseOutNarr(String value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setCloseOutNarr(value);
        setAttributePopulated(TroubleTicketValue.CLOSEOUTNARR);
    }

    public void setReceivedTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setReceivedTime(value);
        setAttributePopulated(TroubleTicketValue.RECEIVEDTIME);
    }

    public void setRelatedTroubleTicketKeyList(TroubleTicketKey[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setRelatedTroubleTicketKeyList(value);
        setAttributePopulated(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST);
    }

    public void setRepairActivityList(RepairActivityList value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setRepairActivityList(value);
        setAttributePopulated(TroubleTicketValue.REPAIRACTIVITYLIST);
    }

    public void setRestoredTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setRestoredTime(value);
        setAttributePopulated(TroubleTicketValue.RESTOREDTIME);
    }

    public void setSPRoleAssignmentList(SPRoleAssignment[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setSPRoleAssignmentList(value);
        setAttributePopulated(TroubleTicketValue.SPROLEASSIGNMENTLIST);
    }

    public void setTroubleDescription(String value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleDescription(value);
        setAttributePopulated(TroubleTicketValue.TROUBLEDESCRIPTION);
    }

    public void setClearancePerson(PersonReach value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setClearancePerson(value);
        setAttributePopulated(TroubleTicketValue.CLEARANCEPERSON);
    }

    public void setTroubleFound(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleFound(value);
        setAttributePopulated(TroubleTicketValue.TROUBLEFOUND);
    }

    public void setTroubleLocation(String value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleLocation(value);
        setAttributePopulated(TroubleTicketValue.TROUBLELOCATION);
    }

    public void setTroubleNumList(String[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleNumList(value);
        setAttributePopulated(TroubleTicketValue.TROUBLENUMLIST);
    }

    public void setTroubledObject(String value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubledObject(value);
        setAttributePopulated(TroubleTicketValue.TROUBLEDOBJECT);
    }

    public void setTroubleType(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleType(value);
        setAttributePopulated(TroubleTicketValue.TROUBLETYPE);
    }

    public void setTroubleState(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleState(value);
        setAttributePopulated(TroubleTicketValue.TROUBLESTATE);
    }

    public void setTroubleStatus(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleStatus(value);
        setAttributePopulated(TroubleTicketValue.TROUBLESTATUS);
    }

    public void setTroubleStatusTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleStatusTime(value);
        setAttributePopulated(TroubleTicketValue.TROUBLESTATUSTIME);
    }

    public void setAfterHoursRepairAuthority(boolean value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setAfterHoursRepairAuthority(value);
        setAttributePopulated(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY);
    }

    public void setAuthorizationList(Authorization[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setAuthorizationList(value);
        setAttributePopulated(TroubleTicketValue.AUTHORIZATIONLIST);
    }

    public void setCancelRequestedByCustomer(boolean value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setCancelRequestedByCustomer(value);
        setAttributePopulated(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER);
    }

    public void setCloseOutVerification(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setCloseOutVerification(value);
        setAttributePopulated(TroubleTicketValue.CLOSEOUTVERIFICATION);
    }

    public void setCommitmentTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setCommitmentTime(value);
        setAttributePopulated(TroubleTicketValue.COMMITMENTTIME);
    }

    public void setCommitmentTimeRequested(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setCommitmentTimeRequested(value);
        setAttributePopulated(TroubleTicketValue.COMMITMENTTIMEREQUESTED);
    }

    public void setCustomerRoleAssignmentList(CustomerRoleAssignment[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setCustomerRoleAssignmentList(value);
        setAttributePopulated(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST);
    }

    public void setCustomerTroubleNum(String value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setCustomerTroubleNum(value);
        setAttributePopulated(TroubleTicketValue.CUSTOMERTROUBLENUM);
    }

    public void setDialog(String value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setDialog(value);
        setAttributePopulated(TroubleTicketValue.DIALOG);
    }

    public void setEscalationList(EscalationList value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setEscalationList(value);
        setAttributePopulated(TroubleTicketValue.ESCALATIONLIST);
    }

    public void setInitiatingMode(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setInitiatingMode(value);
        setAttributePopulated(TroubleTicketValue.INITIATINGMODE);
    }

    public void setLastUpdateTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setLastUpdateTime(value);
        setAttributePopulated(TroubleTicketValue.LASTUPDATETIME);
    }

    public void setMaintServiceCharge(boolean value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setMaintServiceCharge(value);
        setAttributePopulated(TroubleTicketValue.MAINTSERVICECHARGE);
    }

    public void setOutageDuration(TimeLength value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setOutageDuration(value);
        setAttributePopulated(TroubleTicketValue.OUTAGEDURATION);
    }

    public void setPerceivedTroubleSeverity(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setPerceivedTroubleSeverity(value);
        setAttributePopulated(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY);
    }

    public void setPreferredPriority(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setPreferredPriority(value);
        setAttributePopulated(TroubleTicketValue.PREFERREDPRIORITY);
    }

    public void setRepeatReport(int value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setRepeatReport(value);
        setAttributePopulated(TroubleTicketValue.REPEATREPORT);
    }

    public void setSuspectObjectList(SuspectObject[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setSuspectObjectList(value);
        setAttributePopulated(TroubleTicketValue.SUSPECTOBJECTLIST);
    }

    public void setTroubleDetectionTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleDetectionTime(value);
        setAttributePopulated(TroubleTicketValue.TROUBLEDETECTIONTIME);
    }

    public void setTroubleLocationInfoList(TroubleLocationInfo[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleLocationInfoList(value);
        setAttributePopulated(TroubleTicketValue.TROUBLELOCATIONINFOLIST);
    }

    public void setTroubledObjectAccessFromTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubledObjectAccessFromTime(value);
        setAttributePopulated(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME);
    }

    public void setTroubledObjectAccessHoursList(AccessHours[] value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubledObjectAccessHoursList(value);
        setAttributePopulated(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST);
    }

    public void setTroubledObjectAccessToTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubledObjectAccessToTime(value);
        setAttributePopulated(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME);
    }

    public void setServiceUnavailableBeginTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setServiceUnavailableBeginTime(value);
        setAttributePopulated(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME);
    }

    public void setServiceUnavailableEndTime(java.util.Date value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setServiceUnavailableEndTime(value);
        setAttributePopulated(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME);
    }

    public void setOriginator(String value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setOriginator(value);
        setAttributePopulated(TroubleTicketValue.ORIGINATOR);
    }

    public void setTroubleSystemDN(String value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setTroubleSystemDN(value);
        setAttributePopulated(TroubleTicketValue.TROUBLESYSTEMDN);
    }

    public void setCustomer(PersonReach value)
            throws java.lang.IllegalArgumentException {
        ttAttrs.setCustomer(value);
        setAttributePopulated(TroubleTicketValue.CUSTOMER);
    }

    public void setAccountOwner(PersonReach value)
            throws java.lang.IllegalArgumentException {
        log("---setAccountOwner in---");
        ttAttrs.setAccountOwner(value);
        setAttributePopulated(TroubleTicketValue.ACCOUNTOWNER);
        log("---setAccountOwner return---");
    }


    //------------------------------------------------------------------------------
    // Factory (make) Methods for non-primitive types
    //------------------------------------------------------------------------------
    public ActivityDuration makeActivityDuration() {
        return new ActivityDurationImpl();
    }

    public RepairActivity makeRepairActivity() {
        return new RepairActivityImpl();
    }

    public SPRoleAssignment makeSPRoleAssignment() {
        return new SPRoleAssignmentImpl();
    }

    public Authorization makeAuthorization() {
        return new AuthorizationImpl();
    }

    public CustomerRoleAssignment makeCustomerRoleAssignment() {
        return new CustomerRoleAssignmentImpl();
    }

    public Escalation makeEscalation() {
        return new EscalationImpl();
    }

    public SuspectObject makeSuspectObject() {
        return new SuspectObjectImpl();
    }

    public TroubleLocationInfo makeTroubleLocationInfo() {
        return new TroubleLocationInfoImpl();
    }

    public AccessHours makeAccessHours() {
        return new AccessHoursImpl();
    }

    public AlarmValue makeAlarmValue() {
        return new AlarmValueImpl();
    }

    public PersonReach makePersonReach() {
        return new PersonReachImpl();
    }

    public Time makeTime() {
        return new TimeImpl();
    }

    public TimeLength makeTimeLength() {
        return new TimeLengthImpl();
    }

    public TimeInterval makeTimeInterval() {
        return new TimeIntervalImpl();
    }

    public TroubleTicketKey makeTroubleTicketKey() {
        return new TroubleTicketKeyImpl();
    }

    //MultiValue make methods
    public ActivityDurationList makeActivityDurationList() {
        return new ActivityDurationListImpl();
    }

    public RepairActivityList makeRepairActivityList() {
        return new RepairActivityListImpl();
    }

    public EscalationList makeEscalationList() {
        return new EscalationListImpl();
    }

    public AlarmValueList makeAlarmValueList() {
        return new AlarmValueListImpl();
    }


    public TroubleTicketInterfaceReflector makeTTInterfaceReflector() {
        return new TroubleTicketInterfaceReflector();
    }



    //-----------------------------------------------------------------------------------
    // methods from base oss.ManagedEntityValue
    //-----------------------------------------------------------------------------------



    /**
     * Manufacture a Key for this kind of managed entity.
     */
    public ManagedEntityKey makeManagedEntityKey() {
        //todo
        ManagedEntityKey mek = null;
        // VP
        mek = new TroubleTicketKeyImpl();
        return mek;
    }

    //the RI support only the Address and NorthAmericaAddress type
    public Address makeAddress(String addressType)
            throws java.lang.IllegalArgumentException {
        if (addressType.equals(NorthAmericaAddress.class.getName()))
            return new NorthAmericaAddressImpl();
        else if (addressType.equals(Address.class.getName())){
			return new AddressImpl();
		} else {
			throw new java.lang.IllegalArgumentException("Unsupported Address Type:" + addressType);
		}
    }

    //return the attr value matching the attr name as a java object
    public Object getAttributeValue(String attrName)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {
        Logger.log("TT:getAttributeValue" + attrName);

        if (attrName.equals(TroubleTicketValue.ACCOUNTOWNER))
            return ttAttrs.getAccountOwner();
        else if (attrName.equals(TroubleTicketValue.ACTIVITYDURATIONLIST))
            return ttAttrs.getActivityDurationList();
        else if (attrName.equals(TroubleTicketValue.RELATEDALARMLIST))
            return ttAttrs.getRelatedAlarmList();
        else if (attrName.equals(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST))
            return ttAttrs.getAdditionalTroubleInfoList();
        else if (attrName.equals(TroubleTicketValue.CLOSEOUTNARR))
            return ttAttrs.getCloseOutNarr();
        else if (attrName.equals(TroubleTicketValue.RECEIVEDTIME))
            return ttAttrs.getReceivedTime();
        else if (attrName.equals(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST))
            return ttAttrs.getRelatedTroubleTicketKeyList();
        else if (attrName.equals(TroubleTicketValue.REPAIRACTIVITYLIST))
            return ttAttrs.getRepairActivityList();
        else if (attrName.equals(TroubleTicketValue.RESTOREDTIME))
            return ttAttrs.getRestoredTime();
        else if (attrName.equals(TroubleTicketValue.SPROLEASSIGNMENTLIST))
            return ttAttrs.getSPRoleAssignmentList();
        else if (attrName.equals(TroubleTicketValue.TROUBLEDESCRIPTION))
            return ttAttrs.getTroubleDescription();
        else if (attrName.equals(TroubleTicketValue.CLEARANCEPERSON))
            return ttAttrs.getClearancePerson();
        else if (attrName.equals(TroubleTicketValue.TROUBLEFOUND))
            return new Integer(ttAttrs.getTroubleFound());
        else if (attrName.equals(TroubleTicketValue.TROUBLELOCATION))
            return ttAttrs.getTroubleLocation();
        else if (attrName.equals(TroubleTicketValue.TROUBLENUMLIST))
            return ttAttrs.getTroubleNumList();
        else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECT))
            return ttAttrs.getTroubledObject();
        else if (attrName.equals(TroubleTicketValue.TROUBLETYPE))
            return new Integer(ttAttrs.getTroubleType());
        else if (attrName.equals(TroubleTicketValue.TROUBLESTATE))
            return new Integer(ttAttrs.getTroubleState());
        else if (attrName.equals(TroubleTicketValue.TROUBLESTATUS))
            return new Integer(ttAttrs.getTroubleStatus());
        else if (attrName.equals(TroubleTicketValue.TROUBLESTATUSTIME))
            return ttAttrs.getTroubleStatusTime();
        else if (attrName.equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY))
            return new Boolean(ttAttrs.getAfterHoursRepairAuthority());
        else if (attrName.equals(TroubleTicketValue.AUTHORIZATIONLIST)) {
            Logger.log("GETTING AUTHORIZATIONLIST");
            if (ttAttrs.getAuthorizationList() == null) {
                Logger.log("NULL AUTHORIZATIONLIST");
            }
            return ttAttrs.getAuthorizationList();
        } else if (attrName.equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER))
            return new Boolean(ttAttrs.getCancelRequestedByCustomer());
        else if (attrName.equals(TroubleTicketValue.CLOSEOUTVERIFICATION))
            return new Integer(ttAttrs.getCloseOutVerification());
        else if (attrName.equals(TroubleTicketValue.COMMITMENTTIME))
            return ttAttrs.getCommitmentTime();
        else if (attrName.equals(TroubleTicketValue.COMMITMENTTIMEREQUESTED))
            return ttAttrs.getCommitmentTimeRequested();
        else if (attrName.equals(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST))
            return ttAttrs.getCustomerRoleAssignmentList();
        else if (attrName.equals(TroubleTicketValue.CUSTOMERTROUBLENUM))
            return ttAttrs.getCustomerTroubleNum();
        else if (attrName.equals(TroubleTicketValue.DIALOG))
            return ttAttrs.getDialog();
        else if (attrName.equals(TroubleTicketValue.ESCALATIONLIST))
            return ttAttrs.getEscalationList();
        else if (attrName.equals(TroubleTicketValue.INITIATINGMODE))
            return new Integer(ttAttrs.getInitiatingMode());
        else if (attrName.equals(TroubleTicketValue.LASTUPDATETIME))
            return ttAttrs.getLastUpdateTime();
        else if (attrName.equals(TroubleTicketValue.MAINTSERVICECHARGE))
            return new Boolean(ttAttrs.getMaintServiceCharge());
        else if (attrName.equals(TroubleTicketValue.OUTAGEDURATION))
            return ttAttrs.getOutageDuration();
        else if (attrName.equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY))
            return new Integer(ttAttrs.getPerceivedTroubleSeverity());
        else if (attrName.equals(TroubleTicketValue.PREFERREDPRIORITY))
            return new Integer(ttAttrs.getPreferredPriority());
        else if (attrName.equals(TroubleTicketValue.REPEATREPORT))
            return new Integer(ttAttrs.getRepeatReport());
        else if (attrName.equals(TroubleTicketValue.SUSPECTOBJECTLIST))
            return ttAttrs.getSuspectObjectList();
        else if (attrName.equals(TroubleTicketValue.TROUBLEDETECTIONTIME))
            return ttAttrs.getTroubleDetectionTime();
        else if (attrName.equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST))
            return ttAttrs.getTroubleLocationInfoList();
        else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME))
            return ttAttrs.getTroubledObjectAccessFromTime();
        else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST))
            return ttAttrs.getTroubledObjectAccessHoursList();
        else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME))
            return ttAttrs.getTroubledObjectAccessToTime();
        else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME))
            return ttAttrs.getServiceUnavailableBeginTime();
        else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME))
            return ttAttrs.getServiceUnavailableEndTime();
        else if (attrName.equals(TroubleTicketValue.ORIGINATOR))
            return ttAttrs.getOriginator();
        else if (attrName.equals(TroubleTicketValue.TROUBLESYSTEMDN))
            return ttAttrs.getTroubleSystemDN();
        else if (attrName.equals(TroubleTicketValue.CUSTOMER))
            return ttAttrs.getCustomer();
        else {
            String msg = new String("TTValueImpl::getAttributeValue - unknown attribute: " + attrName);
            log(msg);
            throw new java.lang.IllegalArgumentException(msg);
        }


    }

    //set an attribute value
    public void setAttributeValue(String attrName, Object value)
            throws java.lang.IllegalArgumentException {


        Logger.log("**setAttributeValue in Impl******");
        Logger.log(attrName);


//1. if the single mutators of this class supported a
//   "setXXX(Object value)" syntax, we might be able to use reflection
//   to code the mutators.
//2. do validation for right type and throw IllegalArgumentException if there's
//   a mismatch

//System.out.println("TTValue:setAttributeValue setting: " + attrName);
        if (attrName.equals(TroubleTicketValue.ACTIVITYDURATIONLIST)) {
            if (!(value instanceof ActivityDurationList)) throw new java.lang.IllegalArgumentException();
            setActivityDurationList((ActivityDurationList) value);
        } else if (attrName.equals(TroubleTicketValue.RELATEDALARMLIST)) {
            if (!(value instanceof AlarmValueList)) throw new java.lang.IllegalArgumentException();
            setRelatedAlarmList((AlarmValueList) value);
        } else if (attrName.equals(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST)) {
            if (!(value instanceof String[])) throw new java.lang.IllegalArgumentException();
            setAdditionalTroubleInfoList((String[]) value);
        } else if (attrName.equals(TroubleTicketValue.CLOSEOUTNARR)) {
            if (!(value instanceof String)) throw new java.lang.IllegalArgumentException();
            setCloseOutNarr((String) value);
        } else if (attrName.equals(TroubleTicketValue.RECEIVEDTIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setReceivedTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST)) {
            if (!(value instanceof TroubleTicketKey[])) throw new java.lang.IllegalArgumentException();
            setRelatedTroubleTicketKeyList((TroubleTicketKey[]) value);
        } else if (attrName.equals(TroubleTicketValue.REPAIRACTIVITYLIST)) {
            if (!(value instanceof RepairActivityList)) throw new java.lang.IllegalArgumentException();
            setRepairActivityList((RepairActivityList) value);
        } else if (attrName.equals(TroubleTicketValue.RESTOREDTIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setRestoredTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.SPROLEASSIGNMENTLIST)) {
            if (!(value instanceof SPRoleAssignment[])) throw new java.lang.IllegalArgumentException();
            setSPRoleAssignmentList((SPRoleAssignment[]) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLEDESCRIPTION)) {
            if (!(value instanceof String)) throw new java.lang.IllegalArgumentException();
            setTroubleDescription((String) value);
        } else if (attrName.equals(TroubleTicketValue.CLEARANCEPERSON)) {
            if (!(value instanceof PersonReach)) throw new java.lang.IllegalArgumentException();
            setClearancePerson((PersonReach) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLEFOUND)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setTroubleFound(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATION)) {
            if (!(value instanceof String)) throw new java.lang.IllegalArgumentException();
            setTroubleLocation((String) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLENUMLIST)) {
            if (!(value instanceof String[])) throw new java.lang.IllegalArgumentException();
            setTroubleNumList((String[]) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECT)) {
            if (!(value instanceof String)) throw new java.lang.IllegalArgumentException();
            setTroubledObject((String) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLETYPE)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setTroubleType(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.TROUBLESTATE)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setTroubleState(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUS)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setTroubleStatus(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUSTIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setTroubleStatusTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY)) {
            if (!(value instanceof Boolean)) throw new java.lang.IllegalArgumentException();
            Boolean val = (Boolean) value;
            setAfterHoursRepairAuthority(val.booleanValue());
        } else if (attrName.equals(TroubleTicketValue.AUTHORIZATIONLIST)) {
            if (!(value instanceof Authorization[])) throw new java.lang.IllegalArgumentException();
            setAuthorizationList((Authorization[]) value);
        } else if (attrName.equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER)) {
            if (!(value instanceof Boolean)) throw new java.lang.IllegalArgumentException();
            Boolean val = (Boolean) value;
            setCancelRequestedByCustomer(val.booleanValue());
        } else if (attrName.equals(TroubleTicketValue.CLOSEOUTVERIFICATION)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setCloseOutVerification(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.COMMITMENTTIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setCommitmentTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.COMMITMENTTIMEREQUESTED)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setCommitmentTimeRequested((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST)) {
            if (!(value instanceof CustomerRoleAssignment[])) throw new java.lang.IllegalArgumentException();
            setCustomerRoleAssignmentList((CustomerRoleAssignment[]) value);
        } else if (attrName.equals(TroubleTicketValue.CUSTOMERTROUBLENUM)) {
            if (!(value instanceof String)) throw new java.lang.IllegalArgumentException();
            setCustomerTroubleNum((String) value);
        } else if (attrName.equals(TroubleTicketValue.DIALOG)) {
            if (!(value instanceof String)) throw new java.lang.IllegalArgumentException();
            setDialog((String) value);
        } else if (attrName.equals(TroubleTicketValue.ESCALATIONLIST)) {
            if (!(value instanceof EscalationList)) throw new java.lang.IllegalArgumentException();
            setEscalationList((EscalationList) value);
        } else if (attrName.equals(TroubleTicketValue.INITIATINGMODE)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setInitiatingMode(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.LASTUPDATETIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setLastUpdateTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.MAINTSERVICECHARGE)) {
            if (!(value instanceof Boolean)) throw new java.lang.IllegalArgumentException();
            Boolean val = (Boolean) value;
            setMaintServiceCharge(val.booleanValue());
        } else if (attrName.equals(TroubleTicketValue.OUTAGEDURATION)) {
            if (!(value instanceof TimeLength)) throw new java.lang.IllegalArgumentException();
            setOutageDuration((TimeLength) value);
        } else if (attrName.equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setPerceivedTroubleSeverity(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.PREFERREDPRIORITY)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setPreferredPriority(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.REPEATREPORT)) {
            if (!(value instanceof Integer)) throw new java.lang.IllegalArgumentException();
            Integer val = (Integer) value;
            setRepeatReport(val.intValue());
        } else if (attrName.equals(TroubleTicketValue.SUSPECTOBJECTLIST)) {
            if (!(value instanceof SuspectObject[])) throw new java.lang.IllegalArgumentException();
            setSuspectObjectList((SuspectObject[]) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLEDETECTIONTIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setTroubleDetectionTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST)) {
            if (!(value instanceof TroubleLocationInfo[])) throw new java.lang.IllegalArgumentException();
            setTroubleLocationInfoList((TroubleLocationInfo[]) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setTroubledObjectAccessFromTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST)) {
            if (!(value instanceof AccessHours[])) throw new java.lang.IllegalArgumentException();
            setTroubledObjectAccessHoursList((AccessHours[]) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME)) {
            Logger.log("-----FOUND TROUBLEDOBJECTACCESSTOTIME-----");


            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
//System.out.println("-----AFTER 1-----");
            setTroubledObjectAccessToTime((java.util.Date) value);
//System.out.println("-----AFTER 2-----");
        } else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setServiceUnavailableBeginTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME)) {
            if (!(value instanceof java.util.Date)) throw new java.lang.IllegalArgumentException();
            setServiceUnavailableEndTime((java.util.Date) value);
        } else if (attrName.equals(TroubleTicketValue.ORIGINATOR)) {
            if (!(value instanceof String)) throw new java.lang.IllegalArgumentException();
            setOriginator((String) value);
        } else if (attrName.equals(TroubleTicketValue.TROUBLESYSTEMDN)) {
            if (!(value instanceof String)) throw new java.lang.IllegalArgumentException();
            setTroubleSystemDN((String) value);
        } else if (attrName.equals(TroubleTicketValue.CUSTOMER)) {
            if (!(value instanceof PersonReach)) throw new java.lang.IllegalArgumentException();
            setCustomer((PersonReach) value);
        } else if (attrName.equals(TroubleTicketValue.ACCOUNTOWNER)) {
            setAccountOwner((PersonReach) value);
        } else {
            Logger.log("TroubleTicketValueImpl::setAttributeValue - unknown attribute: " + attrName);
            throw new java.lang.IllegalArgumentException();
        }

    }

    public void deepCopyAttributes(TroubleTicketValue ttValIn)
            throws RemoteException, //(allows for Entity Bean to subclass)
            IllegalAttributeValueException,
            IllegalArgumentException,
            IllegalStateException,
            javax.oss.UnsupportedAttributeException {

        String[] attrNames = ttValIn.getPopulatedAttributeNames();
        int size = attrNames.length;
        Logger.log("TroubleTicketValueImpl::setAttribues");
        //System.out.println("---------------------------------------");
        //System.out.println(size + " Attributes to set:");
        //System.out.println("---------------------------------------");
        for (int x = 0; x < size; x++) {
            Logger.log(attrNames[x]);
        }

        //First set the Entity Bean's attribute to that passed in the value object.
        for (int x = 0; x < size; x++) {
            String attrName = attrNames[x];
            log("TroubleTicketValueImpl:deepCopy attribute: " + attrName);
            Object value = null;
            try {
                value = ttValIn.getAttributeValue(attrName);
            } catch (java.lang.IllegalArgumentException iaex) {
                throw new javax.oss.IllegalArgumentException();
            } catch (java.lang.IllegalStateException iaex) {
                throw new javax.oss.IllegalStateException();
            } catch (javax.oss.UnsupportedAttributeException ux) {
                throw new javax.oss.IllegalStateException();
            }
            try {
                if (attrName.equals(TroubleTicketValue.ACTIVITYDURATIONLIST)) {


                    if (value != null)
                        setActivityDurationList((ActivityDurationList) ((ActivityDurationList) value).clone());
                    else
                        setActivityDurationList(null);

                } else if (attrName.equals(TroubleTicketValue.RELATEDALARMLIST)) {
                    if (value != null)
                        setRelatedAlarmList((AlarmValueList) ((AlarmValueList) value).clone());
                    else
                        setRelatedAlarmList(null);
                } else if (attrName.equals(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST)) {
                    if (value != null) {
                        String[] oarray = (String[]) value;
                        String[] array = new String[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = new String(oarray[i]);
                        }
                        setAdditionalTroubleInfoList(array);
                    } else
                        setAdditionalTroubleInfoList(null);
                } else if (attrName.equals(TroubleTicketValue.CLOSEOUTNARR)) {
                    if (value != null)
                        setCloseOutNarr(new String((String) value));
                    else
                        setCloseOutNarr(null);
                } else if (attrName.equals(TroubleTicketValue.RECEIVEDTIME)) {
                    if (value != null)
                        setReceivedTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setReceivedTime(null);
                } else if (attrName.equals(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST)) {
                    if (value != null) {
                        TroubleTicketKey[] oarray = (TroubleTicketKey[]) value;
                        TroubleTicketKey[] array = new TroubleTicketKey[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = (TroubleTicketKey) ((TroubleTicketKey) oarray[i]).clone();
                        }
                        setRelatedTroubleTicketKeyList(array);
                    } else
                        setRelatedTroubleTicketKeyList(null);
                } else if (attrName.equals(TroubleTicketValue.REPAIRACTIVITYLIST)) {
                    if (value != null)
                        setRepairActivityList((RepairActivityList) ((RepairActivityList) value).clone());
                    else
                        setRepairActivityList(null);
                } else if (attrName.equals(TroubleTicketValue.RESTOREDTIME)) {
                    if (value != null)
                        setRestoredTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setRestoredTime(null);
                } else if (attrName.equals(TroubleTicketValue.SPROLEASSIGNMENTLIST)) {
                    if (value != null) {
                        SPRoleAssignment[] oarray = (SPRoleAssignment[]) value;
                        SPRoleAssignment[] array = new SPRoleAssignment[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = (SPRoleAssignment) ((SPRoleAssignment) oarray[i]).clone();
                        }
                        setSPRoleAssignmentList(array);
                    } else
                        setSPRoleAssignmentList(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDESCRIPTION)) {
                    if (value != null)
                        setTroubleDescription(new String((String) value));
                    else
                        setTroubleDescription(null);
                } else if (attrName.equals(TroubleTicketValue.CLEARANCEPERSON)) {
                    if (value != null)
                        setClearancePerson((PersonReach) ((PersonReach) value).clone());
                    else
                        setClearancePerson(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLEFOUND)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setTroubleFound(val.intValue());
                    } else
                        setTroubleFound(0);
                } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATION)) {
                    if (value != null)
                        setTroubleLocation(new String((String) value));
                    else
                        setTroubleLocation(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLENUMLIST)) {
                    if (value != null) {
                        String[] oarray = (String[]) value;
                        String[] array = new String[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = new String(oarray[i]);
                        }
                        setTroubleNumList(array);
                    } else
                        setTroubleNumList(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECT)) {
                    if (value != null)
                        setTroubledObject(new String((String) value));
                    else
                        setTroubledObject(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLETYPE)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setTroubleType(val.intValue());
                    } else
                        setTroubleType(0);
                } else if (attrName.equals(TroubleTicketValue.TROUBLESTATE)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setTroubleState(val.intValue());
                    } else
                        setTroubleState(0);
                } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUS)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setTroubleStatus(val.intValue());
                    } else
                        setTroubleStatus(0);
                } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUSTIME)) {
                    if (value != null)
                        setTroubleStatusTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setTroubleStatusTime(null);
                } else if (attrName.equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY)) {
                    if (value != null) {
                        Boolean val = (Boolean) ttValIn.getAttributeValue(attrName);
                        setAfterHoursRepairAuthority(val.booleanValue());
                    } else
                        setAfterHoursRepairAuthority(false);
                } else if (attrName.equals(TroubleTicketValue.AUTHORIZATIONLIST)) {
                    if (value != null) {
                        Authorization[] oarray = (Authorization[]) value;
                        Authorization[] array = new Authorization[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = (Authorization) ((Authorization) oarray[i]).clone();
                        }
                        setAuthorizationList(array);
                    } else
                        setAuthorizationList(null);
                } else if (attrName.equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER)) {
                    if (value != null) {
                        Boolean val = (Boolean) ttValIn.getAttributeValue(attrName);
                        setCancelRequestedByCustomer(val.booleanValue());
                    } else
                        setCancelRequestedByCustomer(false);
                } else if (attrName.equals(TroubleTicketValue.CLOSEOUTVERIFICATION)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setCloseOutVerification(val.intValue());
                    } else
                        setCloseOutVerification(0);
                } else if (attrName.equals(TroubleTicketValue.COMMITMENTTIME)) {
                    if (value != null)
                        setCommitmentTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setCommitmentTime(null);
                } else if (attrName.equals(TroubleTicketValue.COMMITMENTTIMEREQUESTED)) {
                    if (value != null)
                        setCommitmentTimeRequested((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setCommitmentTimeRequested(null);
                } else if (attrName.equals(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST)) {
                    if (value != null) {
                        CustomerRoleAssignment[] oarray = (CustomerRoleAssignment[]) value;
                        CustomerRoleAssignment[] array = new CustomerRoleAssignment[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = (CustomerRoleAssignment) ((CustomerRoleAssignment) oarray[i]).clone();
                        }
                        setCustomerRoleAssignmentList(array);
                    } else
                        setCustomerRoleAssignmentList(null);
                } else if (attrName.equals(TroubleTicketValue.CUSTOMERTROUBLENUM)) {
                    if (value != null)
                        setCustomerTroubleNum(new String((String) value));
                    else
                        setCustomerTroubleNum(null);
                } else if (attrName.equals(TroubleTicketValue.DIALOG)) {
                    if (value != null)
                        setDialog(new String((String) value));
                    else
                        setDialog(null);
                } else if (attrName.equals(TroubleTicketValue.ESCALATIONLIST)) {
                    if (value != null)
                        setEscalationList((EscalationList) ((EscalationList) value).clone());
                    else
                        setEscalationList(null);
                } else if (attrName.equals(TroubleTicketValue.INITIATINGMODE)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setInitiatingMode(val.intValue());
                    } else
                        setInitiatingMode(0);
                } else if (attrName.equals(TroubleTicketValue.LASTUPDATETIME)) {
                    if (value != null)
                        setLastUpdateTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setLastUpdateTime(null);
                } else if (attrName.equals(TroubleTicketValue.MAINTSERVICECHARGE)) {
                    if (value != null) {
                        Boolean val = (Boolean) ttValIn.getAttributeValue(attrName);
                        setMaintServiceCharge(val.booleanValue());
                    } else
                        setMaintServiceCharge(false);
                } else if (attrName.equals(TroubleTicketValue.OUTAGEDURATION)) {
                    if (value != null)
                        setOutageDuration((TimeLength) ((TimeLength) value).clone());
                    else
                        setOutageDuration(null);
                } else if (attrName.equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setPerceivedTroubleSeverity(val.intValue());
                    } else
                        setPerceivedTroubleSeverity(0);
                } else if (attrName.equals(TroubleTicketValue.PREFERREDPRIORITY)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setPreferredPriority(val.intValue());
                    } else
                        setPreferredPriority(0);
                } else if (attrName.equals(TroubleTicketValue.REPEATREPORT)) {
                    if (value != null) {
                        Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                        setRepeatReport(val.intValue());
                    } else
                        setRepeatReport(0);
                } else if (attrName.equals(TroubleTicketValue.SUSPECTOBJECTLIST)) {
                    if (value != null) {
                        SuspectObject[] oarray = (SuspectObject[]) value;
                        SuspectObject[] array = new SuspectObject[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = (SuspectObject) ((SuspectObject) oarray[i]).clone();
                        }
                        setSuspectObjectList(array);
                    } else
                        setSuspectObjectList(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDETECTIONTIME)) {
                    if (value != null)
                        setTroubleDetectionTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setTroubleDetectionTime(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST)) {
                    if (value != null) {
                        TroubleLocationInfo[] oarray = (TroubleLocationInfo[]) value;
                        TroubleLocationInfo[] array = new TroubleLocationInfo[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = (TroubleLocationInfo) ((TroubleLocationInfo) oarray[i]).clone();
                        }
                        setTroubleLocationInfoList(array);
                    } else
                        setTroubleLocationInfoList(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME)) {
                    if (value != null)
                        setTroubledObjectAccessFromTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setTroubledObjectAccessFromTime(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST)) {
                    if (value != null) {
                        AccessHours[] oarray = (AccessHours[]) value;
                        AccessHours[] array = new AccessHours[oarray.length];
                        for (int i = 0; i < oarray.length; i++) {
                            array[i] = (AccessHours) ((AccessHours) oarray[i]).clone();
                        }
                        setTroubledObjectAccessHoursList(array);
                    } else
                        setTroubledObjectAccessHoursList(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME)) {
                    if (value != null)
                        setTroubledObjectAccessToTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setTroubledObjectAccessToTime(null);
                } else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME)) {
                    if (value != null)
                        setServiceUnavailableBeginTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setServiceUnavailableBeginTime(null);
                } else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME)) {
                    if (value != null)
                        setServiceUnavailableEndTime((java.util.Date) ((java.util.Date) value).clone());
                    else
                        setServiceUnavailableEndTime(null);
                } else if (attrName.equals(TroubleTicketValue.ORIGINATOR)) {
                    if (value != null)
                        setOriginator(new String((String) value));
                    else
                        setOriginator(null);
                } else if (attrName.equals(TroubleTicketValue.TROUBLESYSTEMDN)) {
                    if (value != null)
                        setTroubleSystemDN(new String((String) value));
                    else
                        setTroubleSystemDN(null);
                } else if (attrName.equals(TroubleTicketValue.CUSTOMERTROUBLENUM)) {
                    if (value != null)
                        setCustomerTroubleNum(new String((String) value));
                    else
                        setCustomerTroubleNum(null);
                } else if (attrName.equals(TroubleTicketValue.CUSTOMER)) {
                    if (value != null)
                        setCustomer((PersonReach) ((PersonReach) value).clone());
                    else
                        setCustomer(null);
                } else if (attrName.equals(TroubleTicketValue.ACCOUNTOWNER)) {


                    if (value != null) {
                        setAccountOwner((PersonReach) ((PersonReach) value).clone());

                    } else {
                        setAccountOwner(null);
                    }


                } else {
                    log("TroubleTicketValueImpl::setAttributes - unknown attribute: " + attrName);
                    throw new IllegalArgumentException();
                }

            } catch (Exception e) {
                log("Exception is: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //--------------------------------------------------------------------
    // setAttributes - set this value object via another value object
    // (like a "Deep" C++ copy constructor)
    //--------------------------------------------------------------------
    public void setAttributes(TroubleTicketValue ttValIn)
            throws RemoteException, //(allows for Entity Bean to subclass)
            IllegalAttributeValueException,
            IllegalArgumentException,
            IllegalStateException,
            javax.oss.UnsupportedAttributeException {

        String[] attrNames = ttValIn.getPopulatedAttributeNames();
        int size = attrNames.length;
        Logger.log("TroubleTicketValueImpl::setAttribues");

        for (int x = 0; x < size; x++) {
            Logger.log(attrNames[x]);
        }

        //First set the Entity Bean's attribute to that passed in the value object.
        for (int x = 0; x < size; x++) {
            String attrName = attrNames[x];

            Object value = null;
            try {
                value = ttValIn.getAttributeValue(attrName);
            } catch (java.lang.IllegalArgumentException iaex) {
                throw new javax.oss.IllegalArgumentException();
            } catch (java.lang.IllegalStateException iaex) {
                throw new javax.oss.IllegalStateException();
            } catch (javax.oss.UnsupportedAttributeException ux) {
                throw new javax.oss.IllegalStateException();
            }

            if (attrName.equals(TroubleTicketValue.ACTIVITYDURATIONLIST))
                setActivityDurationList((ActivityDurationList) value);
            else if (attrName.equals(TroubleTicketValue.RELATEDALARMLIST))
                setRelatedAlarmList((AlarmValueList) value);
            else if (attrName.equals(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST))
                setAdditionalTroubleInfoList((String[]) value);
            else if (attrName.equals(TroubleTicketValue.CLOSEOUTNARR))
                setCloseOutNarr((String) value);
            else if (attrName.equals(TroubleTicketValue.RECEIVEDTIME))
                setReceivedTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST))
                setRelatedTroubleTicketKeyList((TroubleTicketKey[]) value);
            else if (attrName.equals(TroubleTicketValue.REPAIRACTIVITYLIST))
                setRepairActivityList((RepairActivityList) value);
            else if (attrName.equals(TroubleTicketValue.RESTOREDTIME))
                setRestoredTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.SPROLEASSIGNMENTLIST))
                setSPRoleAssignmentList((SPRoleAssignment[]) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEDESCRIPTION))
                setTroubleDescription((String) value);
            else if (attrName.equals(TroubleTicketValue.CLEARANCEPERSON))
                setClearancePerson((PersonReach) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEFOUND)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setTroubleFound(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATION))
                setTroubleLocation((String) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLENUMLIST))
                setTroubleNumList((String[]) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECT))
                setTroubledObject((String) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLETYPE)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setTroubleType(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.TROUBLESTATE)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setTroubleState(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUS)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setTroubleStatus(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUSTIME))
                setTroubleStatusTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY)) {
                Boolean val = (Boolean) ttValIn.getAttributeValue(attrName);
                setAfterHoursRepairAuthority(val.booleanValue());
            } else if (attrName.equals(TroubleTicketValue.AUTHORIZATIONLIST))
                setAuthorizationList((Authorization[]) value);
            else if (attrName.equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER)) {
                Boolean val = (Boolean) ttValIn.getAttributeValue(attrName);
                setCancelRequestedByCustomer(val.booleanValue());
            } else if (attrName.equals(TroubleTicketValue.CLOSEOUTVERIFICATION)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setCloseOutVerification(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.COMMITMENTTIME))
                setCommitmentTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.COMMITMENTTIMEREQUESTED))
                setCommitmentTimeRequested((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST))
                setCustomerRoleAssignmentList((CustomerRoleAssignment[]) value);
            else if (attrName.equals(TroubleTicketValue.CUSTOMERTROUBLENUM))
                setCustomerTroubleNum((String) value);
            else if (attrName.equals(TroubleTicketValue.DIALOG))
                setDialog((String) value);
            else if (attrName.equals(TroubleTicketValue.ESCALATIONLIST))
                setEscalationList((EscalationList) value);
            else if (attrName.equals(TroubleTicketValue.INITIATINGMODE)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setInitiatingMode(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.LASTUPDATETIME))
                setLastUpdateTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.MAINTSERVICECHARGE)) {
                Boolean val = (Boolean) ttValIn.getAttributeValue(attrName);
                setMaintServiceCharge(val.booleanValue());
            } else if (attrName.equals(TroubleTicketValue.OUTAGEDURATION)) {
                setOutageDuration((TimeLength) value);
            } else if (attrName.equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setPerceivedTroubleSeverity(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.PREFERREDPRIORITY)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setPreferredPriority(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.REPEATREPORT)) {
                Integer val = (Integer) ttValIn.getAttributeValue(attrName);
                setRepeatReport(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.SUSPECTOBJECTLIST)) {
                setSuspectObjectList((SuspectObject[]) value);
            } else if (attrName.equals(TroubleTicketValue.TROUBLEDETECTIONTIME)) {
                setTroubleDetectionTime((java.util.Date) value);
            } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST)) {
                setTroubleLocationInfoList((TroubleLocationInfo[]) value);
            } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME)) {
                setTroubledObjectAccessFromTime((java.util.Date) value);
            } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST)) {
                setTroubledObjectAccessHoursList((AccessHours[]) value);
            } else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME)) {
                setTroubledObjectAccessToTime((java.util.Date) value);
            } else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME)) {
                setServiceUnavailableBeginTime((java.util.Date) value);
            } else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME)) {
                setServiceUnavailableEndTime((java.util.Date) value);
            } else if (attrName.equals(TroubleTicketValue.ORIGINATOR)) {
                setOriginator((String) value);
            } else if (attrName.equals(TroubleTicketValue.TROUBLESYSTEMDN)) {
                setTroubleSystemDN((String) value);
            } else if (attrName.equals(TroubleTicketValue.CUSTOMERTROUBLENUM)) {
                setCustomerTroubleNum((String) value);
            } else if (attrName.equals(TroubleTicketValue.CUSTOMER)) {
                setCustomer((PersonReach) value);
            } else if (attrName.equals(TroubleTicketValue.ACCOUNTOWNER)) {
                setAccountOwner((PersonReach) value);
            } else {
                log("TroubleTicketValueImpl::setAttributes - unknown attribute: " + attrName);
                throw new IllegalArgumentException();
            }
        }
    }


    //get the managed entity key
    public ManagedEntityKey getManagedEntityKey() {
        return troubleTicketKey;
    }

    //set the managed entity key
    public void setManagedEntityKey(ManagedEntityKey key) {
        troubleTicketKey = (TroubleTicketKey) key;
    }

    //externalizes this tt value to XML
    public String toXml()
            throws org.xml.sax.SAXException {
        return XmlTranslator.toXml(this, desiredAttributes);
    }

    public String toXmlNoRoot()
            throws org.xml.sax.SAXException {
        return XmlTranslator.toXmlNoRoot(this, desiredAttributes);
    }

    //set the values of this TTValue to those values in the passed XML doc
    public void fromXml(org.w3c.dom.Element doc)
            throws org.xml.sax.SAXException {
        XmlTranslator.fromXml(doc, this);
    }

    //Only method required for XmlSerializer.toXml()
    public String toXml(String elementName)
            throws org.xml.sax.SAXException {
        String[] attrNames = getPopulatedAttributeNames();
        HashMap attMap = new HashMap();
        if (attrNames != null) {
            for (int i = 0; i < attrNames.length; i++) {
                attMap.put(attrNames[i], null);

            }
        }

        //System.out.println("\n         ******  TTKey To XML     ***********       \n");
        StringBuffer strBuffer = new StringBuffer();

        strBuffer.append("\n<" + elementName + ">\n");
        strBuffer.append(XmlTranslator.toXmlNoRoot(this, attMap));

        strBuffer.append("</" + elementName + ">\n");
        return strBuffer.toString();


    }

    public String getXmlHeader() {
        return new String();
    }

    /**
     * Get the XML element name at the root of this document type, where it
     * exists as a standalone XML document instance.
     *
     * @return String XML element name
     */
    public String getRootName() {
        return new String();
    }

    public String getRootAttributes() {
        return new String();
    }




    //--------------------------------------------------------------
    // Specific methods from AttributeAccess
    //--------------------------------------------------------------

    /** Returns all attribute names, which are available in this value object.
     * <p>
     * Use one of the returned names to access the generic methods
     * <CODE>getAttributeValue(...)</CODE and <CODE>setAttributeValue(...)</CODE>.
     * <p>This method may be used by generic clients to obtain information about
     * the attributes. It does not say anything about the state of an attribute,
     * i.e. if it is populated or not.
     *
     * @return the array contains all attribute names in no particular order.
     */
    public String[] getAttributeNames() {
        return TroubleTicketAttributes.TTAttributeNames;
    }

    /** Gets all attributes which can be set in the server implementation.
     * <p>
     * It is required to return a subset of the array returned
     * by <CODE>getAttributeNames()</CODE>.
     */
    public String[] getSettableAttributeNames() {
        //for now they are all settable
        return TroubleTicketAttributes.TTAttributeNames;
    }

    /**
     * Get multiple attribute values given an array of attribute names.
     *
     * @param attributeNames
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public java.util.Map getAttributeValues(String[] attributeNames)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {

        java.util.Hashtable hashTab = new java.util.Hashtable();

        Object attr = null;
        for (int x = 0; x < attributeNames.length; x++) {
            try {
                attr = getAttributeValue(attributeNames[x]);
            } catch (java.lang.IllegalArgumentException ex) {
                //message will have been logged - assume best effort.
            }
            //don't bother populate if its null.
            if (attr != null)
                hashTab.put(attributeNames[x], attr);


        }

        return hashTab;
    }

    /**
     * Setting multiple attribute values
     *
     * @param attrNameValuePairs
     * @exception java.lang.IllegalArgumentException
     * @exception java.lang.IllegalStateException
     */
    public void setAttributeValues(java.util.Map attrNameValuePairs)
            throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException {

        //for now, the integrity of the Map is assumed to be OK.
        java.util.Set attrSet = attrNameValuePairs.keySet();
        String[] attrNames = (String[]) attrSet.toArray(new String[0]);

        java.util.Collection attrColl = attrNameValuePairs.values();
        Object[] attrValues = (Object[]) attrColl.toArray(new Object[0]);

        for (int x = 0; x < attrNames.length; x++) {
            try {
                setAttributeValue(attrNames[x], attrValues[x]);
            } catch (java.lang.IllegalArgumentException iaex) {
                throw iaex;
            }

        }

    }

    public java.util.Map getAllPopulatedAttributes()
            throws java.lang.IllegalStateException {
        return getAttributeValues(getAttributeNames());
    }


    //--------------------------------------------------------------
    // You might also consider using WebLogic's log service
    //--------------------------------------------------------------
    private void log(String s) {
        Logger.log(s);
    }

    public boolean equals(Object arg) {

        if ((arg != null) && (arg instanceof TroubleTicketValue)) {
            TroubleTicketValue argVal = (TroubleTicketValue) arg;
            return utils.compareValues(this, argVal);
        }

        return false;

    }


}
