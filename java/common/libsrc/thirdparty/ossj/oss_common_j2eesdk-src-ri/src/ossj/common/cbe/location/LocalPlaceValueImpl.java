/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.LocalPlaceKey;
import javax.oss.cbe.location.LocalPlaceValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.LocalPlaceValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.location.LocalPlaceValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class LocalPlaceValueImpl
extends ossj.common.cbe.location.PlaceValueImpl
implements LocalPlaceValue
{ 

    /**
     * Constructs a new LocalPlaceValueImpl with empty attributes.
     * 
     */

    public LocalPlaceValueImpl() {
        super();
        setManagedEntityKeyInstance( new LocalPlaceKeyImpl());
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
        if (LocalPlaceValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (LocalPlaceValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(LocalPlaceValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.location.LocalPlaceKey makeLocalPlaceKey(){
        return (LocalPlaceKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the localPlaceKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new localPlaceKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setLocalPlaceKey(javax.oss.cbe.location.LocalPlaceKey value)
    throws java.lang.IllegalArgumentException    {
        setPlaceKey(value);
    }


    /**
     * Returns this LocalPlaceValueImpl's localPlaceKey
     * 
     * @return the localPlaceKey
     * 
    */

    public javax.oss.cbe.location.LocalPlaceKey getLocalPlaceKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.location.LocalPlaceKey)getPlaceKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        LocalPlaceValue val = null;
            val = (LocalPlaceValue)super.clone();

            return val;
    }

    /**
     * Checks whether two LocalPlaceValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an LocalPlaceValue object that has the arguments.
     * 
     * @param value the Object to compare with this LocalPlaceValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof LocalPlaceValue)) {
            LocalPlaceValue argVal = (LocalPlaceValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
