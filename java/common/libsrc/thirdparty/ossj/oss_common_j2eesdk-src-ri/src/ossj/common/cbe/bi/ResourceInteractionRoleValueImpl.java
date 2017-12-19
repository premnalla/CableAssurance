/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.ResourceInteractionRoleKey;
import javax.oss.cbe.bi.ResourceInteractionRoleValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.ResourceInteractionRoleValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.bi.ResourceInteractionRoleValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ResourceInteractionRoleValueImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleValueImpl
implements ResourceInteractionRoleValue
{ 

    /**
     * Constructs a new ResourceInteractionRoleValueImpl with empty attributes.
     * 
     */

    public ResourceInteractionRoleValueImpl() {
        super();
        setManagedEntityKeyInstance( new ResourceInteractionRoleKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
    };

    private static final String[] settableAttributeNames = {
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ResourceInteractionRoleValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ResourceInteractionRoleValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ResourceInteractionRoleValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.bi.ResourceInteractionRoleKey makeResourceInteractionRoleKey(){
        return (ResourceInteractionRoleKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
    }



    /**
     * Changes the resourceInteractionRoleKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new resourceInteractionRoleKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setResourceInteractionRoleKey(javax.oss.cbe.bi.ResourceInteractionRoleKey value)
    throws java.lang.IllegalArgumentException    {
        setBusinessInteractionRoleKey(value);
    }


    /**
     * Returns this ResourceInteractionRoleValueImpl's resourceInteractionRoleKey
     * 
     * @return the resourceInteractionRoleKey
     * 
    */

    public javax.oss.cbe.bi.ResourceInteractionRoleKey getResourceInteractionRoleKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.bi.ResourceInteractionRoleKey)getBusinessInteractionRoleKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ResourceInteractionRoleValue val = null;
            val = (ResourceInteractionRoleValue)super.clone();

            return val;
    }

    /**
     * Checks whether two ResourceInteractionRoleValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ResourceInteractionRoleValue object that has the arguments.
     * 
     * @param value the Object to compare with this ResourceInteractionRoleValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ResourceInteractionRoleValue)) {
            ResourceInteractionRoleValue argVal = (ResourceInteractionRoleValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
