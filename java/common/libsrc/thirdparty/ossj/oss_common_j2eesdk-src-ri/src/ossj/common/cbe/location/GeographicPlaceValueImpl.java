/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.GeographicPlaceKey;
import javax.oss.cbe.location.GeographicPlaceValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.GeographicPlaceValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.location.GeographicPlaceValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class GeographicPlaceValueImpl
extends ossj.common.cbe.location.PlaceValueImpl
implements GeographicPlaceValue
{ 

    /**
     * Constructs a new GeographicPlaceValueImpl with empty attributes.
     * 
     */

    public GeographicPlaceValueImpl() {
        super();
        setManagedEntityKeyInstance( new GeographicPlaceKeyImpl());
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
        if (GeographicPlaceValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (GeographicPlaceValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(GeographicPlaceValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.location.GeographicPlaceKey makeGeographicPlaceKey(){
        return (GeographicPlaceKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the geographicPlaceKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new geographicPlaceKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setGeographicPlaceKey(javax.oss.cbe.location.GeographicPlaceKey value)
    throws java.lang.IllegalArgumentException    {
        setPlaceKey(value);
    }


    /**
     * Returns this GeographicPlaceValueImpl's geographicPlaceKey
     * 
     * @return the geographicPlaceKey
     * 
    */

    public javax.oss.cbe.location.GeographicPlaceKey getGeographicPlaceKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.location.GeographicPlaceKey)getPlaceKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        GeographicPlaceValue val = null;
            val = (GeographicPlaceValue)super.clone();

            return val;
    }

    /**
     * Checks whether two GeographicPlaceValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an GeographicPlaceValue object that has the arguments.
     * 
     * @param value the Object to compare with this GeographicPlaceValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof GeographicPlaceValue)) {
            GeographicPlaceValue argVal = (GeographicPlaceValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
