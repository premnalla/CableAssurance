/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import javax.oss.ApplicationContext;


/**
 * An implementation class for the <CODE>ossj.common.ex.ManagedEntityEx2Key</CODE> interface.  
 * 
 * @see ossj.common.ex.ManagedEntityEx2Key
 * 
 * @author OSS through Java(tm) Initiative
 * @version 1.2.2 
 * @since September 2005 
 */


public class ManagedEntityEx2KeyImpl
extends ossj.common.ex.ManagedEntityExKeyImpl
implements ManagedEntityEx2Key
{ 

	/**
	 * Constructs a new ManagedEntityEx2KeyImpl with empty attributes.
	 * 
	 */

	public ManagedEntityEx2KeyImpl() {
		super();
	}
    /**
     * Creates a new ManagedEntityEx2KeyImpl object using the given attributes
     *
     * @param anApplicationContext 
     * @param appDN 
     * @param MevType
     */
    public ManagedEntityEx2KeyImpl(ApplicationContext anApplicationContext, String appDN,
        String MevType) {
        super(anApplicationContext, appDN, MevType);
    }



	/**
	 * Creates a new object of the same class and with the same contents as this object.
	 * @return a clone of this instance.
	*/
	public Object clone() {
		try { 
			ManagedEntityEx2Key val = (ManagedEntityEx2Key)super.clone();

			return val;
		} catch( Exception e) {
			return null;
		}
	}

	/**
	 * Checks whether two ManagedEntityEx2Key are equal.
	 * The result is true if and only if the argument is not null 
	 * and is an ManagedEntityEx2Key object that has the arguments.
	 * 
	 * @param value the Object to compare with this ManagedEntityEx2Key
	 * @return if the objects are equal; false otherwise.
	 */

	public boolean equals (Object value) {
		if ( this == value ){ return true;}
		if ( (value != null) && ( value instanceof ManagedEntityEx2Key)) {
			ManagedEntityEx2Key argVal = (ManagedEntityEx2Key) value;

			return super.equals(argVal);
		} else {
			return false;
		}
	}

}