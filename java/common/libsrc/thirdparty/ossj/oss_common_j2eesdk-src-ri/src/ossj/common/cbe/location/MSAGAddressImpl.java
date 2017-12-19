/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.MSAGAddress;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.MSAGAddress</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.MSAGAddress
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class MSAGAddressImpl
implements MSAGAddress
{ 

    /**
     * Constructs a new MSAGAddressImpl with empty attributes.
     * 
     */

    public MSAGAddressImpl() {
    }

    private java.lang.String _msagaddressimpl_city = null;
    private java.lang.String _msagaddressimpl_houseNr = null;
    private java.lang.String _msagaddressimpl_houseNrPref = null;
    private java.lang.String _msagaddressimpl_houseNrSuf = null;
    private java.lang.String _msagaddressimpl_postDirectional = null;
    private java.lang.String _msagaddressimpl_preDirectional = null;
    private java.lang.String _msagaddressimpl_streetNm = null;
    private java.lang.String _msagaddressimpl_streetNmSuf = null;


    /**
     * Changes the city to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new city for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCity(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _msagaddressimpl_city = value;
    }


    /**
     * Returns this MSAGAddressImpl's city
     * 
     * @return the city
     * 
    */

    public java.lang.String getCity() {
        return _msagaddressimpl_city;
    }

    /**
     * Changes the houseNr to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new houseNr for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setHouseNr(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _msagaddressimpl_houseNr = value;
    }


    /**
     * Returns this MSAGAddressImpl's houseNr
     * 
     * @return the houseNr
     * 
    */

    public java.lang.String getHouseNr() {
        return _msagaddressimpl_houseNr;
    }

    /**
     * Changes the houseNrPref to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new houseNrPref for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setHouseNrPref(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _msagaddressimpl_houseNrPref = value;
    }


    /**
     * Returns this MSAGAddressImpl's houseNrPref
     * 
     * @return the houseNrPref
     * 
    */

    public java.lang.String getHouseNrPref() {
        return _msagaddressimpl_houseNrPref;
    }

    /**
     * Changes the houseNrSuf to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new houseNrSuf for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setHouseNrSuf(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _msagaddressimpl_houseNrSuf = value;
    }


    /**
     * Returns this MSAGAddressImpl's houseNrSuf
     * 
     * @return the houseNrSuf
     * 
    */

    public java.lang.String getHouseNrSuf() {
        return _msagaddressimpl_houseNrSuf;
    }

    /**
     * Changes the postDirectional to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new postDirectional for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPostDirectional(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _msagaddressimpl_postDirectional = value;
    }


    /**
     * Returns this MSAGAddressImpl's postDirectional
     * 
     * @return the postDirectional
     * 
    */

    public java.lang.String getPostDirectional() {
        return _msagaddressimpl_postDirectional;
    }

    /**
     * Changes the preDirectional to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new preDirectional for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPreDirectional(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _msagaddressimpl_preDirectional = value;
    }


    /**
     * Returns this MSAGAddressImpl's preDirectional
     * 
     * @return the preDirectional
     * 
    */

    public java.lang.String getPreDirectional() {
        return _msagaddressimpl_preDirectional;
    }

    /**
     * Changes the streetNm to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetNm for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetNm(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _msagaddressimpl_streetNm = value;
    }


    /**
     * Returns this MSAGAddressImpl's streetNm
     * 
     * @return the streetNm
     * 
    */

    public java.lang.String getStreetNm() {
        return _msagaddressimpl_streetNm;
    }

    /**
     * Changes the streetNmSuf to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new streetNmSuf for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStreetNmSuf(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _msagaddressimpl_streetNmSuf = value;
    }


    /**
     * Returns this MSAGAddressImpl's streetNmSuf
     * 
     * @return the streetNmSuf
     * 
    */

    public java.lang.String getStreetNmSuf() {
        return _msagaddressimpl_streetNmSuf;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        MSAGAddress val = null;
        try { 
            val = (MSAGAddress)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("MSAGAddressImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two MSAGAddress are equal.
     * The result is true if and only if the argument is not null 
     * and is an MSAGAddress object that has the arguments.
     * 
     * @param value the Object to compare with this MSAGAddress
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof MSAGAddress)) {
            MSAGAddress argVal = (MSAGAddress) value;
            if( !Utils.compareAttributes( getCity(), argVal.getCity())) { return false; } 
            if( !Utils.compareAttributes( getHouseNr(), argVal.getHouseNr())) { return false; } 
            if( !Utils.compareAttributes( getHouseNrPref(), argVal.getHouseNrPref())) { return false; } 
            if( !Utils.compareAttributes( getHouseNrSuf(), argVal.getHouseNrSuf())) { return false; } 
            if( !Utils.compareAttributes( getPostDirectional(), argVal.getPostDirectional())) { return false; } 
            if( !Utils.compareAttributes( getPreDirectional(), argVal.getPreDirectional())) { return false; } 
            if( !Utils.compareAttributes( getStreetNm(), argVal.getStreetNm())) { return false; } 
            if( !Utils.compareAttributes( getStreetNmSuf(), argVal.getStreetNmSuf())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
