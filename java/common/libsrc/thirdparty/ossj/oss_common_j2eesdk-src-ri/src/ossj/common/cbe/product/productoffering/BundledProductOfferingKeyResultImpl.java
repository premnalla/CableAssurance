/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.BundledProductOfferingKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.BundledProductOfferingKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.BundledProductOfferingKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class BundledProductOfferingKeyResultImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingKeyResultImpl
implements BundledProductOfferingKeyResult
{ 

    /**
     * Constructs a new BundledProductOfferingKeyResultImpl with empty attributes.
     * 
     */

    public BundledProductOfferingKeyResultImpl() {
        super();
    }




    /**
     * Returns this BundledProductOfferingKeyResultImpl's bundledProductOfferingKey
     * 
     * @return the bundledProductOfferingKey
     * 
    */

    public javax.oss.cbe.product.productoffering.BundledProductOfferingKey getBundledProductOfferingKey() {
        // Use the based MEK method
        return (javax.oss.cbe.product.productoffering.BundledProductOfferingKey)getManagedEntityKey();
    }

}
