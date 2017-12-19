

package com.nokia.oss.ossj.common.ri;

import javax.oss.*;

import com.sun.oss.common.ri.XmlSerializerImpl ;

/** Basic implementation of the <CODE>javax.oss.Event</CODE> interface.
 *
 *   @author BanuPrasad Dhanakoti Nokia Networks
 *   @version 1.1
 * January 2002
 */
public class BaseEvent implements javax.oss.Event {

    private java.util.Date eventTime;
    String eventType;
    String applicationDN;
    String sourceIndicator;
    String notificationId;
      
    /** Creates new BaseEevnt
     * @param applicationDN specifies the distinguished name of the type of the application which issued this event
 */
    public BaseEvent(String applicationDN) {
        setApplicationDN(applicationDN);
        setEventTime(new java.util.Date());
    }

     /**
      * Returns the time when the event was published.
      * <p>
      * @return the event time
 */
    public java.util.Date getEventTime() {
        return eventTime;
    }

/** set method for eventTime property
 * @param newTime adjusts the <CODE>eventTime</CODE> property to <CODE>newTime</CODE>
 * @throws IllegalArgumentException when time is illegal in some sense
 */    
    public void setEventTime(final java.util.Date newTime) throws java.lang.IllegalArgumentException {
        eventTime = newTime;
    }

   /**
    * The DN of the Application sending the Event.
    * The format of this DN is: <p>
    *
    * <i>deployment dependent top context</i>
    * /System=<i>vendor</i>-<i>id</i>
    * /ApplicationType=<i>api-name</i>
    * <p>
    * under which the components the building block
    * sending the event are deployed.
    * 
    * @return DN of ApplicationDN
    */
    public java.lang.String getApplicationDN() {
        return applicationDN;
    }

   /**
    * The DN of the Application sending the Event.
    * The format of this DN is: <p>
    *
    * <i>deployment dependent top context</i>
    * /System=<i>vendor</i>-<i>id</i>
    * /ApplicationType=<i>api-name</i>
    * <p>
    * under which the components the building block
    * sending the event are deployed.
    * @param newApplicationDN Destinguished Name of the Application Type
    * @throws IllegalArgumentException if format is not correct
 */
    public void setApplicationDN(java.lang.String newApplicationDN) throws java.lang.IllegalArgumentException {
        applicationDN = newApplicationDN;
    }
    
   /**
     * Deep copy of this Event
     *
     * Subclasses of BaseEvent should call this method first, which calls Object.clone() itself to create 
     * a shallow copy first, and then hande all properties which have to be deeply cloned.
     * @return deep copy of this Event
     */
    public Object clone() {
        try {
            // first call clone in Object
            Object myClone = super.clone();

            // then update all fields which are only a shallow copy
            BaseEvent anBaseEventClone = (BaseEvent)myClone;
            anBaseEventClone.eventTime = (java.util.Date)eventTime.clone();
            
            return anBaseEventClone;
            
        } catch (CloneNotSupportedException cnse) {
            System.out.println(cnse.toString());
            return null;
        }
    }
    
    /**
     * Manufacture a Serializer for the object type inheriting
     * the interface.
     *
     * @param serializerType The class name of the serializer interface that must
     * be created. For example <CODE>XmlSerializer.getClass().getName()</CODE>
     * @return A serializer matching the serializer type .
     * @exception java.lang.IllegalArgumentException If an Illegal Argument is provided or if no Serializer can be created matching
     * the provided Serializer Type.
     */
    public Serializer makeSerializer(String serializerType) throws java.lang.IllegalArgumentException {
        if( serializerType.equals(XmlSerializer.class.getName()))
          return new XmlSerializerImpl(this.getClass().getName() );
        else throw new java.lang.IllegalArgumentException("Unrecognized Serializer Type");
    }
    
    /**
     * Returns all the serializer types than can be created by this factory.
     * This may return an empty array, in case no serializer is
     * implemented.
     *
     * @return Array of supported serializer types.
     */
    public String[] getSupportedSerializerTypes() {
       String[] serializerTypes = new String[1];
        serializerTypes[0] = new String( XmlSerializer.class.getName());
        return serializerTypes;
    }
    
}
