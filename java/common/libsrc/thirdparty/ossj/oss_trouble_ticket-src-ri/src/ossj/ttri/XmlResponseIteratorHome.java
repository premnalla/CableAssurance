package ossj.ttri;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * XmlResponseIterator Home Interface
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public interface XmlResponseIteratorHome extends EJBHome {

    /**
     * This method corresponds to the ejbCreate method in the bean
     * "XmlResponseIteratorBean.java".
     * The parameter sets of the two methods are identical. When the client calls
     * <code>XmlResponseIteratorHome.create()</code>, the container
     * allocates an instance of the EJBean and calls <code>ejbCreate()</code>.
     *
     * @return                  XmlResponseIteratorIF
     * @exception               RemoteException if there is
     *                          a communications or systems failure
     * @exception               CreateException
     *                          if there is a problem creating the bean
     * @see                     examples.ejb20.basic.statelessSession.TraderBean
     */
    XmlResponseIteratorIF create() throws CreateException, RemoteException;


    XmlResponseIteratorIF create(Object asmtjavaTTIterator, String asmtxmlResponseRootTag,
                                 String[] asmtdesiredAttributeNames)
            throws CreateException, RemoteException;


}
