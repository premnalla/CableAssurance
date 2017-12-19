/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductKeyImpl
extends ossj.common.cbe.EntityKeyImpl
implements ProductKey
{ 

    /**
     * Constructs a new ProductKeyImpl with empty attributes.
     * 
     */

    public ProductKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductKey val = null;
            val = (ProductKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductKey object that has the arguments.
     * 
     * @param value the Object to compare with this ProductKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductKey)) {
            ProductKey argVal = (ProductKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
