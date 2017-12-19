/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.AlarmValue;
import javax.oss.cbe.alarm.AlarmKey;
import javax.oss.cbe.alarm.AlarmEvent;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmEvent</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.alarm.AlarmEvent
 * 
 * @see javax.oss.Event
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AlarmEventImpl
extends ossj.common.EventImpl
implements AlarmEvent
{ 

    /**
     * Constructs a new AlarmEventImpl with empty attributes.
     * 
     */

    public AlarmEventImpl() {
        super();
        initAttributeTypes();
    }

    /**
     * Constructs a new AlarmEventImpl with the given Application DN.
     * @param applicationDN, the string representing the application DN
     *
     */


    AlarmEventImpl(String applicationDN){
        super(applicationDN); 
    }


    private static final String[] attributeNames = {
        AlarmEvent.ALARM_KEY,
        AlarmEvent.ALARM_VALUE
    };

    private static final String[] settableAttributeNames = {
        AlarmEvent.ALARM_KEY,
        AlarmEvent.ALARM_VALUE
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (AlarmEventImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (AlarmEventImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(AlarmEventImpl.settableAttributeNames);
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

    public String[] attributeTypeForAlarmKey() {
        return attributeTypeFor("alarmKey");
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.alarm.AlarmKey makeAlarmKey(String type){
        if(type.equals("javax.oss.cbe.alarm.AlarmKey") || type.equals("ossj.common.cbe.alarm.AlarmKeyImpl")) {
            return new AlarmKeyImpl();
        } else {
            return null;
        }
    }

    public javax.oss.cbe.alarm.AlarmValue makeAlarmValue(String type){
        if(type.equals("javax.oss.cbe.alarm.AlarmValue") || type.equals("ossj.common.cbe.alarm.AlarmValueImpl")) {
            return new AlarmValueImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForAlarmValue() {
        return attributeTypeFor("alarmValue");
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.alarm.AlarmValue";
        addAttributeTypes("alarmValue", list);
        list[0] = "javax.oss.cbe.alarm.AlarmKey";
        addAttributeTypes("alarmKey", list);
    }

    private javax.oss.cbe.alarm.AlarmKey _alarmeventimpl_alarmKey = null;
    private javax.oss.cbe.alarm.AlarmValue _alarmeventimpl_alarmValue = null;


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
        setDirtyAttribute(AlarmEvent.ALARM_KEY);
        _alarmeventimpl_alarmKey = value;
    }


    /**
     * Returns this AlarmEventImpl's alarmKey
     * 
     * @return the alarmKey
     * 
    */

    public javax.oss.cbe.alarm.AlarmKey getAlarmKey()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmEvent.ALARM_KEY);
        return _alarmeventimpl_alarmKey;
    }

    /**
     * Changes the alarmValue to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new alarmValue for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAlarmValue(javax.oss.cbe.alarm.AlarmValue value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AlarmEvent.ALARM_VALUE);
        _alarmeventimpl_alarmValue = value;
    }


    /**
     * Returns this AlarmEventImpl's alarmValue
     * 
     * @return the alarmValue
     * 
    */

    public javax.oss.cbe.alarm.AlarmValue getAlarmValue()
    throws java.lang.IllegalStateException {
        checkAttribute(AlarmEvent.ALARM_VALUE);
        return _alarmeventimpl_alarmValue;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AlarmEvent val = null;
            val = (AlarmEvent)super.clone();

            if( isPopulated(AlarmEvent.ALARM_KEY)) {
                if( this.getAlarmKey()!=null) 
                    val.setAlarmKey((javax.oss.cbe.alarm.AlarmKey)((javax.oss.cbe.alarm.AlarmKey) this.getAlarmKey()).clone());
                else
                    val.setAlarmKey( null );
            }

            if( isPopulated(AlarmEvent.ALARM_VALUE)) {
                if( this.getAlarmValue()!=null) 
                    val.setAlarmValue((javax.oss.cbe.alarm.AlarmValue)((javax.oss.cbe.alarm.AlarmValue) this.getAlarmValue()).clone());
                else
                    val.setAlarmValue( null );
            }

            return val;
    }

    /**
     * Checks whether two AlarmEvent are equal.
     * The result is true if and only if the argument is not null 
     * and is an AlarmEvent object that has the arguments.
     * 
     * @param value the Object to compare with this AlarmEvent
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AlarmEvent)) {
            AlarmEvent argVal = (AlarmEvent) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
