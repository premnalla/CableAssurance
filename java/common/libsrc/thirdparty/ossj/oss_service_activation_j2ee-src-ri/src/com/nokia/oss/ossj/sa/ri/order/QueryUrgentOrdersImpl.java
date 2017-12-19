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

import com.nokia.oss.ossj.common.ri.*;
/**
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class QueryUrgentOrdersImpl extends QueryValueImpl implements com.nokia.oss.ossj.sa.ri.order.QueryUrgentOrders {
    
    private java.util.Date requestedCompletionDateThreshold;
    
    private static AttributeManager queryAttributeManager;
    
    // String array which conatins all attribute Names
    private static final String[] attributeNames = {
        REQUESTED_COMPLETION_DATE_THRESHOLD
    };
    
    // writeable attributes
    private static final String[] settableAttributeNames = {
        REQUESTED_COMPLETION_DATE_THRESHOLD
    };
    
    /** Creates new QueryUrgentOrdersImpl */
    public QueryUrgentOrdersImpl() {
    }
    
    //
    // Configuration of AttributeManager and AttributeAccess
    //
    
    protected void addAttributesTo(AttributeManager anAttributeManager) {
        anAttributeManager.addAttributes(this.attributeNames);
        super.addAttributesTo(anAttributeManager);
    }
    
    protected void configureAttributes(AttributeManager anAttributeManager) {
        anAttributeManager.setSettableAttributes(this.settableAttributeNames);
        super.configureAttributes(anAttributeManager);
    }


    protected AttributeManager getAttributeManager() {
        return queryAttributeManager;
    }

    protected AttributeManager makeAttributeManager() {
        queryAttributeManager = new AttributeManager();
        return queryAttributeManager;
    }

    public java.util.Date getRequestedCompletionDateThreshold() {
        checkAttribute(REQUESTED_COMPLETION_DATE_THRESHOLD);
        return requestedCompletionDateThreshold;
    }
    
    public void setRequestedCompletionDateThreshold(java.util.Date newThreshold) {
        setDirtyAttribute(REQUESTED_COMPLETION_DATE_THRESHOLD);
        requestedCompletionDateThreshold = newThreshold;
    }

    public Object clone() {
        QueryUrgentOrdersImpl newQuery = (QueryUrgentOrdersImpl)this.clone();
        newQuery.requestedCompletionDateThreshold = (java.util.Date)requestedCompletionDateThreshold.clone();
        return newQuery;
    }

}
