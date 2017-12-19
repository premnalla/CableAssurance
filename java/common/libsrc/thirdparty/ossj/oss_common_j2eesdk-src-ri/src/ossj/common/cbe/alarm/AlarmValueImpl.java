/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import java.util.Date;
import javax.oss.cbe.alarm.AlarmSpecificInfo;
import javax.oss.util.InteractionRecord;
import ossj.common.util.InteractionRecordImpl;
import javax.oss.cbe.alarm.CorrelatedNotification;
import javax.oss.cbe.alarm.Comment;
import javax.oss.cbe.alarm.AttributeValueChange;
import javax.oss.cbe.alarm.AlarmKey;
import javax.oss.cbe.alarm.AlarmValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.alarm.AlarmValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AlarmValueImpl
extends ossj.common.cbe.EntityValueImpl
implements AlarmValue
{ 

    /**
     * Constructs a new AlarmValueImpl with empty attributes.
     * 
     */

    public AlarmValueImpl() {
        super();
        setManagedEntityKeyInstance( new AlarmKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        AlarmValue.ACKNOWLEDGEMENT_INTERACTION_RECORD,
        AlarmValue.ADDITIONAL_TEXT,
        AlarmValue.ALARM_CHANGED_TIME,
        AlarmValue.ALARM_RAISED_TIME,
        AlarmValue.ALARM_SPECIFIC_INFO,
        AlarmValue.ALARM_TYPE,
        AlarmValue.ATTRIBUTE_CHANGES,
        AlarmValue.BACK_UP_OBJECT,
        AlarmValue.BACKED_UP,
        AlarmValue.BACKED_UP_STATUS,
        AlarmValue.CLEARING_INTERACTION_RECORD,
        AlarmValue.COMMENTS,
        AlarmValue.CORRELATED_NOTIFICATIONS,
        AlarmValue.NOTIFICATION_ID,
        AlarmValue.PERCEIVED_SEVERITY,
        AlarmValue.PROBABLE_CAUSE,
        AlarmValue.PROPOSED_REPAIR_ACTIONS,
        AlarmValue.SPECIFIC_PROBLEM,
        AlarmValue.SYSTEM_DN
    };

    private static final String[] settableAttributeNames = {
        AlarmValue.ACKNOWLEDGEMENT_INTERACTION_RECORD,
        AlarmValue.ADDITIONAL_TEXT,
        AlarmValue.ALARM_CHANGED_TIME,
        AlarmValue.ALARM_RAISED_TIME,
        AlarmValue.ALARM_SPECIFIC_INFO,
        AlarmValue.ALARM_TYPE,
        AlarmValue.ATTRIBUTE_CHANGES,
        AlarmValue.BACK_UP_OBJECT,
        AlarmValue.BACKED_UP,
        AlarmValue.BACKED_UP_STATUS,
        AlarmValue.CLEARING_INTERACTION_RECORD,
        AlarmValue.COMMENTS,
        AlarmValue.CORRELATED_NOTIFICATIONS,
        AlarmValue.NOTIFICATION_ID,
        AlarmValue.PERCEIVED_SEVERITY,
        AlarmValue.PROBABLE_CAUSE,
        AlarmValue.PROPOSED_REPAIR_ACTIONS,
        AlarmValue.SPECIFIC_PROBLEM,
        AlarmValue.SYSTEM_DN
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (AlarmValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (AlarmValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(AlarmValueImpl.settableAttributeNames);
            super.configureAttributes(anAttributeManager);
        }
    }

    /**
     * Holds the Attribute manager that manage the optional fields
    */
    private static AttributeManager attributemanager = null;

    protected AttributeManager getAttributeManager() {
        return attributemanager;
    }
    protected AttributeManager makeAttributeManager() {
        attributemanager = new AttributeManager();
        return attributemanager;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.alarm.AlarmKey makeAlarmKey(){
        return (AlarmKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForAttributeChanges() {
        return attributeTypeFor("attributeChanges");
    }

    public javax.oss.cbe.alarm.AttributeValueChange[] makeAttributeValueChanges(int nb, String type){
        if(type.equals("javax.oss.cbe.alarm.AttributeValueChange") || type.equals("ossj.common.cbe.alarm.AttributeValueChangeImpl")) {
            return new AttributeValueChangeImpl[nb];
        } else {
            return null;
        }
    }

    public String[] attributeTypeForComments() {
        return attributeTypeFor("comments");
    }

    public javax.oss.cbe.alarm.Comment[] makeComments(int nb, String type){
        if(type.equals("javax.oss.cbe.alarm.Comment") || type.equals("ossj.common.cbe.alarm.CommentImpl")) {
            return new CommentImpl[nb];
        } else {
            return null;
        }
    }

    public String[] attributeTypeForCorrelatedNotifications() {
        return attributeTypeFor("correlatedNotifications");
    }

    public javax.oss.cbe.alarm.CorrelatedNotification[] makeCorrelatedNotifications(int nb, String type){
        if(type.equals("javax.oss.cbe.alarm.CorrelatedNotification") || type.equals("ossj.common.cbe.alarm.CorrelatedNotificationImpl")) {
            return new CorrelatedNotificationImpl[nb];
        } else {
            return null;
        }
    }

    public String[] attributeTypeForAcknowledgementInteractionRecord() {
        return attributeTypeFor("acknowledgementInteractionRecord");
    }

    public String[] attributeTypeForClearingInteractionRecord() {
        return attributeTypeFor("clearingInteractionRecord");
    }

    public javax.oss.util.InteractionRecord makeInteractionRecord(String type){
        if(type.equals("javax.oss.util.InteractionRecord") || type.equals("ossj.common.util.InteractionRecordImpl")) {
            return new InteractionRecordImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForAlarmSpecificInfo() {
        return attributeTypeFor("alarmSpecificInfo");
    }

    public javax.oss.cbe.alarm.AlarmSpecificInfo makeAlarmSpecificInfo(String type){
        if(type.equals("javax.oss.cbe.alarm.AlarmSpecificInfo") || type.equals("ossj.common.cbe.alarm.AlarmSpecificInfoImpl")) {
            return new AlarmSpecificInfoImpl();
        } else {
            if(type.equals("javax.oss.cbe.alarm.ThresholdInfoType") || type.equals("ossj.common.cbe.alarm.ThresholdInfoTypeImpl")) {
                return new ossj.common.cbe.alarm.ThresholdInfoTypeImpl();
            } else {
                return null;
            }
        }
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        addEnumeration("perceivedSeverity", "javax.oss.cbe.alarm.PerceivedSeverity");
        addEnumeration("probableCause", "javax.oss.cbe.alarm.ProbableCause");
        addEnumeration("backedUpStatus", "javax.oss.cbe.alarm.BackedUpStatus");
        list[0] = "[Ljavax.oss.cbe.alarm.Comment;";
        addAttributeTypes("comments", list);
        list[0] = "java.lang.String";
        addAttributeTypes("systemDN", list);
        list[0] = "java.lang.String";
        addAttributeTypes("additionalText", list);
        list[0] = "javax.oss.util.InteractionRecord";
        addAttributeTypes("clearingInteractionRecord", list);
        list[0] = "javax.oss.util.InteractionRecord";
        addAttributeTypes("acknowledgementInteractionRecord", list);
        list[0] = "java.lang.String";
        addAttributeTypes("backUpObject", list);
        list[0] = "[Ljavax.oss.cbe.alarm.CorrelatedNotification;";
        addAttributeTypes("correlatedNotifications", list);
        list[0] = "javax.oss.cbe.alarm.AlarmSpecificInfo";
        addAttributeTypes("alarmSpecificInfo", list);
        list[0] = "[Ljavax.oss.cbe.alarm.AttributeValueChange;";
        addAttributeTypes("attributeChanges", list);
        list[0] = "boolean";
        addAttributeTypes("backedUp", list);
        list[0] = "java.util.Date";
        addAttributeTypes("alarmChangedTime", list);
        list[0] = "java.util.Date";
        addAttributeTypes("alarmRaisedTime", list);
        list[0] = "int";
        addAttributeTypes("alarmType", list);
        list[0] = "java.lang.String";
        addAttributeTypes("notificationId", list);
        list[0] = "java.lang.String";
        addAttributeTypes("specificProblem", list);
        list[0] = "java.lang.String";
        addAttributeTypes("proposedRepairActions", list);
    }

    private javax.oss.util.InteractionRecord _alarmvalueimpl_acknowledgementInteractionRecord = null;
    private java.lang.String _alarmvalueimpl_additionalText = null;
    private java.util.Date _alarmvalueimpl_alarmChangedTime = null;
    private java.util.Date _alarmvalueimpl_alarmRaisedTime = null;
    private javax.oss.cbe.alarm.AlarmSpecificInfo _alarmvalueimpl_alarmSpecificInfo = null;
    private int _alarmvalueimpl_alarmType;
    private javax.oss.cbe.alarm.AttributeValueChange[] _alarmvalueimpl_attributeChanges = null;
    private java.lang.String _alarmvalueimpl_backUpObject = null;
    private boolean _alarmvalueimpl_backedUp;
    private boolean _alarmvalueimpl_backedUpStatus;
    private javax.oss.util.InteractionRecord _alarmvalueimpl_clearingInteractionRecord = null;
    private javax.oss.cbe.alarm.Comment[] _alarmvalueimpl_comments = null;
    private javax.oss.cbe.alarm.CorrelatedNotification[] _alarmvalueimpl_correlatedNotifications = null;
    private java.lang.String _alarmvalueimpl_notificationId = null;
    private short _alarmvalueimpl_perceivedSeverity;
    private short _alarmvalueimpl_probableCause;
    private java.lang.String _alarmvalueimpl_proposedRepairActions = null;
    private java.lang.String _alarmvalueimpl_specificProblem = null;
    private java.lang.String _alarmvalueimpl_systemDN = null;


    /**
     * Changes the acknowledgementInteractionRecord to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new acknowledgementInteractionRecord for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAcknowledgementInteractionRecord(javax.oss.util.InteractionRecord value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.ACKNOWLEDGEMENT_INTERACTION_RECORD);
        _alarmvalueimpl_acknowledgementInteractionRecord = value;
    }


    /**
     * Returns this AlarmValueImpl's acknowledgementInteractionRecord
     * 
     * @return the acknowledgementInteractionRecord
     * 
    */

    public javax.oss.util.InteractionRecord getAcknowledgementInteractionRecord()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.ACKNOWLEDGEMENT_INTERACTION_RECORD);
        return _alarmvalueimpl_acknowledgementInteractionRecord;
    }

    /**
     * Changes the additionalText to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new additionalText for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAdditionalText(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.ADDITIONAL_TEXT);
        _alarmvalueimpl_additionalText = value;
    }


    /**
     * Returns this AlarmValueImpl's additionalText
     * 
     * @return the additionalText
     * 
    */

    public java.lang.String getAdditionalText()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.ADDITIONAL_TEXT);
        return _alarmvalueimpl_additionalText;
    }

    /**
     * Changes the alarmChangedTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new alarmChangedTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAlarmChangedTime(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.ALARM_CHANGED_TIME);
        _alarmvalueimpl_alarmChangedTime = value;
    }


    /**
     * Returns this AlarmValueImpl's alarmChangedTime
     * 
     * @return the alarmChangedTime
     * 
    */

    public java.util.Date getAlarmChangedTime()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.ALARM_CHANGED_TIME);
        return _alarmvalueimpl_alarmChangedTime;
    }

    /**
     * Changes the alarmKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new alarmKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAlarmKey(javax.oss.cbe.alarm.AlarmKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this AlarmValueImpl's alarmKey
     * 
     * @return the alarmKey
     * 
    */

    public javax.oss.cbe.alarm.AlarmKey getAlarmKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.alarm.AlarmKey)getEntityKey();
    }

    /**
     * Changes the alarmRaisedTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new alarmRaisedTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAlarmRaisedTime(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.ALARM_RAISED_TIME);
        _alarmvalueimpl_alarmRaisedTime = value;
    }


    /**
     * Returns this AlarmValueImpl's alarmRaisedTime
     * 
     * @return the alarmRaisedTime
     * 
    */

    public java.util.Date getAlarmRaisedTime()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.ALARM_RAISED_TIME);
        return _alarmvalueimpl_alarmRaisedTime;
    }

    /**
     * Changes the alarmSpecificInfo to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new alarmSpecificInfo for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAlarmSpecificInfo(javax.oss.cbe.alarm.AlarmSpecificInfo value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.ALARM_SPECIFIC_INFO);
        _alarmvalueimpl_alarmSpecificInfo = value;
    }


    /**
     * Returns this AlarmValueImpl's alarmSpecificInfo
     * 
     * @return the alarmSpecificInfo
     * 
    */

    public javax.oss.cbe.alarm.AlarmSpecificInfo getAlarmSpecificInfo()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.ALARM_SPECIFIC_INFO);
        return _alarmvalueimpl_alarmSpecificInfo;
    }

    /**
     * Changes the alarmType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new alarmType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAlarmType(int value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.ALARM_TYPE);
        _alarmvalueimpl_alarmType = value;
    }


    /**
     * Returns this AlarmValueImpl's alarmType
     * 
     * @return the alarmType
     * 
    */

    public int getAlarmType()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.ALARM_TYPE);
        return _alarmvalueimpl_alarmType;
    }

    /**
     * Changes the attributeChanges to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new attributeChanges for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAttributeChanges(javax.oss.cbe.alarm.AttributeValueChange[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.ATTRIBUTE_CHANGES);
        _alarmvalueimpl_attributeChanges = value;
    }


    /**
     * Returns this AlarmValueImpl's attributeChanges
     * 
     * @return the attributeChanges
     * 
    */

    public javax.oss.cbe.alarm.AttributeValueChange[] getAttributeChanges()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.ATTRIBUTE_CHANGES);
        return _alarmvalueimpl_attributeChanges;
    }

    /**
     * Changes the backUpObject to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new backUpObject for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBackUpObject(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.BACK_UP_OBJECT);
        _alarmvalueimpl_backUpObject = value;
    }


    /**
     * Returns this AlarmValueImpl's backUpObject
     * 
     * @return the backUpObject
     * 
    */

    public java.lang.String getBackUpObject()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.BACK_UP_OBJECT);
        return _alarmvalueimpl_backUpObject;
    }

    /**
     * Changes the backedUp to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new backedUp for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBackedUp(boolean value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.BACKED_UP);
        _alarmvalueimpl_backedUp = value;
    }


    /**
     * Returns this AlarmValueImpl's backedUp
     * 
     * @return the backedUp
     * 
    */

    public boolean isBackedUp()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.BACKED_UP);
        return _alarmvalueimpl_backedUp;
    }

    /**
     * Changes the backedUpStatus to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new backedUpStatus for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBackedUpStatus(boolean value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.BACKED_UP_STATUS);
        _alarmvalueimpl_backedUpStatus = value;
    }


    /**
     * Returns this AlarmValueImpl's backedUpStatus
     * 
     * @return the backedUpStatus
     * 
    */

    public boolean getBackedUpStatus()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.BACKED_UP_STATUS);
        return _alarmvalueimpl_backedUpStatus;
    }

    /**
     * Changes the clearingInteractionRecord to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new clearingInteractionRecord for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setClearingInteractionRecord(javax.oss.util.InteractionRecord value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.CLEARING_INTERACTION_RECORD);
        _alarmvalueimpl_clearingInteractionRecord = value;
    }


    /**
     * Returns this AlarmValueImpl's clearingInteractionRecord
     * 
     * @return the clearingInteractionRecord
     * 
    */

    public javax.oss.util.InteractionRecord getClearingInteractionRecord()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.CLEARING_INTERACTION_RECORD);
        return _alarmvalueimpl_clearingInteractionRecord;
    }

    /**
     * Changes the comments to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new comments for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setComments(javax.oss.cbe.alarm.Comment[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.COMMENTS);
        _alarmvalueimpl_comments = value;
    }


    /**
     * Returns this AlarmValueImpl's comments
     * 
     * @return the comments
     * 
    */

    public javax.oss.cbe.alarm.Comment[] getComments()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.COMMENTS);
        return _alarmvalueimpl_comments;
    }

    /**
     * Changes the correlatedNotifications to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new correlatedNotifications for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCorrelatedNotifications(javax.oss.cbe.alarm.CorrelatedNotification[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.CORRELATED_NOTIFICATIONS);
        _alarmvalueimpl_correlatedNotifications = value;
    }


    /**
     * Returns this AlarmValueImpl's correlatedNotifications
     * 
     * @return the correlatedNotifications
     * 
    */

    public javax.oss.cbe.alarm.CorrelatedNotification[] getCorrelatedNotifications()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.CORRELATED_NOTIFICATIONS);
        return _alarmvalueimpl_correlatedNotifications;
    }

    /**
     * Changes the notificationId to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new notificationId for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setNotificationId(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.NOTIFICATION_ID);
        _alarmvalueimpl_notificationId = value;
    }


    /**
     * Returns this AlarmValueImpl's notificationId
     * 
     * @return the notificationId
     * 
    */

    public java.lang.String getNotificationId()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.NOTIFICATION_ID);
        return _alarmvalueimpl_notificationId;
    }

    /**
     * Changes the perceivedSeverity to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new perceivedSeverity for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPerceivedSeverity(short value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.PERCEIVED_SEVERITY);
        _alarmvalueimpl_perceivedSeverity = value;
    }


    /**
     * Returns this AlarmValueImpl's perceivedSeverity
     * 
     * @return the perceivedSeverity
     * 
    */

    public short getPerceivedSeverity()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.PERCEIVED_SEVERITY);
        return _alarmvalueimpl_perceivedSeverity;
    }

    /**
     * Changes the probableCause to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new probableCause for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProbableCause(short value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.PROBABLE_CAUSE);
        _alarmvalueimpl_probableCause = value;
    }


    /**
     * Returns this AlarmValueImpl's probableCause
     * 
     * @return the probableCause
     * 
    */

    public short getProbableCause()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.PROBABLE_CAUSE);
        return _alarmvalueimpl_probableCause;
    }

    /**
     * Changes the proposedRepairActions to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new proposedRepairActions for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProposedRepairActions(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.PROPOSED_REPAIR_ACTIONS);
        _alarmvalueimpl_proposedRepairActions = value;
    }


    /**
     * Returns this AlarmValueImpl's proposedRepairActions
     * 
     * @return the proposedRepairActions
     * 
    */

    public java.lang.String getProposedRepairActions()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.PROPOSED_REPAIR_ACTIONS);
        return _alarmvalueimpl_proposedRepairActions;
    }

    /**
     * Changes the specificProblem to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new specificProblem for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSpecificProblem(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.SPECIFIC_PROBLEM);
        _alarmvalueimpl_specificProblem = value;
    }


    /**
     * Returns this AlarmValueImpl's specificProblem
     * 
     * @return the specificProblem
     * 
    */

    public java.lang.String getSpecificProblem()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.SPECIFIC_PROBLEM);
        return _alarmvalueimpl_specificProblem;
    }

    /**
     * Changes the systemDN to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new systemDN for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSystemDN(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmValue.SYSTEM_DN);
        _alarmvalueimpl_systemDN = value;
    }


    /**
     * Returns this AlarmValueImpl's systemDN
     * 
     * @return the systemDN
     * 
    */

    public java.lang.String getSystemDN()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmValue.SYSTEM_DN);
        return _alarmvalueimpl_systemDN;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AlarmValue val = null;
            val = (AlarmValue)super.clone();

            if( isPopulated(AlarmValue.ACKNOWLEDGEMENT_INTERACTION_RECORD)) {
                if( this.getAcknowledgementInteractionRecord()!=null) 
                    val.setAcknowledgementInteractionRecord((javax.oss.util.InteractionRecord)((javax.oss.util.InteractionRecord) this.getAcknowledgementInteractionRecord()).clone());
                else
                    val.setAcknowledgementInteractionRecord( null );
            }

            if( isPopulated(AlarmValue.ADDITIONAL_TEXT)) {
                if( this.getAdditionalText()!=null) 
                    val.setAdditionalText(((java.lang.String) new String( this.getAdditionalText())));
                else
                    val.setAdditionalText( null );
            }

            if( isPopulated(AlarmValue.ALARM_CHANGED_TIME)) {
                if( this.getAlarmChangedTime()!=null) 
                    val.setAlarmChangedTime((java.util.Date)((java.util.Date) this.getAlarmChangedTime()).clone());
                else
                    val.setAlarmChangedTime( null );
            }

            if( isPopulated(AlarmValue.ALARM_RAISED_TIME)) {
                if( this.getAlarmRaisedTime()!=null) 
                    val.setAlarmRaisedTime((java.util.Date)((java.util.Date) this.getAlarmRaisedTime()).clone());
                else
                    val.setAlarmRaisedTime( null );
            }

            if( isPopulated(AlarmValue.ALARM_SPECIFIC_INFO)) {
                if( this.getAlarmSpecificInfo()!=null) 
                    val.setAlarmSpecificInfo((javax.oss.cbe.alarm.AlarmSpecificInfo)((javax.oss.cbe.alarm.AlarmSpecificInfo) this.getAlarmSpecificInfo()).clone());
                else
                    val.setAlarmSpecificInfo( null );
            }

            if( isPopulated(AlarmValue.ATTRIBUTE_CHANGES)) {
                if( this.getAttributeChanges()!=null) 
                    val.setAttributeChanges((javax.oss.cbe.alarm.AttributeValueChange[])((javax.oss.cbe.alarm.AttributeValueChange[]) this.getAttributeChanges()).clone());
                else
                    val.setAttributeChanges( null );
            }

            if( isPopulated(AlarmValue.BACK_UP_OBJECT)) {
                if( this.getBackUpObject()!=null) 
                    val.setBackUpObject(((java.lang.String) new String( this.getBackUpObject())));
                else
                    val.setBackUpObject( null );
            }

            if( isPopulated(AlarmValue.CLEARING_INTERACTION_RECORD)) {
                if( this.getClearingInteractionRecord()!=null) 
                    val.setClearingInteractionRecord((javax.oss.util.InteractionRecord)((javax.oss.util.InteractionRecord) this.getClearingInteractionRecord()).clone());
                else
                    val.setClearingInteractionRecord( null );
            }

            if( isPopulated(AlarmValue.COMMENTS)) {
                if( this.getComments()!=null) 
                    val.setComments((javax.oss.cbe.alarm.Comment[])((javax.oss.cbe.alarm.Comment[]) this.getComments()).clone());
                else
                    val.setComments( null );
            }

            if( isPopulated(AlarmValue.CORRELATED_NOTIFICATIONS)) {
                if( this.getCorrelatedNotifications()!=null) 
                    val.setCorrelatedNotifications((javax.oss.cbe.alarm.CorrelatedNotification[])((javax.oss.cbe.alarm.CorrelatedNotification[]) this.getCorrelatedNotifications()).clone());
                else
                    val.setCorrelatedNotifications( null );
            }

            if( isPopulated(AlarmValue.NOTIFICATION_ID)) {
                if( this.getNotificationId()!=null) 
                    val.setNotificationId(((java.lang.String) new String( this.getNotificationId())));
                else
                    val.setNotificationId( null );
            }

            if( isPopulated(AlarmValue.PROPOSED_REPAIR_ACTIONS)) {
                if( this.getProposedRepairActions()!=null) 
                    val.setProposedRepairActions(((java.lang.String) new String( this.getProposedRepairActions())));
                else
                    val.setProposedRepairActions( null );
            }

            if( isPopulated(AlarmValue.SPECIFIC_PROBLEM)) {
                if( this.getSpecificProblem()!=null) 
                    val.setSpecificProblem(((java.lang.String) new String( this.getSpecificProblem())));
                else
                    val.setSpecificProblem( null );
            }

            if( isPopulated(AlarmValue.SYSTEM_DN)) {
                if( this.getSystemDN()!=null) 
                    val.setSystemDN(((java.lang.String) new String( this.getSystemDN())));
                else
                    val.setSystemDN( null );
            }

            return val;
    }

    /**
     * Checks whether two AlarmValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an AlarmValue object that has the arguments.
     * 
     * @param value the Object to compare with this AlarmValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AlarmValue)) {
            AlarmValue argVal = (AlarmValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
