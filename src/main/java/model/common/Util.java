package model.common;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 17.01.2017.
 */
public class Util {

    // Helper Date Method
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}