/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceSpecificationKey;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.cbe.datatypes.TimePeriodImpl;
import javax.oss.cbe.service.ServiceSpecificationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceSpecificationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.service.ServiceSpecificationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceSpecificationValueImpl
extends ossj.common.cbe.EntitySpecificationValueImpl
implements ServiceSpecificationValue
{ 

    /**
     * Constructs a new ServiceSpecificationValueImpl with empty attributes.
     * 
     */

    public ServiceSpecificationValueImpl() {
        super();
        setManagedEntityKeyInstance( new ServiceSpecificationKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ServiceSpecificationValue.DESCRIPTION,
        ServiceSpecificationValue.NAME,
        ServiceSpecificationValue.SERVICE_BUSINESS_NAME,
        ServiceSpecificationValue.VALID_FOR
    };

    private static final String[] settableAttributeNames = {
        ServiceSpecificationValue.DESCRIPTION,
        ServiceSpecificationValue.NAME,
        ServiceSpecificationValue.SERVICE_BUSINESS_NAME,
        ServiceSpecificationValue.VALID_FOR
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ServiceSpecificationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ServiceSpecificationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ServiceSpecificationValueImpl.settableAttributeNames);
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

    public String[] attributeTypeForValidFor() {
        return attributeTypeFor("validFor");
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.datatypes.TimePeriod makeTimePeriod(String type){
        if(type.equals("javax.oss.cbe.datatypes.TimePeriod") || type.equals("ossj.common.cbe.datatypes.TimePeriodImpl")) {
            return new TimePeriodImpl();
        } else {
            return null;
        }
    }

    public javax.oss.cbe.service.ServiceSpecificationKey makeServiceSpecificationKey(){
        return (ServiceSpecificationKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.datatypes.TimePeriod";
        addAttributeTypes("validFor", list);
        list[0] = "java.lang.String";
        addAttributeTypes("description", list);
        list[0] = "java.lang.String";
        addAttributeTypes("serviceBusinessName", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
    }

    private java.lang.String _servicespecificationvalueimpl_description = null;
    private java.lang.String _servicespecificationvalueimpl_name = null;
    private java.lang.String _servicespecificationvalueimpl_serviceBusinessName = null;
    private javax.oss.cbe.datatypes.TimePeriod _servicespecificationvalueimpl_validFor = null;


    /**
     * Changes the description to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new description for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setDescription(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceSpecificationValue.DESCRIPTION);
        _servicespecificationvalueimpl_description = value;
    }


    /**
     * Returns this ServiceSpecificationValueImpl's description
     * 
     * @return the description
     * 
    */

    public java.lang.String getDescription()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceSpecificationValue.DESCRIPTION);
        return _servicespecificationvalueimpl_description;
    }

    /**
     * Changes the name to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new name for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceSpecificationValue.NAME);
        _servicespecificationvalueimpl_name = value;
    }


    /**
     * Returns this ServiceSpecificationValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceSpecificationValue.NAME);
        return _servicespecificationvalueimpl_name;
    }

    /**
     * Changes the serviceBusinessName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceBusinessName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceBusinessName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceSpecificationValue.SERVICE_BUSINESS_NAME);
        _servicespecificationvalueimpl_serviceBusinessName = value;
    }


    /**
     * Returns this ServiceSpecificationValueImpl's serviceBusinessName
     * 
     * @return the serviceBusinessName
     * 
    */

    public java.lang.String getServiceBusinessName()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceSpecificationValue.SERVICE_BUSINESS_NAME);
        return _servicespecificationvalueimpl_serviceBusinessName;
    }

    /**
     * Changes the serviceSpecificationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceSpecificationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceSpecificationKey(javax.oss.cbe.service.ServiceSpecificationKey value)
    throws java.lang.IllegalArgumentException    {
        setEntitySpecificationKey(value);
    }


    /**
     * Returns this ServiceSpecificationValueImpl's serviceSpecificationKey
     * 
     * @return the serviceSpecificationKey
     * 
    */

    public javax.oss.cbe.service.ServiceSpecificationKey getServiceSpecificationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.service.ServiceSpecificationKey)getEntitySpecificationKey();
    }

    /**
     * Changes the validFor to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new validFor for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setValidFor(javax.oss.cbe.datatypes.TimePeriod value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceSpecificationValue.VALID_FOR);
        _servicespecificationvalueimpl_validFor = value;
    }


    /**
     * Returns this ServiceSpecificationValueImpl's validFor
     * 
     * @return the validFor
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getValidFor()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceSpecificationValue.VALID_FOR);
        return _servicespecificationvalueimpl_validFor;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceSpecificationValue val = null;
            val = (ServiceSpecificationValue)super.clone();

            if( isPopulated(ServiceSpecificationValue.DESCRIPTION)) {
                if( this.getDescription()!=null) 
                    val.setDescription(((java.lang.String) new String( this.getDescription())));
                else
                    val.setDescription( null );
            }

            if( isPopulated(ServiceSpecificationValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(ServiceSpecificationValue.SERVICE_BUSINESS_NAME)) {
                if( this.getServiceBusinessName()!=null) 
                    val.setServiceBusinessName(((java.lang.String) new String( this.getServiceBusinessName())));
                else
                    val.setServiceBusinessName( null );
            }

            if( isPopulated(ServiceSpecificationValue.VALID_FOR)) {
                if( this.getValidFor()!=null) 
                    val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
                else
                    val.setValidFor( null );
            }

            return val;
    }

    /**
     * Checks whether two ServiceSpecificationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceSpecificationValue object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceSpecificationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceSpecificationValue)) {
            ServiceSpecificationValue argVal = (ServiceSpecificationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
