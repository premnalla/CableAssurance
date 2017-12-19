/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.PartyKey;
import javax.oss.cbe.party.PartyRoleKey;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.cbe.datatypes.TimePeriodImpl;
import javax.oss.cbe.party.PartyRoleValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyRoleValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.party.PartyRoleValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyRoleValueImpl
extends ossj.common.cbe.EntityValueImpl
implements PartyRoleValue
{ 

    /**
     * Constructs a new PartyRoleValueImpl with empty attributes.
     * 
     */

    public PartyRoleValueImpl() {
        super();
        setManagedEntityKeyInstance( new PartyRoleKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        PartyRoleValue.PARTY_KEY,
        PartyRoleValue.STATE,
        PartyRoleValue.STATUS,
        PartyRoleValue.VALID_FOR
    };

    private static final String[] settableAttributeNames = {
        PartyRoleValue.PARTY_KEY,
        PartyRoleValue.STATE,
        PartyRoleValue.STATUS,
        PartyRoleValue.VALID_FOR
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (PartyRoleValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (PartyRoleValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(PartyRoleValueImpl.settableAttributeNames);
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

    public javax.oss.cbe.party.PartyRoleKey makePartyRoleKey(){
        return (PartyRoleKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForPartyKey() {
        return attributeTypeFor("partyKey");
    }

    public javax.oss.cbe.party.PartyKey makePartyKey(String type){
        if(type.equals("javax.oss.cbe.party.PartyKey") || type.equals("ossj.common.cbe.party.PartyKeyImpl")) {
            return new PartyKeyImpl();
        } else {
            return null;
        }
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.datatypes.TimePeriod";
        addAttributeTypes("validFor", list);
        list[0] = "javax.oss.cbe.party.PartyKey";
        addAttributeTypes("partyKey", list);
        list[0] = "java.lang.String";
        addAttributeTypes("state", list);
        list[0] = "java.lang.String";
        addAttributeTypes("status", list);
    }

    private javax.oss.cbe.party.PartyKey _partyrolevalueimpl_partyKey = null;
    private java.lang.String _partyrolevalueimpl_state = null;
    private java.lang.String _partyrolevalueimpl_status = null;
    private javax.oss.cbe.datatypes.TimePeriod _partyrolevalueimpl_validFor = null;


    /**
     * Changes the partyKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new partyKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPartyKey(javax.oss.cbe.party.PartyKey value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PartyRoleValue.PARTY_KEY);
        _partyrolevalueimpl_partyKey = value;
    }


    /**
     * Returns this PartyRoleValueImpl's partyKey
     * 
     * @return the partyKey
     * 
    */

    public javax.oss.cbe.party.PartyKey getPartyKey()
    throws java.lang.IllegalStateException {
        checkAttribute(PartyRoleValue.PARTY_KEY);
        return _partyrolevalueimpl_partyKey;
    }

    /**
     * Changes the partyRoleKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new partyRoleKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPartyRoleKey(javax.oss.cbe.party.PartyRoleKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this PartyRoleValueImpl's partyRoleKey
     * 
     * @return the partyRoleKey
     * 
    */

    public javax.oss.cbe.party.PartyRoleKey getPartyRoleKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.party.PartyRoleKey)getEntityKey();
    }

    /**
     * Changes the state to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new state for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PartyRoleValue.STATE);
        _partyrolevalueimpl_state = value;
    }


    /**
     * Returns this PartyRoleValueImpl's state
     * 
     * @return the state
     * 
    */

    public java.lang.String getState()
    throws java.lang.IllegalStateException {
        checkAttribute(PartyRoleValue.STATE);
        return _partyrolevalueimpl_state;
    }

    /**
     * Changes the status to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new status for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStatus(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PartyRoleValue.STATUS);
        _partyrolevalueimpl_status = value;
    }


    /**
     * Returns this PartyRoleValueImpl's status
     * 
     * @return the status
     * 
    */

    public java.lang.String getStatus()
    throws java.lang.IllegalStateException {
        checkAttribute(PartyRoleValue.STATUS);
        return _partyrolevalueimpl_status;
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
        setDirtyAttribute(PartyRoleValue.VALID_FOR);
        _partyrolevalueimpl_validFor = value;
    }


    /**
     * Returns this PartyRoleValueImpl's validFor
     * 
     * @return the validFor
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getValidFor()
    throws java.lang.IllegalStateException {
        checkAttribute(PartyRoleValue.VALID_FOR);
        return _partyrolevalueimpl_validFor;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PartyRoleValue val = null;
            val = (PartyRoleValue)super.clone();

            if( isPopulated(PartyRoleValue.PARTY_KEY)) {
                if( this.getPartyKey()!=null) 
                    val.setPartyKey((javax.oss.cbe.party.PartyKey)((javax.oss.cbe.party.PartyKey) this.getPartyKey()).clone());
                else
                    val.setPartyKey( null );
            }

            if( isPopulated(PartyRoleValue.STATE)) {
                if( this.getState()!=null) 
                    val.setState(((java.lang.String) new String( this.getState())));
                else
                    val.setState( null );
            }

            if( isPopulated(PartyRoleValue.STATUS)) {
                if( this.getStatus()!=null) 
                    val.setStatus(((java.lang.String) new String( this.getStatus())));
                else
                    val.setStatus( null );
            }

            if( isPopulated(PartyRoleValue.VALID_FOR)) {
                if( this.getValidFor()!=null) 
                    val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
                else
                    val.setValidFor( null );
            }

            return val;
    }

    /**
     * Checks whether two PartyRoleValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an PartyRoleValue object that has the arguments.
     * 
     * @param value the Object to compare with this PartyRoleValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PartyRoleValue)) {
            PartyRoleValue argVal = (PartyRoleValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
