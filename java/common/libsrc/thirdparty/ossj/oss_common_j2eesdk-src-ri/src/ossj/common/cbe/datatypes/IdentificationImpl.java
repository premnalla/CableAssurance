/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.datatypes;

import javax.oss.cbe.datatypes.TimePeriod;
import javax.oss.cbe.datatypes.Identification;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.datatypes.Identification</CODE> interface.  
 * 
 * @see javax.oss.cbe.datatypes.Identification
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class IdentificationImpl
implements Identification
{ 

    /**
     * Constructs a new IdentificationImpl with empty attributes.
     * 
     */

    public IdentificationImpl() {
    }

    public String[] attributeTypeForValidFor() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.datatypes.TimePeriod";
        return list;
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

    private java.lang.String _identificationimpl_type = null;
    private javax.oss.cbe.datatypes.TimePeriod _identificationimpl_validFor = null;
    private java.lang.String _identificationimpl_value = null;


    /**
     * Changes the type to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new type for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _identificationimpl_type = value;
    }


    /**
     * Returns this IdentificationImpl's type
     * 
     * @return the type
     * 
    */

    public java.lang.String getType() {
        return _identificationimpl_type;
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
        _identificationimpl_validFor = value;
    }


    /**
     * Returns this IdentificationImpl's validFor
     * 
     * @return the validFor
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getValidFor() {
        return _identificationimpl_validFor;
    }

    /**
     * Changes the value to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new value for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setValue(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _identificationimpl_value = value;
    }


    /**
     * Returns this IdentificationImpl's value
     * 
     * @return the value
     * 
    */

    public java.lang.String getValue() {
        return _identificationimpl_value;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        Identification val = null;
        try { 
            val = (Identification)super.clone();

            if( this.getValidFor()!=null) 
                val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
            else
                val.setValidFor( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("IdentificationImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two Identification are equal.
     * The result is true if and only if the argument is not null 
     * and is an Identification object that has the arguments.
     * 
     * @param value the Object to compare with this Identification
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof Identification)) {
            Identification argVal = (Identification) value;
            if( !Utils.compareAttributes( getType(), argVal.getType())) { return false; } 
            if( !Utils.compareAttributes( getValidFor(), argVal.getValidFor())) { return false; } 
            if( !Utils.compareAttributes( getValue(), argVal.getValue())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
