package jlooney;

//import MoonPhase;

import java.util.Calendar;
import java.util.TimeZone;

public class Phases {
    private static final int LUNATION_OFFSET = 953;
    private static final double MY_PI = 3.14159265358979323846;

    /*<pre> This file is part of the kmoon application with explicit permission by the author
    Copyright 1996 Christopher Osburn <chris@speakeasy.org>

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Library General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Library General Public License for more details.

    You should have received a copy of the GNU Library General Public License
    along with this library; see the file COPYING.LIB.  If not, write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA 02110-1301, USA.
     *</pre>
     */
    /*
     ** jd.c:
     ** 1996/02/11
     **
     ** Copyright 1996, Christopher Osburn, Lunar Outreach Services, <chris@speakeasy.org>
     ** Non-commercial usage license granted to all.
     **
     ** convert a Julian Day number to a struct tm
     **
     ** Parameter:
     **   double jd:  Julian day number with fraction of day
     **
     ** Returns:
     **   struct tm *event_date:  Date-time group holding year, month, day, hour,
     **                and minute of the event
     *</pre>
     */



    /* convert a Julian Date to a date-time group */
    static Calendar JDtoDate(double jd, Calendar event_date) {
	long a, a1, z, b, c, d, e;
	double f, day;

	if (null == event_date){
	    event_date = Calendar.getInstance(TimeZone.getDefault());
	}
	jd += 0.5;
	z = (long) jd;
	f = jd - z;

	if (z < 2299161) {
	    a = z;
	}
	else  {
	    a1 = (long) ((z - 1867216.25) / 36524.25);
	    a = z + 1 + a1 - (long)(a1 / 4);
	}

	b = a + 1524;
	c = (long)((b - 122.1) / 365.25);
	d = (long)(365.25 * c);
	e = (long)((b - d)/30.6001);

	day = b - d - (long)(30.6001 * e) + f;

	if (e < 14L) {
	    event_date.set(Calendar.MONTH, (int)((e - 1) - 1));
	}
	else {
	    event_date.set(Calendar.MONTH, (int)((e - 13) - 1));
	}

	if (event_date.get(Calendar.MONTH) > (2 - 1)) {
	    event_date.set(Calendar.YEAR, (int) (c - 4716));
	}
	else  {
	    event_date.set(Calendar.YEAR,  (int) (c - 4715));
	}
	event_date.set(Calendar.DAY_OF_MONTH,  (int)day);
	day -= event_date.get(Calendar.DAY_OF_MONTH);
	day *= 24;
	event_date.set(Calendar.HOUR_OF_DAY,  (int)day);
	day -= event_date.get(Calendar.HOUR);
	day *= 60;
	event_date.set(Calendar.MINUTE,  (int)day);
	day -= event_date.get(Calendar.MINUTE);
	day *= 60;
	event_date.set(Calendar.SECOND,  (int)day);

	// TODO: HANDLE time zone and daylight saving time -- ignored

	return event_date;
    }

    /* convert a date-time group to a JD with fraction */
    double DatetoJD(Calendar _event_date) {
	Calendar event_date = _event_date;
	int y, m;
	double d;
	int a, b;
	double jd;

	y = event_date.get(Calendar.YEAR);
	m = event_date.get(Calendar.MONTH)+ 1;
	d = (double)(event_date.get(Calendar.DAY_OF_MONTH) + (event_date.get(Calendar.HOUR_OF_DAY) / 24.0)
		+ (event_date.get(Calendar.MINUTE) / 1440.0) + (event_date.get(Calendar.SECOND)/ 86400.0));
	if (m == 1 || m == 2) {
	    y--;
	    m += 12;
	}

	a = (int)(y / 100);
	b = 2 - a + (int)(a / 4);

	if (y < 1583)
	    if ((y < 1582) || (m < 10) || ((m == 10) && (d <= 15)))
		b = 0;
	jd = (long)(365.25 * (y + 4716)) + (long)(30.6001 * (m+1))
	+ d + b - 1524.5;
	return jd;
    }

