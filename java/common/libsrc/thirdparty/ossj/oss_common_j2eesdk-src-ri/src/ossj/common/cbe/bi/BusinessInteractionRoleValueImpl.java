/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.BusinessInteractionRoleKey;
import javax.oss.cbe.bi.BusinessInteractionRoleValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.BusinessInteractionRoleValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.bi.BusinessInteractionRoleValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BusinessInteractionRoleValueImpl
extends ossj.common.cbe.EntityValueImpl
implements BusinessInteractionRoleValue
{ 

    /**
     * Constructs a new BusinessInteractionRoleValueImpl with empty attributes.
     * 
     */

    public BusinessInteractionRoleValueImpl() {
        super();
        setManagedEntityKeyInstance( new BusinessInteractionRoleKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        BusinessInteractionRoleValue.INTERACTION_ROLE
    };

    private static final String[] settableAttributeNames = {
        BusinessInteractionRoleValue.INTERACTION_ROLE
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (BusinessInteractionRoleValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (BusinessInteractionRoleValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(BusinessInteractionRoleValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.bi.BusinessInteractionRoleKey makeBusinessInteractionRoleKey(){
        return (BusinessInteractionRoleKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "java.lang.String";
        addAttributeTypes("interactionRole", list);
    }

    private java.lang.String _businessinteractionrolevalueimpl_interactionRole = null;


    /**
     * Changes the businessInteractionRoleKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new businessInteractionRoleKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setBusinessInteractionRoleKey(javax.oss.cbe.bi.BusinessInteractionRoleKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this BusinessInteractionRoleValueImpl's businessInteractionRoleKey
     * 
     * @return the businessInteractionRoleKey
     * 
    */

    public javax.oss.cbe.bi.BusinessInteractionRoleKey getBusinessInteractionRoleKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.bi.BusinessInteractionRoleKey)getEntityKey();
    }

    /**
     * Changes the interactionRole to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new interactionRole for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setInteractionRole(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(BusinessInteractionRoleValue.INTERACTION_ROLE);
        _businessinteractionrolevalueimpl_interactionRole = value;
    }


    /**
     * Returns this BusinessInteractionRoleValueImpl's interactionRole
     * 
     * @return the interactionRole
     * 
    */

    public java.lang.String getInteractionRole()
    throws java.lang.IllegalStateException {
        checkAttribute(BusinessInteractionRoleValue.INTERACTION_ROLE);
        return _businessinteractionrolevalueimpl_interactionRole;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        BusinessInteractionRoleValue val = null;
            val = (BusinessInteractionRoleValue)super.clone();

            if( isPopulated(BusinessInteractionRoleValue.INTERACTION_ROLE)) {
                if( this.getInteractionRole()!=null) 
                    val.setInteractionRole(((java.lang.String) new String( this.getInteractionRole())));
                else
                    val.setInteractionRole( null );
            }

            return val;
    }

    /**
     * Checks whether two BusinessInteractionRoleValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an BusinessInteractionRoleValue object that has the arguments.
     * 
     * @param value the Object to compare with this BusinessInteractionRoleValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof BusinessInteractionRoleValue)) {
            BusinessInteractionRoleValue argVal = (BusinessInteractionRoleValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
