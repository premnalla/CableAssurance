package com.nec.oss.ipb.producer.ri;


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
import java.util.ArrayList;


/**
 * Spec imports.
 */
import javax.oss.ipb.producer.MediationCapability;
import javax.oss.ipb.event.MediationCapabilityChangeEventDecoder;
import javax.oss.ipb.type.UsageDataSchema;


/**
 * Implementation for javax.oss.ipb.producer.MediationCapability interface.
 * This defines the base descriptor for expressing of Mediation
 * Capabilities of a <CODE>Producer</CODE> Entity.
 *
 * <p>
 * More specialized sub interfaces can be implemented to add more capabilities.
 * Also, by following OSS/J Value object pattern, each capability
 * attribute has JavaBeans like get and set methods
 * for access by client of this interface.
 *
 * <p>
 * This class is used by the <CODE>ProducerValue</CODE> and
 * <CODE>JVTProducerSession</CODE> interfaces to
 * return the mediation capabilities of a <CODE>Producer</CODE> Entity.
 *
 * @see javax.oss.ipb.producer.ProducerValue
 * @see javax.oss.ipb.producer.JVTProducerSession
 * @ossj:complexdata
 *
 */

public class MediationCapabilityImpl
implements javax.oss.ipb.producer.MediationCapability
{

    /**
     * Member Data
     */
    
    /**
     * Symbolic name for this capability.
     */
    private String name;

    private String[] collectionCategories;

    private String collectionFormat;
    private String collectionFormatVersion;
    private String collectionEncoding;
    private UsageDataSchema collectionSchema;
    private String[] outputFormats;
    private ArrayList outputFormatVersions;
    private String[] outputEncodings;
    private UsageDataSchema[] outputSchemas;

    private MediationCapabilityChangeEventDecoder eventDecoder;

    /**
     * Operations
     */

    /**
     * Get the name for this MediationCapbility
     * @return the user-friendly name for this mediation capability descriptor.
     * <p>
     * This is an mandatory field.
     * @exception java.lang.IllegalStateException If this attribute value is
     * not yet set.
     */
    public String getName()
        throws java.lang.IllegalStateException
    {
        if(name == null)
        {
            throw new java.lang.IllegalStateException(
                "Name attribute has not been initialized");
        }
        return name;
    }
    

    /**
     * Set the user-friendly name for this mediation capability descriptor.
     * <p>
     * This is an mandatory field.
	 * 
	 * @param name The name for this MediationCapability
     * @exception java.lang.IllegalArgumentException If the supplied
     * <CODE>name</CODE> parameter is malformed.
     */
    public void setName(String name)
        throws java.lang.IllegalArgumentException
    {
        if(name == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Name parameter value is not valid");
        }
        
        this.name = name;
    }
    
    
    
	/**
	 * Get the Usage Data Collection Category Names for this
     * <CODE>Producer</CODE> Entity.
     * <p>
     * This is an optional field.
	 * 
	 * @return An array of String values for category names.
	 *
	 * @exception java.lang.IllegalStateException If this attribute value is
     * not yet set.
	 *
	 */
	public String[] getCollectionCategories()
        throws java.lang.IllegalStateException
    {
        if(collectionCategories == null)
        {
            throw new java.lang.IllegalStateException(
                "CollectionCategories attribute has not been initialized");
        }
        return (String[]) collectionCategories.clone();
    }
    

	/**
	 * Set the Usage Data Collection Category Names for a <CODE>Producer</CODE>.
     * <p>
     * This is an optional field.
	 * 
	 * @param categories The Category names as an array of String instances.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied
     <CODE>categories</CODE> parameter is malformed.
	 *
	 */
	public void setCollectionCategories(
        String[] collectionCategories) 
		throws java.lang.IllegalArgumentException
    {
        if(collectionCategories == null)
        {
            throw new java.lang.IllegalArgumentException(
                "CollectionCategories parameter value is not valid");
        }
        
        this.collectionCategories = (String[]) collectionCategories.clone();
    }
    


	/**
	 * Get the format name of the <CODE>Producer</CODE> collected Usage Data.
     * Ex: IPDR for supporting IPDR formatted usage data.
     * <p>
     * This is an mandatory field.
	 * 
	 * @return A String value for the format name.
	 *
	 * @exception java.lang.IllegalStateException If this attribute
     * value is not yet set.
	 *
	 */
	public String getCollectionFormat()
        throws java.lang.IllegalStateException
    {
        if(collectionFormat == null)
        {
            throw new java.lang.IllegalStateException(
                "CollectionFormat attribute has not been initialized");
        }
        return collectionFormat;
    }
    

	/**
	 * Set the format name of the <CODE>Producer</CODE> collected Usage Data.
     * <p>
     * This is an mandatory field.
	 * 
	 * @param collectionFormat The Format name as a String value.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied
     * parameter is malformed.
	 *
	 */
	public void setCollectionFormat(
        String collectionFormat) 
		throws java.lang.IllegalArgumentException
    {
        if(collectionFormat == null)
        {
            throw new java.lang.IllegalArgumentException(
                "CollectionFormat parameter value is not valid");
        }
        
        this.collectionFormat = collectionFormat;
    }
    

	/**
	 * Get the version of collection format
     * of the <CODE>Producer</CODE> Usage Data,
     * (e.g.) 3.0 for an "IPDR" format.
     * <p>
     * This is an optional field.
     * Note that this attribute can be omitted to indicate that all
     * legal versions of the specified format are handled.
	 * 	
     * @return A String value for the version value.
	 *
	 * @exception java.lang.IllegalStateException If this attribute
     * value is not yet set.
	 *
	 */
	public String getCollectionFormatVersion()
        throws java.lang.IllegalStateException
    {
        if(collectionFormatVersion == null)
        {
            throw new java.lang.IllegalStateException(
                "CollectionFormatVersion attribute has not been initialized");
        }

        return collectionFormatVersion;
    }
    
    

	/**
	 * Set the version of the format of the <CODE>Producer</CODE>'s
     * collected Usage Data.
     * <p>
     * This is an optional field.
     * Note that this value may be unset to null, to indicate that
     * all legal versions of the collection format are handled.
	 * 
	 * @param collectionFormatVersion The Version as a String value.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied
     * parameter is malformed.
	 *
	 */
	public void setCollectionFormatVersion(
        String collectionFormatVersion) 
		throws java.lang.IllegalArgumentException
    {
        if(collectionFormatVersion == null)
        {
            throw new java.lang.IllegalArgumentException(
                "CollectionFormatVersion parameter value is not valid");
        }
        
        this.collectionFormatVersion = collectionFormatVersion;
    }
    

	/**
	 * Get the encoding scheme name of the
     * <CODE>Producer</CODE> collected Usage Data.
     * <p>
     * This is an optional field.
	 * 
	 * @return A String value for the encoding name.
	 *
	 * @exception java.lang.IllegalStateException If this
     * attribute value is not yet set.
	 *
	 */
	public String getCollectionEncoding()
        throws java.lang.IllegalStateException
    {
        if(collectionEncoding == null)
        {
            throw new java.lang.IllegalStateException(
                "CollectionEncoding attribute has not been initialized");
        }

        return collectionEncoding;
    }
    

	/**
	 * Set the encoding scheme name of the
     * <CODE>Producer</CODE> collected Usage Data.
     * <p>
     * This is an optional field.
	 * 
	 * @param collectionEncoding The encoding name as a String value.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied
     * parameter is malformed.
	 *
	 */
	public void setCollectionEncoding(
        String collectionEncoding) 
		throws java.lang.IllegalArgumentException
    {
        if(collectionEncoding == null)
        {
            throw new java.lang.IllegalArgumentException(
                "CollectionEncoding parameter value is not valid");
        }
        
        this.collectionEncoding = collectionEncoding;
    }
    

	/**
	 * Get the collection schema object of the
     * <CODE>Producer</CODE> Usage Data.
     * <p>
     * This is an optional field.
     * This Schema definition can be provided on an optional basis
     * to describe non-standard (proprietary) collection format
     * to client. Use of this field is optional for each implementation.
	 * 
	 * @return A Schema Object for the collection format.
	 *
	 * @exception java.lang.IllegalStateException If this attribute
     * value is not yet set.
	 *
	 */
	public UsageDataSchema getCollectionSchema()
        throws java.lang.IllegalStateException
    {
        if(collectionSchema == null)
        {
            throw new java.lang.IllegalStateException(
                "CollectionSchema attribute has not been initialized");
        }
        
        return (UsageDataSchema) collectionSchema.clone();
    }
    

	/**
	 * Set the collection schema name of the
     * <CODE>Producer</CODE>'s  Usage Data.
     * <p>
     * This is an optional field.
	 * 
	 * @param collectionSchema The collection schema value.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied

     * parameter is malformed.
	 *
	 */
	public void setCollectionSchema(UsageDataSchema collectionSchema) 
		throws java.lang.IllegalArgumentException
    {
        if(collectionSchema == null)
        {
            throw new java.lang.IllegalArgumentException(
                "CollectionSchema parameter value is not valid");
        }
        
        this.collectionSchema =
            (UsageDataSchema) collectionSchema.clone();
    }
    


	/**
	 * Get the names of the <CODE>Producer</CODE>
     * supported Usage Data output formats for the specified
     * collection format.
     * <p>
     * Note that a Producer may support mediation/conversion from a
     * single collection format to more than one
     * output format.
     * <p>
     * If this attribute is not set, it is implied that
     * producer does not offer mediation capability for this format
     * of usage record to convert to other formats.
	 * 
	 * @return An array of String values for the output format names.
	 *
	 * @exception java.lang.IllegalStateException If this attribute value
     * is not yet set.
	 *
	 * @see #getCollectionFormat
	 */
	public String[] getOutputFormats()
        throws java.lang.IllegalStateException
    {
        if(outputFormats == null)
        {
            throw new java.lang.IllegalStateException(
                "OutputFormats attribute has not been initialized");
        }

        return (String[]) outputFormats.clone();
    }

	/**
	 * Set the names of the <CODE>Producer</CODE> supported
     * Usage Data output formats.
     * <p>
     * If this attribute is not set, it is implied that
     * producer does not offer mediation capability for this format
     * of usage record to convert to other formats.
	 * 
	 * @param outputFormats The output format names as an array of String values.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied
     * parameter is malformed.
	 *
	 * @see #setCollectionFormat
	 */
	public void setOutputFormats(
        String[] outputFormats)
		throws java.lang.IllegalArgumentException
    {
        if(outputFormats == null)
        {
            throw new java.lang.IllegalArgumentException(
                "OutputFormats parameter value is not valid");
        }
        
        this.outputFormats = (String[]) outputFormats.clone();
    }


	/**
	 * Get the versions of collection format
     * of the <CODE>Producer</CODE> Usage Data,
     * (e.g.) 3.0 for an "IPDR" format.
     * <p>
     * The returned result should consist of a list of
     * array of String values. Each String array represents the
     * supported versions for corresponding output format.
     * <p>
     * Note that this attribute can be omitted to indicate that all
     * legal versions of the specified formats are handled.
     * <p>
     * An individual output format version Array can be set to null to
     * indicate that all legal versions of that output format are
     * handled by this <CODE>Producer</CODE>.
	 * 	
     * @return A list of String array values for the version values for
     * each supported output format.
	 *
	 * @exception java.lang.IllegalStateException If
     * this attribute value is not yet set.
	 *
	 */
	public ArrayList getOutputFormatVersions()
        throws java.lang.IllegalStateException
    {
        if(outputFormatVersions == null)
        {
            throw new java.lang.IllegalStateException(
                "OutputFormatVersions attribute has not been initialized");
        }

        ArrayList result = (ArrayList) outputFormatVersions.clone();

        // Do some sanity checking and copying.
        for(int i = 0; i < outputFormatVersions.size(); ++i)
        {
            result.set(i,
                       ((String []) outputFormatVersions.get(i)).clone());
                
        }

        return result;
    }

	/**
	 * Set the version of the format of the <CODE>Producer</CODE>'s
     * collected Usage Data.
     * <p>
     * Note that this value may be unset to null, to indicate that
     * all legal versions of the collection format are handled.
     * <p>
     * The supplied version parameter should consist of a list of
     * array of String values. Each String array represents the
     * supported versions for corresponding output format.
     * <p>
     * Note that this attribute can be omitted to indicate that all
     * legal versions of the specified formats are handled.
     * <p>
     * An individual output format version Array can be set to null to
     * indicate that all legal versions of that output format are
     * handled by this <CODE>Producer</CODE>.
	 * 	
	 * @param outputFormatVersions The supported output format versions as a 
     * ArrayList value.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied
     * parameter is malformed.
	 *
	 */
	public void setOutputFormatVersions(
        ArrayList outputFormatVersions) 
		throws java.lang.IllegalArgumentException
    {
        if(outputFormatVersions == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Name parameter value is not valid");
        }
        
        this.outputFormatVersions =
            (ArrayList) outputFormatVersions.clone();

        for(int i = 0; i < outputFormatVersions.size(); ++i)
        {
            Object o = outputFormatVersions.get(i);

            if( ( o == null ) ||
                (!( o instanceof String[])))
            {
                throw new java.lang.IllegalArgumentException(
                    "OutputFormatVersions parameter value is not valid");
            }
            // Clone individual object. They all should be string
            // arrays.
            this.outputFormatVersions.set(
                i,
                ((String[]) o).clone());
            
        }
    }
    
	/**
	 * Get the names of the <CODE>Producer</CODE>
     * supported Usage Data output encodings.
     * <p>
     * This is an optional field.
	 * 
	 * @return An array of String values for the output encoding names.
	 *
	 * @exception java.lang.IllegalStateException If this
     * attribute value is not yet set.
	 *
	 * @see #getCollectionEncoding
	 */
	public String[] getOutputEncodings()
        throws java.lang.IllegalStateException
    {
        if(outputEncodings == null)
        {
            throw new java.lang.IllegalStateException(
                "OutputEncodings attribute has not been initialized");
        }
        return (String[]) outputEncodings.clone();
        
    }

	/**
	 * Set the names of the <CODE>Producer</CODE> supported
     * Usage Data output encodings.
     * <p>
     * This is an optional field.
	 * 
	 * @param outputEncodings An array of String values for the encoding names.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied
     * is malformed.
	 *
	 * @see #setCollectionEncoding
	 */
	public void setOutputEncodings(String[] outputEncodings) 
		throws java.lang.IllegalArgumentException
    {
        if(outputEncodings == null)
        {
            throw new java.lang.IllegalArgumentException(
                "OutputEncodings parameter value is not valid");
        }
        
        this.outputEncodings = outputEncodings;
    }


	/**
	 * Get the output schema objects of the
     * <CODE>Producer</CODE> Output Usage Data.
     * <p>
     * This is an optional field.
     * These Schema definitions can be provided on an optional basis
     * to describe non-standard (proprietary) output formats
     * to client. Use of this field is optional for each implementation.
	 * 
	 * @return A Schema Object for the output format.
	 *
	 * @exception java.lang.IllegalStateException If this attribute
     * value is not yet set.
	 *
	 */
	public UsageDataSchema[] getOutputSchemas()
        throws java.lang.IllegalStateException
    {
        if(outputSchemas == null)
        {
            throw new java.lang.IllegalStateException(
                "OutputSchemas attribute has not been initialized");
        }

        UsageDataSchema [] result =
            (UsageDataSchema []) outputSchemas.clone();
        
        for ( int i = 0; i < outputSchemas.length; ++i)
        {
            result[i] = (UsageDataSchema) outputSchemas[i].clone();
        }
        
        return result;
    }

	/**
	 * Set the output scheme values of the
     * <CODE>Producer</CODE> collected Usage Data.
     * <p>
     * This is an optional field.
	 * 
	 * @param outputSchemas The output schema values.
	 *
	 * @exception java.lang.IllegalArgumentException If the supplied
     * parameter is malformed.
	 *
	 */
	public void setOutputSchemas(UsageDataSchema[] outputSchemas) 
		throws java.lang.IllegalArgumentException
    {
        if(outputSchemas == null)
        {
            throw new java.lang.IllegalArgumentException(
                "Name parameter value is not valid");
        }
        
        this.outputSchemas = (UsageDataSchema []) outputSchemas.clone();

        for ( int i = 0; i < outputSchemas.length; ++i)
        {
            this.outputSchemas[i] = (UsageDataSchema) outputSchemas[i].clone();
        }
        
    }

    
    /**
    ********************************
    * The following methods are helper methods. They don't form part of
    * mediation capability attributes itself.
    ********************************
     */
    
    /**
     * Returns a deep copy of this value
     *
     * @return A deep copy of this value
     *
     */

    public Object clone()
    {
        /**
         * This instance clone.
         */
        MediationCapabilityImpl clone = null;

        try
        {
            clone = (MediationCapabilityImpl) super.clone();

            if(collectionSchema != null)
            {
                clone.collectionSchema = (UsageDataSchema)
                    collectionSchema.clone();
            }
            
            if(collectionCategories != null)
            {
                clone.collectionCategories = (String[])
                    collectionCategories.clone();
            }
            
            if(outputFormats != null)
            {
                clone.outputFormats = (String[]) outputFormats.clone();
            }

            if(outputEncodings != null)
            {
                clone.outputEncodings = (String[]) outputEncodings.clone();
            }
            
            if(outputSchemas != null)
            {
                clone.outputSchemas = (UsageDataSchema[]) outputSchemas.clone();

                for(int i = 0; i < outputSchemas.length; ++i)
                {
                    clone.outputSchemas[i] = (UsageDataSchema)
                        outputSchemas[i].clone();
                }
            }
            
            if(outputFormatVersions != null)
            {
                clone.outputFormatVersions =
                    (ArrayList) outputFormatVersions.clone();

                for(int i = 0; i < outputFormatVersions.size(); ++i)
                {
                    clone.outputFormatVersions.set(
                        i,
                        ((String[])outputFormatVersions.get(i)).clone());
                }
            }
            
            if(eventDecoder != null)
            {
                clone.eventDecoder =
                    (MediationCapabilityChangeEventDecoder) eventDecoder.clone();
            }
        }

        catch ( CloneNotSupportedException e )
        {
//            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
            clone = null;
        }

        return clone;
    }

    /**
     * Checks if two MediationCapability values are equal.
     *
     * @param other The other instance.
     *
     * @return boolean If they are equal returns <CODE>true</CODE>.
     * Otherwise returns <CODE>false</CODE>.
     */
    public boolean equals(Object other)
    {
		int arrayLen;
		int arrayIdx;

        if(other == this)
        {
            return true;
        }

        if(!(other instanceof MediationCapabilityImpl))
        {
            return false;
        }

		MediationCapabilityImpl otherMC = (MediationCapabilityImpl)other;

		if(otherMC.hashCode() == hashCode())
		{

			if(name!=null)
			{
				if(!this.name.equals(otherMC.name))
				{
        			return false;
				}
			}
			else if (this.name != otherMC.name)
			{
   	    		return false;
			}
	
			if(collectionCategories != null)
			{
				arrayLen=this.collectionCategories.length;
	
				if(arrayLen != otherMC.collectionCategories.length)
				{
   		     		return false;
				}
	
				for(arrayIdx=0;arrayIdx<arrayLen;++arrayIdx)
				{
					if(!this.collectionCategories[arrayIdx].equals(
								otherMC.collectionCategories[arrayIdx]))
					{
   	     			return false;
					}
				}
			}
			else if(collectionCategories != otherMC.collectionCategories)
			{
   		     	return false;
			}


			if(collectionFormat != null)
			{
				if(!this.collectionFormat.equals(otherMC.collectionFormat))
				{
       		 		return false;
				}
			}
			else if(this.collectionFormat != otherMC.collectionFormat)
			{
   				return false;
			}



			if(collectionFormatVersion != null)
			{
				if(!this.collectionFormatVersion.equals(
									otherMC.collectionFormatVersion))
				{
   		     		return false;
				}
			}
			else if(this.collectionFormatVersion != otherMC.collectionFormatVersion)
			{
  	     		return false;
			}


			if(collectionEncoding != null)
			{
				if(!this.collectionEncoding.equals(otherMC.collectionEncoding))
				{
       				return false;
				}
			}
			else if(this.collectionEncoding != otherMC.collectionEncoding)
			{
   	    		return false;
			}
		
			if(collectionSchema != null)
			{
				if(!this.collectionSchema.equals(otherMC.collectionSchema))
				{
   			 		return false;
				}
			}
			else if(collectionSchema != otherMC.collectionSchema)
			{
   	   		  	return false;
			}

			if(outputFormats != null)
			{
				arrayLen=this.outputFormats.length;

				if(arrayLen != otherMC.outputFormats.length)
				{
   		     		return false;
				}

				for(arrayIdx=0;arrayIdx<arrayLen;++arrayIdx)
				{
					if(!this.outputFormats[arrayIdx].equals(
									otherMC.outputFormats[arrayIdx]))
					{
   		     			return false;
					}
				}
			}
			else if(this.outputFormats != otherMC.outputFormats)
			{
   		     	return false;
			}


			if((outputFormatVersions != null) && 
					(otherMC.outputFormatVersions != null))
			{
				arrayLen=this.outputFormatVersions.size();
	
				if(arrayLen != otherMC.outputFormatVersions.size())
				{
   		     		return false;
				}

				for(arrayIdx=0;arrayIdx<arrayLen;++arrayIdx)
				{
					// For the reference implementation, the output format
					// version objects are String arrays
					String[] myOutputFormatVersion = 
							(String[])this.outputFormatVersions.get(arrayIdx);
					String[] otherOutputFormatVersion = 
							(String[])otherMC.outputFormatVersions.get(arrayIdx);
					if((myOutputFormatVersion != null) && 
							(otherOutputFormatVersion != null))
					{
						int outputLen=myOutputFormatVersion.length;
   
                		if(outputLen != otherOutputFormatVersion.length)
                		{
                    		return false;
                		}

						for(int outputIdx=0;outputIdx<outputLen;++outputIdx)
						{
							if(!myOutputFormatVersion[outputIdx].equals(
										otherOutputFormatVersion[outputIdx]))
							{
                    			return false;
							}
						}
					}
				}
			}
			else if (this.outputFormatVersions != otherMC.outputFormatVersions)
			{
   		     	return false;
			}
	
			if(outputEncodings != null)
			{
				arrayLen=this.outputEncodings.length;
	
				if(arrayLen != otherMC.outputEncodings.length)
				{
   		     		return false;
				}
	
				for(arrayIdx=0;arrayIdx<arrayLen;++arrayIdx)
				{
					if(!this.outputEncodings[arrayIdx].equals(
									otherMC.outputEncodings[arrayIdx]))
					{
   		     			return false;
					}
   		 		}
   		 	}
			else if (this.outputEncodings != otherMC.outputEncodings)
			{
   		     	return false;
			}

			if(outputSchemas != null)
			{
				arrayLen=this.outputSchemas.length;

				if(arrayLen != otherMC.outputSchemas.length)
				{
   		     		return false;
				}

				for(arrayIdx=0;arrayIdx<arrayLen;++arrayIdx)
				{
					if(!this.outputSchemas[arrayIdx].equals(
									otherMC.outputSchemas[arrayIdx]))
					{
   		     			return false;
					}
				}
			}
			else if (this.outputSchemas != otherMC.outputSchemas)
			{
   		     	return false;
			}

			if(eventDecoder != null)
			{
				if(!this.eventDecoder.equals(otherMC.eventDecoder))
				{
   		     		return false;
				}
			}
			else if (this.eventDecoder != otherMC.eventDecoder)
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

		if(name != null)
		{
			hashCode = name.hashCode();
		}
        if(collectionCategories != null)
        {
            int tpLen=collectionCategories.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(collectionCategories[tpIdx] != null)
                {
                     hashCode ^= collectionCategories[tpIdx].hashCode();
                }
            }
        }
		if(collectionFormat != null)
		{
			hashCode ^= collectionFormat.hashCode();
		}
		if(collectionFormatVersion != null)
		{
			hashCode ^= collectionFormatVersion.hashCode();
		}
		if(collectionEncoding != null)
		{
			hashCode ^= collectionEncoding.hashCode();
		}
		if(collectionSchema != null)
		{
			hashCode ^= collectionSchema.hashCode();
		}
        if(outputFormats != null)
        {
            int tpLen=outputFormats.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(outputFormats[tpIdx] != null)
                {
                     hashCode ^= outputFormats[tpIdx].hashCode();
                }
            }
        }
		if(outputFormatVersions != null)
		{
            int oFVLen=outputFormatVersions.size();
            for(int oFVIdx=0; oFVIdx<oFVLen; ++oFVIdx)
            {
				String[] tmpStringArray=(String[])outputFormatVersions.get(oFVIdx);
				int strArrayLen=tmpStringArray.length;
            	for(int stIdx=0; stIdx<strArrayLen; stIdx++)
            	{
                	if(tmpStringArray[stIdx] != null)
                	{
                     	hashCode ^= tmpStringArray[stIdx].hashCode();
                	}
            	}
            }
		}
        if(outputEncodings != null)
        {
            int tpLen=outputFormats.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(outputEncodings[tpIdx] != null)
                {
                     hashCode ^= outputEncodings[tpIdx].hashCode();
                }
            }
        }
        if(outputSchemas != null)
        {
            int tpLen=outputSchemas.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(outputSchemas[tpIdx] != null)
                {
                     hashCode ^= outputSchemas[tpIdx].hashCode();
                }
            }
        }
		if(eventDecoder != null)
		{
			hashCode ^= eventDecoder.hashCode();
		}

		return hashCode;
	}
    /**
     * Get a Decoder instance for this type of Mediation Capability.
     * @return MediationCapabilityChangeEventDecoder for this object
     * @exception java.lang.IllegalStateException Thrown if the event decoder
     * has not been set
     */
    public MediationCapabilityChangeEventDecoder getChangeEventDecoder()
        throws java.lang.IllegalStateException
    {
        if(eventDecoder == null)
        {
            throw new java.lang.IllegalStateException(
                "EventDecoder attribute is not set");
            
        }
        
        return (MediationCapabilityChangeEventDecoder)
            eventDecoder.clone();
        
    }

    /**
     * Set a Decoder instance for this type of Mediation Capability.
     * Sub types of MediationCapability may need to invoke this method to
     * provide their own decoders.
     * @param changeEventDecoder MediationCapabilityChangeEventDecoder for this object
     * @exception java.lang.IllegalArgumentException  Thrown if the input param
     * is invalid
     */
    public void setChangeEventDecoder(
        MediationCapabilityChangeEventDecoder changeEventDecoder)
        throws java.lang.IllegalArgumentException
    {
        if(changeEventDecoder == null)
        {
            throw new java.lang.IllegalArgumentException(
                "EventDecoder parameter value is not valid");
        }
        
        this.eventDecoder = (MediationCapabilityChangeEventDecoder)
            changeEventDecoder.clone();
    }
}
