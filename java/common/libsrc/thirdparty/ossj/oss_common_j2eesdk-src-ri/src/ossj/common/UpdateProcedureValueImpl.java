/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;


import javax.oss.UpdateProcedureValue;
import ossj.common.AttributeManager;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.UpdateProcedureValue</CODE> interface.  
 * 
 * @see javax.oss.AttributeAccess
 * 
 * @see javax.oss.UpdateProcedureValue
 * 
 * @author OSS through Java(tm) Initiative
 * @version 1.2.2 
 * @since September 2005 
 */


public class UpdateProcedureValueImpl
extends ossj.common.AttributeAccessImpl
implements UpdateProcedureValue
{ 

	/**
	 * Constructs a new UpdateProcedureValueImpl with empty attributes.
	 * 
	 */

	public UpdateProcedureValueImpl() {
		super();
	}

	private static final String[] attributeNames = {
	};

	private static final String[] settableAttributeNames = {
	};

	//
	// Configuration of AttributeManager and AttributeAccess
	//
	protected void addAttributesTo(AttributeManager anAttributeManager) {
		if (UpdateProcedureValueImpl.attributeNames != null) {
			anAttributeManager.addAttributes(this.attributeNames);
			super.addAttributesTo(anAttributeManager);
		}
	}

	protected void configureAttributes(AttributeManager anAttributeManager) {
		if (UpdateProcedureValueImpl.settableAttributeNames != null) {
			anAttributeManager.setSettableAttributes(UpdateProcedureValueImpl.settableAttributeNames);
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



	/**
	 * Creates a new object of the same class and with the same contents as this object.
	 * @return a clone of this instance.
	*/
	public Object clone() {
		try { 
			UpdateProcedureValue val = (UpdateProcedureValue)super.clone();

			return val;
		} catch( Exception e) {
			return null;
		}
	}

	/**
	 * Checks whether two UpdateProcedureValue are equal.
	 * The result is true if and only if the argument is not null 
	 * and is an UpdateProcedureValue object that has the arguments.
	 * 
	 * @param value the Object to compare with this UpdateProcedureValue
	 * @return if the objects are equal; false otherwise.
	 */

	public boolean equals (Object value) {
		if ( this == value ){ return true;}
		if ( (value != null) && ( value instanceof UpdateProcedureValue)) {
			UpdateProcedureValue argVal = (UpdateProcedureValue) value;
			return Utils.compareAttributeAccess( this, argVal );
		}
		return false;
	}

}
