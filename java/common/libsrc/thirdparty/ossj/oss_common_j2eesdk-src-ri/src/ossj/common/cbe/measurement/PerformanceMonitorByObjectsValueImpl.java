/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey;
import javax.oss.cbe.measurement.PerformanceAttributeDescriptor;
import javax.oss.cbe.measurement.PerformanceMonitorByObjectsValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByObjectsValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByObjectsValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByObjectsValueImpl
extends ossj.common.cbe.measurement.PerformanceMonitorValueImpl
implements PerformanceMonitorByObjectsValue
{ 

    /**
     * Constructs a new PerformanceMonitorByObjectsValueImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByObjectsValueImpl() {
        super();
        setManagedEntityKeyInstance( new PerformanceMonitorByObjectsKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES,
        PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS,
    };

    private static final String[] settableAttributeNames = {
        PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES,
        PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS,
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (PerformanceMonitorByObjectsValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (PerformanceMonitorByObjectsValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(PerformanceMonitorByObjectsValueImpl.settableAttributeNames);
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

    public String[] attributeTypeForMeasurementAttributes() {
        return attributeTypeFor("measurementAttributes");
    }

    
    //==================================================================
    // FACTORIES AND ATTRIBUTE TYPE FOR ================================
    //==================================================================
    
    public javax.oss.cbe.measurement.PerformanceAttributeDescriptor[] makePerformanceAttributeDescriptors(int nb, String type){
        if(type.equals("javax.oss.cbe.measurement.PerformanceAttributeDescriptor") || type.equals("ossj.common.cbe.measurement.PerformanceAttributeDescriptorImpl")) {
            return new PerformanceAttributeDescriptorImpl[nb];
        } else {
            return null;
        }
    }

    public javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey makePerformanceMonitorByObjectsKey(){
        return (PerformanceMonitorByObjectsKey) makeManagedEntityKey();
    }

    /**
     * Init the Map Attribute Name/Attribute Type
     * Init the enumeration Map
     */
    protected void initAttributeTypes(){
        String[] list = new String[1];
        list[0] = "[Ljavax.oss.cbe.measurement.PerformanceAttributeDescriptor;";
        addAttributeTypes("measurementAttributes", list);
        list[0] = "[Ljava.lang.String;";
        addAttributeTypes("observedObjects", list);
    }

    private javax.oss.cbe.measurement.PerformanceAttributeDescriptor[] _performancemonitorbyobjectsvalueimpl_measurementAttributes = null;
    private java.lang.String[] _performancemonitorbyobjectsvalueimpl_observedObjects = null;


    /**
     * Changes the measurementAttributes to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new measurementAttributes for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setMeasurementAttributes(javax.oss.cbe.measurement.PerformanceAttributeDescriptor[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES);
        _performancemonitorbyobjectsvalueimpl_measurementAttributes = value;
    }


    /**
     * Returns this PerformanceMonitorByObjectsValueImpl's measurementAttributes
     * 
     * @return the measurementAttributes
     * 
    */

    public javax.oss.cbe.measurement.PerformanceAttributeDescriptor[] getMeasurementAttributes()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES);
        return _performancemonitorbyobjectsvalueimpl_measurementAttributes;
    }

    /**
     * Changes the observedObjects to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new observedObjects for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setObservedObjects(java.lang.String[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS);
        _performancemonitorbyobjectsvalueimpl_observedObjects = value;
    }


    /**
     * Returns this PerformanceMonitorByObjectsValueImpl's observedObjects
     * 
     * @return the observedObjects
     * 
    */

    public java.lang.String[] getObservedObjects()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS);
        return _performancemonitorbyobjectsvalueimpl_observedObjects;
    }

    /**
     * Changes the performanceMonitorByObjectsKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new performanceMonitorByObjectsKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPerformanceMonitorByObjectsKey(javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey value)
    throws java.lang.IllegalArgumentException    {
        setPerformanceMonitorKey(value);
    }


    /**
     * Returns this PerformanceMonitorByObjectsValueImpl's performanceMonitorByObjectsKey
     * 
     * @return the performanceMonitorByObjectsKey
     * 
    */

    public javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey getPerformanceMonitorByObjectsKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.measurement.PerformanceMonitorByObjectsKey)getPerformanceMonitorKey();
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PerformanceMonitorByObjectsValue val = null;
            val = (PerformanceMonitorByObjectsValue)super.clone();

            if( isPopulated(PerformanceMonitorByObjectsValue.MEASUREMENT_ATTRIBUTES)) {
                if( this.getMeasurementAttributes()!=null) 
                    val.setMeasurementAttributes((javax.oss.cbe.measurement.PerformanceAttributeDescriptor[])((javax.oss.cbe.measurement.PerformanceAttributeDescriptor[]) this.getMeasurementAttributes()).clone());
                else
                    val.setMeasurementAttributes( null );
            }

            if( isPopulated(PerformanceMonitorByObjectsValue.OBSERVED_OBJECTS)) {
                if( this.getObservedObjects()!=null) 
                    val.setObservedObjects((java.lang.String[])((java.lang.String[]) this.getObservedObjects()).clone());
                else
                    val.setObservedObjects( null );
            }

            return val;
    }

    /**
     * Checks whether two PerformanceMonitorByObjectsValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an PerformanceMonitorByObjectsValue object that has the arguments.
     * 
     * @param value the Object to compare with this PerformanceMonitorByObjectsValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PerformanceMonitorByObjectsValue)) {
            PerformanceMonitorByObjectsValue argVal = (PerformanceMonitorByObjectsValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
