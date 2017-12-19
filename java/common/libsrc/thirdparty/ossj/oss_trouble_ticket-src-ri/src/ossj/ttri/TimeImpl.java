package ossj.ttri;

import javax.oss.trouble.Time;

/**
 * Time Implementation Class
 * Time 10:02:00:00 + UtcOffset
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TimeImpl implements Time, java.io.Serializable {

    private short Hour;
    private short Minute;
    private short Seconds;
    private short MilliSeconds;
    private short UtcOffset;

    public short getHour() {
        return Hour;
    }

    public short getMinute() {
        return Minute;
    }

    public short getSeconds() {
        return Seconds;
    }

    public short getMilliSeconds() {
        return MilliSeconds;
    }

    public short getUtcOffset() {
        return UtcOffset;
    }

    public void setHour(short value) {
        Hour = value;
    }

    public void setMinute(short value) {
        Minute = value;
    }

    public void setSeconds(short value) {
        Seconds = value;
    }

    public void setMilliSeconds(short value) {
        MilliSeconds = value;
    }

    public void setUtcOffset(short value) {
        UtcOffset = value;
    }

    public Object clone() {

        TimeImpl newVal = new TimeImpl();
        newVal.setHour(getHour());
        newVal.setMilliSeconds(getMilliSeconds());
        newVal.setMinute(getMinute());
        newVal.setSeconds(getSeconds());
        newVal.setUtcOffset(getUtcOffset());

        return newVal;
    }

    public void print(int indent) {
        String sp = null;
        if ((indent > 0) && (indent < 60)) {
            char[] spaces = new char[indent];
            for (int x = 0; x < indent; x++) spaces[x] = ' ';
            sp = new String(spaces);
        }

        Logger.log(sp + "         Hour: " + Hour);
        Logger.log(sp + "       Minute: " + Minute);
        Logger.log(sp + "      Seconds: " + Seconds);
        Logger.log(sp + " MilliSeconds: " + MilliSeconds);
        Logger.log(sp + "    UtcOffset: " + UtcOffset);
        //TODO - format the time in a single line, e.g. HH:MM:SS, offset
    }

    public boolean equals(Object tiObj) {
        if (tiObj == null) return false;
        Time ti = (Time) tiObj;

        if (Hour != ti.getHour()) return false;
        if (Minute != ti.getMinute()) return false;
        if (Seconds != ti.getSeconds()) return false;
        if (MilliSeconds != ti.getMilliSeconds()) return false;
        if (UtcOffset != ti.getUtcOffset()) return false;

        return true;
    }


}
