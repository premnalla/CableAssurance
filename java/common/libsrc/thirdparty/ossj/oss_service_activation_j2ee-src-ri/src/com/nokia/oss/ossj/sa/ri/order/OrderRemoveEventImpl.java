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
import com.nokia.oss.ossj.common.ri.BaseEvent;

/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class OrderRemoveEventImpl extends BaseEvent implements javax.oss.order.OrderRemoveEvent {

    private javax.oss.order.OrderValue value;
    /** Creates new OrderRemoveEventImpl */
    public OrderRemoveEventImpl(String domain, javax.oss.order.OrderValue value) {
        super(domain);
        this.value = value;
    }

    public javax.oss.order.OrderValue getOrderValue() {
        return value;
     }
}
