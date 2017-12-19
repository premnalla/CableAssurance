/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import java.util.Date;
import javax.oss.cbe.measurement.ThresholdDefinition;
import ossj.common.cbe.measurement.ThresholdDefinitionImpl;
import javax.oss.cbe.alarm.ThresholdInfoType;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.ThresholdInfoType</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.ThresholdInfoType
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ThresholdInfoTypeImpl
extends ossj.common.cbe.alarm.AlarmSpecificInfoImpl
implements ThresholdInfoType
{ 

    /**
     * Constructs a new ThresholdInfoTypeImpl with empty attributes.
     * 
     */

    public ThresholdInfoTypeImpl() {
        super();
    }

    public String[] attributeTypeForThresholdDefinition() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.measurement.ThresholdDefinition";
        return list;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.measurement.ThresholdDefinition makeThresholdDefinition(String type){
        if(type.equals("javax.oss.cbe.measurement.ThresholdDefinition") || type.equals("ossj.common.cbe.measurement.ThresholdDefinitionImpl")) {
            return new ThresholdDefinitionImpl();
        } else {
            return null;
        }
    }

    private java.util.Date _thresholdinfotypeimpl_armTime = null;
    private javax.oss.cbe.measurement.ThresholdDefinition _thresholdinfotypeimpl_thresholdDefinition = null;
    private java.lang.String _thresholdinfotypeimpl_trendIndication = null;


    /**
     * Changes the armTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new armTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setArmTime(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        _thresholdinfotypeimpl_armTime = value;
    }


    /**
     * Returns this ThresholdInfoTypeImpl's armTime
     * 
     * @return the armTime
     * 
    */

    public java.util.Date getArmTime() {
        return _thresholdinfotypeimpl_armTime;
    }

    /**
     * Changes the thresholdDefinition to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new thresholdDefinition for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setThresholdDefinition(javax.oss.cbe.measurement.ThresholdDefinition value)
    throws java.lang.IllegalArgumentException    {
        _thresholdinfotypeimpl_thresholdDefinition = value;
    }


    /**
     * Returns this ThresholdInfoTypeImpl's thresholdDefinition
     * 
     * @return the thresholdDefinition
     * 
    */

    public javax.oss.cbe.measurement.ThresholdDefinition getThresholdDefinition() {
        return _thresholdinfotypeimpl_thresholdDefinition;
    }

    /**
     * Changes the trendIndication to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new trendIndication for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTrendIndication(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _thresholdinfotypeimpl_trendIndication = value;
    }


    /**
     * Returns this ThresholdInfoTypeImpl's trendIndication
     * 
     * @return the trendIndication
     * 
    */

    public java.lang.String getTrendIndication() {
        return _thresholdinfotypeimpl_trendIndication;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ThresholdInfoType val = null;
            val = (ThresholdInfoType)super.clone();

            if( this.getArmTime()!=null) 
                val.setArmTime((java.util.Date)((java.util.Date) this.getArmTime()).clone());
            else
                val.setArmTime( null );

            if( this.getThresholdDefinition()!=null) 
                val.setThresholdDefinition((javax.oss.cbe.measurement.ThresholdDefinition)((javax.oss.cbe.measurement.ThresholdDefinition) this.getThresholdDefinition()).clone());
            else
                val.setThresholdDefinition( null );

            return val;
    }

    /**
     * Checks whether two ThresholdInfoType are equal.
     * The result is true if and only if the argument is not null 
     * and is an ThresholdInfoType object that has the arguments.
     * 
     * @param value the Object to compare with this ThresholdInfoType
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ThresholdInfoType)) {
            ThresholdInfoType argVal = (ThresholdInfoType) value;
            if( !Utils.compareAttributes( getArmTime(), argVal.getArmTime())) { return false; } 
            if( !Utils.compareAttributes( getThresholdDefinition(), argVal.getThresholdDefinition())) { return false; } 
            if( !Utils.compareAttributes( getTrendIndication(), argVal.getTrendIndication())) { return false; } 

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
