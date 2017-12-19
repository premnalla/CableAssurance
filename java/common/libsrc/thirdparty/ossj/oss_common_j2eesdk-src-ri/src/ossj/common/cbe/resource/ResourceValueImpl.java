/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceKey;
import javax.oss.cbe.resource.ResourceValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.resource.ResourceValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceValueImpl
extends ossj.common.cbe.EntityValueImpl
implements ResourceValue
{ 

    /**
     * Constructs a new ResourceValueImpl with empty attributes.
     * 
     */

    public ResourceValueImpl() {
        super();
        setManagedEntityKeyInstance( new ResourceKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ResourceValue.RESOURCE_BUSINESS_NAME,
    };

    private static final String[] settableAttributeNames = {
        ResourceValue.RESOURCE_BUSINESS_NAME,
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ResourceValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ResourceValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ResourceValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.resource.ResourceKey makeResourceKey(){
        return (ResourceKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "java.lang.String";
        addAttributeTypes("resourceBusinessName", list);
    }

    private java.lang.String _resourcevalueimpl_resourceBusinessName = null;


    /**
     * Changes the resourceBusinessName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new resourceBusinessName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setResourceBusinessName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ResourceValue.RESOURCE_BUSINESS_NAME);
        _resourcevalueimpl_resourceBusinessName = value;
    }


    /**
     * Returns this ResourceValueImpl's resourceBusinessName
     * 
     * @return the resourceBusinessName
     * 
    */

    public java.lang.String getResourceBusinessName()
    throws java.lang.IllegalStateException {
        checkAttribute(ResourceValue.RESOURCE_BUSINESS_NAME);
        return _resourcevalueimpl_resourceBusinessName;
    }

    /**
     * Changes the resourceKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new resourceKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setResourceKey(javax.oss.cbe.resource.ResourceKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this ResourceValueImpl's resourceKey
     * 
     * @return the resourceKey
     * 
    */

    public javax.oss.cbe.resource.ResourceKey getResourceKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.resource.ResourceKey)getEntityKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ResourceValue val = null;
            val = (ResourceValue)super.clone();

            if( isPopulated(ResourceValue.RESOURCE_BUSINESS_NAME)) {
                if( this.getResourceBusinessName()!=null) 
                    val.setResourceBusinessName(((java.lang.String) new String( this.getResourceBusinessName())));
                else
                    val.setResourceBusinessName( null );
            }

            return val;
    }

    /**
     * Checks whether two ResourceValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ResourceValue object that has the arguments.
     * 
     * @param value the Object to compare with this ResourceValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ResourceValue)) {
            ResourceValue argVal = (ResourceValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
