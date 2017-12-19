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
import java.util.Calendar;

/**
 * Spec imports
 */
import javax.oss.Serializer;
import javax.oss.ipb.type.UsageRecFilterValue;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.AttributeAccessImpl;
import com.nokia.oss.ossj.common.ri.AttributeManager;

/**
 * This is an implementation of
 * {@link javax.oss.ipb.type.UsageRecFilterValue} interface.
 * This class models the behavior for all types of Usage Record
 * Filter interfaces in the IP Billing API.
 * <p>
 * A Usage Record Filter instance supports setting of the various queryable
 * attributes, by a which Usage Data Records can be filtered for retrieval.
 * Some common querying parameters for a Usage Data Record supported by
 * this interface are:
 * <UL>
 * <li> Usage Data Category: Usage Data Application type: VoD, IM, M-COMMERCE, Voice
 * <li> Usage Data Format: IPDR, (X)-CDR, ...
 * <li> Usage Data Format Version - String value of version (1.0, 2.5, etc.).
 * <li> Usage Data Encoding - XML, XDR, ASCII, ASN.1/BER, CSV, OSS/J Usage Record.
 * <li> Usage Data Timestamp - Records with this timestamp of collection time.
 * <li> Usage Attribute field values  - These can be added to this filter
 * instance at any time by utilizing the base interface method
 * {@link #setAttributeValue}.
 * collection time.
 * <li> Usage Data Collection Period - Records collected between
 * these timestamps.
 * </UL>
 * It is also possible that new interfaces could be derived from this
 * interface to represent specific filterable attributes for the
 * querying the Usage Data Records in question.
 * <p>
 * All the attributes set in this Filter instances are cancatenated
 * using logical AND expression for query filter purposes.
 *
 */

