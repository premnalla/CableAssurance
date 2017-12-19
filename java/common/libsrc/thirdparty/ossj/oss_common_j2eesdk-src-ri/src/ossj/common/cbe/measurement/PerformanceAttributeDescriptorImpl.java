/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceAttributeDescriptor;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceAttributeDescriptor</CODE> interface.  
 * 
 * @see javax.oss.cbe.measurement.PerformanceAttributeDescriptor
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceAttributeDescriptorImpl
implements PerformanceAttributeDescriptor
{ 

    /**
     * Constructs a new PerformanceAttributeDescriptorImpl with empty attributes.
     * 
     */

    public PerformanceAttributeDescriptorImpl() {
    }

    private boolean _performanceattributedescriptorimpl_array;
    private java.lang.String _performanceattributedescriptorimpl_collectionMethod = null;
    private java.lang.String _performanceattributedescriptorimpl_name = null;
    private int _performanceattributedescriptorimpl_type;


    /**
     * Changes the array to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new array for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setArray(boolean value)
    throws java.lang.IllegalArgumentException    {
        _performanceattributedescriptorimpl_array = value;
    }


    /**
     * Returns this PerformanceAttributeDescriptorImpl's array
     * 
     * @return the array
     * 
    */

    public boolean isArray() {
        return _performanceattributedescriptorimpl_array;
    }

    /**
     * Changes the collectionMethod to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new collectionMethod for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCollectionMethod(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        //fix to Bug ID 6253087
        if ((value == null)
        || ((value.compareTo(PerformanceAttributeDescriptor.CUMULATIVE_COUNTER) != 0)
        && (value.compareTo(PerformanceAttributeDescriptor.DISCRETE_EVENT_REGISTRATION)!= 0)
        && (value.compareTo(PerformanceAttributeDescriptor.GAUGE) != 0)
        && (value.compareTo(PerformanceAttributeDescriptor.STATUS_INSPECTION) != 0))) {
            throw new java.lang.IllegalArgumentException(value 
            + " is not a valid attribute (null or different from "
            + "CUMULATIVE_COUNTER, DISCRETE_EVENT_REGISTRATION, GAUGE or STATUS_INSPECTION");
        }
        _performanceattributedescriptorimpl_collectionMethod = value;
    }


    /**
     * Returns this PerformanceAttributeDescriptorImpl's collectionMethod
     * 
     * @return the collectionMethod
     * 
    */

    public java.lang.String getCollectionMethod() {
        return _performanceattributedescriptorimpl_collectionMethod;
    }

    /**
     * Changes the name to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new name for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _performanceattributedescriptorimpl_name = value;
    }


    /**
     * Returns this PerformanceAttributeDescriptorImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName() {
        return _performanceattributedescriptorimpl_name;
    }

    /**
     * Changes the type to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new type for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setType(int value)
    throws java.lang.IllegalArgumentException    {
        if ((value != PerformanceAttributeDescriptor.INTEGER)
        && (value != PerformanceAttributeDescriptor.REAL)
        && (value != PerformanceAttributeDescriptor.STRING)) {
            throw new java.lang.IllegalArgumentException();
        }
        _performanceattributedescriptorimpl_type = value;
    }


    /**
     * Returns this PerformanceAttributeDescriptorImpl's type
     * 
     * @return the type
     * 
    */

    public int getType() {
        return _performanceattributedescriptorimpl_type;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PerformanceAttributeDescriptor val = null;
        try { 
            val = (PerformanceAttributeDescriptor)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("PerformanceAttributeDescriptorImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two PerformanceAttributeDescriptor are equal.
     * The result is true if and only if the argument is not null 
     * and is an PerformanceAttributeDescriptor object that has the arguments.
     * 
     * @param value the Object to compare with this PerformanceAttributeDescriptor
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PerformanceAttributeDescriptor)) {
            PerformanceAttributeDescriptor argVal = (PerformanceAttributeDescriptor) value;
            if( this.isArray() != argVal.isArray()) { return false; } 
            if( !Utils.compareAttributes( getCollectionMethod(), argVal.getCollectionMethod())) { return false; } 
            if( !Utils.compareAttributes( getName(), argVal.getName())) { return false; } 
            if( this.getType() != argVal.getType()) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
