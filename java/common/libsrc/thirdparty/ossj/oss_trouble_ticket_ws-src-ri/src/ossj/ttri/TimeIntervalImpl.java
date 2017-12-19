package ossj.ttri;

import javax.oss.trouble.Time;
import javax.oss.trouble.TimeInterval;

/**
 * TimeInterval Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TimeIntervalImpl implements TimeInterval, java.io.Serializable {

    private Time IntervalBegin;
    private Time IntervalEnd;

    public Time getIntervalBegin() {
        return IntervalBegin;
    }

    public Time getIntervalEnd() {
        return IntervalEnd;
    }

    public void setIntervalBegin(Time value) {
        IntervalBegin = value;
    }

    public void setIntervalEnd(Time value) {
        IntervalEnd = value;
    }

    public Object clone() {

        TimeIntervalImpl newVal = new TimeIntervalImpl();
        if (IntervalBegin != null) {
            newVal.setIntervalBegin((Time) ((Time) IntervalBegin).clone());
        } else {
            newVal.setIntervalBegin(null);
        }
        if (IntervalBegin != null) {
            newVal.setIntervalEnd((Time) ((Time) IntervalEnd).clone());
        } else {
            newVal.setIntervalEnd(null);
        }

        return newVal;
    }

    public void print(int indent) {
        String sp = null;
        if ((indent > 0) && (indent < 60)) {
            char[] spaces = new char[indent];
            for (int x = 0; x < indent; x++) spaces[x] = ' ';
            sp = new String(spaces);
        }


        Logger.log(sp + "TimeInterval:");
        Logger.log(sp + "    IntervalBegin: ");
        TimeImpl ti = (TimeImpl) IntervalBegin;
        if (ti != null)
            ti.print(indent + 12);
        Logger.log(sp + "    IntervalEnd: ");
        ti = (TimeImpl) IntervalEnd;
        if (ti != null)
            ti.print(indent + 12);

    }

    public boolean equals(Object tiObj) {
        if (tiObj == null) return false;
        TimeInterval ti = (TimeInterval) tiObj;

        if (!utils.matchTime(IntervalBegin, ti.getIntervalBegin())) return false;
        if (!utils.matchTime(IntervalEnd, ti.getIntervalEnd())) return false;

        return true;
    }
}
