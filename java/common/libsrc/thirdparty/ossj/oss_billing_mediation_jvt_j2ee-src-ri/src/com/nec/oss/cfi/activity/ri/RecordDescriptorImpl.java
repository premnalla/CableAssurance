
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
import javax.oss.cfi.activity.RecordDescriptor;
import javax.oss.cfi.activity.AttributeDescriptor;


/**
 * Implements RecordDescriptor interface, representing the information
 * about the records of
 * report, that are returned by the
 * {@link javax.oss.cfi.activity.ReportIterator} as
 * {@link javax.oss.cfi.activity.ReportRecord} objects.
 */

public class RecordDescriptorImpl
implements javax.oss.cfi.activity.RecordDescriptor
{

    /**
     * Members
     */

    /**
     * Type of the report record
     */
    protected String reportRecordType;

    /**
     * Description of the fields in the record
     */
    protected AttributeDescriptor [] fieldsInformation;

    private static String[] noSupportedSerializerTypes = new String[0];
    
    /**
     * Returns a deep copy of this value
     *
     * @return A deep copy of this value
     *
     */
    public Object clone()
    {
        RecordDescriptorImpl clone = null;
        try
        {
            clone = (RecordDescriptorImpl) super.clone();

            if(fieldsInformation != null)
            {
                clone.fieldsInformation = (AttributeDescriptor [])
                    fieldsInformation.clone();

                for(int i = 0; i < fieldsInformation.length; ++i)
                {
                    clone.fieldsInformation[i] =
                        (AttributeDescriptor) fieldsInformation[i].clone();
                }
                
            }
            
        }
        
        catch(CloneNotSupportedException e)
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
            
        }

        return clone;
    }
    
    
    /**
     * Get the loadable java class name of the ReportRecord, which
     * is described by this Descriptor instance.
     * @return report record java class name
     * @exception java.lang.IllegalStateException Thrown if the Report record
     * type isn't set
     */
    public String getReportRecordType()
        throws java.lang.IllegalStateException
    {
        if(reportRecordType == null)
        {
            throw new java.lang.IllegalStateException(
                "ReportRecordType attribute was not set");
            
        }
        return reportRecordType;
    }
    
    /**
     * Set the loadable java class name of the ReportRecord, which
     * is described by this Descriptor instance.
     *
     * @param reportRecordType report record java class name
     * @exception java.lang.IllegalArgumentException Thrown if the input Report
     * record type isn't set
     */
    public void setReportRecordType(
        String reportRecordType)
        throws java.lang.IllegalArgumentException
    {
        if(reportRecordType == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ReportRecordType parameter is invalid");
            
        }
        this.reportRecordType = reportRecordType;
    }
    
        
    /**
     * make an empty instance of attribute descriptor.
     * @return empty instance of attribute descriptor.
     */
    public AttributeDescriptor makeAttributeDescriptor()
    {
        return new AttributeDescriptorImpl();
    }
    

    /**
     * A record consists of a series of attributes.
     * Get names of these attributes in an array.
     * @return Array of the attribute names
     * @exception java.lang.IllegalStateException Thrown if the fields
     * aren't set
     */
    public String[] getFieldsNames()
        throws java.lang.IllegalStateException
    {
        if( (fieldsInformation == null) ||
            (fieldsInformation.length == 0))
        {
            return new String[0];
        }

        String [] fieldsNames = new String [fieldsInformation.length];
    
        for(int i = 0; i < fieldsInformation.length; ++i)
        {
            AttributeDescriptor desc = fieldsInformation[i];
            if(desc == null)
            {
                fieldsNames = null;
                throw new java.lang.IllegalStateException(
                    "Element at " + i + " of descriptor array is null");
            }

            fieldsNames[i] = desc.getName();
            
        }

        return fieldsNames;
    }
    

    /**
     * A record consists of a series of attributes.
     * Get these as an array.
     * @return Array of the attribute descriptors
     * @exception java.lang.IllegalStateException Thrown if the fields
     * aren't set
     */
    public AttributeDescriptor[] getFieldsInformation()
        throws java.lang.IllegalStateException
    {
        if(fieldsInformation == null)
        {
            throw new java.lang.IllegalStateException(
                "FieldsInformation attribute was not set");
        }

        AttributeDescriptor [] newAdArray = (AttributeDescriptor [])
        fieldsInformation.clone();

        for(int i = 0; i < fieldsInformation.length; ++i)
        {
            newAdArray[i] = (AttributeDescriptor)
                fieldsInformation[i].clone();
        }

        return newAdArray;
    }
    

    /**
     * A record consists of a series of attributes.
     * Set these as an array.
     * @param fields Array of the attribute descriptors
     * @exception java.lang.IllegalArgumentException Thrown if the fields
     * aren't valid
     */
    public void setFieldsInformation(
        AttributeDescriptor[] fieldsInformation)
        throws java.lang.IllegalArgumentException
    {
        if(fieldsInformation == null)
        {
            throw new java.lang.IllegalArgumentException(
                "fieldsInformation paramter is not valid");
            
        }
        
        this.fieldsInformation = (AttributeDescriptor [])
            fieldsInformation.clone();

        for(int i = 0; i < fieldsInformation.length; ++i)
        {
            this.fieldsInformation[i] = (AttributeDescriptor)
                fieldsInformation[i].clone();
        }
        
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
            RecordDescriptorImpl.class.getName() +
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

        if(!(other instanceof RecordDescriptorImpl))
        {
            return false;
        }

        RecordDescriptorImpl localOther = 
							(RecordDescriptorImpl) other;

        if(localOther.hashCode() == hashCode())
        {

			if (reportRecordType != null)
			{
				if  (!(reportRecordType.
								equals(localOther.reportRecordType)))
				{
					return false;
				}
			}
			else if(reportRecordType != localOther.reportRecordType)
			{
				return false;
			}

			if (fieldsInformation != null)
			{
				if  (fieldsInformation.length != 
								localOther.fieldsInformation.length)
				{
					return false;
				}
				else
				{
					int tpLen=fieldsInformation.length;
					for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
					{
						if (!fieldsInformation[tpIdx].equals
							(localOther.fieldsInformation[tpIdx]))
						{
							return false;
						}
					}
				}
			}
			else if(fieldsInformation != 
								localOther.fieldsInformation)
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

        if(reportRecordType != null)
        {
            hashCode = reportRecordType.hashCode();
        }

        if(fieldsInformation != null)
        {
            int tpLen=fieldsInformation.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(fieldsInformation[tpIdx] != null)
                {
                     hashCode ^= fieldsInformation[tpIdx].hashCode();
                }
            }
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

