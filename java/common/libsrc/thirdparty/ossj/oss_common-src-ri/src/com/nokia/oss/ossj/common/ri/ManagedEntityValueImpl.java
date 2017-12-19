
package com.nokia.oss.ossj.common.ri;


import java.lang.reflect.Method;

import javax.oss.*;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.beans.*;

/**
 *
 *   @author BanuPrasad Dhanakoti Nokia Networks
 *  @version 1.1
 *   January 2002
 */
public abstract class ManagedEntityValueImpl extends AttributeAccessImpl implements javax.oss.ManagedEntityValue {
    
    // String array which conatins all attribute Names
    private static final String[] attributeNames = {KEY};

    // writeable attributes
    private static final String[] settableAttributeNames = {};
    
/** holds value for property lastModifiedOnServer.
 * This is the date of the last modification of that managed entity on the
 * server. Comparing the value of the property in this object with the value
 * on the server on an attempt to set new values on the server with this object
 * enables the server to detect an outdated value object (this)
 */    
    protected Date lastModifiedOnServer;
    
    /** Holds value of property managedEntityKeyDummy. Used as template for makeManagedEntityKey() */
    protected javax.oss.ManagedEntityKey managedEntityKeyDummy;
    
    /** Creates new ManagedEntityValueImpl */
    public ManagedEntityValueImpl() {
        super();
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

    public Date getLastModifiedOnServer() {
        return lastModifiedOnServer;
    }
    
    public void setLastModifiedOnServer(Date newDate) {
        lastModifiedOnServer = newDate;
    }
    
    /**
     * Last Update Version Number
     */
    public long getLastUpdateVersionNumber() {
        return getLastModifiedOnServer().getTime();
    }
    
    /**
     * Set the last update version number.
     * 
     * This field should never be set by the application client. Mutator is
     * provided for Serialization and Deserialization purposes only.
     * 
     * @param lastUpdateNumber
     */
    public void setLastUpdateVersionNumber( long lastUpdateNumber)
    throws java.lang.IllegalArgumentException {
        setLastModifiedOnServer(new Date(lastUpdateNumber));
    }
    // helper methods for users of MEV
    
    public int validateAttributeNames(String[] attributeNames) throws javax.oss.IllegalArgumentException {
        if (attributeNames == null) {
            return -1;
        }
        for (int i=0 ; i<attributeNames.length ; i++) {
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
        try {
            // first call clone in Object
            Object myClone = super.clone();

            // then update all fields which are only a shallow copy
            ManagedEntityValueImpl anMevImplClone = (ManagedEntityValueImpl)myClone;
            anMevImplClone.lastModifiedOnServer = (java.util.Date)lastModifiedOnServer.clone();
            
            return anMevImplClone;
            
        } catch (CloneNotSupportedException cnse) {
            System.out.println(cnse.toString());
            return null;
        }
        
    }
    
    /** Getter for property managedEntityKeyDummy.
     * @return Value of property managedEntityKeyDummy.
 */
    /**
     * Manufacture a Key for this of managed entity.
     */
    public ManagedEntityKey makeManagedEntityKey() {
		return (ManagedEntityKey) getManagedEntityKeyDummy().clone(); 
    }
   
    public javax.oss.ManagedEntityKey getManagedEntityKeyDummy() {
        return managedEntityKeyDummy;
    }    
    
    /** Setter for property managedEntityKeyDummy.
     * @param managedEntityKeyDummy New value of property managedEntityKeyDummy.
 */
    public void setManagedEntityKeyDummy(javax.oss.ManagedEntityKey managedEntityKeyDummy) {
        this.managedEntityKeyDummy = managedEntityKeyDummy;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.getClass().getName());
        buffer.append(": ");
        String[] populated = getPopulatedAttributeNames();
        int i=0;
        if (populated!=null && populated.length>0) {
            Object obj;
            while (true) {
                buffer.append(populated[i]);
                buffer.append("=");
                obj = getAttributeValue(populated[i]);
                if (obj == null) {
                    buffer.append("null");
                } else {
                    if (obj.getClass().isArray()) {
                        buffer.append(ArrayWriter.toString((Object[])obj));
                    } else {
                        buffer.append(obj.toString());
                    }
                }
                if (++i==populated.length) break;
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

}
