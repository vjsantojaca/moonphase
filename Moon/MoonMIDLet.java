/*
 * TODO: 1.Fix mismatched values for % and moon age
 * TODO: 2. Add next full/new moon data.
 * TODO: 3.Fix timezone DONE
 */
package Moon;

import java.io.IOException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Calendar;
//import java.util.TimeZone;
//import java.util.Date;
/**
 * @author xh
 */
public class MoonMIDLet extends MIDlet implements CommandListener, ItemCommandListener, ItemStateListener {

    private boolean midletPaused = false;
    private Calendar _calCurDate;
    private String _yearList;
    private MoonPhase _mp;
    private double _phase;
    private int _phaseIndex = 0;
    private Prefs _prefs;
    private static final String[] explanations = {
        "New Moon\nThe first phase.\nSignifies birth and spring. Good time to start new projects, begin relationships and plant gardens. You might find yourself feeling impulsive.",
        "Waxing Crescent Moon\nThe second phase. \"Waxing\" is growing. Time of increased consciousness. Symbolizes the period when you become aware of your individuality. You realize you can struggle against the established pattern.",
        "First Quarter Moon\nThe third phase.  The crisis time. During this period you feel friction in the air - this is a movement toward solution. If you act now, it might bring you dividents in the future. Awareness is still moving toward understanding. It is the time to implement ideas and look at possible solutions.",
        "Waxing Gibbous Moon\nThe fourth phase. Time for analysis and evaluation of our actions. We appraise our personal progress.",
        "Full Moon\nThe fifth phase. The Moon is on the opposite side of Earth from the Sun. Symbolizes the climax, and the time when you get answers. You see a lot of extremes. You may think that people are acting like lunatics, but that is just your projection. Try to find balance. Otherwise you will either be blinded by the Sun, or won't even see it. On Full Moon people go wild. It is also the time when they fall in love. This period is associated with fertilization and ovulation.",
        "Waning Gibbous Moon\nThe sixth phase. \"Waning\" means shrinking. When the Moon is waning, we see a little less of the Moon each day until it completely disappears.\nDuring this time we feel a strong desire to demonstrate to others what we have learned. Time to share the knowledge. The intellect is in charge.",
        "Last Quarter Moon\nThe eighth phase. Time to engage in conscious action. ",
        "Waning Crescent Moon\nThe ninth phase. Time to harvest to fruits of your Karma. You reap what you've sown. If you haven't learned the lessons, you can't move on. During this period you have no power to influence the outcome. You have already taken the steps that set up this outcome. It's a time to release the past."

    };
    
