/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */

package com.nokia.oss.ossj.sa.ri;

import javax.oss.ManagedEntityKey;

// [10.8.2] The primary key class must be public, and must have a public constructor with no parameters.
// Serializable has to be implemented explicitly, otherwise WLS won't deploy

/**
 *
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class CMPManagedEntityKey implements java.io.Serializable
{
    public String mevPrimaryKey;

    // [10.8.2] The primary key class must be public, and must have a public constructor with no parameters.
    public CMPManagedEntityKey()
    {
    }

    public CMPManagedEntityKey(ManagedEntityKey mek) {
        mevPrimaryKey = mek.getPrimaryKey().toString();
    }
    
    public CMPManagedEntityKey(String primaryKey) {
        mevPrimaryKey = primaryKey;
    }
    
    public boolean equals( Object other )
    {
        if ( other instanceof CMPManagedEntityKey )
        {
            CMPManagedEntityKey oKey = ( CMPManagedEntityKey )other;
            return mevPrimaryKey.equals( oKey.mevPrimaryKey );
        }
        return false;
    }

    /**
     *It is not required that if two objects are unequal according to the
     * <code>equals(java.lang.Object)</code> method, then calling the
     * <code>hashCode</code> method on each of the two objects must produce
     * distinct integer results. However, the programmer should be
     * aware that producing distinct integer results for unequal objects may
     * improve the performance of hashtables.
     */
    public int hashCode()
    {
        return (toString()).hashCode();
    }

    public String toString() {
        return mevPrimaryKey;
    }
}
