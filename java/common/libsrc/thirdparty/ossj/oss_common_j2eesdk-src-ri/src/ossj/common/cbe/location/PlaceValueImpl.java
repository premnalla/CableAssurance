/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.PlaceKey;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.cbe.datatypes.TimePeriodImpl;
import javax.oss.cbe.location.PlaceValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.PlaceValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.location.PlaceValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PlaceValueImpl
extends ossj.common.cbe.EntityValueImpl
implements PlaceValue
{ 

    /**
     * Constructs a new PlaceValueImpl with empty attributes.
     * 
     */

    public PlaceValueImpl() {
        super();
        setManagedEntityKeyInstance( new PlaceKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        PlaceValue.VALID_FOR
    };

    private static final String[] settableAttributeNames = {
        PlaceValue.VALID_FOR
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (PlaceValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (PlaceValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(PlaceValueImpl.settableAttributeNames);
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

    public javax.oss.cbe.location.PlaceKey makePlaceKey(){
        return (PlaceKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.datatypes.TimePeriod";
        addAttributeTypes("validFor", list);
    }

    private javax.oss.cbe.datatypes.TimePeriod _placevalueimpl_validFor = null;


    /**
     * Changes the placeKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new placeKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPlaceKey(javax.oss.cbe.location.PlaceKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this PlaceValueImpl's placeKey
     * 
     * @return the placeKey
     * 
    */

    public javax.oss.cbe.location.PlaceKey getPlaceKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.location.PlaceKey)getEntityKey();
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
        setDirtyAttribute(PlaceValue.VALID_FOR);
        _placevalueimpl_validFor = value;
    }


    /**
     * Returns this PlaceValueImpl's validFor
     * 
     * @return the validFor
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getValidFor()
    throws java.lang.IllegalStateException {
        checkAttribute(PlaceValue.VALID_FOR);
        return _placevalueimpl_validFor;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PlaceValue val = null;
            val = (PlaceValue)super.clone();

            if( isPopulated(PlaceValue.VALID_FOR)) {
                if( this.getValidFor()!=null) 
                    val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
                else
                    val.setValidFor( null );
            }

            return val;
    }

    /**
     * Checks whether two PlaceValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an PlaceValue object that has the arguments.
     * 
     * @param value the Object to compare with this PlaceValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PlaceValue)) {
            PlaceValue argVal = (PlaceValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
