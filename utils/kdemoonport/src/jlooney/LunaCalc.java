package jlooney;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

/***************************************************************************
 * <pre>
 * Copyright 1998,2000 Stephan Kulow <coolo@kde.org> 
 * Copyright 2008 by Davide Bettio <davide.bettio@kdemail.net> 
 * Copyright 2009 by Glad Deschrijver <glad.deschrijver@gmail.com> 
 * This program is free software; you can redistribute it and/or modify 
 * it under the terms ofthe GNU General Public License as published by 
 * the Free Software Foundation; either version 2 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA 02110-1301 USA.
 * Ported to Java by dogpizza@gmail.com.
 * </pre>
 ***************************************************************************/
public class LunaCalc {

    private static final int TEXT_WIDTH = 45;
    private final static long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
    private static final String DATE_FORMAT = "%1$tA, %1$td-%1$tB-%1$tY %1$tT";
    
    public enum InfoKey {CURRENT_DATE, LAST_NEW, NEXT_FULL, NEXT_NEW, CUR_PHASE, MOON_AGE}

    /*
     * 'H' Hour of the day for the 24-hour clock, formatted as two digits with a
     * leading zero as necessary i.e. 00 - 23. 'I' Hour for the 12-hour clock,
     * formatted as two digits with a leading zero as necessary, i.e. 01 - 12.
     * 'k' Hour of the day for the 24-hour clock, i.e. 0 - 23. 'l' Hour for the
     * 12-hour clock, i.e. 1 - 12. 'M' Minute within the hour formatted as two
     * digits with a leading zero as necessary, i.e. 00 - 59. 'S' Seconds within
     * the minute, formatted as two digits with a leading zero as necessary,
     * i.e. 00 - 60 ("60" is a special value required to support leap seconds).
     * 'L' Millisecond within the second formatted as three digits with leading
     * zeros as necessary, i.e. 000 - 999. 'N' Nanosecond within the second,
     * formatted as nine digits with leading zeros as necessary, i.e. 000000000
     * - 999999999. 'p' Locale-specific morning or afternoon marker in lower
     * case, e.g."am" or "pm". Use of the conversion prefix 'T' forces this
     * output to upper case. 'z' RFC 822 style numeric time zone offset from
     * GMT, e.g. -0800. 'Z' A string representing the abbreviation for the time
     * zone. The Formatter's locale will supersede the locale of the argument
     * (if any). 's' Seconds since the beginning of the epoch starting at 1
     * January 1970 00:00:00 UTC, i.e. Long.MIN_VALUE/1000 to
     * Long.MAX_VALUE/1000. 'Q' Milliseconds since the beginning of the epoch
     * starting at 1 January 1970 00:00:00 UTC, i.e. Long.MIN_VALUE to
     * Long.MAX_VALUE.
     * 
     * The following conversion characters are used for formatting dates: 'B'
     * Locale-specific full month name, e.g. "January", "February". 'b'
     * Locale-specific abbreviated month name, e.g. "Jan", "Feb". 'h' Same as
     * 'b'. 'A' Locale-specific full name of the day of the week, e.g. "Sunday",
     * "Monday" 'a' Locale-specific short name of the day of the week, e.g.
     * "Sun", "Mon" 'C' Four-digit year divided by 100, formatted as two digits
     * with leading zero as necessary, i.e. 00 - 99 'Y' Year, formatted as at
     * least four digits with leading zeros as necessary, e.g. 0092 equals 92 CE
     * for the Gregorian calendar. 'y' Last two digits of the year, formatted
     * with leading zeros as necessary, i.e. 00 - 99. 'j' Day of year, formatted
     * as three digits with leading zeros as necessary, e.g. 001 - 366 for the
     * Gregorian calendar. 'm' Month, formatted as two digits with leading zeros
     * as necessary, i.e. 01 - 13. 'd' Day of month, formatted as two digits
     * with leading zeros as necessary, i.e. 01 - 31 'e' Day of month, formatted
     * as two digits, i.e. 1 - 31.
     * 
     * 
     * The following conversion characters are used for formatting common
     * date/time compositions. 'R' Time formatted for the 24-hour clock as
     * "%tH:%tM" 'T' Time formatted for the 24-hour clock as "%tH:%tM:%tS". 'r'
     * Time formatted for the 12-hour clock as "%tI:%tM:%tS %Tp". The location
     * of the morning or afternoon marker ('%Tp') may be locale-dependent. 'D'
     * Date formatted as "%tm/%td/%ty". 'F' ISO 8601 complete date formatted as
     * "%tY-%tm-%td". 'c' Date and time formatted as "%ta %tb %td %tT %tZ %tY",
     * e.g. "Sun Jul 20 16:17:00 EDT 1969".
     */

