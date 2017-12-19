/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.policy;

import javax.oss.cbe.policy.PolicyKey;
import javax.oss.cbe.policy.PolicyValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.policy.PolicyValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.policy.PolicyValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PolicyValueImpl
extends ossj.common.cbe.EntityValueImpl
implements PolicyValue
{ 

    /**
     * Constructs a new PolicyValueImpl with empty attributes.
     * 
     */

    public PolicyValueImpl() {
        super();
        setManagedEntityKeyInstance( new PolicyKeyImpl());
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
        if (PolicyValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (PolicyValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(PolicyValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.policy.PolicyKey makePolicyKey(){
        return (PolicyKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the policyKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new policyKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPolicyKey(javax.oss.cbe.policy.PolicyKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this PolicyValueImpl's policyKey
     * 
     * @return the policyKey
     * 
    */

    public javax.oss.cbe.policy.PolicyKey getPolicyKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.policy.PolicyKey)getEntityKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PolicyValue val = null;
            val = (PolicyValue)super.clone();

            return val;
    }

    /**
     * Checks whether two PolicyValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an PolicyValue object that has the arguments.
     * 
     * @param value the Object to compare with this PolicyValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PolicyValue)) {
            PolicyValue argVal = (PolicyValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
