package com.tutorial.apidemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringProccessUtil {
    public static Date StringToDate(String str) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date format. Use dd/MM/yyyy.");
        }
        return date;
    }

    public static String DateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }
    public static Integer daysBetween2Dates(Date x,Date y){
        return Math.toIntExact((y.getTime() - x.getTime()) / (24 * 3600 * 1000));
    }
}
