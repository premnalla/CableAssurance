/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.SimpleProductOfferingKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.SimpleProductOfferingKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.SimpleProductOfferingKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class SimpleProductOfferingKeyResultImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingKeyResultImpl
implements SimpleProductOfferingKeyResult
{ 

    /**
     * Constructs a new SimpleProductOfferingKeyResultImpl with empty attributes.
     * 
     */

    public SimpleProductOfferingKeyResultImpl() {
        super();
    }




    /**
     * Returns this SimpleProductOfferingKeyResultImpl's simpleProductOfferingKey
     * 
     * @return the simpleProductOfferingKey
     * 
    */

    public javax.oss.cbe.product.productoffering.SimpleProductOfferingKey getSimpleProductOfferingKey() {
        // Use the based MEK method
        return (javax.oss.cbe.product.productoffering.SimpleProductOfferingKey)getManagedEntityKey();
    }

}
