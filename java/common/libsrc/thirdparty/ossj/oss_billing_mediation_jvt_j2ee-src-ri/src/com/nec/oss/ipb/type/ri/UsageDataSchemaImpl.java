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
 * This represents a base implemetation of the UsageDataSchema interface. This 
 * describes a collection format or output format of a Usage Data Record.
 * The Schema descriptions are set and returned as {@link java.lang.Object}.
 * This allows the API to let a specific implementation to support the
 * passing of a schema definition to a client. Details of the schema
 * definition construction and details of schema definition
 * deciphering are left up to the individual implementations and the
 * API clients.
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
import javax.oss.ipb.type.UsageDataSchema;


/**
 * Implementaion of UsageDataSchema interface.
 */

public class UsageDataSchemaImpl
implements UsageDataSchema
{

	private String name=null;		// Schema name
	private String baseType=null;	// Schema base type
	private Object schemaDef=null;	// Schema definition object

    /**
	 * Constructor
	 */
	public UsageDataSchemaImpl()
	{
		// Take no action
	}

    /**
     * Deep copies the instance. May return null, if
     * {@link CloneNotSupportedException} occurrs.
     *
     * @return A deep copy of this value
     *
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
     * 
     * @return flag indicating value equality
     */
    public boolean equals(Object other)
	{
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof UsageDataSchemaImpl))
        {
            return false;
        }

        UsageDataSchemaImpl localOther = (UsageDataSchemaImpl) other;

        if(localOther.hashCode() == hashCode())
        {
			if (name != null)
			{
				if  (!(name.equals(localOther.name)))
				{
					return false;
				}

			}
			else if(name != localOther.name)
			{
				return false;
			}

			if(baseType != null)
			{
				if  (!(baseType.equals(localOther.baseType)))
				{
					return false;
				}
			}
			else if(baseType != localOther.baseType)
			{
				return false;
			}
                         
			if (schemaDef != null)
			{
				if  (!(schemaDef.equals(localOther.schemaDef)))
				{
					return false;
				}

			}
			else if(schemaDef != localOther.schemaDef)
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
     * String representation of the object
     * 
     * @return String value of the object
     */
	public String toString()
	{
		StringBuffer sb = new StringBuffer(50);

		sb.append("name: " + name + " | baseType: " + baseType +
					"| schemaDef: " + schemaDef);
		return sb.toString();
	}

    /**
     * hashCode value of the object
     * 
     * @return integer hashCode of the object
     */
	public int hashCode()
	{
		int hashCode=-1;

        if(name != null)
        {
            hashCode = name.hashCode();
        }

        if(baseType != null)
        {
            hashCode ^= baseType.hashCode();
        }

        if(schemaDef != null)
        {
            hashCode ^= schemaDef.hashCode();
        }

		return hashCode;
	}
    
    /**
     * Gets a user friendly name for this schema name.
     * <p>
     * Ex: IPDR_3.0_Schema for defining the schema for IPDR 3.0,
     * which is used to format usage records.
     * 
     * @return name of the object
     */
    public String getName()
	{
        if ( (name == null) ||
             (name.length() == 0) )
        {
            throw new java.lang.IllegalStateException(
                "name attribute is not yet set");
        }

        return name;
	}


    /**
     * Sets a user friendly name for this schema name.
     * <p>
     * Ex: IPDR_3.0_Schema for defining the schema for IPDR 3.0,
     * which is used to format usage records.
     * 
     * @param name  Name to set for the object
     */
    public void setName(String name)
	{
		if((name == null) || (name.length() == 0))
		{
			throw new java.lang.IllegalArgumentException();
		}

		this.name = name;
	}
    
    /**
     * Gets the base type for the format of the schema.
     * <p>
     * Ex: "XML_1.0" for an IPDR_3.0_Schema
     * for defining the schema for IPDR 3.0,
     * which is used to format usage records.
     * 
     * @return base type description of the object
     */
    public String getBaseType()
	{
		if((baseType == null) || (baseType.length() == 0))
		{
            throw new java.lang.IllegalStateException(
                "baseType attribute is not yet set");
		}

		return(baseType);
	}

    /**
     * Sets the base type for the format of the schema.
     * <p>
     * Ex: "XML_1.0" for an IPDR_3.0_Schema
     * for defining the schema for IPDR 3.0,
     * which is used to format usage records.
     * 
     * @param baseType base type description of the object
     */
    public void setBaseType(String baseType)
	{
		if((baseType == null) || (baseType.length() == 0))
		{
			throw new java.lang.IllegalArgumentException();
		}

		this.baseType = baseType;
	}

    /**
     * Gets the actual schema object definition.
     * <p>
     * Note that the returned object is a Seriazable and cloneable value.
     * 
     * @return schema definition for this object
     */
    public Object getSchemaDefinition()
	{
		if(schemaDef == null)
		{
            throw new java.lang.IllegalStateException(
                "schemaDef attribute is not yet set");
		}

		return(schemaDef);
	}

    /**
     * Sets the actual schema object definition.
     * <p>
     * Note that the <CODE>schemaDef</CODE> parameter should be
     * a Seriazable and cloneable value.
     * 
     * @param schemaDef Schema definition object to set
     */
    public void setSchemaDefinition(Object schemaDef)
	{
		if(schemaDef == null)
		{
			throw new java.lang.IllegalArgumentException();
		}

		this.schemaDef = schemaDef;
	}
}
