package ossj.qos.fmri;

import javax.oss.fm.monitor.AlarmCountsValue;
import javax.oss.Serializer;
import javax.oss.XmlSerializer;
import ossj.qos.xmlri.fmxmlri.FmXmlSerializerImpl;
import ossj.qos.util.Util;

/**
 * AlarmCountsValueImpl
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC.
 * All Rights Reserved
 */
public class AlarmCountsValueImpl implements AlarmCountsValue {

    /*
     *  Alarm Counts
     */
    int critical_count = 0;
    int major_count = 0;
    int minor_count = 0;
    int warning_count = 0;
    int indeterminate_count = 0;
    int cleared_count = 0;


    /**
     * AlarmCountsValueImpl - default constructor
     */
    public AlarmCountsValueImpl() {
    }

    /**
     * AlarmCountsValueImpl - constructor
     */
    public AlarmCountsValueImpl( int num_indeterminate,
    int num_critical,
    int num_major,
    int num_minor,
    int num_warning,
    int num_cleared ) {

        critical_count = num_critical;
        major_count = num_major;
        minor_count = num_minor;
        warning_count = num_warning;
        indeterminate_count = num_indeterminate;
        cleared_count = num_cleared;
        return;
    }

    /**
     * Returns number of critical alarms.
     *
     * @return int - number of critical alarms
     */
    public int getCriticalCount()
    {
        return critical_count;
    }

    /**
     * Returns number of critical alarms.
     *
     * @return int - number of critical alarms
     */
    public int getMajorCount()
    {
        return major_count;
    }

    /**
     * Returns number of minor alarms.
     *
     * @return int - number of minor alarms
     */
    public int getMinorCount()
    {
        return minor_count;
    }

    /**
     * Returns number of warning alarms.
     *
     * @return int - number of warning alarms
     */
    public int getWarningCount()
    {
        return warning_count;
    }

    /**
     * Returns number of indeterminate alarms.
     *
     * @return int - number of indeterminate alarms
     */
    public int getIndeterminateCount()
    {
        return indeterminate_count;
    }

    /**
     * Returns number of cleared alarms.
     *
     * @return int - number of cleared alarms
     */
    public int getClearedCount()
    {
        return cleared_count;
    }

    /**
     * Performs a deep copy of this Instance.
     *
     * @return Object that represents a deep copy of this Instance.
     */
     public Object clone() {
        AlarmCountsValueImpl obj = null;
        try {
            obj = (AlarmCountsValueImpl)super.clone();
        }
        catch ( CloneNotSupportedException ex ) {
            // should never happen
            //System.out.println( "Problem cloning AlarmCountsValue." );
        }
        return obj;
    }

    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in AlarmCountsValueImpl instance is equal to
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof AlarmCountsValueImpl ) {
            AlarmCountsValueImpl objAlarmCountsVal = (AlarmCountsValueImpl)anObject;
            isEqual = ( critical_count == objAlarmCountsVal.critical_count  &&
                        major_count == objAlarmCountsVal.major_count  &&
                        minor_count == objAlarmCountsVal.minor_count  &&
                        warning_count == objAlarmCountsVal.warning_count  &&
                        indeterminate_count == objAlarmCountsVal.indeterminate_count  &&
                        cleared_count == objAlarmCountsVal.cleared_count );
        }
        return isEqual;
    }

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(200);
        buffer.append(Util.rightJustify( 30, "critical count = ") + critical_count + "\n");
        buffer.append(Util.rightJustify( 30, "major count = ") + major_count + "\n");
        buffer.append(Util.rightJustify( 30, "minor count = ") + minor_count + "\n");
        buffer.append(Util.rightJustify( 30, "warning count = ") + warning_count + "\n");
        buffer.append(Util.rightJustify( 30, "indeterminant count = ") + indeterminate_count + "\n");
        buffer.append(Util.rightJustify( 30, "cleared count = ") + cleared_count + "\n");

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
