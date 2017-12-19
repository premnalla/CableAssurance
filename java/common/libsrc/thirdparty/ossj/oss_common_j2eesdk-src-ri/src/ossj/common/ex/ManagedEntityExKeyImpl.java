/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import javax.oss.ApplicationContext;


/**
 * An implementation class for the <CODE>ossj.common.ex.ManagedEntityExKey</CODE> interface.
 *
 * @see ossj.common.ex.ManagedEntityExKey
 *
 * @author OSS through Java(tm) Initiative
 * @version 1.2.2
 * @since September 2005
 */
public class ManagedEntityExKeyImpl extends ossj.common.ManagedEntityKeyImpl
    implements ManagedEntityExKey {
    /**
     * Constructs a new ManagedEntityExKeyImpl with empty attributes.
     *
     */
    public ManagedEntityExKeyImpl() {
        super();
    }

    /**
     * Creates a new ManagedEntityExKeyImpl object using the given attributes
     *
     * @param anApplicationContext
     * @param appDN
     * @param MevType
     */
    public ManagedEntityExKeyImpl(ApplicationContext anApplicationContext, String appDN,
        String mevType) {
    	
        super();
        setApplicationContext(anApplicationContext);
        setApplicationDN(appDN);
        setType(mevType);
        setPrimaryKey(new String());
    }

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        try {
            ManagedEntityExKey val = (ManagedEntityExKey) super.clone();

            return val;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks whether two ManagedEntityExKey are equal.
     * The result is true if and only if the argument is not null
     * and is an ManagedEntityExKey object that has the arguments.
     *
     * @param value the Object to compare with this ManagedEntityExKey
     * @return if the objects are equal; false otherwise.
     */
    public boolean equals(Object value) {
        if (this == value) {
            return true;
        }

        if ((value != null) && (value instanceof ManagedEntityExKey)) {
            ManagedEntityExKey argVal = (ManagedEntityExKey) value;

            return super.equals(argVal);
        } else {
            return false;
        }
    }

    /** Returns a new primary key String.
     * @see ossj.common.ManagedEntityKeyImpl#makePrimaryKey()
     */
    public Object makePrimaryKey() {
        return new String();
    }
}
