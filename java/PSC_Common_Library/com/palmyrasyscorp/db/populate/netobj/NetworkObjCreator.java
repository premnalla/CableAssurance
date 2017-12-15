package com.palmyrasyscorp.db.populate.netobj;

import java.util.*;
import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.populate.stats.*;

public class NetworkObjCreator extends AbstractWorker {

	private DbConnection m_conn = null;

	// June-11-2007
	private String m_localSystemType;

	private String m_localSystemName;

	protected NetworkObjCreator() {
	}

	/**
	 * Jun-11-07
	 */
	protected NetworkObjCreator(String localSystemType, String localSystemName) {
		m_localSystemType = localSystemType;
		m_localSystemName = localSystemName;
	}

	public void run() {
		// step 1
		getConnection();
		// getInput();

		// create rows in db

		if (m_localSystemType != null) {
			if (m_localSystemType.equalsIgnoreCase("market")) {
				Market mkt = new Market(m_conn, m_localSystemName);
				mkt.create();
			} else if (m_localSystemType.equalsIgnoreCase("blade")) {
				Blade bld = new Blade(m_conn, m_localSystemName);
				bld.create();
			} else {
				System.err.println("exitting...");
				System.exit(-1);
			}
		} else {
			System.err.println("exitting...");
			System.exit(-1);
		}

		// close
		releaseConnection();

		// finally: inform that we are done
		setDoneWork();
	}

	protected boolean getConnection() {
		boolean ret = true;

		ProgramProperties props = ProgramProperties.getInstance();
		String connUrl = props.getValue("db.conn");

		System.out.println(connUrl);
		
		m_conn = createDbConnection(connUrl);

		return (ret);
	}

	protected boolean releaseConnection() {
		boolean ret = true;

		closeConnection(m_conn);
		m_conn = null;

		return (ret);
	}

}
