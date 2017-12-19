/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import ossj.common.AttributeManager;
import ossj.common.EventImpl;
import ossj.common.Utils;

import javax.oss.ManagedEntityValue;


/**
 * Create event sent when a new instance is created.
 *
 * @see javax.oss.AttributeAccess
 *
 * @see javax.oss.Event
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class ManagedEntityCreateEvent extends EventImpl {
    private static final String[] attributeNames = { "managedEntityValue" };
    private static final String[] settableAttributeNames = { "managedEntityValue" };

    /**
      * Holds the Attribute manager that manage the optional fields
     */
    private static AttributeManager attributemanager;

    //
    // All getters and setters for all declared attributes:
    //
    private javax.oss.ManagedEntityValue _managedentitycreateevent_managedEntityValue = null;

    /**
     * Creates a new ManagedEntityCreateEvent object.
     */
    public ManagedEntityCreateEvent() {
        this("", null);
    }

    /**
     * Creates a new ManagedEntityCreateEvent object.
     *
     * @param domain DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public ManagedEntityCreateEvent(String domain, javax.oss.ManagedEntityValue value) {
        super(domain);

        try {
            setManagedEntityValue(value);
        } catch (Exception ex) {
        }
    }

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ManagedEntityCreateEvent.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ManagedEntityCreateEvent.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ManagedEntityCreateEvent.settableAttributeNames);
            super.configureAttributes(anAttributeManager);
        }
    }

    protected AttributeManager getAttributeManager() {
        return attributemanager;
    }

    protected AttributeManager makeAttributeManager() {
        attributemanager = new AttributeManager();

        return attributemanager;
    }

    /**
      * Changes the reason to be equal to the given argument.
      * null argument is supported.
      *
      * @param value the new reason for this object.
     */
    public void setManagedEntityValue(ManagedEntityValue value) {
        setDirtyAttribute("managedEntityValue");
        _managedentitycreateevent_managedEntityValue = value;
    }

    /**
      * Returns this NotifyPotentialFaultyAlarmListEventImpl's reason
      *
      * @return the reason
      *
     */
    public ManagedEntityValue getManagedEntityValue() {
        checkAttribute("managedEntityValue");

        return _managedentitycreateevent_managedEntityValue;
    }

    /**
      * Creates a new object of the same class and with the same contents as this object.
      * @return a clone of this instance.
     */
    public Object clone() {
        try {
            ManagedEntityCreateEvent val = (ManagedEntityCreateEvent) super.clone();

            if (isPopulated("managedEntityValue")) {
                if (this.getManagedEntityValue() != null) {
                    val.setManagedEntityValue(((ManagedEntityValue) this.getManagedEntityValue()
                                                                        .clone()));
                } else {
                    val.setManagedEntityValue(null);
                }
            }

            return val;
        } catch (Exception e) {
            return null;
        }
    }

    /**
      * Checks whether two ManagedEntityCreateEvent are equal.
      * The result is true if and only if the argument is not null
      * and is an NotifyPotentialFaultyAlarmListEvent object that has the arguments.
      *
      * @param value the Object to compare with this ManagedEntityCreateEvent
      * @return if the objects are equal; false otherwise.
      */
    public boolean equals(Object value) {
        if (this == value) {
            return true;
        }

        if ((value != null) && (value instanceof ManagedEntityCreateEvent)) {
            ManagedEntityCreateEvent argVal = (ManagedEntityCreateEvent) value;

            return Utils.compareAttributeAccess(this, argVal);
        }

        return false;
    }
}
