package skyline.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatter {

    static private SimpleDateFormat sdfdate8 = new SimpleDateFormat("yyyyMMdd");
    static private SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
    static private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");
    static private SimpleDateFormat sdftime8 = new SimpleDateFormat("HH:mm:ss");
    static private SimpleDateFormat sdfdatetime14 = new SimpleDateFormat("yyyyMMddHHmmss");
    static private SimpleDateFormat sdfdatetime18 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTodayAddDays(int days) throws ParseException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.DATE, days);
        Date thatDate = cal.getTime();
        return sdfdate10.format(thatDate);
    }

    public static String getDatetime14() {
    	return sdfdatetime14.format(new Date());
    }

    public static String getDatetime18() {
    	return sdfdatetime18.format(new Date());
    }

    public static String getSdfdate8() {
        return sdfdate8.format(new Date());
    }

    public static String getSdfdate10() {
        return sdfdate10.format(new Date());
    }

    public static String getSdftime6() {
        return sdftime6.format(new Date());
    }

    public static String getSdftime8() {
        return sdftime8.format(new Date());
    }

    public static String getSubstrBetweenStrs(String fromStr, String startStr, String endStr) {
        int length = startStr.length();
        int start = fromStr.indexOf(startStr) + length;
        int end = fromStr.indexOf(endStr);
        return fromStr.substring(start, end);
    }
}
