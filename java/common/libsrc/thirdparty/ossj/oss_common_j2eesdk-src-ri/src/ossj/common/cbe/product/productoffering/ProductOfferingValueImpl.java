/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductOfferingKey;
import javax.oss.cbe.datatypes.TimePeriod;
import ossj.common.cbe.datatypes.TimePeriodImpl;
import javax.oss.cbe.product.productoffering.ProductOfferingValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductOfferingValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.product.productoffering.ProductOfferingValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingValueImpl
extends ossj.common.cbe.EntityValueImpl
implements ProductOfferingValue
{ 

    /**
     * Constructs a new ProductOfferingValueImpl with empty attributes.
     * 
     */

    public ProductOfferingValueImpl() {
        super();
        setManagedEntityKeyInstance( new ProductOfferingKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        ProductOfferingValue.DESCRIPTION,
        ProductOfferingValue.NAME,
        ProductOfferingValue.STATE,
        ProductOfferingValue.STATUS,
        ProductOfferingValue.TIME_PERIOD
    };

    private static final String[] settableAttributeNames = {
        ProductOfferingValue.DESCRIPTION,
        ProductOfferingValue.NAME,
        ProductOfferingValue.STATE,
        ProductOfferingValue.STATUS,
        ProductOfferingValue.TIME_PERIOD
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (ProductOfferingValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (ProductOfferingValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(ProductOfferingValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.datatypes.TimePeriod makeTimePeriod(String type){
        if(type.equals("javax.oss.cbe.datatypes.TimePeriod") || type.equals("ossj.common.cbe.datatypes.TimePeriodImpl")) {
            return new TimePeriodImpl();
        } else {
            return null;
        }
    }

    public String[] attributeTypeForTimePeriod() {
        return attributeTypeFor("timePeriod");
    }

    public javax.oss.cbe.product.productoffering.ProductOfferingKey makeProductOfferingKey(){
        return (ProductOfferingKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "javax.oss.cbe.datatypes.TimePeriod";
        addAttributeTypes("timePeriod", list);
        list[0] = "java.lang.String";
        addAttributeTypes("description", list);
        list[0] = "java.lang.String";
        addAttributeTypes("state", list);
        list[0] = "java.lang.String";
        addAttributeTypes("status", list);
        list[0] = "java.lang.String";
        addAttributeTypes("name", list);
    }

    private java.lang.String _productofferingvalueimpl_description = null;
    private java.lang.String _productofferingvalueimpl_name = null;
    private java.lang.String _productofferingvalueimpl_state = null;
    private java.lang.String _productofferingvalueimpl_status = null;
    private javax.oss.cbe.datatypes.TimePeriod _productofferingvalueimpl_timePeriod = null;


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
        setDirtyAttribute(ProductOfferingValue.DESCRIPTION);
        _productofferingvalueimpl_description = value;
    }


    /**
     * Returns this ProductOfferingValueImpl's description
     * 
     * @return the description
     * 
    */

    public java.lang.String getDescription()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductOfferingValue.DESCRIPTION);
        return _productofferingvalueimpl_description;
    }

    /**
     * Changes the name to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new name for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductOfferingValue.NAME);
        _productofferingvalueimpl_name = value;
    }


    /**
     * Returns this ProductOfferingValueImpl's name
     * 
     * @return the name
     * 
    */

    public java.lang.String getName()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductOfferingValue.NAME);
        return _productofferingvalueimpl_name;
    }

    /**
     * Changes the productOfferingKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new productOfferingKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setProductOfferingKey(javax.oss.cbe.product.productoffering.ProductOfferingKey value)
    throws java.lang.IllegalArgumentException    {
        setEntityKey(value);
    }


    /**
     * Returns this ProductOfferingValueImpl's productOfferingKey
     * 
     * @return the productOfferingKey
     * 
    */

    public javax.oss.cbe.product.productoffering.ProductOfferingKey getProductOfferingKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.product.productoffering.ProductOfferingKey)getEntityKey();
    }

    /**
     * Changes the state to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new state for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductOfferingValue.STATE);
        _productofferingvalueimpl_state = value;
    }


    /**
     * Returns this ProductOfferingValueImpl's state
     * 
     * @return the state
     * 
    */

    public java.lang.String getState()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductOfferingValue.STATE);
        return _productofferingvalueimpl_state;
    }

    /**
     * Changes the status to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new status for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setStatus(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductOfferingValue.STATUS);
        _productofferingvalueimpl_status = value;
    }


    /**
     * Returns this ProductOfferingValueImpl's status
     * 
     * @return the status
     * 
    */

    public java.lang.String getStatus()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductOfferingValue.STATUS);
        return _productofferingvalueimpl_status;
    }

    /**
     * Changes the timePeriod to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new timePeriod for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setTimePeriod(javax.oss.cbe.datatypes.TimePeriod value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(ProductOfferingValue.TIME_PERIOD);
        _productofferingvalueimpl_timePeriod = value;
    }


    /**
     * Returns this ProductOfferingValueImpl's timePeriod
     * 
     * @return the timePeriod
     * 
    */

    public javax.oss.cbe.datatypes.TimePeriod getTimePeriod()
    throws java.lang.IllegalStateException {
        checkAttribute(ProductOfferingValue.TIME_PERIOD);
        return _productofferingvalueimpl_timePeriod;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductOfferingValue val = null;
            val = (ProductOfferingValue)super.clone();

            if( isPopulated(ProductOfferingValue.DESCRIPTION)) {
                if( this.getDescription()!=null) 
                    val.setDescription(((java.lang.String) new String( this.getDescription())));
                else
                    val.setDescription( null );
            }

            if( isPopulated(ProductOfferingValue.NAME)) {
                if( this.getName()!=null) 
                    val.setName(((java.lang.String) new String( this.getName())));
                else
                    val.setName( null );
            }

            if( isPopulated(ProductOfferingValue.STATE)) {
                if( this.getState()!=null) 
                    val.setState(((java.lang.String) new String( this.getState())));
                else
                    val.setState( null );
            }

            if( isPopulated(ProductOfferingValue.STATUS)) {
                if( this.getStatus()!=null) 
                    val.setStatus(((java.lang.String) new String( this.getStatus())));
                else
                    val.setStatus( null );
            }

            if( isPopulated(ProductOfferingValue.TIME_PERIOD)) {
                if( this.getTimePeriod()!=null) 
                    val.setTimePeriod((javax.oss.cbe.datatypes.TimePeriod)((javax.oss.cbe.datatypes.TimePeriod) this.getTimePeriod()).clone());
                else
                    val.setTimePeriod( null );
            }

            return val;
    }

    /**
     * Checks whether two ProductOfferingValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductOfferingValue object that has the arguments.
     * 
     * @param value the Object to compare with this ProductOfferingValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductOfferingValue)) {
            ProductOfferingValue argVal = (ProductOfferingValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
