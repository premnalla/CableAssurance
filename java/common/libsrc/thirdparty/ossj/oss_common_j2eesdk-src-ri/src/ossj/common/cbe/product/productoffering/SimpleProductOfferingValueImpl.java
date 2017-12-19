/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.SimpleProductOfferingKey;
import javax.oss.cbe.product.productoffering.SimpleProductOfferingValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.SimpleProductOfferingValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.product.productoffering.SimpleProductOfferingValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class SimpleProductOfferingValueImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingValueImpl
implements SimpleProductOfferingValue
{ 

    /**
     * Constructs a new SimpleProductOfferingValueImpl with empty attributes.
     * 
     */

    public SimpleProductOfferingValueImpl() {
        super();
        setManagedEntityKeyInstance( new SimpleProductOfferingKeyImpl());
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
        if (SimpleProductOfferingValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (SimpleProductOfferingValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(SimpleProductOfferingValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.product.productoffering.SimpleProductOfferingKey makeSimpleProductOfferingKey(){
        return (SimpleProductOfferingKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the simpleProductOfferingKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new simpleProductOfferingKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSimpleProductOfferingKey(javax.oss.cbe.product.productoffering.SimpleProductOfferingKey value)
    throws java.lang.IllegalArgumentException    {
        setProductOfferingKey(value);
    }


    /**
     * Returns this SimpleProductOfferingValueImpl's simpleProductOfferingKey
     * 
     * @return the simpleProductOfferingKey
     * 
    */

    public javax.oss.cbe.product.productoffering.SimpleProductOfferingKey getSimpleProductOfferingKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.product.productoffering.SimpleProductOfferingKey)getProductOfferingKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        SimpleProductOfferingValue val = null;
            val = (SimpleProductOfferingValue)super.clone();

            return val;
    }

    /**
     * Checks whether two SimpleProductOfferingValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an SimpleProductOfferingValue object that has the arguments.
     * 
     * @param value the Object to compare with this SimpleProductOfferingValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof SimpleProductOfferingValue)) {
            SimpleProductOfferingValue argVal = (SimpleProductOfferingValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