    private static final String moon_phase_name[] = { "New Moon",        // 0
                                              "Waxing crescent",    // 1 
                                              "First quarter",      // 2 
                                              "Waxing gibbous",     // 3
                                              "Full Moon",          // 4 
                                              "Waning gibbous",     // 5
                                              "Third quarter",      // 6
                                              "Waning crescent" };  // 7
    

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command helpCommand;
    private Command YearList;
    private Command exitCommand1;
    private Command helpMain1;
    private Command describePhase;
    private Command setCurrentTime;
    private Command options;
    private Command back;
    private Command savePrefs;
    private Command backCommand;
    private Form mainForm1;
    private DateField dateField;
    private StringItem phaseName_si;
    private ImageItem imageItem;
    private StringItem moonAge_si;
    private Form YearList1;
    private StringItem stringItemListYear;
    private Alert HelpMain;
    private Alert HelpYearList;
    private Alert explainPopup;
    private Form frmPrefs;
    private TextField txtUtcOffset;
    private ChoiceGroup dayLightSaving;
    private ChoiceGroup timeZoneMode;
    private Font font;
    private Font font1;
    private Image image;
    //</editor-fold>//GEN-END:|fields|0|
    /**
     * The MoonMIDLet constructor.
     */
    public MoonMIDLet() {
        _calCurDate = Calendar.getInstance();
        _mp = new MoonPhase(_calCurDate);
        _phase = _mp.getPhase();
        _phaseIndex = _mp.getPhaseIndex();
        _prefs = new Prefs();
    }
    

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|
 

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getMainForm1());//GEN-LINE:|3-startMIDlet|1|3-postAction
    // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
    // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
    // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
    // write post-switch user code here
     
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == YearList1) {//GEN-BEGIN:|7-commandAction|1|114-preAction
            if (command == back) {//GEN-END:|7-commandAction|1|114-preAction
                // write pre-action user code here
                switchDisplayable(null, getMainForm1());//GEN-LINE:|7-commandAction|2|114-postAction
                // write post-action user code here
            } else if (command == exitCommand1) {//GEN-LINE:|7-commandAction|3|68-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|4|68-postAction
                // write post-action user code here
            } else if (command == helpMain1) {//GEN-LINE:|7-commandAction|5|85-preAction
                // write pre-action user code here
                switchDisplayable(null, getHelpYearList());//GEN-LINE:|7-commandAction|6|85-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|7|62-preAction
        } else if (displayable == frmPrefs) {
            if (command == backCommand) {//GEN-END:|7-commandAction|7|62-preAction
                // write pre-action user code here
                switchDisplayable(null, getMainForm1());//GEN-LINE:|7-commandAction|8|62-postAction
                // write post-action user code here
            } else if (command == savePrefs) {//GEN-LINE:|7-commandAction|9|101-preAction
                // write pre-action user code here
                ////////update.PREFS
                boolean hasChanged = false;
                if (txtUtcOffset.size() ==0){
                    txtUtcOffset.setString("0");
                }
                if (_prefs.autoTimeZone != timeZoneMode.isSelected(0)){
                    _prefs.autoTimeZone = timeZoneMode.isSelected(0);
                    hasChanged = true;
                }
                if (_prefs.timeZoneOffset != Short.parseShort(txtUtcOffset.getString())){
                    _prefs.timeZoneOffset = Short.parseShort(txtUtcOffset.getString());
                    hasChanged = true;
                }
                if (_prefs.useDayLightSaving != dayLightSaving.isSelected(0)){
                    _prefs.useDayLightSaving = dayLightSaving.isSelected(0);
                    hasChanged = true;
                }
                if (hasChanged) {
                    _prefs.Save();
                }
                switchDisplayable(null, getMainForm1());//GEN-LINE:|7-commandAction|10|101-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|11|19-preAction
        } else if (displayable == mainForm1) {
            if (command == YearList) {//GEN-END:|7-commandAction|11|19-preAction
                // write pre-action user code here
                switchDisplayable(null, getYearList1());//GEN-LINE:|7-commandAction|12|19-postAction
                // write post-action user code here
            } else if (command == describePhase) {//GEN-LINE:|7-commandAction|13|58-preAction
                // write pre-action user code here
                switchDisplayable(null, getExplainPopup());//GEN-LINE:|7-commandAction|14|58-postAction
                explainPopup.setString(explanations[ _phaseIndex]);

                // write post-action user code here
            } else if (command == exitCommand) {//GEN-LINE:|7-commandAction|15|119-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|16|119-postAction
            // write post-action user code here
            } else if (command == helpCommand) {//GEN-LINE:|7-commandAction|17|112-preAction
                // write pre-action user code here
                switchDisplayable(null, getHelpMain());//GEN-LINE:|7-commandAction|18|112-postAction
                // write post-action user code here
            } else if (command == options) {//GEN-LINE:|7-commandAction|19|122-preAction
                // write pre-action user code here
                switchDisplayable(null, getFrmPrefs());//GEN-LINE:|7-commandAction|20|122-postAction
                // write post-action user code here
            } else if (command == setCurrentTime) {//GEN-LINE:|7-commandAction|21|125-preAction

                // write pre-action user code here
                _calCurDate = Calendar.getInstance();
                dateField.setDate(new java.util.Date(System.currentTimeMillis()));
                itemStateChanged(dateField);

//GEN-LINE:|7-commandAction|22|125-postAction
                // write post-action user code here
                
                
            }//GEN-BEGIN:|7-commandAction|23|7-postCommandAction
        }//GEN-END:|7-commandAction|23|7-postCommandAction
    // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|24|
    //</editor-fold>//GEN-END:|7-commandAction|24|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
        // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: mainForm1 ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initiliazed instance of mainForm1 component.
     * @return the initialized component instance
     */
    public Form getMainForm1() {
        if (mainForm1 == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            mainForm1 = new Form("Moon Phase", new Item[] { getImageItem(), getDateField(), getPhaseName_si(), getMoonAge_si() });//GEN-BEGIN:|14-getter|1|14-postInit
            mainForm1.addCommand(getYearList());
            mainForm1.addCommand(getDescribePhase());
            mainForm1.addCommand(getSetCurrentTime());
            mainForm1.addCommand(getHelpCommand());
            mainForm1.addCommand(getOptions());
            mainForm1.addCommand(getExitCommand());
            mainForm1.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
        // write post-init user code here
            mainForm1.setItemStateListener(this);
        }//GEN-BEGIN:|14-getter|2|
        return mainForm1;
    }
    //</editor-fold>//GEN-END:|14-getter|2|




        // write post-action user code here
    //}

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: helpCommand ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of helpCommand component.
     * @return the initialized component instance
     */
    public Command getHelpCommand() {
        if (helpCommand == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            helpCommand = new Command("Help", Command.HELP, 0);//GEN-LINE:|22-getter|1|22-postInit
            // write post-init user code here
        }//GEN-BEGIN:|22-getter|2|
        return helpCommand;
    }
    //</editor-fold>//GEN-END:|22-getter|2|





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: dateField ">//GEN-BEGIN:|28-getter|0|28-preInit
    /**
     * Returns an initiliazed instance of dateField component.
     * @return the initialized component instance
     */
    public DateField getDateField() {
        if (dateField == null) {//GEN-END:|28-getter|0|28-preInit
            // write pre-init user code here
            dateField = new DateField(null, DateField.DATE_TIME);//GEN-BEGIN:|28-getter|1|28-postInit
            dateField.setItemCommandListener(this);
            dateField.setDate(new java.util.Date(System.currentTimeMillis()));//GEN-END:|28-getter|1|28-postInit
            // write post-init user code here
//            dateField.setItemCommandListener(new ItemCommandListener() {

            //
//                public void commandAction(Command arg0, Item arg1) {
//                    throw new UnsupportedOperationException("Not supported yet.");
//                }
//            });

            
        }//GEN-BEGIN:|28-getter|2|
        return dateField;
    }
    //</editor-fold>//GEN-END:|28-getter|2|

    public Calendar getDateFieldValue(){
        
        Calendar cal = _calCurDate;
           
        cal.setTime(dateField.getDate() );
       
        return cal;
    }
    

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItemListYear ">                                   
    /**
     * Returns an initiliazed instance of stringItemListYear component.
     * @return the initialized component instance
     */
    public StringItem getStringItemListYear() {
		//        if (stringItemListYear == null) {
            // write pre-init user code here
            // SKIP_CHECK for null
            stringItemListYear = new StringItem(null, null, Item.PLAIN);//GEN-BEGIN:|46-getter|1|46-postInit
            stringItemListYear.setItemCommandListener(this);
            stringItemListYear.setLayout(ImageItem.LAYOUT_DEFAULT);
            stringItemListYear.setFont(getFont());//GEN-END:|46-getter|1|46-postInit
            // SKIP_CHECK for null
            //String phasesListYear = PhaseList.moonPhase(2008).getTime();
            //Calendar cal = getDateFieldValue();
            _yearList = PhaseList.moonPhaseListYear(_calCurDate.get(Calendar.YEAR), _prefs);
            stringItemListYear.setText(_yearList);
            // write post-init user code here
        ///}
        return stringItemListYear;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: font ">//GEN-BEGIN:|49-getter|0|49-preInit
    /**
     * Returns an initiliazed instance of font component.
     * @return the initialized component instance
     */
    public Font getFont() {
        if (font == null) {//GEN-END:|49-getter|0|49-preInit
            // write pre-init user code here
            font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);//GEN-LINE:|49-getter|1|49-postInit
            // write post-init user code here
        }//GEN-BEGIN:|49-getter|2|
        return font;
    }
    //</editor-fold>//GEN-END:|49-getter|2|


    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: phaseName_si ">//GEN-BEGIN:|55-getter|0|55-preInit
    /**
     * Returns an initiliazed instance of phaseName_si component.
     * @return the initialized component instance
     */
    public StringItem getPhaseName_si() {
        if (phaseName_si == null) {//GEN-END:|55-getter|0|55-preInit
            // write pre-init user code here
            phaseName_si = new StringItem("Current phase:", null, Item.PLAIN);//GEN-BEGIN:|55-getter|1|55-postInit
            phaseName_si.setItemCommandListener(this);
            phaseName_si.setLayout(ImageItem.LAYOUT_DEFAULT | ImageItem.LAYOUT_NEWLINE_BEFORE | ImageItem.LAYOUT_NEWLINE_AFTER);
            phaseName_si.setFont(getFont1());//GEN-END:|55-getter|1|55-postInit
            
            phaseName_si.setText(calcMoonPhaseName());
            // write post-init user code here
        }//GEN-BEGIN:|55-getter|2|
        return phaseName_si;
    }
    //</editor-fold>//GEN-END:|55-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: HelpMain ">//GEN-BEGIN:|59-getter|0|59-preInit
    /**
     * Returns an initiliazed instance of HelpMain component.
     * @return the initialized component instance
     */
    public Alert getHelpMain() {
        if (HelpMain == null) {//GEN-END:|59-getter|0|59-preInit
            // write pre-init user code here
            HelpMain = new Alert("About MoonPhase", "MoonPhase shows the current moon phase and the dates of all new/full moon phases for a given year.\nTo change the current date, use the calendar control on the main screen.\nThe percentage value represents the illuminated fraction of the Moon\'s disc as a value from -99.9 to +99.9. The value is negative when the Moon wanes, and positive otherwise.\nThe New Moon phase is around 0% and the Full Moon phase is around the +/-99%.\nMoonPhase uses portions of code from the John Walker\'s MoonTool application, the Stellafane Moon Phase Calculator by by Ken Slater, and the MoonCalculator by Angus McIntyre.\nThis program is free software.\nCreated by xhuman. Copywhite (c) 2008, dogpizza@gmail.com\n", getImage(), null);//GEN-BEGIN:|59-getter|1|59-postInit
            HelpMain.setTimeout(Alert.FOREVER);//GEN-END:|59-getter|1|59-postInit
            // write post-init user code here
        }//GEN-BEGIN:|59-getter|2|
        return HelpMain;
    }
    //</editor-fold>//GEN-END:|59-getter|2|



    /**
     * Returns an initiliazed instance of YearList1 component.
     * @return the initialized component instance
     */
    public Form getYearList1() {
            // write pre-init user code here
            // SKIP_CHECK for null #2
            YearList1 = new Form("", new Item[] { getStringItemListYear() });//GEN-BEGIN:|44-getter|1|44-postInit
            YearList1.addCommand(getExitCommand1());
            YearList1.addCommand(getHelpMain1());
            YearList1.addCommand(getBack());
            YearList1.setCommandListener(this);//GEN-END:|44-getter|1|44-postInit
            YearList1.setTitle( "" +_calCurDate.get(Calendar.YEAR));
            // write post-init user code here
            // SKIP_CHECK for null #2

        return YearList1;
    }


    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: font1 ">//GEN-BEGIN:|81-getter|0|81-preInit
    /**
     * Returns an initiliazed instance of font1 component.
     * @return the initialized component instance
     */
    public Font getFont1() {
        if (font1 == null) {//GEN-END:|81-getter|0|81-preInit
            // write pre-init user code here
            font1 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);//GEN-LINE:|81-getter|1|81-postInit
            // write post-init user code here
        }//GEN-BEGIN:|81-getter|2|
        return font1;
    }
    //</editor-fold>//GEN-END:|81-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: helpMain1 ">//GEN-BEGIN:|84-getter|0|84-preInit
    /**
     * Returns an initiliazed instance of helpMain1 component.
     * @return the initialized component instance
     */
    public Command getHelpMain1() {
        if (helpMain1 == null) {//GEN-END:|84-getter|0|84-preInit
            // write pre-init user code here
            helpMain1 = new Command("Help", Command.HELP, 0);//GEN-LINE:|84-getter|1|84-postInit
            // write post-init user code here
        }//GEN-BEGIN:|84-getter|2|
        return helpMain1;
    }
    //</editor-fold>//GEN-END:|84-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: HelpYearList ">//GEN-BEGIN:|86-getter|0|86-preInit
    /**
     * Returns an initiliazed instance of HelpYearList component.
     * @return the initialized component instance
     */
    public Alert getHelpYearList() {
        if (HelpYearList == null) {//GEN-END:|86-getter|0|86-preInit
            // write pre-init user code here
            HelpYearList = new Alert("New/Full Moon List", "The dates for new/full moon phases for the selected year.\nThe program can show moon phases for any year between 1902 to 2033.\nN - new moon.\nF - full moon.\n", getImage(), null);//GEN-BEGIN:|86-getter|1|86-postInit
            HelpYearList.setTimeout(Alert.FOREVER);//GEN-END:|86-getter|1|86-postInit
            // write post-init user code here
        }//GEN-BEGIN:|86-getter|2|
        return HelpYearList;
    }
    //</editor-fold>//GEN-END:|86-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: image ">//GEN-BEGIN:|90-getter|0|90-preInit
    /**
     * Returns an initiliazed instance of image component.
     * @return the initialized component instance
     */
    public Image getImage() {
        if (image == null) {//GEN-END:|90-getter|0|90-preInit
            // write pre-init user code here
            try {//GEN-BEGIN:|90-getter|1|90-@java.io.IOException
                image = Image.createImage("/help1.png");
            } catch (java.io.IOException e) {//GEN-END:|90-getter|1|90-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|90-getter|2|90-postInit
            // write post-init user code here
        }//GEN-BEGIN:|90-getter|3|
        return image;
    }
    //</editor-fold>//GEN-END:|90-getter|3|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: imageItem ">//GEN-BEGIN:|91-getter|0|91-preInit
    /**
     * Returns an initiliazed instance of imageItem component.
     * @return the initialized component instance
     */
    public ImageItem getImageItem() {
        if (imageItem == null) {//GEN-END:|91-getter|0|91-preInit
            // write pre-init user code here
            imageItem = new ImageItem(null, null, ImageItem.LAYOUT_CENTER | ImageItem.LAYOUT_NEWLINE_BEFORE | ImageItem.LAYOUT_NEWLINE_AFTER, "current moon phase", Item.PLAIN);//GEN-BEGIN:|91-getter|1|91-postInit
            imageItem.setItemCommandListener(this);//GEN-END:|91-getter|1|91-postInit
            setPhaseImage(_phaseIndex);
            // write post-init user code here
        }//GEN-BEGIN:|91-getter|2|
        return imageItem;
    }
    //</editor-fold>//GEN-END:|91-getter|2|

    public void setPhaseImage(int imageIndex){
        try {
            imageItem.setImage(Image.createImage("/res/" + Integer.toString(imageIndex) + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: describePhase ">//GEN-BEGIN:|100-getter|0|100-preInit
    /**
     * Returns an initiliazed instance of describePhase component.
     * @return the initialized component instance
     */
    public Command getDescribePhase() {
        if (describePhase == null) {//GEN-END:|100-getter|0|100-preInit
            // write pre-init user code here
            describePhase = new Command("Explain", Command.OK, -2);//GEN-LINE:|100-getter|1|100-postInit
            // write post-init user code here
        }//GEN-BEGIN:|100-getter|2|
        return describePhase;
    }
    //</editor-fold>//GEN-END:|100-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: explainPopup ">//GEN-BEGIN:|99-getter|0|99-preInit
    /**
     * Returns an initiliazed instance of explainPopup component.
     * @return the initialized component instance
     */
    public Alert getExplainPopup() {
        if (explainPopup == null) {//GEN-END:|99-getter|0|99-preInit
            // write pre-init user code here
            explainPopup = new Alert("About this phase");//GEN-BEGIN:|99-getter|1|99-postInit
            explainPopup.setTimeout(Alert.FOREVER);//GEN-END:|99-getter|1|99-postInit
            // write post-init user code here
            
        }//GEN-BEGIN:|99-getter|2|
        return explainPopup;
    }
    //</editor-fold>//GEN-END:|99-getter|2|


    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: YearList ">//GEN-BEGIN:|32-getter|0|32-preInit
    /**
     * Returns an initiliazed instance of YearList component.
     * @return the initialized component instance
     */
    public Command getYearList() {
        if (YearList == null) {//GEN-END:|32-getter|0|32-preInit
            // write pre-init user code here
            YearList = new Command("Show All", Command.SCREEN, 0);//GEN-LINE:|32-getter|1|32-postInit
            // write post-init user code here
        }//GEN-BEGIN:|32-getter|2|
        return YearList;
    }
    //</editor-fold>//GEN-END:|32-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: moonAge_si ">//GEN-BEGIN:|107-getter|0|107-preInit
    /**
     * Returns an initiliazed instance of moonAge_si component.
     * @return the initialized component instance
     */
    public StringItem getMoonAge_si() {
        if (moonAge_si == null) {//GEN-END:|107-getter|0|107-preInit
            // write pre-init user code here
            moonAge_si = new StringItem("Moon Age:", null);//GEN-BEGIN:|107-getter|1|107-postInit
            moonAge_si.setItemCommandListener(this);
            moonAge_si.setFont(getFont1());//GEN-END:|107-getter|1|107-postInit
            // write post-init user code here
            
            moonAge_si.setText(calcMoonAgeData());

        }//GEN-BEGIN:|107-getter|2|
        return moonAge_si;
    }
    //</editor-fold>//GEN-END:|107-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: setCurrentTime ">//GEN-BEGIN:|111-getter|0|111-preInit
    /**
     * Returns an initiliazed instance of setCurrentTime component.
     * @return the initialized component instance
     */
    public Command getSetCurrentTime() {
        if (setCurrentTime == null) {//GEN-END:|111-getter|0|111-preInit
            // write pre-init user code here
            setCurrentTime = new Command("Now", Command.ITEM, 0);//GEN-LINE:|111-getter|1|111-postInit
            // write post-init user code here
        }//GEN-BEGIN:|111-getter|2|
        return setCurrentTime;
    }
    //</editor-fold>//GEN-END:|111-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|67-getter|0|67-preInit
    /**
     * Returns an initiliazed instance of exitCommand1 component.
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {//GEN-END:|67-getter|0|67-preInit
            // write pre-init user code here
            exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|67-getter|1|67-postInit
            // write post-init user code here
        }//GEN-BEGIN:|67-getter|2|
        return exitCommand1;
    }
    //</editor-fold>//GEN-END:|67-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: back ">//GEN-BEGIN:|113-getter|0|113-preInit
    /**
     * Returns an initiliazed instance of back component.
     * @return the initialized component instance
     */
    public Command getBack() {
        if (back == null) {//GEN-END:|113-getter|0|113-preInit
            // write pre-init user code here
            back = new Command("Back", Command.BACK, 0);//GEN-LINE:|113-getter|1|113-postInit
            // write post-init user code here
        }//GEN-BEGIN:|113-getter|2|
        return back;
    }
    //</editor-fold>//GEN-END:|113-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmPrefs ">//GEN-BEGIN:|116-getter|0|116-preInit
    /**
     * Returns an initiliazed instance of frmPrefs component.
     * @return the initialized component instance
     */
    public Form getFrmPrefs() {
        if (frmPrefs == null) {//GEN-END:|116-getter|0|116-preInit
            // write pre-init user code here
            //frmPrefs = new Form("Settings", new Item[] { getTimeZoneMode(), getTxtUtcOffset(), getDayLightSaving() });
            frmPrefs = new Form("Settings");
            frmPrefs.append(getTimeZoneMode());
//            frmPrefs.append(getTxtUtcOffset());
//            frmPrefs.append(getDayLightSaving());
            
//GEN-BEGIN:|116-getter|1|116-postInit
            frmPrefs.addCommand(getBackCommand());
            frmPrefs.addCommand(getSavePrefs());
            frmPrefs.setCommandListener(this);//GEN-END:|116-getter|1|116-postInit
                        
             // write post-init user code here
            frmPrefs.setItemStateListener(this);
            
            //SKIP_ME:::: COMMENT LINE BELOW!!!!
            updateSettingsDisplay();
        }//GEN-BEGIN:|116-getter|2|
        return frmPrefs;
    }
    //</editor-fold>//GEN-END:|116-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: timeZoneMode ">//GEN-BEGIN:|117-getter|0|117-preInit
    /**
     * Returns an initiliazed instance of timeZoneMode component.
     * @return the initialized component instance
     */
    public ChoiceGroup getTimeZoneMode() {
        if (timeZoneMode == null) {//GEN-END:|117-getter|0|117-preInit
            // write pre-init user code here
            timeZoneMode = new ChoiceGroup("Time Zone Mode", Choice.EXCLUSIVE);//GEN-BEGIN:|117-getter|1|117-postInit
            timeZoneMode.append("Auto", null);
            timeZoneMode.append("Manual", null);
            timeZoneMode.setFitPolicy(Choice.TEXT_WRAP_DEFAULT);
            timeZoneMode.setSelectedFlags(new boolean[] { _prefs.autoTimeZone, !_prefs.autoTimeZone });
            timeZoneMode.setFont(0, null);
            timeZoneMode.setFont(1, null);//GEN-END:|117-getter|1|117-postInit
            // write post-init user code here
            timeZoneMode.setItemCommandListener(this);
//            _prefs.autoTimeZone;
//            _prefs.timeZoneOffset;
//            _prefs.useDayLightSaving;
        }//GEN-BEGIN:|117-getter|2|
        return timeZoneMode;
    }
    //</editor-fold>//GEN-END:|117-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: options ">//GEN-BEGIN:|118-getter|0|118-preInit
    /**
     * Returns an initiliazed instance of options component.
     * @return the initialized component instance
     */
    public Command getOptions() {
        if (options == null) {//GEN-END:|118-getter|0|118-preInit
            // write pre-init user code here
            options = new Command("Settings", Command.ITEM, 0);//GEN-LINE:|118-getter|1|118-postInit
            // write post-init user code here
        }//GEN-BEGIN:|118-getter|2|
        return options;
    }
    //</editor-fold>//GEN-END:|118-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|121-getter|0|121-preInit
    /**
     * Returns an initiliazed instance of backCommand component.
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|121-getter|0|121-preInit
            // write pre-init user code here
            backCommand = new Command("Cancel", Command.BACK, 0);//GEN-LINE:|121-getter|1|121-postInit
            // write post-init user code here
        }//GEN-BEGIN:|121-getter|2|
        return backCommand;
    }
    //</editor-fold>//GEN-END:|121-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: savePrefs ">//GEN-BEGIN:|124-getter|0|124-preInit
    /**
     * Returns an initiliazed instance of savePrefs component.
     * @return the initialized component instance
     */
    public Command getSavePrefs() {
        if (savePrefs == null) {//GEN-END:|124-getter|0|124-preInit
            // write pre-init user code here
            savePrefs = new Command("Save", Command.OK, 0);//GEN-LINE:|124-getter|1|124-postInit
            // write post-init user code here
        }//GEN-BEGIN:|124-getter|2|
        return savePrefs;
    }
    //</editor-fold>//GEN-END:|124-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtUtcOffset ">//GEN-BEGIN:|133-getter|0|133-preInit
    /**
     * Returns an initiliazed instance of txtUtcOffset component.
     * @return the initialized component instance
     */
    public TextField getTxtUtcOffset() {
        if (txtUtcOffset == null) {//GEN-END:|133-getter|0|133-preInit
            // write pre-init user code here
            txtUtcOffset = new TextField("UTC Offset", ""+_prefs.timeZoneOffset, 32, TextField.NUMERIC);//GEN-LINE:|133-getter|1|133-postInit
            // write post-init user code here
            txtUtcOffset.setMaxSize(3);

            
        }//GEN-BEGIN:|133-getter|2|
        return txtUtcOffset;
    }
    //</editor-fold>//GEN-END:|133-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: dayLightSaving ">//GEN-BEGIN:|134-getter|0|134-preInit
    /**
     * Returns an initiliazed instance of dayLightSaving component.
     * @return the initialized component instance
     */
    public ChoiceGroup getDayLightSaving() {
        if (dayLightSaving == null) {//GEN-END:|134-getter|0|134-preInit
            // write pre-init user code here
            dayLightSaving = new ChoiceGroup("Day Light Saving", Choice.MULTIPLE);//GEN-BEGIN:|134-getter|1|134-postInit
            dayLightSaving.append("Enabled", null);
            dayLightSaving.setSelectedFlags(new boolean[] { _prefs.useDayLightSaving });
            dayLightSaving.setFont(0, null);//GEN-END:|134-getter|1|134-postInit
//            choiceGroup.setSelectedFlags(_prefs.xxxx);
            // write post-init user code here
        }//GEN-BEGIN:|134-getter|2|
        return dayLightSaving;
    }
    //</editor-fold>//GEN-END:|134-getter|2|
//GEN-LINE:|44-getter|1|
    /*//GEN-LINE:|44-getter|0|44-preInit
     * Returns a display instance.//GEN-LINE:|44-getter|2|
     */
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
           // initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }
    public void itemStateChanged(Item item)
    {
        if (item == dateField)
        {
            _calCurDate.setTime(dateField.getDate());
            _mp.updateCal(_calCurDate);
            _phase = _mp.getPhase();
            _phaseIndex = _mp.getPhaseIndex();
            phaseName_si.setText(calcMoonPhaseName());
            moonAge_si.setText(calcMoonAgeData());
            setPhaseImage(_phaseIndex);
        }
        if (item==timeZoneMode){
            _prefs.autoTimeZone = timeZoneMode.isSelected(0);
            updateSettingsDisplay();
        }
    }
    
    public String calcMoonPhaseName(){
        
        String sPhase = Double.toString(_phase);
        
        return (moon_phase_name[_phaseIndex] + "\n" + 
                sPhase.substring(0, sPhase.indexOf('.') +3)+"%");
    }
    public String calcMoonAgeData(){
        return _mp.getMoonAgeAsDays();
    }

    public void commandAction(Command arg0, Item arg1) {
    }

    private void updateSettingsDisplay() {
        if (_prefs.autoTimeZone){
//            System.out.println("DBG: frmPrefs.size()=" + frmPrefs.size());
            int i = frmPrefs.size();
            while( i-- >1){
                frmPrefs.delete(i); //delete item txtUtcOffset
                                     //txtUtcOffset.setConstraints(TextField.UNEDITABLE);
                                     //delete item dayLightSaving
                                     //dayLightSaving.
            }
            
        } else {
            frmPrefs.append(getTxtUtcOffset());
            frmPrefs.append(getDayLightSaving());
        }
    }
    
}
