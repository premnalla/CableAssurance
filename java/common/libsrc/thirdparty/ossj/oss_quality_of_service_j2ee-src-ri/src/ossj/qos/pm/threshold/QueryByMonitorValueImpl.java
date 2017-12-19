/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import ossj.qos.QueryValueImpl;
import javax.oss.pm.threshold.*;

import ossj.qos.util.Log; // logging

/**
 * Implements the QueryByMonitorValue.
 * @author Henrik Lindstrom, Ericsson
 */
public class QueryByMonitorValueImpl extends TmQueryValueImpl implements QueryByMonitorValue {

    /**
     * Constructs the QueryByMonitorValue. A new object is created and all
     * attributes initialized to null (unpopulated).
     */
    public QueryByMonitorValueImpl() {
        Log.write(this,Log.LOG_ALL,"QueryByMonitorValueImpl()","called");

        map.put( QueryByMonitorValue.GRANULARITY, null );
        map.put( QueryByMonitorValue.VALUE_TYPE, null );
        map.put( QueryByMonitorValue.NAME, null );
        map.put( QueryByMonitorValue.STATE, null );

        // Note, QueryByMonitorValue.QUERY_TYPE is not an attribute constant!
    }

    protected boolean isValidAttribute( String attrName, Object attrValue ) {
		boolean valid = false;

        // check also valid attribute for this class.
        if ( attrName != null && attrValue != null ) {
            if ( attrName.equals( QueryByMonitorValue.GRANULARITY )
              && attrValue instanceof Integer ) {
                int granularity = ((Integer)attrValue).intValue();
                valid = AttributeValidator.isValidGranularity( granularity );
            } else if ( attrName.equals( QueryByMonitorValue.NAME )
              && attrValue instanceof String ) {
                valid = true;
            } else if ( attrName.equals( QueryByMonitorValue.VALUE_TYPE )
              && attrValue instanceof String ) {
                String valueType = (String)attrValue;
                if ( valueType.equals(SimpleThresholdMonitorValue.VALUE_TYPE)
                  || valueType.equals(TriggerOnAnyThresholdMonitorValue.VALUE_TYPE)
                  || valueType.equals(TriggerOnAllThresholdMonitorValue.VALUE_TYPE) )
                {
                    valid = true;
                }
            } else if ( attrName.equals( QueryByMonitorValue.STATE )
              && attrValue instanceof Integer ) {
                int state = ((Integer)attrValue).intValue();
                valid = AttributeValidator.isValidState( state );
            } // Note, QueryByMonitorValue.QUERY_TYPE is not an attribute.
        }
		return valid;
	}

    public int getGranularityPeriod() throws IllegalStateException {
        Log.write(this,Log.LOG_ALL,"getMonitorName()","called");
        return ((Integer)this.getAttributeValue( QueryByMonitorValue.GRANULARITY )).intValue();
    }

    public String getName() throws IllegalStateException {
        Log.write(this,Log.LOG_ALL,"getMonitorName()","called");
        return (String)this.getAttributeValue( QueryByMonitorValue.NAME );
    }

    public String getValueType() throws IllegalStateException {
        Log.write(this,Log.LOG_ALL,"getMonitorValueType()","called");
        return (String)this.getAttributeValue( QueryByMonitorValue.VALUE_TYPE );
    }

    public int getState() throws IllegalStateException {
        Log.write(this,Log.LOG_ALL,"getState()","called");
        return ((Integer)this.getAttributeValue( QueryByMonitorValue.STATE )).intValue();
    }

    public void setGranularityPeriod(int granularityPeriod) throws IllegalArgumentException {
        Log.write(this,Log.LOG_ALL,"setGranularityPeriod()","granularityPeriod="+granularityPeriod);
        this.setAttributeValue(QueryByMonitorValue.GRANULARITY,new Integer(granularityPeriod));
    }

    public void setName(String name) throws IllegalArgumentException {
        Log.write(this,Log.LOG_ALL,"setMonitorName()","name="+name);
        this.setAttributeValue( QueryByMonitorValue.NAME, name );
    }

    public void setValueType(String type) throws IllegalArgumentException {
        Log.write(this,Log.LOG_ALL,"setMonitorValueType()","type="+type);
        this.setAttributeValue( QueryByMonitorValue.VALUE_TYPE, type );
    }

    public void setState(int state) throws IllegalArgumentException {
        Log.write(this,Log.LOG_ALL,"setState()","state="+state);
        this.setAttributeValue( QueryByMonitorValue.STATE, new Integer(state) );
    }

    public String toString() {
        return "" + map.get( QueryByMonitorValue.NAME );
    }
}
