package com.palmyrasyscorp.db.populate.stats;

import java.util.LinkedList;
import com.palmyrasyscorp.db.tables.*;

public class ChannelMtaCountLog extends AbstractCmCountLog {

	protected LinkedList getObjectList()
	{
		return (getChannelList());
	}
	
	protected long getResId(Object o)
	{
		return (((Channel)o).getChannelResId().longValue());
	}

	/* (non-Javadoc)
	 * @see com.networkdatapeople.db.teststatsdata.AbstractWorker#getTableName()
	 */
	protected String getTableName() {
		// TODO Auto-generated method stub
		return ("channel_mta_counts_log");
	}

	/* (non-Javadoc)
	 * @see com.networkdatapeople.db.teststatsdata.AbstractWorker#getResIdColumnName()
	 */
	protected String getResIdColumnName() {
		// TODO Auto-generated method stub
		return ("channel_res_id");
	}

	/* (non-Javadoc)
	 * @see com.networkdatapeople.db.teststatsdata.AbstractWorker#getTotalColumnName()
	 */
	protected String getTotalColumnName() {
		// TODO Auto-generated method stub
		return ("mta_total");
	}

	/* (non-Javadoc)
	 * @see com.networkdatapeople.db.teststatsdata.AbstractWorker#getOnlineColumnName()
	 */
	protected String getOnlineColumnName() {
		// TODO Auto-generated method stub
		return ("mta_available");
	}

	/* (non-Javadoc)
	 * @see com.networkdatapeople.db.teststatsdata.AbstractWorker#getTotalCount()
	 */
	protected int getTotalCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	/* (non-Javadoc)
	 * @see com.networkdatapeople.db.teststatsdata.AbstractWorker#getOnlineCount()
	 */
	protected int getOnlineCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
