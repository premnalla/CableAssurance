/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.util.LinkedList;
import com.palmyrasyscorp.db.tables.*;


/**
 * @author Prem
 *
 */
public class CmStatusLog extends AbstractStatusLog {

	/**
	 * 
	 */
	public CmStatusLog() {
	}

	protected String getCreateSql(long resId) {
		String sql = "insert into cm_status_log(cm_res_id,modem_state) values (" + resId + ",6);";
		return (sql);
	}

	protected LinkedList getObjectList() {
		return (getEnterpriseCmList());
	}

	protected long getResId(Object o) {
		return (((CableModem)o).getCmResId().longValue());
	}

}
