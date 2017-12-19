/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.PartyInteractionRoleKey;
import javax.oss.cbe.bi.PartyInteractionRoleValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.PartyInteractionRoleValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.bi.PartyInteractionRoleValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PartyInteractionRoleValueImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleValueImpl
implements PartyInteractionRoleValue
{ 

    /**
     * Constructs a new PartyInteractionRoleValueImpl with empty attributes.
     * 
     */

    public PartyInteractionRoleValueImpl() {
        super();
        setManagedEntityKeyInstance( new PartyInteractionRoleKeyImpl());
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
        if (PartyInteractionRoleValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (PartyInteractionRoleValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(PartyInteractionRoleValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.bi.PartyInteractionRoleKey makePartyInteractionRoleKey(){
        return (PartyInteractionRoleKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the partyInteractionRoleKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new partyInteractionRoleKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPartyInteractionRoleKey(javax.oss.cbe.bi.PartyInteractionRoleKey value)
    throws java.lang.IllegalArgumentException    {
        setBusinessInteractionRoleKey(value);
    }


    /**
     * Returns this PartyInteractionRoleValueImpl's partyInteractionRoleKey
     * 
     * @return the partyInteractionRoleKey
     * 
    */

    public javax.oss.cbe.bi.PartyInteractionRoleKey getPartyInteractionRoleKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.bi.PartyInteractionRoleKey)getBusinessInteractionRoleKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PartyInteractionRoleValue val = null;
            val = (PartyInteractionRoleValue)super.clone();

            return val;
    }

    /**
     * Checks whether two PartyInteractionRoleValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an PartyInteractionRoleValue object that has the arguments.
     * 
     * @param value the Object to compare with this PartyInteractionRoleValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PartyInteractionRoleValue)) {
            PartyInteractionRoleValue argVal = (PartyInteractionRoleValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
