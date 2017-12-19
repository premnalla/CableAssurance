/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.ProductCatalogKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.ProductCatalogKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.ProductCatalogKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductCatalogKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements ProductCatalogKey
{ 

    /**
     * Constructs a new ProductCatalogKeyImpl with empty attributes.
     * 
     */

    public ProductCatalogKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductCatalogKey val = null;
            val = (ProductCatalogKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductCatalogKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductCatalogKey object that has the arguments.
     * 
     * @param value the Object to compare with this ProductCatalogKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductCatalogKey)) {
            ProductCatalogKey argVal = (ProductCatalogKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
