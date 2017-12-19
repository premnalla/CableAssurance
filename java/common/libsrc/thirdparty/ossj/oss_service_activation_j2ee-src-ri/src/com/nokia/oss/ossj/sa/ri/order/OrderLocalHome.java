package com.nokia.oss.ossj.sa.ri.order;
import java.sql.SQLException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.oss.order.OrderValue;
import java.rmi.RemoteException;

import com.nokia.oss.ossj.sa.ri.CMPManagedEntityKey;

/**
 * @author aebbert
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface OrderLocalHome extends EJBLocalHome {
    public OrderLocal create(OrderValueImpl template) throws CreateException, RemoteException;
    public OrderLocal findByPrimaryKey(CMPManagedEntityKey orderKey) throws FinderException;
    
    // finds order in status open.running with requestedDeliveryDate before aDate
    public Collection findUrgentOrders(java.sql.Timestamp aDate) throws FinderException;
    
    // finds order in status open.running with requestedDeliveryDate bewteen from and to timestamps
    public Collection findNextOrders(java.sql.Timestamp from, java.sql.Timestamp to) throws FinderException;

    public Collection findAllOrders() throws FinderException;
    
    public Collection filterOrders(OrderValue[] orderValueTemplates) throws NamingException, SQLException;
    
    public OrderLocal findInventoryOrder() throws FinderException;

}
