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
public class QueryOrdersByDateIntervalImpl extends QueryValueImpl implements com.nokia.oss.ossj.sa.ri.order.QueryOrdersByDateInterval {
    
    
    /** Holds value of property fromRequestedCompletionDate. */
    private java.util.Date fromRequestedCompletionDate;
    
    /** Holds value of property toRequestedCompletionDate. */
    private java.util.Date toRequestedCompletionDate;

    private static AttributeManager queryAttributeManager;
    
    // String array which conatins all attribute Names
    private static final String[] attributeNames = {
        FROM_REQUESTED_COMPLETION_DATE,
        TO_REQUESTED_COMPLETION_DATE
    };
    
    // writeable attributes
    private static final String[] settableAttributeNames = {
        FROM_REQUESTED_COMPLETION_DATE,
        TO_REQUESTED_COMPLETION_DATE
    };
    
    
    /** Creates new QueryOrdersByDateIntervalImpl */
    public QueryOrdersByDateIntervalImpl() {
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

    /** Getter for property fromRequestedCompletionDate.
     * @return Value of property fromRequestedCompletionDate.
     */
    public java.util.Date getFromRequestedCompletionDate() {
        checkAttribute(FROM_REQUESTED_COMPLETION_DATE);
        return fromRequestedCompletionDate;
    }
    
    /** Setter for property fromRequestedCompletionDate.
     * @param fromRequestedCompletionDate New value of property fromRequestedCompletionDate.
     */
    public void setFromRequestedCompletionDate(java.util.Date fromRequestedCompletionDate) {
        setDirtyAttribute(FROM_REQUESTED_COMPLETION_DATE);
        this.fromRequestedCompletionDate = fromRequestedCompletionDate;
    }
    
    /** Getter for property toRequestedCompletionDate.
     * @return Value of property toRequestedCompletionDate.
     */
    public java.util.Date getToRequestedCompletionDate() {
        checkAttribute(TO_REQUESTED_COMPLETION_DATE);
        return toRequestedCompletionDate;
    }
    
    /** Setter for property toRequestedCompletionDate.
     * @param toRequestedCompletionDate New value of property toRequestedCompletionDate.
     */
    public void setToRequestedCompletionDate(java.util.Date toRequestedCompletionDate) {
        setDirtyAttribute(TO_REQUESTED_COMPLETION_DATE);
        this.toRequestedCompletionDate = toRequestedCompletionDate;
    }
    
    public Object clone() {
        QueryOrdersByDateIntervalImpl newQuery = (QueryOrdersByDateIntervalImpl)this.clone();
        newQuery.toRequestedCompletionDate = (java.util.Date)toRequestedCompletionDate.clone();
        newQuery.fromRequestedCompletionDate = (java.util.Date)fromRequestedCompletionDate.clone();
        return newQuery;
    }

        
}
