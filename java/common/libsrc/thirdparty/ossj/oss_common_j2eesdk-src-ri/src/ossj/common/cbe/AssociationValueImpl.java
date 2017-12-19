/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe;

import javax.oss.cbe.AssociationKey;
import javax.oss.cbe.EntityKey;
import javax.oss.cbe.AssociationValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.AssociationValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.AssociationValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AssociationValueImpl
extends ossj.common.cbe.CBEManagedEntityValueImpl
implements AssociationValue
{ 

    /**
     * Constructs a new AssociationValueImpl with empty attributes.
     * 
     */

    public AssociationValueImpl() {
        super();
        setManagedEntityKeyInstance( new AssociationKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        AssociationValue.A_END_KEY,
        AssociationValue.Z_END_KEY,
    };

    private static final String[] settableAttributeNames = {
        AssociationValue.A_END_KEY,
        AssociationValue.Z_END_KEY,
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (AssociationValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (AssociationValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(AssociationValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.EntityKey makeEntityKey(String type){
        if(type.equals("javax.oss.cbe.EntityKey") || type.equals("ossj.common.cbe.EntityKeyImpl")) {
            return new EntityKeyImpl();
        } else {
            return null;
        }
    }

    public javax.oss.cbe.AssociationKey makeAssociationKey(){
        return (AssociationKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForAEndKey() {
        return attributeTypeFor("AEndKey");
    }

    public String[] attributeTypeForZEndKey() {
        return attributeTypeFor("ZEndKey");
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.EntityKey";
        addAttributeTypes("ZEndKey", list);
        list[0] = "javax.oss.cbe.EntityKey";
        addAttributeTypes("AEndKey", list);
    }

    private javax.oss.cbe.EntityKey _associationvalueimpl_AEndKey = null;
    private javax.oss.cbe.EntityKey _associationvalueimpl_ZEndKey = null;


    /**
     * Changes the AEndKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new AEndKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAEndKey(javax.oss.cbe.EntityKey value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AssociationValue.A_END_KEY);
        _associationvalueimpl_AEndKey = value;
    }


    /**
     * Returns this AssociationValueImpl's AEndKey
     * 
     * @return the AEndKey
     * 
    */

    public javax.oss.cbe.EntityKey getAEndKey()
    throws java.lang.IllegalStateException {
        checkAttribute(AssociationValue.A_END_KEY);
        return _associationvalueimpl_AEndKey;
    }

    /**
     * Changes the ZEndKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new ZEndKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setZEndKey(javax.oss.cbe.EntityKey value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(AssociationValue.Z_END_KEY);
        _associationvalueimpl_ZEndKey = value;
    }


    /**
     * Returns this AssociationValueImpl's ZEndKey
     * 
     * @return the ZEndKey
     * 
    */

    public javax.oss.cbe.EntityKey getZEndKey()
    throws java.lang.IllegalStateException {
        checkAttribute(AssociationValue.Z_END_KEY);
        return _associationvalueimpl_ZEndKey;
    }

    /**
     * Changes the associationKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new associationKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAssociationKey(javax.oss.cbe.AssociationKey value)
    throws java.lang.IllegalArgumentException    {
        setManagedEntityKey(value);
    }


    /**
     * Returns this AssociationValueImpl's associationKey
     * 
     * @return the associationKey
     * 
    */

    public javax.oss.cbe.AssociationKey getAssociationKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.AssociationKey)getManagedEntityKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AssociationValue val = null;
            val = (AssociationValue)super.clone();

            if( isPopulated(AssociationValue.A_END_KEY)) {
                if( this.getAEndKey()!=null) 
                    val.setAEndKey((javax.oss.cbe.EntityKey)((javax.oss.cbe.EntityKey) this.getAEndKey()).clone());
                else
                    val.setAEndKey( null );
            }

            if( isPopulated(AssociationValue.Z_END_KEY)) {
                if( this.getZEndKey()!=null) 
                    val.setZEndKey((javax.oss.cbe.EntityKey)((javax.oss.cbe.EntityKey) this.getZEndKey()).clone());
                else
                    val.setZEndKey( null );
            }

            return val;
    }

    /**
     * Checks whether two AssociationValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an AssociationValue object that has the arguments.
     * 
     * @param value the Object to compare with this AssociationValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AssociationValue)) {
            AssociationValue argVal = (AssociationValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
