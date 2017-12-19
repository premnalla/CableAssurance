/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecApplicability;
import javax.oss.cbe.service.ServiceLevelObjectiveKey;
import javax.oss.cbe.service.ServiceLevelSpecificationKey;
import javax.oss.cbe.EntitySpecificationKey;
import ossj.common.cbe.EntitySpecificationKeyImpl;
import javax.oss.cbe.service.ServiceLevelSpecificationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelSpecificationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.service.ServiceLevelSpecificationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelSpecificationValueImpl
extends ossj.common.cbe.EntitySpecificationValueImpl
implements ServiceLevelSpecificationValue
{ 

    /**
     * Constructs a new ServiceLevelSpecificationValueImpl with empty attributes.
     * 
     */

    public ServiceLevelSpecificationValueImpl() {
        super();
        setManagedEntityKeyInstance( new ServiceLevelSpecificationKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ServiceLevelSpecificationValue.NAME,
        ServiceLevelSpecificationValue.RELATED_ENTITY_KEY,
        ServiceLevelSpecificationValue.RELATED_ENTITY_VALUE_TYPE,
        ServiceLevelSpecificationValue.SERVICE_LEVEL_OBJECTIVE_KEYS,
        ServiceLevelSpecificationValue.SERVICE_LEVEL_SPEC_APPLICABILITIES,
    };

    private static final String[] settableAttributeNames = {
        ServiceLevelSpecificationValue.NAME,
        ServiceLevelSpecificationValue.RELATED_ENTITY_KEY,
        ServiceLevelSpecificationValue.RELATED_ENTITY_VALUE_TYPE,
        ServiceLevelSpecificationValue.SERVICE_LEVEL_OBJECTIVE_KEYS,
        ServiceLevelSpecificationValue.SERVICE_LEVEL_SPEC_APPLICABILITIES,
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ServiceLevelSpecificationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ServiceLevelSpecificationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ServiceLevelSpecificationValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.EntitySpecificationKey makeEntitySpecificationKey(String type){
        if(type.equals("javax.oss.cbe.EntitySpecificationKey") || type.equals("ossj.common.cbe.EntitySpecificationKeyImpl")) {
            return new EntitySpecificationKeyImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForRelatedEntityKey() {
        return attributeTypeFor("relatedEntityKey");
    }

    public javax.oss.cbe.service.ServiceLevelSpecificationKey makeServiceLevelSpecificationKey(){
        return (ServiceLevelSpecificationKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForServiceLevelObjectiveKeys() {
        return attributeTypeFor("serviceLevelObjectiveKeys");
    }

    public javax.oss.cbe.service.ServiceLevelObjectiveKey[] makeServiceLevelObjectiveKeys(int nb, String type){
        if(type.equals("javax.oss.cbe.service.ServiceLevelObjectiveKey") || type.equals("ossj.common.cbe.service.ServiceLevelObjectiveKeyImpl")) {
            return new ServiceLevelObjectiveKeyImpl[nb];
        } else {
            return null;
        }
    }

    public String[] attributeTypeForServiceLevelSpecApplicabilities() {
        return attributeTypeFor("serviceLevelSpecApplicabilities");
    }

    public javax.oss.cbe.service.ServiceLevelSpecApplicability[] makeServiceLevelSpecApplicabilities(int nb, String type){
        if(type.equals("javax.oss.cbe.service.ServiceLevelSpecApplicability") || type.equals("ossj.common.cbe.service.ServiceLevelSpecApplicabilityImpl")) {
            return new ServiceLevelSpecApplicabilityImpl[nb];
        } else {
            return null;
        }
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "java.lang.String";
        addAttributeTypes("relatedEntityValueType", list);
        list[0] = "javax.oss.cbe.EntitySpecificationKey";
        addAttributeTypes("relatedEntityKey", list);
        list[0] = "[Ljavax.oss.cbe.service.ServiceLevelObjectiveKey;";
        addAttributeTypes("serviceLevelObjectiveKeys", list);
        list[0] = "[Ljavax.oss.cbe.service.ServiceLevelSpecApplicability;";
        addAttributeTypes("serviceLevelSpecApplicabilities", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
    }

    private java.lang.String _servicelevelspecificationvalueimpl_name = null;
    private javax.oss.cbe.EntitySpecificationKey _servicelevelspecificationvalueimpl_relatedEntityKey = null;
    private java.lang.String _servicelevelspecificationvalueimpl_relatedEntityValueType = null;
    private javax.oss.cbe.service.ServiceLevelObjectiveKey[] _servicelevelspecificationvalueimpl_serviceLevelObjectiveKeys = null;
    private javax.oss.cbe.service.ServiceLevelSpecApplicability[] _servicelevelspecificationvalueimpl_serviceLevelSpecApplicabilities = null;


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
        setDirtyAttribute(ServiceLevelSpecificationValue.NAME);
        _servicelevelspecificationvalueimpl_name = value;
    }


    /**
     * Returns this ServiceLevelSpecificationValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelSpecificationValue.NAME);
        return _servicelevelspecificationvalueimpl_name;
    }

    /**
     * Changes the relatedEntityKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new relatedEntityKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRelatedEntityKey(javax.oss.cbe.EntitySpecificationKey value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelSpecificationValue.RELATED_ENTITY_KEY);
        _servicelevelspecificationvalueimpl_relatedEntityKey = value;
    }


    /**
     * Returns this ServiceLevelSpecificationValueImpl's relatedEntityKey
     * 
     * @return the relatedEntityKey
     * 
    */

    public javax.oss.cbe.EntitySpecificationKey getRelatedEntityKey()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelSpecificationValue.RELATED_ENTITY_KEY);
        return _servicelevelspecificationvalueimpl_relatedEntityKey;
    }

    /**
     * Changes the relatedEntityValueType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new relatedEntityValueType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRelatedEntityValueType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelSpecificationValue.RELATED_ENTITY_VALUE_TYPE);
        _servicelevelspecificationvalueimpl_relatedEntityValueType = value;
    }


    /**
     * Returns this ServiceLevelSpecificationValueImpl's relatedEntityValueType
     * 
     * @return the relatedEntityValueType
     * 
    */

    public java.lang.String getRelatedEntityValueType()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelSpecificationValue.RELATED_ENTITY_VALUE_TYPE);
        return _servicelevelspecificationvalueimpl_relatedEntityValueType;
    }

    /**
     * Changes the serviceLevelObjectiveKeys to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelObjectiveKeys for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelObjectiveKeys(javax.oss.cbe.service.ServiceLevelObjectiveKey[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelSpecificationValue.SERVICE_LEVEL_OBJECTIVE_KEYS);
        _servicelevelspecificationvalueimpl_serviceLevelObjectiveKeys = value;
    }


    /**
     * Returns this ServiceLevelSpecificationValueImpl's serviceLevelObjectiveKeys
     * 
     * @return the serviceLevelObjectiveKeys
     * 
    */

    public javax.oss.cbe.service.ServiceLevelObjectiveKey[] getServiceLevelObjectiveKeys()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelSpecificationValue.SERVICE_LEVEL_OBJECTIVE_KEYS);
        return _servicelevelspecificationvalueimpl_serviceLevelObjectiveKeys;
    }

    /**
     * Changes the serviceLevelSpecApplicabilities to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelSpecApplicabilities for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelSpecApplicabilities(javax.oss.cbe.service.ServiceLevelSpecApplicability[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelSpecificationValue.SERVICE_LEVEL_SPEC_APPLICABILITIES);
        _servicelevelspecificationvalueimpl_serviceLevelSpecApplicabilities = value;
    }


    /**
     * Returns this ServiceLevelSpecificationValueImpl's serviceLevelSpecApplicabilities
     * 
     * @return the serviceLevelSpecApplicabilities
     * 
    */

    public javax.oss.cbe.service.ServiceLevelSpecApplicability[] getServiceLevelSpecApplicabilities()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelSpecificationValue.SERVICE_LEVEL_SPEC_APPLICABILITIES);
        return _servicelevelspecificationvalueimpl_serviceLevelSpecApplicabilities;
    }

    /**
     * Changes the serviceLevelSpecificationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelSpecificationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelSpecificationKey(javax.oss.cbe.service.ServiceLevelSpecificationKey value)
    throws java.lang.IllegalArgumentException    {
        setEntitySpecificationKey(value);
    }


    /**
     * Returns this ServiceLevelSpecificationValueImpl's serviceLevelSpecificationKey
     * 
     * @return the serviceLevelSpecificationKey
     * 
    */

    public javax.oss.cbe.service.ServiceLevelSpecificationKey getServiceLevelSpecificationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.service.ServiceLevelSpecificationKey)getEntitySpecificationKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceLevelSpecificationValue val = null;
            val = (ServiceLevelSpecificationValue)super.clone();

            if( isPopulated(ServiceLevelSpecificationValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(ServiceLevelSpecificationValue.RELATED_ENTITY_KEY)) {
                if( this.getRelatedEntityKey()!=null) 
                    val.setRelatedEntityKey((javax.oss.cbe.EntitySpecificationKey)((javax.oss.cbe.EntitySpecificationKey) this.getRelatedEntityKey()).clone());
                else
                    val.setRelatedEntityKey( null );
            }

            if( isPopulated(ServiceLevelSpecificationValue.RELATED_ENTITY_VALUE_TYPE)) {
                if( this.getRelatedEntityValueType()!=null) 
                    val.setRelatedEntityValueType(((java.lang.String) new String( this.getRelatedEntityValueType())));
                else
                    val.setRelatedEntityValueType( null );
            }

            if( isPopulated(ServiceLevelSpecificationValue.SERVICE_LEVEL_OBJECTIVE_KEYS)) {
                if( this.getServiceLevelObjectiveKeys()!=null) 
                    val.setServiceLevelObjectiveKeys((javax.oss.cbe.service.ServiceLevelObjectiveKey[])((javax.oss.cbe.service.ServiceLevelObjectiveKey[]) this.getServiceLevelObjectiveKeys()).clone());
                else
                    val.setServiceLevelObjectiveKeys( null );
            }

            if( isPopulated(ServiceLevelSpecificationValue.SERVICE_LEVEL_SPEC_APPLICABILITIES)) {
                if( this.getServiceLevelSpecApplicabilities()!=null) 
                    val.setServiceLevelSpecApplicabilities((javax.oss.cbe.service.ServiceLevelSpecApplicability[])((javax.oss.cbe.service.ServiceLevelSpecApplicability[]) this.getServiceLevelSpecApplicabilities()).clone());
                else
                    val.setServiceLevelSpecApplicabilities( null );
            }

            return val;
    }

    /**
     * Checks whether two ServiceLevelSpecificationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceLevelSpecificationValue object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceLevelSpecificationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceLevelSpecificationValue)) {
            ServiceLevelSpecificationValue argVal = (ServiceLevelSpecificationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
