package ossj.qos.fmri;

import javax.oss.fm.monitor.AlarmKey;
import javax.oss.ManagedEntityKey;

import javax.oss.Serializer;
import javax.oss.XmlSerializer;

import ossj.qos.xmlri.fmxmlri.FmXmlSerializerImpl;
import ossj.qos.util.Util;


/**
 * AlarmKeyResultImpl
 *
 * Represents the result of an operation on a set of alarm values. It contains
 * the alarm reference, a pass or fail status, and an exception if the result failed.
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */

public class AlarmKeyResultImpl implements javax.oss.fm.monitor.AlarmKeyResult
{
    private AlarmKey alarmKey = null;
    private boolean isSuccess = false;
    private Exception exception = null;

    /**
     * AlarmOperationResultImpl - default constructor
     */
    public AlarmKeyResultImpl()
    {
    }

    /**
     * AlarmOperationResultImpl - constructor
     */
    public AlarmKeyResultImpl( AlarmKey key, boolean status, Exception exp ) {
        alarmKey = key;
        isSuccess = status;
        exception = exp;
    }

    /**
     * Returns alarm reference.
     *
     * @return <code>AlarmKey</code> - an alarm key.
     */
    public AlarmKey getAlarmKey() {
        return alarmKey;
    }


    /**
     * Sets the alarm reference.
     *
     * @param key An AlarmKey - an alarm key.
     */
    public void setAlarmKey( AlarmKey key ) throws java.lang.IllegalArgumentException {
        if ( key == null ) {
            throw new IllegalArgumentException( LogMessages.NULL_ATTRIBUTE_ENTERED_MSG
                                                + "Entered Value: " + key );
        }
        alarmKey = key;
        return;
    }

    /**
     * Indicates if the operation targeted for the alarm reference was successful.
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * Sets the status of the operation relative to the alarm reference.
     * @exception java.lang.IllegalArgumentException
     */
    public void setSuccess( boolean status ) throws java.lang.IllegalArgumentException {
        isSuccess = status;
        return;
    }


    /**
     * Sets the exception indicating why the operation was not successfull.
     *
     * @param exp <code>Exception</code> - Reason for failure.
     * @exception java.lang.IllegalArgumentException
     */
    public void setException( Exception exp )
    throws java.lang.IllegalArgumentException {
        exception = exp;
        return;
    }

    /**
     * Returns exception if the operation was not successfull.
     *
     * @return <code>Exception</code> - Reason for failure.
     */
    public Exception getException() {
        return exception;
    }


      /**
       * Returns the Managed Entity Key for which an operation Result is reported.
       * @return <code>ManagedEntityKey</code> - a managed entity key.
       */
      public ManagedEntityKey getManagedEntityKey() {
        return alarmKey;
      }


      /**
       * Sets the key of the managed entity targeted by the operation.
       *
       * @param key
       * @exception java.lang.IllegalArgumentException
       */
      public void setManagedEntityKey(ManagedEntityKey key)
      throws java.lang.IllegalArgumentException {
         if ( key == null || key instanceof AlarmKey == false ) {
            throw new java.lang.IllegalArgumentException( "Invalid key type entered. " +
            "AlarmKey required.");
        }
        alarmKey = (AlarmKey) key;
        return;
      }

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(200);
        buffer.append(Util.rightJustify(30,"alarm reference = ") + Util.printObject(alarmKey) + "\n");
        buffer.append(Util.rightJustify(30,"isSuccess = ") + isSuccess + "\n");
        buffer.append(Util.rightJustify(30,"exception = ") + Util.printObject(exception) + "\n");

        return buffer.toString();
    }
    /**
     * Returns a serializer (FmXmlserializer)
     *
     * @return Serializer
     */

     /* Added on 2002-03-14
      * to comply with the new version
      * Hooman Tahamtnai, Ericsson Microwave AB
      * Gothenburg, Sweden.
      */
      public Serializer makeSerializer(String seriializerType)
                        throws java.lang.IllegalArgumentException{
         Serializer serializer = null;
         try{
            serializer = new FmXmlSerializerImpl(seriializerType);
         }
         catch (java.lang.IllegalArgumentException ie){
             throw new java.lang.IllegalArgumentException(ie.getMessage());
         }
         catch (Exception ex){
             throw new java.lang.IllegalArgumentException(ex.getMessage());
         }
         return serializer;
      }

      /**
       * Returns all the serializer types than can be created by this factory
       */

       /* Added on 2002-03-14
        * to comply with the new version
        * Hooman Tahamtnai, Ericsson Microwave AB
        * Gothenburg, Sweden.
        */

      public String[] getSupportedSerializerTypes() {
          String[] serializerTypes = new String[1];
          serializerTypes[0] = new String( FmXmlSerializerImpl.class.getName());
          return serializerTypes;
      }

}
