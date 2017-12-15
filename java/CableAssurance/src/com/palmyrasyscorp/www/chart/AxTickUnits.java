/**
 * 
 */
package com.palmyrasyscorp.www.chart;

import org.jfree.chart.axis.Tick;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.NumberTickUnit;

/**
 * Singleton for custom TickUnits
 * 
 * @author Prem
 *
 */
public class AxTickUnits extends TickUnits {

	/**
	 * 
	 */
	private static AxTickUnits m_instance = null;
	
	private static TickUnits m_longTickUnits = new TickUnits();
	private static TickUnits m_mixedTickUnits = new TickUnits();
	
	static {
		loadLongTickUnits();
		loadMixedTickUnits();
	}
	
	/**
	 * 
	 *
	 */
	protected AxTickUnits() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static AxTickUnits getInstance() {
		if (m_instance == null) {
			m_instance = new AxTickUnits();
		}
		return (m_instance);
	}
	
	/**
	 * 
	 * @return
	 */
	public TickUnits getLongTickUnits() {
		return (m_longTickUnits);
	}
	
	/**
	 * 
	 * @return
	 */
	public TickUnits getMixedTickUnits() {
		return (m_mixedTickUnits);
	}
	/**
	 * 
	 *
	 */
	private static void loadLongTickUnits() {
	    m_longTickUnits.add(new NumberTickUnit(1,  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit(2,  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit(5,  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit(10,  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit(20,  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit(50,  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit(100,  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit(200,  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit((500L),  new AxLongFormat("0")));
	    m_longTickUnits.add(new NumberTickUnit((1000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((2000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((5000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((10000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((20000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((50000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((100000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((200000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((500000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((1000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((2000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((5000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((10000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((20000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((50000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((100000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((200000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((500000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((1000000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((2000000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((5000000000L),  new AxLongFormat("#,##0")));
	    m_longTickUnits.add(new NumberTickUnit((10000000000L),  new AxLongFormat("#,##0")));		
	}

	/**
	 * 
	 *
	 */
	private static void loadMixedTickUnits() {

	    // m_mixedTickUnits.add(new NumberTickUnit((0.0000001),  new AxNumberFormat("0.0000000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.000001),   new AxNumberFormat("0.000000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.00001),    new AxNumberFormat("0.00000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.0001),     new AxNumberFormat("0.0000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.001),      new AxNumberFormat("0.000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.01),       new AxNumberFormat("0.00")));
	    m_mixedTickUnits.add(new NumberTickUnit((0.1),        new AxNumberFormat("0.0")));
	    m_mixedTickUnits.add(new NumberTickUnit((1L),           new AxNumberFormat("0")));
	    m_mixedTickUnits.add(new NumberTickUnit((10L),          new AxNumberFormat("0")));
	    m_mixedTickUnits.add(new NumberTickUnit((100L),         new AxNumberFormat("0")));
	    m_mixedTickUnits.add(new NumberTickUnit((1000L),        new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((10000L),       new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((100000L),      new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((1000000L),     new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((10000000L),    new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((100000000L),   new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((1000000000L),  new AxNumberFormat("#,###,###,##0")));

	    // m_mixedTickUnits.add(new NumberTickUnit((0.00000025), new AxNumberFormat("0.00000000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.0000025),  new AxNumberFormat("0.0000000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.000025),   new AxNumberFormat("0.000000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.00025),    new AxNumberFormat("0.00000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.0025),     new AxNumberFormat("0.0000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.025),      new AxNumberFormat("0.000")));
	    m_mixedTickUnits.add(new NumberTickUnit((0.25),       new AxNumberFormat("0.00")));
	    m_mixedTickUnits.add(new NumberTickUnit((2.5),        new AxNumberFormat("0.0")));
	    m_mixedTickUnits.add(new NumberTickUnit((25L),          new AxNumberFormat("0")));
	    m_mixedTickUnits.add(new NumberTickUnit((250L),         new AxNumberFormat("0")));
	    m_mixedTickUnits.add(new NumberTickUnit((2500L),        new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((25000L),       new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((250000L),      new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((2500000L),     new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((25000000L),    new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((250000000L),   new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((2500000000L),  new AxNumberFormat("#,###,###,##0")));

	    // m_mixedTickUnits.add(new NumberTickUnit((0.0000005),  new AxNumberFormat("0.0000000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.000005),   new AxNumberFormat("0.000000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.00005),    new AxNumberFormat("0.00000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.0005),     new AxNumberFormat("0.0000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.005),      new AxNumberFormat("0.000")));
	    // m_mixedTickUnits.add(new NumberTickUnit((0.05),       new AxNumberFormat("0.00")));
	    m_mixedTickUnits.add(new NumberTickUnit((0.5),        new AxNumberFormat("0.0")));
	    m_mixedTickUnits.add(new NumberTickUnit((5L),           new AxNumberFormat("0")));
	    m_mixedTickUnits.add(new NumberTickUnit((50L),          new AxNumberFormat("0")));
	    m_mixedTickUnits.add(new NumberTickUnit((500L),         new AxNumberFormat("0")));
	    m_mixedTickUnits.add(new NumberTickUnit((5000L),        new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((50000L),       new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((500000L),      new AxNumberFormat("#,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((5000000L),     new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((50000000L),    new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((500000000L),   new AxNumberFormat("#,###,##0")));
	    m_mixedTickUnits.add(new NumberTickUnit((5000000000L),  new AxNumberFormat("#,###,###,##0")));
		
	}

}
