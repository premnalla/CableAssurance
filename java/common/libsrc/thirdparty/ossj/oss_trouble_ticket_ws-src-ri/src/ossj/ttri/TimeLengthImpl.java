package ossj.ttri;

import javax.oss.trouble.TimeLength;

/**
 * TimeLength Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TimeLengthImpl implements TimeLength, java.io.Serializable {

    private short Years;
    private short Months;
    private short Days;
    private short Hours;
    private short Minutes;
    private short Seconds;
    private short Msecs;


    public short getYears() {
        return Years;
    }

    public short getMonths() {
        return Months;
    }

    public short getDays() {
        return Days;
    }

    public short getHours() {
        return Hours;
    }

    public short getMinutes() {
        return Minutes;
    }

    public short getSeconds() {
        return Seconds;
    }

    public short getMsecs() {
        return Msecs;
    }

    public void setYears(short value) {
        Years = value;
    }

    public void setMonths(short value) {
        Months = value;
    }

    public void setDays(short value) {
        Days = value;
    }

    public void setHours(short value) {
        Hours = value;
    }

    public void setMinutes(short value) {
        Minutes = value;
    }

    public void setSeconds(short value) {
        Seconds = value;
    }

    public void setMsecs(short value) {
        Msecs = value;
    }

    public Object clone() {

        TimeLengthImpl newVal = new TimeLengthImpl();
        newVal.setYears(getYears());
        newVal.setMonths(getMonths());
        newVal.setDays(getDays());
        newVal.setHours(getHours());
        newVal.setMinutes(getMinutes());
        newVal.setSeconds(getSeconds());
        newVal.setMsecs(getMsecs());
        return newVal;
    }

    public void print(int indent) {
        String sp = null;
        if ((indent > 0) && (indent < 60)) {
            char[] spaces = new char[indent];
            for (int x = 0; x < indent; x++) spaces[x] = ' ';
            sp = new String(spaces);
        }

        Logger.log(sp + "TimeLength:");

        Logger.log(sp + "        Years: " + Years);
        Logger.log(sp + "       Months: " + Months);
        Logger.log(sp + "         Days: " + Days);
        Logger.log(sp + "        Hours: " + Hours);
        Logger.log(sp + "      Minutes: " + Minutes);
        Logger.log(sp + "      Seconds: " + Seconds);
        Logger.log(sp + "        Msecs: " + Msecs);

    }

    //PG to review
    public boolean equals(Object tlObj) {
        if (tlObj == null) return false;
        TimeLength tl = (TimeLength) tlObj;

        if (Years != tl.getYears()) return false;
        if (Months != tl.getMonths()) return false;
        if (Days != tl.getDays()) return false;
        if (Hours != tl.getHours()) return false;
        if (Minutes != tl.getMinutes()) return false;
        if (Seconds != tl.getSeconds()) return false;
        if (Msecs != tl.getMsecs()) return false;

        return true;
    }


}
