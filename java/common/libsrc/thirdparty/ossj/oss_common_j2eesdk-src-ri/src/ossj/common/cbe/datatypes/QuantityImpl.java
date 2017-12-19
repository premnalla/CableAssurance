/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.datatypes;

import java.math.BigDecimal;
import javax.oss.cbe.datatypes.Quantity;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.datatypes.Quantity</CODE> interface.  
 * 
 * @see javax.oss.cbe.datatypes.Quantity
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class QuantityImpl
implements Quantity
{ 

    /**
     * Constructs a new QuantityImpl with empty attributes.
     * 
     */

    public QuantityImpl() {
    }

    private java.math.BigDecimal _quantityimpl_amount = null;
    private java.lang.String _quantityimpl_units = null;


    /**
     * Changes the amount to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new amount for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAmount(java.math.BigDecimal value)
    throws java.lang.IllegalArgumentException    {
        _quantityimpl_amount = value;
    }


    /**
     * Returns this QuantityImpl's amount
     * 
     * @return the amount
     * 
    */

    public java.math.BigDecimal getAmount() {
        return _quantityimpl_amount;
    }

    /**
     * Changes the units to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new units for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setUnits(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _quantityimpl_units = value;
    }


    /**
     * Returns this QuantityImpl's units
     * 
     * @return the units
     * 
    */

    public java.lang.String getUnits() {
        return _quantityimpl_units;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        Quantity val = null;
        try { 
            val = (Quantity)super.clone();

            val.setAmount(this.getAmount());

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("QuantityImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two Quantity are equal.
     * The result is true if and only if the argument is not null 
     * and is an Quantity object that has the arguments.
     * 
     * @param value the Object to compare with this Quantity
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof Quantity)) {
            Quantity argVal = (Quantity) value;
            if( !Utils.compareAttributes( getAmount(), argVal.getAmount())) { return false; } 
            if( !Utils.compareAttributes( getUnits(), argVal.getUnits())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
