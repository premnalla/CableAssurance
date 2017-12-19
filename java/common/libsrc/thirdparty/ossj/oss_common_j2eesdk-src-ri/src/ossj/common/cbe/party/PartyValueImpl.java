/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.OrganizationName;
import javax.oss.cbe.party.IndividualName;
import javax.oss.cbe.party.PartyKey;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.cbe.datatypes.TimePeriodImpl;
import javax.oss.cbe.party.PartyValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.PartyValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.party.PartyValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyValueImpl
extends ossj.common.cbe.EntityValueImpl
implements PartyValue
{ 

    /**
     * Constructs a new PartyValueImpl with empty attributes.
     * 
     */

    public PartyValueImpl() {
        super();
        setManagedEntityKeyInstance( new PartyKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        PartyValue.INDIVIDUAL_PARTY_NAMES,
        PartyValue.ORGANIZATION_PARTY_NAMES,
        PartyValue.VALID_FOR
    };

    private static final String[] settableAttributeNames = {
        PartyValue.INDIVIDUAL_PARTY_NAMES,
        PartyValue.ORGANIZATION_PARTY_NAMES,
        PartyValue.VALID_FOR
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (PartyValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (PartyValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(PartyValueImpl.settableAttributeNames);
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

    public javax.oss.cbe.party.PartyKey makePartyKey(){
        return (PartyKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForIndividualPartyNames() {
        return attributeTypeFor("individualPartyNames");
    }

    public String[] attributeTypeForOrganizationPartyNames() {
        return attributeTypeFor("organizationPartyNames");
    }

    public javax.oss.cbe.party.IndividualName[] makeIndividualNames(int nb, String type){
        if(type.equals("javax.oss.cbe.party.IndividualName") || type.equals("ossj.common.cbe.party.IndividualNameImpl")) {
            return new IndividualNameImpl[nb];
        } else {
            return null;
        }
    }

    public javax.oss.cbe.party.OrganizationName[] makeOrganizationNames(int nb, String type){
        if(type.equals("javax.oss.cbe.party.OrganizationName") || type.equals("ossj.common.cbe.party.OrganizationNameImpl")) {
            return new OrganizationNameImpl[nb];
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
        list[0] = "[Ljavax.oss.cbe.party.OrganizationName;";
        addAttributeTypes("organizationPartyNames", list);
        list[0] = "[Ljavax.oss.cbe.party.IndividualName;";
        addAttributeTypes("individualPartyNames", list);
    }

    private javax.oss.cbe.party.IndividualName[] _partyvalueimpl_individualPartyNames = null;
    private javax.oss.cbe.party.OrganizationName[] _partyvalueimpl_organizationPartyNames = null;
    private javax.oss.cbe.datatypes.TimePeriod _partyvalueimpl_validFor = null;


    /**
     * Changes the individualPartyNames to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new individualPartyNames for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setIndividualPartyNames(javax.oss.cbe.party.IndividualName[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PartyValue.INDIVIDUAL_PARTY_NAMES);
        _partyvalueimpl_individualPartyNames = value;
    }


    /**
     * Returns this PartyValueImpl's individualPartyNames
     * 
     * @return the individualPartyNames
     * 
    */

    public javax.oss.cbe.party.IndividualName[] getIndividualPartyNames()
    throws java.lang.IllegalStateException {
        checkAttribute(PartyValue.INDIVIDUAL_PARTY_NAMES);
        return _partyvalueimpl_individualPartyNames;
    }

    /**
     * Changes the organizationPartyNames to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new organizationPartyNames for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setOrganizationPartyNames(javax.oss.cbe.party.OrganizationName[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PartyValue.ORGANIZATION_PARTY_NAMES);
        _partyvalueimpl_organizationPartyNames = value;
    }


    /**
     * Returns this PartyValueImpl's organizationPartyNames
     * 
     * @return the organizationPartyNames
     * 
    */

    public javax.oss.cbe.party.OrganizationName[] getOrganizationPartyNames()
    throws java.lang.IllegalStateException {
        checkAttribute(PartyValue.ORGANIZATION_PARTY_NAMES);
        return _partyvalueimpl_organizationPartyNames;
    }

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
        setEntityKey(value);
    }


    /**
     * Returns this PartyValueImpl's partyKey
     * 
     * @return the partyKey
     * 
    */

    public javax.oss.cbe.party.PartyKey getPartyKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.party.PartyKey)getEntityKey();
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
        setDirtyAttribute(PartyValue.VALID_FOR);
        _partyvalueimpl_validFor = value;
    }


    /**
     * Returns this PartyValueImpl's validFor
     * 
     * @return the validFor
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getValidFor()
    throws java.lang.IllegalStateException {
        checkAttribute(PartyValue.VALID_FOR);
        return _partyvalueimpl_validFor;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PartyValue val = null;
            val = (PartyValue)super.clone();

            if( isPopulated(PartyValue.INDIVIDUAL_PARTY_NAMES)) {
                if( this.getIndividualPartyNames()!=null) 
                    val.setIndividualPartyNames((javax.oss.cbe.party.IndividualName[])((javax.oss.cbe.party.IndividualName[]) this.getIndividualPartyNames()).clone());
                else
                    val.setIndividualPartyNames( null );
            }

            if( isPopulated(PartyValue.ORGANIZATION_PARTY_NAMES)) {
                if( this.getOrganizationPartyNames()!=null) 
                    val.setOrganizationPartyNames((javax.oss.cbe.party.OrganizationName[])((javax.oss.cbe.party.OrganizationName[]) this.getOrganizationPartyNames()).clone());
                else
                    val.setOrganizationPartyNames( null );
            }

            if( isPopulated(PartyValue.VALID_FOR)) {
                if( this.getValidFor()!=null) 
                    val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
                else
                    val.setValidFor( null );
            }

            return val;
    }

    /**
     * Checks whether two PartyValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an PartyValue object that has the arguments.
     * 
     * @param value the Object to compare with this PartyValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PartyValue)) {
            PartyValue argVal = (PartyValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
