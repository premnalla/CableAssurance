/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.sla;

import javax.oss.cbe.sla.ServiceLevelAgreementItemKey;
import javax.oss.cbe.sla.ServiceLevelAgreementKey;
import javax.oss.cbe.EntityKey;
import ossj.common.cbe.EntityKeyImpl;
import javax.oss.cbe.sla.ServiceLevelAgreementValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.sla.ServiceLevelAgreementValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.sla.ServiceLevelAgreementValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceLevelAgreementValueImpl
extends ossj.common.cbe.agreement.AgreementValueImpl
implements ServiceLevelAgreementValue
{ 

    /**
     * Constructs a new ServiceLevelAgreementValueImpl with empty attributes.
     * 
     */

    public ServiceLevelAgreementValueImpl() {
        super();
        setManagedEntityKeyInstance( new ServiceLevelAgreementKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ServiceLevelAgreementValue.NAME,
        ServiceLevelAgreementValue.RELATED_ENTITY_KEY,
        ServiceLevelAgreementValue.RELATED_ENTITY_VALUE_TYPE,
        ServiceLevelAgreementValue.SERVICE_LEVEL_AGREEMENT_ITEM_KEYS,
    };

    private static final String[] settableAttributeNames = {
        ServiceLevelAgreementValue.NAME,
        ServiceLevelAgreementValue.RELATED_ENTITY_KEY,
        ServiceLevelAgreementValue.RELATED_ENTITY_VALUE_TYPE,
        ServiceLevelAgreementValue.SERVICE_LEVEL_AGREEMENT_ITEM_KEYS,
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ServiceLevelAgreementValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ServiceLevelAgreementValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ServiceLevelAgreementValueImpl.settableAttributeNames);
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

    public javax.oss.cbe.sla.ServiceLevelAgreementKey makeServiceLevelAgreementKey(){
        return (ServiceLevelAgreementKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForServiceLevelAgreementItemKeys() {
        return attributeTypeFor("serviceLevelAgreementItemKeys");
    }

    public javax.oss.cbe.sla.ServiceLevelAgreementItemKey[] makeServiceLevelAgreementItemKeys(int nb, String type){
        if(type.equals("javax.oss.cbe.sla.ServiceLevelAgreementItemKey") || type.equals("ossj.common.cbe.sla.ServiceLevelAgreementItemKeyImpl")) {
            return new ServiceLevelAgreementItemKeyImpl[nb];
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
        list[0] = "[Ljavax.oss.cbe.sla.ServiceLevelAgreementItemKey;";
        addAttributeTypes("serviceLevelAgreementItemKeys", list);
        list[0] = "java.lang.String";
        addAttributeTypes("relatedEntityValueType", list);
        list[0] = "javax.oss.cbe.EntityKey";
        addAttributeTypes("relatedEntityKey", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
    }

    private java.lang.String _servicelevelagreementvalueimpl_name = null;
    private javax.oss.cbe.EntityKey _servicelevelagreementvalueimpl_relatedEntityKey = null;
    private java.lang.String _servicelevelagreementvalueimpl_relatedEntityValueType = null;
    private javax.oss.cbe.sla.ServiceLevelAgreementItemKey[] _servicelevelagreementvalueimpl_serviceLevelAgreementItemKeys = null;


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
        setDirtyAttribute(ServiceLevelAgreementValue.NAME);
        _servicelevelagreementvalueimpl_name = value;
    }


    /**
     * Returns this ServiceLevelAgreementValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementValue.NAME);
        return _servicelevelagreementvalueimpl_name;
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
        setDirtyAttribute(ServiceLevelAgreementValue.RELATED_ENTITY_KEY);
        _servicelevelagreementvalueimpl_relatedEntityKey = value;
    }


    /**
     * Returns this ServiceLevelAgreementValueImpl's relatedEntityKey
     * 
     * @return the relatedEntityKey
     * 
    */

    public javax.oss.cbe.EntityKey getRelatedEntityKey()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementValue.RELATED_ENTITY_KEY);
        return _servicelevelagreementvalueimpl_relatedEntityKey;
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
        setDirtyAttribute(ServiceLevelAgreementValue.RELATED_ENTITY_VALUE_TYPE);
        _servicelevelagreementvalueimpl_relatedEntityValueType = value;
    }


    /**
     * Returns this ServiceLevelAgreementValueImpl's relatedEntityValueType
     * 
     * @return the relatedEntityValueType
     * 
    */

    public java.lang.String getRelatedEntityValueType()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementValue.RELATED_ENTITY_VALUE_TYPE);
        return _servicelevelagreementvalueimpl_relatedEntityValueType;
    }

    /**
     * Changes the serviceLevelAgreementItemKeys to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelAgreementItemKeys for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelAgreementItemKeys(javax.oss.cbe.sla.ServiceLevelAgreementItemKey[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ServiceLevelAgreementValue.SERVICE_LEVEL_AGREEMENT_ITEM_KEYS);
        _servicelevelagreementvalueimpl_serviceLevelAgreementItemKeys = value;
    }


    /**
     * Returns this ServiceLevelAgreementValueImpl's serviceLevelAgreementItemKeys
     * 
     * @return the serviceLevelAgreementItemKeys
     * 
    */

    public javax.oss.cbe.sla.ServiceLevelAgreementItemKey[] getServiceLevelAgreementItemKeys()
    throws java.lang.IllegalStateException {
        checkAttribute(ServiceLevelAgreementValue.SERVICE_LEVEL_AGREEMENT_ITEM_KEYS);
        return _servicelevelagreementvalueimpl_serviceLevelAgreementItemKeys;
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
        setAgreementKey(value);
    }


    /**
     * Returns this ServiceLevelAgreementValueImpl's serviceLevelAgreementKey
     * 
     * @return the serviceLevelAgreementKey
     * 
    */

    public javax.oss.cbe.sla.ServiceLevelAgreementKey getServiceLevelAgreementKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.sla.ServiceLevelAgreementKey)getAgreementKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceLevelAgreementValue val = null;
            val = (ServiceLevelAgreementValue)super.clone();

            if( isPopulated(ServiceLevelAgreementValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(ServiceLevelAgreementValue.RELATED_ENTITY_KEY)) {
                if( this.getRelatedEntityKey()!=null) 
                    val.setRelatedEntityKey((javax.oss.cbe.EntityKey)((javax.oss.cbe.EntityKey) this.getRelatedEntityKey()).clone());
                else
                    val.setRelatedEntityKey( null );
            }

            if( isPopulated(ServiceLevelAgreementValue.RELATED_ENTITY_VALUE_TYPE)) {
                if( this.getRelatedEntityValueType()!=null) 
                    val.setRelatedEntityValueType(((java.lang.String) new String( this.getRelatedEntityValueType())));
                else
                    val.setRelatedEntityValueType( null );
            }

            if( isPopulated(ServiceLevelAgreementValue.SERVICE_LEVEL_AGREEMENT_ITEM_KEYS)) {
                if( this.getServiceLevelAgreementItemKeys()!=null) 
                    val.setServiceLevelAgreementItemKeys((javax.oss.cbe.sla.ServiceLevelAgreementItemKey[])((javax.oss.cbe.sla.ServiceLevelAgreementItemKey[]) this.getServiceLevelAgreementItemKeys()).clone());
                else
                    val.setServiceLevelAgreementItemKeys( null );
            }

            return val;
    }

    /**
     * Checks whether two ServiceLevelAgreementValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceLevelAgreementValue object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceLevelAgreementValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceLevelAgreementValue)) {
            ServiceLevelAgreementValue argVal = (ServiceLevelAgreementValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
