/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.policy;

import javax.oss.cbe.policy.PolicyKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.policy.PolicyKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.policy.PolicyKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class PolicyKeyResultImpl
extends ossj.common.cbe.EntityKeyResultImpl
implements PolicyKeyResult
{ 

    /**
     * Constructs a new PolicyKeyResultImpl with empty attributes.
     * 
     */

    public PolicyKeyResultImpl() {
        super();
    }




    /**
     * Returns this PolicyKeyResultImpl's policyKey
     * 
     * @return the policyKey
     * 
    */

    public javax.oss.cbe.policy.PolicyKey getPolicyKey() {
        // Use the based MEK method
        return (javax.oss.cbe.policy.PolicyKey)getManagedEntityKey();
    }

}
