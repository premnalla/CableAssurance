package ossj.qos.util;
/**
 * Title:        ScaledTimeGMT
 * Description:
 * Copyright:    Copyright (c) 2001 NEC Corporation
 * Company:      NEC Corporation
 * @author Wahei Usui
 * @version 1.0
 */

import java.util.*;
import java.text.DateFormat;

public class ScaledTimeGMT {

  private static SimpleTimeZone gmt = new SimpleTimeZone( 0, "GMT");
  private long end;
  private int scaledGP;
  private int scaleFactor;
  private int granularityPeriod;


  public ScaledTimeGMT(GregorianCalendar gcl) {

    // defalut scale 60, GP = 15 minutes
    scaleFactor = 60;
    granularityPeriod = 15*60*1000;

    // The following two lines are to round the end.
    // Otherwise two instances of this class would have different milliSecs.
    end = gcl.getTime().getTime()/1000;
    end *= 1000;
    //System.out.println("**ScaledTimeGMT** end:"+formatTime(end));

    scaledGP = granularityPeriod / scaleFactor;
  }

  public long getCurrentTimeStamp() {
//	return getTimeStamp(new Date().getTime()-CORRECTION);
    return getTimeStamp(new Date().getTime());
  }

  /**
   * This method returns the row number one should read at a given time.
   * The returned number is between 0 and numberOfRows.
   * It is based on the same validation and method as in getTimeStamp().
   * @param time the actual time.
   * @param numberOfRows The number of rows in the data file (1000 for Nice demo).
   * @return a number between 0 and numberOfRows.
   */
  public int getRowNumber(long time, int numberOfRows) {
    long row=end-getTimeStamp(time); // Use of getTimeStamp here so validation and formulas are the same.

    row /= granularityPeriod;

    row = numberOfRows- (row % numberOfRows);

    row--;

    return (int) row;
  }

  public long getTimeStamp(long time) {
    if (time > end) {
      throw new RuntimeException("Error: ScaledTimeGMT is going into the future");
    }
    // Add 100 millisec.  Helps if time stamp
    // is slightly before a GP boundary (can happen if you are sleeping
    // and wake up a little early).
    time += 100;

    // do the scaling
    long trueTimeStamp = end - ((end - time) * scaleFactor);

    // round down to the scaled GP boundary
    return (trueTimeStamp / granularityPeriod) * granularityPeriod;
  }

  // how long to sleep if you need to wake up at GP boundary.
  public long getRealTimeUntilNextGPBoundary() {
//	long rval = scaledGP - ((new Date().getTime()-CORRECTION) % scaledGP);
    long rval = scaledGP - ((new Date().getTime()) % scaledGP);
    if (rval < 100) {
      // assume we are already at this GP -- advance to next one
      rval += scaledGP;
    }
    return rval;
  }

  public void sleepUntilNextGP() {
    try {
      Thread.sleep(this.getRealTimeUntilNextGPBoundary());
    } catch (Exception x) {
    }
  }

  public long getEndTime() {
    return end;
  }

  public static void main(String[] args) {

    GregorianCalendar currentDateApprox = new GregorianCalendar ();
    currentDateApprox.add(GregorianCalendar.DATE, 1);

       while (true) {
    ScaledTimeGMT demoTime = new ScaledTimeGMT( currentDateApprox );
//VP
//System.out.println("Time reference is: " + formatTime(demoTime.getEndTime()));
//System.out.println("Start time: " + formatTime(new Date().getTime()-CORRECTION));
//System.out.println("Start time: " + formatTime(new Date().getTime()));

//System.out.println("TimeStamp: " + formatTime(demoTime.getCurrentTimeStamp()));
      demoTime.sleepUntilNextGP();
//System.out.println("  Woke at: " + formatTime(new Date().getTime()-CORRECTION));
//System.out.println("  Woke at: " + formatTime(new Date().getTime()));
//System.out.println("Row: " + demoTime.getRowNumber( new Date().getTime(), 1000) );
      // simulate doing some stuff
      try {Thread.sleep(500);} catch (Exception x) {}
    }
  }

  private static String formatTime(long time) {
    DateFormat	dfm = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
    dfm.setTimeZone(gmt);

    return dfm.format(new Date(time)) + "  (" + (time % 1000) + " MS)";
  }
}
