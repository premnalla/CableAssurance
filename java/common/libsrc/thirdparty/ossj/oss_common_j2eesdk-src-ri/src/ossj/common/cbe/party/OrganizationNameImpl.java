/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.OrganizationName;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.OrganizationName</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.OrganizationName
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class OrganizationNameImpl
implements OrganizationName
{ 

    /**
     * Constructs a new OrganizationNameImpl with empty attributes.
     * 
     */

    public OrganizationNameImpl() {
    }

    private java.lang.String _organizationnameimpl_tradingName = null;


    /**
     * Changes the tradingName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new tradingName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTradingName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _organizationnameimpl_tradingName = value;
    }


    /**
     * Returns this OrganizationNameImpl's tradingName
     * 
     * @return the tradingName
     * 
    */

    public java.lang.String getTradingName() {
        return _organizationnameimpl_tradingName;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        OrganizationName val = null;
        try { 
            val = (OrganizationName)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("OrganizationNameImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two OrganizationName are equal.
     * The result is true if and only if the argument is not null 
     * and is an OrganizationName object that has the arguments.
     * 
     * @param value the Object to compare with this OrganizationName
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof OrganizationName)) {
            OrganizationName argVal = (OrganizationName) value;
            if( !Utils.compareAttributes( getTradingName(), argVal.getTradingName())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
