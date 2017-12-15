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
public class MtaProvStatusLog extends AbstractStatusLog {

	/**
	 * 
	 */
	public MtaProvStatusLog() {
	}

	protected String getCreateSql(long resId) {
		String sql = "insert into mta_prov_status_log(emta_res_id,prov_state) values (" + resId + ",1);";
		return (sql);
	}

	protected LinkedList getObjectList() {
		return (getEnterpriseMtaList());
	}

	protected long getResId(Object o) {
		return (((Emta)o).getEmtaResId().longValue());
	}

}
