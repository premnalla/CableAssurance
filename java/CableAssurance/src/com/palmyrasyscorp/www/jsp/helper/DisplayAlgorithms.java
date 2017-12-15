/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import com.palmyrasyscorp.db.tables.IntegerHolder;

/**
 * @author Prem
 * 
 */
public final class DisplayAlgorithms {

	/**
	 * 
	 * 
	 */
	private DisplayAlgorithms() {
	}

	/**
	 * 
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public static final boolean IsTableBeginTr(int currentRow, int itemsPerRow) {
		return ((currentRow % itemsPerRow) == 0);
	}

	/**
	 * How to call this:
	 * 
	 * IntegerHolder iH = new IntegerHolder();
	 * iH.val = -1;
	 * int itemsPerRow = 3;
	 * for (int i = 0; i<list.size(); i++) {
	 * 	if (IsTableEndTr(i, itemsPerRow, list.size(), iH)) {
	 *		....  
	 * 	}
	 * }
	 * 
	 * @param currentRow
	 * @param itemsPerRow
	 * @param listSize
	 * @param iH
	 * @return
	 */
	public static final boolean IsTableEndTr(int currentRow, int itemsPerRow,
			int listSize, IntegerHolder iH) {
		boolean ret = false;

		currentRow++;
		int p = currentRow/itemsPerRow;
		int currQ = (p * itemsPerRow) - 1;
		
		if (currQ != iH.value || currentRow >= listSize) {
			iH.value = currQ;
			ret = true;
		}
				
		return (ret);
	}
	
}
