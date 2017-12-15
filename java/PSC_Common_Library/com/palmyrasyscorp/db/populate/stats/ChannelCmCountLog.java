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
public class ChannelCmCountLog extends AbstractCmCountLog {

	protected LinkedList getObjectList()
	{
		return (getChannelList());
	}
	
	protected long getResId(Object o)
	{
		return (((Channel)o).getChannelResId().longValue());
	}

	protected String getTableName()
	{
		return ("channel_cm_counts_log");
	}
	
	protected String getResIdColumnName()
	{
		return ("channel_res_id");
	}
	
	protected String getTotalColumnName()
	{
		return ("cm_total");
	}
	
	protected String getOnlineColumnName()
	{
		return ("cm_online");
	}
	
	protected int getTotalCount()
	{
		return (4);
	}
	
	protected int getOnlineCount()
	{
		return (3);
	}

}
