/*
 * Generates a list of new and full moon dates for the current year.
 */

package Moon;

/**
 *
 * @author xh
 */
//import static java.lang.Math.*;

import java.util.Calendar;
import java.util.Date;
import java.lang.Math;

//import java.text.DateFormat;
import java.util.TimeZone;

/**
 * from Stellafane Moon Phase Calculator for http://www.Stellafane.com 1999-Apr-24 KHS Created by Ken Slater
 * User: xh
 * Date: Feb 23, 2008
 * Time: 2:36:30 AM
 * Generates a list of new and full moon dates for the current year.
 * converted from JavaScript code found at http://stellafane.org/observing/moon_phase.html
 * STATUS: completed
 * @noinspection ConstantConditions
 */
public class PhaseList {

    /*
     * Emulates BASIC's floor Funtion
     */
    public static double meFloor(double n) {
        return Math.floor(n);
    }

    private static Calendar TDTtoUTC(Calendar tCal) {

        // Correction lookup table has entry for every even year between TBLfirst and TBLlast
        long TBLfirst = 1620, TBLlast = 2002;    // Range of years in lookup table

        double[] TBL = {                    // Corrections in Seconds
                /*1620*/ 121, 112, 103, 95, 88, 82, 77, 72, 68, 63, 60, 56, 53, 51, 48, 46, 44, 42, 40, 38,
                /*1660*/  35, 33, 31, 29, 26, 24, 22, 20, 18, 16, 14, 12, 11, 10, 9, 8, 7, 7, 7, 7,
                /*1700*/   7, 7, 8, 8, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11,
                /*1740*/  11, 11, 12, 12, 12, 12, 13, 13, 13, 14, 14, 14, 14, 15, 15, 15, 15, 15, 16, 16,
                /*1780*/  16, 16, 16, 16, 16, 16, 15, 15, 14, 13,
                /*1800*/ 13.1, 12.5, 12.2, 12.0, 12.0, 12.0, 12.0, 12.0, 12.0, 11.9, 11.6, 11.0, 10.2, 9.2, 8.2,
                /*1830*/  7.1, 6.2, 5.6, 5.4, 5.3, 5.4, 5.6, 5.9, 6.2, 6.5, 6.8, 7.1, 7.3, 7.5, 7.6,
                /*1860*/  7.7, 7.3, 6.2, 5.2, 2.7, 1.4, -1.2, -2.8, -3.8, -4.8, -5.5, -5.3, -5.6, -5.7, -5.9,
                /*1890*/ -6.0, -6.3, -6.5, -6.2, -4.7, -2.8, -0.1, 2.6, 5.3, 7.7, 10.4, 13.3, 16.0, 18.2, 20.2,
                /*1920*/ 21.1, 22.4, 23.5, 23.8, 24.3, 24.0, 23.9, 23.9, 23.7, 24.0, 24.3, 25.3, 26.2, 27.3, 28.2,
                /*1950*/ 29.1, 30.0, 30.7, 31.4, 32.2, 33.1, 34.0, 35.0, 36.5, 38.3, 40.2, 42.2, 44.5, 46.5, 48.5,
                /*1980*/ 50.5, 52.5, 53.8, 54.9, 55.8, 56.9, 58.3, 60.0, 61.6, 63.0, 63.8, 64.3}; /*2002 last entry*/
        // Values for Delta T for 2000 thru 2002 from NASA
        double deltaT; // deltaT = TDT - UTC (in Seconds)
        long Year = tCal.get(Calendar.YEAR);
        long t = (Year - 2000) / 100;    // Centuries from the epoch 2000.0

        if (Year >= TBLfirst && Year <= TBLlast) { // Find correction in table
            if (1 == (Year % 2)) { // Odd year - interpolate
                deltaT = (TBL[(int) (Year - TBLfirst - 1) / 2] + TBL[(int) (Year - TBLfirst + 1) / 2]) / 2;
            } else { // Even year - direct table lookup
                deltaT = TBL[(int) (Year - TBLfirst) / 2];
            }
        } else if (Year < 948) {
            deltaT = 2177 + 497 * t + 44.1 * (t * t);
        } else if (Year >= 948) {
            deltaT = 102 + 102 * t + 25.3 * (t * t);
            if (Year >= 2000 && Year <= 2100) { // Special correction to avoid discontinurity in 2000
                deltaT += 0.37 * (Year - 2100);
            }
        } else {
            throw new NumberFormatException();

            //System.out.println("Error: TDT to UTC correction not computed");
        }

        // convert calRes to milliseconds
        Date date = tCal.getTime();

        long adjustedDate = date.getTime() - ((long) (deltaT * 1000));

        Calendar calRes = Calendar.getInstance();

        calRes.setTime(new Date(adjustedDate));

        return calRes;
    } // End TDTtoUTC

    private static Calendar JDtoUTC2(double jd) {
        int z;
        double f;
        int alp, a, b, c, d, e;
        double dd;
        int mm, yy;
        double tmpH, tmpM, tmpS;

        z = (int) (jd + 0.5);
        f = (jd + 0.5) - z;
        if (z < 2299161) {
            a = z;
        } else {
            alp = (int) ((z - 1867216.25) / 36524.25);
            a = z + 1 + alp - (alp / 4);
        }
        b = a + 1524;
        c = (int) ((b - 122.1) / 365.25);
        d = (int) (c * 365.25);
        e = (int) ((b - d) / 30.6001);

        if (e < 14)
            mm = e - 1;
        else
            mm = e - 13;


        if (mm > 2)
            yy = c - 4716;
        else
            yy = c - 4715;

        dd = b - d - (int) (30.6001 * e) + f;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, yy);
        cal.set(Calendar.MONTH, mm - 1);
        cal.set(Calendar.DATE, (int) (dd));
        tmpH = (dd - (int) dd) * 24;
        cal.set(Calendar.HOUR_OF_DAY, (int) (tmpH));
        tmpM = (tmpH - (int) (tmpH)) * 60;
        cal.set(Calendar.MINUTE, (int) (tmpM));
        tmpS = (tmpM - (int) (tmpM)) * 60;
        cal.set(Calendar.SECOND, (int) (tmpS));

