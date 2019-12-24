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
            int second = Integer.valueOf(tokenizer.nextToken());
            GregorianCalendar calendar = new GregorianCalendar(year,month-1, dayOfMonth, hourOfDay, minute, second);
            return calendar;
        }
    }
    public static String getCurrentDate(){
        return format(new GregorianCalendar());
    }
    public static String format(GregorianCalendar calendar){
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(calendar.getTime());
    }
    public static String getYear(String date){
        StringTokenizer tokenizer = new StringTokenizer(date, " .:");
        tokenizer.nextToken(); // день
        tokenizer.nextToken();// месяц
        return tokenizer.nextToken(); // год
    }
    public static String getMonth(String date){
        StringTokenizer tokenizer = new StringTokenizer(date, " .:");
        tokenizer.nextToken(); // день
        return tokenizer.nextToken();// месяц
    }
    public static String getDay(String date){
        StringTokenizer tokenizer = new StringTokenizer(date, " .:");
        return tokenizer.nextToken(); // день
    }
}