    // private static final String DATE_FORMAT =
    // "%1$ta, %1$td-%1$tm-%1$tb-%1$tY %1$tH:%1$tM:%1$tS";

    /**
     * @param time
     * @return Map<String, Calendar> map of phase descriptions and dates
     */
    public static Map<Calendar, String> calcStatus(Calendar time) {
	int lun = 0;
	Calendar last_new = null;
	Calendar next_new = null;
	Calendar now = Calendar.getInstance();
	StringBuffer comment = new StringBuffer("Current phase: ");
	// use a treemap to get free key sorting
	TreeMap<Calendar, String> phases = new TreeMap<Calendar, String>();

	do {
	    double JDE = Phases.moonphasebylunation(lun, 0);
	    last_new = next_new;
	    next_new = Phases.JDtoDate(JDE, null);
	    lun++;
	} while (next_new.compareTo(time) < 0);

	lun -= 2;

	Calendar first_quarter = Phases.JDtoDate(
		Phases.moonphasebylunation(lun, 1), null);
	Calendar full_moon = Phases.JDtoDate(
		Phases.moonphasebylunation(lun, 2), null);
	Calendar third_quarter = Phases.JDtoDate(
		Phases.moonphasebylunation(lun, 3), null);

	int m_counter = LunaCalc.diffDays(now, last_new);

	int daysSinceLastNewMoon = LunaCalc.diffDays(now, last_new);
	// int daysToFirstQuarter = LunaCalc.diffDays(now, first_quarter);
	// int daysToThirdQuarter = LunaCalc.diffDays(now, third_quarter);
	int daysToFullMoon = LunaCalc.diffDays(now, full_moon);
	int daysToNextNew = LunaCalc.diffDays(now, next_new);

	if (daysToNextNew > 0){
	    phases.put(next_new, "Next new moon will be in " + daysToNextNew
		    + " days");
	}
	
	if (daysSinceLastNewMoon != 0){
	    phases.put(last_new, "Last new moon was " + daysSinceLastNewMoon
		    + " days ago");
	}
	// if(daysToFirstQuarter == 0)
	// phases.put(first_quarter, "First quarter is today");
	// else
	// phases.put(first_quarter, "First quarter will be in " +
	// daysToFirstQuarter + " days");

	if (daysToFullMoon == 0)
	    phases.put(full_moon, "Full moon is today");
	else
	    phases.put(full_moon, "Next full moon will be in " + daysToFullMoon
		    + " days");
	// phases.put(third_quarter, "Third quarter will be in " +
	// daysToThirdQuarter + " days");


	if (LunaCalc.diffDays(now, full_moon) == 0) {
	    m_counter = 14;
	    comment.append(" is full moon\n");
	} else if ((m_counter <= 15) && (m_counter >= 13)) {
	    m_counter = 14 + LunaCalc.diffDays(now, full_moon);
	    comment.append(" around is full moon\n");
	}

	int diff = LunaCalc.diffDays(first_quarter, now);
	if (diff == 0) {
	    m_counter = 7;
	} else if ((m_counter <= 8) && (m_counter >= 6)) {
	    m_counter = 7 + diff;
	    comment.append(" around first quarter\n");
	}

	diff = LunaCalc.diffDays(last_new, now);
	if (diff == 0) {
	    m_counter = 0;
	} else if ((m_counter <= 1) || (m_counter >= 28)) {

	    m_counter = (29 + diff) % 29;
	    diff = LunaCalc.diffDays(next_new, now);
	    if (diff == 0) {
		m_counter = 0;
	    } else if (diff < 3) {
		m_counter = 29 - diff;
	    }
	    comment.append(" around new moon\n");
	}
	if (LunaCalc.diffDays(third_quarter, now) == 0) {
	    m_counter = 21;
	} else if ((m_counter <= 22) && (m_counter >= 20)) {
	    m_counter = 21 + LunaCalc.diffDays(third_quarter, now);
	    comment.append(" third quarter\n");
	}
	if (!((m_counter >= 0) && (m_counter < 29))) {
	    throw new RuntimeException("invalid delta" + m_counter);
	}

	switch (m_counter) {
	case 0:
	    comment.append("New Moon");
	    break;
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
	    comment.append("Waxing Crescent");
            if (m_counter == 1){
        	comment.append(" (New Moon was yesterday)");
            }
	    break;
	case 7:
	    comment.append("First Quarter");
	    break;
	case 8:
	case 9:
	case 10:
	case 11:
	case 12:
	case 13:
	    comment.append("Waxing Gibbous");
	    if(m_counter == 13){
		comment.append(" (Tomorrow is Full Moon)");
	    }
//		    "Waxing Gibbous (%1 days to Full Moon)",
//		    LunaCalc.diffDays(full_moon, now)));// -fm.daysTo(
	    break;
	case 14:
	    comment.append("Full Moon");
	    break;
	case 15:
	case 16:
	case 17:
	case 18:
	case 19:
	case 20:
	    comment.append("Waning Gibbous");
	    if (m_counter == 20){
		comment.append(" (Yesterday was Full Moon)");
	    }
//	    (Yesterday was Full Moon)",
//		    "Waning Gibbous (%1 days since Full Moon)",
//		    LunaCalc.diffDays(full_moon, now)));// fm.daysTo( now ) ) );
	    break;
	case 21:
	    comment.append("Last Quarter");
	    break;
	case 22:
	case 23:
	case 24:
	case 25:
	case 26:
	case 27:
	case 28:
	    comment.append("Waning Crescent");
	    if(m_counter == 28){
		comment.append(" ((Tomorrow is New Moon))");
	    }
	    break;
	default:
	    comment.append("The world exploded!");
	}
	comment.append("\nMoon age: " + m_counter + " days.");
	phases.put(now, comment.toString());
	return phases;
    }

