

package com.nokia.oss.ossj.common.ri.ex;

import com.nokia.oss.ossj.common.ri.*;

public class QueryAllManagedEntitiesValue extends AttributeAccessImpl implements javax.oss.QueryValue  {

    private static AttributeManager queryAttributeManager;
    
    // String array which conatins all attribute Names
    private static final String[] attributeNames = {
    };
    
    // writeable attributes
    private static final String[] settableAttributeNames = {
    };
    

    /** Creates new QueryAllOrdersValueImpl */
    public QueryAllManagedEntitiesValue() {
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

    public Object clone() {
        return new QueryAllManagedEntitiesValue();
    }
        
}

