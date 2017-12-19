package ossj.ttri;

import javax.ejb.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.oss.IllegalArgumentException;
import javax.oss.*;
import javax.oss.trouble.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

/**
 * TroubleTicketBean Implementation Class
 * Entity Bean Implementation
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketBean extends TroubleTicketValueImpl
        implements EntityBean,
        java.io.Serializable {

    final static private boolean VERBOSE = true;

    //keep track of what the current method invoked is.  This allows me to
    //send a CloseOut in setAttributes only when its the Close method being
    //invoked, and reuse setAttributes on the ejbCreate.
    private int currentMethod;
    private static final int NONE = 0;
    private static final int CREATE = 1;
    private static final int SET = 2;
    private static final int CANCEL = 3;
    private static final int CLOSE = 4;
    private static final int ESCALATE = 5;

    //events that are sent at the end of ejbStore.
    //(Create event is sent at end of ejbCreate)
    private int sendEvent = 0;
    private static final int AVC = 1;
    private static final int CANCELLATION = 2;
    private static final int CLOSEOUT = 3;
    private static final int REMOVE = 4;
    private static final int STATUSCHANGE = 5;

    int oldStatus = -1;
    int newStatus = -1;


    //used for AVC events
    private TroubleTicketValue AVCAttributes;

    private EntityContext ctx;
    InitialContext initCtx;


    //-------------------------------------------------------------------------
    //
    // EJB Container methods
    //
    //-------------------------------------------------------------------------
    public TroubleTicketKeyImpl ejbCreate(TroubleTicketValue ttValue)
            throws CreateException {

        log("TTEntityBean:ejbCreate");

        currentMethod = CREATE;

        //set my key to the generated key
        this.troubleTicketKey = createTTKey();
        String ttKeyStr = troubleTicketKey.getTroubleTicketPrimaryKey();
        this.setLastUpdateVersionNumber(0);


        //-----------------------------------------
        // Set the attributes of the bean
        //-----------------------------------------
        try {
            setAttributes(ttValue, true);
        } catch (EJBException rex) {
            reset();
            throw new CreateException
                    ("TTEntityBean: EJBException occurred on setAttributes in ejbCreate");
        } catch (IllegalAttributeValueException iavEx) {
            reset();
            throw new CreateException
                    ("TTEntityBean: IllegalAttributeValuesException occurred on setAttributes in ejbCreate. " +
                    " Nested exception is: " + iavEx.getMessage());
        } catch (IllegalArgumentException iaEx) {
            reset();
            throw new CreateException
                    ("TTEntityBean: IllegalArgumentException occurred on setAttributes in ejbCreate. " +
                    " Nested exception is: " + iaEx.getMessage());
        } catch (javax.oss.ResyncRequiredException rex) {
            reset();
            throw new CreateException
                    ("TTEntityBean: ResyncRequiredException occurred on setAttributes in ejbCreate. " +
                    " Nested exception is: " + rex.getMessage());

        }
        /*  catch( javax.oss.UnsupportedAttributeException ux ) {
              reset();
              throw new CreateException
                  ("TTEntityBean: UnsupportedAttributeException occurred on setAttributes in ejbCreate. " +
                   " Nested exception is: " + ux.getMessage() );

          } */


        log("---------------------------");
        log("TroubleTicketBean.ejbCreate");
        log("---------------------------");
        log("Assigned TT key: " + ttKeyStr);
        log("Description: " + ttValue.getTroubleDescription());

        try {
            DBAccess.insert(this, false);
        } catch (SQLException sqe) {

            reset();      //reset the attributes to "not dirty" in the value object


            //Is there a better way of doing this?   MR TODO

            // Check to see if this SQLException is due to a unique constraint
            // violation on our database table (ie. there is already a pk with the
            // value of accountId in the table).  If so, throw a
            // DuplicateKeyException else throw a CreateException.
            try {
                ejbFindByPrimaryKey((TroubleTicketKeyImpl) this.troubleTicketKey);
            } catch (FinderException onfe) {
                String error = "SQLException: " + sqe;
                log(error);
                throw new CreateException(error);
            }
            String error = new String
                    ("TT with Primary Key: " + ttKeyStr + " already exists");
            log(error);
            throw new DuplicateKeyException(error);
        }

        //------------------------------------------------------------------
        // TTEventPublisher creates the event, sets its data, (both base and
        // specific), sets the filterarble properties via the eventproperty
        // descriptor, and ships the event.
        //------------------------------------------------------------------

        ttValue.setTroubleTicketKey(this.troubleTicketKey);
        TTEventPublisher.getInstance().sendCreateEvent(ttValue);
        reset();
        return (TroubleTicketKeyImpl) this.troubleTicketKey;

    }


    public void setEntityContext(EntityContext ctx) {

        try {
            initCtx = new InitialContext();
        } catch (NamingException nex) {
            log("TTSession:setEntityContext:  Naming exception caught while creating InitialContext");
            nex.printStackTrace();
        }

        log("TTEntityBean:setEntityContext");
        this.ctx = ctx;

    }

    public void unsetEntityContext() {
        log("TTEntityBean:unsetEntityContext");
        this.ctx = null;
    }

    public void ejbActivate() {
        log("TTEntityBean:ejbActivate");
        //VP
        this.troubleTicketKey = (TroubleTicketKey) ctx.getPrimaryKey();
        //end VP
    }

    public void ejbPassivate() {
        log("TTEntityBean:ejbPassivate");
        //VP
        this.troubleTicketKey = null;
        //end VP
    }

    public void ejbLoad() {

        log("TTEntityBean:ejbLoad");
        currentMethod = NONE;
        sendEvent = 0;

        TroubleTicketKey ttKey = (TroubleTicketKey) ctx.getPrimaryKey();
        setTroubleTicketKey(ttKey);
        String ttId = ttKey.getTroubleTicketPrimaryKey();

        try {
            DBAccess.loadTroubleTicket(ttId, this);
        } catch (SQLException sqlex) {
            log("SQLException occurred in ejbLoad");
            sqlex.printStackTrace();
        } catch (ObjectNotFoundException onfex) {
            log("ObjectNotFoundException occurred in ejbLoad");
            onfex.printStackTrace();
        }

        reset();    //reset attributes to clean

        //log("ejbLoad complete");

    }


    public void ejbStore() {

        log("---TTEntityBean:ejbStore----");

        //only persist the TT attributes if they have been modified.
        log("No. of attrs to update: " + populatedAttributeNames.size());
        if (populatedAttributeNames.size() <= 0)
            return;     //get outta here!

        int updates = 0;
        try {
            log("Version Number is: ---->" + this.getLastUpdateVersionNumber());
            updates = DBAccess.updateByKey(this, true);
        }

                //map exceptions to EJB exception - populate the msg
        catch (SQLException sqlex) {
            throw new EJBException("SQLException" + sqlex.getMessage());
        } catch (ObjectNotFoundException ex) {
            throw new EJBException("ObjectNotFoundException" + ex.getMessage());
        } catch (javax.oss.IllegalAttributeValueException ex) {
            throw new EJBException("IllegalAttributeValueException" + ex.getMessage());
        }


        //Only send the event *after* the DB has been successfully updated
        if (updates > 0) {
            log("---ABOUT TO SEND EVENT----:" + sendEvent);
            TroubleTicketValueImpl ttVal = null;
            switch (sendEvent) {
                case AVC:
                    log("---SENDING AVC EVENT----");
                    String[] attrNames = AVCAttributes.getPopulatedAttributeNames();
                    log("Number of Populated Attributes in Value being Sent");
                    log(new Integer(attrNames.length).toString());

                    AVCAttributes.setTroubleTicketKey(this.getTroubleTicketKey());
                    attrNames = AVCAttributes.getPopulatedAttributeNames();
                    log("Number of Populated Attributes in Value being Sent");
                    log(new Integer(attrNames.length).toString());

                    TTEventPublisher.getInstance().sendAVCEvent(AVCAttributes);
                    break;

                case CANCELLATION:
                    log("---SENDING CANCEL EVENT----");
                    TTEventPublisher.getInstance().sendCancellationEvent(this.getTroubleTicketKey(),
                            this.getCloseOutNarr());
                    break;

                case CLOSEOUT:
                    //can't pass the TroubleTicketBean subclass to the TTEventPublisher -
                    //it can't be serialized properly.  Create a new ttValue and set it with the
                    //bean's attributes.
                    log("---SENDING CLOSEOUT EVENT----");
                    ttVal = new TroubleTicketValueImpl();
                    try {
                        ttVal.setAttributes(this);
                    } catch (Exception ex) {
                        Logger.logException("Exception caught setting attrs for event", ex);
                    }
                    ttVal.setTroubleTicketKey(this.getTroubleTicketKey());
                    TTEventPublisher.getInstance().sendCloseOutEvent(ttVal);
                    break;

                case STATUSCHANGE:
                    log("---SENDING STATUSCHANGE EVENT----");
                    TTEventPublisher.getInstance().sendStatusChangeEvent(this.getTroubleTicketKey(),
                            this.getTroubleStatus(),
                            this.getTroubleStatusTime(),
                            this.getTroubleState());
                    break;
            }
        }

    }


    public void ejbPostCreate(TroubleTicketValue ttValue) {
        log("TTEntityBean:ejbPostCreate");
    }

    public void ejbRemove() {
        log("TTEntityBean:ejbRemove");
        //VP implement the removal from the DB
        try {
            DBAccess.deleteByKey(this);
        } catch (SQLException sqlex) {
            log("Unable to remove row in DB : " + sqlex);
        } catch (ObjectNotFoundException onfex) {
            log("Unable to remove row in DB : " + onfex);
        }
        // end VP
    }

    //----------------------------------------------------------------
    // ejbFindByPrimaryKey
    //----------------------------------------------------------------
    public TroubleTicketKeyImpl ejbFindByPrimaryKey(TroubleTicketKeyImpl pk)
            throws FinderException {

        log("TroubleTicketBean.ejbFindByPrimaryKey (" + pk + ")");
        String ttId = pk.getTroubleTicketPrimaryKey();

        boolean found = false;

        found = DBAccess.findByPrimaryKey(ttId);
        if (!found)
            throw new FinderException();

        log("ejbFindByPrimaryKey (" + pk.getTroubleTicketPrimaryKey() + ") found");
        return pk;

    }


    //-----------------------------------------------------------------
    // Get a bunch of attributes, returning a TTValue object.
    //
    // ejbLoad has been invoked before this method and has loaded the
    // attributes from persistent store into the TroubleTicketAttributes
    // object; so just return those attributes.
    //-----------------------------------------------------------------
    public TroubleTicketValue getAttributes(String[] attrNames)
            throws EJBException,
            IllegalArgumentException {

        TroubleTicketValueImpl ttValOut = new TroubleTicketValueImpl();

        //If no desired attrs are specified, return all.
        //NB. tried to return "this" since the Entity Bean is a TroubleTicketValue,
        //but I get a MarshalException - the Entity Bean is not serializable.
        //exception text is:
        //  java.rmi.MarshalException: error marshalling return; nested exception is:
        //  java.io.NotSerializableException: weblogic.ejb20.internal.EntityEJBContextImpl


        boolean loadAll = false;
        if (attrNames == null)
            loadAll = true;
        else if (attrNames.length == 0)
            loadAll = true;

        ttValOut.setLastUpdateVersionNumber(getLastUpdateVersionNumber());

        if (loadAll) {

            ttValOut.setAccountOwner(ttAttrs.getAccountOwner());
            ttValOut.setActivityDurationList(ttAttrs.getActivityDurationList());
            ttValOut.setRelatedAlarmList(ttAttrs.getRelatedAlarmList());
            ttValOut.setAdditionalTroubleInfoList(ttAttrs.getAdditionalTroubleInfoList());
            ttValOut.setCloseOutNarr(ttAttrs.getCloseOutNarr());
            ttValOut.setReceivedTime(ttAttrs.getReceivedTime());
            ttValOut.setRelatedTroubleTicketKeyList(ttAttrs.getRelatedTroubleTicketKeyList());
            ttValOut.setRepairActivityList(ttAttrs.getRepairActivityList());
            ttValOut.setRestoredTime(ttAttrs.getRestoredTime());
            ttValOut.setSPRoleAssignmentList(ttAttrs.getSPRoleAssignmentList());

            ttValOut.setTroubleDescription(ttAttrs.getTroubleDescription());
            ttValOut.setClearancePerson(ttAttrs.getClearancePerson());
            ttValOut.setTroubleFound(ttAttrs.getTroubleFound());
            ttValOut.setTroubleLocation(ttAttrs.getTroubleLocation());
            ttValOut.setTroubleNumList(ttAttrs.getTroubleNumList());
            ttValOut.setTroubledObject(ttAttrs.getTroubledObject());
            ttValOut.setTroubleType(ttAttrs.getTroubleType());
            ttValOut.setTroubleState(ttAttrs.getTroubleState());
            ttValOut.setTroubleStatus(ttAttrs.getTroubleStatus());
            ttValOut.setTroubleStatusTime(ttAttrs.getTroubleStatusTime());

            ttValOut.setAfterHoursRepairAuthority(ttAttrs.getAfterHoursRepairAuthority());
            ttValOut.setAuthorizationList(ttAttrs.getAuthorizationList());
            ttValOut.setCancelRequestedByCustomer(ttAttrs.getCancelRequestedByCustomer());
            ttValOut.setCloseOutVerification(ttAttrs.getCloseOutVerification());
            ttValOut.setCommitmentTime(ttAttrs.getCommitmentTime());
            ttValOut.setCommitmentTimeRequested(ttAttrs.getCommitmentTimeRequested());
            ttValOut.setCustomerRoleAssignmentList(ttAttrs.getCustomerRoleAssignmentList());
            ttValOut.setCustomerTroubleNum(ttAttrs.getCustomerTroubleNum());
            ttValOut.setDialog(ttAttrs.getDialog());
            ttValOut.setEscalationList(ttAttrs.getEscalationList());

            ttValOut.setInitiatingMode(ttAttrs.getInitiatingMode());
            ttValOut.setLastUpdateTime(ttAttrs.getLastUpdateTime());
            ttValOut.setMaintServiceCharge(ttAttrs.getMaintServiceCharge());
            ttValOut.setOutageDuration(ttAttrs.getOutageDuration());
            ttValOut.setPerceivedTroubleSeverity(ttAttrs.getPerceivedTroubleSeverity());
            ttValOut.setPreferredPriority(ttAttrs.getPreferredPriority());
            ttValOut.setRepeatReport(ttAttrs.getRepeatReport());
            ttValOut.setSuspectObjectList(ttAttrs.getSuspectObjectList());
            ttValOut.setTroubleDetectionTime(ttAttrs.getTroubleDetectionTime());
            ttValOut.setTroubleLocationInfoList(ttAttrs.getTroubleLocationInfoList());

            ttValOut.setTroubledObjectAccessFromTime(ttAttrs.getTroubledObjectAccessFromTime());
            ttValOut.setTroubledObjectAccessHoursList(ttAttrs.getTroubledObjectAccessHoursList());
            ttValOut.setTroubledObjectAccessToTime(ttAttrs.getTroubledObjectAccessToTime());
            ttValOut.setServiceUnavailableBeginTime(ttAttrs.getServiceUnavailableBeginTime());
            ttValOut.setServiceUnavailableEndTime(ttAttrs.getServiceUnavailableEndTime());
            ttValOut.setOriginator(ttAttrs.getOriginator());
            ttValOut.setTroubleSystemDN(ttAttrs.getTroubleSystemDN());
            ttValOut.setCustomer(ttAttrs.getCustomer());
        } else


            for (int x = 0; x < attrNames.length; x++) {

                if (attrNames[x].equals(TroubleTicketValue.ACCOUNTOWNER))
                    ttValOut.setAccountOwner(ttAttrs.getAccountOwner());
                else if (attrNames[x].equals(TroubleTicketValue.ACTIVITYDURATIONLIST))
                    ttValOut.setActivityDurationList(ttAttrs.getActivityDurationList());
                else if (attrNames[x].equals(TroubleTicketValue.RELATEDALARMLIST))
                    ttValOut.setRelatedAlarmList(ttAttrs.getRelatedAlarmList());
                else if (attrNames[x].equals(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST))
                    ttValOut.setAdditionalTroubleInfoList(ttAttrs.getAdditionalTroubleInfoList());
                else if (attrNames[x].equals(TroubleTicketValue.CLOSEOUTNARR))
                    ttValOut.setCloseOutNarr(ttAttrs.getCloseOutNarr());
                else if (attrNames[x].equals(TroubleTicketValue.RECEIVEDTIME))
                    ttValOut.setReceivedTime(ttAttrs.getReceivedTime());
                else if (attrNames[x].equals(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST))
                    ttValOut.setRelatedTroubleTicketKeyList(ttAttrs.getRelatedTroubleTicketKeyList());
                else if (attrNames[x].equals(TroubleTicketValue.REPAIRACTIVITYLIST))
                    ttValOut.setRepairActivityList(ttAttrs.getRepairActivityList());
                else if (attrNames[x].equals(TroubleTicketValue.RESTOREDTIME))
                    ttValOut.setRestoredTime(ttAttrs.getRestoredTime());
                else if (attrNames[x].equals(TroubleTicketValue.SPROLEASSIGNMENTLIST))
                    ttValOut.setSPRoleAssignmentList(ttAttrs.getSPRoleAssignmentList());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDESCRIPTION))
                    ttValOut.setTroubleDescription(ttAttrs.getTroubleDescription());
                else if (attrNames[x].equals(TroubleTicketValue.CLEARANCEPERSON))
                    ttValOut.setClearancePerson(ttAttrs.getClearancePerson());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLEFOUND))
                    ttValOut.setTroubleFound(ttAttrs.getTroubleFound());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLELOCATION))
                    ttValOut.setTroubleLocation(ttAttrs.getTroubleLocation());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLENUMLIST))
                    ttValOut.setTroubleNumList(ttAttrs.getTroubleNumList());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDOBJECT))
                    ttValOut.setTroubledObject(ttAttrs.getTroubledObject());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLETYPE))
                    ttValOut.setTroubleType(ttAttrs.getTroubleType());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLESTATE))
                    ttValOut.setTroubleState(ttAttrs.getTroubleState());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLESTATUS))
                    ttValOut.setTroubleStatus(ttAttrs.getTroubleStatus());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLESTATUSTIME))
                    ttValOut.setTroubleStatusTime(ttAttrs.getTroubleStatusTime());
                else if (attrNames[x].equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY))
                    ttValOut.setAfterHoursRepairAuthority(ttAttrs.getAfterHoursRepairAuthority());
                else if (attrNames[x].equals(TroubleTicketValue.AUTHORIZATIONLIST))
                    ttValOut.setAuthorizationList(ttAttrs.getAuthorizationList());
                else if (attrNames[x].equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER))
                    ttValOut.setCancelRequestedByCustomer(ttAttrs.getCancelRequestedByCustomer());
                else if (attrNames[x].equals(TroubleTicketValue.CLOSEOUTVERIFICATION))
                    ttValOut.setCloseOutVerification(ttAttrs.getCloseOutVerification());
                else if (attrNames[x].equals(TroubleTicketValue.COMMITMENTTIME))
                    ttValOut.setCommitmentTime(ttAttrs.getCommitmentTime());
                else if (attrNames[x].equals(TroubleTicketValue.COMMITMENTTIMEREQUESTED))
                    ttValOut.setCommitmentTimeRequested(ttAttrs.getCommitmentTimeRequested());
                else if (attrNames[x].equals(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST))
                    ttValOut.setCustomerRoleAssignmentList(ttAttrs.getCustomerRoleAssignmentList());
                else if (attrNames[x].equals(TroubleTicketValue.CUSTOMERTROUBLENUM))
                    ttValOut.setCustomerTroubleNum(ttAttrs.getCustomerTroubleNum());
                else if (attrNames[x].equals(TroubleTicketValue.DIALOG))
                    ttValOut.setDialog(ttAttrs.getDialog());
                else if (attrNames[x].equals(TroubleTicketValue.ESCALATIONLIST))
                    ttValOut.setEscalationList(ttAttrs.getEscalationList());
                else if (attrNames[x].equals(TroubleTicketValue.INITIATINGMODE))
                    ttValOut.setInitiatingMode(ttAttrs.getInitiatingMode());
                else if (attrNames[x].equals(TroubleTicketValue.LASTUPDATETIME))
                    ttValOut.setLastUpdateTime(ttAttrs.getLastUpdateTime());
                else if (attrNames[x].equals(TroubleTicketValue.MAINTSERVICECHARGE))
                    ttValOut.setMaintServiceCharge(ttAttrs.getMaintServiceCharge());
                else if (attrNames[x].equals(TroubleTicketValue.OUTAGEDURATION))
                    ttValOut.setOutageDuration(ttAttrs.getOutageDuration());
                else if (attrNames[x].equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY))
                    ttValOut.setPerceivedTroubleSeverity(ttAttrs.getPerceivedTroubleSeverity());
                else if (attrNames[x].equals(TroubleTicketValue.PREFERREDPRIORITY))
                    ttValOut.setPreferredPriority(ttAttrs.getPreferredPriority());
                else if (attrNames[x].equals(TroubleTicketValue.REPEATREPORT))
                    ttValOut.setRepeatReport(ttAttrs.getRepeatReport());
                else if (attrNames[x].equals(TroubleTicketValue.SUSPECTOBJECTLIST))
                    ttValOut.setSuspectObjectList(ttAttrs.getSuspectObjectList());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDETECTIONTIME))
                    ttValOut.setTroubleDetectionTime(ttAttrs.getTroubleDetectionTime());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST))
                    ttValOut.setTroubleLocationInfoList(ttAttrs.getTroubleLocationInfoList());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME))
                    ttValOut.setTroubledObjectAccessFromTime(ttAttrs.getTroubledObjectAccessFromTime());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST))
                    ttValOut.setTroubledObjectAccessHoursList(ttAttrs.getTroubledObjectAccessHoursList());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME))
                    ttValOut.setTroubledObjectAccessToTime(ttAttrs.getTroubledObjectAccessToTime());
                else if (attrNames[x].equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME))
                    ttValOut.setServiceUnavailableBeginTime(ttAttrs.getServiceUnavailableBeginTime());
                else if (attrNames[x].equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME))
                    ttValOut.setServiceUnavailableEndTime(ttAttrs.getServiceUnavailableEndTime());
                else if (attrNames[x].equals(TroubleTicketValue.ORIGINATOR))
                    ttValOut.setOriginator(ttAttrs.getOriginator());
                else if (attrNames[x].equals(TroubleTicketValue.TROUBLESYSTEMDN))
                    ttValOut.setTroubleSystemDN(ttAttrs.getTroubleSystemDN());
                else if (attrNames[x].equals(TroubleTicketValue.CUSTOMER))
                    ttValOut.setCustomer(ttAttrs.getCustomer());
                else {
                    log("TTEntityBean::ttValOut.setAttributes - unknown attribute: " + attrNames[x]);
                    throw new IllegalArgumentException();
                }

            }


        return ttValOut;

    }


    //-----------------------------------------------------------------
    // Set a bunch of attributes, returning a TTValue object.
    //
    // ejbLoad has been called before this method, so the current
    // attribute set has been refreshed.  ejbStore will be called after
    // this method, in which case the populated attributes will be
    // written to persistent store.
    //
    //-----------------------------------------------------------------
    //public TroubleTicketValue setAttributes(TroubleTicketValue ttValIn)
    public void setAttributes(TroubleTicketValue ttValIn, boolean resyncRequired)
            throws EJBException,
            IllegalAttributeValueException,
            IllegalArgumentException,
            ResyncRequiredException
            //javax.oss.UnsupportedAttributeException
    {
        long lastUpdateNumber = getLastUpdateVersionNumber();

        //lastUpdateNumber is restored from the DB at ejbLoad
        //and stored in the DB at ejbStore...
        if ((ttValIn.getLastUpdateVersionNumber() != lastUpdateNumber) &&
                (resyncRequired == true)) {
            throw new javax.oss.ResyncRequiredException();
        }

        //otherwise just increment lastUpdateNumber
        lastUpdateNumber++;
        setLastUpdateVersionNumber(lastUpdateNumber);

        //if this method is being called externally, it is a "setTroubleTicket"
        //call.  In this csae we need to retain the troubleTicketValue for the
        //AVC event which is sent after a successful update.
        if (currentMethod == NONE) {
            currentMethod = SET;
            sendEvent = AVC;
            AVCAttributes = ttValIn;
        }

        Logger.log("TTBean::setAttributes");


        String[] attrNames = ttValIn.getPopulatedAttributeNames();
        int size = attrNames.length;
        Logger.log(size + " Attributes to set:");
        for (int x = 0; x < size; x++) {
            Logger.log(attrNames[x]);
        }

        //First set the Entity Bean's attribute to that passed in the value object.
        for (int x = 0; x < size; x++) {
            String attrName = attrNames[x];
            //log("TTEntityBean:setAttributes setting: " + attrName);
            Object value = null;

            try {
                value = ttValIn.getAttributeValue(attrName);
            } catch (java.lang.IllegalArgumentException iaex) {
                //throw iaex;
                throw new javax.oss.IllegalArgumentException("IllegalArgumentException");
            } catch (java.lang.IllegalStateException isex) {
                throw new javax.oss.IllegalArgumentException("IllegalStateException");
            } catch (javax.oss.UnsupportedAttributeException usax) {
                throw new javax.oss.IllegalArgumentException("IllegalStateException");
            }

            if (attrName.equals(TroubleTicketValue.ACCOUNTOWNER))
                setAccountOwner((PersonReach) value);
            else if (attrName.equals(TroubleTicketValue.ACTIVITYDURATIONLIST))
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
            else if (attrName.equals(TroubleTicketValue.TROUBLEDESCRIPTION)) {
                setTroubleDescription((String) value);
            } else if (attrName.equals(TroubleTicketValue.CLEARANCEPERSON))
                setClearancePerson((PersonReach) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEFOUND)) {
                Integer val = (Integer) value;
                setTroubleFound(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.TROUBLELOCATION))
                setTroubleLocation((String) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLENUMLIST))
                setTroubleNumList((String[]) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECT))
                setTroubledObject((String) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLETYPE)) {
                Integer val = (Integer) value;
                setTroubleType(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.TROUBLESTATE)) {

                Integer val = (Integer) value;
                int newState = val.intValue();
                int oldState = ttAttrs.getTroubleState();   //the current TroubleTicketState
                //----------------------------------------------------------------
                // ensure that the state transition is valid in accordance with
                // X.790/NMF601
                //----------------------------------------------------------------
                if (currentMethod == SET) {
                    if (newState != oldState)    //WIPRO
                    {
                        if (ValidateStateTransition(newState, oldState) == false) {
                            String oss = ttifReflector.getEnumFieldName(TroubleState.class, oldState);
                            String nss = ttifReflector.getEnumFieldName(TroubleState.class, newState);
                            String reason = ("Invalid TroubleTicket State transition: old value: " + oss + ", new value: " + nss);
                            throw new IllegalAttributeValueException(TroubleTicketValue.TROUBLESTATE, reason);
                        }
                    }
                } else {
                    if (currentMethod == CREATE) {
                        //Valid states for create are QUEUED or OPENACTIVE
                        if ((newState != TroubleState.QUEUED) &&
                                (newState != TroubleState.OPENACTIVE)) {
                            //to do: make interface reflector a singleton
                            //String nss = TroubleTicketInterfaceReflector.getInstance().getInterfaceFieldName(TroubleTicketState, newState);
                            //String reason = ("Invalid TroubleTicket State transition: old value: " + oss + "new value: " + nss);
                            String reason = ("Invalid initial TroubleState: " + newState);
                            throw new IllegalAttributeValueException(TroubleTicketValue.TROUBLESTATE, reason);
                        }
                    }
                }

                setTroubleState(newState);

            } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUS)) {
                Integer val = (Integer) value;
                oldStatus = ttAttrs.getTroubleStatus();
                newStatus = val.intValue();
                if (oldStatus != newStatus) {               //WIPRO
                    setTroubleStatus(newStatus);

                    //--------------------------------------------------------------
                    // Send a TroubleTicketStatusChangeEvent on any change to
                    // the "Status" attribute.  Do it after setting all the attrs.
                    //--------------------------------------------------------------
                    if (currentMethod == SET)
                        sendEvent = STATUSCHANGE;
                }          //WIPRO
            } else if (attrName.equals(TroubleTicketValue.TROUBLESTATUSTIME))
                setTroubleStatusTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY)) {
                Boolean val = (Boolean) value;
                setAfterHoursRepairAuthority(val.booleanValue());
            } else if (attrName.equals(TroubleTicketValue.AUTHORIZATIONLIST))
                setAuthorizationList((Authorization[]) value);
            else if (attrName.equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER)) {
                Boolean val = (Boolean) value;
                setCancelRequestedByCustomer(val.booleanValue());
            } else if (attrName.equals(TroubleTicketValue.CLOSEOUTVERIFICATION)) {
                Integer val = (Integer) value;
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
                Integer val = (Integer) value;
                setInitiatingMode(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.LASTUPDATETIME))
                setLastUpdateTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.MAINTSERVICECHARGE)) {
                Boolean val = (Boolean) value;
                setMaintServiceCharge(val.booleanValue());
            } else if (attrName.equals(TroubleTicketValue.OUTAGEDURATION)) {
                TimeLength val = (TimeLength) value;
                setOutageDuration(val);
            } else if (attrName.equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY)) {
                Integer val = (Integer) value;
                setPerceivedTroubleSeverity(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.PREFERREDPRIORITY)) {
                Integer val = (Integer) value;
                setPreferredPriority(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.REPEATREPORT)) {
                Integer val = (Integer) value;
                setRepeatReport(val.intValue());
            } else if (attrName.equals(TroubleTicketValue.SUSPECTOBJECTLIST))
                setSuspectObjectList((SuspectObject[]) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEDETECTIONTIME))
                setTroubleDetectionTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST))
                setTroubleLocationInfoList((TroubleLocationInfo[]) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME))
                setTroubledObjectAccessFromTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST))
                setTroubledObjectAccessHoursList((AccessHours[]) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME))
                setTroubledObjectAccessToTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME))
                setServiceUnavailableBeginTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME))
                setServiceUnavailableEndTime((java.util.Date) value);
            else if (attrName.equals(TroubleTicketValue.ORIGINATOR))
                setOriginator((String) value);
            else if (attrName.equals(TroubleTicketValue.TROUBLESYSTEMDN))
                setTroubleSystemDN((String) value);
            else if (attrName.equals(TroubleTicketValue.CUSTOMER))
                setCustomer((PersonReach) value);
            else {
                log("TTEntityBean::setAttributes - unknown attribute: " + attrName);
                throw new IllegalArgumentException();
            }

        }

    }

    //validate TroubleState transition
    public boolean ValidateStateTransition(int newState, int oldState) {

        Logger.log("ValidateStateTransition: old: " + oldState + ", new: " + newState);

        //can switch to disabled from any state
        if (newState == TroubleState.DISABLED)
            return true;

        //can switch to and from UNKNOWN from any state
        if ((oldState == TroubleState.UNKNOWNTROUBLESTATE) ||
                (newState == TroubleState.UNKNOWNTROUBLESTATE))
            return true;

        //QUEUED can go to OPENACTIVE or CLOSED
        if (oldState == TroubleState.QUEUED)
            if ((newState == TroubleState.OPENACTIVE) ||
                    (newState == TroubleState.CLOSED))
                return true;

        //OPENACTIVE can go to CLEARED, DEFERRED or CLOSED
        if (oldState == TroubleState.OPENACTIVE)
            if ((newState == TroubleState.CLEARED) ||
                    (newState == TroubleState.DEFERRED) ||
                    (newState == TroubleState.CLOSED))
                return true;

        //DEFERRED and CLEARED can go to OPENACTIVE or CLOSED
        if ((oldState == TroubleState.DEFERRED) ||
                (oldState == TroubleState.CLEARED))
            if ((newState == TroubleState.OPENACTIVE) ||
                    (newState == TroubleState.CLOSED))
                return true;

        return false;
    }


    //-----------------------------------------------------------------------------------
    //
    // business methods
    //
    //-----------------------------------------------------------------------------------
    public void closeTroubleTicket(int closeOutStatus, String closeOutNarr)
            throws javax.oss.trouble.CloseException {

        currentMethod = CLOSE;
        sendEvent = CLOSEOUT;

        String ttId = this.troubleTicketKey.getTroubleTicketPrimaryKey();
        log("TTEntityBean.closeTroubleTicket: Key: " + ttId);

        //only invalid state transition here is no transition - don't close
        //a TT if its already closed.
        if (getTroubleState() == TroubleState.CLOSED) {
            throw new CloseException("Trouble Ticket already closed");
        }

        setTroubleState(TroubleState.CLOSED);
        setCloseOutNarr(closeOutNarr);
        //todo - could validate status vis-a-vis state
        setTroubleStatus(closeOutStatus);

        //ejbStore will be called hereafter to update the persistent store

    }

    public void cancelTroubleTicket(int closeOutStatus, String closeOutNarr)
            throws javax.oss.trouble.CancelException      //TODO - review exceptions
    {

        currentMethod = CANCEL;
        sendEvent = CANCELLATION;

        String ttId = this.troubleTicketKey.getTroubleTicketPrimaryKey();
        log("TTEntityBean.cancelTroubleTicket: Key: " + ttId);

        //only invalid state transition here is no transition - don't close
        //a TT if its already closed.
        if (getTroubleState() == TroubleState.CLOSED) {
            throw new CancelException("Trouble Ticket already closed");
        }

        setTroubleState(TroubleState.CLOSED);
        setCloseOutNarr(closeOutNarr);
        //todo - could validate status vis-a-vis state
        setTroubleStatus(closeOutStatus);

        //ejbStore will be called hereafter to update the persistent store

    }

    //PG There is a BUG here why going to the DB for this type
    //of operation ????
    public void escalateTroubleTicket(EscalationList escalation)
            throws EJBException,
            EscalateException {

        currentMethod = ESCALATE;
        sendEvent = AVC;

        String ttId = this.troubleTicketKey.getTroubleTicketPrimaryKey();
        log("TTEntityBean.escalateTroubleTicket: Key: " + ttId);


        //PG that should be done in the ejbStore method...
        //reuse the DBAccess "applyMVAUpdate" method.  This method reads
        //the multi value attribute from the back end and applies the update
        //(add or remove)

        TroubleTicketValue ttVal = new TroubleTicketValueImpl();
        ttVal.setEscalationList(escalation);

        int modifier = escalation.getModifier();
        if ((modifier == MultiValueList.ADD) ||
                (modifier == MultiValueList.REMOVE)) {
            java.util.Vector escVect = new java.util.Vector();
            escVect.add("EscalationList");  //attribute name for the "select" statement
            try {
                DBAccess.applyMVAUpdate(ttVal, ttId, escVect);
            } catch (ObjectNotFoundException ex) {
                throw new EscalateException("ObjectNotFoundException " + ex.getMessage());
            } catch (SQLException ex) {
                log("TTEntityBean.escalateTT: caught SQLException");
                throw new EscalateException("SQLException " + ex.getMessage());
            } catch (IllegalAttributeValueException ex) {
                throw new EscalateException("IllegalAttributeValueException" + ex.getMessage());
            }
        }
        //set the new attributes value object to enable the AVC event
        AVCAttributes = ttVal;

        //make sure that a "set" is also done properly...
        setEscalationList(ttVal.getEscalationList());
        //ejbLoad will be called by the container after this which will do the update

    }


    //-----------------------------------------------------------------------------------
    //
    // business methods defined in Home Interface
    //
    //-----------------------------------------------------------------------------------


    private TroubleTicketKeyResult createTroubleTicket(TroubleTicketValue ttVal) {

        //For the create operation, return TroubleTicketKeyResults for both
        //failed and successful creates.
        TroubleTicketKeyResult keyResult = new TroubleTicketKeyResultImpl();

        //set the primary key
        TroubleTicketKey ttKey = createTTKey();
        ttVal.setTroubleTicketKey(ttKey);
        keyResult.setTroubleTicketKey(ttKey);

        //initialize lastUpdateNumber

        ((TroubleTicketValueImpl) ttVal).setLastUpdateVersionNumber(0);

        int updates = 0;
        try {
            updates = DBAccess.insert(ttVal, true);
        } catch (SQLException sqlex) {
            log("createTroubleTicket: building TroubleTicketKeyResultImpl");
            keyResult.setSuccess(false);
            keyResult.setException(sqlex);
        }
        //Q is this possible?
        if (updates <= 0) {
            keyResult.setSuccess(false);
            keyResult.setException(new CreateException("Could not create TroubleTicket with primary key: " + ttKey + "(updates = 0)"));
        } else {
            keyResult.setSuccess(true);
            TTEventPublisher.getInstance().sendCreateEvent(ttVal);
        }

        return keyResult;

    }

    private TroubleTicketKeyResult setTroubleTicket(TroubleTicketValue ttVal) {

        log("TTEntityBean.setTroubleTicket");
        //Only return TroubleTicketKeyResults for the set operations which FAIL
        //TroubleTicketKeyResult keyResult = new TroubleTicketKeyResultImpl();

        //validate the key
        TroubleTicketKey ttKey = ttVal.getTroubleTicketKey();
        if (ttKey == null) {
            return new TroubleTicketKeyResultImpl(ttKey,
                    false,
                    new SetException("Null TroubleTicket Key"));
        }

        log("TTEntityBean.setTroubleTicket (2)");

        String ttPrimaryKey = ttKey.getTroubleTicketPrimaryKey();
        if (ttPrimaryKey == null) {
            return new TroubleTicketKeyResultImpl(ttKey,
                    false,
                    new SetException("Null TroubleTicket Primary Key"));
        }

        int updates = 0;
        log("TTEntityBean.setTroubleTicket (3)");

        try {
            updates = DBAccess.updateByKey(ttVal, false);
        }
                //exceptions caught here are SQLException, ObjectNotFoundException,
                //and IllegalAttributeVaueExcpetion.  Put the exceptiom into the TTKeyResult
        catch (Exception ex) {
            log("setTroubleTicket: building TroubleTicketKeyResultImpl");
            return new TroubleTicketKeyResultImpl(ttKey, false, ex);
        }

        if (updates <= 0) {
            return new TroubleTicketKeyResultImpl(ttKey,
                    false,
                    new SetException("TroubleTicket with primary key: " + ttPrimaryKey + " does not exist"));
        }
        // WIPRO
        else {
            if (sendEvent != CANCEL && sendEvent != CLOSE) {
                if (ttVal.isPopulated(TROUBLESTATUS))
                    sendEvent = STATUSCHANGE;
                else
                    sendEvent = AVC;
            }
            switch (sendEvent) {

                case AVC:
                    TTEventPublisher.getInstance().sendAVCEvent(ttVal);
                    break;

                case STATUSCHANGE:
                    TTEventPublisher.getInstance().sendStatusChangeEvent(ttVal.getTroubleTicketKey(), ttVal.getTroubleStatus(), ttVal.getTroubleStatusTime(), ttVal.getTroubleState());
                    break;

                case CANCEL:
                    TTEventPublisher.getInstance().sendCancellationEvent(ttVal.getTroubleTicketKey(), ttVal.getCloseOutNarr());
                    break;

                case CLOSE:
                    TTEventPublisher.getInstance().sendCloseOutEvent(ttVal);
            }
        }
        //WIPRO

        //return a "success" ttKeyResult

        return new TroubleTicketKeyResultImpl(ttKey, true, null);

    }


    //query troubletickets - generic query method
    public TroubleTicketValueIterator
            ejbHomeQueryTroubleTickets(QueryValue queryValue,
                                       String[] attrNames)
            throws FinderException,
            EJBException,
            IllegalArgumentException {

        log("TTEntityBean:ejbHomeQueryTroubleTickets");

        //lookup the stateful session bean TroubleTicketValueIteratorHome
        TroubleTicketValueIteratorHome ttValueIterHome = null;
        try {
            ttValueIterHome = (TroubleTicketValueIteratorHome)
                    initCtx.lookup("java:comp/env/ejb/TroubleTicketValueIteratorHome");
        } catch (NamingException nex) {
            log("ejbHomeQueryTroubleTickets:  Naming exception caught on lookup of TroubleTicketValueIteratorHome");
            nex.printStackTrace();
        }

        //create the query via the QueryFactory
        Properties[] props = null;
        Operation queryOp = (Operation)
                QueryFactory.getInstance().create(queryValue, //contains query name
                        attrNames,
                        props);

        //create the stateful session bean
        //TroubleTicketValueIterator via its home.
        log("TTBean creating TTValueIterator for query...");
        TroubleTicketValueIterator retIter = null;
        TroubleTicketValueIteratorIF ejbIter = null;
        try {
            ejbIter = ttValueIterHome.create(queryOp);
            retIter = new TroubleTicketValueIteratorImpl(ejbIter);
        } catch (CreateException cex) {
            log("CreateException caught in TTEntityBean::ejbHomeQueryTroubleTickets (single template)");
            cex.printStackTrace();
            throw new EJBException("CreateException" + cex.getMessage());
        } catch (RemoteException rex) {
            log("RemoteException caught in TTEntityBean::ejbHomeQueryTroubleTickets (single template)");
            rex.printStackTrace();
            throw new EJBException("RemoteException" + rex.getMessage());
        }

        log("ejbHomeQueryTroubleTickets: created TroubleTicketValueIterator OK");

        return retIter;

    }


    /**
     * getTroubleTickets (by keys)
     *
     * @param ttKeys            TroubleTicketKey[]
     * @param attrNames         String[] - desired attributes
     * @return                  TroubleTicketValue[]
     * @exception               javax.ejb.EJBException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketValue[]
            ejbHomeGetTroubleTickets(TroubleTicketKey[] ttKeys,
                                     String[] attrNames)
            throws FinderException {

        log("ejbHomeGetTroubleTickets - keys version - For " + ttKeys.length + " keys");

        //-------------------------------------------------------------------------
        // from the OSS/J TT spec:
        //
        // "Each object in the TroubleTicketValue[] array is Java value object
        // corresponding to the provided key in the input array. If there is no
        // object matching a specific key then a null value is returned at the
        // index corresponding to the key array. If there is no trouble ticket
        // matching any of the keys then the javax.ejb.FinderException exception
        // is raised."
        //-------------------------------------------------------------------------
        java.util.Vector ttValsVect = new java.util.Vector();
        TroubleTicketValue ttValue = null;

        boolean ttFound = false;
        String ttId = null;
        for (int x = 0; x < ttKeys.length; x++) {
            ttValue = null;
            ttId = ttKeys[x].getTroubleTicketPrimaryKey();
            log("ejbHomeGetTroubleTickets - looking for:" + ttId);
            try {
                ttValue = DBAccess.selectByKey(ttId, attrNames);
            } catch (SQLException ex) {
                log("ejbHomeGetTroubleTickets: SQLException exception occurred");
                log("Exception text: " + ex.getMessage());
            }
            ttValsVect.addElement(ttValue);
            log("ejbHomeGetTroubleTickets: [" + ttId + "] found.");
            if (ttValue != null)
                ttFound = true;
        }

        if (!ttFound)
            throw new FinderException("ejbHomeGetTroubleTickets - No matching TTs found");

        return (TroubleTicketValue[]) (ttValsVect.toArray(new TroubleTicketValue[0]));

    }


    /**
     * Generic method to process templated operations (TroubleTicketValue[] template)
     *
     * @param templates            TroubleTicketValue[]
     * @param attrNames            attrNames
     * @return                  TroubleTicketValueIterator[] - used by client to iterate over result set.
     * @exception               javax.ejb.EJBException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketValueIterator
            ejbHomeGetTroubleTickets(TroubleTicketValue[] templates,
                                     String[] attrNames)
            throws EJBException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.CreateException,
            javax.oss.IllegalArgumentException,
            java.sql.SQLException {


        log("ejbHomeGetTroubleTickets - templates version");

        TroubleTicketValueIteratorHome ttValueIterHome = null;

        ttValueIterHome = Finder.getInstance().lookupTTValueIteratorHome();
        if (ttValueIterHome == null)
            throw new javax.ejb.ObjectNotFoundException("ejbHomeGetTroubleTickets: Lookup of TTValueIteratorHome failed");


        //create the "GetTroubleTicketsOp" bulk operation via the factory

        log("ejbHomeGetTroubleTickets - calling BulkOperationFactory.create...");

        Operation templatedGetOp = (Operation)
                BulkOperationFactory.getInstance().create("GetTroubleTicketsOp",
                        templates,
                        attrNames);

        log("ejbHomeGetTroubleTickets - calling GetTroubleTicketsOp.initialize()");

        templatedGetOp.initialize();   //start the operation

        log("ejbHomeGetTroubleTickets - creating TroubleTicketValueIterator via Home");

        //create the stateful session bean
        //TroubleTicketValueIterator via its home.

        TroubleTicketValueIterator retIter = null;
        TroubleTicketValueIteratorIF ejbIter = null;

        try {
            ejbIter = ttValueIterHome.create(templatedGetOp);
            retIter = new TroubleTicketValueIteratorImpl(ejbIter);

        } catch (CreateException cex) {
            log("TTEntityBean.ejbHomeGetTroubleTickets: CreateException - create TroubleTicketValueIterator Bean failed");
            cex.printStackTrace();
            throw cex;
        } catch (RemoteException rex) {
            log("TTEntityBean.ejbHomeGetTroubleTickets: RemoteException - create TroubleTicketValueIterator Bean failed");
            rex.printStackTrace();
            throw new EJBException("RemoteException" + rex.getMessage());
        }

        log("TTEntityBean.ejbHomeGetTroubleTickets: created  TroubleTicketValueIterator OK");

        return retIter;


    }



    //--------------------------------------------------------------------
    //
    // Generic implementations for the 4 types of Best Effort bulk calls:
    //
    // - Multiple Array
    // - Multiple Keys, single array
    // - Template (both single and multiple templates handled via the
    //   multiple template method
    //
    //--------------------------------------------------------------------


    /**
     * Generic method to process TroubleTicketValue[] array
     *
     * @param values            TroubleTicketValue[]
     * @param opType            operation type (enumerated type)
     * @param props             PropertyList - generic properties for the operation
     * @return                  TroubleTicketKeyResult[] - results for ttKeys affect by the operation
     * @exception               javax.ejb.EJBException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketKeyResult[]
            ejbHomeProcessValues(TroubleTicketValue[] values,
                                 int opType,
                                 PropertyList props)
            throws EJBException {

        //only possible operation types are CREATE and SET
        Vector krVect = new java.util.Vector();
        TroubleTicketKeyResult ttKeyRes = null;

        try {

            for (int x = 0; x < values.length; x++) {
                if (opType == OperationTypes.CREATE)
                    ttKeyRes = createTroubleTicket(values[x]);
                else if (opType == OperationTypes.SET)
                    ttKeyRes = setTroubleTicket(values[x]);

                if (ttKeyRes != null)
                    krVect.addElement(ttKeyRes);
            }

        } catch (Exception ex) {
            log("Exception caught in TTEntityBean::ejbHomeProcessValues");
            ex.printStackTrace();
            throw new EJBException("Exception" + ex.getMessage());
        }

        return (TroubleTicketKeyResult[]) krVect.toArray(new TroubleTicketKeyResult[0]);

    }


    /**
     * Generic method to process TroubleTicketKey[] array
     *
     * @param ttKeys            TroubleTicketKey[]
     * @param ttValue		   TroubleTicketValue used to set values
     * @param opType            operation type (enumerated type)
     * @param props             PropertyList - generic properties for the operation
     * @return                  TroubleTicketKeyResult[] - results for ttKeys affect by the operation
     * @exception               javax.ejb.EJBException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketKeyResult[]
            ejbHomeProcessKeys(TroubleTicketKey[] ttKeys,
                               TroubleTicketValue ttValue,
                               int opType,
                               PropertyList props)
            throws EJBException {
        log("TTEntityBean.ejbHomeProcessKeys");

        //possible op types are set,close,cancel,escalate.
        //these are all update types, so re-use the "setTroubleTicket" method.

        Vector krVect = new java.util.Vector();
        TroubleTicketKeyResult ttKeyRes = null;
        TroubleTicketValue tmpValue = null;

        try {
            for (int x = 0; x < ttKeys.length; x++) {

                log("ejbHomeProcessKeys: key " + ttKeys[x].getTroubleTicketPrimaryKey());
                ttKeyRes = null;
                switch (opType) {
                    //PG BUG were trying to set the TT matching key
                    //with value one way is to change ttValue with
                    //the key and apply the update
                    case OperationTypes.SET:
                        ttValue.setTroubleTicketKey(ttKeys[x]);
                        ttKeyRes = setTroubleTicket(ttValue);
                        break;
                        //cancel and close are treated the same...
                    case OperationTypes.CANCEL:
                        log("got cancel");

                        //WIPRO
                        sendEvent = CANCEL;
                        tmpValue = buildCloseTTValue(ttKeys[x], props);
                        ttKeyRes = setTroubleTicket(tmpValue);
                        break;
                        // WIPRO

                    case OperationTypes.CLOSE:
                        sendEvent = CLOSE;        //WIPRO
                        tmpValue = buildCloseTTValue(ttKeys[x], props);
                        ttKeyRes = setTroubleTicket(tmpValue);
                        break;
                    case OperationTypes.ESCALATE:
                        tmpValue = buildEscalateTTValue(ttKeys[x], props);
                        ttKeyRes = setTroubleTicket(tmpValue);
                        break;
                    default:
                        break;
                }

                if (ttKeyRes != null)
                    krVect.addElement(ttKeyRes);
            }

        } catch (Exception ex) {
            log("Exception caught in TTEntityBean::ejbHomeProcessKeys");
            ex.printStackTrace();
            throw new EJBException("Exception" + ex.getMessage());
        }

        return (TroubleTicketKeyResult[]) krVect.toArray(new TroubleTicketKeyResult[0]);

    }


    /**
     * Generic method to process templated operations (TroubleTicketValue[] template)
     *
     * @param templates            TroubleTicketValue[]
     * @param opType            operation type (enumerated type)
     * @param props             PropertyList - generic properties for the operation
     * @return                  TroubleTicketKeyResultIterator[] - used by client to iterate over result set.
     * @exception               javax.ejb.EJBException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketKeyResultIterator
            ejbHomeProcessTemplates(TroubleTicketValue[] templates,
                                    TroubleTicketValue value,
                                    boolean failuresOnly,
                                    int opType,
                                    PropertyList props)
            throws EJBException,
            javax.ejb.ObjectNotFoundException,
            javax.ejb.CreateException,
            javax.oss.IllegalArgumentException,
            java.sql.SQLException {
        //PG Bug Fix...
        switch (opType) {

            case OperationTypes.SET:
                break;
                //cancel and close are treated the same...
            case OperationTypes.CANCEL:
            case OperationTypes.CLOSE:
                value = buildCloseTTValue(null, props);
                break;
            case OperationTypes.ESCALATE:
                value = buildEscalateTTValue(null, props);
                break;
            default:
                break;
        }


        log("ejbHomeProcessTemplates");

        TroubleTicketKeyResultIteratorHome ttKeyResultIterHome = null;

        ttKeyResultIterHome = Finder.getInstance().lookupTTKeyResultIteratorHome();
        if (ttKeyResultIterHome == null)
            throw new javax.ejb.ObjectNotFoundException("ejbHomeProcessTemplates: Lookup of TTKeyResultIteratorHome failed");

        //create the "BestEffortTemplateOp" bulk operation via the factory

        log("ejbHomeProcessTemplates - calling BulkOperationFactory.create...");

        Operation templatedOp = (Operation)
                BulkOperationFactory.getInstance().create("BestEffortTemplateOp",
                        templates,
                        value,
                        failuresOnly,
                        opType,
                        props);


        log("ejbHomeProcessTemplates - calling BestEffortTemplateOp.initialize()");

        templatedOp.initialize();   //start the operation

        log("ejbHomeProcessTemplates - creating TroubleTicketKeyResultIterator via Home");

        //create the stateful session bean
        //TroubleTicketKeyResultIterator via its home.
        TroubleTicketKeyResultIteratorIF ejbIter = null;
        //VP
        TroubleTicketKeyResultIterator retIter = null;
        try {
            //VP
            ejbIter = ttKeyResultIterHome.create(templatedOp);
            retIter = new TroubleTicketKeyResultIteratorImpl(ejbIter);
        } catch (CreateException cex) {
            log("TTEntityBean.ejbHomeProcessTemplates: CreateException - create TroubleTicketKeyResultIterator Bean failed");
            cex.printStackTrace();
            throw cex;
        } catch (RemoteException rex) {
            log("TTEntityBean.ejbHomeProcessTemplates: RemoteException - create TroubleTicketKeyResultIterator Bean failed");
            rex.printStackTrace();
            throw new EJBException("RemoteException" + rex.getMessage());
        }

        log("TTEntityBean.ejbHomeProcessTemplates: created  TroubleTicketKeyResultIterator OK");

        return retIter;

    }


    private TroubleTicketKey createTTKey() {
        //---------------------------------------------------------------------
        // create a key value for this TroubleTicketKey
        // use current time in milliseconds
        //---------------------------------------------------------------------
        TroubleTicketKeyImpl ttKey = new TroubleTicketKeyImpl();
        long timeMillis = java.lang.System.currentTimeMillis();
        Long longTime = new Long(timeMillis);
        String strTime = longTime.toString();
        ttKey.setTroubleTicketPrimaryKey(strTime);
        ttKey.setApplicationDN(TTConfig.getInstance().getApplicationTypeDN());
        ttKey.setApplicationContext(TTConfig.getInstance().getApplicationContext());
        return ttKey;

    }

    private TroubleTicketValue buildCloseTTValue(TroubleTicketKey ttKey,
                                                 PropertyList props) {

        log("TTEntityBean.buildCloseTTValue");
        TroubleTicketValue ttValue = new TroubleTicketValueImpl();
        if (ttKey != null)
            ttValue.setTroubleTicketKey(ttKey);

        Property prop = props.getProperty("CloseOutStatus");
        int status = prop.getIntProperty();
        ttValue.setTroubleStatus(status);
        ttValue.setTroubleState(TroubleState.CLOSED);
        prop = props.getProperty("CloseOutNarr");
        String closedOutNarr = prop.getStringProperty();
        ttValue.setCloseOutNarr(closedOutNarr);
        return ttValue;

    }

    private TroubleTicketValue buildEscalateTTValue(TroubleTicketKey ttKey,
                                                    PropertyList props) {

        TroubleTicketValue ttValue = new TroubleTicketValueImpl();
        if (ttKey != null)
            ttValue.setTroubleTicketKey(ttKey);

        Property prop = props.getProperty("EscalationList");
        EscalationList escaList = (EscalationList) prop.getObjectProperty();

        ttValue.setEscalationList(escaList);
        return ttValue;
    }

    //--------------------------------------------------------------
    // You might also consider using WebLogic's log service
    //--------------------------------------------------------------
    private void log(String s) {
        Logger.log(s);

    }
}
