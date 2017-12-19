package ossj.ttri;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * XmlResponseIteratorIF  Interface
 *
 * The methods in this interface are the public face of XmlResponseIteratorBean.
 * The signatures of the methods are identical to those of the EJBean, except
 * that these methods throw a java.rmi.RemoteException.
 * Note that the EJBean does not implement this interface. The corresponding
 * code-generated EJBObject, XmlResponseIteratorBeanE, implements this interface and
 * delegates to the bean.
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */


public interface XmlResponseIteratorIF extends EJBObject {


    /**
     * Gets the xml response documents
     *
     * @param howMany         int Number of stringified xml response documents to return
     *                            in the getNext() method
     * @return                String xml response document
     * @exception               RemoteException if there is
     *                          a communications or systems failure
     */
    public String getNext(int howMany)
            //public String[] getNext( int howMany )
            throws java.rmi.RemoteException;

}
