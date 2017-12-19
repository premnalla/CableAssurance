/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.product.productoffering;

import javax.oss.cbe.product.productoffering.SimpleProductOfferingKey;


/**
 * An implementation class for the <CODE>javax.oss.cbe.product.productoffering.SimpleProductOfferingKey</CODE> interface.  
 * 
 * @see javax.oss.cbe.product.productoffering.SimpleProductOfferingKey
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class SimpleProductOfferingKeyImpl
extends ossj.common.cbe.product.productoffering.ProductOfferingKeyImpl
implements SimpleProductOfferingKey
{ 

    /**
     * Constructs a new SimpleProductOfferingKeyImpl with empty attributes.
     * 
     */

    public SimpleProductOfferingKeyImpl() {
        super();
    }



    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        SimpleProductOfferingKey val = null;
            val = (SimpleProductOfferingKey)super.clone();

            return val;
    }

    /**
     * Checks whether two SimpleProductOfferingKey are equal.
     * The result is true if and only if the argument is not null 
     * and is an SimpleProductOfferingKey object that has the arguments.
     * 
     * @param value the Object to compare with this SimpleProductOfferingKey
     * @return if the objects are equal; false otherwise.
     */

    public boolean equals (Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof SimpleProductOfferingKey)) {
            SimpleProductOfferingKey argVal = (SimpleProductOfferingKey) value;

            return super.equals(argVal);
        } else {
            return super.equals(value);
        }
    }

}
