/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.party;

import javax.oss.cbe.party.IndividualName;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.party.IndividualName</CODE> interface.  
 * 
 * @see javax.oss.cbe.party.IndividualName
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class IndividualNameImpl
implements IndividualName
{ 

    /**
     * Constructs a new IndividualNameImpl with empty attributes.
     * 
     */

    public IndividualNameImpl() {
    }

    private java.lang.String _individualnameimpl_familyGeneration = null;
    private java.lang.String _individualnameimpl_familyName = null;
    private java.lang.String _individualnameimpl_familyNamePrefix = null;
    private java.lang.String _individualnameimpl_formOfAddress = null;
    private java.lang.String _individualnameimpl_givenName = null;
    private java.lang.String _individualnameimpl_middleName = null;
    private java.lang.String _individualnameimpl_preferredGivenName = null;


    /**
     * Changes the familyGeneration to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new familyGeneration for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setFamilyGeneration(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _individualnameimpl_familyGeneration = value;
    }


    /**
     * Returns this IndividualNameImpl's familyGeneration
     * 
     * @return the familyGeneration
     * 
    */

    public java.lang.String getFamilyGeneration() {
        return _individualnameimpl_familyGeneration;
    }

    /**
     * Changes the familyName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new familyName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setFamilyName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _individualnameimpl_familyName = value;
    }


    /**
     * Returns this IndividualNameImpl's familyName
     * 
     * @return the familyName
     * 
    */

    public java.lang.String getFamilyName() {
        return _individualnameimpl_familyName;
    }

    /**
     * Changes the familyNamePrefix to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new familyNamePrefix for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setFamilyNamePrefix(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _individualnameimpl_familyNamePrefix = value;
    }


    /**
     * Returns this IndividualNameImpl's familyNamePrefix
     * 
     * @return the familyNamePrefix
     * 
    */

    public java.lang.String getFamilyNamePrefix() {
        return _individualnameimpl_familyNamePrefix;
    }

    /**
     * Changes the formOfAddress to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new formOfAddress for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setFormOfAddress(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _individualnameimpl_formOfAddress = value;
    }


    /**
     * Returns this IndividualNameImpl's formOfAddress
     * 
     * @return the formOfAddress
     * 
    */

    public java.lang.String getFormOfAddress() {
        return _individualnameimpl_formOfAddress;
    }

    /**
     * Changes the givenName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new givenName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setGivenName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _individualnameimpl_givenName = value;
    }


    /**
     * Returns this IndividualNameImpl's givenName
     * 
     * @return the givenName
     * 
    */

    public java.lang.String getGivenName() {
        return _individualnameimpl_givenName;
    }

    /**
     * Changes the middleName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new middleName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setMiddleName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _individualnameimpl_middleName = value;
    }


    /**
     * Returns this IndividualNameImpl's middleName
     * 
     * @return the middleName
     * 
    */

    public java.lang.String getMiddleName() {
        return _individualnameimpl_middleName;
    }

    /**
     * Changes the preferredGivenName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new preferredGivenName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPreferredGivenName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _individualnameimpl_preferredGivenName = value;
    }


    /**
     * Returns this IndividualNameImpl's preferredGivenName
     * 
     * @return the preferredGivenName
     * 
    */

    public java.lang.String getPreferredGivenName() {
        return _individualnameimpl_preferredGivenName;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        IndividualName val = null;
        try { 
            val = (IndividualName)super.clone();

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("IndividualNameImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two IndividualName are equal.
     * The result is true if and only if the argument is not null 
     * and is an IndividualName object that has the arguments.
     * 
     * @param value the Object to compare with this IndividualName
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof IndividualName)) {
            IndividualName argVal = (IndividualName) value;
            if( !Utils.compareAttributes( getFamilyGeneration(), argVal.getFamilyGeneration())) { return false; } 
            if( !Utils.compareAttributes( getFamilyName(), argVal.getFamilyName())) { return false; } 
            if( !Utils.compareAttributes( getFamilyNamePrefix(), argVal.getFamilyNamePrefix())) { return false; } 
            if( !Utils.compareAttributes( getFormOfAddress(), argVal.getFormOfAddress())) { return false; } 
            if( !Utils.compareAttributes( getGivenName(), argVal.getGivenName())) { return false; } 
            if( !Utils.compareAttributes( getMiddleName(), argVal.getMiddleName())) { return false; } 
            if( !Utils.compareAttributes( getPreferredGivenName(), argVal.getPreferredGivenName())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
