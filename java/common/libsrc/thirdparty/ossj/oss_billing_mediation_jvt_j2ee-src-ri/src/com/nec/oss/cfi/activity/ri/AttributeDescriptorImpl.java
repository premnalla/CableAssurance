
package com.nec.oss.cfi.activity.ri;

/**
 * Spec imports.
 */
import javax.oss.Serializer;
import javax.oss.cfi.activity.AttributeDescriptor;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * An implementation of Attribute Descriptor interface.
 * 
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 *
 */
public class AttributeDescriptorImpl
implements AttributeDescriptor
{

    private boolean isArray;
    private int type = -1;
    private String name;
    private String typeName;

    private static String[] noSupportedSerializerTypes = new String[0];


    /**
     * Constructor - empty
     */
    public AttributeDescriptorImpl()
    {
    }

    /**
     * Deep copies the instance. May return null, if
     * {@link CloneNotSupportedException} occurrs.
     * @return Deep copy of this object
     */
    public Object clone()
    {
        Object clone = null;
        try
        {
            clone = super.clone();
        }
        catch(CloneNotSupportedException e)
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
        }
        return clone;
    }

    /**
     * Deep equality checking of the instance.
     * @return boolean flag indicating if the objects are equals
     */
    public boolean equals(Object other1)
    {
        if(other1 == this)
        {
            return true;
        }

        if(!(other1 instanceof AttributeDescriptorImpl))
        {
            return false;
        }

        AttributeDescriptorImpl other = (AttributeDescriptorImpl) other1;

        if(other.hashCode() == hashCode())
        {
            if ( ( type == other.type ) &&
                 ( isArray == other.isArray ) )
            {
                if (name != null)
                {
                    if  (!(name.equals(other.name)))
                    {
                        return false;
                    }
                }
                else if(name != other.name)
                {
                    return false;
                }

                if(typeName != null)
                {
                    
                    if  (!(typeName.equals(other.typeName)))
                    {
                        return false;
                    }
                }
                else if(typeName != other.typeName)
                {
                    return false;
                }
                         
            }
            else
            {
                return false;
            }
        }
		else
		{
			return false;
		}
        
        return true;
    }

    /**
     * hashCode value of the object
     *
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode = -1;
        
        if(name != null)
        {
            hashCode = name.hashCode();
        }
        
        if(typeName != null)
        {
            hashCode ^= typeName.hashCode();
        }
        
        return hashCode;
    
    }

    /**
     * Get a string representation of the object
     *
     * @return string representation of the object 
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(50);

        sb.append("name: " + name + " | type: " + type +
                  "| typeName: " + typeName +
                  "| isArray: " + isArray);
        return sb.toString();
    }
    

    /**
     * Get the name for this attribute
     *
     * @return  the name for this attribute
     * @exception java.lang.IllegalStateException Thrown if name is not set
     */
    public String getName()
        throws java.lang.IllegalStateException
    {
        if ( (name == null) ||
             (name.length() == 0) )
        {
            throw new java.lang.IllegalStateException(
                "Name attribute is not yet set");
        }
        
        return name;
    }

    /**
     * Get the type for this attribute
     *
     * @param  the type for this attribute
     * @exception java.lang.IllegalStateException Thrown if type isn't set
     */
    public int getType()
        throws java.lang.IllegalStateException
    {
        if (type == -1)
        {
            throw new java.lang.IllegalStateException(
                "Type attribute is not yet set");
        }
        return type;
    }

    /**
     * Get the flag indicating if this is an array.
     * <p>
     * @return  flag indicating if this is an array.
     */
    public boolean isArray()
    {
        return isArray;
    }

    /**
     * Set the name for this attribute
     *
     * @param  name the name for this attribute
     * @exception java.lang.IllegalArgumentException Thrown if name is null
     */
    public void setName(String name)
        throws java.lang.IllegalArgumentException
    {
        if(name == null)
            throw new java.lang.IllegalArgumentException();

        this.name = name;
    }

    /**
     * Set the type for this attribute
     *
     * @param  type the type for this attribute
     * @exception java.lang.IllegalArgumentException Thrown if type isn't valid
     */
    public void setType(int type)
        throws java.lang.IllegalArgumentException
    {
        if (
            (type != this.OBJECT )&&
            (type != this.INTEGER )&&
            (type != this.REAL) &&
            (type != this.STRING) &&
            (type != this.BOOLEAN )&&
            (type != this.BYTE) &&
            (type != this.LONG) &&
            (type != this.SHORT) &&
            (type != this.DATE) &&
            (type != this.CHAR) &&
            (type != this.FLOAT) &&
            (type != this.DOUBLE) &&
            (type != this.TYPE_DESC) 
               )
        {
            throw new java.lang.IllegalArgumentException();
        }
        
        this.type = type;
    }

    /**
     * Set the flag indicating if this is an array.
     * <p>
     * @param isArray  flag indicating if this is an array.
     */
    public void setIsArray(boolean isArray)
    {
        this.isArray = isArray;
    }

    /**
     * Get the actual java type name, in case this is a complex
     * attribute.
     * <p>
     * Pre-requsite: Type attribute must have been set using setType.
     * @return Java type name for this attribute
     * @exception java.lang.IllegalStateException Thrown if this type is not set
     */
    public String getTypeName()
        throws java.lang.IllegalStateException
    {
        if (
            (getType() != this.OBJECT) &&
            (getType() != this.TYPE_DESC)
               )
        {
            throw new java.lang.IllegalStateException(
                "Typename attribute is not valid for this type");
            
        }
        if(typeName == null)
        {
            throw new java.lang.IllegalStateException(
                "Typename attribute is not yet set");
        }
        
        return typeName;
    }
    
    /**
     * Set the actual java type name, in case this is a complex
     * attribute. This should be set when the type is of "OBJECT".
     * The name string should be the one returned by class.getName(),
     * so that new instances can be constructed on the fly.
     * <p>
     * Pre-requsite: Type attribute must have been set using setType.
     * @param attributeTypeName Name for Java type
     * @exception java.lang.IllegalArgumentException Thrown if the input name
     * is not valid
     */
    public void setTypeName(String attributeTypeName)
        throws java.lang.IllegalArgumentException,
            java.lang.IllegalStateException
    {
        if (
            (getType() != this.OBJECT) &&
            (getType() != this.TYPE_DESC)
               )
        {
            throw new java.lang.IllegalStateException(
                "Typename attribute is not valid for this type");
            
        }

        if(attributeTypeName == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Typename parameter is not valid");
        }

        this.typeName = attributeTypeName;
        
    }

    /**
     * Returns an empty String Array. Always.
     * Since, it is not possible to generalize the serilization for each API,
     * each API is expected to
     * derive sub class this, to provide implementation for
     * methods {@link javax.oss.SerializerFactory#getSupportedSerializerTypes},
     * and {@link javax.oss.SerializerFactory#makeSerializer}.
     * In this implementation, these methods work as follows:
     * <UL>
     * <LI> {@link javax.oss.SerializerFactory#getSupportedSerializerTypes}:
     * Always returns a ZERO length string array.
     * <LI> {@link javax.oss.SerializerFactory#makeSerializer}:
     * Always throws an {@link IllegalArgumentException}.
     * </UL>
     * @return Empty array for all invocations
     */
    public String[] getSupportedSerializerTypes()
    {
        return noSupportedSerializerTypes;
    }

    /**
     * Always throws IllegalArgumentException.
     * Since, it is not possible to generalize the serilization for each API,
     * each API is expected to
     * derive sub class this, to provide implementation for
     * methods {@link javax.oss.SerializerFactory#getSupportedSerializerTypes},
     * and {@link javax.oss.SerializerFactory#makeSerializer}.
     * In this implementation, these methods work as follows:
     * <UL>
     * <LI> {@link javax.oss.SerializerFactory#getSupportedSerializerTypes}:
     * Always returns a ZERO length string array.
     * <LI> {@link javax.oss.SerializerFactory#makeSerializer}:
     * Always throws an {@link IllegalArgumentException}.
     * </UL>
     * @return New instance of Serializer
     * @exception java.lang.IllegalArgumentException Thrown every time
     */
    public Serializer makeSerializer( String serializerType )
        throws java.lang.IllegalArgumentException
    {
        throw new java.lang.IllegalArgumentException(
            "makeSerializer() is not supported in the " +
            AttributeDescriptor.class.getName() +
            "Class.");
    }
}
