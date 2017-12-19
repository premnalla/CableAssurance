/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import javax.oss.pm.threshold.ThresholdMonitorKeyResult;
import javax.oss.pm.threshold.ThresholdMonitorKey;
import javax.oss.ManagedEntityKey;

import ossj.qos.ManagedEntityKeyResultImpl;
import ossj.qos.util.Log;


import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import ossj.qos.xmlri.tmxmlri.TmXmlSerializerImpl;


/**
 * Implements the ThresholdMonitorKeyResult.
 * @author Henrik Lindstrom, Ericsson
 */
public class ThresholdMonitorKeyResultImpl extends ManagedEntityKeyResultImpl implements ThresholdMonitorKeyResult
{
   /**
    * Constructs a new ThresholdMonitorKeyResult.
    */
   public ThresholdMonitorKeyResultImpl()
   {
        Log.write(this,Log.LOG_ALL,"ThresholdMonitorKeyResultImpl()","created");
   }

   public ThresholdMonitorKey getThresholdMonitorKey()
   {
        Log.write(this,Log.LOG_ALL,"getThresholdMonitorKey()","managedEntityKey="+managedEntityKey);
        return (ThresholdMonitorKey)managedEntityKey;
   }

/*   public void setThresholdMonitorKey( ThresholdMonitorKey key )
   {
        Log.write(this,Log.LOG_ALL,"setThresholdMonitorKey()","called");
        this.setManagedEntityKey( key );
   }*/

    public String toString() {
        return "ThresholdMonitorKeyResultImpl=" + super.toString();
    }

  /*
  Added the method Below to comply with the new implementation.
  March 15, 2002.
  Hooman Tahamtani, Ericsson Microwave AB
  Gothenburg, Sweden
  */
      public String[] getSupportedSerializerTypes() {
          String[] serializerTypes = new String[1];
          serializerTypes[0] = new String( TmXmlSerializerImpl.class.getName());
          return serializerTypes;
      }

     /*
        Added the method below to comply with the new implementation.
        March 15, 2002
        Hooman Tahamtani, Ericsson Microwave AB
        Gothenburg, Sweden
     */

    public Serializer makeSerializer( String serializerType )
    throws java.lang.IllegalArgumentException {
        Serializer serializer = null;
     try{
        serializer = new TmXmlSerializerImpl(serializerType);
     }catch(java.lang.IllegalArgumentException e){
        throw new java.lang.IllegalArgumentException(e.getMessage());
     }
        return serializer;
    }

}
