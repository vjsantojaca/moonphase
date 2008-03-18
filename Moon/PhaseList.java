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
/**
 * from Stellafane Moon Phase Calculator for http://www.Stellafane.com 1999-Apr-24 KHS Created by Ken Slater
 * User: xh
 * Date: Feb 23, 2008
 * Time: 2:36:30 AM
 * To change this template use File | Settings | File Templates.
 * converted from JS source in document saved from http://stellafane.org/observing/moon_phase.html
 * STATUS: unfinished
 */
public class PhaseList {

	/**Emulates BASIC's floor Funtion*/
    public static double meFloor(double n) {
        return Math.floor(n);
    } 


    public static Calendar TDTtoUTC(Calendar tCal) {

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
        double deltaT = 0; // deltaT = TDT - UTC (in Seconds)
        long Year = tCal.get(Calendar.YEAR);
        long t = (Year - 2000) / 100;    // Centuries from the epoch 2000.0

        if (Year >= TBLfirst && Year <= TBLlast) { // Find correction in table
            if (1 == (Year % 2)) { // Odd year - interpolate
                deltaT = (TBL[(int)(Year - TBLfirst - 1) / 2] + TBL[(int)(Year - TBLfirst + 1) / 2]) / 2;
            } else { // Even year - direct table lookup
                deltaT = TBL[(int)(Year - TBLfirst) / 2];
            }
        } else if (Year < 948) {
            deltaT = 2177 + 497 * t + 44.1 * (t*t);
        } else if (Year >= 948) {
            deltaT = 102 + 102 * t + 25.3 * (t*t);
            if (Year >= 2000 && Year <= 2100) { // Special correction to avoid discontinurity in 2000
                deltaT += 0.37 * (Year - 2100);
            }
        } else {
            throw new NumberFormatException();
            
            //System.out.println("Error: TDT to UTC correction not computed");
        }

        
        // convert calRes to milliseconds
        Date date = tCal.getTime();
        
        long adjustedDate = date.getTime() - ((long)(deltaT * 1000));
        
        Calendar calRes = Calendar.getInstance();
        
        calRes.setTime(new Date(adjustedDate));

        return calRes; 
    } // End TDTtoUTC

    public static Calendar JDtoUTC2(double jd)
{
	int z;
	double f;
	int alp,a,b,c,d,e;
	double dd;
	int mm,yy;
	double tmpH,tmpM,tmpS;

	z=(int)(jd+0.5);
	f=(jd+0.5)-z;
	if(z<2299161) {
		a=z;
	}
	else {
		alp=(int)((z-1867216.25)/36524.25);
		a=z+1+alp-(alp / 4);
	}
	b=a+1524;
	c=(int)((b-122.1)/365.25);
	d=(int)(c*365.25);
	e=(int)((b-d)/30.6001);

	if(e<14)
		mm=e-1;
	else
		mm=e-13;


	if(mm>2)
		yy=c-4716;
	else
		yy=c-4715;

	dd=b-d-(int)(30.6001*e)+f;
    
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, yy);
	cal.set(Calendar. MONTH, mm - 1);
	cal.set(Calendar. DATE, (int)(dd));
	tmpH=(dd-(int)dd)*24;
	cal.set(Calendar.HOUR_OF_DAY, (int)(tmpH));
	tmpM=(tmpH-(int)(tmpH))*60;
	cal.set(Calendar.MINUTE, (int)(tmpM));
	tmpS=(tmpM-(int)(tmpM))*60;
	cal.set(Calendar.SECOND, (int)(tmpS));

