/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;

import java.util.ArrayList;

import javax.oss.EventPropertyDescriptor;


/**
  * String constants that define the predefined
  * JMS property names.
  * <p>
  * For the values of the properties, see {@link JVTActivationSession}.
  * <p>
  * The inherited method {@link javax.oss.EventPropertyDescriptor#makeEvent} returns
  * always null, i.e. it is not supported.
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
  */
public class EventPropertyDescriptorImpl implements javax.oss.EventPropertyDescriptor {
    /**
      * Factory for Event associated with that Event Descriptor.
      * <p>
      * A client may call this method to implicitly load the class file for the
      * event type to its local JVM. Otherwise a implementation specific .jar file
      * has to be deployed in the client.
      *
      * <p><b>Postcondition:</b>
      * <ul>
      * <li>The returned object must match to this descriptor:<br>
      *     <code>result instanceof Class.forName(getEventType())</code>.
      * </li>
      * </ul>
      * @return An implementation of the Event interface.
      */
    String type = null;
    protected ArrayList propertyNames = new ArrayList();
    protected ArrayList propertyTypes = new ArrayList();
    javax.oss.Event dummyEvent = null;

    /**
     * Creates a new IRPEventPropertyDescriptorImpl object.
     *
     */
    public EventPropertyDescriptorImpl() {
    }

    /**
     * Creates a new EventPropertyDescriptorImpl object.
     *
     * @param type
     * @param property names
     * @param property types
     * @param dummyEvent
     */
    public EventPropertyDescriptorImpl(String type, String[] propertyNames, String[] propertyTypes,
        javax.oss.Event dummyEvent) {
    	
    	//VP note: TODO use the intraspection to dynamically build the properties.....
    	// instead of setting all the attribute in constructor....
        this.type = type;

        for (int i = propertyNames.length - 1; i >= 0; i--) {
            this.propertyNames.add(propertyNames[i]);
            this.propertyTypes.add(propertyTypes[i]);
        }

        this.dummyEvent = dummyEvent;
    }

    /**
     * Get the name of the event type
     * associated with that descriptor.
     *
     * @return Fully qualified name of the Event interface
     * associated with that Event descriptor.
    */
    public String getEventType() {
        return type;
    }

    /**
     * Get the names of the filterable properties
     * associated with an Event.
     *
     * @return String array of <i>XXX</i>_PROP_NAME's
    */
    public String[] getPropertyNames() {
        return (String[])propertyNames.toArray(new String[0]);
    }

    /**
     * Get the types for the filterable properties
     * associated with an Event.
     *
     * @return String array of <i>XXX</i>_PROP_TYPE's
    */
    public String[] getPropertyTypes() {
        return (String[])propertyTypes.toArray(new String[0]);
    }

    /**
     * Factory for Event associated with
     * that Event Descriptor.
     *
     * @return An implementation of the Event interface.
    */
    public javax.oss.Event makeEvent() {
        return dummyEvent;
    }
}
