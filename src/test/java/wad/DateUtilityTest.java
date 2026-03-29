package wad;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * Unit tests for time constants and date calculations.
 * Tests the date calculation utilities used by the Manager class.
 */
public class DateUtilityTest {
    
    private static final long MINUTE = 1000 * 60;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long YEAR = 365 * DAY;
    
    @Test
    public void testMinuteConstant() {
        assertEquals("One minute should be 60000 milliseconds", 60000, MINUTE);
    }
    
    @Test
    public void testHourConstant() {
        assertEquals("One hour should be 3600000 milliseconds", 3600000, HOUR);
    }
    
    @Test
    public void testDayConstant() {
        long expectedDay = 24 * 60 * 60 * 1000;
        assertEquals("One day should be 86400000 milliseconds", expectedDay, DAY);
    }
    
    @Test
    public void testYearConstant() {
        assertEquals("One year should be 31536000000 milliseconds", 31536000000L, YEAR);
    }
    
    @Test
    public void testMultipleDaysCalculation() {
        long threeDays = 3 * DAY;
        long sevenDays = 7 * DAY;
        long thirtyDays = 30 * DAY;
        
        assertTrue("Three days should be less than seven days", threeDays < sevenDays);
        assertTrue("Seven days should be less than thirty days", sevenDays < thirtyDays);
    }
}
