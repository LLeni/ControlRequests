package sample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Date {
    public static GregorianCalendar getGregorianCalendar(String date){
        if(date == null){
            return null;
        } else {
            StringTokenizer tokenizer = new StringTokenizer(date, " .:");
            int dayOfMonth = Integer.valueOf(tokenizer.nextToken());
            int month = Integer.valueOf(tokenizer.nextToken());
            int year = Integer.valueOf(tokenizer.nextToken());
            int hourOfDay = Integer.valueOf(tokenizer.nextToken());
            int minute = Integer.valueOf(tokenizer.nextToken());
            GregorianCalendar calendar = new GregorianCalendar(year,month-1,dayOfMonth,hourOfDay,minute);
            return calendar;
        }
    }
    public static String getCurrentDate(){
        return format(new GregorianCalendar());
    }
    public static String format(GregorianCalendar calendar){
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(calendar.getTime());
    }
}
