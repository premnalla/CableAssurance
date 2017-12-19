/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.CBEManagedEntityKey;
import javax.oss.cbe.CBEManagedEntityValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.CBEManagedEntityValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.CBEManagedEntityValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CBEManagedEntityValueImpl
extends ossj.common.ManagedEntityValueImpl
implements CBEManagedEntityValue
{ 

    /**
     * Constructs a new CBEManagedEntityValueImpl with empty attributes.
     * 
     */

    public CBEManagedEntityValueImpl() {
        super();
        setManagedEntityKeyInstance( new CBEManagedEntityKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        CBEManagedEntityValue.SUB_GRAPH_ID
    };

    private static final String[] settableAttributeNames = {
        CBEManagedEntityValue.SUB_GRAPH_ID
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (CBEManagedEntityValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (CBEManagedEntityValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(CBEManagedEntityValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.CBEManagedEntityKey makeCBEManagedEntityKey(){
        return (CBEManagedEntityKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "long";
        addAttributeTypes("subGraphId", list);
    }

    private long _cbemanagedentityvalueimpl_subGraphId;


    /**
     * Changes the CBEManagedEntityKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new CBEManagedEntityKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setCBEManagedEntityKey(javax.oss.cbe.CBEManagedEntityKey value)
    throws java.lang.IllegalArgumentException    {
        setManagedEntityKey(value);
    }


    /**
     * Returns this CBEManagedEntityValueImpl's CBEManagedEntityKey
     * 
     * @return the CBEManagedEntityKey
     * 
    */

    public javax.oss.cbe.CBEManagedEntityKey getCBEManagedEntityKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.CBEManagedEntityKey)getManagedEntityKey();
    }

    /**
     * Changes the subGraphId to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new subGraphId for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setSubGraphId(long value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(CBEManagedEntityValue.SUB_GRAPH_ID);
        _cbemanagedentityvalueimpl_subGraphId = value;
    }


    /**
     * Returns this CBEManagedEntityValueImpl's subGraphId
     * 
     * @return the subGraphId
     * 
    */

    public long getSubGraphId()
    throws java.lang.IllegalStateException {
        checkAttribute(CBEManagedEntityValue.SUB_GRAPH_ID);
        return _cbemanagedentityvalueimpl_subGraphId;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        CBEManagedEntityValue val = null;
            val = (CBEManagedEntityValue)super.clone();

            return val;
    }

    /**
     * Checks whether two CBEManagedEntityValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an CBEManagedEntityValue object that has the arguments.
     * 
     * @param value the Object to compare with this CBEManagedEntityValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof CBEManagedEntityValue)) {
            CBEManagedEntityValue argVal = (CBEManagedEntityValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
