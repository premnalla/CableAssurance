package ossj.qos.fmri;

import javax.oss.fm.monitor.AttributeValueChange;
import ossj.qos.util.Util;

/**
 * AttributeValueChangeImpl
 *
 * Represents changed attribute value including both old and new value.
 * It is used in a changed alarm event to report changed attribute value.
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public class AttributeValueChangeImpl implements AttributeValueChange
{

    // AttributeValueChangeImpl class attributes

    private String attributeName = null;
    private String attributeType = null;
    private Object attributeOldValue = null;
    private Object attributeNewValue = null;

    /**
     *  AttributeValueChangeImpl default constructor
     */
    public AttributeValueChangeImpl ( ) {
    }

    /**
     *  AttributeValueChangeImpl constructor
     */
    public AttributeValueChangeImpl ( String name, String type, Object oldVal, Object newVal )
    {
        attributeName = name;
        attributeType = type;
        attributeOldValue = oldVal;
        attributeNewValue = newVal;
    }

    /**
     * Returns attribute name which is needed for encoding the value.
     *
     * @return String - attribute name.
     * @see #setAttributeName
     */
    public String getAttributeName()
    {
        return attributeName;
    }

    /**
     * Returns attribute type which is needed for encoding the value.
     *
     * @return String - attribute name.
     * @see #setAttributeType
     */
    public String getAttributeType()
    {
        return attributeType;
    }

    /**
     * Returns old attribute value.
     *
     * @return Object - old value.
     * @see #setOldValue
     */
    public Object getOldValue()
    {
        return attributeOldValue;
    }

    /**
     * Returns new attribute value.
     *
     * @return Object -  new value.
     * @see #setNewValue
     */
    public Object getNewValue()
    {
        return attributeNewValue;
    }

    /**
     * Sets an attribute name which is needed for encoding the value.
     *
     * @param name A String representing the attribute name.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAttributeName
     */
    public void setAttributeName( String name )
    throws java.lang.IllegalArgumentException
    {
        if ( name == null ) { 
            throw new IllegalArgumentException( "Null attribute name entered.");
        }
        attributeName = name;
        return;
    }

    /**
     * Sets an attribute type which is needed for encoding the value.
     *
     * @param type A String representing the attribute type.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAttributeType
     */
    public void setAttributeType( String type )
    throws java.lang.IllegalArgumentException
    {
        if ( type == null ) {
            throw new IllegalArgumentException( "Null attribute type entered." );
        }
        attributeType = type;
        return;
    }

    /**
     * Sets an old attribute value.
     *
     * @param value An Object represenging the old value.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getOldValue
     */
    public void setOldValue( Object value )
    throws java.lang.IllegalArgumentException
    {
        attributeOldValue = value;
        return;
    }

    /**
     * Sets a new attribute value.
     *
     * @param value An Object representing the new value.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getNewValue
     */
    public void setNewValue( Object value )
    throws java.lang.IllegalArgumentException
    {
        attributeNewValue = value;
        return;
    }

    /**
     * Performs a deep copy of this attribute value.
     *
     * @return deep copy of this attribute value.
     */
    public Object clone() {
        AttributeValueChangeImpl obj = null;
        try {
            obj = (AttributeValueChangeImpl) super.clone();
            obj.attributeNewValue = (Object) Util.clone( attributeNewValue );
            obj.attributeOldValue = (Object) Util.clone( attributeOldValue );
        }
        catch ( CloneNotSupportedException cex ) {
            //System.out.println( "Problem cloning AttributeValueChangeImpl." );
        }
        return obj;
    }
    
    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in AttributeValueChangeImpl instance are equal to the 
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof AttributeValueChangeImpl ) {
            AttributeValueChangeImpl objAttrValChan = (AttributeValueChangeImpl)anObject;
            isEqual = ( Util.isEqual( attributeName, objAttrValChan.attributeName ) && 
                        Util.isEqual( attributeType, objAttrValChan.attributeType ) &&  
                        Util.isEqual( attributeOldValue, objAttrValChan.attributeOldValue ) &&
                        Util.isEqual( attributeNewValue, objAttrValChan.attributeNewValue ) );
        }
        return isEqual;    
    }

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(200);
        buffer.append(Util.rightJustify(30,"attributeName = ") + attributeName + "\n");
        buffer.append(Util.rightJustify(30,"attributeType = ") + attributeType + "\n");
        buffer.append(Util.rightJustify(30,"newValue = ") + Util.printObject( attributeNewValue ) );
        buffer.append(Util.rightJustify(30,"oldValue = ") + Util.printObject( attributeOldValue ) );
        return buffer.toString();
    }
}
