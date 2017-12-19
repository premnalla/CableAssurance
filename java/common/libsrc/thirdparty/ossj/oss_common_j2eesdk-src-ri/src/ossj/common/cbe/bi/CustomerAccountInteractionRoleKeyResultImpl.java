/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.bi;

import javax.oss.cbe.bi.CustomerAccountInteractionRoleKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.bi.CustomerAccountInteractionRoleKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.bi.CustomerAccountInteractionRoleKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class CustomerAccountInteractionRoleKeyResultImpl
extends ossj.common.cbe.bi.BusinessInteractionRoleKeyResultImpl
implements CustomerAccountInteractionRoleKeyResult
{ 

    /**
     * Constructs a new CustomerAccountInteractionRoleKeyResultImpl with empty attributes.
     * 
     */

    public CustomerAccountInteractionRoleKeyResultImpl() {
        super();
    }




    /**
     * Returns this CustomerAccountInteractionRoleKeyResultImpl's customerAccountInteractionRoleKey
     * 
     * @return the customerAccountInteractionRoleKey
     * 
    */

    public javax.oss.cbe.bi.CustomerAccountInteractionRoleKey getCustomerAccountInteractionRoleKey() {
        // Use the based MEK method
        return (javax.oss.cbe.bi.CustomerAccountInteractionRoleKey)getManagedEntityKey();
    }

}
