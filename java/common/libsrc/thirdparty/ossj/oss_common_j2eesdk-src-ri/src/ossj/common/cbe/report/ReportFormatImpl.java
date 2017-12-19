/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.report;

import javax.oss.cbe.datatypes.Version;
import ossj.common.cbe.datatypes.VersionImpl;
import javax.oss.cbe.report.ReportFormat;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.report.ReportFormat</CODE> interface.  
 * 
 * @see javax.oss.cbe.report.ReportFormat
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ReportFormatImpl
implements ReportFormat
{ 

    /**
     * Constructs a new ReportFormatImpl with empty attributes.
     * 
     */

    public ReportFormatImpl() {
    }

    public String[] attributeTypeForVersion() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.datatypes.Version";
        return list;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.datatypes.Version makeVersion(String type){
        if(type.equals("javax.oss.cbe.datatypes.Version") || type.equals("ossj.common.cbe.datatypes.VersionImpl")) {
            return new VersionImpl();
        } else {
            return null;
        }
    }

    private java.lang.String _reportformatimpl_owner = null;
    private java.lang.String _reportformatimpl_specification = null;
    private java.lang.String _reportformatimpl_technology = null;
    private int _reportformatimpl_type;
    private javax.oss.cbe.datatypes.Version _reportformatimpl_version = null;


    /**
     * Changes the owner to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new owner for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setOwner(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _reportformatimpl_owner = value;
    }


    /**
     * Returns this ReportFormatImpl's owner
     * 
     * @return the owner
     * 
    */

    public java.lang.String getOwner() {
        return _reportformatimpl_owner;
    }

    /**
     * Changes the specification to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new specification for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSpecification(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _reportformatimpl_specification = value;
    }


    /**
     * Returns this ReportFormatImpl's specification
     * 
     * @return the specification
     * 
    */

    public java.lang.String getSpecification() {
        return _reportformatimpl_specification;
    }

    /**
     * Changes the technology to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new technology for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTechnology(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _reportformatimpl_technology = value;
    }


    /**
     * Returns this ReportFormatImpl's technology
     * 
     * @return the technology
     * 
    */

    public java.lang.String getTechnology() {
        return _reportformatimpl_technology;
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
        _reportformatimpl_type = value;
    }


    /**
     * Returns this ReportFormatImpl's type
     * 
     * @return the type
     * 
    */

    public int getType() {
        return _reportformatimpl_type;
    }

    /**
     * Changes the version to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new version for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setVersion(javax.oss.cbe.datatypes.Version value)
    throws java.lang.IllegalArgumentException    {
        _reportformatimpl_version = value;
    }


    /**
     * Returns this ReportFormatImpl's version
     * 
     * @return the version
     * 
    */

    public javax.oss.cbe.datatypes.Version getVersion() {
        return _reportformatimpl_version;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ReportFormat val = null;
        try { 
            val = (ReportFormat)super.clone();

            if( this.getVersion()!=null) 
                val.setVersion((javax.oss.cbe.datatypes.Version)((javax.oss.cbe.datatypes.Version) this.getVersion()).clone());
            else
                val.setVersion( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("ReportFormatImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two ReportFormat are equal.
     * The result is true if and only if the argument is not null 
     * and is an ReportFormat object that has the arguments.
     * 
     * @param value the Object to compare with this ReportFormat
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ReportFormat)) {
            ReportFormat argVal = (ReportFormat) value;
            if( !Utils.compareAttributes( getOwner(), argVal.getOwner())) { return false; } 
            if( !Utils.compareAttributes( getSpecification(), argVal.getSpecification())) { return false; } 
            if( !Utils.compareAttributes( getTechnology(), argVal.getTechnology())) { return false; } 
            if( this.getType() != argVal.getType()) { return false; } 
            if( !Utils.compareAttributes( getVersion(), argVal.getVersion())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
