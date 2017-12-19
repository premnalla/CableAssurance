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
public class QueryFromCreationDateImpl extends NamedQueryValueImpl implements javax.oss.NamedQueryValue {

	private static AttributeManager queryAttributeManager;
    private java.util.Date _creationDate;
    private static final String CREATIONDATE = "creationDate";

    // String array which conatins all attribute Names
    private static final String[] attributeNames = { CREATIONDATE };

    // writeable attributes
    private static final String[] settableAttributeNames = { CREATIONDATE };

    /** Creates new QueryUrgentOrdersImpl */
    public QueryFromCreationDateImpl() {
 
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
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public java.util.Date getCreationDate() {
        checkAttribute(CREATIONDATE);

        return _creationDate;
    }

    /**
     * DOCUMENT ME!
     *
     * @param date DOCUMENT ME!
     */
    public void setCreationDate(java.util.Date date) {
        setDirtyAttribute(CREATIONDATE);
        _creationDate = date;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object clone() {
        try {
            QueryFromCreationDateImpl val = (QueryFromCreationDateImpl) super.clone();

            if (isPopulated(CREATIONDATE)) {
                if (this.getCreationDate() != null) {
                    val.setCreationDate((java.util.Date)this.getCreationDate().clone());
                } else {
                    val.setCreationDate(null);
                }
            }

            return val;
        } catch (Exception e) {
            return null;
        }
    }
}