public class UsageRecFilterValueImpl
extends AttributeAccessImpl
implements UsageRecFilterValue
{
	private static String[] noSupportedSerializerTypes = new String[0];
	private static AttributeManager usageRecFilterAttributeManager;

    private static final String[] attributeNames =
    {
		USAGE_DATA_CATEGORY,
		USAGE_DATA_FORMAT,
		USAGE_DATA_FORMAT_VERSION,
		USAGE_DATA_ENCODING,
		USAGE_DATA_TIMESTAMP,
		USAGE_DATA_COLLECTION_PERIOD,
    };

    // writeable attributes
    private static final String[] settableAttributeNames =
    {
		USAGE_DATA_CATEGORY,
		USAGE_DATA_FORMAT,
		USAGE_DATA_FORMAT_VERSION,
		USAGE_DATA_ENCODING,
		USAGE_DATA_TIMESTAMP,
		USAGE_DATA_COLLECTION_PERIOD,
    };

	private String usageDataCategory=null;				// Data category 
	private String usageDataFormat=null;				// Data format 
	private String usageDataFormatVersion=null;			// Data format version
	private String usageDataEncoding=null;				// Data encoding
	private Calendar usageDataTimeStamp=null;			// Data timestamp
	private Calendar[] usageDataCollectionPeriod=null;	// Data time period

    /**
     * Constructor - creates an empty instance of the UsageRecFilterValueImpl
     * 
     */
	public UsageRecFilterValueImpl()
	{
		// Perform no action
	}

    /**
     * Configuration of AttributeManager and AttributeAccess
     * 
     * @param anAttributeManager Container of attributes to add
     */
    protected void addAttributesTo(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.addAttributes(
            this.attributeNames);
        super.addAttributesTo(anAttributeManager);
    }

    /**
     * Configuration of AttributeManager and AttributeAccess
     * 
     * @param anAttributeManager Container of attributes to configure
     */
    protected void configureAttributes(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.setSettableAttributes(
            this.settableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }

    /**
     * Get the attribute manager
     * 
     * @return The associated attribute manager
     */
    protected AttributeManager getAttributeManager()
    {
        return usageRecFilterAttributeManager;
    }

    /**
     * Make the attribute manager
     * 
     * @return The attribute manager that was just made
     */
    protected AttributeManager makeAttributeManager()
    {
        usageRecFilterAttributeManager = new AttributeManager();
        return usageRecFilterAttributeManager;
    }

    /**
     * Deep copies the instance. May return null, if
     * {@link CloneNotSupportedException} occurrs.
     *
     * @return A deep copy of this value
     */
    public Object clone()
    {
        UsageRecFilterValueImpl clone = null;
        try
        {
            clone = (UsageRecFilterValueImpl) super.clone();
        }
        catch(CloneNotSupportedException e)
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
        }

		if (usageRecFilterAttributeManager != null) 
		{
			clone.usageRecFilterAttributeManager = getAttributeManager();
		}

		if (usageDataTimeStamp != null) 
		{
			clone.setUsageDataTimestamp(getUsageDataTimestamp());
		}

		if (usageDataCollectionPeriod != null) 
		{
			clone.setCollectionTimePeriod(getCollectionTimePeriod());
		}


        return clone;
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

        if(!(other instanceof UsageRecFilterValueImpl))
        {
            return false;
        }

        UsageRecFilterValueImpl localOther = (UsageRecFilterValueImpl) other;

        if(localOther.hashCode() == hashCode())
        {

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
						if (!(noSupportedSerializerTypes[tpIdx].equals(
								localOther.noSupportedSerializerTypes[tpIdx])))
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
		
			if (usageRecFilterAttributeManager != null)
			{
				if  (!(usageRecFilterAttributeManager.
						equals(localOther.usageRecFilterAttributeManager)))
				{
					return false;
				}

			}
			else if(usageRecFilterAttributeManager != 
                                    localOther.usageRecFilterAttributeManager)
			{
				return false;
			}
    	
			if (usageDataCategory != null)
			{
				if  (!(usageDataCategory.equals(localOther.usageDataCategory)))
				{
					return false;
				}

			}
			else if(usageDataCategory != localOther.usageDataCategory)
			{
				return false;
			}

			if(usageDataFormat != null)
			{
				if  (!(usageDataFormat.equals(localOther.usageDataFormat)))
				{
					return false;
				}
			}
			else if(usageDataFormat != localOther.usageDataFormat)
			{
				return false;
			}
                         
			if (usageDataFormatVersion != null)
			{
				if  (!(usageDataFormatVersion.equals(
									localOther.usageDataFormatVersion)))
				{
					return false;
				}

			}
			else if(usageDataFormatVersion != localOther.usageDataFormatVersion)
			{
				return false;
			}

			if (usageDataEncoding != null)
			{
				if  (!(usageDataEncoding.equals(localOther.usageDataEncoding)))
				{
					return false;
				}
			}
			else if(usageDataEncoding != localOther.usageDataEncoding)
			{
				return false;
			}

			if (usageDataTimeStamp != null)
			{
				if  (!(usageDataTimeStamp.equals(
											localOther.usageDataTimeStamp)))
				{
					return false;
				}

			}
			else if(usageDataTimeStamp != localOther.usageDataTimeStamp)
			{
				return false;
			}

			if (usageDataCollectionPeriod != null)
			{
				if  (usageDataCollectionPeriod.length != 
									localOther.usageDataCollectionPeriod.length)
				{
					return false;
				}

				else
				{
					int tpLen=usageDataCollectionPeriod.length;
					for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
					{
						if (!(usageDataCollectionPeriod[tpIdx].equals(
								localOther.usageDataCollectionPeriod[tpIdx])))
						{
							return false;
						}
					}
				}
			}
			else if(usageDataCollectionPeriod != 
										localOther.usageDataCollectionPeriod)
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
     * @return String representin the object
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(150);

        sb.append("usageDataCategory: " + usageDataCategory + 
					"| usageDataFormat: " + usageDataFormat +
                    "| usageDataFormatVersion: " + usageDataFormatVersion + 
                    "| usageDataEncoding: " + usageDataEncoding + 
					"| usageDataTimeStamp: " + usageDataTimeStamp + 
					"| usageDataCollectionPeriod: "+ usageDataCollectionPeriod);

        return sb.toString();
    }

    /**
     * hashCode value of the object
     *
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode=-1;

        if(usageRecFilterAttributeManager != null)
        {
            hashCode = usageRecFilterAttributeManager.hashCode();
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

        if(usageDataCategory != null)
        {
            hashCode ^= usageDataCategory.hashCode();
        }

        if(usageDataFormat != null)
        {
            hashCode ^= usageDataFormat.hashCode();
        }

        if(usageDataFormatVersion != null)
        {
            hashCode ^= usageDataFormatVersion.hashCode();
        }

        if(usageDataEncoding != null)
        {
            hashCode ^= usageDataEncoding.hashCode();
        }

        if(usageDataTimeStamp != null)
        {
            hashCode ^= usageDataTimeStamp.hashCode();
        }

        if(usageDataCollectionPeriod != null)
        {
            int tpLen=usageDataCollectionPeriod.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(usageDataCollectionPeriod[tpIdx] != null)
                {
                    hashCode ^= usageDataCollectionPeriod[tpIdx].hashCode();
                }
            }
        }

		return hashCode;
	}


   /**
     * Get the queryable category attribute value.
	 * @return String value representing the category.
	 *
	 * @exception java.lang.IllegalStateException If the
     * <CODE>usageDataCategory</CODE> attribute is not yet set.
	 *
	 */
	public String getUsageDataCategory()
        throws java.lang.IllegalStateException

	{
        if ( (usageDataCategory == null) ||
             (usageDataCategory.length() == 0) )
        {
            throw new java.lang.IllegalStateException(
                "usageDataCategory attribute is not yet set");
        }

		return usageDataCategory;
	}

	/**
	 * Set the queryable category attribute value.
	 * 
	 * @param category String value representing the category.
	 *
	 * @exception java.lang.IllegalArgumentException If the
     * <CODE>category</CODE> parameter value is malformed.
     *
	 */
	public void setUsageDataCategory(String category) 
		throws java.lang.IllegalArgumentException
	{
		if((category == null) || (category.length() == 0))
        {
            throw new java.lang.IllegalArgumentException();
        }

		this.usageDataCategory=category;
	}

	/**
	 * Get the queryable format attribute value.
	 * 
	 * @return String value representing the format.
	 *
	 * @exception java.lang.IllegalStateException If the
     * <CODE>usageDataFormat</CODE> attribute is not yet set.
	 *
	 */
	public String getUsageDataFormat()
        throws java.lang.IllegalStateException
	{
        if ( (usageDataFormat == null) ||
             (usageDataFormat.length() == 0) )
        {
            throw new java.lang.IllegalStateException(
                "usageDataFormat attribute is not yet set");
        }

		return usageDataFormat;
	}

	/**
	 * Set the queryable format attribute value.
	 * 
	 * @param format String value representing the format.
	 *
	 * @exception java.lang.IllegalArgumentException If the
     * <CODE>format</CODE> parameter value is malformed.
     *
	 */
	public void setUsageDataFormat(String format) 
		throws java.lang.IllegalArgumentException
	{
		if((format == null) || (format.length() == 0))
        {
            throw new java.lang.IllegalArgumentException();
        }

		this.usageDataFormat=format;
	}

	/**
	 * Get the queryable format version attribute value.
	 * 
	 * @return String value representing the format version.
	 *
	 * @exception java.lang.IllegalStateException If the
     * <CODE>usageDataFormatVersion</CODE> attribute is not yet set.
	 *
	 */
	public String getUsageDataFormatVersion()
        throws java.lang.IllegalStateException
	{
        if ( (usageDataFormatVersion == null) ||
             (usageDataFormatVersion.length() == 0) )
        {
            throw new java.lang.IllegalStateException(
                "usageDataFormatVersion attribute is not yet set");
        }
		return usageDataFormatVersion;
	}

	/**
	 * Set the queryable formatVersion attribute value.
	 * 
	 * @param formatVersion String value representing the formatVersion.
	 *
	 * @exception java.lang.IllegalArgumentException If the
     * <CODE>formatVersion</CODE> parameter value is malformed.
	 */
	public void setUsageDataFormatVersion(String formatVersion) 
		throws java.lang.IllegalArgumentException
	{
		if((formatVersion == null) || (formatVersion.length() == 0))
		{
			throw new java.lang.IllegalArgumentException();
		}

		this.usageDataFormatVersion=formatVersion;
	}

	/**
	 * Get the queryable encoding attribute value.
	 * 
	 * @return String value representing the encoding.
	 *
	 * @exception java.lang.IllegalStateException If the
     * <CODE>usageDataEncoding</CODE> attribute is not yet set.
	 *
	 */
	public String getUsageDataEncoding()
        throws java.lang.IllegalStateException
	{
        if ( (usageDataEncoding == null) ||
             (usageDataEncoding.length() == 0) )
        {
            throw new java.lang.IllegalStateException(
                "usageDataEncoding attribute is not yet set");
        }
		return usageDataEncoding;
	}

	/**
	 * Set the queryable encoding attribute value.
	 * 
	 * @param encoding String value representing the encoding.
	 *
	 * @exception java.lang.IllegalArgumentException If the
     * <CODE>encoding</CODE> parameter value is malformed.
	 */
	public void setUsageDataEncoding(String encoding) 
		throws java.lang.IllegalArgumentException
	{
		if((encoding == null) || (encoding.length() == 0))
		{
			throw new java.lang.IllegalArgumentException();
		}

		this.usageDataEncoding=encoding;
	}

	/**
	 * Get the queryable collection timestamp attribute value.
	 * 
	 * @return value representing the collection timestamp.
	 *
	 * @exception java.lang.IllegalStateException If the
     * <CODE>usageDataTimeStamp</CODE> attribute is not yet set.
	 *
	 */
	public Calendar getUsageDataTimestamp()
        throws java.lang.IllegalStateException
	{
        if ( usageDataTimeStamp == null)
        {
            throw new java.lang.IllegalStateException(
                "usageDataTimeStamp attribute is not yet set");
        }
		return (Calendar) usageDataTimeStamp.clone();
	}

	/**
	 * Set the queryable collection timestamp attribute value.
	 * 
	 * @param timestamp value representing the collection timestamp.
	 *
	 * @exception java.lang.IllegalArgumentException If the
     * <CODE>timestamp</CODE> parameter value is malformed.
	 */
	public void setUsageDataTimestamp(Calendar timestamp) 
		throws java.lang.IllegalArgumentException
	{
		if(timestamp == null)
		{
			throw new java.lang.IllegalArgumentException();
		}
		this.usageDataTimeStamp=(Calendar)timestamp.clone();
	}

	/**
	 * Get the <CODE>usageDataCollectionPeriod</CODE> filterable attribute value.
     * This attribute allows one to search for Usage Data Records that
     * were collected during a particular timePeriod.
	 * 
	 * @return An array of two element length, of date/time.
	 *
	 * @exception java.lang.IllegalStateException If this
     * attribute is not yet set or initialized.
	 *
	 * @see java.util.Calendar
	 */
	public Calendar[] getCollectionTimePeriod() 
		throws java.lang.IllegalStateException
	{
        if ( usageDataCollectionPeriod == null)
        {
            throw new java.lang.IllegalStateException(
                "usageDataCollectionPeriod attribute is not yet set");
        }

		Calendar [] newUDCP = (Calendar []) usageDataCollectionPeriod.clone();

		for (int i = 0; i < usageDataCollectionPeriod.length; ++i) 
		{
		    newUDCP[i] = (Calendar) usageDataCollectionPeriod[i].clone();
		}

		return newUDCP;
	}

	/**
	 * Set the <CODE>collectionTimePeriod</CODE> filterable attribute value.
     * This attribute allows one to search for Usage Data Records that
     * were collected during a particular timePeriod.
	 * 
	 * @param timePeriod An array of two element length, of date/time. 
	 *
	 * @exception java.lang.IllegalArgumentException If the passed
     * <CODE>timePeriod</CODE> is malformed.
	 *
	 * @see java.util.Calendar
	 */
	public void setCollectionTimePeriod(Calendar[] timePeriod) 
		throws java.lang.IllegalArgumentException
	{
		if(timePeriod == null)
		{
			throw new java.lang.IllegalArgumentException();
		}

		Calendar [] newUDCP = (Calendar []) timePeriod.clone();

		for (int i = 0; i < timePeriod.length; ++i) 
		{
		    newUDCP[i] = (Calendar) timePeriod[i].clone();
		}

		this.usageDataCollectionPeriod=newUDCP;
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
     */
    
    public Serializer makeSerializer( String serializerType )
        throws java.lang.IllegalArgumentException
    {
        throw new java.lang.IllegalArgumentException(
            "makeSerializer() is not supported in the " +
            UsageRecFilterValueImpl.class.getName() +
            "Class.");
    }
}
