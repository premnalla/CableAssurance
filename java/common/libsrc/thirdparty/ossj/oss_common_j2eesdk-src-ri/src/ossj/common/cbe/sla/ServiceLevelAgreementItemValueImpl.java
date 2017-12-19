/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementKey;
import javax.oss.cbe.sla.ServiceLevelAgreementItemKey;
import javax.oss.cbe.service.ServiceLevelSpecificationKey;
import ossj.common.cbe.service.ServiceLevelSpecificationKeyImpl;
import javax.oss.cbe.EntityKey;
import ossj.common.cbe.EntityKeyImpl;
import javax.oss.cbe.sla.ServiceLevelAgreementItemValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementItemValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementItemValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementItemValueImpl
extends ossj.common.cbe.agreement.AgreementItemValueImpl
implements ServiceLevelAgreementItemValue
{ 

    /**
     * Constructs a new ServiceLevelAgreementItemValueImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementItemValueImpl() {
        super();
        setManagedEntityKeyInstance( new ServiceLevelAgreementItemKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ServiceLevelAgreementItemValue.NAME,
        ServiceLevelAgreementItemValue.RELATED_ENTITY_KEY,
        ServiceLevelAgreementItemValue.RELATED_ENTITY_VALUE_TYPE,
        ServiceLevelAgreementItemValue.SERVICE_LEVEL_AGREEMENT_KEY,
        ServiceLevelAgreementItemValue.SERVICE_LEVEL_SPECIFICATION_KEYS
    };

    private static final String[] settableAttributeNames = {
        ServiceLevelAgreementItemValue.NAME,
        ServiceLevelAgreementItemValue.RELATED_ENTITY_KEY,
        ServiceLevelAgreementItemValue.RELATED_ENTITY_VALUE_TYPE,
        ServiceLevelAgreementItemValue.SERVICE_LEVEL_AGREEMENT_KEY,
        ServiceLevelAgreementItemValue.SERVICE_LEVEL_SPECIFICATION_KEYS
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ServiceLevelAgreementItemValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ServiceLevelAgreementItemValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ServiceLevelAgreementItemValueImpl.settableAttributeNames);
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

    public String[] attributeTypeForServiceLevelSpecificationKeys() {
        return attributeTypeFor("serviceLevelSpecificationKeys");
    }

    public javax.oss.cbe.service.ServiceLevelSpecificationKey[] makeServiceLevelSpecificationKeys(int nb, String type){
        if(type.equals("javax.oss.cbe.service.ServiceLevelSpecificationKey") || type.equals("ossj.common.cbe.service.ServiceLevelSpecificationKeyImpl")) {
            return new ServiceLevelSpecificationKeyImpl[nb];
        } else {
            return null;
        }
    }

    public javax.oss.cbe.sla.ServiceLevelAgreementItemKey makeServiceLevelAgreementItemKey(){
        return (ServiceLevelAgreementItemKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForServiceLevelAgreementKey() {
        return attributeTypeFor("serviceLevelAgreementKey");
    }

    public javax.oss.cbe.sla.ServiceLevelAgreementKey makeServiceLevelAgreementKey(String type){
        if(type.equals("javax.oss.cbe.sla.ServiceLevelAgreementKey") || type.equals("ossj.common.cbe.sla.ServiceLevelAgreementKeyImpl")) {
            return new ServiceLevelAgreementKeyImpl();
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
        list[0] = "javax.oss.cbe.sla.ServiceLevelAgreementKey";
        addAttributeTypes("serviceLevelAgreementKey", list);
        list[0] = "java.lang.String";
        addAttributeTypes("relatedEntityValueType", list);
        list[0] = "javax.oss.cbe.EntityKey";
        addAttributeTypes("relatedEntityKey", list);
        list[0] = "[Ljavax.oss.cbe.service.ServiceLevelSpecificationKey;";
        addAttributeTypes("serviceLevelSpecificationKeys", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
    }

    private java.lang.String _servicelevelagreementitemvalueimpl_name = null;
    private javax.oss.cbe.EntityKey _servicelevelagreementitemvalueimpl_relatedEntityKey = null;
    private java.lang.String _servicelevelagreementitemvalueimpl_relatedEntityValueType = null;
    private javax.oss.cbe.sla.ServiceLevelAgreementKey _servicelevelagreementitemvalueimpl_serviceLevelAgreementKey = null;
    private javax.oss.cbe.service.ServiceLevelSpecificationKey[] _servicelevelagreementitemvalueimpl_serviceLevelSpecificationKeys = null;


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
        setDirtyAttribute(ServiceLevelAgreementItemValue.NAME);
        _servicelevelagreementitemvalueimpl_name = value;
    }


    /**
     * Returns this ServiceLevelAgreementItemValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementItemValue.NAME);
        return _servicelevelagreementitemvalueimpl_name;
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
        setDirtyAttribute(ServiceLevelAgreementItemValue.RELATED_ENTITY_KEY);
        _servicelevelagreementitemvalueimpl_relatedEntityKey = value;
    }


    /**
     * Returns this ServiceLevelAgreementItemValueImpl's relatedEntityKey
     * 
     * @return the relatedEntityKey
     * 
    */

    public javax.oss.cbe.EntityKey getRelatedEntityKey()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementItemValue.RELATED_ENTITY_KEY);
        return _servicelevelagreementitemvalueimpl_relatedEntityKey;
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
        setDirtyAttribute(ServiceLevelAgreementItemValue.RELATED_ENTITY_VALUE_TYPE);
        _servicelevelagreementitemvalueimpl_relatedEntityValueType = value;
    }


    /**
     * Returns this ServiceLevelAgreementItemValueImpl's relatedEntityValueType
     * 
     * @return the relatedEntityValueType
     * 
    */

    public java.lang.String getRelatedEntityValueType()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementItemValue.RELATED_ENTITY_VALUE_TYPE);
        return _servicelevelagreementitemvalueimpl_relatedEntityValueType;
    }

    /**
     * Changes the serviceLevelAgreementItemKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelAgreementItemKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelAgreementItemKey(javax.oss.cbe.sla.ServiceLevelAgreementItemKey value)
    throws java.lang.IllegalArgumentException    {
        setAgreementItemKey(value);
    }


    /**
     * Returns this ServiceLevelAgreementItemValueImpl's serviceLevelAgreementItemKey
     * 
     * @return the serviceLevelAgreementItemKey
     * 
    */

    public javax.oss.cbe.sla.ServiceLevelAgreementItemKey getServiceLevelAgreementItemKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.sla.ServiceLevelAgreementItemKey)getAgreementItemKey();
    }

    /**
     * Changes the serviceLevelAgreementKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelAgreementKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelAgreementKey(javax.oss.cbe.sla.ServiceLevelAgreementKey value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelAgreementItemValue.SERVICE_LEVEL_AGREEMENT_KEY);
        _servicelevelagreementitemvalueimpl_serviceLevelAgreementKey = value;
    }


    /**
     * Returns this ServiceLevelAgreementItemValueImpl's serviceLevelAgreementKey
     * 
     * @return the serviceLevelAgreementKey
     * 
    */

    public javax.oss.cbe.sla.ServiceLevelAgreementKey getServiceLevelAgreementKey()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementItemValue.SERVICE_LEVEL_AGREEMENT_KEY);
        return _servicelevelagreementitemvalueimpl_serviceLevelAgreementKey;
    }

    /**
     * Changes the serviceLevelSpecificationKeys to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelSpecificationKeys for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelSpecificationKeys(javax.oss.cbe.service.ServiceLevelSpecificationKey[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelAgreementItemValue.SERVICE_LEVEL_SPECIFICATION_KEYS);
        _servicelevelagreementitemvalueimpl_serviceLevelSpecificationKeys = value;
    }


    /**
     * Returns this ServiceLevelAgreementItemValueImpl's serviceLevelSpecificationKeys
     * 
     * @return the serviceLevelSpecificationKeys
     * 
    */

    public javax.oss.cbe.service.ServiceLevelSpecificationKey[] getServiceLevelSpecificationKeys()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementItemValue.SERVICE_LEVEL_SPECIFICATION_KEYS);
        return _servicelevelagreementitemvalueimpl_serviceLevelSpecificationKeys;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceLevelAgreementItemValue val = null;
            val = (ServiceLevelAgreementItemValue)super.clone();

            if( isPopulated(ServiceLevelAgreementItemValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(ServiceLevelAgreementItemValue.RELATED_ENTITY_KEY)) {
                if( this.getRelatedEntityKey()!=null) 
                    val.setRelatedEntityKey((javax.oss.cbe.EntityKey)((javax.oss.cbe.EntityKey) this.getRelatedEntityKey()).clone());
                else
                    val.setRelatedEntityKey( null );
            }

            if( isPopulated(ServiceLevelAgreementItemValue.RELATED_ENTITY_VALUE_TYPE)) {
                if( this.getRelatedEntityValueType()!=null) 
                    val.setRelatedEntityValueType(((java.lang.String) new String( this.getRelatedEntityValueType())));
                else
                    val.setRelatedEntityValueType( null );
            }

            if( isPopulated(ServiceLevelAgreementItemValue.SERVICE_LEVEL_AGREEMENT_KEY)) {
                if( this.getServiceLevelAgreementKey()!=null) 
                    val.setServiceLevelAgreementKey((javax.oss.cbe.sla.ServiceLevelAgreementKey)((javax.oss.cbe.sla.ServiceLevelAgreementKey) this.getServiceLevelAgreementKey()).clone());
                else
                    val.setServiceLevelAgreementKey( null );
            }

            if( isPopulated(ServiceLevelAgreementItemValue.SERVICE_LEVEL_SPECIFICATION_KEYS)) {
                if( this.getServiceLevelSpecificationKeys()!=null) 
                    val.setServiceLevelSpecificationKeys((javax.oss.cbe.service.ServiceLevelSpecificationKey[])((javax.oss.cbe.service.ServiceLevelSpecificationKey[]) this.getServiceLevelSpecificationKeys()).clone());
                else
                    val.setServiceLevelSpecificationKeys( null );
            }

            return val;
    }

    /**
     * Checks whether two ServiceLevelAgreementItemValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceLevelAgreementItemValue object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceLevelAgreementItemValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceLevelAgreementItemValue)) {
            ServiceLevelAgreementItemValue argVal = (ServiceLevelAgreementItemValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
