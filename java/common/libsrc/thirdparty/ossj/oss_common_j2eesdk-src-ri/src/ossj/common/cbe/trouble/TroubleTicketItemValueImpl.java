/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.trouble.TroubleTicketItemKey;
import javax.oss.cbe.trouble.TroubleTicketItemValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketItemValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketItemValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketItemValueImpl
extends ossj.common.cbe.bi.BusinessInteractionItemValueImpl
implements TroubleTicketItemValue
{ 

    /**
     * Constructs a new TroubleTicketItemValueImpl with empty attributes.
     * 
     */

    public TroubleTicketItemValueImpl() {
        super();
        setManagedEntityKeyInstance( new TroubleTicketItemKeyImpl());
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
        if (TroubleTicketItemValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (TroubleTicketItemValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(TroubleTicketItemValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.trouble.TroubleTicketItemKey makeTroubleTicketItemKey(){
        return (TroubleTicketItemKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the troubleTicketItemKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new troubleTicketItemKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTroubleTicketItemKey(javax.oss.cbe.trouble.TroubleTicketItemKey value)
    throws java.lang.IllegalArgumentException    {
        setBusinessInteractionItemKey(value);
    }


    /**
     * Returns this TroubleTicketItemValueImpl's troubleTicketItemKey
     * 
     * @return the troubleTicketItemKey
     * 
    */

    public javax.oss.cbe.trouble.TroubleTicketItemKey getTroubleTicketItemKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.trouble.TroubleTicketItemKey)getBusinessInteractionItemKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        TroubleTicketItemValue val = null;
            val = (TroubleTicketItemValue)super.clone();

            return val;
    }

    /**
     * Checks whether two TroubleTicketItemValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an TroubleTicketItemValue object that has the arguments.
     * 
     * @param value the Object to compare with this TroubleTicketItemValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof TroubleTicketItemValue)) {
            TroubleTicketItemValue argVal = (TroubleTicketItemValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
