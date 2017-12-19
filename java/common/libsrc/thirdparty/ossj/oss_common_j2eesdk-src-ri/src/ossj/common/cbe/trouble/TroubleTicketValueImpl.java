/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.trouble;

import java.util.Date;
import javax.oss.cbe.trouble.TroubleTicketItemKey;
import javax.oss.cbe.trouble.TroubleTicketRoleAssignment;
import javax.oss.cbe.trouble.TroubleTicketKey;
import javax.oss.cbe.trouble.TroubleTicketValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.trouble.TroubleTicketValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.trouble.TroubleTicketValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class TroubleTicketValueImpl
extends ossj.common.cbe.bi.BusinessInteractionValueImpl
implements TroubleTicketValue
{ 

    /**
     * Constructs a new TroubleTicketValueImpl with empty attributes.
     * 
     */

    public TroubleTicketValueImpl() {
        super();
        setManagedEntityKeyInstance( new TroubleTicketKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        TroubleTicketValue.ROLE_ASSIGNMENTS,
        TroubleTicketValue.SERVICE_RESTORED_TIME,
        TroubleTicketValue.TROUBLE_DESCRIPTION,
        TroubleTicketValue.TROUBLE_DETECTION_TIME,
        TroubleTicketValue.TROUBLE_TICKET_ITEM_KEYS,
        TroubleTicketValue.TROUBLE_TICKET_STATE
    };

    private static final String[] settableAttributeNames = {
        TroubleTicketValue.ROLE_ASSIGNMENTS,
        TroubleTicketValue.SERVICE_RESTORED_TIME,
        TroubleTicketValue.TROUBLE_DESCRIPTION,
        TroubleTicketValue.TROUBLE_DETECTION_TIME,
        TroubleTicketValue.TROUBLE_TICKET_ITEM_KEYS,
        TroubleTicketValue.TROUBLE_TICKET_STATE
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (TroubleTicketValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (TroubleTicketValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(TroubleTicketValueImpl.settableAttributeNames);
            super.configureAttributes(anAttributeManager);
        }
    }

    /**
     * Holds the Attribute manager that manage the optional fields
    */
    private static AttributeManager attributemanager = null;

    protected AttributeManager getAttributeManager() {
        return attributemanager;
    }
    protected AttributeManager makeAttributeManager() {
        attributemanager = new AttributeManager();
        return attributemanager;
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.trouble.TroubleTicketKey makeTroubleTicketKey(){
        return (TroubleTicketKey) makeManagedEntityKey();
    }

    public javax.oss.cbe.trouble.TroubleTicketRoleAssignment[] makeTroubleTicketRoleAssignments(int nb, String type){
        if(type.equals("javax.oss.cbe.trouble.TroubleTicketRoleAssignment") || type.equals("ossj.common.cbe.trouble.TroubleTicketRoleAssignmentImpl")) {
            return new TroubleTicketRoleAssignmentImpl[nb];
        } else {
            return null;
        }
    }

    public javax.oss.cbe.trouble.TroubleTicketItemKey[] makeTroubleTicketItemKeys(int nb, String type){
        if(type.equals("javax.oss.cbe.trouble.TroubleTicketItemKey") || type.equals("ossj.common.cbe.trouble.TroubleTicketItemKeyImpl")) {
            return new TroubleTicketItemKeyImpl[nb];
        } else {
            return null;
        }
    }

    public String[] attributeTypeForRoleAssignments() {
        return attributeTypeFor("roleAssignments");
    }

    public String[] attributeTypeForTroubleTicketItemKeys() {
        return attributeTypeFor("troubleTicketItemKeys");
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        addEnumeration("troubleTicketState", "javax.oss.cbe.trouble.TroubleTicketState");
        list[0] = "[Ljavax.oss.cbe.trouble.TroubleTicketRoleAssignment;";
        addAttributeTypes("roleAssignments", list);
        list[0] = "java.util.Date";
        addAttributeTypes("troubleDetectionTime", list);
        list[0] = "java.lang.String";
        addAttributeTypes("troubleDescription", list);
        list[0] = "java.util.Date";
        addAttributeTypes("serviceRestoredTime", list);
        list[0] = "[Ljavax.oss.cbe.trouble.TroubleTicketItemKey;";
        addAttributeTypes("troubleTicketItemKeys", list);
    }

    private javax.oss.cbe.trouble.TroubleTicketRoleAssignment[] _troubleticketvalueimpl_roleAssignments = null;
    private java.util.Date _troubleticketvalueimpl_serviceRestoredTime = null;
    private java.lang.String _troubleticketvalueimpl_troubleDescription = null;
    private java.util.Date _troubleticketvalueimpl_troubleDetectionTime = null;
    private javax.oss.cbe.trouble.TroubleTicketItemKey[] _troubleticketvalueimpl_troubleTicketItemKeys = null;
    private java.lang.String _troubleticketvalueimpl_troubleTicketState = null;


    /**
     * Changes the roleAssignments to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new roleAssignments for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setRoleAssignments(javax.oss.cbe.trouble.TroubleTicketRoleAssignment[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(TroubleTicketValue.ROLE_ASSIGNMENTS);
        _troubleticketvalueimpl_roleAssignments = value;
    }


    /**
     * Returns this TroubleTicketValueImpl's roleAssignments
     * 
     * @return the roleAssignments
     * 
    */

    public javax.oss.cbe.trouble.TroubleTicketRoleAssignment[] getRoleAssignments()
    throws java.lang.IllegalStateException {
        checkAttribute(TroubleTicketValue.ROLE_ASSIGNMENTS);
        return _troubleticketvalueimpl_roleAssignments;
    }

    /**
     * Changes the serviceRestoredTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new serviceRestoredTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setServiceRestoredTime(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(TroubleTicketValue.SERVICE_RESTORED_TIME);
        _troubleticketvalueimpl_serviceRestoredTime = value;
    }


    /**
     * Returns this TroubleTicketValueImpl's serviceRestoredTime
     * 
     * @return the serviceRestoredTime
     * 
    */

    public java.util.Date getServiceRestoredTime()
    throws java.lang.IllegalStateException {
        checkAttribute(TroubleTicketValue.SERVICE_RESTORED_TIME);
        return _troubleticketvalueimpl_serviceRestoredTime;
    }

    /**
     * Changes the troubleDescription to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new troubleDescription for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTroubleDescription(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(TroubleTicketValue.TROUBLE_DESCRIPTION);
        _troubleticketvalueimpl_troubleDescription = value;
    }


    /**
     * Returns this TroubleTicketValueImpl's troubleDescription
     * 
     * @return the troubleDescription
     * 
    */

    public java.lang.String getTroubleDescription()
    throws java.lang.IllegalStateException {
        checkAttribute(TroubleTicketValue.TROUBLE_DESCRIPTION);
        return _troubleticketvalueimpl_troubleDescription;
    }

    /**
     * Changes the troubleDetectionTime to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new troubleDetectionTime for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTroubleDetectionTime(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(TroubleTicketValue.TROUBLE_DETECTION_TIME);
        _troubleticketvalueimpl_troubleDetectionTime = value;
    }


    /**
     * Returns this TroubleTicketValueImpl's troubleDetectionTime
     * 
     * @return the troubleDetectionTime
     * 
    */

    public java.util.Date getTroubleDetectionTime()
    throws java.lang.IllegalStateException {
        checkAttribute(TroubleTicketValue.TROUBLE_DETECTION_TIME);
        return _troubleticketvalueimpl_troubleDetectionTime;
    }

    /**
     * Changes the troubleTicketItemKeys to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new troubleTicketItemKeys for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTroubleTicketItemKeys(javax.oss.cbe.trouble.TroubleTicketItemKey[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(TroubleTicketValue.TROUBLE_TICKET_ITEM_KEYS);
        _troubleticketvalueimpl_troubleTicketItemKeys = value;
    }


    /**
     * Returns this TroubleTicketValueImpl's troubleTicketItemKeys
     * 
     * @return the troubleTicketItemKeys
     * 
    */

    public javax.oss.cbe.trouble.TroubleTicketItemKey[] getTroubleTicketItemKeys()
    throws java.lang.IllegalStateException {
        checkAttribute(TroubleTicketValue.TROUBLE_TICKET_ITEM_KEYS);
        return _troubleticketvalueimpl_troubleTicketItemKeys;
    }

    /**
     * Changes the troubleTicketKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new troubleTicketKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTroubleTicketKey(javax.oss.cbe.trouble.TroubleTicketKey value)
    throws java.lang.IllegalArgumentException    {
        setBusinessInteractionKey(value);
    }


    /**
     * Returns this TroubleTicketValueImpl's troubleTicketKey
     * 
     * @return the troubleTicketKey
     * 
    */

    public javax.oss.cbe.trouble.TroubleTicketKey getTroubleTicketKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.trouble.TroubleTicketKey)getBusinessInteractionKey();
    }

    /**
     * Changes the troubleTicketState to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new troubleTicketState for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTroubleTicketState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(TroubleTicketValue.TROUBLE_TICKET_STATE);
        _troubleticketvalueimpl_troubleTicketState = value;
    }


    /**
     * Returns this TroubleTicketValueImpl's troubleTicketState
     * 
     * @return the troubleTicketState
     * 
    */

    public java.lang.String getTroubleTicketState()
    throws java.lang.IllegalStateException {
        checkAttribute(TroubleTicketValue.TROUBLE_TICKET_STATE);
        return _troubleticketvalueimpl_troubleTicketState;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        TroubleTicketValue val = null;
            val = (TroubleTicketValue)super.clone();

            if( isPopulated(TroubleTicketValue.ROLE_ASSIGNMENTS)) {
                if( this.getRoleAssignments()!=null) 
                    val.setRoleAssignments((javax.oss.cbe.trouble.TroubleTicketRoleAssignment[])((javax.oss.cbe.trouble.TroubleTicketRoleAssignment[]) this.getRoleAssignments()).clone());
                else
                    val.setRoleAssignments( null );
            }

            if( isPopulated(TroubleTicketValue.SERVICE_RESTORED_TIME)) {
                if( this.getServiceRestoredTime()!=null) 
                    val.setServiceRestoredTime((java.util.Date)((java.util.Date) this.getServiceRestoredTime()).clone());
                else
                    val.setServiceRestoredTime( null );
            }

            if( isPopulated(TroubleTicketValue.TROUBLE_DESCRIPTION)) {
                if( this.getTroubleDescription()!=null) 
                    val.setTroubleDescription(((java.lang.String) new String( this.getTroubleDescription())));
                else
                    val.setTroubleDescription( null );
            }

            if( isPopulated(TroubleTicketValue.TROUBLE_DETECTION_TIME)) {
                if( this.getTroubleDetectionTime()!=null) 
                    val.setTroubleDetectionTime((java.util.Date)((java.util.Date) this.getTroubleDetectionTime()).clone());
                else
                    val.setTroubleDetectionTime( null );
            }

            if( isPopulated(TroubleTicketValue.TROUBLE_TICKET_ITEM_KEYS)) {
                if( this.getTroubleTicketItemKeys()!=null) 
                    val.setTroubleTicketItemKeys((javax.oss.cbe.trouble.TroubleTicketItemKey[])((javax.oss.cbe.trouble.TroubleTicketItemKey[]) this.getTroubleTicketItemKeys()).clone());
                else
                    val.setTroubleTicketItemKeys( null );
            }

            if( isPopulated(TroubleTicketValue.TROUBLE_TICKET_STATE)) {
                if( this.getTroubleTicketState()!=null) 
                    val.setTroubleTicketState(((java.lang.String) new String( this.getTroubleTicketState())));
                else
                    val.setTroubleTicketState( null );
            }

            return val;
    }

    /**
     * Checks whether two TroubleTicketValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an TroubleTicketValue object that has the arguments.
     * 
     * @param value the Object to compare with this TroubleTicketValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof TroubleTicketValue)) {
            TroubleTicketValue argVal = (TroubleTicketValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
