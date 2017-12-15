/**
 * 
 */
package com.palmyrasyscorp.common.exec;

import java.util.LinkedList;

/**
 * @author Prem
 *
 */
public abstract class AbstractConsumerSupplierQ {

	private Object m_mutex = new Object();
	private LinkedList m_queue = new LinkedList();
	
	/**
	 * 
	 *
	 */
	protected AbstractConsumerSupplierQ() {
		super();
	}
	
	/**
	 * 
	 * @param o
	 */
	public void enQ(Object o) {
		synchronized(m_mutex) {
			m_queue.add(o);
			m_mutex.notifyAll();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Object deQ() {
		Object ret = null;
		
		while (ret == null) {
			synchronized(m_mutex) {
				ret = m_queue.remove();
				if (ret == null) {
					try {
						m_queue.wait();
					} catch (Exception ex) {
						
					}
				}
			}
		}
		
		return (ret);
	}
}
