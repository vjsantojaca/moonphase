package jlooney;

import java.util.Calendar;

import jlooney.LunaCalc.Phase;

public class MoonEvent implements Comparable<MoonEvent> {
    private Calendar dateTime;
    private String description;
    private Phase phaseName;
    
public MoonEvent(Phase phaseName, Calendar dateTime, String description) {
	super();
	this.dateTime = dateTime;
	this.description = description;
	this.phaseName = phaseName;
    }
    
    public Calendar getDateTime() {
        return dateTime;
    }
    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Phase getPhaseName() {
        return phaseName;
    }
    public void setPhaseName(Phase phaseName) {
        this.phaseName = phaseName;
    }

    @Override
    public int compareTo(MoonEvent o) {
	if (this.getDateTime() == null || o.getDateTime() == null){
	    throw new RuntimeException("ERROR: date not set for event!");
	}
	return this.getDateTime().compareTo(o.getDateTime());
    }
    
}
