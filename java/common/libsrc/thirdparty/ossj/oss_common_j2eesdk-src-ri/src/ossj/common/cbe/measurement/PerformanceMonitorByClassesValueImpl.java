/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.measurement;

import javax.oss.cbe.measurement.PerformanceAttributeDescriptor;
import javax.oss.cbe.measurement.PerformanceMonitorByClassesKey;
import javax.oss.cbe.measurement.PerformanceMonitorByClassesValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.measurement.PerformanceMonitorByClassesValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.cbe.measurement.PerformanceMonitorByClassesValue
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PerformanceMonitorByClassesValueImpl
extends ossj.common.cbe.measurement.PerformanceMonitorValueImpl
implements PerformanceMonitorByClassesValue
{ 

    /**
     * Constructs a new PerformanceMonitorByClassesValueImpl with empty attributes.
     * 
     */

    public PerformanceMonitorByClassesValueImpl() {
        super();
        setManagedEntityKeyInstance( new PerformanceMonitorByClassesKeyImpl());
        initAttributeTypes();
    }

    private static final String[] attributeNames = {
        PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES,
        PerformanceMonitorByClassesValue.OBSERVED_OBJECT_CLASSES,
        PerformanceMonitorByClassesValue.SCOPE
    };

    private static final String[] settableAttributeNames = {
        PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES,
        PerformanceMonitorByClassesValue.OBSERVED_OBJECT_CLASSES,
        PerformanceMonitorByClassesValue.SCOPE
    };

    //
    // Configuration of AttributeManager and AttributeAccess
    //
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        if (PerformanceMonitorByClassesValueImpl.attributeNames != null) {
            anAttributeManager.addAttributes(this.attributeNames);
            super.addAttributesTo(anAttributeManager);
        }
    }

    protected void configureAttributes(AttributeManager anAttributeManager) {
        if (PerformanceMonitorByClassesValueImpl.settableAttributeNames != null) {
            anAttributeManager.setSettableAttributes(PerformanceMonitorByClassesValueImpl.settableAttributeNames);
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
    
    public javax.oss.cbe.measurement.PerformanceMonitorByClassesKey makePerformanceMonitorByClassesKey(){
        return (PerformanceMonitorByClassesKey) makeManagedEntityKey();
    }

    public String[] attributeTypeForMeasurementAttributes() {
        return attributeTypeFor("measurementAttributes");
    }

    public javax.oss.cbe.measurement.PerformanceAttributeDescriptor[] makePerformanceAttributeDescriptors(int nb, String type){
        if(type.equals("javax.oss.cbe.measurement.PerformanceAttributeDescriptor") || type.equals("ossj.common.cbe.measurement.PerformanceAttributeDescriptorImpl")) {
            return new PerformanceAttributeDescriptorImpl[nb];
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
        list[0] = "[Ljavax.oss.cbe.measurement.PerformanceAttributeDescriptor;";
        addAttributeTypes("measurementAttributes", list);
        list[0] = "[Ljava.lang.String;";
        addAttributeTypes("observedObjectClasses", list);
        list[0] = "java.lang.String";
        addAttributeTypes("scope", list);
    }

    private javax.oss.cbe.measurement.PerformanceAttributeDescriptor[] _performancemonitorbyclassesvalueimpl_measurementAttributes = null;
    private java.lang.String[] _performancemonitorbyclassesvalueimpl_observedObjectClasses = null;
    private java.lang.String _performancemonitorbyclassesvalueimpl_scope = null;


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
        setDirtyAttribute(PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES);
        _performancemonitorbyclassesvalueimpl_measurementAttributes = value;
    }


    /**
     * Returns this PerformanceMonitorByClassesValueImpl's measurementAttributes
     * 
     * @return the measurementAttributes
     * 
    */

    public javax.oss.cbe.measurement.PerformanceAttributeDescriptor[] getMeasurementAttributes()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES);
        return _performancemonitorbyclassesvalueimpl_measurementAttributes;
    }

    /**
     * Changes the observedObjectClasses to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new observedObjectClasses for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setObservedObjectClasses(java.lang.String[] value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorByClassesValue.OBSERVED_OBJECT_CLASSES);
        _performancemonitorbyclassesvalueimpl_observedObjectClasses = value;
    }


    /**
     * Returns this PerformanceMonitorByClassesValueImpl's observedObjectClasses
     * 
     * @return the observedObjectClasses
     * 
    */

    public java.lang.String[] getObservedObjectClasses()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorByClassesValue.OBSERVED_OBJECT_CLASSES);
        return _performancemonitorbyclassesvalueimpl_observedObjectClasses;
    }

    /**
     * Changes the performanceMonitorByClassesKey to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new performanceMonitorByClassesKey for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setPerformanceMonitorByClassesKey(javax.oss.cbe.measurement.PerformanceMonitorByClassesKey value)
    throws java.lang.IllegalArgumentException    {
        setPerformanceMonitorKey(value);
    }


    /**
     * Returns this PerformanceMonitorByClassesValueImpl's performanceMonitorByClassesKey
     * 
     * @return the performanceMonitorByClassesKey
     * 
    */

    public javax.oss.cbe.measurement.PerformanceMonitorByClassesKey getPerformanceMonitorByClassesKey()
    throws java.lang.IllegalStateException {
        return (javax.oss.cbe.measurement.PerformanceMonitorByClassesKey)getPerformanceMonitorKey();
    }

    /**
     * Changes the scope to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new scope for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setScope(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        setDirtyAttribute(PerformanceMonitorByClassesValue.SCOPE);
        _performancemonitorbyclassesvalueimpl_scope = value;
    }


    /**
     * Returns this PerformanceMonitorByClassesValueImpl's scope
     * 
     * @return the scope
     * 
    */

    public java.lang.String getScope()
    throws java.lang.IllegalStateException {
        checkAttribute(PerformanceMonitorByClassesValue.SCOPE);
        return _performancemonitorbyclassesvalueimpl_scope;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        PerformanceMonitorByClassesValue val = null;
            val = (PerformanceMonitorByClassesValue)super.clone();

            if( isPopulated(PerformanceMonitorByClassesValue.MEASUREMENT_ATTRIBUTES)) {
                if( this.getMeasurementAttributes()!=null) 
                    val.setMeasurementAttributes((javax.oss.cbe.measurement.PerformanceAttributeDescriptor[])((javax.oss.cbe.measurement.PerformanceAttributeDescriptor[]) this.getMeasurementAttributes()).clone());
                else
                    val.setMeasurementAttributes( null );
            }

            if( isPopulated(PerformanceMonitorByClassesValue.OBSERVED_OBJECT_CLASSES)) {
                if( this.getObservedObjectClasses()!=null) 
                    val.setObservedObjectClasses((java.lang.String[])((java.lang.String[]) this.getObservedObjectClasses()).clone());
                else
                    val.setObservedObjectClasses( null );
            }

            if( isPopulated(PerformanceMonitorByClassesValue.SCOPE)) {
                if( this.getScope()!=null) 
                    val.setScope(((java.lang.String) new String( this.getScope())));
                else
                    val.setScope( null );
            }

            return val;
    }

    /**
     * Checks whether two PerformanceMonitorByClassesValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an PerformanceMonitorByClassesValue object that has the arguments.
     * 
     * @param value the Object to compare with this PerformanceMonitorByClassesValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof PerformanceMonitorByClassesValue)) {
            PerformanceMonitorByClassesValue argVal = (PerformanceMonitorByClassesValue) value;
            return Utils.compareAttributeAccess( this, argVal );
        }
        return false;
    }

}
