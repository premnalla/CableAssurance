package com.nec.oss.ipb.producer.ri.xml;

import java.lang.reflect.*;
import com.ilog.ossj.xml.binding.*;

/**
 * Extension of XML Tooling FieldDescriptor to handle the name/value pair
 * accessor methods of UsageRecValue
 *
 */
public class NameValueFieldDescriptor 
extends com.ilog.ossj.xml.binding.FieldDescriptor
{
	protected String	valueName = null;

	/**
	 * Class Constructor - calls the parent constructor and sets the valueName
     * to null
	 * 
	 * @param name Name for the field
	 * @param fieldType Data type for the field
	 * @param declaringType Declared type for the field (used for enumerations)
	 *
	 * @see
	 */
	public NameValueFieldDescriptor(String name, ClassDescriptor fieldType, 
									ClassDescriptor declaringType) 
	{
		super(name, fieldType, declaringType);

		this.valueName = null;
	}

	/**
	 * Get the value name for this name/value pair
	 * @return Name of the value
	 *
	 * @see
	 */
	public String getValueName() 
	{
		return this.valueName;
	}

	/**
	 * Sets the value name for this name/value pair
	 * @param valueName The valueName to set
	 */
	public void setValueName(String valueName) 
	{
		this.valueName = valueName;
	}

	// not valid for Nonserializable , array

	/**
	 * Get the object contained by this descriptor
	 * @param parent Parent object for this type
	 * @return Object held by this descriptor
	 *
	 * @exception InvocationTargetException Thrown if there is an error 
     * invoking the getter
	 */
	public Object getObject(Object parent) throws InvocationTargetException 
	{
		if (!directAccess && (getter == null)) 
		{
			throw new IllegalStateException("The field descriptor not support getObject()! ");
		}

		try 
		{
			if (directAccess) 
			{
				return field.get(parent);
			} 
			else 
			{
				Object[]	parameters = new Object[1];

				parameters[0] = valueName;

				return getter.invoke(parent, parameters);
			}
		} 
		catch (Exception e) 
		{

			// unsupported attribute exists, ignore it!
			if (e instanceof InvocationTargetException) 
			{
				throw (InvocationTargetException) e;
			} 
			else 
			{
				throw new IllegalArgumentException("Error getting object!" 
												   + e);
			}
		}
	}
}
