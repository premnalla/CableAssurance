/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.agreement;

import javax.oss.cbe.agreement.AgreementItemKeyResult;


/**
 * An implementation class for the <CODE>javax.oss.cbe.agreement.AgreementItemKeyResult</CODE> interface.  
 * 
 * @see javax.oss.cbe.agreement.AgreementItemKeyResult
 * 
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3 
 * @since January 2006 
 */


public class AgreementItemKeyResultImpl
extends ossj.common.cbe.bi.BusinessInteractionItemKeyResultImpl
implements AgreementItemKeyResult
{ 

    /**
     * Constructs a new AgreementItemKeyResultImpl with empty attributes.
     * 
     */

    public AgreementItemKeyResultImpl() {
        super();
    }




    /**
     * Returns this AgreementItemKeyResultImpl's agreementItemKey
     * 
     * @return the agreementItemKey
     * 
    */

    public javax.oss.cbe.agreement.AgreementItemKey getAgreementItemKey() {
        // Use the based MEK method
        return (javax.oss.cbe.agreement.AgreementItemKey)getManagedEntityKey();
    }

}
