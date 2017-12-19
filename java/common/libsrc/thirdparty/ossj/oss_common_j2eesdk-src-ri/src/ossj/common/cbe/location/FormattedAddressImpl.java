/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.FormattedAddress;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.FormattedAddress</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.FormattedAddress
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class FormattedAddressImpl
implements FormattedAddress
{ 

    /**
     * Constructs a new FormattedAddressImpl with empty attributes.
     * 
     */

    public FormattedAddressImpl() {
    }

    private java.lang.String _formattedaddressimpl_addrLn1 = null;
    private java.lang.String _formattedaddressimpl_addrLn2 = null;
    private java.lang.String _formattedaddressimpl_addrLn3 = null;
    private java.lang.String _formattedaddressimpl_addrLn4 = null;


    /**
     * Changes the addrLn1 to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new addrLn1 for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAddrLn1(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _formattedaddressimpl_addrLn1 = value;
    }


    /**
     * Returns this FormattedAddressImpl's addrLn1
     * 
     * @return the addrLn1
     * 
    */

    public java.lang.String getAddrLn1() {
        return _formattedaddressimpl_addrLn1;
    }

    /**
     * Changes the addrLn2 to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new addrLn2 for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAddrLn2(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _formattedaddressimpl_addrLn2 = value;
    }


    /**
     * Returns this FormattedAddressImpl's addrLn2
     * 
     * @return the addrLn2
     * 
    */

    public java.lang.String getAddrLn2() {
        return _formattedaddressimpl_addrLn2;
    }

    /**
     * Changes the addrLn3 to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new addrLn3 for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAddrLn3(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _formattedaddressimpl_addrLn3 = value;
    }


    /**
     * Returns this FormattedAddressImpl's addrLn3
     * 
     * @return the addrLn3
     * 
    */

    public java.lang.String getAddrLn3() {
        return _formattedaddressimpl_addrLn3;
    }

    /**
     * Changes the addrLn4 to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new addrLn4 for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAddrLn4(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _formattedaddressimpl_addrLn4 = value;
    }


    /**
     * Returns this FormattedAddressImpl's addrLn4
     * 
     * @return the addrLn4
     * 
    */

    public java.lang.String getAddrLn4() {
        return _formattedaddressimpl_addrLn4;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        FormattedAddress val = null;
        try { 
            val = (FormattedAddress)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("FormattedAddressImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two FormattedAddress are equal.
     * The result is true if and only if the argument is not null 
     * and is an FormattedAddress object that has the arguments.
     * 
     * @param value the Object to compare with this FormattedAddress
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof FormattedAddress)) {
            FormattedAddress argVal = (FormattedAddress) value;
            if( !Utils.compareAttributes( getAddrLn1(), argVal.getAddrLn1())) { return false; } 
            if( !Utils.compareAttributes( getAddrLn2(), argVal.getAddrLn2())) { return false; } 
            if( !Utils.compareAttributes( getAddrLn3(), argVal.getAddrLn3())) { return false; } 
            if( !Utils.compareAttributes( getAddrLn4(), argVal.getAddrLn4())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
