/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntitySpecificationKey;
import javax.oss.cbe.EntitySpecificationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntitySpecificationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.EntitySpecificationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntitySpecificationValueImpl
extends ossj.common.cbe.CBEManagedEntityValueImpl
implements EntitySpecificationValue
{ 

    /**
     * Constructs a new EntitySpecificationValueImpl with empty attributes.
     * 
     */

    public EntitySpecificationValueImpl() {
        super();
        setManagedEntityKeyInstance( new EntitySpecificationKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        EntitySpecificationValue.DESCRIBED_ENTITY_TYPE,
    };

    private static final String[] settableAttributeNames = {
        EntitySpecificationValue.DESCRIBED_ENTITY_TYPE,
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (EntitySpecificationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (EntitySpecificationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(EntitySpecificationValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.EntitySpecificationKey makeEntitySpecificationKey(){
        return (EntitySpecificationKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "java.lang.String";
        addAttributeTypes("describedEntityType", list);
    }

    private java.lang.String _entityspecificationvalueimpl_describedEntityType = null;


    /**
     * Changes the describedEntityType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new describedEntityType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setDescribedEntityType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(EntitySpecificationValue.DESCRIBED_ENTITY_TYPE);
        _entityspecificationvalueimpl_describedEntityType = value;
    }


    /**
     * Returns this EntitySpecificationValueImpl's describedEntityType
     * 
     * @return the describedEntityType
     * 
    */

    public java.lang.String getDescribedEntityType()
    throws java.lang.IllegalStateException {
        checkAttribute(EntitySpecificationValue.DESCRIBED_ENTITY_TYPE);
        return _entityspecificationvalueimpl_describedEntityType;
    }

    /**
     * Changes the entitySpecificationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new entitySpecificationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setEntitySpecificationKey(javax.oss.cbe.EntitySpecificationKey value)
    throws java.lang.IllegalArgumentException    {
        setManagedEntityKey(value);
    }


    /**
     * Returns this EntitySpecificationValueImpl's entitySpecificationKey
     * 
     * @return the entitySpecificationKey
     * 
    */

    public javax.oss.cbe.EntitySpecificationKey getEntitySpecificationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.EntitySpecificationKey)getManagedEntityKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        EntitySpecificationValue val = null;
            val = (EntitySpecificationValue)super.clone();

            if( isPopulated(EntitySpecificationValue.DESCRIBED_ENTITY_TYPE)) {
                if( this.getDescribedEntityType()!=null) 
                    val.setDescribedEntityType(((java.lang.String) new String( this.getDescribedEntityType())));
                else
                    val.setDescribedEntityType( null );
            }

            return val;
    }

    /**
     * Checks whether two EntitySpecificationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an EntitySpecificationValue object that has the arguments.
     * 
     * @param value the Object to compare with this EntitySpecificationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof EntitySpecificationValue)) {
            EntitySpecificationValue argVal = (EntitySpecificationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
