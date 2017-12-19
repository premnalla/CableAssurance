package ossj.ttri;

import javax.oss.trouble.AccessHours;
import javax.oss.trouble.TimeInterval;

/**
 * AccessHours Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class AccessHoursImpl implements AccessHours,
        java.io.Serializable {

    private int DayOfWeek;
    private TimeInterval[] TimeIntervalList;


    public int getDayOfWeek() {
        return DayOfWeek;
    }

    public TimeInterval[] getTimeIntervalList() {
        return TimeIntervalList;
    }

    public void setDayOfWeek(int value) {
        DayOfWeek = value;
    }

    public void setTimeIntervalList(TimeInterval[] value) {
        TimeIntervalList = value;
    }

    public Object clone() {

        AccessHoursImpl newVal = new AccessHoursImpl();
        newVal.setDayOfWeek(DayOfWeek);
        if (TimeIntervalList != null) {
            TimeInterval[] newTimeIntervalList = new TimeInterval[TimeIntervalList.length];
            for (int i = 0; i < TimeIntervalList.length; i++) {
                newTimeIntervalList[i] = (TimeInterval) ((TimeInterval) TimeIntervalList[i]).clone();
            }
            newVal.setTimeIntervalList(newTimeIntervalList);
        } else
            newVal.setTimeIntervalList(null);
        return newVal;
    }


    public boolean equals(Object ahObj) {

        if (ahObj == null) return false;
        AccessHours ahi = (AccessHours) ahObj;
        if (DayOfWeek != ahi.getDayOfWeek()) return false;

        TimeInterval[] tiList = ahi.getTimeIntervalList();

        if ((TimeIntervalList == null) && (tiList == null)) return true;
        if (TimeIntervalList.length != tiList.length) return false;

        if ((TimeIntervalList != null) && (tiList != null)) {
            for (int x = 0; x < TimeIntervalList.length; x++) {
                if (!utils.matchTimeInterval(TimeIntervalList[x], tiList[x]))
                    return false;
            }
        }

        return true;

    }

}
