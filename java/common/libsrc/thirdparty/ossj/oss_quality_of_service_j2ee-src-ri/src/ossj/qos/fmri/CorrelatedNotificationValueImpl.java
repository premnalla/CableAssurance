package ossj.qos.fmri;

import javax.oss.fm.monitor.CorrelatedNotificationValue;
import ossj.qos.util.Util;

/**
 * CorrelatedNotificationValueImpl
 *
 * Represents the implementation of a correlated notification identifier.
 * Correlated notifications in one server can be
 * distinguished by a notification ID and a managed object instance.
 *
 * @author Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */

public class CorrelatedNotificationValueImpl implements CorrelatedNotificationValue
{
    private String[] notificationIds = new String[0];
    private String managedObjectDN = null;


    /**
     * CorrelatedNotificationValueImpl - default constructor
     */
    public CorrelatedNotificationValueImpl() {
    }

    /**
     * CorrelatedNotificationValueImpl constructor
     */
    public CorrelatedNotificationValueImpl( String[] ids, String name ) {
        notificationIds = ids;
        managedObjectDN = name;
    }

    /**
     * Returns a set of notification IDs.
     *
     * @return <code>String[] </code> - A String array of Notification IDs.
     * @see #setNotificationIds
     */
    public String[] getNotificationIds() {
        return notificationIds;
    }

    /**
     * Returns managed object instance.
     *
     * @return <code>String</code> - Managed object instance.
     * @see #setManagedObjectInstance
     */

    public String getManagedObjectInstance() {
        return managedObjectDN;
    }

    /**
     * Sets a set of notification IDs.
     *
     * @param ids An array of Strings that represent the Notifiction IDs.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getNotificationIds
     */
    public void setNotificationIds ( String[] ids )
    throws java.lang.IllegalArgumentException  {
        if ( ids == null || ids.length == 0 ) {
            throw new IllegalArgumentException( "Invalid notificaitonIds entered. Empty notification ids are illegal." ); 
        }
        notificationIds = ids;
        return;
    }

    /**
     * Sets a managed object instance.
     *
     * @param moi A String that represents the managed object instance.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getManagedObjectInstance
     */
    public void setManagedObjectInstance( String moc )
    throws java.lang.IllegalArgumentException {
        if ( moc == null ) {
            throw new IllegalArgumentException( "Null managed object instance name entered." ); 
        }
        managedObjectDN = moc;
        return;
    }

    /**
     * Performs a deep copy of this correlated notification value.
     *
     * @return deep copy of this correlated notification value.
     */
    public Object clone() {
        CorrelatedNotificationValueImpl obj = null;
        try {
            obj = (CorrelatedNotificationValueImpl) super.clone();
            obj.notificationIds = (String[]) notificationIds.clone();
        }
        catch ( CloneNotSupportedException cex ) {
            // should never happen
            //System.out.println( "Problem cloning CorrelatedNotificationValue." );
        }
        return obj;
    }
    
    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in CorrelatedNotificationValueImpl instance is equal to 
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof CorrelatedNotificationValueImpl ) {
            CorrelatedNotificationValueImpl objCorNotVal = (CorrelatedNotificationValueImpl)anObject;
            isEqual = ( Util.isEqual( managedObjectDN, objCorNotVal.managedObjectDN ) && 
                        Util.isEqual( notificationIds, objCorNotVal.notificationIds ) );
        }
        return isEqual;    
    }
    
     /**
     * Returns a string representation of this class.
     *
     * @return String - representation of this class.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(200);
        buffer.append(Util.rightJustify(30,"managedObjectInstance = ") + managedObjectDN + "\n" );
        buffer.append(Util.rightJustify(30,"notificationIds = " ) + Util.printObject( notificationIds ) );
        return buffer.toString();
    }
}
