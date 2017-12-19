/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import javax.oss.cbe.party.PartyValue;
import ossj.common.cbe.party.PartyValueImpl;
import javax.oss.cbe.trouble.TroubleTicketRoleAssignment;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketRoleAssignment</CODE> interface.  
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketRoleAssignment
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketRoleAssignmentImpl
implements TroubleTicketRoleAssignment
{ 

    /**
     * Constructs a new TroubleTicketRoleAssignmentImpl with empty attributes.
     * 
     */

    public TroubleTicketRoleAssignmentImpl() {
    }

    public String[] attributeTypeForAssignedParty() {
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.party.PartyValue";
        return list;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.party.PartyValue makePartyValue(String type){
        if(type.equals("javax.oss.cbe.party.PartyValue") || type.equals("ossj.common.cbe.party.PartyValueImpl")) {
            return new PartyValueImpl();
        } else {
            return null;
        }
    }

    private javax.oss.cbe.party.PartyValue _troubleticketroleassignmentimpl_assignedParty = null;
    private java.lang.String _troubleticketroleassignmentimpl_roleName = null;


    /**
     * Changes the assignedParty to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new assignedParty for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAssignedParty(javax.oss.cbe.party.PartyValue value)
    throws java.lang.IllegalArgumentException    {
        _troubleticketroleassignmentimpl_assignedParty = value;
    }


    /**
     * Returns this TroubleTicketRoleAssignmentImpl's assignedParty
     * 
     * @return the assignedParty
     * 
    */

    public javax.oss.cbe.party.PartyValue getAssignedParty() {
        return _troubleticketroleassignmentimpl_assignedParty;
    }

    /**
     * Changes the roleName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new roleName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRoleName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _troubleticketroleassignmentimpl_roleName = value;
    }


    /**
     * Returns this TroubleTicketRoleAssignmentImpl's roleName
     * 
     * @return the roleName
     * 
    */

    public java.lang.String getRoleName() {
        return _troubleticketroleassignmentimpl_roleName;
    }


    /**
     * Returns this TroubleTicketRoleAssignmentImpl's supportedPartyTypes
     * 
     * @return the supportedPartyTypes
     * 
    */

    public java.lang.String[] getSupportedPartyTypes() {
        // TODO There is no setter for this value
        // The Value have to be computed
        return null;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        TroubleTicketRoleAssignment val = null;
        try { 
            val = (TroubleTicketRoleAssignment)super.clone();

            if( this.getAssignedParty()!=null) 
                val.setAssignedParty((javax.oss.cbe.party.PartyValue)((javax.oss.cbe.party.PartyValue) this.getAssignedParty()).clone());
            else
                val.setAssignedParty( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("TroubleTicketRoleAssignmentImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }

    /**
     * Checks whether two TroubleTicketRoleAssignment are equal.
     * The result is true if and only if the argument is not null 
     * and is an TroubleTicketRoleAssignment object that has the arguments.
     * 
     * @param value the Object to compare with this TroubleTicketRoleAssignment
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof TroubleTicketRoleAssignment)) {
            TroubleTicketRoleAssignment argVal = (TroubleTicketRoleAssignment) value;
            if( !Utils.compareAttributes( getAssignedParty(), argVal.getAssignedParty())) { return false; } 
            if( !Utils.compareAttributes( getRoleName(), argVal.getRoleName())) { return false; } 
            if( !Utils.compareAttributes( getSupportedPartyTypes(), argVal.getSupportedPartyTypes())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
