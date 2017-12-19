/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements ProductKeyResult
{ 

    /**
     * Constructs a new ProductKeyResultImpl with empty attributes.
     * 
     */

    public ProductKeyResultImpl() {
        super();
    }




    /**
     * Returns this ProductKeyResultImpl's productKey
     * 
     * @return the productKey
     * 
    */

    public javax.oss.cbe.product.ProductKey getProductKey() {
        // Use the based MEK method
        return (javax.oss.cbe.product.ProductKey)getManagedEntityKey();
    }

}
