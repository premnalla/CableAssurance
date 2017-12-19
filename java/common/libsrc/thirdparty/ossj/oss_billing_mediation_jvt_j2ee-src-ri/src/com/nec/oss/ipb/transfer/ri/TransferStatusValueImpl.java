package com.nec.oss.ipb.transfer.ri;

/**
 * Title:        JSR130 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      NEC America, Inc.
 * @author Shyam Chintalapati
 * @version 0.1
 */

/**
 * Spec. imports.
 */

import javax.oss.ApplicationContext;
import javax.oss.ManagedEntityKey;

import javax.oss.ipb.transfer.TransferStatusKey;
import javax.oss.ipb.transfer.TransferStatusValue;

import com.nec.oss.cfi.activity.ri.ActivityReportParamsImpl;

import javax.oss.cfi.activity.ActivityReportParams;


/**
 * RI imports.
 */
import com.nokia.oss.ossj.common.ri.ManagedEntityValueImpl;
import com.nokia.oss.ossj.common.ri.AttributeManager;

/**
 * Implementaion of TransferStatusValue interface.
 */
public class TransferStatusValueImpl
extends ManagedEntityValueImpl
implements TransferStatusValue
{
    protected static AttributeManager transferStatusAttributeManager;

    /**
     * String array which contains all attribute names
     */
    private static final String [] transferStatusAttributeNames =
    {
        TRANSFER_STATUS_KEY,
        TRANSFER_STATUS_CODE,
		TRANSFER_FAILURE_REASON,
		REPORTING_PARAMS
    };

    /**
     * String array which contains names of all settable attributes.
     */
    private static final String [] transferStatusSettableAttributeNames =
    {
        TRANSFER_STATUS_KEY,
        TRANSFER_STATUS_CODE,
		TRANSFER_FAILURE_REASON,
		REPORTING_PARAMS
    };

    /**
     * Value object.
     */
    protected TransferStatusKey transferStatusKey=null;
    protected int transferStatus=TransferStatusValue.TRANSFER_STATUS_NOT_SET;
    protected String transferFailureReason=null;
	protected ActivityReportParams reportingParameters=null;


    /**
     * Default Constructor
     */
    public TransferStatusValueImpl()
    {
		// perform no action
    }

    /**
	 * Method for adding attributes to this object.
	 * 
	 * @param anAttributeManager AttributeManager containing attributes
     */
    protected void addAttributesTo(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.addAttributes(
            this.transferStatusAttributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    /**
	 * Method for configuring attributes to this object.
	 * 
	 * @param anAttributeManager AttributeManager containing attributes
     */
    protected void configureAttributes(
        AttributeManager anAttributeManager)
    {
        anAttributeManager.setSettableAttributes(
            this.transferStatusSettableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }

    /**
	 * Method for retrieving attributes of this object.
	 * 
	 * @return AttributeManager for this object
     */
    protected AttributeManager getAttributeManager()
    {
        return transferStatusAttributeManager;
    }

    /**
	 * Create the attribute manager for this object.
	 * 
	 * @return new AttributeManager for this object
     */
    protected AttributeManager makeAttributeManager()
    {
        transferStatusAttributeManager = new AttributeManager();
        return transferStatusAttributeManager;
    }

    /** Setter for property managedEntityKeyDummy.
     *
     * @param managedEntityKeyDummy New value of property managedEntityKeyDummy.
     */
    public void setManagedEntityKeyDummy(
        javax.oss.ManagedEntityKey managedEntityKeyDummy)
    {
        if (managedEntityKeyDummy instanceof TransferStatusKey)
        {
            super.setManagedEntityKeyDummy(managedEntityKeyDummy);
        }
    }
    
    /**
	 * Method for retrieving the managed entity key of this object.
	 * 
	 * @return ManagedEntityKey for this object
     * @exception java.lang.IllegalStateException If the
     * <CODE>key</CODE> attribute is not yet set. 
     */
    public ManagedEntityKey getManagedEntityKey()
        throws java.lang.IllegalStateException
    {
    	checkAttribute(KEY);
        return transferStatusKey;
    }
         
    /**
	 * Method for setting the managed entity key of this object.
	 * 
     * @param managedEntityKey A valid instance of <CODE>TransferStatusKey</CODE>.
     *
     * @exception java.lang.IllegalArgumentException If the
     * argument value not valid.
     */
    public void setManagedEntityKey(
        ManagedEntityKey managedEntityKey) 
        throws java.lang.IllegalArgumentException
    {
    	if (managedEntityKey instanceof TransferStatusKey)
        {
        	setDirtyAttribute(KEY);
        	this.transferStatusKey = (TransferStatusKey) managedEntityKey;
        } 
        else
        {
            throw new java.lang.IllegalArgumentException(
                "Not the correct type of key");
        }
    }

    /**
	 * Create the ManagedEntityKey for this object.
	 * 
	 * @return new ManagedEntityKey for this object
     */
    public ManagedEntityKey makeManagedEntityKey()
    {
        if ( getManagedEntityKeyDummy() != null )
        {
            return super.makeManagedEntityKey() ;
        }
        
        return  new TransferStatusKeyImpl() ;
    }

	/**
	 * Factory method for making an instance of
     * <CODE>TransferStatusKey</CODE>, an attribute of
     * this instance.
	 * 
	 * @return An new unfilled instance of <CODE>TransferStatusKey</CODE>.
	 *
	 * @see javax.oss.ipb.transfer.TransferStatusKey
	 */
	public TransferStatusKey makeTransferStatusKey()
    {
        return (TransferStatusKey) makeManagedEntityKey();
    }

	/**
	 * Get the <CODE>TransferStatus</CODE> entity's key attribute value.
	 * 
	 * @return The key value instance of the <CODE>TransferStatus</CODE> Entity's key.
	 *
	 * @exception java.lang.IllegalStateException If the <CODE>TransferStatusKey</CODE>
     * attribute value is not yet filled in.
	 *
	 * @see javax.oss.ipb.transfer.TransferStatusKey
	 */
	public TransferStatusKey getTransferStatusKey()
        throws java.lang.IllegalStateException
    {
        return (TransferStatusKey) getManagedEntityKey();
    }
    
	/**
	 * Set the <CODE>TransferStatus</CODE> entity's key attribute value.
	 * 
	 * @param key The key attribute value of the <CODE>TransferStatus</CODE> Entity.
	 *
	 * @exception java.lang.IllegalArgumentException If
     * the <CODE>TransferStatusKey</CODE> attribute value provided as the argument to
     * this method is not well formed.
	 *
	 * @see javax.oss.ipb.transfer.TransferStatusKey
	 */
	public void setTransferStatusKey(TransferStatusKey key) 
		throws java.lang.IllegalArgumentException
    {
        setManagedEntityKey(key);
    }

    /**
     * Get the status of the transfer.
     *
     * @return Value of the TransferStatus
     * @exception java.lang.IllegalStateException If the
     * transferStatus attribute is not yet set.
     */
    public int getTransferStatusCode()
        throws java.lang.IllegalStateException
    {
        checkAttribute(TRANSFER_STATUS_CODE);
        return transferStatus;
    }
    
    /**
     * Set the value of the TransferStatus code.
     *
     * @param statusCode Status of the transfer
     * @exception java.lang.IllegalArgumentException If this
     * <CODE>statusCode</CODE> parameter value not one of the recognized values.
     */
    public void setTransferStatusCode(int statusCode)
        throws java.lang.IllegalArgumentException
    {
        if( (statusCode != TransferStatusValue.TRANSFER_STATUS_NOT_SET) &&
        	(statusCode != TransferStatusValue.TRANSFER_STATUS_IN_PROGRESS) &&
        	(statusCode != TransferStatusValue.TRANSFER_STATUS_COMPLETED) &&
        	(statusCode != TransferStatusValue.TRANSFER_STATUS_FAILED) )
        {
            throw new java.lang.IllegalArgumentException(
                "transferStatus value is invalid:" + statusCode);
        }
        setDirtyAttribute(TRANSFER_STATUS_CODE);
        this.transferStatus = statusCode;
    }

    /**
     * Get the reason for the transfer failure
     *
     * @return Reason for the transfer failure
     * @exception java.lang.IllegalStateException If this
     * <CODE>transferFailureReason</CODE> attribute value not yet set.
     */
    public String getTransferFailureReason()
        throws java.lang.IllegalStateException
    {
        checkAttribute(TRANSFER_FAILURE_REASON);
        return transferFailureReason;
    }
    
    /**
     * Set the reason of the transfer failure
     *
     * @param failureReason A String, indicating the
     * reason for the failure of the transfer.
     *
     * @exception java.lang.IllegalArgumentException If this
     * <CODE>failureReason</CODE> parameter value not valid.
     */
    public void setTransferFailureReason(String failureReason)
        throws java.lang.IllegalArgumentException
    {
        if( (failureReason == null) ||
            (failureReason.length() == 0) )
        {
            throw new java.lang.IllegalArgumentException(
                "failureReason value is invalid");
        }
        setDirtyAttribute(TRANSFER_FAILURE_REASON);
        this.transferFailureReason = failureReason;
    }

    /**
     * Make an empty ActivityReportParams instance.
     *
     * @return An empty instance of ActivityReportParams.
     */
    public ActivityReportParams makeReportingParams()
    {
        return ( new ActivityReportParamsImpl());
    }
    
    /**
     * Get the reporting parameters for this activity.
     * An array of reporting parameters is returned. It is possible
     * that one class of activity may be able to support different
     * reporting schemes simultaneously. Ultimately, the JVT Session impl. is
     * responsible for validating these values, before creating this entity.
     *
     * @return Reporting parameters for this object
     * @exception java.lang.IllegalStateException If the
     * <CODE>reportingParams</CODE> attribute is not yet set.
     */
    public ActivityReportParams getReportingParams()
        throws java.lang.IllegalStateException
    {
        checkAttribute(REPORTING_PARAMS);

        ActivityReportParams reportParams =
            (ActivityReportParams)reportingParameters.clone();

        return reportParams;
    }
    
    /**
     * Set the Activity Reporting Parameters, for this activity.
     * <p>
     * If an implementation does not support handling of specified
     * activity Reporting
     * parameters, it must throw IllegalArgumentException at the time
     * of creation of Activity Entity.
     * <p>
     * Note that in an implementation
     * a subset of reporting parameters may make sense, but this may
     * not necessarily true for just any permutation of reporting parameters.
     * In general, the implementation must check for such a partial support
     * at the entity creation time and must throw IllegalArgumentException
     * wherever and whenever appropriate.
     *
     * @param params New reporting parameters
     *
     * @exception java.lang.IllegalArgumentException If the
     * <CODE>reportingParams</CODE> parameter is not valid.
     */
    public void setReportingParams(ActivityReportParams params)
        throws java.lang.IllegalArgumentException
    {
        if (params == null)
        {
            throw new java.lang.IllegalArgumentException(
                "ActivityReportParams parameter value is invalid");
        }

        this.reportingParameters = (ActivityReportParams) params.clone();

        setDirtyAttribute(REPORTING_PARAMS);
    }


    /**
     * Deep copy of this key
     * 
     * @return deep copy of this key
     */
    public Object clone()
    {

        TransferStatusValueImpl myClone = (TransferStatusValueImpl)super.clone();


        if(transferStatusKey != null)
        {
            myClone.transferStatusKey =
					(TransferStatusKey) transferStatusKey.clone(); 
        }
        
        if(reportingParameters != null)
        {
            myClone.reportingParameters =
					(ActivityReportParams) reportingParameters.clone();
        }


        return myClone;
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

        if(!(other instanceof TransferStatusValueImpl))
        {
            return false;
        }

        TransferStatusValueImpl realOther =
            (TransferStatusValueImpl) other;


        if(realOther.hashCode() == hashCode())
        {

            boolean result = true;

			if(transferStatus != realOther.transferStatus)  
			{
				result = false;
			}
            
            if(result == true)
            {
                if(transferStatusKey != null)
                {
                    result =
                        transferStatusKey.equals(realOther.transferStatusKey);
                }
                else
                {
                    result = (transferStatusKey == realOther.transferStatusKey);
                }
            }

            if(result == true)
            {
                if(reportingParameters != null)
                {
                    result =
                        reportingParameters.equals(realOther.reportingParameters);
                }
                else
                {
                    result = (reportingParameters == realOther.reportingParameters);
                }
            }

			if(result == true)
			{
				if(transferFailureReason != null)
				{
					result = transferFailureReason.equals
							(realOther.transferFailureReason);
				}
				else
				{
					result = (transferFailureReason == 
								realOther.transferFailureReason);
				}
			}

	        return result;
        }

        return false;
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
        if(transferStatusKey != null)
        {
            hashCode = transferStatusKey.hashCode();
        }

        if(transferFailureReason != null)
        {
            hashCode ^= transferFailureReason.hashCode();
        }

        if(reportingParameters != null)
        {
            hashCode ^= reportingParameters.hashCode();
        }

        return hashCode;
    }
}
