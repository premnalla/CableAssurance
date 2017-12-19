/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.CorrelatedNotification;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.CorrelatedNotification</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.CorrelatedNotification
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CorrelatedNotificationImpl
implements CorrelatedNotification
{ 

    /**
     * Constructs a new CorrelatedNotificationImpl with empty attributes.
     * 
     */

    public CorrelatedNotificationImpl() {
    }

    private java.lang.String[] _correlatednotificationimpl_managedObjectInstance = null;
    private java.lang.String[] _correlatednotificationimpl_notificationIds = null;


    /**
     * Changes the managedObjectInstance to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new managedObjectInstance for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setManagedObjectInstance(java.lang.String[] value)
    throws java.lang.IllegalArgumentException    {
        _correlatednotificationimpl_managedObjectInstance = value;
    }


    /**
     * Returns this CorrelatedNotificationImpl's managedObjectInstance
     * 
     * @return the managedObjectInstance
     * 
    */

    public java.lang.String[] getManagedObjectInstance() {
        return _correlatednotificationimpl_managedObjectInstance;
    }

    /**
     * Changes the notificationIds to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new notificationIds for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setNotificationIds(java.lang.String[] value)
    throws java.lang.IllegalArgumentException    {
        _correlatednotificationimpl_notificationIds = value;
    }


    /**
     * Returns this CorrelatedNotificationImpl's notificationIds
     * 
     * @return the notificationIds
     * 
    */

    public java.lang.String[] getNotificationIds() {
        return _correlatednotificationimpl_notificationIds;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        CorrelatedNotification val = null;
        try { 
            val = (CorrelatedNotification)super.clone();

            if( this.getManagedObjectInstance()!=null) 
                val.setManagedObjectInstance((java.lang.String[])((java.lang.String[]) this.getManagedObjectInstance()).clone());
            else
                val.setManagedObjectInstance( null );

            if( this.getNotificationIds()!=null) 
                val.setNotificationIds((java.lang.String[])((java.lang.String[]) this.getNotificationIds()).clone());
            else
                val.setNotificationIds( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("CorrelatedNotificationImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two CorrelatedNotification are equal.
     * The result is true if and only if the argument is not null 
     * and is an CorrelatedNotification object that has the arguments.
     * 
     * @param value the Object to compare with this CorrelatedNotification
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof CorrelatedNotification)) {
            CorrelatedNotification argVal = (CorrelatedNotification) value;
            if( !Utils.compareAttributes( getManagedObjectInstance(), argVal.getManagedObjectInstance())) { return false; } 
            if( !Utils.compareAttributes( getNotificationIds(), argVal.getNotificationIds())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
