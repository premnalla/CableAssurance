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
public class MtaPingStatusLog extends AbstractStatusLog {

	/**
	 * 
	 */
	public MtaPingStatusLog() {
	}

	protected String getCreateSql(long resId) {
		String sql = "insert into mta_ping_status_log(emta_res_id,ping_state) values (" + resId + ",1);";
		return (sql);
	}

	protected LinkedList getObjectList() {
		return (getEnterpriseMtaList());
	}

	protected long getResId(Object o) {
		return (((Emta)o).getEmtaResId().longValue());
	}

}
