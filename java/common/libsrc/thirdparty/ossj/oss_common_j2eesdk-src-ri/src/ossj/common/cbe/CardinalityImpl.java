/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.Cardinality;


/**
 * An implementation class for the <CODE>javax.oss.cbe.Cardinality</CODE> interface.  
 * 
 * @see javax.oss.cbe.Cardinality
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CardinalityImpl
implements Cardinality
{ 

    /**
     * Constructs a new CardinalityImpl with empty attributes.
     * 
     */

    public CardinalityImpl() {
    }

    private int _cardinalityimpl_maxNumber;
    private int _cardinalityimpl_minNumber;


    /**
     * Changes the maxNumber to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new maxNumber for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setMaxNumber(int value)
    throws java.lang.IllegalArgumentException    {
        _cardinalityimpl_maxNumber = value;
    }


    /**
     * Returns this CardinalityImpl's maxNumber
     * 
     * @return the maxNumber
     * 
    */

    public int getMaxNumber() {
        return _cardinalityimpl_maxNumber;
    }

    /**
     * Changes the minNumber to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new minNumber for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setMinNumber(int value)
    throws java.lang.IllegalArgumentException    {
        _cardinalityimpl_minNumber = value;
    }


    /**
     * Returns this CardinalityImpl's minNumber
     * 
     * @return the minNumber
     * 
    */

    public int getMinNumber() {
        return _cardinalityimpl_minNumber;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        Cardinality val = null;
        try { 
            val = (Cardinality)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("CardinalityImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two Cardinality are equal.
     * The result is true if and only if the argument is not null 
     * and is an Cardinality object that has the arguments.
     * 
     * @param value the Object to compare with this Cardinality
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof Cardinality)) {
            Cardinality argVal = (Cardinality) value;
            if( this.getMaxNumber() != argVal.getMaxNumber()) { return false; } 
            if( this.getMinNumber() != argVal.getMinNumber()) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
