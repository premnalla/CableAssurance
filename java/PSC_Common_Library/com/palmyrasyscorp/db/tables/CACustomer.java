/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.common.ProxyDbStatement;

/**
 * @author Prem
 *
 */
public class CACustomer extends AbstractDbObject {

	private static Log log = LogFactory.getLog(CACustomer.class);

	private Integer m_id;
	public Integer getCustomerId() {return m_id;}
	
	private String m_accountNumber;
	public String getAccountNumber() { return m_accountNumber; }
	
	private String m_addrStreet1;
	private String m_addrStreet2;
	private String m_addrCity;
	private String m_addrZip;
	private String m_addrPhone1;
	private String m_addrPhone2;
	public String getAddressAndPhone() {
		StringBuffer ret = new StringBuffer();
		if (m_addrStreet1 != null) {
			ret.append(m_addrStreet1).append(", ");
		}
		if (m_addrStreet2 != null) {
			ret.append(m_addrStreet2).append(", ");
		}
		if (m_addrCity != null) {
			ret.append(m_addrCity).append(", ");
		}
		if (m_addrZip != null) {
			ret.append(m_addrZip).append(", ");
		}
		if (m_addrPhone1 != null) {
			ret.append(m_addrPhone1);
		}
		return (ret.toString());
	}
	

	/**
	 * 
	 *
	 */
	protected CACustomer() {
		
	}
	
	
	/**
	 * 
	 * @param rs
	 */
	public CACustomer(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (Integer) rs.getObject(i++);
			m_accountNumber = (String) rs.getObject(i++);
			i++;
			i++;
			i++;
			i++;
			m_addrStreet1 = rs.getString(i++);
			m_addrStreet2 = rs.getString(i++);
			m_addrCity = rs.getString(i++);
			m_addrZip = rs.getString(i++);
			m_addrPhone1 = rs.getString(i++);
			m_addrPhone2 = rs.getString(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}
	
	
	/**
	 * 
	 * @param resId
	 * @return
	 */
	static public CACustomer getCustomerForResource(BigInteger resId) {
		CACustomer ret = null;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			String qryStr = GlobalQueries.QRY_GET_CUSTOMER_FOR_RES + resId;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = new CACustomer(rs);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (rs != null) {
				rs.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}
}
