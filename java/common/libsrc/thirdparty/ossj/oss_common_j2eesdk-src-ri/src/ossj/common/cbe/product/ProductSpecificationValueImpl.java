/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductSpecificationKey;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.cbe.datatypes.TimePeriodImpl;
import javax.oss.cbe.product.ProductSpecificationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductSpecificationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.product.ProductSpecificationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductSpecificationValueImpl
extends ossj.common.cbe.EntitySpecificationValueImpl
implements ProductSpecificationValue
{ 

    /**
     * Constructs a new ProductSpecificationValueImpl with empty attributes.
     * 
     */

    public ProductSpecificationValueImpl() {
        super();
        setManagedEntityKeyInstance( new ProductSpecificationKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ProductSpecificationValue.BRAND,
        ProductSpecificationValue.DESCRIPTION,
        ProductSpecificationValue.LIFE_CYCLE_STATE,
        ProductSpecificationValue.LIFE_CYCLE_STATUS,
        ProductSpecificationValue.NAME,
        ProductSpecificationValue.PRODUCT_BUSINESS_NAME,
        ProductSpecificationValue.PRODUCT_NUMBER,
        ProductSpecificationValue.VALID_FOR
    };

    private static final String[] settableAttributeNames = {
        ProductSpecificationValue.BRAND,
        ProductSpecificationValue.DESCRIPTION,
        ProductSpecificationValue.LIFE_CYCLE_STATE,
        ProductSpecificationValue.LIFE_CYCLE_STATUS,
        ProductSpecificationValue.NAME,
        ProductSpecificationValue.PRODUCT_BUSINESS_NAME,
        ProductSpecificationValue.PRODUCT_NUMBER,
        ProductSpecificationValue.VALID_FOR
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ProductSpecificationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ProductSpecificationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ProductSpecificationValueImpl.settableAttributeNames);
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

    public javax.oss.cbe.product.ProductSpecificationKey makeProductSpecificationKey(){
        return (ProductSpecificationKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        addEnumeration("lifeCycleStatus", "javax.oss.cbe.product.LifeCycleStatus");
        addEnumeration("lifeCycleState", "javax.oss.cbe.product.LifeCycleState");
        list[0] = "javax.oss.cbe.datatypes.TimePeriod";
        addAttributeTypes("validFor", list);
        list[0] = "java.lang.String";
        addAttributeTypes("brand", list);
        list[0] = "java.lang.String";
        addAttributeTypes("productNumber", list);
        list[0] = "java.lang.String";
        addAttributeTypes("productBusinessName", list);
        list[0] = "java.lang.String";
        addAttributeTypes("description", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
    }

    private java.lang.String _productspecificationvalueimpl_brand = null;
    private java.lang.String _productspecificationvalueimpl_description = null;
    private java.lang.String _productspecificationvalueimpl_lifeCycleState = null;
    private int _productspecificationvalueimpl_lifeCycleStatus;
    private java.lang.String _productspecificationvalueimpl_name = null;
    private java.lang.String _productspecificationvalueimpl_productBusinessName = null;
    private java.lang.String _productspecificationvalueimpl_productNumber = null;
    private javax.oss.cbe.datatypes.TimePeriod _productspecificationvalueimpl_validFor = null;


    /**
     * Changes the brand to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new brand for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBrand(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductSpecificationValue.BRAND);
        _productspecificationvalueimpl_brand = value;
    }


    /**
     * Returns this ProductSpecificationValueImpl's brand
     * 
     * @return the brand
     * 
    */

    public java.lang.String getBrand()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductSpecificationValue.BRAND);
        return _productspecificationvalueimpl_brand;
    }

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
        setDirtyAttribute(ProductSpecificationValue.DESCRIPTION);
        _productspecificationvalueimpl_description = value;
    }


    /**
     * Returns this ProductSpecificationValueImpl's description
     * 
     * @return the description
     * 
    */

    public java.lang.String getDescription()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductSpecificationValue.DESCRIPTION);
        return _productspecificationvalueimpl_description;
    }

    /**
     * Changes the lifeCycleState to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new lifeCycleState for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setLifeCycleState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductSpecificationValue.LIFE_CYCLE_STATE);
        _productspecificationvalueimpl_lifeCycleState = value;
    }


    /**
     * Returns this ProductSpecificationValueImpl's lifeCycleState
     * 
     * @return the lifeCycleState
     * 
    */

    public java.lang.String getLifeCycleState()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductSpecificationValue.LIFE_CYCLE_STATE);
        return _productspecificationvalueimpl_lifeCycleState;
    }

    /**
     * Changes the lifeCycleStatus to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new lifeCycleStatus for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setLifeCycleStatus(int value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductSpecificationValue.LIFE_CYCLE_STATUS);
        _productspecificationvalueimpl_lifeCycleStatus = value;
    }


    /**
     * Returns this ProductSpecificationValueImpl's lifeCycleStatus
     * 
     * @return the lifeCycleStatus
     * 
    */

    public int getLifeCycleStatus()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductSpecificationValue.LIFE_CYCLE_STATUS);
        return _productspecificationvalueimpl_lifeCycleStatus;
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
        setDirtyAttribute(ProductSpecificationValue.NAME);
        _productspecificationvalueimpl_name = value;
    }


    /**
     * Returns this ProductSpecificationValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductSpecificationValue.NAME);
        return _productspecificationvalueimpl_name;
    }

    /**
     * Changes the productBusinessName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productBusinessName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductBusinessName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductSpecificationValue.PRODUCT_BUSINESS_NAME);
        _productspecificationvalueimpl_productBusinessName = value;
    }


    /**
     * Returns this ProductSpecificationValueImpl's productBusinessName
     * 
     * @return the productBusinessName
     * 
    */

    public java.lang.String getProductBusinessName()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductSpecificationValue.PRODUCT_BUSINESS_NAME);
        return _productspecificationvalueimpl_productBusinessName;
    }

    /**
     * Changes the productNumber to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productNumber for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductNumber(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductSpecificationValue.PRODUCT_NUMBER);
        _productspecificationvalueimpl_productNumber = value;
    }


    /**
     * Returns this ProductSpecificationValueImpl's productNumber
     * 
     * @return the productNumber
     * 
    */

    public java.lang.String getProductNumber()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductSpecificationValue.PRODUCT_NUMBER);
        return _productspecificationvalueimpl_productNumber;
    }

    /**
     * Changes the productSpecificationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productSpecificationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductSpecificationKey(javax.oss.cbe.product.ProductSpecificationKey value)
    throws java.lang.IllegalArgumentException    {
        setEntitySpecificationKey(value);
    }


    /**
     * Returns this ProductSpecificationValueImpl's productSpecificationKey
     * 
     * @return the productSpecificationKey
     * 
    */

    public javax.oss.cbe.product.ProductSpecificationKey getProductSpecificationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.product.ProductSpecificationKey)getEntitySpecificationKey();
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
        setDirtyAttribute(ProductSpecificationValue.VALID_FOR);
        _productspecificationvalueimpl_validFor = value;
    }


    /**
     * Returns this ProductSpecificationValueImpl's validFor
     * 
     * @return the validFor
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getValidFor()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductSpecificationValue.VALID_FOR);
        return _productspecificationvalueimpl_validFor;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductSpecificationValue val = null;
            val = (ProductSpecificationValue)super.clone();

            if( isPopulated(ProductSpecificationValue.BRAND)) {
                if( this.getBrand()!=null) 
                    val.setBrand(((java.lang.String) new String( this.getBrand())));
                else
                    val.setBrand( null );
            }

            if( isPopulated(ProductSpecificationValue.DESCRIPTION)) {
                if( this.getDescription()!=null) 
                    val.setDescription(((java.lang.String) new String( this.getDescription())));
                else
                    val.setDescription( null );
            }

            if( isPopulated(ProductSpecificationValue.LIFE_CYCLE_STATE)) {
                if( this.getLifeCycleState()!=null) 
                    val.setLifeCycleState(((java.lang.String) new String( this.getLifeCycleState())));
                else
                    val.setLifeCycleState( null );
            }

            if( isPopulated(ProductSpecificationValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(ProductSpecificationValue.PRODUCT_BUSINESS_NAME)) {
                if( this.getProductBusinessName()!=null) 
                    val.setProductBusinessName(((java.lang.String) new String( this.getProductBusinessName())));
                else
                    val.setProductBusinessName( null );
            }

            if( isPopulated(ProductSpecificationValue.PRODUCT_NUMBER)) {
                if( this.getProductNumber()!=null) 
                    val.setProductNumber(((java.lang.String) new String( this.getProductNumber())));
                else
                    val.setProductNumber( null );
            }

            if( isPopulated(ProductSpecificationValue.VALID_FOR)) {
                if( this.getValidFor()!=null) 
                    val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
                else
                    val.setValidFor( null );
            }

            return val;
    }

    /**
     * Checks whether two ProductSpecificationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductSpecificationValue object that has the arguments.
     * 
     * @param value the Object to compare with this ProductSpecificationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductSpecificationValue)) {
            ProductSpecificationValue argVal = (ProductSpecificationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
