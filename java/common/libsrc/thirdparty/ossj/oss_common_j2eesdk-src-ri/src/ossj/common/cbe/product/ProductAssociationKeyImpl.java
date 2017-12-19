/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product;

import javax.oss.cbe.product.ProductAssociationKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.ProductAssociationKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.ProductAssociationKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class ProductAssociationKeyImpl
extends ossj.common.cbe.AssociationKeyImpl
implements ProductAssociationKey
{ 

    /**
     * Constructs a new ProductAssociationKeyImpl with empty attributes.
     * 
     */

    public ProductAssociationKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        ProductAssociationKey val = null;
            val = (ProductAssociationKey)super.clone();

            return val;
    }

    /**
     * Checks whether two ProductAssociationKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an ProductAssociationKey object that has the arguments.
     * 
     * @param value the Object to compare with this ProductAssociationKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof ProductAssociationKey)) {
            ProductAssociationKey argVal = (ProductAssociationKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
