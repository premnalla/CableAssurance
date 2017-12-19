/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import ossj.common.AttributeAccessImpl;
import ossj.common.AttributeManager;
import ossj.common.NamedQueryValueImpl;


/**
 * DOCUMENT ME!
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class QueryAllManagedEntitiesValue extends NamedQueryValueImpl implements javax.oss.NamedQueryValue {
    private static AttributeManager queryAttributeManager;

    // String array which conatins all attribute Names
    private static final String[] attributeNames = {  };

    // writeable attributes
    private static final String[] settableAttributeNames = {  };

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

    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
     * @throws java.lang.CloneNotSupportedException DOCUMENT ME!
     */
    public Object clone() {
        return super.clone();
    }
}
