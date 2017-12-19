package com.nec.oss.ipb.producer.ri.xml;

import java.util.*;
import java.lang.reflect.*;
import javax.oss.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ilog.ossj.xml.util.Utilities;
import com.ilog.ossj.xml.binding.*;

/**
 * This is an extension of the XML Tooling Registry class for the IP Billing
 * API. This extension was necessary to handle the NameValueFieldDescriptor 
 * which is used to represent the UsageRecValue name/value pair accessor
 * methods.
 */
public class IPBRIRegistry extends Registry 
{

	/**
	 * Class Constructor - invokes the parent constructor
	 * 
	 */
	public IPBRIRegistry() 
	{
		super();
	}

	/**
	 * This method looks at the methods of a Class to determine the
     * accessor methods which represent attributes of the class.
	 * 
	 * @param classDescriptor ClassDescriptor for the class that should
     * be processed.
	 *
	 * @return Vector of FieldDescriptors for each of the serializableFields
	 *
	 * @see
	 */
	protected Vector introspectMethods(ClassDescriptor classDescriptor) 
	{
		Class		clazz = classDescriptor.getJavaType();
		ArrayList   skipFields = getFieldsToSkip(clazz);
		Vector		serializableFields = new Vector();

		try 
		{
			Method[]	methods = clazz.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++) 
			{
				Method  setter = null;
				String  fieldName;

				if (!ClassUtils.isGetter(methods[i])) 
				{

					// If this is a UsageRecValue, this is a special case
					// Check if the method is a name/value pair getter method.
					if (clazz.isAssignableFrom(javax.oss.ipb.type.UsageRecValue.class))
					{
						if (!isNameValueGetter(methods[i])) 
						{
							continue;
						} 
						else 
						{
							fieldName = getFieldFromNameValGetter(methods[i]);
							setter = 
								getNameValSetterFromNameValGetter(methods[i]);
						}
					} 
					else 
					{
						continue;
					}
				} 
				else 
				{
					fieldName = ClassUtils.getFieldFromGetter(methods[i]);
					setter = ClassUtils.getSetterFromGetter(methods[i]);
				}

				// Check if there are any fields that should be skipped.
				if ((skipFields != null) && (skipFields.contains(fieldName))) 
				{
					continue;
				}

				Class			fieldType = methods[i].getReturnType();
				ClassDescriptor descriptor;

				if (fieldType == clazz) 
				{
					descriptor = classDescriptor;
				} 
				else 
				{
					if (fieldType.equals(java.io.Serializable.class)) 
					{
						fieldType = java.lang.Object.class;
					}

					descriptor = getOrMakeClassDescriptor(fieldType);
				}
				if (setter != null) 
				{
					if (fieldName.equals(ClassUtils.SYSTEM_PROPERTIES_NAME)) 
					{
						descriptor.setAsSystemProperties();
					}
				}

				FieldDescriptor fDescriptor = new FieldDescriptor(fieldName, 
					descriptor, classDescriptor);

				fDescriptor.setGetter(methods[i]);
				fDescriptor.setSetter(setter);
				serializableFields.add(fDescriptor);
			}
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		}

		return serializableFields;
	}

	/**
	 * Determine if the Method is a name/value accessor method  
	 * @param getter Method to check
	 *
	 * @return flag indicating if the Method is used to get a name/value pair
     * attribute
	 *
	 */
	public static boolean isNameValueGetter(Method getter) 
	{
		if ((getter == null) || ClassUtils.isIgnorableMethod(getter)) 
		{
			return false;
		}

		String  name = getter.getName();

		if (name.startsWith("is") 
			&& (getter.getReturnType() == Boolean.TYPE)) 
		{
			return true;
		}
		if (!name.startsWith("get")) 
		{
			return false;
		}

		// Check the method to see that it returns something and that
		// it has one argument (would specify the name).
		Class[] methodParams = getter.getParameterTypes();

		if ((getter.getReturnType() == Void.TYPE) 
			|| (methodParams.length != 1)) 
		{
			return false;
		}

		// Check that the single argument (which should specify the name) is
		// a String
		if (!methodParams[0].isAssignableFrom(java.lang.String.class)) 
		{
			return false;
		}

		return true;
	}

	/**
	 * Get the attribute name from the Method
     * 
	 * @param getter Method that should be processed
	 *
	 * @return Name of the attribute represented by the getter Method
	 */
	public static String getFieldFromNameValGetter(Method getter) 
	{
		if (!isNameValueGetter(getter)) 
		{
			throw new java.lang.IllegalArgumentException("Illegal getter method when getting field name!");
		}

		String  name = getter.getName();
		Class   returnType = getter.getReturnType();
		int		getterPrefix = 3;

		if (name.startsWith("get")) 
		{
			getterPrefix = 3;
		} 
		else if (name.startsWith("is")) 
		{
			getterPrefix = 2;
		}

		String  fieldName = name.substring(getterPrefix);

		if (fieldName.equals("")) 
		{
			if (returnType.isArray()) 
			{
				fieldName = 
					Utilities.getShortClassName(returnType.getComponentType());
				fieldName = 
					ClassUtils.getPluralityOfString(Utilities.lowerFirstLetter(fieldName));
			}
		} 
		else 
		{
			fieldName = Utilities.lowerFirstLetter(fieldName);
		}

		return fieldName;
	}

	/**
	 * Get the Method for setting the attribute represented by the specified
     * getter Method
     * 
	 * @param getter Method that should be processed
	 *
	 * @return Method to set the attribute that corresponds to the getter Method
	 */
	public static Method getNameValSetterFromNameValGetter(Method getter) 
	{
		if (!isNameValueGetter(getter)) 
		{
			return null;
		}

		Method  setter = null;
		Class   clazz = getter.getDeclaringClass();
		String  getterName = getter.getName();
		String  setterName = null;

		if (getterName.startsWith("get")) 
		{
			setterName = "s" + getterName.substring(1);
		} 
		else if (getterName.startsWith("is")) 
		{
			setterName = "set" + getterName.substring(2);
		} 
		else 
		{
			log.warn("Unexpected getter name " + getterName 
					 + ". Failed to compute setter name");

			return null;
		}

		// Verify that the parameters are a name/value pair
		Class[] parameterTypes = new Class[2];

		parameterTypes[0] = java.lang.String.class;

		Class   getRetType = getter.getReturnType();

		if (getRetType.equals(java.io.Serializable.class)) 
		{
			getRetType = java.lang.Object.class;
		}

		parameterTypes[1] = getRetType;

		try 
		{
			setter = clazz.getMethod(setterName, parameterTypes);

			if (setter.getReturnType() != Void.TYPE) 
			{
				return null;
			}
		} 
		catch (Exception e) 
		{
			if (e instanceof SecurityException) 
			{
				e.printStackTrace();
			}
		}

		return setter;
	}

}
