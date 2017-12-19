/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import java.lang.reflect.Method;
import javax.oss.cbe.alarm.AttributeValueChange;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AttributeValueChange</CODE> interface.  
 * 
 * @see javax.oss.cbe.alarm.AttributeValueChange
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AttributeValueChangeImpl
implements AttributeValueChange
{ 

    /**
     * Constructs a new AttributeValueChangeImpl with empty attributes.
     * 
     */

    public AttributeValueChangeImpl() {
    }

    private java.lang.String _attributevaluechangeimpl_attributeName = null;
    private java.lang.String _attributevaluechangeimpl_attributeType = null;
    private java.lang.Object _attributevaluechangeimpl_newValue = null;
    private java.lang.Object _attributevaluechangeimpl_oldValue = null;


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
        _attributevaluechangeimpl_attributeName = value;
    }


    /**
     * Returns this AttributeValueChangeImpl's attributeName
     * 
     * @return the attributeName
     * 
    */

    public java.lang.String getAttributeName() {
        return _attributevaluechangeimpl_attributeName;
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
        _attributevaluechangeimpl_attributeType = value;
    }


    /**
     * Returns this AttributeValueChangeImpl's attributeType
     * 
     * @return the attributeType
     * 
    */

    public java.lang.String getAttributeType() {
        return _attributevaluechangeimpl_attributeType;
    }

    /**
     * Changes the newValue to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new newValue for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setNewValue(java.lang.Object value)
    throws java.lang.IllegalArgumentException    {
        _attributevaluechangeimpl_newValue = value;
    }


    /**
     * Returns this AttributeValueChangeImpl's newValue
     * 
     * @return the newValue
     * 
    */

    public java.lang.Object getNewValue() {
        return _attributevaluechangeimpl_newValue;
    }

    /**
     * Changes the oldValue to be equal to the given argument.
     * 
     * This method support a null argument.
     * 
     * @param value the new oldValue for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
    */

    public void setOldValue(java.lang.Object value)
    throws java.lang.IllegalArgumentException    {
        _attributevaluechangeimpl_oldValue = value;
    }


    /**
     * Returns this AttributeValueChangeImpl's oldValue
     * 
     * @return the oldValue
     * 
    */

    public java.lang.Object getOldValue() {
        return _attributevaluechangeimpl_oldValue;
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        AttributeValueChange val = null;
        try { 
            val = (AttributeValueChange)super.clone();

            if( this.getNewValue()!=null) 
                val.setNewValue((java.lang.Object)clone(getNewValue()));
            else
                val.setNewValue( null );

            if( this.getOldValue()!=null) 
                val.setOldValue((java.lang.Object)clone(getOldValue()));
            else
                val.setOldValue( null );

            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("AttributeValueChangeImpl: Unable to clone this object: "+ ex.getMessage());
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
     * Checks whether two AttributeValueChange are equal.
     * The result is true if and only if the argument is not null 
     * and is an AttributeValueChange object that has the arguments.
     * 
     * @param value the Object to compare with this AttributeValueChange
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof AttributeValueChange)) {
            AttributeValueChange argVal = (AttributeValueChange) value;
            if( !Utils.compareAttributes( getAttributeName(), argVal.getAttributeName())) { return false; } 
            if( !Utils.compareAttributes( getAttributeType(), argVal.getAttributeType())) { return false; } 
            if( !Utils.compareAttributes( getNewValue(), argVal.getNewValue())) { return false; } 
            if( !Utils.compareAttributes( getOldValue(), argVal.getOldValue())) { return false; } 

            return true;
        } else {
            return super.equals(value);
        }
    }

}
