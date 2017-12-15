/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.util.*;
import com.palmyrasyscorp.db.tables.*;

/**
 * @author Prem
 *
 */
public class CmtsCmCountLog extends AbstractCmCountLog {
	
	public CmtsCmCountLog() {}
	
	protected LinkedList getObjectList()
	{
		return (getCmtsList());
	}
	
	protected long getResId(Object o)
	{
		return (((Cmts)o).getCmtsResId().longValue());
	}
	
	protected String getTableName()
	{
		return ("cmts_cm_counts_log");
	}
	
	protected String getResIdColumnName()
	{
		return ("cmts_res_id");
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
		return (8);
	}
	
	protected int getOnlineCount()
	{
		return (7);
	}
	
}