    /**
     * @param args
     * 
     */
    public static void main(String[] args) {
	Map<Calendar, String> phases;
	String comment = null;
	Calendar now = Calendar.getInstance(TimeZone.getDefault());
	System.out.println(LunaCalc.padRight("Today is", LunaCalc.TEXT_WIDTH,
		'.') + String.format(LunaCalc.DATE_FORMAT, now.getTime()));

	phases = LunaCalc.calcStatus(now);
	Iterator<Map.Entry<Calendar, String>> it = phases.entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry<Calendar, String> pair = it.next();
	    String desc = LunaCalc.padRight(pair.getValue(),
		    LunaCalc.TEXT_WIDTH, '.');
	    if (desc.startsWith("Current")) {
		comment = desc;
	    } else {
		System.out.println(desc
			+ String.format(LunaCalc.DATE_FORMAT, pair.getKey()
				.getTime()));
	    }
	}
	System.out.println(comment);
	System.out.println();
    }

    /**
     * @param c1
     *            fist date
     * @param c2
     *            second date
     * @return
     */
    private static int diffDays(Calendar c1, Calendar c2) {
	return Math
		.abs((int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / LunaCalc.MILLSECS_PER_DAY));
    }

    public static String padRight(String str, int size, char padChar) {
	StringBuffer padded = new StringBuffer(str);
	while (padded.length() < size) {
	    padded.append(padChar);
	}
	return padded.toString();
    }
}
