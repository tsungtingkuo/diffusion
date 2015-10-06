package network;

import java.text.*;
import java.util.*;

public class Time {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.0");

	public static final long start = 1293811200000l;	// 2011-01-01 00:00:00.0
	public static final long stop = 1305475200000l;		// 2011-05-16 00:00:00.0
	public static final long average = 1299643200000l;	// Average of start and stop
	public static final long unit = 86400000l;			// One day

	public static void main(String[] args) throws Exception {
		//System.out.println(getAgeInDayPlusOne(1293811200000l));
		for(long i=start; i<=stop; i+=unit) {
			System.out.println(i + ", " + getAgeInDayPlusOneTransformed(i));
		}
	}

	public static double transform(double age) throws Exception {
		double t = 135;
		return Math.exp(-1.0*age/t);
	}
	
	public static double getAgeInDayTransformed(String s) throws Exception {
		return transform(getAgeInDay(s));
	}
	
	public static double getAgeInDayPlusOneTransformed(String s) throws Exception {
		return transform(getAgeInDayPlusOne(s));
	}

	public static double getAgeInDayPlusOneTransformed(long time) throws Exception {
		return transform(getAgeInDayPlusOne(time));
	}

	public static double getAgeInDay(String s) throws Exception {
		return getAgeInDay(parseTime(s).getTimeInMillis());
	}

	public static double getAgeInDayPlusOne(String s) throws Exception {
		return getAgeInDayPlusOne(parseTime(s).getTimeInMillis());
	}

	public static double getAgeInDayPlusOne(long time) throws Exception {
		return getAgeInDay(time)+1.0;
	}
	
	public static double getAgeInDay(long time) throws Exception {
		if(time<start || time>=stop) {
			time = average;
		}
		return (double)(stop - time)/(double)unit;
		//return (double)(time - start)/(double)unit;
	}
	
	public static GregorianCalendar parseTime(String s) throws Exception {
		Date d = sdf.parse(s);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return c;
	}

	public static String formatTime(GregorianCalendar c) throws Exception {
		Date d = c.getTime();
		String s = sdf.format(d);
		return s;
	}

	/**
	 * @return the start
	 */
	public static long getStart() {
		return start;
	}

	/**
	 * @return the stop
	 */
	public static long getStop() {
		return stop;
	}
}