    /*
     ** misc.h
     ** 1996/02/11
     **
     ** Copyright 1996, Christopher Osburn, Lunar Outreach Services,
     ** Non-commercial usage license granted to all.
     **
     ** Miscellaneous routines for moon phase programs
     * @param x
     * @return
     */
    static double torad(double x) {
	return Math.toRadians(x); /* normalize the angle */
	//  x = Math.floor(x / 360.0); /* normalize the angle */
	// ????x = fmod(x, 360.0); /* normalize the angle */
	/**
	double _x = x % 360.0;
	return (_x * 0.01745329251994329576);
	*/
	//double _x = Math.IEEEremainder(x, 360.0);
       //return (x) * (MY_PI / 180.0);
	/* and return the result */
    }

    /*
     * <pre>
     ** moonphase.c
     ** 1996/02/11
     **
     ** Copyright 1996, Christopher Osburn, Lunar Outreach Services,
     ** Non-commercial usage license granted to all.
     **
     ** calculate phase of the moon per Meeus Ch. 47
     **
     ** Parameters:
     **    int lun:  phase parameter.  This is the number of lunations
     **              since the New Moon of 2000 January 6.
     **
     **    int phi:  another phase parameter, selecting the phase of the
     **              moon.  0 = New, 1 = First Qtr, 2 = Full, 3 = Last Qtr
     **
     ** Return:  Apparent JD of the needed phase
     *</pre>
     */

