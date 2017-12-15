/**
 * 
 */
package com.palmyrasyscorp.www.exec;

import com.palmyrasyscorp.common.exec.AbstractConsumerSupplierQ;
//import com.palmyrasyscorp.common.exec.AbstractXRunnable;

/**
 * @author Prem
 *
 */
public class RunnableQ extends AbstractConsumerSupplierQ {

	private static RunnableQ m_instance = null;
	
	/**
	 * 
	 *
	 */
	private RunnableQ() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static RunnableQ getInstance() {
		if (m_instance == null) {
			m_instance = new RunnableQ();
		}
		return (m_instance);
	}
	
//	/**
//	 * 
//	 * @param r
//	 */
//	public void enQ(AbstractXRunnable r) {
//		super.enQ(r);
//	}
//	
//	/**
//	 * 
//	 */
//	public AbstractXRunnable deQ() {
//		return ((AbstractXRunnable)super.deQ());
//	}
	
}
