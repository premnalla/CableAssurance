/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceAssociationKey;
import javax.oss.cbe.resource.ResourceAssociationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceAssociationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.resource.ResourceAssociationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceAssociationValueImpl
extends ossj.common.cbe.AssociationValueImpl
implements ResourceAssociationValue
{ 

    /**
     * Constructs a new ResourceAssociationValueImpl with empty attributes.
     * 
     */

    public ResourceAssociationValueImpl() {
        super();
        setManagedEntityKeyInstance( new ResourceAssociationKeyImpl());
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
        if (ResourceAssociationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ResourceAssociationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ResourceAssociationValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.resource.ResourceAssociationKey makeResourceAssociationKey(){
        return (ResourceAssociationKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the resourceAssociationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new resourceAssociationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setResourceAssociationKey(javax.oss.cbe.resource.ResourceAssociationKey value)
    throws java.lang.IllegalArgumentException    {
        setAssociationKey(value);
    }


    /**
     * Returns this ResourceAssociationValueImpl's resourceAssociationKey
     * 
     * @return the resourceAssociationKey
     * 
    */

    public javax.oss.cbe.resource.ResourceAssociationKey getResourceAssociationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.resource.ResourceAssociationKey)getAssociationKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ResourceAssociationValue val = null;
            val = (ResourceAssociationValue)super.clone();

            return val;
    }

    /**
     * Checks whether two ResourceAssociationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ResourceAssociationValue object that has the arguments.
     * 
     * @param value the Object to compare with this ResourceAssociationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ResourceAssociationValue)) {
            ResourceAssociationValue argVal = (ResourceAssociationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
