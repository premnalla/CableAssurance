
/**
 * Copyright ÉD 2002 NEC America, Inc. All rights reserved.
 * Use is subject to license terms.
 */
package com.nec.oss.ipb.query.ri;

/**
 * Standard imports
 */
import java.lang.reflect.Array;

/**
 * Spec imports.
 */
import javax.oss.Serializer;
import javax.oss.SerializerFactory;
import javax.oss.ipb.query.QueryProducersByMediationCapability;
import javax.oss.ipb.producer.MediationCapability;

import javax.oss.ipb.producer.ProducerKey;
import javax.oss.ipb.type.UsageRecFilterValue;

/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.AttributeAccessImpl;
import com.nokia.oss.ossj.common.ri.AttributeManager;
import com.nec.oss.cfi.activity.ri.QueryActivityReportDataImpl;


/**
 * A javax.oss.ipb.query.QueryProducersByMediationCapability
 * interface implementation.
 * <p>
 * A Query operation using such a query may result in zero, one or multiple
 * <CODE>Producer</CODE> Entities to be returned.
 * <p>
 * For Query expression purposes
 * all attributes of <CODE>MeidationCapability</CODE> instnace are
 * concatenated, using logical AND operation.
 * <p>
 *
 * @see  javax.oss.ipb.producer.MediationCapability
 * @see  javax.oss.ipb.producer.JVTProducerSession
 *
 * @author Shyam Chintalapati
 * @version   %I%, %G%
 */
