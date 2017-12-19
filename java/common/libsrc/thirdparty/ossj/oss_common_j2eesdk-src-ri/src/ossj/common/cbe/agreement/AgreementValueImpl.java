/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementItemKey;
import javax.oss.cbe.agreement.AgreementKey;
import javax.oss.cbe.agreement.AgreementValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.agreement.AgreementValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementValueImpl
extends ossj.common.cbe.bi.BusinessInteractionValueImpl
implements AgreementValue
{ 

    /**
     * Constructs a new AgreementValueImpl with empty attributes.
     * 
     */

    public AgreementValueImpl() {
        super();
        setManagedEntityKeyInstance( new AgreementKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        AgreementValue.AGREEMENT_ITEM_KEYS,
    };

    private static final String[] settableAttributeNames = {
        AgreementValue.AGREEMENT_ITEM_KEYS,
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (AgreementValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (AgreementValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(AgreementValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.agreement.AgreementKey makeAgreementKey(){
        return (AgreementKey) makeManagedEntityKey();
    }

    public javax.oss.cbe.agreement.AgreementItemKey[] makeAgreementItemKeys(int nb, String type){
        if(type.equals("javax.oss.cbe.agreement.AgreementItemKey") || type.equals("ossj.common.cbe.agreement.AgreementItemKeyImpl")) {
            return new AgreementItemKeyImpl[nb];
        } else {
            return null;
        }
    }

    public String[] attributeTypeForAgreementItemKeys() {
        return attributeTypeFor("agreementItemKeys");
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "[Ljavax.oss.cbe.agreement.AgreementItemKey;";
        addAttributeTypes("agreementItemKeys", list);
    }

    private javax.oss.cbe.agreement.AgreementItemKey[] _agreementvalueimpl_agreementItemKeys = null;


    /**
     * Changes the agreementItemKeys to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new agreementItemKeys for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAgreementItemKeys(javax.oss.cbe.agreement.AgreementItemKey[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AgreementValue.AGREEMENT_ITEM_KEYS);
        _agreementvalueimpl_agreementItemKeys = value;
    }


    /**
     * Returns this AgreementValueImpl's agreementItemKeys
     * 
     * @return the agreementItemKeys
     * 
    */

    public javax.oss.cbe.agreement.AgreementItemKey[] getAgreementItemKeys()
    throws java.lang.IllegalStateException {
        checkAttribute(AgreementValue.AGREEMENT_ITEM_KEYS);
        return _agreementvalueimpl_agreementItemKeys;
    }

    /**
     * Changes the agreementKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new agreementKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAgreementKey(javax.oss.cbe.agreement.AgreementKey value)
    throws java.lang.IllegalArgumentException    {
        setBusinessInteractionKey(value);
    }


    /**
     * Returns this AgreementValueImpl's agreementKey
     * 
     * @return the agreementKey
     * 
    */

    public javax.oss.cbe.agreement.AgreementKey getAgreementKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.agreement.AgreementKey)getBusinessInteractionKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AgreementValue val = null;
            val = (AgreementValue)super.clone();

            if( isPopulated(AgreementValue.AGREEMENT_ITEM_KEYS)) {
                if( this.getAgreementItemKeys()!=null) 
                    val.setAgreementItemKeys((javax.oss.cbe.agreement.AgreementItemKey[])((javax.oss.cbe.agreement.AgreementItemKey[]) this.getAgreementItemKeys()).clone());
                else
                    val.setAgreementItemKeys( null );
            }

            return val;
    }

    /**
     * Checks whether two AgreementValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an AgreementValue object that has the arguments.
     * 
     * @param value the Object to compare with this AgreementValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AgreementValue)) {
            AgreementValue argVal = (AgreementValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
