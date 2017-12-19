/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import java.util.Date;
import javax.oss.cbe.bi.BusinessInteractionItemKey;
import javax.oss.cbe.bi.BusinessInteractionKey;
import javax.oss.cbe.bi.BusinessInteractionValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionValueImpl
extends ossj.common.cbe.EntityValueImpl
implements BusinessInteractionValue
{ 

    /**
     * Constructs a new BusinessInteractionValueImpl with empty attributes.
     * 
     */

    public BusinessInteractionValueImpl() {
        super();
        setManagedEntityKeyInstance( new BusinessInteractionKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        BusinessInteractionValue.BUSINESS_INTERACTION_ITEM_KEYS,
        BusinessInteractionValue.DESCRIPTION,
        BusinessInteractionValue.INTERACTION_DATE,
        BusinessInteractionValue.INTERACTION_DATE_COMPLETE,
        BusinessInteractionValue.INTERACTION_STATE,
        BusinessInteractionValue.INTERACTION_STATUS
    };

    private static final String[] settableAttributeNames = {
        BusinessInteractionValue.BUSINESS_INTERACTION_ITEM_KEYS,
        BusinessInteractionValue.DESCRIPTION,
        BusinessInteractionValue.INTERACTION_DATE,
        BusinessInteractionValue.INTERACTION_DATE_COMPLETE,
        BusinessInteractionValue.INTERACTION_STATE,
        BusinessInteractionValue.INTERACTION_STATUS
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (BusinessInteractionValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (BusinessInteractionValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(BusinessInteractionValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.bi.BusinessInteractionKey makeBusinessInteractionKey(){
        return (BusinessInteractionKey) makeManagedEntityKey();
    }

    public javax.oss.cbe.bi.BusinessInteractionItemKey[] makeBusinessInteractionItemKeys(int nb, String type){
        if(type.equals("javax.oss.cbe.bi.BusinessInteractionItemKey") || type.equals("ossj.common.cbe.bi.BusinessInteractionItemKeyImpl")) {
            return new BusinessInteractionItemKeyImpl[nb];
        } else {
            return null;
        }
    }

    public String[] attributeTypeForBusinessInteractionItemKeys() {
        return attributeTypeFor("businessInteractionItemKeys");
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        addEnumeration("interactionStatus", "javax.oss.cbe.bi.InteractionStatus");
        addEnumeration("interactionState", "javax.oss.cbe.bi.InteractionState");
        list[0] = "[Ljavax.oss.cbe.bi.BusinessInteractionItemKey;";
        addAttributeTypes("businessInteractionItemKeys", list);
        list[0] = "java.util.Date";
        addAttributeTypes("interactionDate", list);
        list[0] = "java.util.Date";
        addAttributeTypes("interactionDateComplete", list);
        list[0] = "java.lang.String";
        addAttributeTypes("description", list);
    }

    private javax.oss.cbe.bi.BusinessInteractionItemKey[] _businessinteractionvalueimpl_businessInteractionItemKeys = null;
    private java.lang.String _businessinteractionvalueimpl_description = null;
    private java.util.Date _businessinteractionvalueimpl_interactionDate = null;
    private java.util.Date _businessinteractionvalueimpl_interactionDateComplete = null;
    private java.lang.String _businessinteractionvalueimpl_interactionState = null;
    private java.lang.String _businessinteractionvalueimpl_interactionStatus = null;


    /**
     * Changes the businessInteractionItemKeys to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new businessInteractionItemKeys for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBusinessInteractionItemKeys(javax.oss.cbe.bi.BusinessInteractionItemKey[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(BusinessInteractionValue.BUSINESS_INTERACTION_ITEM_KEYS);
        _businessinteractionvalueimpl_businessInteractionItemKeys = value;
    }


    /**
     * Returns this BusinessInteractionValueImpl's businessInteractionItemKeys
     * 
     * @return the businessInteractionItemKeys
     * 
    */

    public javax.oss.cbe.bi.BusinessInteractionItemKey[] getBusinessInteractionItemKeys()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionValue.BUSINESS_INTERACTION_ITEM_KEYS);
        return _businessinteractionvalueimpl_businessInteractionItemKeys;
    }

    /**
     * Changes the businessInteractionKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new businessInteractionKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBusinessInteractionKey(javax.oss.cbe.bi.BusinessInteractionKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this BusinessInteractionValueImpl's businessInteractionKey
     * 
     * @return the businessInteractionKey
     * 
    */

    public javax.oss.cbe.bi.BusinessInteractionKey getBusinessInteractionKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.bi.BusinessInteractionKey)getEntityKey();
    }

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
        setDirtyAttribute(BusinessInteractionValue.DESCRIPTION);
        _businessinteractionvalueimpl_description = value;
    }


    /**
     * Returns this BusinessInteractionValueImpl's description
     * 
     * @return the description
     * 
    */

    public java.lang.String getDescription()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionValue.DESCRIPTION);
        return _businessinteractionvalueimpl_description;
    }

    /**
     * Changes the interactionDate to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new interactionDate for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setInteractionDate(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(BusinessInteractionValue.INTERACTION_DATE);
        _businessinteractionvalueimpl_interactionDate = value;
    }


    /**
     * Returns this BusinessInteractionValueImpl's interactionDate
     * 
     * @return the interactionDate
     * 
    */

    public java.util.Date getInteractionDate()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionValue.INTERACTION_DATE);
        return _businessinteractionvalueimpl_interactionDate;
    }

    /**
     * Changes the interactionDateComplete to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new interactionDateComplete for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setInteractionDateComplete(java.util.Date value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(BusinessInteractionValue.INTERACTION_DATE_COMPLETE);
        _businessinteractionvalueimpl_interactionDateComplete = value;
    }


    /**
     * Returns this BusinessInteractionValueImpl's interactionDateComplete
     * 
     * @return the interactionDateComplete
     * 
    */

    public java.util.Date getInteractionDateComplete()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionValue.INTERACTION_DATE_COMPLETE);
        return _businessinteractionvalueimpl_interactionDateComplete;
    }

    /**
     * Changes the interactionState to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new interactionState for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setInteractionState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(BusinessInteractionValue.INTERACTION_STATE);
        _businessinteractionvalueimpl_interactionState = value;
    }


    /**
     * Returns this BusinessInteractionValueImpl's interactionState
     * 
     * @return the interactionState
     * 
    */

    public java.lang.String getInteractionState()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionValue.INTERACTION_STATE);
        return _businessinteractionvalueimpl_interactionState;
    }

    /**
     * Changes the interactionStatus to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new interactionStatus for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setInteractionStatus(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(BusinessInteractionValue.INTERACTION_STATUS);
        _businessinteractionvalueimpl_interactionStatus = value;
    }


    /**
     * Returns this BusinessInteractionValueImpl's interactionStatus
     * 
     * @return the interactionStatus
     * 
    */

    public java.lang.String getInteractionStatus()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionValue.INTERACTION_STATUS);
        return _businessinteractionvalueimpl_interactionStatus;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        BusinessInteractionValue val = null;
            val = (BusinessInteractionValue)super.clone();

            if( isPopulated(BusinessInteractionValue.BUSINESS_INTERACTION_ITEM_KEYS)) {
                if( this.getBusinessInteractionItemKeys()!=null) 
                    val.setBusinessInteractionItemKeys((javax.oss.cbe.bi.BusinessInteractionItemKey[])((javax.oss.cbe.bi.BusinessInteractionItemKey[]) this.getBusinessInteractionItemKeys()).clone());
                else
                    val.setBusinessInteractionItemKeys( null );
            }

            if( isPopulated(BusinessInteractionValue.DESCRIPTION)) {
                if( this.getDescription()!=null) 
                    val.setDescription(((java.lang.String) new String( this.getDescription())));
                else
                    val.setDescription( null );
            }

            if( isPopulated(BusinessInteractionValue.INTERACTION_DATE)) {
                if( this.getInteractionDate()!=null) 
                    val.setInteractionDate((java.util.Date)((java.util.Date) this.getInteractionDate()).clone());
                else
                    val.setInteractionDate( null );
            }

            if( isPopulated(BusinessInteractionValue.INTERACTION_DATE_COMPLETE)) {
                if( this.getInteractionDateComplete()!=null) 
                    val.setInteractionDateComplete((java.util.Date)((java.util.Date) this.getInteractionDateComplete()).clone());
                else
                    val.setInteractionDateComplete( null );
            }

            if( isPopulated(BusinessInteractionValue.INTERACTION_STATE)) {
                if( this.getInteractionState()!=null) 
                    val.setInteractionState(((java.lang.String) new String( this.getInteractionState())));
                else
                    val.setInteractionState( null );
            }

            if( isPopulated(BusinessInteractionValue.INTERACTION_STATUS)) {
                if( this.getInteractionStatus()!=null) 
                    val.setInteractionStatus(((java.lang.String) new String( this.getInteractionStatus())));
                else
                    val.setInteractionStatus( null );
            }

            return val;
    }

    /**
     * Checks whether two BusinessInteractionValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an BusinessInteractionValue object that has the arguments.
     * 
     * @param value the Object to compare with this BusinessInteractionValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof BusinessInteractionValue)) {
            BusinessInteractionValue argVal = (BusinessInteractionValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
