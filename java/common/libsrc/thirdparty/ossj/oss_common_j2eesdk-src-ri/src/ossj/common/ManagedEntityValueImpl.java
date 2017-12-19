/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import java.beans.*;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.oss.*;


/**
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public abstract class ManagedEntityValueImpl extends AttributeAccessImpl
    implements javax.oss.ManagedEntityValue {
    // String array which contains all attribute Names
    private static final String[] attributeNames = { KEY };

    // writeable attributes
    private static final String[] settableAttributeNames = {  //    		KEY
         };

    /** holds value for property lastModifiedOnServer.
     * This is the date of the last modification of that managed entity on the
     * server. Comparing the value of the property in this object with the value
     * on the server on an attempt to set new values on the server with this object
     * enables the server to detect an outdated value object (this)
    */
    protected Date lastModifiedOnServer;

    /**
     * Holds value of property managedEntityKeyInstance use
     *
     */
    protected javax.oss.ManagedEntityKey _managedEntityKeyInstance = null;

    /** Holds the key fo rthis entity
     *
     */
    protected javax.oss.ManagedEntityKey _managedEntityKey;

    /**
     * Creates new ManagedEntityValueImpl
     *
     */
    public ManagedEntityValueImpl() {
        super();
    }

    /**
     * Setter for the mandatory predefined attribute named KEY
     * @param key
     * @throws java.lang.IllegalArgumentException
     */
    public void setManagedEntityKey(ManagedEntityKey key)
        throws java.lang.IllegalArgumentException {
        setDirtyAttribute(KEY);
        this._managedEntityKey = key;
    }

    /**
     * Getter for the mandatory predefined attribute named KEY
     * @return
     * @throws java.lang.IllegalStateException
     */
    public ManagedEntityKey getManagedEntityKey() throws java.lang.IllegalStateException {
        checkAttribute(KEY);

        return _managedEntityKey;
    }

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        anAttributeManager.addAttributes(attributeNames);
        super.addAttributesTo(anAttributeManager);
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        anAttributeManager.setSettableAttributes(settableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }

    /**
     * Returns the date fo the latest modification.
     * @return
     */
    public Date getLastModifiedOnServer() {
        return lastModifiedOnServer;
    }

    /**
     * Set the lastModified Date value to the given date.
     * @param newDate
     */
    public void setLastModifiedOnServer(Date newDate) {
        lastModifiedOnServer = newDate;
    }

    /**
     * Last Update Version Number
     */
    public long getLastUpdateVersionNumber() {
        if (lastModifiedOnServer != null) {
            return getLastModifiedOnServer().getTime();
        } else {
            return 0L;
        }
    }

    /**
     * Set the last update version number.
     *
     * This field should never be set by the application client. Mutator is
     * provided for Serialization and Deserialization purposes only.
     *
     * @param lastUpdateNumber
     */
    public void setLastUpdateVersionNumber(long lastUpdateNumber)
        throws java.lang.IllegalArgumentException {
        setLastModifiedOnServer(new Date(lastUpdateNumber));
    }

    /**
     * Returns the position of the first invalid attribute name, else returns -1.
     *
     * @param attributeNames
     * @return
     * @throws javax.oss.IllegalArgumentException
     */
    public int validateAttributeNames(String[] attributeNames)
        throws javax.oss.OssIllegalArgumentException {
        if (attributeNames == null) {
            return -1;
        }

        for (int i = 0; i < attributeNames.length; i++) {
            if (!isValidAttributeName(attributeNames[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns a deep copy of this value
     *
     * Subclasses of ManagedEntity should call this method first, which calls Object.clone() itself to create
     * a shallow copy first, and then hande all properties which have to be deeply cloned.
     * @return deep copy of this Event
     */
    public Object clone() {
        // first call clone in Object
        Object myClone = super.clone();

        // then update all fields which are only a shallow copy
        ManagedEntityValueImpl anMevImplClone = (ManagedEntityValueImpl) myClone;

        if (isPopulated(KEY)) {
            if (this.getManagedEntityKey() != null) {
                anMevImplClone.setManagedEntityKey((ManagedEntityKey) ((ManagedEntityKey) this
                    .getManagedEntityKey()).clone());
            } else {
                anMevImplClone.setManagedEntityKey(null);
            }
        }

        if (lastModifiedOnServer != null) {
            anMevImplClone.lastModifiedOnServer = (java.util.Date) lastModifiedOnServer.clone();
        } else {
            anMevImplClone.lastModifiedOnServer = null;
        }

        return anMevImplClone;
    }

    /**
     * Checks whether two ManagedEntityValue are equal.
     * The result is true if and only if the argument is not null
     * and is an ManagedEntityValue object that has the arguments.
     *
     * @param value the Object to compare with this ManagedEntityValue
     * @return if the objects are equal; false otherwise.
     */
    public boolean equals(Object value) {
        if (this == value) {
            return true;
        }

        if ((value != null) && (value instanceof ManagedEntityValue)) {
            ManagedEntityValue argVal = (ManagedEntityValue) value;

            return Utils.compareAttributeAccess(this, argVal);
        }

        return false;
    }

    /**
     * Manufacture a Key for this of managed entity.
     */
    public ManagedEntityKey makeManagedEntityKey() {
        return (ManagedEntityKey) getManagedEntityKeyInstance().clone();
    }

    /**
     * Returns the managed entity key instance.
     * @return
     */
    public javax.oss.ManagedEntityKey getManagedEntityKeyInstance() {
        return _managedEntityKeyInstance;
    }

    /** Setter for property managedEntityKeyInstance.
     * @param managedEntityKeyInstance New value of property managedEntityKeyInstance.
     */
    public void setManagedEntityKeyInstance(javax.oss.ManagedEntityKey managedEntityKey) {
        this._managedEntityKeyInstance = managedEntityKey;
    }

    /**
     * Returns a String representation of the populated attribute of this Managed entity
     * @return
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.getClass().getName());
        buffer.append(": ");

        String[] populated = getPopulatedAttributeNames();
        int i = 0;

        if ((populated != null) && (populated.length > 0)) {
            Object obj;

            while (true) {
                buffer.append(populated[i]);
                buffer.append("=");
                obj = getAttributeValue(populated[i]);

                if (obj == null) {
                    buffer.append("null");
                } else {
                    if (obj.getClass().isArray()) {
                        buffer.append(ArrayWriter.toString((Object[]) obj));
                    } else {
                        buffer.append(obj.toString());
                    }
                }

                if (++i == populated.length) {
                    break;
                }

                buffer.append("; ");
            }
        }

        return buffer.toString();
    }

    /** Gets all attributes which can be set in the server implementation.
     * <p>
     * It is required to return a subset of the array returned
     * by <CODE>getAttributeNames()</CODE>.
     */
    public String[] getSettableAttributeNames() {
        return myAttributeManager.getSettableAttributeNames();
    }

    /*
     * TODO ??????????????????????????
     * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
     * public String toQUeryFilter(){}
     */
}