public class QueryProducersByMediationCapabilityImpl
extends QueryActivityReportDataImpl
implements QueryProducersByMediationCapability
{
    private static AttributeManager medCapAttributeManager;
    private static String[] noSupportedSerializerTypes = new String[0];

    
    // String array which conatins all attribute Names
    private static final String[] medCapAttributeNames =
    {
        MEDIATION_CAPABILITY_VALUES
    };
    
    // writeable attributes
    private static final String[] settableMedCapAttributeNames =
    {
        MEDIATION_CAPABILITY_VALUES
    };

    /**
     * Member data.
     */

    /**
     * MediationCapability values attribute.
     */
    protected MediationCapability[] medCapValues;

    /**
     * join operand value attribute.
     */
    protected int joinOperand = JOIN_OPERAND_AND;

	/**
	 * Make instances of <CODE>MediationCapability</CODE> of specified type.
     * A Factory Method.
     *
     * @param mediationCapabilityType Specifies which type of Mediation
     * Capability should be instantiated. The supported types of Mediation
     * Capabilities can be obtained by a client by invoking
     * {@link javax.oss.ipb.producer.JVTProducerSession#getMediationCapabilityTypes} method.
	 *
     @param how_many Specifies how many instances to create.
     *
	 * @return An array of <CODE>how_many</CODE> empty instances
     * of specified Mediation Capability type.
	 *
	 */
	public MediationCapability[] makeMediationCapabilityValues(
        String mediationCapabilityType,
        int how_many)
    {
        if(how_many < 0)
        {
            how_many = 0;
        }
        
        ClassLoader cl = getClass().getClassLoader();
        Class medCapClass = null;
        try
        {
            medCapClass = cl.loadClass(mediationCapabilityType);

            return (MediationCapability[]) Array.newInstance(
                medCapClass, how_many);
            
        }
        catch (Exception e)
        {
            throw new java.lang.IllegalArgumentException(
                "Unknown MediationCapability Type" + e);
        }
    }
    
    
	/**
	 * Gets the <CODE>mediationcapability</CODE> filterable attribute value.
	 * 
	 * @return The set of instances of <CODE>MediationCapability</CODE>, used in
     * setting up the query filter expression.
	 *
	 * @exception java.lang.IllegalStateException If the attribute is not
     * set or not initialized.
	 *
	 * @see  MediationCapability
	 * @see javax.oss.ipb.producer.ProducerValue
	 */
	public MediationCapability[] getMediationCapabilityValues()
		throws java.lang.IllegalStateException
    {
        if(medCapValues == null)
        {
            throw new java.lang.IllegalStateException(
                "MediationCapability array attirbute is not yet set");
        }

        MediationCapability[] result =
            (MediationCapability[]) medCapValues.clone();
        
        for(int i = 0; i < medCapValues.length; ++i)
        {
            result[i] = (MediationCapability)
                medCapValues[i].clone();
        }
        
        return result;
    }
    

	/**
	 * Sets the <CODE>MediationCapability</CODE> filterable attribute value.
	 * 
	 * @param mCaps  The set of instances of
     * <CODE>MediationCapability</CODE>, used in
     * setting up the query filter expression.
	 *
	 * @exception java.lang.IllegalArgumentException If the passed
     * <CODE>mCaps</CODE> argument is malformed.
	 *
	 * @see MediationCapability
	 * @see javax.oss.ipb.producer.ProducerValue
	 */
	public void setMediationCapabilityValues(
        MediationCapability[] mCaps) 
		throws java.lang.IllegalArgumentException
    {
        if(mCaps == null)
        {
            throw new java.lang.IllegalArgumentException(
                "MediationCapability array parameter value is invalid");
        }

        medCapValues = (MediationCapability[]) mCaps.clone();

        for(int i = 0; i < medCapValues.length; ++i)
        {
            if(mCaps[i] == null)
            {
                throw new java.lang.IllegalArgumentException(
                    "MediationCapability array element at index [" +
                    i + "] is invalid");
            }
            
            medCapValues[i] = (MediationCapability) mCaps[i].clone();
        }
    }
    

    /**
     * Gets the type of the boolean expression used to join the
     * multiple mediation values, if present. The default value is
     * is boolean AND. The valid values are "boolean AND|boolean OR".
     * @return The current join operand for the query.
     *
     * @exception java.lang.IllegalStateException If the attribute is not
     * set or not initialized.
     */
    public int getJoinOperand()
    {
        return joinOperand;
    }
    

    /**
     * Gets the type of the boolean expression used to join the
     * multiple mediation values, if present. The default value is
     * is boolean AND. The valid values are "boolean AND|boolean OR".
     * @param booleanOperandType  The join operand to use in the query
     *
     * @exception java.lang.IllegalArgumentException If the passed
     * <CODE>booleanOperandType</CODE> argument is not valid.
     */
    public void setJoinOperand(
        int booleanOperandType)
        throws java.lang.IllegalArgumentException
    {
        if( (booleanOperandType != JOIN_OPERAND_AND) &&
            (booleanOperandType != JOIN_OPERAND_OR) )
        {
            throw new java.lang.IllegalArgumentException(
                "JoinOperand parameter value is invalid");
            
        }
        joinOperand = booleanOperandType;
        
    }
    
    /**
     * Configuration of AttributeManager and AttributeAccess
     */
    protected void addAttributesTo(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.addAttributes(
            this.medCapAttributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    protected void configureAttributes(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.setSettableAttributes(
            this.settableMedCapAttributeNames);
        super.configureAttributes(anAttributeManager);
    }


    protected AttributeManager getAttributeManager()
    {
        return medCapAttributeManager;
    }

    protected AttributeManager makeAttributeManager()
    {
        medCapAttributeManager = new AttributeManager();
        return medCapAttributeManager;
    }

    /**
     * Deep copy of this object.
     * 
     * @return deep copy of this object.
     */
    public Object clone()
    {
        QueryProducersByMediationCapabilityImpl clone =

            (QueryProducersByMediationCapabilityImpl) super.clone();

        if(medCapValues != null)
        {
            clone.medCapValues =
                (MediationCapability []) medCapValues.clone();

            for(int i = 0; i < medCapValues.length; ++i)
            {
                /**
                 * clone each element.
                 */
                clone.medCapValues[i] = (MediationCapability)
                    medCapValues[i].clone();
                
            }
            
        }
 
        return clone;
    }

    /**
     * Compare two instances of this class.
     *
     * @param other The other instance.
     *
     * @return boolean If they are equal returns <CODE>true</CODE>.
     * Otherwise returns <CODE>false</CODE>.
     *
     */
    public boolean equals(Object other)
    {
        if(other == this)
        {
            return true;
        }

        if(!(other instanceof QueryProducersByMediationCapabilityImpl))
        {
            return false;
        }

        QueryProducersByMediationCapabilityImpl realOther =
            (QueryProducersByMediationCapabilityImpl) other;

        if(realOther.hashCode() == hashCode())
        {
            boolean result = true;

			if(result == true)
			{
				if(joinOperand != realOther.joinOperand)
				{
					result = false;
				}
			}
            
            if(result == true)
            {
                if((medCapValues != null) &&
                   (realOther.medCapValues != null))
                {
                    for(int i = 0; i < medCapValues.length; ++i)
                    {
                        
                        if(medCapValues[i] != null)
                        {
                            if(!(medCapValues[i].equals(
                                realOther.medCapValues[i])))
                            {
                                result = false;
                                break;
                            }
                        }
                        else
                        {
                            result = (medCapValues[i] ==
                                      realOther.medCapValues[i]);
                        }
                    }
                    

                }
                else
                {
                    result = (medCapValues == realOther.medCapValues);
                }
            }

            return result;
        }
		else
		{
			return false;
		}

    }

    /**
     * It is not required that if two objects are unequal according to the
     * <code>equals(java.lang.Object)</code> method, then calling the
     * <code>hashCode</code> method on each of the two objects must produce
     * distinct integer results. However, the programmer should be
     * aware that producing distinct integer results for unequal objects may
     * improve the performance of hashtables.
     * @return hashCode integer for the object
     */
    public int hashCode()
    {
        int hashCode = -1;
		boolean hashCodeFlag = false;

        if(medCapValues != null)
        {
            int tpLen=medCapValues.length;
            for(int tpIdx=0; tpIdx<tpLen; tpIdx++)
            {
                if(medCapValues[tpIdx] != null)
                {
					if(hashCodeFlag)
					{
                    	hashCode ^= medCapValues[tpIdx].hashCode();
					}
					else
					{
                    	hashCode = medCapValues[tpIdx].hashCode();
						hashCodeFlag = true;
					}
                }
            }
        }

        return hashCode;
    }

    /**
     * Returns a string representation of this instance.
     * @return string representation of this instance.
     */
    public String toString()
    {
        if(medCapValues == null)
        {
            return "medCapValues: " + medCapValues;
        }

        StringBuffer sb = new StringBuffer(500);
        sb.append("Join Operand: " +
                  ((joinOperand == JOIN_OPERAND_AND) ? "AND" : "OR"));
                  
        sb.append("Mediation Capabilities Array Length: " +
                  medCapValues.length +
                  "\n");
        
        for(int i = 0; i < medCapValues.length; ++i)
        {
            sb.append("[" + i+1 + "]\t" + medCapValues[i] + "\n");
        }

        return sb.toString();
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
            QueryProducersByMediationCapabilityImpl.class.getName() +
            "Class.");
    }
}
