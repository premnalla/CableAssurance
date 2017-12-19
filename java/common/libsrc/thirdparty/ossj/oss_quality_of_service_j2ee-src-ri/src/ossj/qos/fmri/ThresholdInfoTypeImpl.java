package ossj.qos.fmri;

import javax.oss.fm.monitor.ThresholdInfoType;
import javax.oss.pm.threshold.ThresholdDefinition;
import java.util.Date;
import ossj.qos.util.Util;

/**
 * ThresholdInfoTypeImpl
 *
 * @author  Audrey Ward
 * @version  1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class ThresholdInfoTypeImpl implements ThresholdInfoType {
    
    private String observedObject = null;
    private Object observedValue = null;
    private Date armTime = null;
    private ThresholdDefinition thresholdDefinition = null;

    /** 
     * ThresholdInfoTypeImpl - default constructor
     */
    public ThresholdInfoTypeImpl() {
    }
    
    /** 
     * Gets the distinguished name of the observed object that crossed the threshold.
     *
     * @return String the distinguished name of the observed object.
     * @see #setObservedObject
     */
    public String getObservedObject() {
        return observedObject;
    }
    
    /** 
     * Sets the distinguished name of the observed object that crossed the threshold.
     *
     * @param dn String the distinguished name of the observed object.
     * @see #getObservedObject
     */
    public void setObservedObject(String dn) {
        observedObject = dn;
        return;
    }
    
    /** 
     * Gets the threshold definition.
     *
     * @return javax.oss.pm.threshold.ThresholdDefinition the definition of the threshold.
     * @see #setThresholdDefinition
     */
    public javax.oss.pm.threshold.ThresholdDefinition getThresholdDefinition() {
        return thresholdDefinition;
    }
    
    /** 
     * Sets the threshold definition.
     *
     * @param td the definition of the threshold.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getThresholdDefinition
     */
    public void setThresholdDefinition(javax.oss.pm.threshold.ThresholdDefinition td) 
    throws java.lang.IllegalArgumentException {
        thresholdDefinition = td;
        return;
    }
    
    /** 
     * The value of the attribute which was crossed the threshold.
     *
     * @return Object the value of the attribute. The type can be detirmind from
     * <CODE>ThresholdDefinition</CODE>.
     * @see #setObservedValue
     */
    public Object getObservedValue() {
        return observedValue;
    }
    
    /** 
     * The value of the attribute which was crossed the threshold.
     *
     * @param value the value of the attribute. The type can be detirmind from
     * <CODE>ThresholdDefinition</CODE>.
     * @see #getObservedValue
     */
    public void setObservedValue(Object value) {
        observedValue = value;
        return;
    }
    
    /** 
     * The time since the last threshold crossing.
     *
     * <p>
     * For a gauge threshold, the time at which the threshold was last re-armed,
     * namely the time after the previous threshold crossing at which the hysteresis
     * value of the threshold was exceeded thus again permitting generation of
     * notifications when the threshold is crossed.
     * For a counter threshold, the later of the time at which the threshold offset was
     * last applied, or the time at which the counter was last initialized (for
     * resettable counters).
     *
     * @return java.util.Date the time since last crossing.
     * @see #setArmTime
     */
    public java.util.Date getArmTime() {
        return armTime;
    }
    
    /** 
     * The time since the last threshold crossing.
     *
     * <p>
     * For a gauge threshold, the time at which the threshold was last re-armed,
     * namely the time after the previous threshold crossing at which the hysteresis
     * value of the threshold was exceeded thus again permitting generation of
     * notifications when the threshold is crossed.
     * For a counter threshold, the later of the time at which the threshold offset was
     * last applied, or the time at which the counter was last initialized (for
     * resettable counters).
     *
     * @param time the time since last crossing.
     * @see #getArmTime
     */
    public void setArmTime(java.util.Date time) {
        armTime = time;
        return;
    }
    
    /** 
     * Performs a deep copy of this Instance.
     *
     * @return Object that represents a deep copy of this Instance.
     */
    public Object clone() {
        ThresholdInfoTypeImpl obj = null;
        try {
            obj = (ThresholdInfoTypeImpl)super.clone();
            obj.thresholdDefinition = (ThresholdDefinition) thresholdDefinition.clone();
            obj.observedValue = Util.clone( observedValue );
        }
        catch ( CloneNotSupportedException ex ) {
            //System.out.println( "Problem cloning the ThresholdInfoType");
        }
        return obj;
    }
    
     /** 
     * Makes a ThresholdDefinition instance.
     *
     * @return ThresholdDefinition that contains the definitions associated
     * with the threshold quantity.
     */
    public ThresholdDefinition makeThresholdDefinition() {
        return new ThresholdDefinitionImpl();
    }
    
    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in ThresholdInfoTypeImpl instance are equal to the 
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof ThresholdInfoTypeImpl ) {
            ThresholdInfoTypeImpl objThresInfoType = (ThresholdInfoTypeImpl)anObject;
            isEqual = ( Util.isEqual( observedObject, objThresInfoType.observedObject ) && 
                        Util.isEqual( observedValue, objThresInfoType.observedValue ) &&
                        Util.isEqual( armTime, objThresInfoType.armTime ) &&
                        Util.isEqual( thresholdDefinition, objThresInfoType.thresholdDefinition ) );
        }
        return isEqual;    
    }

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(200);
      
        buffer.append("     observedObject = " + observedObject + "\n");
        buffer.append("      observedValue = " );
        if ( observedValue != null ) {
            buffer.append( observedValue.toString() + "\n" );
        }
        else {
            buffer.append( "null \n");
        }
        buffer.append("            armTime = " + Util.convertUTCTimeString( armTime ) );
        if ( thresholdDefinition != null ) {
            buffer.append( thresholdDefinition.toString() + "\n" );
        }
        else {
            buffer.append( "null \n");
        }
        return buffer.toString();
    }
       
}
