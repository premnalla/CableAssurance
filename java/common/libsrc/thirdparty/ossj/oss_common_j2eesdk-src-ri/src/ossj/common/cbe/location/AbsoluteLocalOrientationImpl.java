/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.location;

import javax.oss.cbe.location.AbsoluteLocalOrientation;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.location.AbsoluteLocalOrientation</CODE> interface.  
 * 
 * @see javax.oss.cbe.location.AbsoluteLocalOrientation
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AbsoluteLocalOrientationImpl
implements AbsoluteLocalOrientation
{ 

    /**
     * Constructs a new AbsoluteLocalOrientationImpl with empty attributes.
     * 
     */

    public AbsoluteLocalOrientationImpl() {
    }

    private java.lang.String _absolutelocalorientationimpl_piCoord = null;
    private java.lang.String _absolutelocalorientationimpl_psiCoord = null;
    private java.lang.String _absolutelocalorientationimpl_thetaCoord = null;


    /**
     * Changes the piCoord to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new piCoord for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPiCoord(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _absolutelocalorientationimpl_piCoord = value;
    }


    /**
     * Returns this AbsoluteLocalOrientationImpl's piCoord
     * 
     * @return the piCoord
     * 
    */

    public java.lang.String getPiCoord() {
        return _absolutelocalorientationimpl_piCoord;
    }

    /**
     * Changes the psiCoord to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new psiCoord for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPsiCoord(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _absolutelocalorientationimpl_psiCoord = value;
    }


    /**
     * Returns this AbsoluteLocalOrientationImpl's psiCoord
     * 
     * @return the psiCoord
     * 
    */

    public java.lang.String getPsiCoord() {
        return _absolutelocalorientationimpl_psiCoord;
    }

    /**
     * Changes the thetaCoord to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new thetaCoord for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setThetaCoord(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _absolutelocalorientationimpl_thetaCoord = value;
    }


    /**
     * Returns this AbsoluteLocalOrientationImpl's thetaCoord
     * 
     * @return the thetaCoord
     * 
    */

    public java.lang.String getThetaCoord() {
        return _absolutelocalorientationimpl_thetaCoord;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AbsoluteLocalOrientation val = null;
        try { 
            val = (AbsoluteLocalOrientation)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("AbsoluteLocalOrientationImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two AbsoluteLocalOrientation are equal.
     * The result is true if and only if the argument is not null 
     * and is an AbsoluteLocalOrientation object that has the arguments.
     * 
     * @param value the Object to compare with this AbsoluteLocalOrientation
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AbsoluteLocalOrientation)) {
            AbsoluteLocalOrientation argVal = (AbsoluteLocalOrientation) value;
            if( !Utils.compareAttributes( getPiCoord(), argVal.getPiCoord())) { return false; } 
            if( !Utils.compareAttributes( getPsiCoord(), argVal.getPsiCoord())) { return false; } 
            if( !Utils.compareAttributes( getThetaCoord(), argVal.getThetaCoord())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
