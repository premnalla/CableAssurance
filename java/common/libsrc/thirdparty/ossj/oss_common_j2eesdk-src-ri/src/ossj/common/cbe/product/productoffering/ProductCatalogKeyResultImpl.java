/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductCatalogKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductCatalogKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.ProductCatalogKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductCatalogKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements ProductCatalogKeyResult
{ 

    /**
     * Constructs a new ProductCatalogKeyResultImpl with empty attributes.
     * 
     */

    public ProductCatalogKeyResultImpl() {
        super();
    }




    /**
     * Returns this ProductCatalogKeyResultImpl's productCatalogKey
     * 
     * @return the productCatalogKey
     * 
    */

    public javax.oss.cbe.product.productoffering.ProductCatalogKey getProductCatalogKey() {
        // Use the based MEK method
        return (javax.oss.cbe.product.productoffering.ProductCatalogKey)getManagedEntityKey();
    }

}
