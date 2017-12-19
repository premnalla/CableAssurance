/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionItemKey;
import javax.oss.cbe.datatypes.Quantity;
import ossj.common.cbe.datatypes.QuantityImpl;
import javax.oss.cbe.bi.BusinessInteractionItemValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionItemValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionItemValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionItemValueImpl
extends ossj.common.cbe.EntityValueImpl
implements BusinessInteractionItemValue
{ 

    /**
     * Constructs a new BusinessInteractionItemValueImpl with empty attributes.
     * 
     */

    public BusinessInteractionItemValueImpl() {
        super();
        setManagedEntityKeyInstance( new BusinessInteractionItemKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        BusinessInteractionItemValue.ACTION,
        BusinessInteractionItemValue.QUANTITY
    };

    private static final String[] settableAttributeNames = {
        BusinessInteractionItemValue.ACTION,
        BusinessInteractionItemValue.QUANTITY
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (BusinessInteractionItemValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (BusinessInteractionItemValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(BusinessInteractionItemValueImpl.settableAttributeNames);
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

    public String[] attributeTypeForQuantity() {
        return attributeTypeFor("quantity");
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.datatypes.Quantity makeQuantity(String type){
        if(type.equals("javax.oss.cbe.datatypes.Quantity") || type.equals("ossj.common.cbe.datatypes.QuantityImpl")) {
            return new QuantityImpl();
        } else {
            return null;
        }
    }

    public javax.oss.cbe.bi.BusinessInteractionItemKey makeBusinessInteractionItemKey(){
        return (BusinessInteractionItemKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "java.lang.String";
        addAttributeTypes("action", list);
        list[0] = "javax.oss.cbe.datatypes.Quantity";
        addAttributeTypes("quantity", list);
    }

    private java.lang.String _businessinteractionitemvalueimpl_action = null;
    private javax.oss.cbe.datatypes.Quantity _businessinteractionitemvalueimpl_quantity = null;


    /**
     * Changes the action to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new action for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAction(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(BusinessInteractionItemValue.ACTION);
        _businessinteractionitemvalueimpl_action = value;
    }


    /**
     * Returns this BusinessInteractionItemValueImpl's action
     * 
     * @return the action
     * 
    */

    public java.lang.String getAction()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionItemValue.ACTION);
        return _businessinteractionitemvalueimpl_action;
    }

    /**
     * Changes the businessInteractionItemKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new businessInteractionItemKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBusinessInteractionItemKey(javax.oss.cbe.bi.BusinessInteractionItemKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this BusinessInteractionItemValueImpl's businessInteractionItemKey
     * 
     * @return the businessInteractionItemKey
     * 
    */

    public javax.oss.cbe.bi.BusinessInteractionItemKey getBusinessInteractionItemKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.bi.BusinessInteractionItemKey)getEntityKey();
    }

    /**
     * Changes the quantity to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new quantity for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setQuantity(javax.oss.cbe.datatypes.Quantity value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(BusinessInteractionItemValue.QUANTITY);
        _businessinteractionitemvalueimpl_quantity = value;
    }


    /**
     * Returns this BusinessInteractionItemValueImpl's quantity
     * 
     * @return the quantity
     * 
    */

    public javax.oss.cbe.datatypes.Quantity getQuantity()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionItemValue.QUANTITY);
        return _businessinteractionitemvalueimpl_quantity;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        BusinessInteractionItemValue val = null;
            val = (BusinessInteractionItemValue)super.clone();

            if( isPopulated(BusinessInteractionItemValue.ACTION)) {
                if( this.getAction()!=null) 
                    val.setAction(((java.lang.String) new String( this.getAction())));
                else
                    val.setAction( null );
            }

            if( isPopulated(BusinessInteractionItemValue.QUANTITY)) {
                if( this.getQuantity()!=null) 
                    val.setQuantity((javax.oss.cbe.datatypes.Quantity)((javax.oss.cbe.datatypes.Quantity) this.getQuantity()).clone());
                else
                    val.setQuantity( null );
            }

            return val;
    }

    /**
     * Checks whether two BusinessInteractionItemValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an BusinessInteractionItemValue object that has the arguments.
     * 
     * @param value the Object to compare with this BusinessInteractionItemValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof BusinessInteractionItemValue)) {
            BusinessInteractionItemValue argVal = (BusinessInteractionItemValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
