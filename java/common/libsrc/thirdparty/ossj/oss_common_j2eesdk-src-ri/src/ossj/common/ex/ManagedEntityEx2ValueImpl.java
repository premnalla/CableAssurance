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
 * An implementation class for the <CODE>ossj.common.ex.ManagedEntityEx2Value</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see ossj.common.ex.ManagedEntityEx2Value
 * 
 * @see javax.oss.ManagedEntityValue
 * 
 * @author OSS through Java(tm) Initiative
 * @version 1.2.2 
 * @since September 2005 
 */


public class ManagedEntityEx2ValueImpl
extends ossj.common.ManagedEntityValueImpl
implements ManagedEntityEx2Value
{ 

	/**
	 * Constructs a new ManagedEntityEx2ValueImpl with empty attributes.
	 * 
	 */

	public ManagedEntityEx2ValueImpl() {
		super();
		setManagedEntityKeyInstance( new ManagedEntityEx2KeyImpl());
	}
	public ManagedEntityEx2ValueImpl(ManagedEntityEx2Key value) {
		super();
		setManagedEntityKeyInstance( new ManagedEntityEx2KeyImpl());
		setManagedEntityEx2Key(value);
	}

	private static final String[] attributeNames = {
		ManagedEntityEx2Value.EX_2_API_CLIENT_ID,
		ManagedEntityEx2Value.EX_2_CREATION_DATE,
		ManagedEntityEx2Value.EX_2_DESCRIPTION,
		ManagedEntityEx2Value.EX_2_MAX_VALUE,
		ManagedEntityEx2Value.EX_2_MIN_VALUE,
		ManagedEntityEx2Value.EX_2_MULTI_VALUE_LIST,
		ManagedEntityEx2Value.EX_2_NAME,
		ManagedEntityEx2Value.EX_2_VALUE,
		ManagedEntityEx2Value.MANAGED_ENTITY_EX_2_KEY
	};

	private static final String[] settableAttributeNames = {
		ManagedEntityEx2Value.EX_2_API_CLIENT_ID,
		ManagedEntityEx2Value.EX_2_CREATION_DATE,
		ManagedEntityEx2Value.EX_2_DESCRIPTION,
		ManagedEntityEx2Value.EX_2_MAX_VALUE,
		ManagedEntityEx2Value.EX_2_MIN_VALUE,
		ManagedEntityEx2Value.EX_2_MULTI_VALUE_LIST,
		ManagedEntityEx2Value.EX_2_NAME,
		ManagedEntityEx2Value.EX_2_VALUE,
		ManagedEntityEx2Value.MANAGED_ENTITY_EX_2_KEY
	};

	//
	// Configuration of AttributeManager and AttributeAccess
	//
	protected void addAttributesTo(AttributeManager anAttributeManager) {
		if (ManagedEntityEx2ValueImpl.attributeNames != null) {
			anAttributeManager.addAttributes(this.attributeNames);
			super.addAttributesTo(anAttributeManager);
		}
	}

	protected void configureAttributes(AttributeManager anAttributeManager) {
		if (ManagedEntityEx2ValueImpl.settableAttributeNames != null) {
			anAttributeManager.setSettableAttributes(ManagedEntityEx2ValueImpl.settableAttributeNames);
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

	public ossj.common.ex.ManagedEntityEx2Key makeManagedEntityEx2Key(){
		return (ManagedEntityEx2Key) makeManagedEntityKey();
	}

	private java.lang.String _managedentityex2valueimpl_ex2ApiClientId = new String();
	private java.util.Date _managedentityex2valueimpl_ex2CreationDate = new Date();
	private java.lang.String _managedentityex2valueimpl_ex2Description = new String();
	private java.lang.String _managedentityex2valueimpl_ex2MaxValue = new String();
	private java.lang.String _managedentityex2valueimpl_ex2MinValue = new String();
	private ossj.common.StringMultiValueList _managedentityex2valueimpl_ex2MultiValueList = new StringMultiValueListImpl();
	private java.lang.String _managedentityex2valueimpl_ex2Name = new String();
	private java.lang.String _managedentityex2valueimpl_ex2Value = new String();


	/**
	 * Changes the ex2ApiClientId to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex2ApiClientId for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx2ApiClientId(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.EX_2_API_CLIENT_ID);
		_managedentityex2valueimpl_ex2ApiClientId = value;
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's ex2ApiClientId
	 * 
	 * @return the ex2ApiClientId
	 * 
	*/

	public java.lang.String getEx2ApiClientId() {
		checkAttribute(ManagedEntityEx2Value.EX_2_API_CLIENT_ID);
		return _managedentityex2valueimpl_ex2ApiClientId;
	}

	/**
	 * Changes the ex2CreationDate to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex2CreationDate for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx2CreationDate(java.util.Date value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.EX_2_CREATION_DATE);
		_managedentityex2valueimpl_ex2CreationDate = value;
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's ex2CreationDate
	 * 
	 * @return the ex2CreationDate
	 * 
	*/

	public java.util.Date getEx2CreationDate() {
		checkAttribute(ManagedEntityEx2Value.EX_2_CREATION_DATE);
		return _managedentityex2valueimpl_ex2CreationDate;
	}

	/**
	 * Changes the ex2Description to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex2Description for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx2Description(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.EX_2_DESCRIPTION);
		_managedentityex2valueimpl_ex2Description = value;
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's ex2Description
	 * 
	 * @return the ex2Description
	 * 
	*/

	public java.lang.String getEx2Description() {
		checkAttribute(ManagedEntityEx2Value.EX_2_DESCRIPTION);
		return _managedentityex2valueimpl_ex2Description;
	}

	/**
	 * Changes the ex2MaxValue to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex2MaxValue for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx2MaxValue(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.EX_2_MAX_VALUE);
		_managedentityex2valueimpl_ex2MaxValue = value;
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's ex2MaxValue
	 * 
	 * @return the ex2MaxValue
	 * 
	*/

	public java.lang.String getEx2MaxValue() {
		checkAttribute(ManagedEntityEx2Value.EX_2_MAX_VALUE);
		return _managedentityex2valueimpl_ex2MaxValue;
	}

	/**
	 * Changes the ex2MinValue to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex2MinValue for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx2MinValue(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.EX_2_MIN_VALUE);
		_managedentityex2valueimpl_ex2MinValue = value;
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's ex2MinValue
	 * 
	 * @return the ex2MinValue
	 * 
	*/

	public java.lang.String getEx2MinValue() {
		checkAttribute(ManagedEntityEx2Value.EX_2_MIN_VALUE);
		return _managedentityex2valueimpl_ex2MinValue;
	}

	/**
	 * Changes the ex2MultiValueList to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex2MultiValueList for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx2MultiValueList(ossj.common.StringMultiValueList value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.EX_2_MULTI_VALUE_LIST);
		_managedentityex2valueimpl_ex2MultiValueList = value;
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's ex2MultiValueList
	 * 
	 * @return the ex2MultiValueList
	 * 
	*/

	public ossj.common.StringMultiValueList getEx2MultiValueList() {
		checkAttribute(ManagedEntityEx2Value.EX_2_MULTI_VALUE_LIST);
		return _managedentityex2valueimpl_ex2MultiValueList;
	}

	/**
	 * Changes the ex2Name to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex2Name for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx2Name(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.EX_2_NAME);
		_managedentityex2valueimpl_ex2Name = value;
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's ex2Name
	 * 
	 * @return the ex2Name
	 * 
	*/

	public java.lang.String getEx2Name() {
		checkAttribute(ManagedEntityEx2Value.EX_2_NAME);
		return _managedentityex2valueimpl_ex2Name;
	}

	/**
	 * Changes the ex2Value to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new ex2Value for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	*/

	public void setEx2Value(java.lang.String value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.EX_2_VALUE);
		_managedentityex2valueimpl_ex2Value = value;
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's ex2Value
	 * 
	 * @return the ex2Value
	 * 
	*/

	public java.lang.String getEx2Value() {
		checkAttribute(ManagedEntityEx2Value.EX_2_VALUE);
		return _managedentityex2valueimpl_ex2Value;
	}

	/**
	 * Changes the managedEntityEx2Key to be equal to the given argument.
	 * 
	 * This method do not allow a null argument.
	 * 
	 * @param value the new managedEntityEx2Key for this object.
	 * @exception java.lang.IllegalArgumentException - Is thrown to report
	 * that a bad or a null argument was provided to the method.
	 * @ossj:ignore
	*/

	public void setManagedEntityEx2Key(ossj.common.ex.ManagedEntityEx2Key value)	{
		if (value == null) {
			    throw new java.lang.IllegalArgumentException();
		}

		setDirtyAttribute(ManagedEntityEx2Value.MANAGED_ENTITY_EX_2_KEY);
		setManagedEntityKey(value);
	}


	/**
	 * Returns this ManagedEntityEx2ValueImpl's managedEntityEx2Key
	 * 
	 * @return the managedEntityEx2Key
	 * 
	*/

	public ossj.common.ex.ManagedEntityEx2Key getManagedEntityEx2Key() {
		checkAttribute(ManagedEntityEx2Value.MANAGED_ENTITY_EX_2_KEY);
		return (ossj.common.ex.ManagedEntityEx2Key)getManagedEntityKey();
	}

	/**
	 * Creates a new object of the same class and with the same contents as this object.
	 * @return a clone of this instance.
	*/
	public Object clone() {
		try { 
			ManagedEntityEx2Value val = (ManagedEntityEx2Value)super.clone();

			if( isPopulated(ManagedEntityEx2Value.EX_2_API_CLIENT_ID)) {
					val.setEx2ApiClientId(((java.lang.String) new String( this.getEx2ApiClientId())));
			}

			if( isPopulated(ManagedEntityEx2Value.EX_2_CREATION_DATE)) {
					val.setEx2CreationDate((java.util.Date)((java.util.Date) this.getEx2CreationDate()).clone());
			}

			if( isPopulated(ManagedEntityEx2Value.EX_2_DESCRIPTION)) {
					val.setEx2Description(((java.lang.String) new String( this.getEx2Description())));
			}

			if( isPopulated(ManagedEntityEx2Value.EX_2_MAX_VALUE)) {
					val.setEx2MaxValue(((java.lang.String) new String( this.getEx2MaxValue())));
			}

			if( isPopulated(ManagedEntityEx2Value.EX_2_MIN_VALUE)) {
					val.setEx2MinValue(((java.lang.String) new String( this.getEx2MinValue())));
			}

			if( isPopulated(ManagedEntityEx2Value.EX_2_MULTI_VALUE_LIST)) {
					val.setEx2MultiValueList((ossj.common.StringMultiValueList)((ossj.common.StringMultiValueList) this.getEx2MultiValueList()).clone());
			}

			if( isPopulated(ManagedEntityEx2Value.EX_2_NAME)) {
					val.setEx2Name(((java.lang.String) new String( this.getEx2Name())));
			}

			if( isPopulated(ManagedEntityEx2Value.EX_2_VALUE)) {
					val.setEx2Value(((java.lang.String) new String( this.getEx2Value())));
			}

			if( isPopulated(ManagedEntityEx2Value.MANAGED_ENTITY_EX_2_KEY)) {
					val.setManagedEntityEx2Key((ossj.common.ex.ManagedEntityEx2Key)((ossj.common.ex.ManagedEntityEx2Key) this.getManagedEntityEx2Key()).clone());
			}

			return val;
		} catch( Exception e) {
			return null;
		}
	}

	/**
	 * Checks whether two ManagedEntityEx2Value are equal.
	 * The result is true if and only if the argument is not null 
	 * and is an ManagedEntityEx2Value object that has the arguments.
	 * 
	 * @param value the Object to compare with this ManagedEntityEx2Value
	 * @return if the objects are equal; false otherwise.
	 */

	public boolean equals (Object value) {
		if ( this == value ){ return true;}
		if ( (value != null) && ( value instanceof ManagedEntityEx2Value)) {
			ManagedEntityEx2Value argVal = (ManagedEntityEx2Value) value;
			return Utils.compareAttributeAccess( this, argVal );
		}
		return false;
	}

}