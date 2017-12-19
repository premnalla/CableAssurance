/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductCatalogKey;
import javax.oss.cbe.product.productoffering.ProductCatalogValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductCatalogValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.product.productoffering.ProductCatalogValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductCatalogValueImpl
extends ossj.common.cbe.EntityValueImpl
implements ProductCatalogValue
{ 

    /**
     * Constructs a new ProductCatalogValueImpl with empty attributes.
     * 
     */

    public ProductCatalogValueImpl() {
        super();
        setManagedEntityKeyInstance( new ProductCatalogKeyImpl());
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
        if (ProductCatalogValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ProductCatalogValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ProductCatalogValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.product.productoffering.ProductCatalogKey makeProductCatalogKey(){
        return (ProductCatalogKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the productCatalogKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productCatalogKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductCatalogKey(javax.oss.cbe.product.productoffering.ProductCatalogKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this ProductCatalogValueImpl's productCatalogKey
     * 
     * @return the productCatalogKey
     * 
    */

    public javax.oss.cbe.product.productoffering.ProductCatalogKey getProductCatalogKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.product.productoffering.ProductCatalogKey)getEntityKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductCatalogValue val = null;
            val = (ProductCatalogValue)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductCatalogValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductCatalogValue object that has the arguments.
     * 
     * @param value the Object to compare with this ProductCatalogValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductCatalogValue)) {
            ProductCatalogValue argVal = (ProductCatalogValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
