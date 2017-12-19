package ossj.qos.fmri;

import javax.oss.fm.monitor.AttributeValue;
import ossj.qos.util.Util;

/**
 * AttributeValueImpl 
 *
 * Represents an attribute value implementation.
 *
 * @author Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */

public class AttributeValueImpl implements AttributeValue
{

    private String attributeName = null;
    private String attributeType = null;
    private Object attributeValue = null;

    /**
     * AttributeValueImpl default constructor.
     */
    public AttributeValueImpl() {
    }
    
    /**
     * AttributeValueImpl constructor.
     */
    public AttributeValueImpl( String name, String type, Object value ) {
        attributeName = name;
        attributeType = type;
        attributeValue = value;
    }

     /**
     * Returns the attribute name which is needed for encoding value.
     *
     * @return <code>String</code> - Attribute name.
     * @see #setAttributeName
     */    
    public String getAttributeName() {
        return attributeName;
    }

   /**
     * Returns the attribute type which is needed for encoding value.
     * 
     * @return <code>String</code> - Attribute type.
     * @see #setAttributeType
     */
    public String getAttributeType() {
        return attributeType;
    }

   /**
     * Returns the attribute value.
     * 
     * @return <code>Object</code> - Attribute value.
     * @see #setValue
     */
    public Object getValue() {
        return attributeValue;
    }
    
    /**
     * Sets the attribute name which is needed for encoding value.
     * 
     * @param name A String that represents the attribute name.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAttributeName
     */
    public void setAttributeName( String name ) 
      throws java.lang.IllegalArgumentException  {
        if ( name == null ) {
            throw new IllegalArgumentException ( "Null attribute name entered." );
        }
        attributeName = name;
        return;
    }
    
    /**
     * Sets the attribute type which is needed for encoding value.
     * 
     * @param type A String that represents the attribute type.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getAttributeType
     */
    public void setAttributeType( String type )
      throws java.lang.IllegalArgumentException {
        if ( type == null ) {
            throw new IllegalArgumentException ( "Null attribute type entered." );
        }
        attributeType = type;
        return;
    }
    
    /**
     * Sets the attribute value.
     * 
     * @param value An Object that represents the attribute value.
     * @exception java.lang.IllegalArgumentException Is thrown to report that
     * a bad argument was provided to the method.
     * @see #getValue
     */
    public void setValue( Object value ) 
      throws java.lang.IllegalArgumentException {
        attributeValue = value;
        return;
    }
    
    /**
     * Performs a deep copy of this attribute value.
     * 
     * @return deep copy of this attribute value.
     */
    public Object clone() {
        AttributeValueImpl obj = null;
        try {
            obj = (AttributeValueImpl) super.clone();
            obj.attributeValue = (Object) Util.clone( attributeValue );
        }
        catch ( CloneNotSupportedException cex ) {
            // this should never happen 
            //System.out.println( "Problem cloning AttributeValue." );
        }
        return obj;
    }
    
    /**
     * Returns a boolean that indicates whether the contents of the
     * passed in AttributeValueImpl instance are equal to the 
     * contents of this instance.
     *
     * @return boolean - indicating if the instances are equal.
     */
    public boolean equals ( Object anObject ) {
        boolean isEqual = false;
        if ( anObject instanceof AttributeValueImpl ) {
            AttributeValueImpl objAttrVal = (AttributeValueImpl)anObject;
            isEqual = ( Util.isEqual( attributeName, objAttrVal.attributeName ) && 
                        Util.isEqual( attributeType, objAttrVal.attributeType ) &&  
                        Util.isEqual( attributeValue, objAttrVal.attributeValue ) );
        }
        return isEqual;    
    }

    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(200);
        buffer.append(Util.rightJustify(30,"attributeName = ") + attributeName + "\n");
        buffer.append(Util.rightJustify(30,"attributeType = ") + attributeType + "\n");
        buffer.append(Util.rightJustify(30,"attributeValue = ") + Util.printObject( attributeValue ) );
        return buffer.toString();
    }
}
