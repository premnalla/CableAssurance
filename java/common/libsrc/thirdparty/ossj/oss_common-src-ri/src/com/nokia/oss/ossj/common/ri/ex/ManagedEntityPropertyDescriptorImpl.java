package com.nokia.oss.ossj.common.ri.ex;

import javax.oss.*;

/**
  * String constants that define the predefined 
  * JMS property names.
  * <p>
  * For the values of the properties, see {@link JVTActivationSession}.
  * <p>
  * The inherited method {@link javax.oss.EventPropertyDescriptor#makeEvent} returns
  * always null, i.e. it is not supported.
  */

public class ManagedEntityPropertyDescriptorImpl implements javax.oss.EventPropertyDescriptor {
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
      String type;
    String[] propertyNames;
    String[] propertyTypes;
    javax.oss.Event dummyEvent;
    
      public ManagedEntityPropertyDescriptorImpl(String type, String[] properties, String[] propertyTypes, javax.oss.Event dummyEvent) {
        this.type = type;
        this.propertyNames = propertyNames;
        this.propertyTypes = propertyTypes;
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
        return propertyNames;
    }
    
    /**
     * Get the types for the filterable properties
     * associated with an Event.
     *
     * @return String array of <i>XXX</i>_PROP_TYPE's
 */
    public String[] getPropertyTypes() {
        return propertyTypes;
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
   
    

    public static final String OSS_API_CLIENT_ID_PROP_NAME = "OSS_API_CLIENT_ID";
    public static final String OSS_API_CLIENT_ID_PROP_TYPE = STRING;

}

