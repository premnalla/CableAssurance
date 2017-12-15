package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 import java.sql.Connection;
 import java.sql.ResultSet;
 import java.sql.Statement;

 import com.networkdatapeople.db.common.DbConnectionPool;
 import com.networkdatapeople.db.common.DbUtils;
 */

public class Enterprise extends AbstractDbObject {

	private static Log log = LogFactory.getLog(Enterprise.class);

	private Integer m_enterpriseId;

	public Enterprise() {
	}

	public Integer getId() {
		return (m_enterpriseId);
	}

}
