/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.CustomerAccountInteractionRoleKey;
import javax.oss.cbe.bi.CustomerAccountInteractionRoleValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.CustomerAccountInteractionRoleValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.bi.CustomerAccountInteractionRoleValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CustomerAccountInteractionRoleValueImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleValueImpl
implements CustomerAccountInteractionRoleValue
{ 

    /**
     * Constructs a new CustomerAccountInteractionRoleValueImpl with empty attributes.
     * 
     */

    public CustomerAccountInteractionRoleValueImpl() {
        super();
        setManagedEntityKeyInstance( new CustomerAccountInteractionRoleKeyImpl());
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
        if (CustomerAccountInteractionRoleValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (CustomerAccountInteractionRoleValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(CustomerAccountInteractionRoleValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.bi.CustomerAccountInteractionRoleKey makeCustomerAccountInteractionRoleKey(){
        return (CustomerAccountInteractionRoleKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the customerAccountInteractionRoleKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new customerAccountInteractionRoleKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCustomerAccountInteractionRoleKey(javax.oss.cbe.bi.CustomerAccountInteractionRoleKey value)
    throws java.lang.IllegalArgumentException    {
        setBusinessInteractionRoleKey(value);
    }


    /**
     * Returns this CustomerAccountInteractionRoleValueImpl's customerAccountInteractionRoleKey
     * 
     * @return the customerAccountInteractionRoleKey
     * 
    */

    public javax.oss.cbe.bi.CustomerAccountInteractionRoleKey getCustomerAccountInteractionRoleKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.bi.CustomerAccountInteractionRoleKey)getBusinessInteractionRoleKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        CustomerAccountInteractionRoleValue val = null;
            val = (CustomerAccountInteractionRoleValue)super.clone();

            return val;
    }

    /**
     * Checks whether two CustomerAccountInteractionRoleValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an CustomerAccountInteractionRoleValue object that has the arguments.
     * 
     * @param value the Object to compare with this CustomerAccountInteractionRoleValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof CustomerAccountInteractionRoleValue)) {
            CustomerAccountInteractionRoleValue argVal = (CustomerAccountInteractionRoleValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
