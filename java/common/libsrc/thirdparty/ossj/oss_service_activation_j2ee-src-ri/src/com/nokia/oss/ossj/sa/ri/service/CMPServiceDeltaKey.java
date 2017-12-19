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
package com.nokia.oss.ossj.sa.ri.service;

import javax.oss.*;
import javax.oss.order.*;
import javax.oss.service.*;

import com.nokia.oss.ossj.sa.ri.CMPManagedEntityKey;
/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class CMPServiceDeltaKey extends CMPManagedEntityKey {

    public String orderPrimaryKey;
    
    /** Creates new CMPServiceDeltaKey */
    public CMPServiceDeltaKey() {
    }
    
    public CMPServiceDeltaKey(ServiceKey mek, OrderKey anOrderKey) {
        super(mek);
        orderPrimaryKey = anOrderKey.getPrimaryKey().toString();
    }
    
    public CMPServiceDeltaKey(String primaryKey, String orderPrimaryKey) {
        super(primaryKey);
        this.orderPrimaryKey = orderPrimaryKey;
    }
    
    public boolean equals( Object other )
    {
        if ( other instanceof CMPServiceDeltaKey )
        {
            CMPServiceDeltaKey oKey = ( CMPServiceDeltaKey )other;
            return super.equals(other) || orderPrimaryKey.equals( oKey.orderPrimaryKey );
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
        return mevPrimaryKey+" - "+orderPrimaryKey;
    }

}
