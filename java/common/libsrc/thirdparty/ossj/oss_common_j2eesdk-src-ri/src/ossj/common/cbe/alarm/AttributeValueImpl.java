/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import java.lang.reflect.Method;
import javax.oss.cbe.alarm.AttributeValue;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AttributeValue</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.AttributeValue
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AttributeValueImpl
implements AttributeValue
{ 

    /**
     * Constructs a new AttributeValueImpl with empty attributes.
     * 
     */

    public AttributeValueImpl() {
    }

    private java.lang.String _attributevalueimpl_attributeName = null;
    private java.lang.String _attributevalueimpl_attributeType = null;
    private java.lang.Object _attributevalueimpl_value = null;


    /**
     * Changes the attributeName to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new attributeName for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAttributeName(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _attributevalueimpl_attributeName = value;
    }


    /**
     * Returns this AttributeValueImpl's attributeName
     * 
     * @return the attributeName
     * 
    */

    public java.lang.String getAttributeName() {
        return _attributevalueimpl_attributeName;
    }

    /**
     * Changes the attributeType to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new attributeType for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setAttributeType(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _attributevalueimpl_attributeType = value;
    }


    /**
     * Returns this AttributeValueImpl's attributeType
     * 
     * @return the attributeType
     * 
    */

    public java.lang.String getAttributeType() {
        return _attributevalueimpl_attributeType;
    }

    /**
     * Changes the value to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new value for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setValue(java.lang.Object value)
    throws java.lang.IllegalArgumentException    {
        _attributevalueimpl_value = value;
    }


    /**
     * Returns this AttributeValueImpl's value
     * 
     * @return the value
     * 
    */

    public java.lang.Object getValue() {
        return _attributevalueimpl_value;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AttributeValue val = null;
        try { 
            val = (AttributeValue)super.clone();

            if( this.getValue()!=null) 
                val.setValue((java.lang.Object)clone(getValue()));
            else
                val.setValue( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("AttributeValueImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }
    
    private Object clone(Object obj) {
        Object clone = obj;
        if ((obj != null) && obj instanceof Cloneable) {
            try {
                Method method = obj.getClass().getMethod("clone",(Class[]) null);
                clone = method.invoke(obj, (Object[])null);
            } catch (Exception e) {
                return null;
            }
        }
        return clone;
    }

    /**
     * Checks whether two AttributeValue are equal.
     * The result is true if and only if the argument is not null 
     * and is an AttributeValue object that has the arguments.
     * 
     * @param value the Object to compare with this AttributeValue
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AttributeValue)) {
            AttributeValue argVal = (AttributeValue) value;
            if( !Utils.compareAttributes( getAttributeName(), argVal.getAttributeName())) { return false; } 
            if( !Utils.compareAttributes( getAttributeType(), argVal.getAttributeType())) { return false; } 
            if( !Utils.compareAttributes( getValue(), argVal.getValue())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
