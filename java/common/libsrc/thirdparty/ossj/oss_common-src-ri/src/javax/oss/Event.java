package javax.oss;

/**
 * This is the base interface for an event object in a JMS ObjectMessage payload.
 * <p>
 * If a JMS MessageListener receives such a message,
 * the following code will run:
 * <pre>
 * public void onMessage(Message msg) {
 *    ObjectMessage omsg = (ObjectMessage) msg;
 *    Event event = (Event) omsg.getObject();
 *    System.out.println("New event received from " + event.getApplicationDN());
 * }
 * </pre>
 *
 *
 */


public interface Event extends java.io.Serializable, Cloneable, javax.oss.SerializerFactory
{
   /**
     * Deep copy this Event.
     *
     * @return deep copy of this Event.
     */
     public Object clone();


     /**
      * Return the time the event was published.
      *
      * @return time the event was published.
      */
     public java.util.Date getEventTime();

     /**
      * Set the time that the event is published.
      *
      * @throws java.lang.IllegalArgumentException
      */
     public void   setEventTime( java.util.Date time)
     throws java.lang.IllegalArgumentException;


   /**
    * Return the DN of the application sending the event.
    * The format of this DN is described in
    * @link ManagedEntityKey#getApplicationDN()
    *
    * @throws java.lang.IllegalArgumentException
    * @return applicationDN.
    */
    public String getApplicationDN();

    public void   setApplicationDN ( String applicationDN)
    throws java.lang.IllegalArgumentException;

}
