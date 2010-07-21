package jlooney;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import jlooney.LunaCalc.Phase;

public class MoonConditions {
    
    private List<MoonEvent> moonEvents;
    private MoonEvent currentPhase;
    private String moonAge;

    
    public String getMoonAge() {
        return moonAge;
    }

    public void setMoonAge(String moonAge) {
        this.moonAge = moonAge;
    }

    public MoonEvent getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Phase phaseName, Calendar dateTime, String description) {
        this.currentPhase = new MoonEvent(phaseName, dateTime, description);
    }

    public MoonConditions() {
	moonEvents = new ArrayList<MoonEvent>();
    }

    public void addEvent(Phase phaseName, Calendar dateTime, String description) {
	moonEvents.add(new MoonEvent(phaseName, dateTime, description));
    }

    public List<MoonEvent> getMoonEvents() {
        return moonEvents;
    }

    public void sort() {
	Collections.sort(moonEvents);
    }
}
