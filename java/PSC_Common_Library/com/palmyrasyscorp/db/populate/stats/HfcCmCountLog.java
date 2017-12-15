/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.util.LinkedList;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.*;

/**
 * @author Prem
 *
 */
public class HfcCmCountLog extends AbstractCmCountLog {

	protected LinkedList getObjectList()
	{
		return (getHfcList());
	}
	
	protected long getResId(Object o)
	{
		return (((HfcPlant)o).getHfcResId().longValue());
	}

	protected String getTableName()
	{
		return ("hfc_cm_counts_log");
	}
	
	protected String getResIdColumnName()
	{
		return ("hfc_res_id");
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
