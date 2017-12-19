
package com.nec.oss.cfi.activity.ri;


/**
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec imports.
 */
import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import javax.oss.cfi.activity.ReportFormat;

/**
 * This class represents the format of the data reports.
 *
 * Since, it is not possible to generalize the serilization for each API,
 * each API is expected to
 *  sub class this, to provide implementation for
 * methods {@link javax.oss.SerializerFactory#getSupportedSerializerTypes},
 * and {@link javax.oss.SerializerFactory#makeSerializer}.
 * In this implementation, these methods work as follows:
 * <UL>
 * <LI> {@link javax.oss.SerializerFactory#getSupportedSerializerTypes}:
 * Always returns a ZERO length string array.
 * <LI> {@link javax.oss.SerializerFactory#makeSerializer}:
 * Always throws an {@link IllegalArgumentException}.
 * </UL>
 */

public class ReportFormatImpl
implements javax.oss.cfi.activity.ReportFormat
{
    /**
     * Members
     */

    /**
     * By default not set.
     * Example value could be: {@link ReportFormat#XML}.
     */
    private int type = -1 ;
    /**
     * By default not set.
     * Example value could be: "DOCTYPE MeasDataCollection SYSTEM MeasDataCollection.dtd";
     */
    private String specification;
    
    /**
     * By default not set.
     * Example value could be: "3GPP".
     */
    private String owner;
    /**
     * By default not set.
     * Example value could be: "3G".
     */
    private String technology;
    /**
     * By default not set.
     * Example value could be: "32.602-5 V4.0".
     */
    private String version;

    private static String[] noSupportedSerializerTypes = new String[0];

    /**
     * Constructor - empty
     */
    public ReportFormatImpl()
    {
        
    }
    
    /**
     * Create a deep copy of this instance
     * @return a deep copy of this instance
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
     * Gets the report format type.
     *
     * <p>
     * The type of the report format can have one of the following values:
     * <ul>
     * <li> XML
     * <li> ASN1
     * <li> ASCII
     * <li> BINARY
     * </ul>
     *
     *@return int The report format type.
     * @exception java.lang.IllegalStateException Thrown if the type isn't set
     */
    public int getType()
        throws java.lang.IllegalStateException
    {
        if(type == -1)
        {
            // not inited.
            throw new java.lang.IllegalStateException(
                "Type attribute is not set");
        }
        
        return type;
    }
    
    /**
     * Get the report format version.
     *
     *@return String The version of the report format.
     * @exception java.lang.IllegalStateException Thrown if the version isn't
     * set
     */
    public String getVersion()
        throws java.lang.IllegalStateException
    {
        if(version == null)
        {
            // not inited.
            throw new java.lang.IllegalStateException(
                "Version attribute is not set");
        }
        
        return version;
    }
    
    /**
     * Get the vendor name or the organization that have defined the format.
     *
     * <p>
     * Returns the vendor name or organization name of the report format. For example: if the
     * report format is 3GPP XML or ANS1 format this operation should return "3GPP".
     *
     *@return String The name of the Owner of the report format.
     * @exception java.lang.IllegalStateException Thrown if the owner isn't
     * set
     */
    public String getOwner()
        throws java.lang.IllegalStateException
    {
        if(owner == null)
        {
            // not inited.
            throw new java.lang.IllegalStateException(
                "Owner attribute is not set");
        }
        
        return owner;
    }
    
    /**
     * Gets the technology area that this report format is used for.
     *
     * <p>
     * This operation indicates the technology area of the nodes in a measurement report of this
     * report format. For example the operation should return "3G" for 3rd generation networks and
     * "ATM" for a ATM networks.
     *
     * <p>
     * The operation can return an empty string if the report format is applicable for many
     * technology areas.
     *
     *@return String The name of the technology.
     * @exception java.lang.IllegalStateException Thrown if the technology isn't
     * set
     */
    public String getTechnology()
        throws java.lang.IllegalStateException
    {
        if(technology == null)
        {
            // not inited.
            throw new java.lang.IllegalStateException(
                "Technology attribute is not set");
        }
        
        return technology;
    }
    
    /**
     * Returns information about the specification of the report format.
     *
     * <p>
     * This operation shall allow the client to get more detailed information on how to parse
     * the report format. For example this operation should return
     * "DOCTYPE MeasDataCollection SYSTEM MeasDataCollection.dtd" if the report format is
     * according to 3GPP XML format.
     *
     * <p>
     * If no applicable information is available, then an empty string should be returned.
     *
     *@return String Information on how to parse the report format.
     * @exception java.lang.IllegalStateException Thrown if the specification
     * isn't set
     */
    public String getSpecification()
        throws java.lang.IllegalStateException
    {
        if(specification == null)
        {
            // not inited.
            throw new java.lang.IllegalStateException(
                "Specification attribute is not set");
        }
        
        return specification;
    }
    
    /**
     * Set the type of this ReportFormat
     *
     * @param type type of this ReportFormat
     * @exception java.lang.IllegalArgumentException Thrown if the input report
     * type isn't valid
     */
    public void setType(int type)
        throws java.lang.IllegalArgumentException
    {
        if(type != ReportFormat.ASCII && type != ReportFormat.ASN1
           && type != ReportFormat.BINARY && type != ReportFormat.XML)
            throw new java.lang.IllegalArgumentException();
        
        this.type = type;
    }
    
    /**
     * Set the version of this ReportFormat
     *
     * @param version version of this ReportFormat
     * @exception java.lang.IllegalArgumentException Thrown if the input report
     * version isn't valid
     */
    public void setVersion(String version)
        throws java.lang.IllegalArgumentException
    {
        if(version == null)
            throw new java.lang.IllegalArgumentException();
        
        this.version = version;
    }
    
    /**
     * Set the owner of this ReportFormat
     *
     * @param owner owner of this ReportFormat (i.e. "3GPP")
     * @exception java.lang.IllegalArgumentException Thrown if the input owner
     * isn't valid
     */
    public void setOwner(String owner)
        throws java.lang.IllegalArgumentException
    {
        if(owner == null)
            throw new java.lang.IllegalArgumentException();
        
        this.owner = owner;
    }
    
    /**
     * Set the specification associated with this ReportFormat
     *
     * @param specification specification associated with this ReportFormat (i.e. "NMD-U 3.0")
     * @exception java.lang.IllegalArgumentException Thrown if the input
     * specification value isn't valid
     */
    public void setSpecification(String specification)
        throws java.lang.IllegalArgumentException
    {
        if(specification == null)
            throw new java.lang.IllegalArgumentException();
        
        this.specification = specification;
    }
    
    /**
     * Set the technology associated with this ReportFormat
     *
     * @param technology technology associated with this ReportFormat (i.e. "VOD")
     * @exception java.lang.IllegalArgumentException Thrown if the input
     * technology value isn't valid
     */
    public void setTechnology(String technology)
        throws java.lang.IllegalArgumentException
    {
        if(technology == null)
            throw new java.lang.IllegalArgumentException();
        
        this.technology = technology;
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
        throws java.lang.IllegalStateException
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
            ReportFormatImpl.class.getName() +
            "Class.");
    }
    
	/**
     * Deep equality checking of the instance.
     *
     * @return Flag indicating if the objects were equal
     */
    public boolean equals(Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof ReportFormatImpl))
        {
            return false;
        }

        ReportFormatImpl localOther = 
							(ReportFormatImpl) other;

        if(localOther.hashCode() == hashCode())
        {
			if ( type == localOther.type ) 
			{
				if (specification != null)
				{
					if  (!(specification.equals(localOther.specification)))
					{
						return false;
					}
				}
				else if(specification != localOther.specification)
				{
					return false;
				}

				if(owner != null)
				{
					if  (!(owner.equals(localOther.owner)))
					{
						return false;
					}
				}
				else if(owner != localOther.owner)
				{
					return false;
				}
   	                      
				if (technology != null)
				{
					if  (!(technology.equals(
										localOther.technology)))
					{
						return false;
					}
	
				}
				else if(technology != localOther.technology)
				{
					return false;
				}
	
				if (version != null)
				{
					if  (!(version.equals(localOther.version)))
					{
						return false;
					}
				}
				else if(version != localOther.version)
				{
					return false;
				}

				if (noSupportedSerializerTypes != null)
				{
					if  (noSupportedSerializerTypes.length != 
									localOther.noSupportedSerializerTypes.length)
					{
						return false;
					}
					else
					{
						int tpLen=noSupportedSerializerTypes.length;
						for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
						{
							if (noSupportedSerializerTypes[tpIdx] !=
								localOther.noSupportedSerializerTypes[tpIdx])
							{
								return false;
							}
						}
					}
				}
				else if(noSupportedSerializerTypes != 
									localOther.noSupportedSerializerTypes)
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
        int hashCode=-1;

        if(specification != null)
        {
            hashCode = specification.hashCode();
        }

        if(owner != null)
        {
            hashCode ^= owner.hashCode();
        }

        if(technology != null)
        {
            hashCode ^= technology.hashCode();
        }

        if(version != null)
        {
            hashCode ^= version.hashCode();
        }

        if(noSupportedSerializerTypes != null)
        {
            int tpLen=noSupportedSerializerTypes.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(noSupportedSerializerTypes[tpIdx] != null)
                {
                	hashCode ^= noSupportedSerializerTypes[tpIdx].hashCode();
                }
            }
        }

		return hashCode;
	}
}
