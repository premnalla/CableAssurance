/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductAssociationKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductAssociationKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductAssociationKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductAssociationKeyResultImpl
extends ossj.common.cbe.AssociationKeyResultImpl
implements ProductAssociationKeyResult
{ 

    /**
     * Constructs a new ProductAssociationKeyResultImpl with empty attributes.
     * 
     */

    public ProductAssociationKeyResultImpl() {
        super();
    }




    /**
     * Returns this ProductAssociationKeyResultImpl's productAssociationKey
     * 
     * @return the productAssociationKey
     * 
    */

    public javax.oss.cbe.product.ProductAssociationKey getProductAssociationKey() {
        // Use the based MEK method
        return (javax.oss.cbe.product.ProductAssociationKey)getManagedEntityKey();
    }

}
