/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import java.lang.reflect.Method;
import javax.oss.cbe.measurement.PerformanceAttributeDescriptor;
import javax.oss.cbe.measurement.ThresholdDefinition;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.ThresholdDefinition</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.ThresholdDefinition
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ThresholdDefinitionImpl
implements ThresholdDefinition
{ 

    /**
     * Constructs a new ThresholdDefinitionImpl with empty attributes.
     * 
     */

    public ThresholdDefinitionImpl() {
    }

    public String[] attributeTypeForAttributeDescriptor() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.measurement.PerformanceAttributeDescriptor";
        return list;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.measurement.PerformanceAttributeDescriptor makePerformanceAttributeDescriptor(String type){
        if(type.equals("javax.oss.cbe.measurement.PerformanceAttributeDescriptor") || type.equals("ossj.common.cbe.measurement.PerformanceAttributeDescriptorImpl")) {
            return new PerformanceAttributeDescriptorImpl();
        } else {
            return null;
        }
    }

    private javax.oss.cbe.measurement.PerformanceAttributeDescriptor _thresholddefinitionimpl_attributeDescriptor = null;
    private int _thresholddefinitionimpl_direction;
    private java.lang.Object _thresholddefinitionimpl_offset = null;
    private short _thresholddefinitionimpl_perceivedSeverity;
    private java.lang.Object _thresholddefinitionimpl_value = null;


    /**
     * Changes the attributeDescriptor to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new attributeDescriptor for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAttributeDescriptor(javax.oss.cbe.measurement.PerformanceAttributeDescriptor value)
    throws java.lang.IllegalArgumentException    {
        _thresholddefinitionimpl_attributeDescriptor = value;
    }


    /**
     * Returns this ThresholdDefinitionImpl's attributeDescriptor
     * 
     * @return the attributeDescriptor
     * 
    */

    public javax.oss.cbe.measurement.PerformanceAttributeDescriptor getAttributeDescriptor() {
        return _thresholddefinitionimpl_attributeDescriptor;
    }

    /**
     * Changes the direction to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new direction for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setDirection(int value)
    throws java.lang.IllegalArgumentException    {
        if ((value != ThresholdDefinition.FALLING_DIRECTION)
        && (value != ThresholdDefinition.RISING_DIRECTION)) {
            throw new java.lang.IllegalArgumentException("The direction [" + value + "] is illegal!");
        }
        _thresholddefinitionimpl_direction = value;
    }


    /**
     * Returns this ThresholdDefinitionImpl's direction
     * 
     * @return the direction
     * 
    */

    public int getDirection() {
        return _thresholddefinitionimpl_direction;
    }

    /**
     * Changes the offset to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new offset for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setOffset(java.lang.Object value)
    throws java.lang.IllegalArgumentException    {
        _thresholddefinitionimpl_offset = value;
    }


    /**
     * Returns this ThresholdDefinitionImpl's offset
     * 
     * @return the offset
     * 
    */

    public java.lang.Object getOffset() {
        return _thresholddefinitionimpl_offset;
    }

    /**
     * Changes the perceivedSeverity to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new perceivedSeverity for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPerceivedSeverity(short value)
    throws java.lang.IllegalArgumentException    {
        _thresholddefinitionimpl_perceivedSeverity = value;
    }


    /**
     * Returns this ThresholdDefinitionImpl's perceivedSeverity
     * 
     * @return the perceivedSeverity
     * 
    */

    public short getPerceivedSeverity() {
        return _thresholddefinitionimpl_perceivedSeverity;
    }

    /**
     * Changes the value to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new value for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setValue(java.lang.Object value)
    throws java.lang.IllegalArgumentException    {
        _thresholddefinitionimpl_value = value;
    }


    /**
     * Returns this ThresholdDefinitionImpl's value
     * 
     * @return the value
     * 
    */

    public java.lang.Object getValue() {
        return _thresholddefinitionimpl_value;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ThresholdDefinition val = null;
        try { 
            val = (ThresholdDefinition)super.clone();

            if( this.getAttributeDescriptor()!=null) 
                val.setAttributeDescriptor((javax.oss.cbe.measurement.PerformanceAttributeDescriptor)((javax.oss.cbe.measurement.PerformanceAttributeDescriptor) this.getAttributeDescriptor()).clone());
            else
                val.setAttributeDescriptor( null );

            if( this.getOffset()!=null) 
                val.setOffset((java.lang.Object)clone(getOffset()));
            else
                val.setOffset( null );

            if( this.getValue()!=null) 
                val.setValue((java.lang.Object)clone(getValue()));
            else
                val.setValue( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("ThresholdDefinitionImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }
    
    private Object clone(Object obj) {
        Object clone = obj;
        if ((obj != null) && obj instanceof Cloneable) {
            try {
                Method method = obj.getClass().getMethod("clone",(Class[]) null);
                clone = method.invoke(obj, (Object[])null);
            } catch (Exception e) {
                return null;
            }
        }
        return clone;
    }

    /**
     * Checks whether two ThresholdDefinition are equal.
     * The result is true if and only if the argument is not null 
     * and is an ThresholdDefinition object that has the arguments.
     * 
     * @param value the Object to compare with this ThresholdDefinition
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ThresholdDefinition)) {
            ThresholdDefinition argVal = (ThresholdDefinition) value;
            if( !Utils.compareAttributes( getAttributeDescriptor(), argVal.getAttributeDescriptor())) { return false; } 
            if( this.getDirection() != argVal.getDirection()) { return false; } 
            if( !Utils.compareAttributes( getOffset(), argVal.getOffset())) { return false; } 
            if( this.getPerceivedSeverity() != argVal.getPerceivedSeverity()) { return false; } 
            if( !Utils.compareAttributes( getValue(), argVal.getValue())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
