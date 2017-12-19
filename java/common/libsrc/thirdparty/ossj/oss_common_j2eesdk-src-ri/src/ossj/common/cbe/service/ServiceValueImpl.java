/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceKey;
import javax.oss.cbe.service.ServiceValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.service.ServiceValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceValueImpl
extends ossj.common.cbe.EntityValueImpl
implements ServiceValue
{ 

    /**
     * Constructs a new ServiceValueImpl with empty attributes.
     * 
     */

    public ServiceValueImpl() {
        super();
        setManagedEntityKeyInstance( new ServiceKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ServiceValue.MANDATORY,
        ServiceValue.START_MODE,
        ServiceValue.STATE,
        ServiceValue.SUBSCRIBER_ID
    };

    private static final String[] settableAttributeNames = {
        ServiceValue.MANDATORY,
        ServiceValue.START_MODE,
        ServiceValue.STATE,
        ServiceValue.SUBSCRIBER_ID
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ServiceValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ServiceValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ServiceValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.service.ServiceKey makeServiceKey(){
        return (ServiceKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        addEnumeration("startMode", "javax.oss.cbe.service.StartMode");
        list[0] = "java.lang.String";
        addAttributeTypes("subscriberId", list);
        list[0] = "java.lang.String";
        addAttributeTypes("state", list);
        list[0] = "boolean";
        addAttributeTypes("mandatory", list);
    }

    private boolean _servicevalueimpl_mandatory;
    private int _servicevalueimpl_startMode;
    private java.lang.String _servicevalueimpl_state = null;
    private java.lang.String _servicevalueimpl_subscriberId = null;


    /**
     * Changes the mandatory to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new mandatory for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setMandatory(boolean value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceValue.MANDATORY);
        _servicevalueimpl_mandatory = value;
    }


    /**
     * Returns this ServiceValueImpl's mandatory
     * 
     * @return the mandatory
     * 
    */

    public boolean isMandatory()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceValue.MANDATORY);
        return _servicevalueimpl_mandatory;
    }

    /**
    * @deprecated
    */
    public boolean getMandatory() throws java.lang.IllegalStateException {
        return isMandatory();
    }

    /**
     * Changes the serviceKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceKey(javax.oss.cbe.service.ServiceKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this ServiceValueImpl's serviceKey
     * 
     * @return the serviceKey
     * 
    */

    public javax.oss.cbe.service.ServiceKey getServiceKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.service.ServiceKey)getEntityKey();
    }

    /**
     * Changes the startMode to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new startMode for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStartMode(int value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceValue.START_MODE);
        _servicevalueimpl_startMode = value;
    }


    /**
     * Returns this ServiceValueImpl's startMode
     * 
     * @return the startMode
     * 
    */

    public int getStartMode()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceValue.START_MODE);
        return _servicevalueimpl_startMode;
    }

    /**
     * Changes the state to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new state for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceValue.STATE);
        _servicevalueimpl_state = value;
    }


    /**
     * Returns this ServiceValueImpl's state
     * 
     * @return the state
     * 
    */

    public java.lang.String getState()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceValue.STATE);
        return _servicevalueimpl_state;
    }

    /**
     * Changes the subscriberId to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new subscriberId for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSubscriberId(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceValue.SUBSCRIBER_ID);
        _servicevalueimpl_subscriberId = value;
    }


    /**
     * Returns this ServiceValueImpl's subscriberId
     * 
     * @return the subscriberId
     * 
    */

    public java.lang.String getSubscriberId()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceValue.SUBSCRIBER_ID);
        return _servicevalueimpl_subscriberId;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceValue val = null;
            val = (ServiceValue)super.clone();

            if( isPopulated(ServiceValue.STATE)) {
                if( this.getState()!=null) 
                    val.setState(((java.lang.String) new String( this.getState())));
                else
                    val.setState( null );
            }

            if( isPopulated(ServiceValue.SUBSCRIBER_ID)) {
                if( this.getSubscriberId()!=null) 
                    val.setSubscriberId(((java.lang.String) new String( this.getSubscriberId())));
                else
                    val.setSubscriberId( null );
            }

            return val;
    }

    /**
     * Checks whether two ServiceValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceValue object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceValue)) {
            ServiceValue argVal = (ServiceValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