        return cal;
    }


    private static final String WEEK_DAYS[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    private static final String MONTH_NAMES[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    //private static final String AM_PM_CODE[] = {"AM", "PM"};

    public static String moonPhaseListYear(int year) {
        //Converted from Basic by Roger W. Sinnot, Sky & Telescope, March 1985.
        double R1 = Math.PI / 180;
        boolean U = false;
        double K0, T, T2, T3, J0, F0, J, F, M0, M1, B1, K9, K, M5, M6, B6;
        double JDE;
        Calendar TDT, UTC;
        StringBuffer strbuf = new StringBuffer();

        K0 = meFloor((year - 1900) * 12.3685);
        T = (year - 1899.5) / 100;
        T2 = T * T;
        T3 = T * T * T;
        J0 = 2415020 + 29 * K0;
        F0 = 0.0001178 * T2 - 0.000000155 * T3;
        F0 += (0.75933 + 0.53058868 * K0);
        F0 -= (0.000837 * T + 0.000335 * T2);
        M0 = K0 * 0.08084821133;
        M0 = 360 * (M0 - meFloor(M0)) + 359.2242;
        M0 -= 0.0000333 * T2;
        M0 -= 0.00000347 * T3;
        M1 = K0 * 0.07171366128;
        M1 = 360 * (M1 - meFloor(M1)) + 306.0253;
        M1 += 0.0107306 * T2;
        M1 += 0.00001236 * T3;
        B1 = K0 * 0.08519585128;
        B1 = 360 * (B1 - meFloor(B1)) + 21.2964;
        B1 -= 0.0016528 * T2;
        B1 -= 0.00000239 * T3;
        for (K9 = 0; K9 <= 28; K9++) {
            J = J0 + 14 * K9;
            F = F0 + 0.765294 * K9;
            K = K9 / 2;
            M5 = (M0 + K * 029.10535608) * R1;
            M6 = (M1 + K * 385.81691806) * R1;
            B6 = (B1 + K * 390.67050646) * R1;
            F -= 0.4068 * Math.sin(M6);
            F += (0.1734 - 0.000393 * T) * Math.sin(M5);
            F += 0.0161 * Math.sin(2 * M6);
            F += 0.0104 * Math.sin(2 * B6);
            F -= 0.0074 * Math.sin(M5 - M6);
            F -= 0.0051 * Math.sin(M5 + M6);
            F += 0.0021 * Math.sin(2 * M5);
            F += 0.0010 * Math.sin(2 * B6 - M6);
            F += 0.5 / 1440; //Adds 1/2 minute for proper rounding to minutes per Sky & Tel article
            JDE = J + F;                 // Julian Empheris Day with fractions for time of day

            TDT = JDtoUTC2(JDE);        // Convert Julian Days to TDT in a Calendar object

            UTC = TDTtoUTC(TDT);        // Correct TDT to UTC, both as Calendar objects

            U = !U;

            String newOrFull;
            newOrFull = U ? "N " : "F ";

            /* skip previous/next year*/
            if (year != UTC.get(Calendar.YEAR)) {
                continue;
            }


            /* adjust local time zone display*/
            TimeZone tz = TimeZone.getDefault();
//            TimeZone tz = UTC.getTimeZone();
//            int tzOffset = tz. getRawOffset() / (1000 * 60*60);
            long tzOffsetMillis = tz.getOffset(
                    1, //era AD
                    UTC.get(Calendar.YEAR),
                    UTC.get(Calendar.MONTH),
                    UTC.get(Calendar.DAY_OF_MONTH),
                    UTC.get(Calendar.DAY_OF_WEEK),
                    0
            );

            //UTC.setTimeZone(new TimeZone());
            //UTC.set(Calendar.HOUR_OF_DAY, UTC.get(Calendar.HOUR_OF_DAY) + tzOffset);
            long currTime = UTC.getTime().getTime();
            UTC.setTime(new Date(currTime + tzOffsetMillis));

            long tzHours = (tzOffsetMillis / (1000 * 60*60));
            String tzPlusMinus = tzHours > 0? "+":"" ;


            // normalize minutes display
            int int_minutes = UTC.get(Calendar.MINUTE);
            String str_minutes = int_minutes < 10 ? "0" + int_minutes : "" + int_minutes;

            strbuf.append(newOrFull);
            strbuf.append(WEEK_DAYS[UTC.get(Calendar.DAY_OF_WEEK) - 1]);
            strbuf.append(" ");
            strbuf.append(UTC.get(Calendar.DATE));
            strbuf.append("-");
            strbuf.append(MONTH_NAMES[UTC.get(Calendar.MONTH)]);
            strbuf.append(" ");
            strbuf.append(UTC.get(Calendar.HOUR_OF_DAY));
            strbuf.append(":");
            strbuf.append(str_minutes);
            strbuf.append(" (");
            strbuf.append(tzPlusMinus);
            strbuf.append(tzHours);
            strbuf.append(")\n");

        } // Next
        return strbuf.toString(); ///////////////
    } //End MoonPhase

    public static void main(String args[]) {
        //Calendar calPhase = ;
        System.out.println(moonPhaseListYear(2008));
    }
}

