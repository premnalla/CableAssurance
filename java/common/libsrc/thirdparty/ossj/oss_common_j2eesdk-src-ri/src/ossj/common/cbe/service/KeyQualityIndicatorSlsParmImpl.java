/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.TransformationAlgorithm;
import javax.oss.cbe.measurement.PerformanceAttributeDescriptor;
import ossj.common.cbe.measurement.PerformanceAttributeDescriptorImpl;
import ossj.common.cbe.EntityKeyImpl;
import javax.oss.ManagedEntityKey;
import ossj.common.ManagedEntityKeyImpl;
import javax.oss.cbe.service.KeyQualityIndicatorSlsParm;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.KeyQualityIndicatorSlsParm</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.KeyQualityIndicatorSlsParm
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class KeyQualityIndicatorSlsParmImpl
extends ossj.common.cbe.service.ServiceLevelSpecParamImpl
implements KeyQualityIndicatorSlsParm
{ 

    /**
     * Constructs a new KeyQualityIndicatorSlsParmImpl with empty attributes.
     * 
     */

    public KeyQualityIndicatorSlsParmImpl() {
        super();
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.ManagedEntityKey makeManagedEntityKey(String type){
            //TODO replace by the supported and valid KeyImpl
            return new EntityKeyImpl();
    }

    public javax.oss.cbe.measurement.PerformanceAttributeDescriptor makePerformanceAttributeDescriptor(String type){
        if(type.equals("javax.oss.cbe.measurement.PerformanceAttributeDescriptor") || type.equals("ossj.common.cbe.measurement.PerformanceAttributeDescriptorImpl")) {
            return new PerformanceAttributeDescriptorImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForPerformanceAttributeDescriptor() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.measurement.PerformanceAttributeDescriptor";
        return list;
    }

    public String[] attributeTypeForRelatedEntityKey() {
        String[] list = new String[1];
        list[0] = "javax.oss.ManagedEntityKey";
        return list;
    }

    public String[] attributeTypeForTransformationAlgorithm() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.service.TransformationAlgorithm";
        return list;
    }

    public javax.oss.cbe.service.TransformationAlgorithm makeTransformationAlgorithm(String type){
        if(type.equals("javax.oss.cbe.service.TransformationAlgorithm") || type.equals("ossj.common.cbe.service.TransformationAlgorithmImpl")) {
            return new TransformationAlgorithmImpl();
        } else {
            return null;
        }
    }

    private java.lang.String _keyqualityindicatorslsparmimpl_name = null;
    private javax.oss.cbe.measurement.PerformanceAttributeDescriptor _keyqualityindicatorslsparmimpl_performanceAttributeDescriptor = null;
    private javax.oss.ManagedEntityKey _keyqualityindicatorslsparmimpl_relatedEntityKey = null;
    private java.lang.String _keyqualityindicatorslsparmimpl_relatedEntityValueType = null;
    private javax.oss.cbe.service.TransformationAlgorithm _keyqualityindicatorslsparmimpl_transformationAlgorithm = null;


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
        _keyqualityindicatorslsparmimpl_name = value;
    }


    /**
     * Returns this KeyQualityIndicatorSlsParmImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName() {
        return _keyqualityindicatorslsparmimpl_name;
    }

    /**
     * Changes the performanceAttributeDescriptor to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new performanceAttributeDescriptor for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPerformanceAttributeDescriptor(javax.oss.cbe.measurement.PerformanceAttributeDescriptor value)
    throws java.lang.IllegalArgumentException    {
        _keyqualityindicatorslsparmimpl_performanceAttributeDescriptor = value;
    }


    /**
     * Returns this KeyQualityIndicatorSlsParmImpl's performanceAttributeDescriptor
     * 
     * @return the performanceAttributeDescriptor
     * 
    */

    public javax.oss.cbe.measurement.PerformanceAttributeDescriptor getPerformanceAttributeDescriptor() {
        return _keyqualityindicatorslsparmimpl_performanceAttributeDescriptor;
    }

    /**
     * Changes the relatedEntityKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new relatedEntityKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRelatedEntityKey(javax.oss.ManagedEntityKey value)
    throws java.lang.IllegalArgumentException    {
        _keyqualityindicatorslsparmimpl_relatedEntityKey = value;
    }


    /**
     * Returns this KeyQualityIndicatorSlsParmImpl's relatedEntityKey
     * 
     * @return the relatedEntityKey
     * 
    */

    public javax.oss.ManagedEntityKey getRelatedEntityKey() {
        return _keyqualityindicatorslsparmimpl_relatedEntityKey;
    }

    /**
     * Changes the relatedEntityValueType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new relatedEntityValueType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRelatedEntityValueType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _keyqualityindicatorslsparmimpl_relatedEntityValueType = value;
    }


    /**
     * Returns this KeyQualityIndicatorSlsParmImpl's relatedEntityValueType
     * 
     * @return the relatedEntityValueType
     * 
    */

    public java.lang.String getRelatedEntityValueType() {
        return _keyqualityindicatorslsparmimpl_relatedEntityValueType;
    }

    /**
     * Changes the transformationAlgorithm to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new transformationAlgorithm for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTransformationAlgorithm(javax.oss.cbe.service.TransformationAlgorithm value)
    throws java.lang.IllegalArgumentException    {
        _keyqualityindicatorslsparmimpl_transformationAlgorithm = value;
    }


    /**
     * Returns this KeyQualityIndicatorSlsParmImpl's transformationAlgorithm
     * 
     * @return the transformationAlgorithm
     * 
    */

    public javax.oss.cbe.service.TransformationAlgorithm getTransformationAlgorithm() {
        return _keyqualityindicatorslsparmimpl_transformationAlgorithm;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        KeyQualityIndicatorSlsParm val = null;
            val = (KeyQualityIndicatorSlsParm)super.clone();

            if( this.getPerformanceAttributeDescriptor()!=null) 
                val.setPerformanceAttributeDescriptor((javax.oss.cbe.measurement.PerformanceAttributeDescriptor)((javax.oss.cbe.measurement.PerformanceAttributeDescriptor) this.getPerformanceAttributeDescriptor()).clone());
            else
                val.setPerformanceAttributeDescriptor( null );

            if( this.getRelatedEntityKey()!=null) 
                val.setRelatedEntityKey((javax.oss.ManagedEntityKey)((javax.oss.ManagedEntityKey) this.getRelatedEntityKey()).clone());
            else
                val.setRelatedEntityKey( null );

            if( this.getTransformationAlgorithm()!=null) 
                val.setTransformationAlgorithm((javax.oss.cbe.service.TransformationAlgorithm)((javax.oss.cbe.service.TransformationAlgorithm) this.getTransformationAlgorithm()).clone());
            else
                val.setTransformationAlgorithm( null );

            return val;
    }

    /**
     * Checks whether two KeyQualityIndicatorSlsParm are equal.
     * The result is true if and only if the argument is not null 
     * and is an KeyQualityIndicatorSlsParm object that has the arguments.
     * 
     * @param value the Object to compare with this KeyQualityIndicatorSlsParm
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof KeyQualityIndicatorSlsParm)) {
            KeyQualityIndicatorSlsParm argVal = (KeyQualityIndicatorSlsParm) value;
            if( !Utils.compareAttributes( getName(), argVal.getName())) { return false; } 
            if( !Utils.compareAttributes( getPerformanceAttributeDescriptor(), argVal.getPerformanceAttributeDescriptor())) { return false; } 
            if( !Utils.compareAttributes( getRelatedEntityKey(), argVal.getRelatedEntityKey())) { return false; } 
            if( !Utils.compareAttributes( getRelatedEntityValueType(), argVal.getRelatedEntityValueType())) { return false; } 
            if( !Utils.compareAttributes( getTransformationAlgorithm(), argVal.getTransformationAlgorithm())) { return false; } 

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
