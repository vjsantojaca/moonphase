package jlooney;

import java.util.Calendar;
import java.util.TimeZone;


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
    private static final String DATE_FORMAT = "%1$ta, %1$td-%1$tb-%1$ty %1$tR";
    // Thursday, 22-July-2010 01:03:07 private static final String DATE_FORMAT = "%1$tA, %1$td-%1$tB-%1$tY %1$tT";

    // private static final String DATE_FORMAT =
    // "%1$ta, %1$td-%1$tm-%1$tb-%1$tY %1$tH:%1$tM:%1$tS";  
    
    public enum Phase {LAST_NEW, NEXT_FULL, NEXT_NEW, CUR_PHASE, MOON_AGE}


    /**
     * @param time
     * @return Map<String, Calendar> map of phase descriptions and dates
     */
    public static MoonConditions calcStatus(Calendar time) {
	int lun = 0;
	Calendar last_new = null;
	Calendar next_new = null;
	Calendar now = Calendar.getInstance();
	StringBuffer comment = new StringBuffer("Current phase: ");
	// use a treemap to get free key sorting
//	TreeMap<Calendar, String> phases = new TreeMap<Calendar, String>();
	MoonConditions conditions = new MoonConditions();
	//List<MoonEvent> events = new ArrayList<MoonEvent>();

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
	
	String desc;

	if (daysToNextNew > 0){
	    desc = "Next new moon will be in " + daysToNextNew + " days";
//	    phases.put(next_new, desc);
	    conditions.addEvent(Phase.NEXT_NEW, next_new, desc);
	}
	
	if (daysSinceLastNewMoon != 0){
	    desc = "Last new moon was " + daysSinceLastNewMoon + " days ago";
//	    phases.put(last_new, desc);
	    conditions.addEvent(Phase.LAST_NEW, last_new, desc);
	}
	// if(daysToFirstQuarter == 0)
	// phases.put(first_quarter, "First quarter is today");
	// else
	// phases.put(first_quarter, "First quarter will be in " +
	// daysToFirstQuarter + " days");

	if (daysToFullMoon == 0){
//	    phases.put(full_moon, "Full moon is today");
	    conditions.addEvent(Phase.NEXT_FULL, full_moon, "Full moon is today");
	}
	
		
	else {
	    desc = "Next full moon will be in " + daysToFullMoon + " days";
//	    phases.put(full_moon, desc);
	    conditions.addEvent(Phase.NEXT_FULL, full_moon, desc);
	    
	}
	// phases.put(third_quarter, "Third quarter will be in " +
	// daysToThirdQuarter + " days");


	if (LunaCalc.diffDays(now, full_moon) == 0) {
	    m_counter = 14;
	    comment.append(" is full moon");
	} else if ((m_counter <= 15) && (m_counter >= 13)) {
	    m_counter = 14 + LunaCalc.diffDays(now, full_moon);
	    comment.append(" around full moon");
	}

	int diff = LunaCalc.diffDays(first_quarter, now);
	if (diff == 0) {
	    m_counter = 7;
	} else if ((m_counter <= 8) && (m_counter >= 6)) {
	    m_counter = 7 + diff;
	    comment.append(" around first quarter");
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
	    comment.append(" around new moon");
	}
	if (LunaCalc.diffDays(third_quarter, now) == 0) {
	    m_counter = 21;
	} else if ((m_counter <= 22) && (m_counter >= 20)) {
	    m_counter = 21 + LunaCalc.diffDays(third_quarter, now);
	    comment.append(" third quarter");
	}
	if (!((m_counter >= 0) && (m_counter < 29))) {
	    throw new RuntimeException("invalid delta" + m_counter);
	}

	switch (m_counter) {
	case 0:
	    comment.append(" (New Moon)");
	    break;
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
	    comment.append(" (Waxing Crescent)");
            if (m_counter == 1){
        	comment.append(" (New Moon was yesterday)");
            }
	    break;
	case 7:
	    comment.append(" (First Quarter)");
	    break;
	case 8:
	case 9:
	case 10:
	case 11:
	case 12:
	case 13:
	    comment.append(" (Waxing Gibbous)");
	    if(m_counter == 13){
		comment.append(" (Tomorrow is Full Moon)");
	    }
//		    "Waxing Gibbous (%1 days to Full Moon)",
//		    LunaCalc.diffDays(full_moon, now)));// -fm.daysTo(
	    break;
	case 14:
	    comment.append(" Full Moon ");
	    break;
	case 15:
	case 16:
	case 17:
	case 18:
	case 19:
	case 20:
	    comment.append(" (Waning Gibbous)");
	    if (m_counter == 20){
		comment.append(" (Yesterday was Full Moon)");
	    }
	    break;
	case 21:
	    comment.append(" (Last Quarter)");
	    break;
	case 22:
	case 23:
	case 24:
	case 25:
	case 26:
	case 27:
	case 28:
	    comment.append(" (Waning Crescent");
	    if(m_counter == 28){
		comment.append(", Tomorrow is New Moon");
	    }
	    comment.append(")");
	    break;
	default:
	    comment.append("The world exploded!");
	}
	conditions.setMoonAge("Moon age: " + m_counter + " days.");
	
	conditions.setCurrentPhase(Phase.CUR_PHASE, now, comment.toString()); // (comment.toString());
	return conditions;
    }

    /**
     * @param args
     * 
     */
    public static void main(String[] args) {
	Calendar now = Calendar.getInstance(TimeZone.getDefault());
	MoonConditions moonData = LunaCalc.calcStatus(now);
	moonData.sort();
	String formattedTime;
        String paddedDesc;
	for(MoonEvent event : moonData.getMoonEvents()){
    	    formattedTime = String.format(LunaCalc.DATE_FORMAT, event.getDateTime());
    	    paddedDesc = pad(event.getDescription());
    	    System.out.println(paddedDesc + " (" + formattedTime + ")");
	}
	formattedTime = String.format(LunaCalc.DATE_FORMAT, moonData.getCurrentPhase().getDateTime());
	paddedDesc = pad(moonData.getCurrentPhase().getDescription());
	System.out.println(paddedDesc + " (" + formattedTime + ")");
	System.out.println(moonData.getMoonAge());
    }

    /**
     * @param c1 fist date
     * @param c2 second date
     * @return
     */
    private static int diffDays(Calendar c1, Calendar c2) {
	return Math.abs((int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / LunaCalc.MILLSECS_PER_DAY));
    }

    private static String pad(String str) {
	return padRight(str, TEXT_WIDTH, '.');
    }
    private static String padRight(String str, int size, char padChar) {
	StringBuffer padded = new StringBuffer(str);
	while (padded.length() < size) {
	    padded.append(padChar);
	}
	return padded.toString();
    }
}
