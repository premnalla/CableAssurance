/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.resource;

import javax.oss.cbe.resource.ResourceSpecificationKey;
import javax.oss.cbe.resource.ResourceSpecificationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.resource.ResourceSpecificationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.resource.ResourceSpecificationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceSpecificationValueImpl
extends ossj.common.cbe.EntitySpecificationValueImpl
implements ResourceSpecificationValue
{ 

    /**
     * Constructs a new ResourceSpecificationValueImpl with empty attributes.
     * 
     */

    public ResourceSpecificationValueImpl() {
        super();
        setManagedEntityKeyInstance( new ResourceSpecificationKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ResourceSpecificationValue.SKU_NUMBER,
        ResourceSpecificationValue.MODEL_NUMBER,
        ResourceSpecificationValue.PART_NUMBER,
        ResourceSpecificationValue.RESOURCE_BUSINESS_NAME,
        ResourceSpecificationValue.VENDOR_NAME
    };

    private static final String[] settableAttributeNames = {
        ResourceSpecificationValue.SKU_NUMBER,
        ResourceSpecificationValue.MODEL_NUMBER,
        ResourceSpecificationValue.PART_NUMBER,
        ResourceSpecificationValue.RESOURCE_BUSINESS_NAME,
        ResourceSpecificationValue.VENDOR_NAME
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ResourceSpecificationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ResourceSpecificationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ResourceSpecificationValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.resource.ResourceSpecificationKey makeResourceSpecificationKey(){
        return (ResourceSpecificationKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "java.lang.String";
        addAttributeTypes("vendorName", list);
        list[0] = "java.lang.String";
        addAttributeTypes("modelNumber", list);
        list[0] = "java.lang.String";
        addAttributeTypes("partNumber", list);
        list[0] = "java.lang.String";
        addAttributeTypes("SKUNumber", list);
        list[0] = "java.lang.String";
        addAttributeTypes("resourceBusinessName", list);
    }

    private java.lang.String _resourcespecificationvalueimpl_SKUNumber = null;
    private java.lang.String _resourcespecificationvalueimpl_modelNumber = null;
    private java.lang.String _resourcespecificationvalueimpl_partNumber = null;
    private java.lang.String _resourcespecificationvalueimpl_resourceBusinessName = null;
    private java.lang.String _resourcespecificationvalueimpl_vendorName = null;


    /**
     * Changes the SKUNumber to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new SKUNumber for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSKUNumber(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ResourceSpecificationValue.SKU_NUMBER);
        _resourcespecificationvalueimpl_SKUNumber = value;
    }


    /**
     * Returns this ResourceSpecificationValueImpl's SKUNumber
     * 
     * @return the SKUNumber
     * 
    */

    public java.lang.String getSKUNumber()
    throws java.lang.IllegalStateException {
        checkAttribute(ResourceSpecificationValue.SKU_NUMBER);
        return _resourcespecificationvalueimpl_SKUNumber;
    }

    /**
     * Changes the modelNumber to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new modelNumber for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setModelNumber(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ResourceSpecificationValue.MODEL_NUMBER);
        _resourcespecificationvalueimpl_modelNumber = value;
    }


    /**
     * Returns this ResourceSpecificationValueImpl's modelNumber
     * 
     * @return the modelNumber
     * 
    */

    public java.lang.String getModelNumber()
    throws java.lang.IllegalStateException {
        checkAttribute(ResourceSpecificationValue.MODEL_NUMBER);
        return _resourcespecificationvalueimpl_modelNumber;
    }

    /**
     * Changes the partNumber to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new partNumber for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPartNumber(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ResourceSpecificationValue.PART_NUMBER);
        _resourcespecificationvalueimpl_partNumber = value;
    }


    /**
     * Returns this ResourceSpecificationValueImpl's partNumber
     * 
     * @return the partNumber
     * 
    */

    public java.lang.String getPartNumber()
    throws java.lang.IllegalStateException {
        checkAttribute(ResourceSpecificationValue.PART_NUMBER);
        return _resourcespecificationvalueimpl_partNumber;
    }

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
        setDirtyAttribute(ResourceSpecificationValue.RESOURCE_BUSINESS_NAME);
        _resourcespecificationvalueimpl_resourceBusinessName = value;
    }


    /**
     * Returns this ResourceSpecificationValueImpl's resourceBusinessName
     * 
     * @return the resourceBusinessName
     * 
    */

    public java.lang.String getResourceBusinessName()
    throws java.lang.IllegalStateException {
        checkAttribute(ResourceSpecificationValue.RESOURCE_BUSINESS_NAME);
        return _resourcespecificationvalueimpl_resourceBusinessName;
    }

    /**
     * Changes the resourceSpecificationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new resourceSpecificationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setResourceSpecificationKey(javax.oss.cbe.resource.ResourceSpecificationKey value)
    throws java.lang.IllegalArgumentException    {
        setEntitySpecificationKey(value);
    }


    /**
     * Returns this ResourceSpecificationValueImpl's resourceSpecificationKey
     * 
     * @return the resourceSpecificationKey
     * 
    */

    public javax.oss.cbe.resource.ResourceSpecificationKey getResourceSpecificationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.resource.ResourceSpecificationKey)getEntitySpecificationKey();
    }

    /**
     * Changes the vendorName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new vendorName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setVendorName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ResourceSpecificationValue.VENDOR_NAME);
        _resourcespecificationvalueimpl_vendorName = value;
    }


    /**
     * Returns this ResourceSpecificationValueImpl's vendorName
     * 
     * @return the vendorName
     * 
    */

    public java.lang.String getVendorName()
    throws java.lang.IllegalStateException {
        checkAttribute(ResourceSpecificationValue.VENDOR_NAME);
        return _resourcespecificationvalueimpl_vendorName;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ResourceSpecificationValue val = null;
            val = (ResourceSpecificationValue)super.clone();

            if( isPopulated(ResourceSpecificationValue.SKU_NUMBER)) {
                if( this.getSKUNumber()!=null) 
                    val.setSKUNumber(((java.lang.String) new String( this.getSKUNumber())));
                else
                    val.setSKUNumber( null );
            }

            if( isPopulated(ResourceSpecificationValue.MODEL_NUMBER)) {
                if( this.getModelNumber()!=null) 
                    val.setModelNumber(((java.lang.String) new String( this.getModelNumber())));
                else
                    val.setModelNumber( null );
            }

            if( isPopulated(ResourceSpecificationValue.PART_NUMBER)) {
                if( this.getPartNumber()!=null) 
                    val.setPartNumber(((java.lang.String) new String( this.getPartNumber())));
                else
                    val.setPartNumber( null );
            }

            if( isPopulated(ResourceSpecificationValue.RESOURCE_BUSINESS_NAME)) {
                if( this.getResourceBusinessName()!=null) 
                    val.setResourceBusinessName(((java.lang.String) new String( this.getResourceBusinessName())));
                else
                    val.setResourceBusinessName( null );
            }

            if( isPopulated(ResourceSpecificationValue.VENDOR_NAME)) {
                if( this.getVendorName()!=null) 
                    val.setVendorName(((java.lang.String) new String( this.getVendorName())));
                else
                    val.setVendorName( null );
            }

            return val;
    }

    /**
     * Checks whether two ResourceSpecificationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ResourceSpecificationValue object that has the arguments.
     * 
     * @param value the Object to compare with this ResourceSpecificationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ResourceSpecificationValue)) {
            ResourceSpecificationValue argVal = (ResourceSpecificationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
