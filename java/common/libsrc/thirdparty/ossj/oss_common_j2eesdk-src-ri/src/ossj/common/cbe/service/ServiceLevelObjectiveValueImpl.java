/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecParam;
import javax.oss.cbe.service.ServiceLevelSpecificationKey;
import javax.oss.cbe.service.ServiceLevelObjectiveKey;
import javax.oss.cbe.EntityKey;
import ossj.common.cbe.EntityKeyImpl;
import javax.oss.cbe.service.ServiceLevelObjectiveValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceLevelObjectiveValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.service.ServiceLevelObjectiveValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelObjectiveValueImpl
extends ossj.common.cbe.EntityValueImpl
implements ServiceLevelObjectiveValue
{ 

    /**
     * Constructs a new ServiceLevelObjectiveValueImpl with empty attributes.
     * 
     */

    public ServiceLevelObjectiveValueImpl() {
        super();
        setManagedEntityKeyInstance( new ServiceLevelObjectiveKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ServiceLevelObjectiveValue.NAME,
        ServiceLevelObjectiveValue.RELATED_ENTITY_KEY,
        ServiceLevelObjectiveValue.SERVICE_LEVEL_SPEC_PARAM,
        ServiceLevelObjectiveValue.SERVICE_LEVEL_SPECIFICATION_KEY
    };

    private static final String[] settableAttributeNames = {
        ServiceLevelObjectiveValue.NAME,
        ServiceLevelObjectiveValue.RELATED_ENTITY_KEY,
        ServiceLevelObjectiveValue.SERVICE_LEVEL_SPEC_PARAM,
        ServiceLevelObjectiveValue.SERVICE_LEVEL_SPECIFICATION_KEY
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ServiceLevelObjectiveValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ServiceLevelObjectiveValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ServiceLevelObjectiveValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.EntityKey makeEntityKey(String type){
        if(type.equals("javax.oss.cbe.EntityKey") || type.equals("ossj.common.cbe.EntityKeyImpl")) {
            return new EntityKeyImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForRelatedEntityKey() {
        return attributeTypeFor("relatedEntityKey");
    }

    public javax.oss.cbe.service.ServiceLevelObjectiveKey makeServiceLevelObjectiveKey(){
        return (ServiceLevelObjectiveKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForServiceLevelSpecificationKey() {
        return attributeTypeFor("serviceLevelSpecificationKey");
    }

    public javax.oss.cbe.service.ServiceLevelSpecificationKey makeServiceLevelSpecificationKey(String type){
        if(type.equals("javax.oss.cbe.service.ServiceLevelSpecificationKey") || type.equals("ossj.common.cbe.service.ServiceLevelSpecificationKeyImpl")) {
            return new ServiceLevelSpecificationKeyImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForServiceLevelSpecParam() {
        return attributeTypeFor("serviceLevelSpecParam");
    }

    public javax.oss.cbe.service.ServiceLevelSpecParam makeServiceLevelSpecParam(String type){
        if(type.equals("javax.oss.cbe.service.ServiceLevelSpecParam") || type.equals("ossj.common.cbe.service.ServiceLevelSpecParamImpl")) {
            return new ServiceLevelSpecParamImpl();
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
        list[0] = "javax.oss.cbe.service.ServiceLevelSpecParam";
        addAttributeTypes("serviceLevelSpecParam", list);
        list[0] = "javax.oss.cbe.EntityKey";
        addAttributeTypes("relatedEntityKey", list);
        list[0] = "javax.oss.cbe.service.ServiceLevelSpecificationKey";
        addAttributeTypes("serviceLevelSpecificationKey", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
    }

    private java.lang.String _servicelevelobjectivevalueimpl_name = null;
    private javax.oss.cbe.EntityKey _servicelevelobjectivevalueimpl_relatedEntityKey = null;
    private javax.oss.cbe.service.ServiceLevelSpecParam _servicelevelobjectivevalueimpl_serviceLevelSpecParam = null;
    private javax.oss.cbe.service.ServiceLevelSpecificationKey _servicelevelobjectivevalueimpl_serviceLevelSpecificationKey = null;


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
        setDirtyAttribute(ServiceLevelObjectiveValue.NAME);
        _servicelevelobjectivevalueimpl_name = value;
    }


    /**
     * Returns this ServiceLevelObjectiveValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelObjectiveValue.NAME);
        return _servicelevelobjectivevalueimpl_name;
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

    public void setRelatedEntityKey(javax.oss.cbe.EntityKey value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelObjectiveValue.RELATED_ENTITY_KEY);
        _servicelevelobjectivevalueimpl_relatedEntityKey = value;
    }


    /**
     * Returns this ServiceLevelObjectiveValueImpl's relatedEntityKey
     * 
     * @return the relatedEntityKey
     * 
    */

    public javax.oss.cbe.EntityKey getRelatedEntityKey()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelObjectiveValue.RELATED_ENTITY_KEY);
        return _servicelevelobjectivevalueimpl_relatedEntityKey;
    }

    /**
     * Changes the serviceLevelObjectiveKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelObjectiveKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelObjectiveKey(javax.oss.cbe.service.ServiceLevelObjectiveKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this ServiceLevelObjectiveValueImpl's serviceLevelObjectiveKey
     * 
     * @return the serviceLevelObjectiveKey
     * 
    */

    public javax.oss.cbe.service.ServiceLevelObjectiveKey getServiceLevelObjectiveKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.service.ServiceLevelObjectiveKey)getEntityKey();
    }

    /**
     * Changes the serviceLevelSpecParam to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelSpecParam for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelSpecParam(javax.oss.cbe.service.ServiceLevelSpecParam value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelObjectiveValue.SERVICE_LEVEL_SPEC_PARAM);
        _servicelevelobjectivevalueimpl_serviceLevelSpecParam = value;
    }


    /**
     * Returns this ServiceLevelObjectiveValueImpl's serviceLevelSpecParam
     * 
     * @return the serviceLevelSpecParam
     * 
    */

    public javax.oss.cbe.service.ServiceLevelSpecParam getServiceLevelSpecParam()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelObjectiveValue.SERVICE_LEVEL_SPEC_PARAM);
        return _servicelevelobjectivevalueimpl_serviceLevelSpecParam;
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
        setDirtyAttribute(ServiceLevelObjectiveValue.SERVICE_LEVEL_SPECIFICATION_KEY);
        _servicelevelobjectivevalueimpl_serviceLevelSpecificationKey = value;
    }


    /**
     * Returns this ServiceLevelObjectiveValueImpl's serviceLevelSpecificationKey
     * 
     * @return the serviceLevelSpecificationKey
     * 
    */

    public javax.oss.cbe.service.ServiceLevelSpecificationKey getServiceLevelSpecificationKey()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelObjectiveValue.SERVICE_LEVEL_SPECIFICATION_KEY);
        return _servicelevelobjectivevalueimpl_serviceLevelSpecificationKey;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceLevelObjectiveValue val = null;
            val = (ServiceLevelObjectiveValue)super.clone();

            if( isPopulated(ServiceLevelObjectiveValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(ServiceLevelObjectiveValue.RELATED_ENTITY_KEY)) {
                if( this.getRelatedEntityKey()!=null) 
                    val.setRelatedEntityKey((javax.oss.cbe.EntityKey)((javax.oss.cbe.EntityKey) this.getRelatedEntityKey()).clone());
                else
                    val.setRelatedEntityKey( null );
            }

            if( isPopulated(ServiceLevelObjectiveValue.SERVICE_LEVEL_SPEC_PARAM)) {
                if( this.getServiceLevelSpecParam()!=null) 
                    val.setServiceLevelSpecParam((javax.oss.cbe.service.ServiceLevelSpecParam)((javax.oss.cbe.service.ServiceLevelSpecParam) this.getServiceLevelSpecParam()).clone());
                else
                    val.setServiceLevelSpecParam( null );
            }

            if( isPopulated(ServiceLevelObjectiveValue.SERVICE_LEVEL_SPECIFICATION_KEY)) {
                if( this.getServiceLevelSpecificationKey()!=null) 
                    val.setServiceLevelSpecificationKey((javax.oss.cbe.service.ServiceLevelSpecificationKey)((javax.oss.cbe.service.ServiceLevelSpecificationKey) this.getServiceLevelSpecificationKey()).clone());
                else
                    val.setServiceLevelSpecificationKey( null );
            }

            return val;
    }

    /**
     * Checks whether two ServiceLevelObjectiveValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceLevelObjectiveValue object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceLevelObjectiveValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceLevelObjectiveValue)) {
            ServiceLevelObjectiveValue argVal = (ServiceLevelObjectiveValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
