/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.cbe.alarm;

import javax.oss.cbe.alarm.AlarmCounts;


/**
 * An implementation class for the <CODE>javax.oss.cbe.alarm.AlarmCounts</CODE> interface.
 *
 * @see javax.oss.cbe.alarm.AlarmCounts
 *
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.2.2
 * @since September 2005
 */
public class AlarmCountsImpl implements AlarmCounts {
    private int _alarmcountsimpl_critical_count = 0;
    private int _alarmcountsimpl_major_count = 0;
    private int _alarmcountsimpl_minor_count = 0;
    private int _alarmcountsimpl_warning_count = 0;
    private int _alarmcountsimpl_indeterminate_count = 0;
    private int _alarmcountsimpl_cleared_count = 0;
    private int _alarmcountsimpl_acknowledged_count = 0;
    /**
     * Constructs a new AlarmCountsImpl with empty attributes.
     *
     */
    public AlarmCountsImpl() {
    }

    /**
     * Creates a new AlarmCountsImpl object using the given counts.
     *
     * @param critical_count
     * @param major_count
     * @param minor_count
     * @param warning_count
     * @param indeterminate_count
     * @param cleared_count
     * @param acknoledged_count
     */
    public AlarmCountsImpl(int indeterminate_count, int critical_count, int major_count,
        int minor_count, int warning_count, int cleared_count, int acknowledged_count) {
        _alarmcountsimpl_critical_count = critical_count;
        _alarmcountsimpl_major_count = major_count;
        _alarmcountsimpl_minor_count = minor_count;
        _alarmcountsimpl_warning_count = warning_count;
        _alarmcountsimpl_indeterminate_count = indeterminate_count;
        _alarmcountsimpl_cleared_count = cleared_count;
        _alarmcountsimpl_acknowledged_count = acknowledged_count;
    }

    //
    // All getters and setters for all declared attributes:
    //

    /**
     * Returns this AlarmCountsImpl's clearedCount
     *
     * @return the clearedCount
     *
    */
    public int getClearedCount() {
        return _alarmcountsimpl_cleared_count;
    }

    /**
     * Returns this AlarmCountsImpl's criticalCount
     *
     * @return the criticalCount
     *
    */
    public int getCriticalCount() {
        return _alarmcountsimpl_critical_count;
    }

    /**
     * Returns this AlarmCountsImpl's indeterminateCount
     *
     * @return the indeterminateCount
     *
    */
    public int getIndeterminateCount() {
        return _alarmcountsimpl_indeterminate_count;
    }

    /**
     * Returns this AlarmCountsImpl's majorCount
     *
     * @return the majorCount
     *
    */
    public int getMajorCount() {
        return _alarmcountsimpl_major_count;
    }

    /**
     * Returns this AlarmCountsImpl's minorCount
     *
     * @return the minorCount
     *
    */
    public int getMinorCount() {
        return _alarmcountsimpl_minor_count;
    }

    /**
     * Returns this AlarmCountsImpl's warningCount
     *
     * @return the warningCount
     *
    */
    public int getWarningCount() {
        return _alarmcountsimpl_warning_count;
    }

    /**
     * Returns this AlarmCountsImpl's acknowledgedCount
     *
     * @return the warningCount
     *
    */
    public int getAcknowledgedCount() {
        return _alarmcountsimpl_acknowledged_count;
    }
    
	/**
     * Increments this AlarmCountsImpl's clearedCount
     *
     * @return the clearedCount
     *
    */
    public void incClearedCount() {
        _alarmcountsimpl_cleared_count += 1;
    }

    /**
     * Increments this AlarmCountsImpl's criticalCount
     *
     * @return the criticalCount
     *
    */
    public void incCriticalCount() {
        _alarmcountsimpl_critical_count += 1;
    }

    /**
     * Increments this AlarmCountsImpl's indeterminateCount
     *
     * @return the indeterminateCount
     *
    */
    public void incIndeterminateCount() {
        _alarmcountsimpl_indeterminate_count += 1;
    }

    /**
     * Increments this AlarmCountsImpl's majorCount
     *
     * @return the majorCount
     *
    */
    public void incMajorCount() {
        _alarmcountsimpl_major_count += 1;
    }

    /**
     * Increments this AlarmCountsImpl's minorCount
     *
     * @return the minorCount
     *
    */
    public void incMinorCount() {
        _alarmcountsimpl_minor_count += 1;
    }

    /**
     * Increments this AlarmCountsImpl's warningCount
     *
    */
    public void incWarningCount() {
        _alarmcountsimpl_warning_count += 1;
    }

    /**
     * Increments this AlarmCountsImpl's acknoledgeCount
     *
    */
    public void incAcknowledgedCount() {
    	_alarmcountsimpl_acknowledged_count += 1;
    }
    
    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
    */
    public Object clone() {
        try {
            AlarmCounts val = (AlarmCounts) super.clone();

            return val;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks whether two AlarmCounts are equal.
     * The result is true if and only if the argument is not null
     * and is an AlarmCounts object that has the arguments.
     *
     * @param value the Object to compare with this AlarmCounts
     * @return if the objects are equal; false otherwise.
     */
    public boolean equals(Object value) {
        if (this == value) {
            return true;
        }

        if ((value != null) && (value instanceof AlarmCounts)) {
            AlarmCounts argVal = (AlarmCounts) value;

            if (this.getClearedCount() != argVal.getClearedCount()) {
                return false;
            }

            if (this.getCriticalCount() != argVal.getCriticalCount()) {
                return false;
            }

            if (this.getIndeterminateCount() != argVal.getIndeterminateCount()) {
                return false;
            }

            if (this.getMajorCount() != argVal.getMajorCount()) {
                return false;
            }

            if (this.getMinorCount() != argVal.getMinorCount()) {
                return false;
            }

            if (this.getWarningCount() != argVal.getWarningCount()) {
                return false;
            }
            
            if (this.getAcknowledgedCount() != argVal.getAcknowledgedCount()) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
}
