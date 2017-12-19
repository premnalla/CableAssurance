package ossj.ttri;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import javax.oss.QueryValue;
import javax.oss.trouble.*;
import java.rmi.RemoteException;

/**
 * Home interface for the EJBean TroubleTicketBean
 *
 * This interface is the home interface for the EJBean TroubleTicketBean,
 * which in WebLogic is implemented by the code-generated container class
 * TroubleTicketBeanC. A home interface may support one or more create methods,
 * which must correspond to methods named "ejbCreate" in the EJBean.
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public interface TroubleTicketHome extends EJBHome {

    /**
     * This method corresponds to the ejbCreate method in the bean
     * "TroubleTicketBean.java".
     * The parameter sets of the two methods are identical.  When the client calls
     * <code>TroubleTicketHome.create()</code>, the container (which in WebLogic EJB is
     * also the factory) allocates an instance of the bean and
     * calls <code>TroubleTicketBean.ejbCreate()</code>
     *
     * For bean-managed persistence, <code>create()</code> returns
     * a primary key, unlike the case of container-managed
     * persistence, where it returns a void.
     *
     * @param asmtttValue
     * @return                  TroubleTicket
     * @exception               javax.ejb.CreateException
     *                          if there is an error creating the bean
     * @exception               java.rmi.RemoteException
     *                          if there is a communications or systems failure
     * @see                     examples.ejb.basic.beanManaged.TroubleTicketBean
     */
    public TroubleTicket create(TroubleTicketValue asmtttValue)
            throws CreateException, RemoteException;

    /**
     * Attempts to find the EJBean with a given Primary Key from
     * the persistent storage.
     *
     * @param asmtprimaryKey        TroubleTicketKeyImpl Primary Key
     * @return                  TroubleTicket
     * @exception               javax.ejb.FinderException
     *                          if there is an error finding the bean
     * @exception               java.rmi.RemoteException
     *                          if there is a communications or systems failure
     * @see                     examples.ejb.basic.beanManaged.TroubleTicketBean
     */
    public TroubleTicket findByPrimaryKey(TroubleTicketKeyImpl asmtprimaryKey)
            //public TroubleTicket findByPrimaryKey(String primaryKey)

            throws FinderException, RemoteException;


    /**
     * Query trouble tickets using defined QueryValue.
     *
     * @param asmtqueryValue        QueryValue - predefined query
     * @param asmtattrNames         attributes to return in query results
     * @return                  TroubleTicketValueIterator
     * @exception               java.rmi.RemoteException
     *                          if there is a communications or systems failure
     * @see                     examples.ejb.basic.beanManaged.TroubleTicketBean
     */
    public TroubleTicketValueIterator
            queryTroubleTickets(QueryValue asmtqueryValue, String[] asmtattrNames)
            throws RemoteException;


    /**
     * Get trouble tickets via their keys
     *
     * @param asmtkeys              TroubleTicketKey[] - TT Keys to get
     * @param asmtattrNames         String[] - selected attributes names to get
     * @return                  TroubleTicketValue[] - result values
     * @exception               java.rmi.RemoteException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketValue[]
            getTroubleTickets(TroubleTicketKey[] asmtkeys, String[] asmtattrNames)
            throws RemoteException;

    /**
     * Get trouble tickets via templates
     *
     * @param asmttemplates         TroubleTicketValue[] - TT templates used in selection
     * @param asmtattrNames         String[] - selected attributes names to get
     * @return                  TroubleTicketValue[] - result values
     * @exception               java.rmi.RemoteException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketValueIterator
            getTroubleTickets(TroubleTicketValue[] asmttemplates, String[] asmtattrNames)
            throws RemoteException;

    /**
     * Generic method to process TroubleTicketKey[] array
     *
     * @param asmtttKeys            TroubleTicketKey[]
     * @param asmtttValue		   TroubleTicketValue used to set values
     * @param asmtopType            operation type (enumerated type)
     * @param asmtprops             PropertyList - generic properties for the operation
     * @return                  TroubleTicketKeyResultIterator[] - results for ttKeys affect by the operation
     * @exception               java.rmi.RemoteException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketKeyResult[]
            processKeys(TroubleTicketKey[] asmtttKeys,
                        TroubleTicketValue asmtttValue,
                        int asmtopType,
                        PropertyList asmtprops)
            throws RemoteException;

    /**
     * Generic method to process TroubleTicketValue[] array
     *
     * @param asmtvalues            TroubleTicketValue[]
     * @param asmtopType            operation type (enumerated type)
     * @param asmtprops             PropertyList - generic properties for the operation
     * @return                  TroubleTicketKeyResult[] - results for ttKeys affect by the operation
     * @exception               java.rmi.RemoteException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketKeyResult[]
            processValues(TroubleTicketValue[] asmtvalues,
                          int asmtopType,
                          PropertyList asmtprops)
            throws RemoteException;


    /**
     * Generic method to process TroubleTicketKey[] array
     *
     * @param asmttemplates            TroubleTicketValue[]
     * @param asmtvalue                TroubleTicketValue
     * @param asmtfailuresOnly
     * @param asmtopType                   operation type (enumerated type)
     * @param asmtprops             PropertyList - generic properties for the operation
     * @return                  TroubleTicketKeyResultIterator[] - used by client to iterate over result set.
     * @exception               java.rmi.RemoteException
     *                          if there is a communications or systems failure
     */
    public TroubleTicketKeyResultIterator
            processTemplates(TroubleTicketValue[] asmttemplates,
                             TroubleTicketValue asmtvalue,
                             boolean asmtfailuresOnly,
                             int asmtopType,
                             PropertyList asmtprops)
            throws RemoteException;


}
