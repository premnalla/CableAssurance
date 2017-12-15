/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.List;

/**
 * @author Prem
 * 
 */
public interface History {

	// public ArrayList queryHistory();

	// public ArrayList queryHistory(long fromSec, long toSec);

	// public ArrayList queryHistory(short fromIndex, short toIndex);

	public List queryHistory(long fromSec, long toSec, short fromIndex,
			short toIndex);

}
