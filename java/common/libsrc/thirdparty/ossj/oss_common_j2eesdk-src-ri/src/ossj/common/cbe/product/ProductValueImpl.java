/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.location.PlaceKey;
import ossj.common.cbe.location.PlaceKeyImpl;
import javax.oss.cbe.product.ProductKey;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.cbe.datatypes.TimePeriodImpl;
import javax.oss.cbe.product.ProductValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.product.ProductValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductValueImpl
extends ossj.common.cbe.EntityValueImpl
implements ProductValue
{ 

    /**
     * Constructs a new ProductValueImpl with empty attributes.
     * 
     */

    public ProductValueImpl() {
        super();
        setManagedEntityKeyInstance( new ProductKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ProductValue.DESCRIPTION,
        ProductValue.NAME,
        ProductValue.PRODUCT_SERIAL_NUMBER,
        ProductValue.PRODUCT_STATE,
        ProductValue.PRODUCT_STATUS,
        ProductValue.REF_PLACE_KEYS,
        ProductValue.REF_PRODUCT_KEY,
        ProductValue.VALID_FOR
    };

    private static final String[] settableAttributeNames = {
        ProductValue.DESCRIPTION,
        ProductValue.NAME,
        ProductValue.PRODUCT_SERIAL_NUMBER,
        ProductValue.PRODUCT_STATE,
        ProductValue.PRODUCT_STATUS,
        ProductValue.REF_PLACE_KEYS,
        ProductValue.REF_PRODUCT_KEY,
        ProductValue.VALID_FOR
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ProductValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ProductValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ProductValueImpl.settableAttributeNames);
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

    public javax.oss.cbe.product.ProductKey makeProductKey(String type){
        return (ProductKey) makeManagedEntityKey();
    }

    public javax.oss.cbe.product.ProductKey makeProductKey(){
        return (ProductKey) makeManagedEntityKey();
    }

    public javax.oss.cbe.location.PlaceKey[] makePlaceKeys(int nb, String type){
        if(type.equals("javax.oss.cbe.location.PlaceKey") || type.equals("ossj.common.cbe.location.PlaceKeyImpl")) {
            return new PlaceKeyImpl[nb];
        } else {
            return null;
        }
    }

    public String[] attributeTypeForRefProductKey() {
        return attributeTypeFor("refProductKey");
    }

    public String[] attributeTypeForRefPlaceKeys() {
        return attributeTypeFor("refPlaceKeys");
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        addEnumeration("productState", "javax.oss.cbe.product.ProductState");
        addEnumeration("productStatus", "javax.oss.cbe.product.ProductStatus");
        list[0] = "javax.oss.cbe.datatypes.TimePeriod";
        addAttributeTypes("validFor", list);
        list[0] = "java.lang.String";
        addAttributeTypes("productSerialNumber", list);
        list[0] = "java.lang.String";
        addAttributeTypes("description", list);
        list[0] = "[Ljavax.oss.cbe.location.PlaceKey;";
        addAttributeTypes("refPlaceKeys", list);
        list[0] = "javax.oss.cbe.product.ProductKey";
        addAttributeTypes("refProductKey", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
    }

    private java.lang.String _productvalueimpl_description = null;
    private java.lang.String _productvalueimpl_name = null;
    private java.lang.String _productvalueimpl_productSerialNumber = null;
    private java.lang.String _productvalueimpl_productState = null;
    private int _productvalueimpl_productStatus;
    private javax.oss.cbe.location.PlaceKey[] _productvalueimpl_refPlaceKeys = null;
    private javax.oss.cbe.product.ProductKey _productvalueimpl_refProductKey = null;
    private javax.oss.cbe.datatypes.TimePeriod _productvalueimpl_validFor = null;


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
        setDirtyAttribute(ProductValue.DESCRIPTION);
        _productvalueimpl_description = value;
    }


    /**
     * Returns this ProductValueImpl's description
     * 
     * @return the description
     * 
    */

    public java.lang.String getDescription()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductValue.DESCRIPTION);
        return _productvalueimpl_description;
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
        setDirtyAttribute(ProductValue.NAME);
        _productvalueimpl_name = value;
    }


    /**
     * Returns this ProductValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductValue.NAME);
        return _productvalueimpl_name;
    }

    /**
     * Changes the productKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductKey(javax.oss.cbe.product.ProductKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this ProductValueImpl's productKey
     * 
     * @return the productKey
     * 
    */

    public javax.oss.cbe.product.ProductKey getProductKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.product.ProductKey)getEntityKey();
    }

    /**
     * Changes the productSerialNumber to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productSerialNumber for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductSerialNumber(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductValue.PRODUCT_SERIAL_NUMBER);
        _productvalueimpl_productSerialNumber = value;
    }


    /**
     * Returns this ProductValueImpl's productSerialNumber
     * 
     * @return the productSerialNumber
     * 
    */

    public java.lang.String getProductSerialNumber()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductValue.PRODUCT_SERIAL_NUMBER);
        return _productvalueimpl_productSerialNumber;
    }

    /**
     * Changes the productState to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productState for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductValue.PRODUCT_STATE);
        _productvalueimpl_productState = value;
    }


    /**
     * Returns this ProductValueImpl's productState
     * 
     * @return the productState
     * 
    */

    public java.lang.String getProductState()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductValue.PRODUCT_STATE);
        return _productvalueimpl_productState;
    }

    /**
     * Changes the productStatus to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productStatus for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductStatus(int value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductValue.PRODUCT_STATUS);
        _productvalueimpl_productStatus = value;
    }


    /**
     * Returns this ProductValueImpl's productStatus
     * 
     * @return the productStatus
     * 
    */

    public int getProductStatus()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductValue.PRODUCT_STATUS);
        return _productvalueimpl_productStatus;
    }

    /**
     * Changes the refPlaceKeys to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new refPlaceKeys for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRefPlaceKeys(javax.oss.cbe.location.PlaceKey[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductValue.REF_PLACE_KEYS);
        _productvalueimpl_refPlaceKeys = value;
    }


    /**
     * Returns this ProductValueImpl's refPlaceKeys
     * 
     * @return the refPlaceKeys
     * 
    */

    public javax.oss.cbe.location.PlaceKey[] getRefPlaceKeys()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductValue.REF_PLACE_KEYS);
        return _productvalueimpl_refPlaceKeys;
    }

    /**
     * Changes the refProductKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new refProductKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRefProductKey(javax.oss.cbe.product.ProductKey value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductValue.REF_PRODUCT_KEY);
        _productvalueimpl_refProductKey = value;
    }


    /**
     * Returns this ProductValueImpl's refProductKey
     * 
     * @return the refProductKey
     * 
    */

    public javax.oss.cbe.product.ProductKey getRefProductKey()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductValue.REF_PRODUCT_KEY);
        return _productvalueimpl_refProductKey;
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
        setDirtyAttribute(ProductValue.VALID_FOR);
        _productvalueimpl_validFor = value;
    }


    /**
     * Returns this ProductValueImpl's validFor
     * 
     * @return the validFor
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getValidFor()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductValue.VALID_FOR);
        return _productvalueimpl_validFor;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductValue val = null;
            val = (ProductValue)super.clone();

            if( isPopulated(ProductValue.DESCRIPTION)) {
                if( this.getDescription()!=null) 
                    val.setDescription(((java.lang.String) new String( this.getDescription())));
                else
                    val.setDescription( null );
            }

            if( isPopulated(ProductValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(ProductValue.PRODUCT_SERIAL_NUMBER)) {
                if( this.getProductSerialNumber()!=null) 
                    val.setProductSerialNumber(((java.lang.String) new String( this.getProductSerialNumber())));
                else
                    val.setProductSerialNumber( null );
            }

            if( isPopulated(ProductValue.PRODUCT_STATE)) {
                if( this.getProductState()!=null) 
                    val.setProductState(((java.lang.String) new String( this.getProductState())));
                else
                    val.setProductState( null );
            }

            if( isPopulated(ProductValue.REF_PLACE_KEYS)) {
                if( this.getRefPlaceKeys()!=null) 
                    val.setRefPlaceKeys((javax.oss.cbe.location.PlaceKey[])((javax.oss.cbe.location.PlaceKey[]) this.getRefPlaceKeys()).clone());
                else
                    val.setRefPlaceKeys( null );
            }

            if( isPopulated(ProductValue.REF_PRODUCT_KEY)) {
                if( this.getRefProductKey()!=null) 
                    val.setRefProductKey((javax.oss.cbe.product.ProductKey)((javax.oss.cbe.product.ProductKey) this.getRefProductKey()).clone());
                else
                    val.setRefProductKey( null );
            }

            if( isPopulated(ProductValue.VALID_FOR)) {
                if( this.getValidFor()!=null) 
                    val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
                else
                    val.setValidFor( null );
            }

            return val;
    }

    /**
     * Checks whether two ProductValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductValue object that has the arguments.
     * 
     * @param value the Object to compare with this ProductValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductValue)) {
            ProductValue argVal = (ProductValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
