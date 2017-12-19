/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import javax.oss.ApplicationContext;


/**
 * An implementation class for the <CODE>ossj.common.ex.ManagedEntityEx1Key</CODE> interface.  
 * 
 * @see ossj.common.ex.ManagedEntityEx1Key
 * 
 * @author OSS through Java(tm) Initiative
 * @version 1.2.2 
 * @since September 2005 
 */


public class ManagedEntityEx1KeyImpl
extends ossj.common.ex.ManagedEntityExKeyImpl
implements ManagedEntityEx1Key
{ 

	/**
	 * Constructs a new ManagedEntityEx1KeyImpl with empty attributes.
	 * 
	 */

	public ManagedEntityEx1KeyImpl() {
		super();
	}

    /**
     * Creates a new ManagedEntityEx1KeyImpl object using the given attributes
     *
     * @param anApplicationContext 
     * @param appDN 
     * @param MevType
     */
    public ManagedEntityEx1KeyImpl(ApplicationContext anApplicationContext, String appDN,
        String MevType) {
        super(anApplicationContext, appDN, MevType);
    }


	/**
	 * Creates a new object of the same class and with the same contents as this object.
	 * @return a clone of this instance.
	*/
	public Object clone() {
		try { 
			ManagedEntityEx1Key val = (ManagedEntityEx1Key)super.clone();

			return val;
		} catch( Exception e) {
			return null;
		}
	}

	/**
	 * Checks whether two ManagedEntityEx1Key are equal.
	 * The result is true if and only if the argument is not null 
	 * and is an ManagedEntityEx1Key object that has the arguments.
	 * 
	 * @param value the Object to compare with this ManagedEntityEx1Key
	 * @return if the objects are equal; false otherwise.
	 */

	public boolean equals (Object value) {
		if ( this == value ){ return true;}
		if ( (value != null) && ( value instanceof ManagedEntityEx1Key)) {
			ManagedEntityEx1Key argVal = (ManagedEntityEx1Key) value;

			return super.equals(argVal);
		} else {
			return false;
		}
	}

}