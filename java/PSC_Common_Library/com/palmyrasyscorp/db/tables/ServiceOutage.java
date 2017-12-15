/**
 * OBSOLSTED FOR NOW
 */
package com.palmyrasyscorp.db.tables;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import com.palmyrasyscorp.common.base.*;
import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class ServiceOutage extends AbstractServiceOutage {

	private BigInteger m_outage_id;

	private Long m_resource_res_id;

	// private short m_isCleared;
	private Integer m_outage_type;
	private String m_outage_type_str = null;
	private String m_outage_details = null;

	private Timestamp m_detection_time;

	private String m_outage_description;

	public BigInteger getId() {
		return m_outage_id;
	}

	public Long getResId() {
		return m_resource_res_id;
	}

	public Integer getExceptionType() {
		return m_outage_type;
	}

	public String getOutageTypeStr() {
		if (m_outage_type_str == null) {
			return ("");
		} else {
			return (m_outage_type_str);
		}
	}

	public String getOutageDetails() {
		if (m_outage_details == null) {
			return ("");
		} else {
			return (m_outage_details);
		}
	}

	public String getDetectionTime() {
		return m_detection_time.toLocaleString();
	}

	public String getOutageDescription() {
		return m_outage_description;
	}

	protected ServiceOutage() {

	}

	public ServiceOutage(ResultSet rs) {
		int i = 1;
		try {
			// From TABLE 'alarm_basic'
			// Object o = (Object) rs.getObject(i++);
			// m_outage_id = new Long (o.hashCode());
			m_outage_id = (BigInteger) rs.getObject(i++);
			m_resource_res_id = (Long) rs.getObject(i++);
			m_outage_type = (Integer) rs.getObject(i++);
			m_detection_time = (Timestamp) rs.getObject(i++);

			// From TABLE 'alarm_secondary'
			// i++; // id
			// i++; // alarm_outage_id
			// m_alarm_description = rs.getString(i++);
			
			// get type str, etc
			getAndSetAdditionalData();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void getAndSetAdditionalData() {
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT description FROM service_outage_types WHERE outage_type=" + m_outage_type;
			rs = stmt.executeQuery(qryStr);
			
			if (rs.next()) {
				int i = 1;
				m_outage_type_str = rs.getString(i++);
			}
			DbUtils.ReleaseResultSet(rs);
			
			qryStr = "SELECT description FROM service_outage_secondary WHERE outage_id=" + m_outage_id;
			rs = stmt.executeQuery(qryStr);
			
			if (rs.next()) {
				int i = 1;
				m_outage_description = rs.getString(i++);
			}
			DbUtils.ReleaseResultSet(rs);
		
			qryStr = "SELECT a.outage_details FROM service_outage_details a WHERE a.outage_id=" + m_outage_id;
			rs = stmt.executeQuery(qryStr);
			if (rs.next()) {
				int i = 1;
				m_outage_details = rs.getString(i++);
			}
			
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}

}
