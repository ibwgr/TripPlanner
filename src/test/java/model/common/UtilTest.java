package model.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import testFramework.UnitTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 17.01.2017.
 */
public class UtilTest {

    @Category({ UnitTest.class })
    @Test
    public void addDaysPlusOneDayShouldReturnDatePlusOneDay() {

        DateFormat formatter ;
        String strDate;
        Date dateBefore = null;
        Date dateExpected = null;
        Date dateAfter = null;
        try {
            //
            strDate="31.12.1999";
            formatter = new SimpleDateFormat("dd.MM.yyyy");
            dateBefore = formatter.parse(strDate);
            System.out.println("before: " +dateBefore);
            //
            strDate="01.01.2000";
            formatter = new SimpleDateFormat("dd.MM.yyyy");
            dateExpected = formatter.parse(strDate);
            System.out.println("expected: " +dateExpected);
            //
            dateAfter = Util.addDays(dateBefore,1);
            System.out.println("after: " +dateAfter);
            //
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(dateExpected , dateAfter);
    }


    @Category({ UnitTest.class })
    @Test
    public void addDaysMinusOneDayShouldReturnDateMinusOneDay() {

        DateFormat formatter ;
        String strDate;
        Date dateBefore = null;
        Date dateExpected = null;
        Date dateAfter = null;
        try {
            //
            strDate="01.01.2000";
            formatter = new SimpleDateFormat("dd.MM.yyyy");
            dateBefore = formatter.parse(strDate);
            System.out.println("before: " +dateBefore);
            //
            strDate="31.12.1999";
            formatter = new SimpleDateFormat("dd.MM.yyyy");
            dateExpected = formatter.parse(strDate);
            System.out.println("expected: " +dateExpected);
            //
            dateAfter = Util.addDays(dateBefore,-1);
            System.out.println("after: " +dateAfter);
            //
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(dateExpected , dateAfter);
    }

}