/*
 * Copyright (c) 2002
 * Cisco Systems, Inc., Ericsson Radio Systems AB.,
 * Motorola, Inc., NEC Corporation, Nokia Networks Oy,
 * Nortel Networks Limited, Sun Microsystems, Inc.,
 * Telcordia Technologies, Inc., Cygent, Inc.,
 * Agilent Technologies, Inc., BEA Systems, Inc.,
 * Digital Fairway Corporation, Orchestream Holdings plc.
 *
 * All rights reserved.  Use is subject to license terms.
 *
 * DOCUMENTATION IS PROVIDED "AS IS" AND ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE DISCLAIMED, EXCEPT
 * TO THE EXTENT THAT SUCH DISCLAIMERS ARE HELD TO BE LEGALLY
 * INVALID.
 */

package com.nokia.oss.ossj.sa.ri;

import javax.ejb.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/**
 *
 * @author Andreas Ebbert, Nokia Networks
 * @version 1.0.1
 */
public class UniqueKeyGeneratorBean implements SessionBean {
    
    SessionContext mySessionContext;
	private Context context;

    /** Creates new UniqueKeyGeneratorBean */
    public UniqueKeyGeneratorBean() {
    }

    private long querySequence(int increment) {
        try {
            //VP Context namingContext = new InitialContext();
            //VP DataSource ds = (DataSource)namingContext.lookup("java:comp/env/jdbc/RiDB");
            DataSource ds = (DataSource)context.lookup("jdbc/ossjsari");
            Connection conn = ds.getConnection();
            //VP boolean oldAutoCommit = conn.getAutoCommit();
            //VP conn.setAutoCommit(false);
            String newNext = "INSERT INTO UNIQUEKEY VALUES (0)";
            String setNext = "UPDATE UNIQUEKEY SET SEQUENCE = SEQUENCE + "+String.valueOf(increment);
            String getNext = "SELECT SEQUENCE FROM UNIQUEKEY";
            long sequence = 0;
            PreparedStatement psSet = conn.prepareStatement(setNext);
            PreparedStatement psGet = conn.prepareStatement(getNext);
            try {
                psSet.executeUpdate();
                ResultSet rs = psGet.executeQuery();
                rs.next();
                sequence = rs.getLong(1);
                rs.close();
            } catch (SQLException sqle) {
                PreparedStatement psNew = conn.prepareStatement(newNext);
                psNew.executeUpdate();
				//VP add close
				psNew.close();
                // sequence is still zero
            }
            psSet.close();
            psGet.close();
            //VP conn.commit();
            //VP conn.setAutoCommit(oldAutoCommit);
            conn.close();
            return sequence;
        } catch (NamingException ne) {
            try {
                mySessionContext.setRollbackOnly();
            } catch (java.lang.IllegalStateException e) {
                ne.printStackTrace();
                e.printStackTrace();
            }
        } catch (SQLException se) {
            try {
                mySessionContext.setRollbackOnly();
            } catch (java.lang.IllegalStateException e) {
                se.printStackTrace();
                e.printStackTrace();
            }
        }
        return 0L;
    }

    // REMOTE INTERFACE
    
    public String getUniqueKey() {
        return String.valueOf(querySequence(1));
    }
    
    public String[] getUniqueKeys(int amount) {
        String[] uniqueKeys = new String[amount];
        long ceiling = querySequence(amount);
        for (int i=0 ; i<amount ; i++) {
            uniqueKeys[i] = String.valueOf(ceiling-amount+i+1L);
        }
        return uniqueKeys;
    }
    
    // EJB METHODS
    
    public void ejbCreate() throws javax.ejb.CreateException {
		try {
			context = (Context) new InitialContext().lookup("java:comp/env");
		} catch (Exception e) {
			throw new CreateException("UniqueKeyGeneratorBean.ejbCreate(): " 
							  + e.getMessage());
		} 

    }
    
    public void ejbActivate() throws javax.ejb.EJBException, java.rmi.RemoteException {
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException, java.rmi.RemoteException {
    }    
    
    public void ejbRemove() throws javax.ejb.EJBException, java.rmi.RemoteException {
    }
    
    public void setSessionContext(javax.ejb.SessionContext context) throws javax.ejb.EJBException, java.rmi.RemoteException {
        mySessionContext = context;
    }
    
}
