package ossj.qos.fmri;

import javax.oss.util.IRPEvent;
import javax.oss.UnsupportedAttributeException;
import javax.oss.fm.monitor.NotifyNewAlarmEvent;
import javax.oss.fm.monitor.AttributeValue;
import javax.oss.fm.monitor.AttributeValueChange;
import javax.oss.fm.monitor.CorrelatedNotificationValue;
import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.TrendIndicationType;
import javax.oss.fm.monitor.ThresholdInfoType;
import javax.oss.fm.monitor.NotifyNewAlarmEventPropertyDescriptor;

import java.util.TreeMap;

/**
 * NotifyNeweventEvent
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class NotifyNewAlarmEventImpl extends BaseAlarmEvent implements NotifyNewAlarmEvent {

    private String specificProblem = null;
    private CorrelatedNotificationValue[] corNotifications = new CorrelatedNotificationValue[0];
    private Boolean backedUpStatus = null;
    private String backedUpObject = null;
    private String trendIndication = null;
    private ThresholdInfoType thresholdInfo = null;
    private AttributeValue[] monitoredAttributes = new AttributeValue[0];
    private String proposedRepairActions = null;
    private AttributeValueChange[] attributeChanges = new AttributeValueChange[0];
    private String additionalText = null;

    // Map containing a handler representing every attribute
    private static TreeMap attributeHandlerMap = null;

    // Manager that is responsible for the attribute descriptors
    private static AttributeManager attributeManager = null;

    /**
     * NotifyNewAlarmEventImpl - default constructor.
     */
    public NotifyNewAlarmEventImpl() {
        super();
    }

    /**
     * Creates NotifyNewAlarmEventImpl - constructor
     */
    public NotifyNewAlarmEventImpl( AlarmValue alarm ) {
        super(alarm);
        if ( alarm.isPopulated( AlarmValue.ALARM_RAISED_TIME ) ) {
            setEventTime( alarm.getAlarmRaisedTime() );
        }
      // UNSUPPORTED IN THIS RI IMPLEMENTATION  
     //   if ( alarm.isPopulated(NotifyNewAlarmEvent.SPECIFIC_PROBLEM ) ) {
     //       specificProblem = alarm.getSpecificProblem();
     //   }
        if ( alarm.isPopulated(AlarmValue.CORRELATED_NOTIFICATIONS ) ) {
            setCorrelatedNotifications( alarm.getCorrelatedNotifications() );
        }
        if ( alarm.isPopulated(AlarmValue.BACK_UP_OBJECT ) ) {
            setBackUpObject( alarm.getBackUpObject() );
        }
        if ( alarm.isPopulated(AlarmValue.BACKED_UP_STATUS ) ) {
            setBackedUpStatus( alarm.getBackedUpStatus() );
        }
        if ( alarm.isPopulated(AlarmValue.TREND_INDICATION) ) {
            setTrendIndication( alarm.getTrendIndication() );
        }
        if ( alarm.isPopulated(AlarmValue.THRESHOLD_INFO) ) {
            setThresholdInfo( alarm.getThresholdInfo() );
        }
        if ( alarm.isPopulated(AlarmValue.MONITORED_ATTRIBUTES) ) {
            setMonitoredAttributes( alarm.getMonitoredAttributes() );
        }
        if ( alarm.isPopulated(AlarmValue.PROPOSED_REPAIR_ACTIONS ) ) {
            setProposedRepairActions( alarm.getProposedRepairActions() );
        }
        if ( alarm.isPopulated(AlarmValue.ATTRIBUTE_CHANGES ) ) {
            setAttributeChanges( alarm.getAttributeChanges() );
        }
        if ( alarm.isPopulated(AlarmValue.ADDITIONAL_TEXT ) ) {
            setAdditionalText( alarm.getAdditionalText() );
        }
    }

    // Static Initializer for attribute handlers dealing with generic access
    static
    {
        // Map for attribute handlers
        attributeHandlerMap = new TreeMap();
        attributeHandlerMap.put(NotifyNewAlarmEvent.ADDITIONAL_TEXT, new EventAdditionalTextAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.ATTRIBUTE_CHANGES, new EventAttributeChangesAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.BACKED_UP_STATUS, new EventBackedUpStatusAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.BACK_UP_OBJECT, new EventBackedUpObjectAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.CORRELATED_NOTIFICATIONS, new EventCorrelatedNotificationsAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.MONITORED_ATTRIBUTES, new EventMonitoredAttributesAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.PROPOSED_REPAIR_ACTIONS, new EventProposedRepairActionsAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.SPECIFIC_PROBLEM, new EventSpecificProblemAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.THRESHOLD_INFO, new EventThresholdInfoAttr());
        attributeHandlerMap.put(NotifyNewAlarmEvent.TREND_INDICATION, new EventTrendIndicationAttr());

        // Map for attribute descriptors
        TreeMap attributeDescriptorMap = new TreeMap();
        attributeDescriptorMap.put( NotifyNewAlarmEvent.ADDITIONAL_TEXT, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.ATTRIBUTE_CHANGES, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.BACKED_UP_STATUS, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.BACK_UP_OBJECT, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.CORRELATED_NOTIFICATIONS, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.MONITORED_ATTRIBUTES, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.PROPOSED_REPAIR_ACTIONS, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.SPECIFIC_PROBLEM, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.THRESHOLD_INFO, new AttributeDescriptor(true,true,true) );
        attributeDescriptorMap.put( NotifyNewAlarmEvent.TREND_INDICATION, new AttributeDescriptor(true,true,true) );

        attributeManager = new AttributeManager();
        // Point to the parent since this is NOT the BASE CLASS.
        attributeManager.setParent( BaseAlarmEvent.attributeManager );
        attributeManager.loadDescriptors( attributeDescriptorMap );
    }

    // Methods that MUST BE OVERIDDEN in the derived classes
    protected AttributeManager getAttributeManager () {
        return NotifyNewAlarmEventImpl.attributeManager;
    }

    protected AttrObjectHandler getHandler( String handlerName ) {
        AttrObjectHandler handler = null;
        if ( handlerName != null ) {
            handler = (AttrObjectHandler)NotifyNewAlarmEventImpl.attributeHandlerMap.get(handlerName);
            if ( handler == null ) {
                handler = super.getHandler( handlerName );
            }
        }
        return handler;
    }
    // End of Methods that MUST BE OVERIDDEN in the derived classes

    /**
     * Returns specific problem.
     * <p>
     * It provides further qualification on the alarm than probableCause.
     * This parameter-attribute value shall be single-value and of simple
     * type such as integer or string.
     *
     * @return <code>String</code> - specific problem, the content is vendor specific.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setSpecificProblem
     */
    public String getSpecificProblem()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        throw new UnsupportedAttributeException( NotifyNewAlarmEvent.SPECIFIC_PROBLEM, "Unsupported Attribute entered." );
        /*if ( isPopulated( NotifyNewAlarmEvent.SPECIFIC_PROBLEM ) == false ) {
        //    throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
        //    NotifyNewAlarmEvent.SPECIFIC_PROBLEM );
        //}
        // return specificProblem; */
    }

    /**
     * Sets specific problem.
     *
     * @param specific_problem Defines the specific problem, the content is
     *  vendor specific.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getSpecificProblem
     */
    public void setSpecificProblem( String specific_problem )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        throw new UnsupportedAttributeException( NotifyNewAlarmEvent.SPECIFIC_PROBLEM, "Unsupported Attribute entered." );
        //   specificProblem = problem;
        //   populatedAttributeNames.add( NotifyNewAlarmEvent.SPECIFIC_PROBLEM);
        //   return;
    }

    /**
     * Returns correlated notifications.
     * <p>
     * It identifies a set of notifications to which this notification is
     * considered to be correlated.
     *
     * @return <code>CorrelatedNotificationValue[]</code> - list of correlated notifications.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setCorrelatedNotifications
     */
    public CorrelatedNotificationValue[] getCorrelatedNotifications()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyNewAlarmEvent.CORRELATED_NOTIFICATIONS ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyNewAlarmEvent.CORRELATED_NOTIFICATIONS );
        }
        return corNotifications;
    }

    /**
     * Sets the correlated notifications.
     *
     * @param notifications Defines a set of notifications to which this notification
     * is correlated. For further information see 3G TS 32.111-2 [4].
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getCorrelatedNotifications
     */
    public void setCorrelatedNotifications( CorrelatedNotificationValue[] notifications )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        if ( notifications == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            NotifyNewAlarmEvent.CORRELATED_NOTIFICATIONS );
        }
        corNotifications = notifications;
        populatedAttributeNames.add( NotifyNewAlarmEvent.CORRELATED_NOTIFICATIONS);
        return;
    }

    /**
     * Returns a CorrelatedNotificationValue.
     * @return <code>CorrelatedNotificationValue</code> - a correlated notification value.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.CorrelatedNotificationValue
     */
    public CorrelatedNotificationValue makeCorrelatedNotificationValue()
    throws javax.oss.UnsupportedAttributeException {
        return new CorrelatedNotificationValueImpl();
    }

    /**
     * Returns the backed up status.
     * <p>
     * It indicates if an object has a back up. For further information see
     * 3G TS 2 [4].
     * <p>
     * <i>Optional</i>
     * <p>
     * @return <code>boolean</code> - backed up status, valid values are defined in
     * {@link BackedUpStatusType}.
     * }
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setBackedUpStatus
     */
    public Boolean getBackedUpStatus()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyNewAlarmEvent.BACKED_UP_STATUS ) == false ) {
            throw new java.lang.IllegalStateException ( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            BACKED_UP_STATUS );
        }
        return backedUpStatus;
    }

    /**
     * Sets the backed up status.
     *
     * @param status Defines backed up status, valid values are defined in
     *  {@link BackedUpStatusType}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getBackedUpStatus
     */
    public void setBackedUpStatus( Boolean status )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        backedUpStatus = status;
        populatedAttributeNames.add( NotifyNewAlarmEvent.BACKED_UP_STATUS);
        return;
    }

    /**
     * Returns the back up object.
     * <p>
     * It carries the DN of the back up object. It shall be absent if
     * backUpStatus is absent or its value indicates false.
     *
     * @return <code>String</code> - DN of the back up object.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setBackUpObject
     */
    public String getBackUpObject()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyNewAlarmEvent.BACK_UP_OBJECT ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyNewAlarmEvent.BACK_UP_OBJECT );
        }
        return backedUpObject;
    }

    /**
     * Sets the back up object.
     *
     * @param back_up_object Defines DN of the back up object.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getBackUpObject
     */
    public void setBackUpObject( String back_up_object )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        backedUpObject = back_up_object;
        populatedAttributeNames.add( NotifyNewAlarmEvent.BACK_UP_OBJECT);
        return;
    }

    /**
     * Returns the trend indication.
     *
     * It indicates if some observed condition is getting better, worse,
     * or not changing. Predefined legal values are defined in interface
     * <code>javax.oss.fm.monitor.TrendIndication</code>.
     *
     * @return <code>String</code> - trend indication, valid values are defined in
     *  interface {@link TrendIndicationType}.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setTrendIndication
     */
    public String getTrendIndication()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyNewAlarmEvent.TREND_INDICATION ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyNewAlarmEvent.TREND_INDICATION );
        }
        return trendIndication;
    }

    /**
     * Sets the trend indication.
     *
     * @param trend_indication Defines trend indication.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getTrendIndication
     */
    public void setTrendIndication( String trend_indication )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        if ( trend_indication != null && trend_indication.equals( TrendIndicationType.LESS_SEVERE ) == false &&
        trend_indication.equals( TrendIndicationType.NO_CHANGE ) == false &&
        trend_indication.equals( TrendIndicationType.MORE_SEVERE )  ) {
            throw new IllegalArgumentException( "Invalid TrendIndication: " + trend_indication + " Valid values: " +
            TrendIndicationType.LESS_SEVERE + " or " +
            TrendIndicationType.NO_CHANGE + " or " +
            TrendIndicationType.MORE_SEVERE );
        }
        else {
            trendIndication = trend_indication;
            populatedAttributeNames.add( NotifyNewAlarmEvent.TREND_INDICATION);
        }
        return;
    }

    /**
     * Returns the threshold info.
     *
     * @return <code>ThresholdInfoType</code> - threshold information, see interface {@link ThresholdInfoType}.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setThresholdInfo
     */
    public ThresholdInfoType getThresholdInfo()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyNewAlarmEvent.THRESHOLD_INFO ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyNewAlarmEvent.THRESHOLD_INFO );
        }
        return thresholdInfo;
    }

    /**
     * Sets the threshold info.
     *
     * @param threshold_info Defines threshold info, see interface {@link ThresholdInfoType}.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getThresholdInfo
     */
    public void setThresholdInfo( ThresholdInfoType threshold_info )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        thresholdInfo = threshold_info;
        populatedAttributeNames.add( NotifyNewAlarmEvent.THRESHOLD_INFO);
        return;
    }

    /**
     * Returns an ThresholdInfoType.
     * @return <code>ThresholdInfoType</code> - a threshold information.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.AttributeValueChange
     */
    public ThresholdInfoType makeThresholdInfoType()
    throws javax.oss.UnsupportedAttributeException {
        return new ThresholdInfoTypeImpl();
    }

    /**
     * Returns the attribute changes.
     *
     * @return <code>AttributeValueChange</code> - list of changed attributes.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setAttributeChanges
     */
    public AttributeValueChange[] getAttributeChanges()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyNewAlarmEvent.ATTRIBUTE_CHANGES ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyNewAlarmEvent.ATTRIBUTE_CHANGES );
        }
        return attributeChanges;
    }

    /**
     * Sets the attribute changes.
     *
     * @param attribute_changes Defines the changed attributes.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getAttributeChanges
     */
    public void setAttributeChanges( AttributeValueChange[] attribute_changes )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        if ( attribute_changes == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            NotifyNewAlarmEvent.ATTRIBUTE_CHANGES );
        }
        else {
            attributeChanges = attribute_changes;
            populatedAttributeNames.add( NotifyNewAlarmEvent.ATTRIBUTE_CHANGES);
        }
        return;
    }

    /**
     * Returns an AttributeValueChange.
     * @return <code>AttributeValueChange</code> - an attribute value change.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.AttributeValueChange
     */
    public AttributeValueChange makeAttributeValueChange()
    throws javax.oss.UnsupportedAttributeException {
        return new AttributeValueChangeImpl();
    }

    /**
     * Returns the monitored attributes.
     *
     * @return <code>AttributeValue[]</code> - monitored attributes.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setMonitoredAttributes
     */
    public AttributeValue[] getMonitoredAttributes()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated( NotifyNewAlarmEvent.MONITORED_ATTRIBUTES ) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG  +
            NotifyNewAlarmEvent.MONITORED_ATTRIBUTES );
        }
        return monitoredAttributes;
    }

    /**
     * Sets the monitored attributes.
     *
     * @param monitored_attributes Defines the monitored attributes.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getMonitoredAttributes
     */
    public void setMonitoredAttributes( AttributeValue[] monitored_attributes )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        if ( monitored_attributes == null ) {
            throw new java.lang.IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG +
            NotifyNewAlarmEvent.MONITORED_ATTRIBUTES );
        }
        monitoredAttributes = monitored_attributes;
        populatedAttributeNames.add( NotifyNewAlarmEvent.MONITORED_ATTRIBUTES);
        return;
    }

    /**
     * Returns an AttributeValue.
     * @return <code>AttributeValue</code> - an attribute value.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see javax.oss.fm.monitor.AttributeValue
     */
    public AttributeValue makeAttributeValue()
    throws javax.oss.UnsupportedAttributeException {
        return new AttributeValueImpl();
    }

    /**
     * Returns the proposed repair actions.
     *
     * @return <code>String</code> - proposed repair actions.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setProposedRepairActions
     */
    public String getProposedRepairActions()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated(NotifyNewAlarmEvent.PROPOSED_REPAIR_ACTIONS) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG +
            NotifyNewAlarmEvent.PROPOSED_REPAIR_ACTIONS);
        }
        return proposedRepairActions;
    }

    /**
     * Sets proposed repair actions.
     *
     * @param proposed_repair_actions Defines the proposed repair actions.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getProposedRepairActions
     */
    public void setProposedRepairActions( String proposed_repair_actions )
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        proposedRepairActions = proposed_repair_actions;
        populatedAttributeNames.add( NotifyNewAlarmEvent.PROPOSED_REPAIR_ACTIONS);
        return;
    }

    /**
     * Returns the additional text.
     * <p>
     * It provides the identity of the NE (e.g. RNC, Node-B) from which
     * the alarm has been originated. It can contain further information
     * on the alarm.
     *
     * @return <code>String</code> - additional text.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @exception java.lang.IllegalStateException Is thrown if the attribute is supported,
     * and the attribute has not been populated.
     * @see #setAdditionalText
     */
    public String getAdditionalText()
    throws java.lang.IllegalStateException, javax.oss.UnsupportedAttributeException {
        if ( isPopulated(NotifyNewAlarmEvent.ADDITIONAL_TEXT) == false ) {
            throw new java.lang.IllegalStateException( LogMessages.ATTRIBUTE_NOT_POPULATED_MSG +
            NotifyNewAlarmEvent.ADDITIONAL_TEXT);
        }
        return additionalText;
    }

    /**
     * Sets the additional text.
     *
     * @param additional_text Defines the additional text.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @exception javax.oss.UnsupportedAttributeException Is thrown if the attribute is not supported.
     * @see #getAdditionalText
     */
    public void setAdditionalText( String additional_text)
    throws java.lang.IllegalArgumentException, javax.oss.UnsupportedAttributeException {
        additionalText = additional_text;
        populatedAttributeNames.add( NotifyNewAlarmEvent.ADDITIONAL_TEXT);
        return;
    }

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone() {
        NotifyNewAlarmEventImpl obj = (NotifyNewAlarmEventImpl)super.clone();
        obj.monitoredAttributes = (AttributeValue[])monitoredAttributes.clone();
        obj.attributeChanges = (AttributeValueChange[])attributeChanges.clone();
        obj.corNotifications = (CorrelatedNotificationValue[])corNotifications.clone();
        obj.thresholdInfo = (ThresholdInfoType)thresholdInfo.clone();
        return obj;
    }

    private void resetAdditionalText() {
        additionalText = null;
        return;
    }

    private void resetAttributeChanges() {
        attributeChanges = new AttributeValueChangeImpl[0];
        return;
    }

    private void resetBackupStatus() {
        backedUpStatus = null;
        return;
    }

    private void resetBackupObject() {
        backedUpObject = null;
        return;
    }

    private void resetCorrelatedNotifications() {
        corNotifications = new CorrelatedNotificationValue[0];
        return;
    }

    private void resetAttributeValue() {
        monitoredAttributes = new AttributeValueImpl[0];
        return;
    }

    private void resetProposedRepairActions() {
        proposedRepairActions = null;
        return;
    }

    private void resetSpecificProblem() {
        specificProblem = null;
        return;
    }

    private void resetThresholdInfo() {
        thresholdInfo = null;
        return;
    }

    private void resetTrendIndication() {
        trendIndication = null;
        return;
    }

    // static inner classes that represent each attribute

    static class EventAdditionalTextAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getAdditionalText();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyNewAlarmEvent)event).setAdditionalText( (String) obj );
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetAdditionalText();
            return;
        }
    }

    static class EventAttributeChangesAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getAttributeChanges();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyNewAlarmEvent)event).setAttributeChanges((AttributeValueChange[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetAttributeChanges();
            return;
        }
    }

    static class EventBackedUpStatusAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getBackedUpStatus();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                Boolean val = (Boolean)obj;
                ((NotifyNewAlarmEvent)event).setBackedUpStatus(val);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            } 
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetBackupStatus();
            return;
        }
    }

    static class EventBackedUpObjectAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getBackUpObject();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyNewAlarmEvent)event).setBackUpObject((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetBackupObject();
            return;
        }
    }

    static class EventCorrelatedNotificationsAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getCorrelatedNotifications();
        }

        public void setObject( IRPEvent event, Object obj ) {
            try {
                ((NotifyNewAlarmEvent)event).setCorrelatedNotifications((CorrelatedNotificationValue[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetCorrelatedNotifications();
            return;
        }
    }

    static class EventMonitoredAttributesAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getMonitoredAttributes();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyNewAlarmEvent)event).setMonitoredAttributes((AttributeValue[]) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetAttributeValue();
            return;
        }
    }

    static class EventProposedRepairActionsAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getProposedRepairActions();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyNewAlarmEvent)event).setProposedRepairActions((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetProposedRepairActions();
            return;
        }
    }

    static class EventSpecificProblemAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getSpecificProblem();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyNewAlarmEvent)event).setSpecificProblem((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetSpecificProblem();
            return;
        }
    }

    static class EventThresholdInfoAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getThresholdInfo();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyNewAlarmEvent)event).setThresholdInfo((ThresholdInfoType) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetThresholdInfo();
            return;
        }
    }

    static class EventTrendIndicationAttr implements AttrObjectHandler
    {
        public Object getObject( IRPEvent event ) {
            return ((NotifyNewAlarmEvent)event).getTrendIndication();
        }

        public void setObject( IRPEvent event, Object obj )
        throws java.lang.IllegalArgumentException {
            try {
                ((NotifyNewAlarmEvent)event).setTrendIndication((String) obj);
            }
            catch ( ClassCastException e ) {
                throw new java.lang.IllegalArgumentException( LogMessages.INVALID_OBJECT_MSG 
                + obj.getClass().getName() );
            }
            return;
        }

        public void clearObject( IRPEvent event ) {
            ((NotifyNewAlarmEventImpl)event).resetTrendIndication();
            return;
        }
    }
}