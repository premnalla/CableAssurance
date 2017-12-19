/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.BundledProductOfferingKey;
import javax.oss.cbe.product.productoffering.BundledProductOfferingValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.BundledProductOfferingValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.product.productoffering.BundledProductOfferingValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BundledProductOfferingValueImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingValueImpl
implements BundledProductOfferingValue
{ 

    /**
     * Constructs a new BundledProductOfferingValueImpl with empty attributes.
     * 
     */

    public BundledProductOfferingValueImpl() {
        super();
        setManagedEntityKeyInstance( new BundledProductOfferingKeyImpl());
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
        if (BundledProductOfferingValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (BundledProductOfferingValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(BundledProductOfferingValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.product.productoffering.BundledProductOfferingKey makeBundledProductOfferingKey(){
        return (BundledProductOfferingKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the bundledProductOfferingKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new bundledProductOfferingKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBundledProductOfferingKey(javax.oss.cbe.product.productoffering.BundledProductOfferingKey value)
    throws java.lang.IllegalArgumentException    {
        setProductOfferingKey(value);
    }


    /**
     * Returns this BundledProductOfferingValueImpl's bundledProductOfferingKey
     * 
     * @return the bundledProductOfferingKey
     * 
    */

    public javax.oss.cbe.product.productoffering.BundledProductOfferingKey getBundledProductOfferingKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.product.productoffering.BundledProductOfferingKey)getProductOfferingKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        BundledProductOfferingValue val = null;
            val = (BundledProductOfferingValue)super.clone();

            return val;
    }

    /**
     * Checks whether two BundledProductOfferingValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an BundledProductOfferingValue object that has the arguments.
     * 
     * @param value the Object to compare with this BundledProductOfferingValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof BundledProductOfferingValue)) {
            BundledProductOfferingValue argVal = (BundledProductOfferingValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
