/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productofferingprice;

import javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey;
import javax.oss.cbe.product.productofferingprice.ProductOfferingPriceValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productofferingprice.ProductOfferingPriceValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.product.productofferingprice.ProductOfferingPriceValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingPriceValueImpl
extends ossj.common.cbe.EntityValueImpl
implements ProductOfferingPriceValue
{ 

    /**
     * Constructs a new ProductOfferingPriceValueImpl with empty attributes.
     * 
     */

    public ProductOfferingPriceValueImpl() {
        super();
        setManagedEntityKeyInstance( new ProductOfferingPriceKeyImpl());
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
        if (ProductOfferingPriceValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ProductOfferingPriceValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ProductOfferingPriceValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey makeProductOfferingPriceKey(){
        return (ProductOfferingPriceKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the productOfferingPriceKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productOfferingPriceKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductOfferingPriceKey(javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this ProductOfferingPriceValueImpl's productOfferingPriceKey
     * 
     * @return the productOfferingPriceKey
     * 
    */

    public javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey getProductOfferingPriceKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.product.productofferingprice.ProductOfferingPriceKey)getEntityKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductOfferingPriceValue val = null;
            val = (ProductOfferingPriceValue)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductOfferingPriceValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductOfferingPriceValue object that has the arguments.
     * 
     * @param value the Object to compare with this ProductOfferingPriceValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductOfferingPriceValue)) {
            ProductOfferingPriceValue argVal = (ProductOfferingPriceValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
