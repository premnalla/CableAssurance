/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.agreement.AgreementKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementKeyResultImpl
extends ossj.common.cbe.bi.BusinessInteractionKeyResultImpl
implements AgreementKeyResult
{ 

    /**
     * Constructs a new AgreementKeyResultImpl with empty attributes.
     * 
     */

    public AgreementKeyResultImpl() {
        super();
    }




    /**
     * Returns this AgreementKeyResultImpl's agreementKey
     * 
     * @return the agreementKey
     * 
    */

    public javax.oss.cbe.agreement.AgreementKey getAgreementKey() {
        // Use the based MEK method
        return (javax.oss.cbe.agreement.AgreementKey)getManagedEntityKey();
    }

}
