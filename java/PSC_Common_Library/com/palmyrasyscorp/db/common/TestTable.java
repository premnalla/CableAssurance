/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.tables.AbstractDbObject;

/**
 * @author Prem
 * 
 */
public class TestTable extends AbstractDbObject {

	private static Log log = LogFactory.getLog(TestTable.class);
	
	private Integer m_id;

	private String m_name;
	public String getName() { return m_name; }
	
	private Integer m_salary;
	public Integer getSalary() { return m_salary; }

	/**
	 * 
	 * 
	 */
	public TestTable() {

	}

	/**
	 * 
	 * @param id
	 */
	public TestTable(Integer id) {
		super();
		m_id = id;
		querySelf();
	}

	/**
	 * 
	 * @param rs
	 */
	public TestTable(ProxyDbResultSet rs) {
		super();
		int i = 1;
		try {
			m_id = (Integer) rs.getObject(i++);
			m_name = rs.getString(i++);
			m_salary = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * 
	 */
	private void querySelf() {

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
			StringBuffer qryStr = new StringBuffer("select * from test.salary where id=").append(m_id);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				try {
					int i = 1;
					m_id = (Integer) rs.getObject(i++);
					m_name = rs.getString(i++);
					m_salary = (Integer) rs.getObject(i++);
				} catch (Exception e) {
					// e.printStackTrace();
					log.error(e);
				}
			}
			
			System.out.println("ID=" + m_id);
			System.out.println("Name=" + m_name);
			
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
		
	}

	public boolean insert() {
		boolean ret = false;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(
					"insert into test.salary(name,salary) values('foo', 500)");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(qryStr.toString());

			ret = true;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}

	public boolean update() {
		boolean ret = false;

		return (ret);
	}

	public boolean delete() {
		boolean ret = false;

		return (ret);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	static public List<TestTable> Get(Integer id) {
		List<TestTable> ret = new LinkedList<TestTable>();

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
			StringBuffer qryStr = new StringBuffer("select * from test.salary");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			while (rs.next()) {
				try {
					TestTable tbl = new TestTable(rs);
					ret.add(tbl);
				} catch (Exception e) {
					// e.printStackTrace();
					log.error(e);
				}
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

	/**
	 * 
	 * @return
	 */
	public boolean get() {
		boolean ret = false;

		return (ret);
	}
}
