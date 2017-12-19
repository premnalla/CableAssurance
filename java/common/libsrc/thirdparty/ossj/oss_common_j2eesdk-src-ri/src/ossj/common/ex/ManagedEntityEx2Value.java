/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import ossj.common.StringMultiValueList;
import java.util.Date;
import java.io.Serializable;
import javax.oss.ManagedEntityValue;


/**
 * Interface definition for the <CODE>ossj.common.ex.ManagedEntityEx2Value</CODE>.  
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2.2 
 * @since September 2005 
 * @ossj:managedentityvalue
 */


public interface ManagedEntityEx2Value
extends javax.oss.ManagedEntityValue, java.io.Serializable
{
	public static final String VALUE_TYPE = "ossj.common.ex.ManagedEntityEx2Value";


	public static final String EX_2_API_CLIENT_ID = "ex2ApiClientId";
	public static final String EX_2_CREATION_DATE = "ex2CreationDate";
	public static final String EX_2_DESCRIPTION = "ex2Description";
	public static final String EX_2_MAX_VALUE = "ex2MaxValue";
	public static final String EX_2_MIN_VALUE = "ex2MinValue";
	public static final String EX_2_MULTI_VALUE_LIST = "ex2MultiValueList";
	public static final String EX_2_NAME = "ex2Name";
	public static final String EX_2_VALUE = "ex2Value";
	public static final String MANAGED_ENTITY_EX_2_KEY = "managedEntityEx2Key";

	/**
	* Generate a new ossj.common.ex.ManagedEntityEx2Key
	*
	* @return a new ossj.common.ex.ManagedEntityEx2Key
	*/
	public ManagedEntityEx2Key makeManagedEntityEx2Key();
	
	//
	// All getters and setters for all declared attributes:
	//


	/**
	 * Changes the ex2ApiClientId to be equal to the given argument.
	 * 
	 * @param value the new ex2ApiClientId for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx2ApiClientId(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex2ApiClientId
	 * 
	 * @return the String representing the ex2ApiClientId
	 * @throws java.lang.IllegalStateException when the ex2ApiClientId can not be returned
	 * 
	*/

	public String getEx2ApiClientId()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex2CreationDate to be equal to the given argument.
	 * 
	 * @param value the new ex2CreationDate for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx2CreationDate(Date value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex2CreationDate
	 * 
	 * @return the Date representing the ex2CreationDate
	 * @throws java.lang.IllegalStateException when the ex2CreationDate can not be returned
	 * 
	*/

	public Date getEx2CreationDate()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex2Description to be equal to the given argument.
	 * 
	 * @param value the new ex2Description for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx2Description(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex2Description
	 * 
	 * @return the String representing the ex2Description
	 * @throws java.lang.IllegalStateException when the ex2Description can not be returned
	 * 
	*/

	public String getEx2Description()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex2MaxValue to be equal to the given argument.
	 * 
	 * @param value the new ex2MaxValue for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx2MaxValue(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex2MaxValue
	 * 
	 * @return the String representing the ex2MaxValue
	 * @throws java.lang.IllegalStateException when the ex2MaxValue can not be returned
	 * 
	*/

	public String getEx2MaxValue()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex2MinValue to be equal to the given argument.
	 * 
	 * @param value the new ex2MinValue for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx2MinValue(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex2MinValue
	 * 
	 * @return the String representing the ex2MinValue
	 * @throws java.lang.IllegalStateException when the ex2MinValue can not be returned
	 * 
	*/

	public String getEx2MinValue()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex2MultiValueList to be equal to the given argument.
	 * 
	 * @param value the new ex2MultiValueList for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx2MultiValueList(StringMultiValueList value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex2MultiValueList
	 * 
	 * @return the StringMultiValueList representing the ex2MultiValueList
	 * @throws java.lang.IllegalStateException when the ex2MultiValueList can not be returned
	 * 
	*/

	public StringMultiValueList getEx2MultiValueList()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex2Name to be equal to the given argument.
	 * 
	 * @param value the new ex2Name for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx2Name(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex2Name
	 * 
	 * @return the String representing the ex2Name
	 * @throws java.lang.IllegalStateException when the ex2Name can not be returned
	 * 
	*/

	public String getEx2Name()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex2Value to be equal to the given argument.
	 * 
	 * @param value the new ex2Value for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx2Value(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex2Value
	 * 
	 * @return the String representing the ex2Value
	 * @throws java.lang.IllegalStateException when the ex2Value can not be returned
	 * 
	*/

	public String getEx2Value()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the managedEntityEx2Key to be equal to the given argument.
	 * 
	 * @param value the new managedEntityEx2Key for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setManagedEntityEx2Key(ManagedEntityEx2Key value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the managedEntityEx2Key
	 * 
	 * @return the ManagedEntityEx2Key representing the managedEntityEx2Key
	 * @throws java.lang.IllegalStateException when the managedEntityEx2Key can not be returned
	 * @ossj:ignore
	 * 
	*/

	public ManagedEntityEx2Key getManagedEntityEx2Key()
		throws 	java.lang.IllegalStateException;

}
