/**
 * 
 */
package com.palmyrasyscorp.db.populate.netobj;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * @author Prem
 *
 */
public final class GlobalUtils {

	static long m_macAddress = 0;
	static Object m_macLock = new Object();
	
	static final String m_macPrefix[] = {
		"000000000000",
		"00000000000",
		"0000000000",
		"000000000",
		"00000000",
		"0000000",
		"000000",
		"00000",
		"0000",
		"000",
		"00",
		"0",
		""
	};
	
	/**
	 * 
	 */
	protected GlobalUtils() {
		super();
	}

	public static String GetNextMacAddress() {
		String ret = null;
		
		long macAddr = 0;
		
		synchronized (m_macLock) {
			macAddr = ++m_macAddress;
		}
		
		ret = ConvertMacLongToStr(macAddr);
		
		return (ret);
	}
	
	public static String ConvertMacLongToStr(long macAddr) {
		String ret = null;
		
		StringBuffer fin = new StringBuffer();
		
		String s1 = Long.toHexString(macAddr);
		// System.out.println("s1: " + s1);
		
		StringBuffer sb1 = new StringBuffer(m_macPrefix[s1.length()]).append(s1);
		// System.out.println("sb1: " + sb1);
		
		StringCharacterIterator iter = new StringCharacterIterator(sb1.toString());
		
		char c;
		int i;
		for (i=1, c=iter.first(); c!=CharacterIterator.DONE ; i++) {
			// System.out.println("c: " + c);
			fin.append(c);
			c=iter.next();
			if ((i%2) == 0) {
				if (c!=CharacterIterator.DONE) {
					fin.append(":");
				}
			}
		}
		
		ret = fin.toString();
		
		fin = null;
		s1 = null;
		sb1 = null;
		iter = null;
		
		return (ret);
	}
	
	public static void main (String args[]) {
		// long macAddr = 256L;
		// System.out.println("mac-addr: " + Long.toHexString(macAddr));
		// System.out.println("mac-addr: " + Long.toOctalString(macAddr));
		
		// System.out.println(ConvertMacLongToStr(256));

		/*
		*/
		for (int i=1; i<=65537; i++) {
			System.out.println(ConvertMacLongToStr(i));
		}
		
	}
}