    static double moonphase(double k, int phi) {
	int i;                       /* iterator to be named later.  Every program needs an i */
	double T;                    /* time parameter, Julian Centuries since J2000 */
	double JDE;                  /* Julian Ephemeris Day of phase event */
	double E;                    /* Eccentricity anomaly */
	double M;                    /* Sun's mean anomaly */
	double M1;                   /* Moon's mean anomaly */
	double F;                    /* Moon's argument of latitude */
	double O;                    /* Moon's longitude of ascenfing node */
	double[] A = new double[15]; /* planetary arguments */
	double W;                    /* added correction for quarter phases */
	T = k / 1236.85;                            /* (47.3) */

	/* this is the first approximation.  all else is for style points! */
	JDE = 2451550.09765 + (29.530588853 * k)    /* (47.1) */
	+ T * T * (0.0001337 + T * (-0.000000150 + 0.00000000073 * T));

	/* these are correction parameters used below */
	E = 1.0                                     /* (45.6) */
	+ T * (-0.002516 + -0.0000074 * T);
	M = 2.5534 + 29.10535669 * k                /* (47.4) */
	+ T * T * (-0.0000218 + -0.00000011 * T);
	M1 = 201.5643 + 385.81693528 * k            /* (47.5) */
	+ T * T * (0.0107438 + T * (0.00001239 + -0.000000058 * T));
	F = 160.7108 + 390.67050274 * k             /* (47.6) */
	+ T * T * (-0.0016341 * T * (-0.00000227 + 0.000000011 * T));
	O = 124.7746 - 1.56375580 * k               /* (47.7) */
	+ T * T * (0.0020691 + 0.00000215 * T);

	/* planetary arguments */
	A[0]  = 0; /* unused! */
	A[1]  = 299.77 +  0.107408 * k - 0.009173 * T * T;
	A[2]  = 251.88 +  0.016321 * k;
	A[3]  = 251.83 + 26.651886 * k;
	A[4]  = 349.42 + 36.412478 * k;
	A[5]  =  84.66 + 18.206239 * k;
	A[6]  = 141.74 + 53.303771 * k;
	A[7]  = 207.14 +  2.453732 * k;
	A[8]  = 154.84 +  7.306860 * k;
	A[9]  =  34.52 + 27.261239 * k;
	A[10] = 207.19 +  0.121824 * k;
	A[11] = 291.34 +  1.844379 * k;
	A[12] = 161.72 + 24.198154 * k;
	A[13] = 239.56 + 25.513099 * k;
	A[14] = 331.55 +  3.592518 * k;

	/* all of the above crap must be made into radians!!! */
	/* except for E... */

	M = torad(M);
	M1 = torad(M1);
	F = torad(F);
	O = torad(O);

	/* all those planetary arguments, too! */
	for (i=1; i<=14; ++i)
	    A[i] = torad(A[i]);

	/* ok, we have all the parameters, let's apply them to the JDE.
    (remember the JDE?  this is a program about the JDE...)        */

	switch(phi) {
	/* a special case for each different phase.  NOTE!,
       I'm not treating these in a 0123 order!!!  
       Pay attention, there,  you!                         */

	case 0: /* New Moon */
	    JDE = JDE
	    - 0.40720         * Math.sin (M1)
	    + 0.17241 * E     * Math.sin (M)
	    + 0.01608         * Math.sin (2.0 * M1)
	    + 0.01039         * Math.sin (2.0 * F)
	    + 0.00739 * E     * Math.sin (M1 - M)
	    - 0.00514 * E     * Math.sin (M1 + M)
	    + 0.00208 * E * E * Math.sin (2.0 * M)
	    - 0.00111         * Math.sin (M1 - 2.0 * F)
	    - 0.00057         * Math.sin (M1 + 2.0 * F)
	    + 0.00056 * E     * Math.sin (2.0 * M1 + M)
	    - 0.00042         * Math.sin (3.0 * M1)
	    + 0.00042 * E     * Math.sin (M + 2.0 * F)
	    + 0.00038 * E     * Math.sin (M - 2.0 * F)
	    - 0.00024 * E     * Math.sin (2.0 * M1 - M)
	    - 0.00017         * Math.sin (O)
	    - 0.00007         * Math.sin (M1 + 2.0 * M)
	    + 0.00004         * Math.sin (2.0 * M1 - 2.0 * F)
	    + 0.00004         * Math.sin (3.0 * M)
	    + 0.00003         * Math.sin (M1 + M - 2.0 * F)
	    + 0.00003         * Math.sin (2.0 * M1 + 2.0 * F)
	    - 0.00003         * Math.sin (M1 + M + 2.0 * F)
	    + 0.00003         * Math.sin (M1 - M + 2.0 * F)
	    - 0.00002         * Math.sin (M1 - M - 2.0 * F)
	    - 0.00002         * Math.sin (3.0 * M1 + M)
	    + 0.00002         * Math.sin (4.0 * M1);

	    break;

	case 2: /* Full Moon */
	    JDE = JDE
	    - 0.40614         * Math.sin (M1)
	    + 0.17302 * E     * Math.sin (M)
	    + 0.01614         * Math.sin (2.0 * M1)
	    + 0.01043         * Math.sin (2.0 * F)
	    + 0.00734 * E     * Math.sin (M1 - M)
	    - 0.00515 * E     * Math.sin (M1 + M)
	    + 0.00209 * E * E * Math.sin (2.0 * M)
	    - 0.00111         * Math.sin (M1 - 2.0 * F)
	    - 0.00057         * Math.sin (M1 + 2.0 * F)
	    + 0.00056 * E     * Math.sin (2.0 * M1 + M)
	    - 0.00042         * Math.sin (3.0 * M1)
	    + 0.00042 * E     * Math.sin (M + 2.0 * F)
	    + 0.00038 * E     * Math.sin (M - 2.0 * F)
	    - 0.00024 * E     * Math.sin (2.0 * M1 - M)
	    - 0.00017         * Math.sin (O)
	    - 0.00007         * Math.sin (M1 + 2.0 * M)
	    + 0.00004         * Math.sin (2.0 * M1 - 2.0 * F)
	    + 0.00004         * Math.sin (3.0 * M)
	    + 0.00003         * Math.sin (M1 + M - 2.0 * F)
	    + 0.00003         * Math.sin (2.0 * M1 + 2.0 * F)
	    - 0.00003         * Math.sin (M1 + M + 2.0 * F)
	    + 0.00003         * Math.sin (M1 - M + 2.0 * F)
	    - 0.00002         * Math.sin (M1 - M - 2.0 * F)
	    - 0.00002         * Math.sin (3.0 * M1 + M)
	    + 0.00002         * Math.sin (4.0 * M1);

	    break;

	case 1: /* First Quarter */
	case 3: /* Last Quarter */
	    JDE = JDE
	    - 0.62801         * Math.sin (M1)
	    + 0.17172 * E     * Math.sin (M)
	    - 0.01183 * E     * Math.sin (M1 + M)
	    + 0.00862         * Math.sin (2.0 * M1)
	    + 0.00804         * Math.sin (2.0 * F)
	    + 0.00454 * E     * Math.sin (M1 - M)
	    + 0.00204 * E * E * Math.sin (2.0 * M)
	    - 0.00180         * Math.sin (M1 - 2.0 * F)
	    - 0.00070         * Math.sin (M1 + 2.0 * F)
	    - 0.00040         * Math.sin (3.0 * M1)
	    - 0.00034 * E     * Math.sin (2.0 * M1 - M)
	    + 0.00032 * E     * Math.sin (M + 2.0 * F)
	    + 0.00032 * E     * Math.sin (M - 2.0 * F)
	    - 0.00028 * E * E * Math.sin (M1 + 2.0 * M)
	    + 0.00027 * E     * Math.sin (2.0 * M1 + M)
	    - 0.00017         * Math.sin (O)
	    - 0.00005         * Math.sin (M1 - M - 2.0 * F)
	    + 0.00004         * Math.sin (2.0 * M1 + 2.0 * F)
	    - 0.00004         * Math.sin (M1 + M + 2.0 * F)
	    + 0.00004         * Math.sin (M1 - 2.0 * M)
	    + 0.00003         * Math.sin (M1 + M - 2.0 * F)
	    + 0.00003         * Math.sin (3.0 * M)
	    + 0.00002         * Math.sin (2.0 * M1 - 2.0 * F)
	    + 0.00002         * Math.sin (M1 - M + 2.0 * F)
	    - 0.00002         * Math.sin (3.0 * M1 + M);

	    W = 0.00306
	    - 0.00038 * E * Math.cos(M)
	    + 0.00026 * Math.cos(M1)
	    - 0.00002 * Math.cos(M1 - M)
	    + 0.00002 * Math.cos(M1 + M)
	    + 0.00002 * Math.cos(2.0 * F);
	    if (phi == 3)
		W = -W;
	    JDE += W;

	    break;

	default: /* oops! */
	    System.out.print("The Moon has exploded!\n");
	    //(stderr, "The Moon has exploded!\n");
	    //exit(1);
	    throw new RuntimeException("Fatal ERROR!");
	    //break; /* unexecuted code */
	}
	/* now there are some final correction to everything */
	JDE = JDE
	+ 0.000325 * Math.sin(A[1])
	+ 0.000165 * Math.sin(A[2])
	+ 0.000164 * Math.sin(A[3])
	+ 0.000126 * Math.sin(A[4])
	+ 0.000110 * Math.sin(A[5])
	+ 0.000062 * Math.sin(A[6])
	+ 0.000060 * Math.sin(A[7])
	+ 0.000056 * Math.sin(A[8])
	+ 0.000047 * Math.sin(A[9])
	+ 0.000042 * Math.sin(A[10])
	+ 0.000040 * Math.sin(A[11])
	+ 0.000037 * Math.sin(A[12])
	+ 0.000035 * Math.sin(A[13])
	+ 0.000023 * Math.sin(A[14]);
	return JDE;
    }

    //public static LUNATION_OFFSET = 953;

    /**
     * @param lun lunation
     * @param phi
     * @return
     */
    static double moonphasebylunation(int lun, int phi) {
	double k;
	k = lun - LUNATION_OFFSET + phi / 4.0;
	return moonphase(k, phi);
    }
}
