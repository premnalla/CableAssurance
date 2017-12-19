/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.Cardinality;
import javax.oss.cbe.AssociationRule;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.AssociationRule</CODE> interface.  
 * 
 * @see javax.oss.cbe.AssociationRule
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AssociationRuleImpl
implements AssociationRule
{ 

    /**
     * Constructs a new AssociationRuleImpl with empty attributes.
     * 
     */

    public AssociationRuleImpl() {
    }

    public String[] attributeTypeForAEndCardinality() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.Cardinality";
        return list;
    }

    public String[] attributeTypeForZEndCardinality() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.Cardinality";
        return list;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.Cardinality makeCardinality(String type){
        if(type.equals("javax.oss.cbe.Cardinality") || type.equals("ossj.common.cbe.CardinalityImpl")) {
            return new CardinalityImpl();
        } else {
            return null;
        }
    }

    private javax.oss.cbe.Cardinality _associationruleimpl_AEndCardinality = null;
    private java.lang.String _associationruleimpl_AEndRole = null;
    private java.lang.String _associationruleimpl_AEndType = null;
    private javax.oss.cbe.Cardinality _associationruleimpl_ZEndCardinality = null;
    private java.lang.String _associationruleimpl_ZEndRole = null;
    private java.lang.String _associationruleimpl_ZEndType = null;
    private java.lang.String _associationruleimpl_associationType = null;


    /**
     * Changes the AEndCardinality to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new AEndCardinality for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAEndCardinality(javax.oss.cbe.Cardinality value)
    throws java.lang.IllegalArgumentException    {
        _associationruleimpl_AEndCardinality = value;
    }


    /**
     * Returns this AssociationRuleImpl's AEndCardinality
     * 
     * @return the AEndCardinality
     * 
    */

    public javax.oss.cbe.Cardinality getAEndCardinality() {
        return _associationruleimpl_AEndCardinality;
    }

    /**
     * Changes the AEndRole to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new AEndRole for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAEndRole(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _associationruleimpl_AEndRole = value;
    }


    /**
     * Returns this AssociationRuleImpl's AEndRole
     * 
     * @return the AEndRole
     * 
    */

    public java.lang.String getAEndRole() {
        return _associationruleimpl_AEndRole;
    }

    /**
     * Changes the AEndType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new AEndType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAEndType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _associationruleimpl_AEndType = value;
    }


    /**
     * Returns this AssociationRuleImpl's AEndType
     * 
     * @return the AEndType
     * 
    */

    public java.lang.String getAEndType() {
        return _associationruleimpl_AEndType;
    }

    /**
     * Changes the ZEndCardinality to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new ZEndCardinality for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setZEndCardinality(javax.oss.cbe.Cardinality value)
    throws java.lang.IllegalArgumentException    {
        _associationruleimpl_ZEndCardinality = value;
    }


    /**
     * Returns this AssociationRuleImpl's ZEndCardinality
     * 
     * @return the ZEndCardinality
     * 
    */

    public javax.oss.cbe.Cardinality getZEndCardinality() {
        return _associationruleimpl_ZEndCardinality;
    }

    /**
     * Changes the ZEndRole to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new ZEndRole for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setZEndRole(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _associationruleimpl_ZEndRole = value;
    }


    /**
     * Returns this AssociationRuleImpl's ZEndRole
     * 
     * @return the ZEndRole
     * 
    */

    public java.lang.String getZEndRole() {
        return _associationruleimpl_ZEndRole;
    }

    /**
     * Changes the ZEndType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new ZEndType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setZEndType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _associationruleimpl_ZEndType = value;
    }


    /**
     * Returns this AssociationRuleImpl's ZEndType
     * 
     * @return the ZEndType
     * 
    */

    public java.lang.String getZEndType() {
        return _associationruleimpl_ZEndType;
    }

    /**
     * Changes the associationType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new associationType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAssociationType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _associationruleimpl_associationType = value;
    }


    /**
     * Returns this AssociationRuleImpl's associationType
     * 
     * @return the associationType
     * 
    */

    public java.lang.String getAssociationType() {
        return _associationruleimpl_associationType;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AssociationRule val = null;
        try { 
            val = (AssociationRule)super.clone();

            if( this.getAEndCardinality()!=null) 
                val.setAEndCardinality((javax.oss.cbe.Cardinality)((javax.oss.cbe.Cardinality) this.getAEndCardinality()).clone());
            else
                val.setAEndCardinality( null );

            if( this.getZEndCardinality()!=null) 
                val.setZEndCardinality((javax.oss.cbe.Cardinality)((javax.oss.cbe.Cardinality) this.getZEndCardinality()).clone());
            else
                val.setZEndCardinality( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("AssociationRuleImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two AssociationRule are equal.
     * The result is true if and only if the argument is not null 
     * and is an AssociationRule object that has the arguments.
     * 
     * @param value the Object to compare with this AssociationRule
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AssociationRule)) {
            AssociationRule argVal = (AssociationRule) value;
            if( !Utils.compareAttributes( getAEndCardinality(), argVal.getAEndCardinality())) { return false; } 
            if( !Utils.compareAttributes( getAEndRole(), argVal.getAEndRole())) { return false; } 
            if( !Utils.compareAttributes( getAEndType(), argVal.getAEndType())) { return false; } 
            if( !Utils.compareAttributes( getZEndCardinality(), argVal.getZEndCardinality())) { return false; } 
            if( !Utils.compareAttributes( getZEndRole(), argVal.getZEndRole())) { return false; } 
            if( !Utils.compareAttributes( getZEndType(), argVal.getZEndType())) { return false; } 
            if( !Utils.compareAttributes( getAssociationType(), argVal.getAssociationType())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
