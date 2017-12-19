/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.util;

import java.util.Calendar;
import javax.naming.Name;
import javax.oss.util.InteractionRecord;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.util.InteractionRecord</CODE> interface.  
 * 
 * @see javax.oss.util.InteractionRecord
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class InteractionRecordImpl
implements InteractionRecord
{ 

    /**
     * Constructs a new InteractionRecordImpl with empty attributes.
     * 
     */

    public InteractionRecordImpl() {
    }

    private javax.naming.Name _interactionrecordimpl_systemId = null;
    private java.util.Calendar _interactionrecordimpl_time = null;
    private javax.naming.Name _interactionrecordimpl_userId = null;


    /**
     * Changes the systemId to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new systemId for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSystemId(javax.naming.Name value)    {
        _interactionrecordimpl_systemId = value;
    }


    /**
     * Returns this InteractionRecordImpl's systemId
     * 
     * @return the systemId
     * 
    */

    public javax.naming.Name getSystemId() {
        return _interactionrecordimpl_systemId;
    }

    /**
     * Changes the time to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new time for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTime(java.util.Calendar value)    {
        _interactionrecordimpl_time = value;
    }


    /**
     * Returns this InteractionRecordImpl's time
     * 
     * @return the time
     * 
    */

    public java.util.Calendar getTime() {
        return _interactionrecordimpl_time;
    }

    /**
     * Changes the userId to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new userId for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setUserId(javax.naming.Name value)    {
        _interactionrecordimpl_userId = value;
    }


    /**
     * Returns this InteractionRecordImpl's userId
     * 
     * @return the userId
     * 
    */

    public javax.naming.Name getUserId() {
        return _interactionrecordimpl_userId;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        InteractionRecord val = null;
        try { 
            val = (InteractionRecord)super.clone();

            if( this.getSystemId()!=null) 
                val.setSystemId((javax.naming.Name)((javax.naming.Name) this.getSystemId()).clone());
            else
                val.setSystemId( null );

            if( this.getTime()!=null) 
                val.setTime((java.util.Calendar)((java.util.Calendar) this.getTime()).clone());
            else
                val.setTime( null );

            if( this.getUserId()!=null) 
                val.setUserId((javax.naming.Name)((javax.naming.Name) this.getUserId()).clone());
            else
                val.setUserId( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("InteractionRecordImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two InteractionRecord are equal.
     * The result is true if and only if the argument is not null 
     * and is an InteractionRecord object that has the arguments.
     * 
     * @param value the Object to compare with this InteractionRecord
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof InteractionRecord)) {
            InteractionRecord argVal = (InteractionRecord) value;
            if( !Utils.compareAttributes( getSystemId(), argVal.getSystemId())) { return false; } 
            if( !Utils.compareAttributes( getTime(), argVal.getTime())) { return false; } 
            if( !Utils.compareAttributes( getUserId(), argVal.getUserId())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
