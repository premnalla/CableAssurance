/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import ossj.common.StringMultiValueList;
import ossj.common.StringMultiValueListImpl;
import java.util.Date;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>ossj.common.ex.ManagedEntityEx1Value</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see ossj.common.ex.ManagedEntityEx1Value
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative
 * @version 1.2.2 
 * @since September 2005 
 */


public class ManagedEntityEx1ValueImpl
extends ossj.common.ManagedEntityValueImpl
implements ManagedEntityEx1Value
{ 

	/**
	 * Constructs a new ManagedEntityEx1ValueImpl with empty attributes.
	 * 
	 */

	public ManagedEntityEx1ValueImpl() {
		super();
		setManagedEntityKeyInstance( new ManagedEntityEx1KeyImpl());
	}
	
	public ManagedEntityEx1ValueImpl(ManagedEntityEx1Key value) {
		super();
		setManagedEntityKeyInstance( new ManagedEntityEx1KeyImpl());
		setManagedEntityEx1Key(value);
	}

	private static final String[] attributeNames = {
		ManagedEntityEx1Value.EX_1_API_CLIENT_ID,
		ManagedEntityEx1Value.EX_1_CREATION_DATE,
		ManagedEntityEx1Value.EX_1_DESCRIPTION,
		ManagedEntityEx1Value.EX_1_MAX_VALUE,
		ManagedEntityEx1Value.EX_1_MIN_VALUE,
		ManagedEntityEx1Value.EX_1_MULTI_VALUE_LIST,
		ManagedEntityEx1Value.EX_1_NAME,
		ManagedEntityEx1Value.EX_1_VALUE,
		ManagedEntityEx1Value.MANAGED_ENTITY_EX_1_KEY
	};

	private static final String[] settableAttributeNames = {
		ManagedEntityEx1Value.EX_1_API_CLIENT_ID,
		ManagedEntityEx1Value.EX_1_CREATION_DATE,
		ManagedEntityEx1Value.EX_1_DESCRIPTION,
		ManagedEntityEx1Value.EX_1_MAX_VALUE,
		ManagedEntityEx1Value.EX_1_MIN_VALUE,
		ManagedEntityEx1Value.EX_1_MULTI_VALUE_LIST,
		ManagedEntityEx1Value.EX_1_NAME,
		ManagedEntityEx1Value.EX_1_VALUE,
		ManagedEntityEx1Value.MANAGED_ENTITY_EX_1_KEY
	};

	//
	// Configuration of AttributeManager and AttributeAccess
	//
	protected void addAttributesTo(AttributeManager anAttributeManager) {
		if (ManagedEntityEx1ValueImpl.attributeNames != null) {
			anAttributeManager.addAttributes(this.attributeNames);
			super.addAttributesTo(anAttributeManager);
		}
	}

	protected void configureAttributes(AttributeManager anAttributeManager) {
		if (ManagedEntityEx1ValueImpl.settableAttributeNames != null) {
			anAttributeManager.setSettableAttributes(ManagedEntityEx1ValueImpl.settableAttributeNames);
			super.configureAttributes(anAttributeManager);
		}
	}

	/**
	 * Holds the Attribute manager that manage the optional fields
	*/
	private static AttributeManager attributemanager = null;

	protected AttributeManager getAttributeManager() {
		return attributemanager;
	}
	protected AttributeManager makeAttributeManager() {
		attributemanager = new AttributeManager();
		return attributemanager;
	}

	public ossj.common.ex.ManagedEntityEx1Key makeManagedEntityEx1Key(){
		return (ManagedEntityEx1Key) makeManagedEntityKey();
	}

	private java.lang.String _managedentityex1valueimpl_ex1ApiClientId = new String();
	private java.util.Date _managedentityex1valueimpl_ex1CreationDate = new Date();
	private java.lang.String _managedentityex1valueimpl_ex1Description = new String();
	private java.lang.String _managedentityex1valueimpl_ex1MaxValue = new String();
	private java.lang.String _managedentityex1valueimpl_ex1MinValue = new String();
	private ossj.common.StringMultiValueList _managedentityex1valueimpl_ex1MultiValueList = new StringMultiValueListImpl();
	private java.lang.String _managedentityex1valueimpl_ex1Name = new String();
	private java.lang.String _managedentityex1valueimpl_ex1Value = new String();


	/**
	 * Changes the ex1ApiClientId to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex1ApiClientId for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx1ApiClientId(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.EX_1_API_CLIENT_ID);
		_managedentityex1valueimpl_ex1ApiClientId = value;
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's ex1ApiClientId
	 * 
	 * @return the ex1ApiClientId
	 * 
	*/

	public java.lang.String getEx1ApiClientId() {
		checkAttribute(ManagedEntityEx1Value.EX_1_API_CLIENT_ID);
		return _managedentityex1valueimpl_ex1ApiClientId;
	}

	/**
	 * Changes the ex1CreationDate to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex1CreationDate for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx1CreationDate(java.util.Date value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.EX_1_CREATION_DATE);
		_managedentityex1valueimpl_ex1CreationDate = value;
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's ex1CreationDate
	 * 
	 * @return the ex1CreationDate
	 * 
	*/

	public java.util.Date getEx1CreationDate() {
		checkAttribute(ManagedEntityEx1Value.EX_1_CREATION_DATE);
		return _managedentityex1valueimpl_ex1CreationDate;
	}

	/**
	 * Changes the ex1Description to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex1Description for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx1Description(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.EX_1_DESCRIPTION);
		_managedentityex1valueimpl_ex1Description = value;
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's ex1Description
	 * 
	 * @return the ex1Description
	 * 
	*/

	public java.lang.String getEx1Description() {
		checkAttribute(ManagedEntityEx1Value.EX_1_DESCRIPTION);
		return _managedentityex1valueimpl_ex1Description;
	}

	/**
	 * Changes the ex1MaxValue to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex1MaxValue for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx1MaxValue(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.EX_1_MAX_VALUE);
		_managedentityex1valueimpl_ex1MaxValue = value;
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's ex1MaxValue
	 * 
	 * @return the ex1MaxValue
	 * 
	*/

	public java.lang.String getEx1MaxValue() {
		checkAttribute(ManagedEntityEx1Value.EX_1_MAX_VALUE);
		return _managedentityex1valueimpl_ex1MaxValue;
	}

	/**
	 * Changes the ex1MinValue to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex1MinValue for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx1MinValue(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.EX_1_MIN_VALUE);
		_managedentityex1valueimpl_ex1MinValue = value;
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's ex1MinValue
	 * 
	 * @return the ex1MinValue
	 * 
	*/

	public java.lang.String getEx1MinValue() {
		checkAttribute(ManagedEntityEx1Value.EX_1_MIN_VALUE);
		return _managedentityex1valueimpl_ex1MinValue;
	}

	/**
	 * Changes the ex1MultiValueList to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex1MultiValueList for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx1MultiValueList(ossj.common.StringMultiValueList value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.EX_1_MULTI_VALUE_LIST);
		_managedentityex1valueimpl_ex1MultiValueList = value;
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's ex1MultiValueList
	 * 
	 * @return the ex1MultiValueList
	 * 
	*/

	public ossj.common.StringMultiValueList getEx1MultiValueList() {
		checkAttribute(ManagedEntityEx1Value.EX_1_MULTI_VALUE_LIST);
		return _managedentityex1valueimpl_ex1MultiValueList;
	}

	/**
	 * Changes the ex1Name to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex1Name for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx1Name(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.EX_1_NAME);
		_managedentityex1valueimpl_ex1Name = value;
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's ex1Name
	 * 
	 * @return the ex1Name
	 * 
	*/

	public java.lang.String getEx1Name() {
		checkAttribute(ManagedEntityEx1Value.EX_1_NAME);
		return _managedentityex1valueimpl_ex1Name;
	}

	/**
	 * Changes the ex1Value to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex1Value for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx1Value(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.EX_1_VALUE);
		_managedentityex1valueimpl_ex1Value = value;
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's ex1Value
	 * 
	 * @return the ex1Value
	 * 
	*/

	public java.lang.String getEx1Value() {
		checkAttribute(ManagedEntityEx1Value.EX_1_VALUE);
		return _managedentityex1valueimpl_ex1Value;
	}

	/**
	 * Changes the managedEntityEx1Key to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new managedEntityEx1Key for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	 * @ossj:ignore
	*/

	public void setManagedEntityEx1Key(ossj.common.ex.ManagedEntityEx1Key value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx1Value.MANAGED_ENTITY_EX_1_KEY);
		setManagedEntityKey(value);
	}


	/**
	 * Returns this ManagedEntityEx1ValueImpl's managedEntityEx1Key
	 * 
	 * @return the managedEntityEx1Key
	 * 
	*/

	public ossj.common.ex.ManagedEntityEx1Key getManagedEntityEx1Key() {
		checkAttribute(ManagedEntityEx1Value.MANAGED_ENTITY_EX_1_KEY);
		return (ossj.common.ex.ManagedEntityEx1Key)getManagedEntityKey();
	}

	/**
	 * Creates a new object of the same class and with the same contents as this object.
	 * @return a clone of this instance.
	*/
	public Object clone() {
		try { 
			ManagedEntityEx1Value val = (ManagedEntityEx1Value)super.clone();

			if( isPopulated(ManagedEntityEx1Value.EX_1_API_CLIENT_ID)) {
					val.setEx1ApiClientId(((java.lang.String) new String( this.getEx1ApiClientId())));
			}

			if( isPopulated(ManagedEntityEx1Value.EX_1_CREATION_DATE)) {
					val.setEx1CreationDate((java.util.Date)((java.util.Date) this.getEx1CreationDate()).clone());
			}

			if( isPopulated(ManagedEntityEx1Value.EX_1_DESCRIPTION)) {
					val.setEx1Description(((java.lang.String) new String( this.getEx1Description())));
			}

			if( isPopulated(ManagedEntityEx1Value.EX_1_MAX_VALUE)) {
					val.setEx1MaxValue(((java.lang.String) new String( this.getEx1MaxValue())));
			}

			if( isPopulated(ManagedEntityEx1Value.EX_1_MIN_VALUE)) {
					val.setEx1MinValue(((java.lang.String) new String( this.getEx1MinValue())));
			}

			if( isPopulated(ManagedEntityEx1Value.EX_1_MULTI_VALUE_LIST)) {
					val.setEx1MultiValueList((ossj.common.StringMultiValueList)((ossj.common.StringMultiValueList) this.getEx1MultiValueList()).clone());
			}

			if( isPopulated(ManagedEntityEx1Value.EX_1_NAME)) {
					val.setEx1Name(((java.lang.String) new String( this.getEx1Name())));
			}

			if( isPopulated(ManagedEntityEx1Value.EX_1_VALUE)) {
					val.setEx1Value(((java.lang.String) new String( this.getEx1Value())));
			}

			if( isPopulated(ManagedEntityEx1Value.MANAGED_ENTITY_EX_1_KEY)) {
					val.setManagedEntityEx1Key((ossj.common.ex.ManagedEntityEx1Key)((ossj.common.ex.ManagedEntityEx1Key) this.getManagedEntityEx1Key()).clone());
			}

			return val;
		} catch( Exception e) {
			return null;
		}
	}

	/**
	 * Checks whether two ManagedEntityEx1Value are equal.
	 * The result is true if and only if the argument is not null 
	 * and is an ManagedEntityEx1Value object that has the arguments.
	 * 
	 * @param value the Object to compare with this ManagedEntityEx1Value
	 * @return if the objects are equal; false otherwise.
	 */

	public boolean equals (Object value) {
		if ( this == value ){ return true;}
		if ( (value != null) && ( value instanceof ManagedEntityEx1Value)) {
			ManagedEntityEx1Value argVal = (ManagedEntityEx1Value) value;
			return Utils.compareAttributeAccess( this, argVal );
		}
		return false;
	}

}