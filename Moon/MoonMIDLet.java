/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * TODO:
 * 1.Fix AM/PM time display problem. DONE
 * 2.Make phase description the main action on soft key (assign to Next, not Help).
 * 3.
 * 4.Edit main and phase text.  DONE1
 * 5.Fix floats. ???
 * 6.Fix PERCENT display & make it time aware.???
 * 7.Fix TIMEZONE difference. Check for day-light saving time (DLST)
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
    private static final String[] explanations = {
        "New Moon\nThe first phase.\nSignifies birth and spring. Good time to start new projects, begin relationships and plant gardens. You might find yourself feeling impulsive.",
        "Waxing Crescent Moon\nThe second phase. \"Waxing\" is growing. Time of increased consciousness. Symbolizes the period when you become aware of your individuality. You realize you can struggle against the established pattern.",
        "First Quarter Moon\nThe third phase. During this period you feel friction in the air - this is a movement toward solution. Time to start a fire - it will keep you warm and cook your food. Awareness is still moving toward understanding. It is the time to implement ideas and look at possible solutions.",
        "Waxing Gibbous Moon\nThe fourth phase. Time for analysis and evaluation of our actions. We appraise our personal progress. This is more intellectual than the first quarter, which was more dynamic and intuitive.",
        "Full Moon\nThe fifth phase. The Moon is on the opposite side of Earth from the Sun. Symbolizes the climax, and the time when you get answers. You see a lot of extremes.  If you see people acting like lunatics, but that is just your projection. Try to find balance. Otherwise you will either be blinded by the Sun, or won't even see it. It is the time when people go wild, but also time for romance. This period is also associated with fertilization and ovulation.",
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
    private Command Next;
    private Command YearList;
    private Command backCommand;
    private Command backCommand1;
    private Command exitCommand1;
    private Command helpMain1;
    private Command DescribePhase;
    private Command explainSelectedPhase;
    private Command helpCommand2;
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
    }
    

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

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
        if (displayable == YearList1) {//GEN-BEGIN:|7-commandAction|1|70-preAction
            if (command == backCommand1) {//GEN-END:|7-commandAction|1|70-preAction
                // write pre-action user code here
                switchDisplayable(null, getMainForm1());//GEN-LINE:|7-commandAction|2|70-postAction
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
        } else if (displayable == mainForm1) {
            if (command == YearList) {//GEN-END:|7-commandAction|7|62-preAction
                // write pre-action user code here
                switchDisplayable(null, getYearList1());//GEN-LINE:|7-commandAction|8|62-postAction
                // write post-action user code here
            } else if (command == exitCommand) {//GEN-LINE:|7-commandAction|9|19-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|10|19-postAction
            // write post-action user code here
            } else if (command == explainSelectedPhase) {//GEN-LINE:|7-commandAction|11|101-preAction
                // write pre-action user code here
                switchDisplayable(null, getExplainPopup());//GEN-LINE:|7-commandAction|12|101-postAction
                explainPopup.setString(explanations[ _phaseIndex]);
                // write post-action user code here
            } else if (command == helpCommand) {//GEN-LINE:|7-commandAction|13|58-preAction
                // write pre-action user code here
                switchDisplayable(null, getHelpMain());//GEN-LINE:|7-commandAction|14|58-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|15|7-postCommandAction
        }//GEN-END:|7-commandAction|15|7-postCommandAction
    // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|16|
    //</editor-fold>//GEN-END:|7-commandAction|16|







    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, -1);//GEN-LINE:|18-getter|1|18-postInit
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
            mainForm1.addCommand(getExitCommand());
            mainForm1.addCommand(getHelpCommand());
            mainForm1.addCommand(getYearList());
            mainForm1.addCommand(getExplainSelectedPhase());
            mainForm1.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
        // write post-init user code here
            mainForm1.setItemStateListener(this);
        }//GEN-BEGIN:|14-getter|2|
        return mainForm1;
    }
    //</editor-fold>//GEN-END:|14-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Items ">//GEN-BEGIN:|17-itemCommandAction|0|17-preItemCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular item.
     * @param command the Command that was invoked
     * @param displayable the Item where the command was invoked
     */
    public void commandAction(Command command, Item item) {//GEN-END:|17-itemCommandAction|0|17-preItemCommandAction
        // write pre-action user code here
        if (item == phaseName_si) {//GEN-BEGIN:|17-itemCommandAction|1|106-preAction
            if (command == DescribePhase) {//GEN-END:|17-itemCommandAction|1|106-preAction
                // write pre-action user code here
//GEN-LINE:|17-itemCommandAction|2|106-postAction
                // write post-action user code here
            }//GEN-BEGIN:|17-itemCommandAction|3|47-preAction
        } else if (item == stringItemListYear) {
            if (command == backCommand) {//GEN-END:|17-itemCommandAction|3|47-preAction
                // write pre-action user code here
                switchDisplayable(null, getMainForm1());//GEN-LINE:|17-itemCommandAction|4|47-postAction
                // write post-action user code here
            }//GEN-BEGIN:|17-itemCommandAction|5|17-postItemCommandAction
        }//GEN-END:|17-itemCommandAction|5|17-postItemCommandAction
        // write post-action user code here

    }//GEN-BEGIN:|17-itemCommandAction|6|
    //</editor-fold>//GEN-END:|17-itemCommandAction|6|









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
            dateField = new DateField(null, DateField.DATE);//GEN-BEGIN:|28-getter|1|28-postInit
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
    


    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: Next ">//GEN-BEGIN:|31-getter|0|31-preInit
    /**
     * Returns an initiliazed instance of Next component.
     * @return the initialized component instance
     */
    public Command getNext() {
        if (Next == null) {//GEN-END:|31-getter|0|31-preInit
            // write pre-init user code here
            Next = new Command("All Phases", "List all phases", Command.ITEM, 0);//GEN-LINE:|31-getter|1|31-postInit
            // write post-init user code here
        }//GEN-BEGIN:|31-getter|2|
        return Next;
    }
    //</editor-fold>//GEN-END:|31-getter|2|


    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|38-getter|0|38-preInit
    /**
     * Returns an initiliazed instance of backCommand component.
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|38-getter|0|38-preInit
            // write pre-init user code here
            backCommand = new Command("Back", Command.BACK, 0);//GEN-LINE:|38-getter|1|38-postInit
            // write post-init user code here
        }//GEN-BEGIN:|38-getter|2|
        return backCommand;
    }
    //</editor-fold>//GEN-END:|38-getter|2|





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItemListYear ">//GEN-BEGIN:|46-getter|0|46-preInit
    /**
     * Returns an initiliazed instance of stringItemListYear component.
     * @return the initialized component instance
     */
    public StringItem getStringItemListYear() {
        if (stringItemListYear == null) {//GEN-END:|46-getter|0|46-preInit
		//        if (stringItemListYear == null) {
            // write pre-init user code here
            // SKIP_CHECK for null
            stringItemListYear = new StringItem(null, null, Item.PLAIN);//GEN-BEGIN:|46-getter|1|46-postInit
            stringItemListYear.addCommand(getBackCommand());
            stringItemListYear.setItemCommandListener(this);
            stringItemListYear.setLayout(ImageItem.LAYOUT_DEFAULT);
            stringItemListYear.setFont(getFont());//GEN-END:|46-getter|1|46-postInit
            // SKIP_CHECK for null
            //String phasesListYear = PhaseList.moonPhase(2008).getTime();
            //Calendar cal = getDateFieldValue();
            _yearList = PhaseList.moonPhaseListYear(_calCurDate.get(Calendar.YEAR));
            stringItemListYear.setText(_yearList);
            // write post-init user code here
        ///}
        }//GEN-BEGIN:|46-getter|2|
        return stringItemListYear;
    }
    //</editor-fold>//GEN-END:|46-getter|2|

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
            phaseName_si.addCommand(getDescribePhase());
            phaseName_si.setItemCommandListener(this);
            phaseName_si.setDefaultCommand(getDescribePhase());
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



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: YearList1 ">//GEN-BEGIN:|44-getter|0|44-preInit
    /**
     * Returns an initiliazed instance of YearList1 component.
     * @return the initialized component instance
     */
    public Form getYearList1() {
        if (YearList1 == null) {//GEN-END:|44-getter|0|44-preInit
            // write pre-init user code here
            // SKIP_CHECK for null #2
            YearList1 = new Form("", new Item[] { getStringItemListYear() });//GEN-BEGIN:|44-getter|1|44-postInit
            YearList1.addCommand(getExitCommand1());
            YearList1.addCommand(getBackCommand1());
            YearList1.addCommand(getHelpMain1());
            YearList1.setCommandListener(this);//GEN-END:|44-getter|1|44-postInit
            YearList1.setTitle( "" +_calCurDate.get(Calendar.YEAR));
            // write post-init user code here
            // SKIP_CHECK for null #2

        }//GEN-BEGIN:|44-getter|2|
        return YearList1;
    }
    //</editor-fold>//GEN-END:|44-getter|2|

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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand1 ">//GEN-BEGIN:|69-getter|0|69-preInit
    /**
     * Returns an initiliazed instance of backCommand1 component.
     * @return the initialized component instance
     */
    public Command getBackCommand1() {
        if (backCommand1 == null) {//GEN-END:|69-getter|0|69-preInit
            // write pre-init user code here
            backCommand1 = new Command("Back", Command.BACK, 0);//GEN-LINE:|69-getter|1|69-postInit
            // write post-init user code here
        }//GEN-BEGIN:|69-getter|2|
        return backCommand1;
    }
    //</editor-fold>//GEN-END:|69-getter|2|



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
            HelpYearList = new Alert("New/Full Moon List", "The dates for new/full moon phases for the selected year.\nThe midlet shows phases for any year in the range 1902 to 2033.\nN - new moon.\nF - full moon.\n", getImage(), null);//GEN-BEGIN:|86-getter|1|86-postInit
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
            imageItem.setImage(Image.createImage("/" + Integer.toString(imageIndex) + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: explainSelectedPhase ">//GEN-BEGIN:|100-getter|0|100-preInit
    /**
     * Returns an initiliazed instance of explainSelectedPhase component.
     * @return the initialized component instance
     */
    public Command getExplainSelectedPhase() {
        if (explainSelectedPhase == null) {//GEN-END:|100-getter|0|100-preInit
            // write pre-init user code here
            explainSelectedPhase = new Command("Explain", "Describe phase", Command.OK, -2);//GEN-LINE:|100-getter|1|100-postInit
            // write post-init user code here
        }//GEN-BEGIN:|100-getter|2|
        return explainSelectedPhase;
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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: DescribePhase ">//GEN-BEGIN:|103-getter|0|103-preInit
    /**
     * Returns an initiliazed instance of DescribePhase component.
     * @return the initialized component instance
     */
    public Command getDescribePhase() {
        if (DescribePhase == null) {//GEN-END:|103-getter|0|103-preInit
            // write pre-init user code here
            DescribePhase = new Command("Descrobe", "Describe Phase", Command.HELP, 0);//GEN-LINE:|103-getter|1|103-postInit
            // write post-init user code here
        }//GEN-BEGIN:|103-getter|2|
        return DescribePhase;
    }
    //</editor-fold>//GEN-END:|103-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: helpCommand2 ">//GEN-BEGIN:|105-getter|0|105-preInit
    /**
     * Returns an initiliazed instance of helpCommand2 component.
     * @return the initialized component instance
     */
    public Command getHelpCommand2() {
        if (helpCommand2 == null) {//GEN-END:|105-getter|0|105-preInit
            // write pre-init user code here
            helpCommand2 = new Command("Help", Command.HELP, 0);//GEN-LINE:|105-getter|1|105-postInit
            // write post-init user code here
        }//GEN-BEGIN:|105-getter|2|
        return helpCommand2;
    }
    //</editor-fold>//GEN-END:|105-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: YearList ">//GEN-BEGIN:|32-getter|0|32-preInit
    /**
     * Returns an initiliazed instance of YearList component.
     * @return the initialized component instance
     */
    public Command getYearList() {
        if (YearList == null) {//GEN-END:|32-getter|0|32-preInit
            // write pre-init user code here
            YearList = new Command("List", Command.SCREEN, 0);//GEN-LINE:|32-getter|1|32-postInit
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
            moonAge_si.setFont(getFont1());//GEN-END:|107-getter|1|107-postInit
            // write post-init user code here
            
            moonAge_si.setText(calcMoonAgeData());

        }//GEN-BEGIN:|107-getter|2|
        return moonAge_si;
    }
    //</editor-fold>//GEN-END:|107-getter|2|
    /**
     * Returns a display instance.
     * @return the display instance.
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
            initialize();
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
//            
//           Calendar cal = Calendar.getInstance();
//           //cal.setTimeZone(TimeZone.getDefault());
//           
//           
//           cal.setTime );
//           
//           MoonPhase _mp = new MoonPhase(cal);
//                      
//           String phaseName =  _mp.toString(cal);
           
            _calCurDate.setTime(dateField.getDate());
            
            //_mp = new MoonPhase(_calCurDate); change to _mp.update();
            //_mp = new MoonPhase(_calCurDate);
            _mp.updateCal(_calCurDate);
            _phase = _mp.getPhase();
            _phaseIndex = _mp.getPhaseIndex();
            
            phaseName_si.setText(calcMoonPhaseName());
            moonAge_si.setText(calcMoonAgeData());
            //stringItem1.setText("\nAll phases for " +_calCurDate.get(Calendar.YEAR));
            setPhaseImage(_phaseIndex);
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
    
}
