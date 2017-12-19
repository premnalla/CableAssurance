/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.EntitySpecificationKey;
import javax.oss.cbe.EntityKey;
import javax.oss.cbe.EntityValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.EntityValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.EntityValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class EntityValueImpl
extends ossj.common.cbe.CBEManagedEntityValueImpl
implements EntityValue
{ 

    /**
     * Constructs a new EntityValueImpl with empty attributes.
     * 
     */

    public EntityValueImpl() {
        super();
        setManagedEntityKeyInstance( new EntityKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        EntityValue.DESCRIBING_SPECIFICATION_KEY,
    };

    private static final String[] settableAttributeNames = {
        EntityValue.DESCRIBING_SPECIFICATION_KEY,
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (EntityValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (EntityValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(EntityValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.EntityKey makeEntityKey(){
        return (EntityKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForDescribingSpecificationKey() {
        return attributeTypeFor("describingSpecificationKey");
    }

    public javax.oss.cbe.EntitySpecificationKey makeEntitySpecificationKey(String type){
        if(type.equals("javax.oss.cbe.EntitySpecificationKey") || type.equals("ossj.common.cbe.EntitySpecificationKeyImpl")) {
            return new EntitySpecificationKeyImpl();
        } else {
            return null;
        }
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.EntitySpecificationKey";
        addAttributeTypes("describingSpecificationKey", list);
    }

    private javax.oss.cbe.EntitySpecificationKey _entityvalueimpl_describingSpecificationKey = null;


    /**
     * Changes the describingSpecificationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new describingSpecificationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setDescribingSpecificationKey(javax.oss.cbe.EntitySpecificationKey value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(EntityValue.DESCRIBING_SPECIFICATION_KEY);
        _entityvalueimpl_describingSpecificationKey = value;
    }


    /**
     * Returns this EntityValueImpl's describingSpecificationKey
     * 
     * @return the describingSpecificationKey
     * 
    */

    public javax.oss.cbe.EntitySpecificationKey getDescribingSpecificationKey()
    throws java.lang.IllegalStateException {
        checkAttribute(EntityValue.DESCRIBING_SPECIFICATION_KEY);
        return _entityvalueimpl_describingSpecificationKey;
    }

    /**
     * Changes the entityKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new entityKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setEntityKey(javax.oss.cbe.EntityKey value)
    throws java.lang.IllegalArgumentException    {
        setManagedEntityKey(value);
    }


    /**
     * Returns this EntityValueImpl's entityKey
     * 
     * @return the entityKey
     * 
    */

    public javax.oss.cbe.EntityKey getEntityKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.EntityKey)getManagedEntityKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        EntityValue val = null;
            val = (EntityValue)super.clone();

            if( isPopulated(EntityValue.DESCRIBING_SPECIFICATION_KEY)) {
                if( this.getDescribingSpecificationKey()!=null) 
                    val.setDescribingSpecificationKey((javax.oss.cbe.EntitySpecificationKey)((javax.oss.cbe.EntitySpecificationKey) this.getDescribingSpecificationKey()).clone());
                else
                    val.setDescribingSpecificationKey( null );
            }

            return val;
    }

    /**
     * Checks whether two EntityValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an EntityValue object that has the arguments.
     * 
     * @param value the Object to compare with this EntityValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof EntityValue)) {
            EntityValue argVal = (EntityValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
