/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductSpecificationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductSpecificationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductSpecificationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductSpecificationKeyResultImpl
extends ossj.common.cbe.EntitySpecificationKeyResultImpl
implements ProductSpecificationKeyResult
{ 

    /**
     * Constructs a new ProductSpecificationKeyResultImpl with empty attributes.
     * 
     */

    public ProductSpecificationKeyResultImpl() {
        super();
    }




    /**
     * Returns this ProductSpecificationKeyResultImpl's productSpecificationKey
     * 
     * @return the productSpecificationKey
     * 
    */

    public javax.oss.cbe.product.ProductSpecificationKey getProductSpecificationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.product.ProductSpecificationKey)getManagedEntityKey();
    }

}
