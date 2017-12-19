/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceAssociationKey;
import javax.oss.cbe.service.ServiceAssociationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.ServiceAssociationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.service.ServiceAssociationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ServiceAssociationValueImpl
extends ossj.common.cbe.AssociationValueImpl
implements ServiceAssociationValue
{ 

    /**
     * Constructs a new ServiceAssociationValueImpl with empty attributes.
     * 
     */

    public ServiceAssociationValueImpl() {
        super();
        setManagedEntityKeyInstance( new ServiceAssociationKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
    };

    private static final String[] settableAttributeNames = {
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ServiceAssociationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ServiceAssociationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ServiceAssociationValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.service.ServiceAssociationKey makeServiceAssociationKey(){
        return (ServiceAssociationKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the serviceAssociationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceAssociationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceAssociationKey(javax.oss.cbe.service.ServiceAssociationKey value)
    throws java.lang.IllegalArgumentException    {
        setAssociationKey(value);
    }


    /**
     * Returns this ServiceAssociationValueImpl's serviceAssociationKey
     * 
     * @return the serviceAssociationKey
     * 
    */

    public javax.oss.cbe.service.ServiceAssociationKey getServiceAssociationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.service.ServiceAssociationKey)getAssociationKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ServiceAssociationValue val = null;
            val = (ServiceAssociationValue)super.clone();

            return val;
    }

    /**
     * Checks whether two ServiceAssociationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ServiceAssociationValue object that has the arguments.
     * 
     * @param value the Object to compare with this ServiceAssociationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ServiceAssociationValue)) {
            ServiceAssociationValue argVal = (ServiceAssociationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
