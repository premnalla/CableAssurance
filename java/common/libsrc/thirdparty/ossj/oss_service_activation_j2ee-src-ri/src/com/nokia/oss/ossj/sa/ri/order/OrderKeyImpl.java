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
package com.nokia.oss.ossj.sa.ri.order;

import com.nokia.oss.ossj.common.ri.ManagedEntityKeyImpl;
import javax.oss.ApplicationContext;
import javax.oss.order.OrderKey;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class OrderKeyImpl extends ManagedEntityKeyImpl implements OrderKey, java.io.Serializable
{

    public OrderKeyImpl()
    {
    }

    public OrderKeyImpl( ApplicationContext anApplicationContext, String appTypeDN, String mevType, Object primaryKey )
    {
        super(anApplicationContext, appTypeDN, mevType, primaryKey );
    }

    public void setPrimaryKey( Object pk )
    {
        super.setPrimaryKey(pk.toString());
    }

    /**
     * Deep copy of this key
     * 
     * @return depp copy of this key
     */
    public Object clone() {
        OrderKeyImpl myClone = (OrderKeyImpl)super.clone();
        
        // nothing has to be changed since primary key is a String anyway
        
        return myClone;
    }

    /**
     * Manufacture a PrimaryKey
     * 
     * @return PrimaryKey implementation class
     */
    public Object makePrimaryKey() {
        return new String("this is not a primary key");
    }

	/**
	 * @see javax.oss.ManagedEntityKey#setType(String)
	 */
	public void setType(String type) throws IllegalArgumentException {
        // super.setType(type); is always throwing illegal argument exception
        this.type = type;
	}

}
