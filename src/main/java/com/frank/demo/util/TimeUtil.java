package com.frank.demo.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


public class TimeUtil {

    
    public static String getCommonFormatStr() {
        return "yyyy-MM-dd";
    }
    
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException{
    	GregorianCalendar c = new GregorianCalendar();
    	c.setTime(date);
    	XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    	return date2;
    }
    
    public static boolean compareDatesNotIncludingTime(Date date1, Date date2){
    	if (CommonUtil.isExNull(date1) || CommonUtil.isExNull(date2)) {
    		return false;
    	}
    	
    	String d1 = TimeUtil.getDay(date1, "yyyy-MM-dd");
    	String d2 = TimeUtil.getDay(date2, "yyyy-MM-dd");
    	
    	if (d1.equals(d2)) {
    		return true;
    	} else {
    		return false;
    	}
    	
    }
    
    
    public static long getDateDifInMinutes(Date startDate, Date endDate) {
			long duration  = endDate.getTime() - startDate.getTime();
			long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			return diffInMinutes;
     }
    
    
    
    public static int getDateDif(Date fromDate, Date toDate) {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fromDate);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(toDate);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;

     }
    
    public static DateFormat getCommonFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static Date getCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        return calendar.getTime();
    }
    
	public static Date getDateByIncMonth(Date date, int inc) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, inc);
		return calendar.getTime();
	}

    public static Date getDateByIncMinute(Date date, int inc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, inc);
        return calendar.getTime();
    }

    public static String getCurrentDate(String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Date currentDate = getCurrentDate();

        return sdf.format(currentDate);
    }

    public static String getDay(String day, int inc, String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Calendar calendar = new GregorianCalendar();
        // Date now = new Date();
        if (day == null || "".equals(day)) {
            return sdf.format(calendar.getTime());
        }
        Date date = null;
        try {
            date = sdf.parse(day);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, inc);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            //log.error(e.toString());
            return null;
        }
    }

    public static String getDay(int inc, String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Calendar calendar = new GregorianCalendar();
        // Date now = new Date();
        // calendar.setTime(now);
        calendar.add(Calendar.DATE, inc);
        return sdf.format(calendar.getTime());
    }
    
    public static Date getDay(int inc) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, inc);
        return calendar.getTime();
    }
    
    public static Date getDay(Date date, int inc) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, inc);
        return calendar.getTime();
    }
    
    public static Date getDay(int inc, int field) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(field, inc);
        return calendar.getTime();
    }

    public static Date getDay(String day, String formatStyle) {
    	if (CommonUtil.isExNull(day)) {
    		return null;
    	}
    	
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        try {
            return sdf.parse(day);
        } catch (ParseException e) {
            //log.error(e.toString());
            return null;
        }
    }
    


    public static String getDay(Date day, String formatStyle) {
        if (day == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
            return sdf.format(day);
        }
    }

    public static boolean isAfterNow(String day, String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Calendar now = Calendar.getInstance();
        try {
            if (sdf.parse(sdf.format(now.getTime())).before(sdf.parse(day))) {
                return true;
            }
        } catch (ParseException e) {
            //log.error(e.toString());
        }
        return false;
    }
    
    public static boolean isAfterNow(Date day, String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Calendar now = Calendar.getInstance();
        try {
            if (sdf.parse(sdf.format(now.getTime())).before(day)) {
                return true;
            }
        } catch (ParseException e) {
            //log.error(e.toString());
        }
        return false;
    }
    
    public static boolean isBeforeNow(String day, String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Calendar now = Calendar.getInstance();
        try {
            if (sdf.parse(sdf.format(now.getTime())).after(sdf.parse(day))) {
                return true;
            }
        } catch (ParseException e) {
            //log.error(e.toString());
        }
        return false;
    }
    
    public static boolean isBeforeNow(Date day, String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Calendar now = Calendar.getInstance();
        try {
            if (sdf.parse(sdf.format(now.getTime())).after(day)) {
                return true;
            }
        } catch (ParseException e) {
            //log.error(e.toString());
        }
        return false;
    }
    
    public static boolean isBetween(Date date, Date startDate, Date endDate) {
        if ((date.after(startDate) || date.equals(startDate)) 
                && (date.before(endDate) || date.equals(endDate))) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isBetween(Date date, Date startDate, Date endDate, String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Date compareDate;
        Date compareStartDate;
        Date compareEndDate;
        
        try {
            compareDate = sdf.parse(sdf.format(date));
            compareStartDate = sdf.parse(sdf.format(startDate));
            compareEndDate = sdf.parse(sdf.format(endDate));
        } catch (ParseException e) {
            //log.error(e.getMessage());
            
            return false;
        }
        
        if ((compareDate.after(compareStartDate) || compareDate.equals(compareStartDate)) 
                && (compareDate.before(compareEndDate) || compareDate.equals(compareEndDate))) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isBetween(String dateStr, String startDateStr, String endDateStr, String formatStyle) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        Date date;
        Date startDate;
        Date endDate;
        try {
            date = sdf.parse(dateStr);
            startDate = sdf.parse(startDateStr);
            endDate = sdf.parse(endDateStr);
        } catch (ParseException e) {
            //log.error(e.getMessage());
            
            return false;
        }
        
        if ((date.after(startDate) || date.equals(startDate)) 
                && (date.before(endDate) || date.equals(endDate))) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isEquals(String oneStr, String otherStr) {
    	if (CommonUtil.isExNull(oneStr) && CommonUtil.isExNull(otherStr)) {
    		return true;
    	}
    	if (!CommonUtil.isExNull(oneStr) && CommonUtil.isExNull(otherStr)) {
    		return false;
    	}
    	if (CommonUtil.isExNull(oneStr) && !CommonUtil.isExNull(otherStr)) {
    		return false;
    	}
    	
    	String formatStyle = "yyyyMMdd";
    	Date oneDate = getDay(oneStr, formatStyle);
    	Date otherDate = getDay(otherStr, formatStyle);
    	
    	if (!CommonUtil.isExNull(oneDate)) {
    		if (oneDate.equals(otherDate)) {
    			return true;
    		}
    	}
    	if (!CommonUtil.isExNull(otherDate)) {
    		if (otherDate.equals(oneDate)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean isEquals(Date one, String other) {
    	return isEquals(getDay(one, "yyyyMMdd"), other);
    }

    public static String getPreviousDay(String day) {
        return getDay(day, -1, "yyyy-MM-dd");
    }

    public static String getMonthBegin(String strDate) {
        return strDate + "01000000";
    }

    public static String getMonthEnd(String strDate) {
        SimpleDateFormat formatYYYYMM = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat formatYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = formatYYYYMM.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatYYYYMMDD.format(calendar.getTime()) + "235959";
    }

    public static Date getDayBegin(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }

    public static Date getDayEnd(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        
        return calendar.getTime();
    }

    public static Date getLastMonthBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastMonthEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        return calendar.getTime();
    }


    public static Date getLastDayBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    public static Date getLastDayEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        return calendar.getTime();
    }

    public static Date getCurrentDayBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    public static Date getCurrentDayEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        return calendar.getTime();
    }


    public static Date getLastHourBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastHourEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        return calendar.getTime();
    }


    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            //ex.printStackTrace();
        }
        return date != null;
    }

    public static Date getThisMonthBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getThisMonthEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        return calendar.getTime();
    }



    public static void main(String[] args) {
        //System.out.println( isValidFormat("yyyyMMddHHmm","201721101110"));
    }



}
