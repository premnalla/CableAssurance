/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.cbe.datatypes;

import javax.oss.cbe.datatypes.Version;
import ossj.common.Utils;

/**
 * An implementation class for the <CODE>javax.oss.cbe.datatypes.Version</CODE> interface.
 *
 * @see javax.oss.cbe.datatypes.Version
 *
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3
 * @since January 2006
 */


public class VersionImpl
        implements Version,java.lang.Comparable {
    private int major = 0;
    private int minor = 0;
    
    /**
     * Constructs a new VersionImpl with empty attributes.
     *
     */
    
    public VersionImpl() {
        super();
    }
    
    public String[] attributeTypeForValidFor() {
        String[] list = new String[1];
        list [0] = "javax.oss.cbe.datatypes.TimePeriod";
        return list;
    }
    
    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.datatypes.TimePeriod makeTimePeriod(String type){
        if(type.equals("javax.oss.cbe.datatypes.TimePeriod") || type.equals("ossj.common.cbe.datatypes.TimePeriodImpl")) {
            return new TimePeriodImpl();
        } else {
            return null;
        }
    }
    
    private java.lang.String _versionimpl_description = null;
    private javax.oss.cbe.datatypes.TimePeriod _versionimpl_validFor = null;
    
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
    throws java.lang.IllegalArgumentException	{
        _versionimpl_description = value;
    }
    
    
    /**
     * Returns this VersionImpl's description
     *
     * @return the description
     *
     */
    
    public java.lang.String getDescription() {
        return _versionimpl_description;
    }
    
    /**
     * Changes the validFor to be equal to the given argument.
     *
     * This method support a null argument.
     *
     * @param value the new validFor for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
     */
    
    public void setValidFor(javax.oss.cbe.datatypes.TimePeriod value)
    throws java.lang.IllegalArgumentException	{
        _versionimpl_validFor = value;
    }
    
    
    /**
     * Returns this VersionImpl's validFor
     *
     * @return the validFor
     *
     */
    
    public javax.oss.cbe.datatypes.TimePeriod getValidFor() {
        return _versionimpl_validFor;
    }
    
    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
     */
    public Object clone() {
        Version val = null;
        try {
            val = (Version)super.clone();
            
            if( this.getValidFor()!=null)
                val.setValidFor((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getValidFor()).clone());
            else
                val.setValidFor( null );
            
            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("VersionImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }
    /**
     * Checks whether two ProductValue are equal.
     * The result is true if and only if the argument is not null
     * and is an ProductValue object that has the arguments.
     *
     * @param value the Object to compare with this ProductValue
     * @return if the objects are equal; false otherwise.
     */
    
    public boolean equals(Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof Version)) {
            Version argVal = (Version) value;
            if( compareTo(argVal) != 0 ) { return false; }
            if( !Utils.compareAttributes( getDescription(), argVal.getDescription())) { return false; }
            if( !Utils.compareAttributes( getValidFor(), argVal.getValidFor())) { return false; }
            return true;
        } else {
            return super.equals(value);
        }
    }
    
    /**
     * 
     * Compares this object with the specified object for order. Returns a negative
     * integer, zero, or a positive integer as this object is less than, equal to,
     * or greater than the specified object.
     *
     * Description and validForargument are not taken into account, but only 
     * the major and minor version number
     *
     * @see java.lang.Comparable
     * @param o - the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @exception ClassCastException - if the specified object's type prevents it
     * from being compared to this Object.
     */
    
    
    public int compareTo(Object o){
        VersionImpl tested = (VersionImpl)o;
        if (major == tested.getMajor() && minor == tested.getMinor()){
            return 0;
        }
        if ((major == tested.getMajor() && minor < tested.getMinor())
        || major < tested.getMajor() ){
            return 1;
        } else {
            return -1;
        }
    }
    
    public int getMajor() {
        return major;
    }
    
    public void setMajor(int major) {
        this.major = major;
    }
    
    public int getMinor() {
        return minor;
    }
    
    public void setMinor(int minor) {
        this.minor = minor;
    }
    
}