	return cal;
}

    
//    public static Calendar JDtoUTC(double JD) {
//        // JD = Julian Date, possible with fractional days
//        // Output is a JavaScript UTC Date Object
//        double A, alpha;
//        double Z = meFloor(JD + 0.5); // Integer JD's
//        double F = (JD + 0.5) - Z;     // Fractional JD's
//        if (Z < 2299161) {
//            A = Z;
//        } else {
//            alpha = meFloor((Z - 1867216.25) / 36524.25);
//            A = Z + 1 + alpha - meFloor(alpha / 4);
//        }
//        double B = A + 1524;
//        double C = meFloor((B - 122.1) / 365.25);
//        double D = meFloor(365.25 * C);
//        double E = meFloor((B - D) / 30.6001);
//        double DT = B - D - meFloor(30.6001 * E) + F;    // Day of Month with decimals for time
//        double Mon = (E - (E < 13.5 ? 1 : 13));        // Month Number
//        double Yr =  (C - (Mon > 2.5 ? 4716 : 4715));        // Year
//        double Day =  meFloor(DT);                 // Day of Month without decimals for time
//        double H =  (24 * (DT - Day));                // Hours and fractional hours
//        double Hr =  meFloor(H);                 // Integer Hours
//        double M = 60 * (H - Hr);                    // Minutes and fractional minutes
//        double Min =  meFloor(M);                // Integer Minutes
//        double Sec = meFloor(60 * (M - Min));        // Integer Seconds (Milliseconds discarded)
//        //Create and set a JavaScript Date Object and return it
//        //Date theDate = new Date(0);
//        
//        Calendar cal = Calendar.getInstance();
//        // LK:NOT ADJUSTED Mon cal.set(Yr, Mon, Day, Hr, Min, Sec);
//        
//        //cal.set(Yr, Mon - 1, Day, Hr, Min, Sec);
//        
//        cal.set(Calendar.YEAR,(int)Yr);
//        cal.set(Calendar.MONTH,(int) Mon-1);
//        cal.set(Calendar.DAY_OF_MONTH,(int)Day);
//        cal.set(Calendar.HOUR,(int)Hr);
//        cal.set(Calendar.MINUTE,(int)Min);
//        cal.set(Calendar.SECOND,(int)Sec);
//        
//        //cal.setFirstDayOfWeek(Calendar.MONDAY);
////        theDate. .setUTCFullYear(Yr, Mon-1, Day);
////        theDate.setUTCHours(Hr, Min, Sec);
//        return (cal);
//    } //End JDtoUTC

    public static final String WEEK_DAYS[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

    public static final String MONTH_NAMES[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec" };

    public static final String AM_PM_CODE[] = {"AM","PM" };

    public static String moonPhaseListYear(int year) {
        //Converted from Basic by Roger W. Sinnot, Sky & Telescope, March 1985.
        double R1 = Math.PI / 180;
        boolean U = false;
        double K0, T, T2, T3, J0, F0, J, F, M0, M1, B1, K9, K, M5, M6, B6;
        double JDE = 0;
        Calendar TDT, UTC = null;
        StringBuffer strbuf = new StringBuffer();

        //String s = ""; // Formatted Output String
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

            TDT = JDtoUTC2(JDE);        // Convert Julian Days to TDT in a Cal Object

            
            // !!!UNCOMMENT FOR PROPER OPERATION 
            UTC = TDTtoUTC(TDT);        // Correct TDT to UTC, both as Cal Objects
            //UTC = TDT; // Comment for correct results
//        if ( !GetExact() || GetYear()==UTC.getFullYear() ) {	//Filter output
//            switch ( GetTZ() ) {
//                case "LCL": str = UTC.toString()    + "\n"; break;	//Convert to Local time string
//                case "UTC": str = UTC.toUTCString() + "\n"; break;	//Convert to UTC time string
//                case "DYN": str = TDT.toUTCString() + "\n";			//Convert to Dynamical Time String
//                        str = str.replace( /UTC/g, "TDT" ); break;	// Change UTC to TDT in this output string
//            }
//            if ( !U ) WriteNew( str ); else WriteFull( str ); 	//Output Result to correct panel
//        }
            U = !U;

            String newOrFull;
            newOrFull = U ? "N " : "F ";
            

            //SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm");

            //System.out.println(newOrFull + sdf.format(UTC.getTime()));

            //System.out.println(newOrFull + UTC.getTime());
            //strbuf.append(newOrFull  + UTC.getTime() + "\n");

            
//            
//            int HOUR_MIL = UTC.get(Calendar.AM_PM) == Calendar.AM  
//                            ? UTC.get(Calendar.HOUR)
//                            : UTC.get(Calendar.HOUR)+12;
            
            // UTC.get(Calendar.MINUTE) < 10 ? "0" + UTC.get(Calendar.MINUTE): UTC.get(Calendar.MINUTE)
            
            // normalize minutes display
            int int_minutes = UTC.get(Calendar.MINUTE);
            String str_minutes = int_minutes < 10 ? "0" + int_minutes: "" + int_minutes;
            
            strbuf.append(newOrFull + WEEK_DAYS[ UTC.get(Calendar.DAY_OF_WEEK)-1] + " " +
                                       UTC.get(Calendar.DATE) + "-" +
                                       MONTH_NAMES[UTC.get(Calendar.MONTH)] + "-" +
                                       //UTC.get(Calendar.YEAR) + " " +
                                       Integer.toString(UTC.get(Calendar.YEAR)).substring(2) + " " +
                                       UTC.get(Calendar.HOUR_OF_DAY) + ":" 
                                        +  str_minutes + "\n");
            
        } // Next
        return strbuf.toString(); ///////////////
    } //End MoonPhase

    public static void main(String args[]) {
        //Calendar calPhase = ;
        System.out.println(moonPhaseListYear(2008));
    }
}

