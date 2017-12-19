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
 * Interface definition for the <CODE>ossj.common.ex.ManagedEntityEx1Value</CODE>.  
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2.2 
 * @since September 2005 
 * @ossj:managedentityvalue
 */


public interface ManagedEntityEx1Value
extends javax.oss.ManagedEntityValue, java.io.Serializable
{
	public static final String VALUE_TYPE = "ossj.common.ex.ManagedEntityEx1Value";


	public static final String EX_1_API_CLIENT_ID = "ex1ApiClientId";
	public static final String EX_1_CREATION_DATE = "ex1CreationDate";
	public static final String EX_1_DESCRIPTION = "ex1Description";
	public static final String EX_1_MAX_VALUE = "ex1MaxValue";
	public static final String EX_1_MIN_VALUE = "ex1MinValue";
	public static final String EX_1_MULTI_VALUE_LIST = "ex1MultiValueList";
	public static final String EX_1_NAME = "ex1Name";
	public static final String EX_1_VALUE = "ex1Value";
	public static final String MANAGED_ENTITY_EX_1_KEY = "managedEntityEx1Key";

	/**
	* Generate a new ossj.common.ex.ManagedEntityEx1Key
	*
	* @return a new ossj.common.ex.ManagedEntityEx1Key
	*/
	public ManagedEntityEx1Key makeManagedEntityEx1Key();
	
	//
	// All getters and setters for all declared attributes:
	//


	/**
	 * Changes the ex1ApiClientId to be equal to the given argument.
	 * 
	 * @param value the new ex1ApiClientId for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx1ApiClientId(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex1ApiClientId
	 * 
	 * @return the String representing the ex1ApiClientId
	 * @throws java.lang.IllegalStateException when the ex1ApiClientId can not be returned
	 * 
	*/

	public String getEx1ApiClientId()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex1CreationDate to be equal to the given argument.
	 * 
	 * @param value the new ex1CreationDate for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx1CreationDate(Date value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex1CreationDate
	 * 
	 * @return the Date representing the ex1CreationDate
	 * @throws java.lang.IllegalStateException when the ex1CreationDate can not be returned
	 * 
	*/

	public Date getEx1CreationDate()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex1Description to be equal to the given argument.
	 * 
	 * @param value the new ex1Description for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx1Description(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex1Description
	 * 
	 * @return the String representing the ex1Description
	 * @throws java.lang.IllegalStateException when the ex1Description can not be returned
	 * 
	*/

	public String getEx1Description()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex1MaxValue to be equal to the given argument.
	 * 
	 * @param value the new ex1MaxValue for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx1MaxValue(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex1MaxValue
	 * 
	 * @return the String representing the ex1MaxValue
	 * @throws java.lang.IllegalStateException when the ex1MaxValue can not be returned
	 * 
	*/

	public String getEx1MaxValue()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex1MinValue to be equal to the given argument.
	 * 
	 * @param value the new ex1MinValue for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx1MinValue(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex1MinValue
	 * 
	 * @return the String representing the ex1MinValue
	 * @throws java.lang.IllegalStateException when the ex1MinValue can not be returned
	 * 
	*/

	public String getEx1MinValue()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex1MultiValueList to be equal to the given argument.
	 * 
	 * @param value the new ex1MultiValueList for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx1MultiValueList(StringMultiValueList value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex1MultiValueList
	 * 
	 * @return the StringMultiValueList representing the ex1MultiValueList
	 * @throws java.lang.IllegalStateException when the ex1MultiValueList can not be returned
	 * 
	*/

	public StringMultiValueList getEx1MultiValueList()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex1Name to be equal to the given argument.
	 * 
	 * @param value the new ex1Name for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx1Name(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex1Name
	 * 
	 * @return the String representing the ex1Name
	 * @throws java.lang.IllegalStateException when the ex1Name can not be returned
	 * 
	*/

	public String getEx1Name()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the ex1Value to be equal to the given argument.
	 * 
	 * @param value the new ex1Value for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setEx1Value(String value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the ex1Value
	 * 
	 * @return the String representing the ex1Value
	 * @throws java.lang.IllegalStateException when the ex1Value can not be returned
	 * 
	*/

	public String getEx1Value()
		throws 	java.lang.IllegalStateException;

	/**
	 * Changes the managedEntityEx1Key to be equal to the given argument.
	 * 
	 * @param value the new managedEntityEx1Key for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad argument was provided to the method.
	*/

	public void setManagedEntityEx1Key(ManagedEntityEx1Key value)
		throws java.lang.IllegalArgumentException;

	/**
	 * Returns the managedEntityEx1Key
	 * 
	 * @return the ManagedEntityEx1Key representing the managedEntityEx1Key
	 * @throws java.lang.IllegalStateException when the managedEntityEx1Key can not be returned
	 * @ossj:ignore
	 * 
	*/

	public ManagedEntityEx1Key getManagedEntityEx1Key()
		throws 	java.lang.IllegalStateException;

}
