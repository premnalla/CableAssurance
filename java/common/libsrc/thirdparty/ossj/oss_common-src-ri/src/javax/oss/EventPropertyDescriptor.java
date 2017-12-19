package javax.oss;

/**
 * The EventPropertyDescriptor is used to document and
 * specify the filterable properties of an event.
 *
 * An Interface extending the base EventPropertyDescriptor
 * interface must be defined for each type of application
 * specific event emitted by a component.
 *
 * The name of the specific EventPropertyDescriptor
 * interface should be <i>EventType</i>EventPropertyDescriptor
 * where the EventType is the name of the &lt;EventType&gt;
 * interface.
 *
 *
 * @see javax.oss.Event
 */
public interface EventPropertyDescriptor extends java.io.Serializable
{

   /**
    * Get the name of the event type
    * associated with the descriptor.
    *
    * @return the class name of the Event interface
    * associated with this Event descriptor.
    */
   public String   getEventType();



   /**
    * Get the names of the filterable properties
    * associated with an Event.
    *
    * @return String array of <i>XXX</i>_PROP_NAME's
    */
   public String[] getPropertyNames();



   /**
    * Get the types for the filterable properties
    * associated with an Event.
    *
    * @return String array of <i>XXX</i>_PROP_TYPE's
    */
   public String[]  getPropertyTypes();



   /**
    * Factory for Event associated with
    * that Event Descriptor.
    *
    * @return an implementation of the Event interface.
    */
   public Event makeEvent();


   /**
    * INT type. Its value is the string "int".
    */
 public static final String INT ="int";

   /**
    * BYTE type. Its value is the string "byte".
    */
 public static final String BYTE ="byte";

   /**
    * SHORT type. Its value is the string "short".
    */
 public static final String SHORT = "short";

   /**
    * BOOLEAN type. Its value is the string "boolean".
    */
 public static final String BOOLEAN = "boolean";

   /**
    * DOUBLE type. Its value is the string "double".
    */
 public static final String DOUBLE = "double";

   /**
    * LONG type. Its value is the string "long".
    */
 public static final String LONG = "long";

   /**
    * INT FLOAT. Its value is the string "float".
    */
 public static final String FLOAT = "float";

   /**
    * STRING type. Its value is the string "java.lang.String".
    */
 public static final String STRING = "java.lang.String";


/**
 * Definition of the Event Type property name OSS_EVENT_TYPE_PROP_NAME. Its
 * value is the string "OSS_EVENT_TYPE".
 */
   public static final String OSS_EVENT_TYPE_PROP_NAME = "OSS_EVENT_TYPE";

/**
 * Definition of the Event Type property name OSS_EVENT_TYPE_PROP_TYPE. Its
 * value is the type STRING.
 */
   public static final String OSS_EVENT_TYPE_PROP_TYPE = STRING;

   /**
    * Definition of the mandatory OSS_APPLICATION_DN_PROP_NAME Application Type property name.
    * Its value is the string "OSS_APPLICATION_DN".
    */
   public static final String OSS_APPLICATION_DN_PROP_NAME = "OSS_APPLICATION_DN";

   /**
    * Definition of the mandatory OSS_APPLICATION_DN_PROP_TYPE Application Type property name.
    * Its value is the type STRING.
    */
   public static final String OSS_APPLICATION_DN_PROP_TYPE = STRING;

}
