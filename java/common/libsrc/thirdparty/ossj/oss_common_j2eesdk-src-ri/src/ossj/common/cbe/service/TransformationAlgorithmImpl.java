/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.service;

import javax.oss.cbe.service.ServiceLevelSpecParam;
import javax.oss.cbe.service.TransformationAlgorithm;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.service.TransformationAlgorithm</CODE> interface.  
 * 
 * @see javax.oss.cbe.service.TransformationAlgorithm
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TransformationAlgorithmImpl
implements TransformationAlgorithm
{ 

    /**
     * Constructs a new TransformationAlgorithmImpl with empty attributes.
     * 
     */

    public TransformationAlgorithmImpl() {
    }

    public String[] attributeTypeForServiceLevelSpecParams() {
        String[] list = new String[1];
        list[0] = "[Ljavax.oss.cbe.service.ServiceLevelSpecParam;";
        return list;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.service.ServiceLevelSpecParam[] makeServiceLevelSpecParams(int nb, String type){
        if(type.equals("javax.oss.cbe.service.ServiceLevelSpecParam") || type.equals("ossj.common.cbe.service.ServiceLevelSpecParamImpl")) {
            return new ServiceLevelSpecParamImpl[nb];
        } else {
            return null;
        }
    }

    private java.lang.String _transformationalgorithmimpl_description = null;
    private javax.oss.cbe.service.ServiceLevelSpecParam[] _transformationalgorithmimpl_serviceLevelSpecParams = null;


    /**
     * Changes the description to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new description for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setDescription(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _transformationalgorithmimpl_description = value;
    }


    /**
     * Returns this TransformationAlgorithmImpl's description
     * 
     * @return the description
     * 
    */

    public java.lang.String getDescription() {
        return _transformationalgorithmimpl_description;
    }

    /**
     * Changes the serviceLevelSpecParams to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceLevelSpecParams for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceLevelSpecParams(javax.oss.cbe.service.ServiceLevelSpecParam[] value)
    throws java.lang.IllegalArgumentException    {
        _transformationalgorithmimpl_serviceLevelSpecParams = value;
    }


    /**
     * Returns this TransformationAlgorithmImpl's serviceLevelSpecParams
     * 
     * @return the serviceLevelSpecParams
     * 
    */

    public javax.oss.cbe.service.ServiceLevelSpecParam[] getServiceLevelSpecParams() {
        return _transformationalgorithmimpl_serviceLevelSpecParams;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        TransformationAlgorithm val = null;
        try { 
            val = (TransformationAlgorithm)super.clone();

            if( this.getServiceLevelSpecParams()!=null) 
                val.setServiceLevelSpecParams((javax.oss.cbe.service.ServiceLevelSpecParam[])((javax.oss.cbe.service.ServiceLevelSpecParam[]) this.getServiceLevelSpecParams()).clone());
            else
                val.setServiceLevelSpecParams( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("TransformationAlgorithmImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two TransformationAlgorithm are equal.
     * The result is true if and only if the argument is not null 
     * and is an TransformationAlgorithm object that has the arguments.
     * 
     * @param value the Object to compare with this TransformationAlgorithm
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof TransformationAlgorithm)) {
            TransformationAlgorithm argVal = (TransformationAlgorithm) value;
            if( !Utils.compareAttributes( getDescription(), argVal.getDescription())) { return false; } 
            if( !Utils.compareAttributes( getServiceLevelSpecParams(), argVal.getServiceLevelSpecParams())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
