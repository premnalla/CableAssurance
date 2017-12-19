/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementItemKey;
import javax.oss.cbe.agreement.AgreementItemValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementItemValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.agreement.AgreementItemValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementItemValueImpl
extends ossj.common.cbe.bi.BusinessInteractionItemValueImpl
implements AgreementItemValue
{ 

    /**
     * Constructs a new AgreementItemValueImpl with empty attributes.
     * 
     */

    public AgreementItemValueImpl() {
        super();
        setManagedEntityKeyInstance( new AgreementItemKeyImpl());
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
        if (AgreementItemValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (AgreementItemValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(AgreementItemValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.agreement.AgreementItemKey makeAgreementItemKey(){
        return (AgreementItemKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the agreementItemKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new agreementItemKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAgreementItemKey(javax.oss.cbe.agreement.AgreementItemKey value)
    throws java.lang.IllegalArgumentException    {
        setBusinessInteractionItemKey(value);
    }


    /**
     * Returns this AgreementItemValueImpl's agreementItemKey
     * 
     * @return the agreementItemKey
     * 
    */

    public javax.oss.cbe.agreement.AgreementItemKey getAgreementItemKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.agreement.AgreementItemKey)getBusinessInteractionItemKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AgreementItemValue val = null;
            val = (AgreementItemValue)super.clone();

            return val;
    }

    /**
     * Checks whether two AgreementItemValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an AgreementItemValue object that has the arguments.
     * 
     * @param value the Object to compare with this AgreementItemValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AgreementItemValue)) {
            AgreementItemValue argVal = (AgreementItemValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
