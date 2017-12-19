/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductSpecificationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductSpecificationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductSpecificationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductSpecificationKeyImpl
extends ossj.common.cbe.EntitySpecificationKeyImpl
implements ProductSpecificationKey
{ 

    /**
     * Constructs a new ProductSpecificationKeyImpl with empty attributes.
     * 
     */

    public ProductSpecificationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductSpecificationKey val = null;
            val = (ProductSpecificationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductSpecificationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductSpecificationKey object that has the arguments.
     * 
     * @param value the Object to compare with this ProductSpecificationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductSpecificationKey)) {
            ProductSpecificationKey argVal = (ProductSpecificationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
