package com.nec.oss.ipb.type.ri;


/**
 * Title:        JSR130 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Standard imports.
 */
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Spec. imports.
 */
import javax.oss.ipb.type.UsageRecValue;


/**
 * Implementaion of UsageRecValue interface.
 * The OSS/J Usage Record is NOT a Managed Entity type. There is
 * no <CODE>primaryKey</CODE> for this <CODE>UsageRecValue</CODE>.
 *
 * <p>
 * A Usage Record value is just a data record. It can be decoded with
 * the {@link javax.oss.cfi.activity.RecordDescriptor} type information.
 * This Type Description is provided by a
 * <CODE>UsageRecValueIterator</CODE> instance.
 * <p>
 * <p> This interface also provides standardized get and set methods to
 * primitive type attributes (fields) and composite type attributes (by
 * means of <CODE>Object</CODE> ). This helps in standardizing access to
 * member attributes of a Usage Record.
 *
 * @see javax.oss.cfi.activity.RecordDescriptor
 * @see UsageRecValueIterator
 *
 */
public class UsageRecValueImpl
implements UsageRecValue
{

	// Default UsageRec data container
	HashMap usageRecMap = null;

    /**
     * Default Constructor. Creates a HashMap to manages the usage data
     */
    public UsageRecValueImpl()
    {
		// Create the usageRecMap
		usageRecMap = new HashMap();
    }

    /**
     * Get the specified attribute, performing data checks
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> as a Serializable Object
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 */
    public Serializable getUsageAttributeValue (String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		 Object attributeVal=null;

		// First check the attribute name
		if(attributeName == null)
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName is null");
		}

		// Next check that the map contains this attribute
		if (!usageRecMap.containsKey(attributeName))
		{
        	throw new java.lang.IllegalStateException(
				"attributeName " + attributeName + " is missing");
		}

		// Retrieve the attribute value
		attributeVal=usageRecMap.get(attributeName);

		// Also check that this is a Serializable object
		if ((attributeVal != null) &&
				(!(attributeVal instanceof Serializable)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeVal isn't Serializable");
		}

		return ((Serializable)attributeVal);
	}
    
    /**
     * Get the specified attribute, whose type is a composite object.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> as a Serializable Object
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 */
	public Serializable getObjectValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		// Get the attribute value
		return(getUsageAttributeValue(attributeName));
	}

	/**
	 * Get the specified attribute's boolean value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> boolean value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 */
	public boolean getBooleanValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Boolean object
		if ((retValue != null) &&
			(!(retValue instanceof Boolean)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Boolean");
		}

		return(((Boolean)retValue).booleanValue());
	}

	/**
	 * Get the specified attribute's byte value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> byte value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 */
	public byte getByteValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Byte object
		if ((retValue != null) &&
			(!(retValue instanceof Byte)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Byte");
		}

		return(((Byte)retValue).byteValue());
	}

	/**
	 * Get the specified attribute's short value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> short value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 */
	public short getShortValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Short object
		if ((retValue != null) &&
			(!(retValue instanceof Short)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Short");
		}

		return(((Short)retValue).shortValue());
	}

	/**
	 * Get the specified attribute's char value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> char value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 */
	public char getCharValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Character object
		if ((retValue != null) &&
			(!(retValue instanceof Character)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Character");
		}

		return(((Character)retValue).charValue());
	}

	/**
	 * Get the specified attribute's int value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> int value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 */
	public int getIntValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Integer object
		if ((retValue != null) &&
			(!(retValue instanceof Integer)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't an Integer");
		}

		return(((Integer)retValue).intValue());
	}

	/**
	 * Get the specified attribute's long value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> long value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 *
	 */
	public long getLongValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Long object
		if ((retValue != null) &&
			(!(retValue instanceof Long)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Long");
		}

		return(((Long)retValue).longValue());
	}

	/**
	 * Get the specified attribute's float value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> float value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 *
	 */
	public float getFloatValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Float object
		if ((retValue != null) &&
			(!(retValue instanceof Float)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Float");
		}

		return(((Float)retValue).floatValue());
	}

	/**
	 * Get the specified attribute's double value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> double value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 *
	 */
	public double getDoubleValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Double object
		if ((retValue != null) &&
			(!(retValue instanceof Double)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Double");
		}

		return(((Double)retValue).doubleValue());
	}

	/**
	 * Get the specified attribute's String value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> String value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 * 
	 */
	public String getStringValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the string value of the attribute 
		return(getUsageAttributeValue(attributeName).toString());
	}

	/**
	 * Get the specified attribute's Date value.
     *
     * @param attributeName The name of the attribute.
     *
	 * @return The <CODE>attributeName</CODE> Date value
     *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid.
     *
	 *
	 */
	public Date getTimestampValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getUsageAttributeValue(attributeName);

		// Verify that this is a Date object
		if ((retValue != null) &&
			(!(retValue instanceof Date)))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Date");
		}

		return((Date)retValue);
	}

	/**
	 * Methods for getting array type values.
	 * Only referenes to direct internal fields are returned, and
	 * the caller must work with the any concurrent update issues.
	 */

	/**
	 * Get the specified attribute's array of Serializable Object values.
     * <p>
	 * Only referenes to array elements are returned, and
	 * the caller must work with the any concurrent update issues.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of Serializable Object values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 */
	public Serializable[] getObjectArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable attrValue=null;		// Retrieved value

		// Get the attribute value
		attrValue=getUsageAttributeValue(attributeName);

		// Verify that this is an object array
		if ((attrValue != null) &&
			(!attrValue.getClass().isArray()))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't an array");
		}

		return((Serializable[])attrValue);
	}

	/**
	 * Get the specified attribute's array of boolean values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of boolean values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 */
	public boolean[] getBooleanArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a boolean array
		if ((retValue != null) &&
			(!(retValue instanceof boolean[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a boolean[]");
		}

		return((boolean[])retValue);
	}

	/**
	 * Get the specified attribute's array of byte values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of byte values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 * 
	 */
	public byte[] getByteArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a byte array
		if ((retValue != null) &&
			(!(retValue instanceof byte[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a byte[]");
		}

		return((byte[])retValue);
	}

	/**
	 * Get the specified attribute's array of short values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of short values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 */
	public short[] getShortArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a short array
		if ((retValue != null) &&
			(!(retValue instanceof short[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a short[]");
		}

		return((short[])retValue);
	}

	/**
	 * Get the specified attribute's array of char values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of char values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 */
	public char[] getCharArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a char array
		if ((retValue != null) &&
			(!(retValue instanceof char[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a char[]");
		}

		return((char[])retValue);
	}

	/**
	 * Get the specified attribute's array of int values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of int values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 * 
	 */
	public int[] getIntArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is an int array
		if ((retValue != null) &&
			(!(retValue instanceof int[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a int[]");
		}

		return((int[])retValue);
	}

	/**
	 * Get the specified attribute's array of long values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of long values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
     *
	 */
	public long[] getLongArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a long array
		if ((retValue != null) &&
			(!(retValue instanceof long[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a long[]");
		}

		return((long[])retValue);
	}

	/**
	 * Get the specified attribute's array of float values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of float values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 * 
	 */
	public float[] getFloatArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a float array
		if ((retValue != null) &&
			(!(retValue instanceof float[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a float[]");
		}

		return((float[])retValue);
	}

	/**
	 * Get the specified attribute's array of double values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of double values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 * 
	 */
	public double[] getDoubleArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a double array
		if ((retValue != null) &&
			(!(retValue instanceof double[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a double[]");
		}

		return((double[])retValue);
	}

	/**
	 * Get the specified attribute's array of String values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of String values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 * 
	 */
	public String[] getStringArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a String array
		if ((retValue != null) &&
			(!(retValue instanceof String[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a String[]");
		}

		return((String[])retValue);
	}

	/**
	 * Get the specified attribute's array of Date values.
	 * 
	 * @param attributeName The name of the attribute.
	 *
	 * @return The <CODE>attributeName</CODE> array of Date Object values.
	 *
     * @throws java.lang.IllegalStateException If the
     * supplied <CODE>attributeName</CODE> attribute is not present.
	 * 
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName</CODE> attribute is not valid or
     * if its type does not match the return type.
	 */
	public Date[] getTimestampArrayValue(String attributeName)
        throws java.lang.IllegalStateException,
        java.lang.IllegalArgumentException
	{
		Serializable retValue=null;	// Data return value

		// Get the attribute value
		retValue=getObjectValue(attributeName);

		// Verify that this is a Date array
		if ((retValue != null) &&
			(!(retValue instanceof Date[])))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName " + attributeName + " isn't a Date[]");
		}

		return((Date[])retValue);
	}

	/**
	 * Setter methods for creation of the field data.
	 */

	/**
	 * Methods for setting non array type values.
	 */

	/**
	 * Note that the API would be required to only duplicate
	 * object reference.
	 * The deep copy the deep copy semantics are not recommended for efficiency
	 * sake in the API. If the caller intends to reuse the object, it must
	 * deep copy the object itself. This will not only simplify API
	 * implementation and but also improves the stability.
	 */

    /**
     * Set the specified attribute, whose type is a composite type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setObjectValue(String attributeName, Object value)
        throws java.lang.IllegalArgumentException
	{
		// Verify that the attribute name is not null or empty
		if ((attributeName == null) ||
			(attributeName.equals("")))
		{
			throw new java.lang.IllegalArgumentException(
				"attributeName is ' " + attributeName + "'");
		}

		// Verify that the Object implements Serializable
		if((value != null) &&
			(!(value instanceof Serializable)))
		{
			throw new java.lang.IllegalArgumentException(
				"value for " + attributeName + " isn't Serializable");
		}

		// Insert the object in the map
		usageRecMap.put(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is a boolean type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setBooleanValue(String attributeName, boolean value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data type
		setObjectValue(attributeName, new Boolean(value));
	}

    /**
     * Set the specified attribute, whose type is a byte type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setByteValue(String attributeName, byte value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data type
		setObjectValue(attributeName, new Byte(value));
	}

    /**
     * Set the specified attribute, whose type is a short type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setShortValue(String attributeName, short value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data type
		setObjectValue(attributeName, new Short(value));
	}

    /**
     * Set the specified attribute, whose type is a char type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setCharValue(String attributeName, char value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data type
		setObjectValue(attributeName, new Character(value));
	}

    /**
     * Set the specified attribute, whose type is a integer type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setIntValue(String attributeName, int value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data type
		setObjectValue(attributeName, new Integer(value));
	}

    /**
     * Set the specified attribute, whose type is a long type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setLongValue(String attributeName, long value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data type
		setObjectValue(attributeName, new Long(value));
	}

    /**
     * Set the specified attribute, whose type is a float type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setFloatValue(String attributeName, float value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data type
		setObjectValue(attributeName, new Float(value));
	}

    /**
     * Set the specified attribute, whose type is a double type.
     * 
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setDoubleValue(String attributeName, double value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data type
		setObjectValue(attributeName, new Double(value));
	}

    /**
     * Set the specified attribute, whose type is a String type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setStringValue(String attributeName, String value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is a Date type.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setTimestampValue(String attributeName, Date value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

	/**
	 * Methods for setting array type values.
	 * The arrays are not deep copied. Instead they are kept
	 * by their original reference. We leave it up to the caller
	 * to deal with the issues of simultaneous modifications.
	 */

    /**
     * Set the specified attribute, whose type is an array of composite values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setObjectArrayValue(String attributeName, Object[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of boolean values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setBooleanArrayValue(String attributeName, boolean[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of byte values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setByteArrayValue(String attributeName, byte[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of short values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setShortArrayValue(String attributeName, short[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of char values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setCharArrayValue(String attributeName, char[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of integer values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setIntArrayValue(String attributeName, int[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of long values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setLongArrayValue(String attributeName, long[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of float values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setFloatArrayValue(String attributeName, float[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of double values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setDoubleArrayValue(String attributeName, double[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of String values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setStringArrayValue(String attributeName, String[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

    /**
     * Set the specified attribute, whose type is an array of Date values.
     *
     * @param attributeName - The name of the attribute.
     * @param value - The value of the attribute.
     *
     * @throws java.lang.IllegalArgumentException If the
     * supplied <CODE>attributeName or value</CODE> parameters are malformed.
     */
	public void setTimestampArrayValue(String attributeName, Date[] value)
        throws java.lang.IllegalArgumentException
	{
		// Call the method to set the object value, passing in the
		// specified data
		setObjectValue(attributeName, value);
	}

}
