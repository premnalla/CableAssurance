/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductOfferingKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductOfferingKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.ProductOfferingKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductOfferingKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements ProductOfferingKeyResult
{ 

    /**
     * Constructs a new ProductOfferingKeyResultImpl with empty attributes.
     * 
     */

    public ProductOfferingKeyResultImpl() {
        super();
    }




    /**
     * Returns this ProductOfferingKeyResultImpl's productOfferingKey
     * 
     * @return the productOfferingKey
     * 
    */

    public javax.oss.cbe.product.productoffering.ProductOfferingKey getProductOfferingKey() {
        // Use the based MEK method
        return (javax.oss.cbe.product.productoffering.ProductOfferingKey)getManagedEntityKey();
    }

}
