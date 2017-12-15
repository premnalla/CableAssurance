/**
 * 
 */
package com.palmyrasyscorp.db.populate.netobj;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.populate.stats.*;

/**
 * @author Prem
 *
 */
public class HfcOutageCreator extends AbstractWorker {

	private DbConnection m_conn = null;

	/**
	 * 
	 */
	public HfcOutageCreator() {
		super();
	}

	public void run()
	{
		// step 1
		getConnection();
		// getInput();
		
		// create rows in db

		// close
		releaseConnection();
		
		// finally: inform that we are done
		setDoneWork();
	}
	
	protected boolean getConnection()
	{
		boolean ret = true;
		
		m_conn = createDbConnection();
		
		return (ret);
	}
	
	protected boolean releaseConnection()
	{
		boolean ret = true;
		
		closeConnection(m_conn);
		m_conn = null;
		
		return (ret);
	}

}
