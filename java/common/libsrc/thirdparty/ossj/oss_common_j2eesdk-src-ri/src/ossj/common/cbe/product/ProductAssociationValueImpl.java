/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductAssociationKey;
import javax.oss.cbe.product.ProductAssociationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductAssociationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.product.ProductAssociationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductAssociationValueImpl
extends ossj.common.cbe.AssociationValueImpl
implements ProductAssociationValue
{ 

    /**
     * Constructs a new ProductAssociationValueImpl with empty attributes.
     * 
     */

    public ProductAssociationValueImpl() {
        super();
        setManagedEntityKeyInstance( new ProductAssociationKeyImpl());
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
        if (ProductAssociationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ProductAssociationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ProductAssociationValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.product.ProductAssociationKey makeProductAssociationKey(){
        return (ProductAssociationKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the productAssociationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productAssociationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductAssociationKey(javax.oss.cbe.product.ProductAssociationKey value)
    throws java.lang.IllegalArgumentException    {
        setAssociationKey(value);
    }


    /**
     * Returns this ProductAssociationValueImpl's productAssociationKey
     * 
     * @return the productAssociationKey
     * 
    */

    public javax.oss.cbe.product.ProductAssociationKey getProductAssociationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.product.ProductAssociationKey)getAssociationKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductAssociationValue val = null;
            val = (ProductAssociationValue)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductAssociationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductAssociationValue object that has the arguments.
     * 
     * @param value the Object to compare with this ProductAssociationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductAssociationValue)) {
            ProductAssociationValue argVal = (ProductAssociationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
