/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.UrbanPropertyAddress;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.UrbanPropertyAddress</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.UrbanPropertyAddress
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class UrbanPropertyAddressImpl
implements UrbanPropertyAddress
{ 

    /**
     * Constructs a new UrbanPropertyAddressImpl with empty attributes.
     * 
     */

    public UrbanPropertyAddressImpl() {
    }

    private java.lang.String _urbanpropertyaddressimpl_locality = null;
    private java.lang.String _urbanpropertyaddressimpl_municipality = null;
    private java.lang.String _urbanpropertyaddressimpl_postalCode = null;
    private java.lang.String _urbanpropertyaddressimpl_streetName = null;
    private java.lang.String _urbanpropertyaddressimpl_streetNumberFirst = null;
    private java.lang.String _urbanpropertyaddressimpl_streetNumberFirstSuffix = null;
    private java.lang.String _urbanpropertyaddressimpl_streetNumberLast = null;
    private java.lang.String _urbanpropertyaddressimpl_streetNumberLastSuffix = null;
    private java.lang.String _urbanpropertyaddressimpl_streetSuffix = null;
    private java.lang.String _urbanpropertyaddressimpl_streetType = null;


    /**
     * Changes the locality to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new locality for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setLocality(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_locality = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's locality
     * 
     * @return the locality
     * 
    */

    public java.lang.String getLocality() {
        return _urbanpropertyaddressimpl_locality;
    }

    /**
     * Changes the municipality to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new municipality for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setMunicipality(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_municipality = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's municipality
     * 
     * @return the municipality
     * 
    */

    public java.lang.String getMunicipality() {
        return _urbanpropertyaddressimpl_municipality;
    }

    /**
     * Changes the postalCode to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new postalCode for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPostalCode(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_postalCode = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's postalCode
     * 
     * @return the postalCode
     * 
    */

    public java.lang.String getPostalCode() {
        return _urbanpropertyaddressimpl_postalCode;
    }

    /**
     * Changes the streetName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_streetName = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's streetName
     * 
     * @return the streetName
     * 
    */

    public java.lang.String getStreetName() {
        return _urbanpropertyaddressimpl_streetName;
    }

    /**
     * Changes the streetNumberFirst to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetNumberFirst for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetNumberFirst(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_streetNumberFirst = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's streetNumberFirst
     * 
     * @return the streetNumberFirst
     * 
    */

    public java.lang.String getStreetNumberFirst() {
        return _urbanpropertyaddressimpl_streetNumberFirst;
    }

    /**
     * Changes the streetNumberFirstSuffix to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetNumberFirstSuffix for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetNumberFirstSuffix(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_streetNumberFirstSuffix = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's streetNumberFirstSuffix
     * 
     * @return the streetNumberFirstSuffix
     * 
    */

    public java.lang.String getStreetNumberFirstSuffix() {
        return _urbanpropertyaddressimpl_streetNumberFirstSuffix;
    }

    /**
     * Changes the streetNumberLast to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetNumberLast for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetNumberLast(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_streetNumberLast = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's streetNumberLast
     * 
     * @return the streetNumberLast
     * 
    */

    public java.lang.String getStreetNumberLast() {
        return _urbanpropertyaddressimpl_streetNumberLast;
    }

    /**
     * Changes the streetNumberLastSuffix to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetNumberLastSuffix for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetNumberLastSuffix(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_streetNumberLastSuffix = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's streetNumberLastSuffix
     * 
     * @return the streetNumberLastSuffix
     * 
    */

    public java.lang.String getStreetNumberLastSuffix() {
        return _urbanpropertyaddressimpl_streetNumberLastSuffix;
    }

    /**
     * Changes the streetSuffix to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetSuffix for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetSuffix(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_streetSuffix = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's streetSuffix
     * 
     * @return the streetSuffix
     * 
    */

    public java.lang.String getStreetSuffix() {
        return _urbanpropertyaddressimpl_streetSuffix;
    }

    /**
     * Changes the streetType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _urbanpropertyaddressimpl_streetType = value;
    }


    /**
     * Returns this UrbanPropertyAddressImpl's streetType
     * 
     * @return the streetType
     * 
    */

    public java.lang.String getStreetType() {
        return _urbanpropertyaddressimpl_streetType;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        UrbanPropertyAddress val = null;
        try { 
            val = (UrbanPropertyAddress)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("UrbanPropertyAddressImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two UrbanPropertyAddress are equal.
     * The result is true if and only if the argument is not null 
     * and is an UrbanPropertyAddress object that has the arguments.
     * 
     * @param value the Object to compare with this UrbanPropertyAddress
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof UrbanPropertyAddress)) {
            UrbanPropertyAddress argVal = (UrbanPropertyAddress) value;
            if( !Utils.compareAttributes( getLocality(), argVal.getLocality())) { return false; } 
            if( !Utils.compareAttributes( getMunicipality(), argVal.getMunicipality())) { return false; } 
            if( !Utils.compareAttributes( getPostalCode(), argVal.getPostalCode())) { return false; } 
            if( !Utils.compareAttributes( getStreetName(), argVal.getStreetName())) { return false; } 
            if( !Utils.compareAttributes( getStreetNumberFirst(), argVal.getStreetNumberFirst())) { return false; } 
            if( !Utils.compareAttributes( getStreetNumberFirstSuffix(), argVal.getStreetNumberFirstSuffix())) { return false; } 
            if( !Utils.compareAttributes( getStreetNumberLast(), argVal.getStreetNumberLast())) { return false; } 
            if( !Utils.compareAttributes( getStreetNumberLastSuffix(), argVal.getStreetNumberLastSuffix())) { return false; } 
            if( !Utils.compareAttributes( getStreetSuffix(), argVal.getStreetSuffix())) { return false; } 
            if( !Utils.compareAttributes( getStreetType(), argVal.getStreetType())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
