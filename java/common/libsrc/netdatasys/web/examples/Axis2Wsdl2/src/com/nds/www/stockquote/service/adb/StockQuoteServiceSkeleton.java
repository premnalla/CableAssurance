/**
 * StockQuoteServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.1 Nov 13, 2006 (07:31:44 LKT)
 */
package com.nds.www.stockquote.service.adb;

import stockquoteservice.xsd.GetPriceResponse;
import java.util.HashMap;

/**
 * StockQuoteServiceSkeleton java skeleton for the axisService
 */
public class StockQuoteServiceSkeleton implements
		StockQuoteServiceSkeletonInterface {
    private HashMap map = new HashMap();

	/**
	 * Auto generated method signature
	 * 
	 * @param param3
	 * 
	 */
	public void update(stockquoteservice.xsd.Update param3)

	{
        map.put(param3.getSymbol(), new Double(param3.getPrice()));
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param param4
	 * 
	 */
	public stockquoteservice.xsd.GetPriceResponse getPrice(
			stockquoteservice.xsd.GetPrice param4)

	{
		// Todo fill this with the necessary business logic
		// throw new java.lang.UnsupportedOperationException("Please implement "
		// + this.getClass().getName() + "#getPrice");
        Double price = (Double) map.get(param4.getSymbol());
        double ret = 42;
        if(price != null){
            ret = price.doubleValue();
        }
        GetPriceResponse res =
                new GetPriceResponse();
        res.set_return(ret);
        return res;
    }

}
