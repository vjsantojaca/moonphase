/* MoonPhase RMS config class
 * 
**/ 

package Moon;

import java.io.*;
import javax.microedition.rms.*;

/** Contains application preferences:
 * <ul>
 * <li> <i>bool</i> Time Zone Mode (manual/auto)></li>
 * <li> <i>int</i> Time Zone Offset (only used for manual mode)</li>
 * <li> <i>bool</i> Day Light Saving mode (only in manual mode)</li>
 * </ul>
 */
public class Prefs {

    public short timeZoneOffset;
    public boolean autoTimeZone;
    public boolean useDayLightSaving;

    public Prefs() {
        Load();
    }

    public void Save() {
        RecordStore recordStore;
        try {
            recordStore = RecordStore.openRecordStore("moonphase", true);
        }
        catch (RecordStoreException rsc) {
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(baos);
        try {
            outputStream.writeShort(timeZoneOffset);
            outputStream.writeBoolean(autoTimeZone);
            outputStream.writeBoolean(useDayLightSaving);
            byte[] bytes = baos.toByteArray();
            if (recordStore.getNumRecords() == 0) {
                recordStore.addRecord(bytes, 0, bytes.length);
            }
            else {
                recordStore.setRecord(1, bytes, 0, bytes.length);
            }
            outputStream.close();
            recordStore.closeRecordStore();
        }
        catch (Exception exception) {
        }
    }

   
    private void Load() {
        RecordStore recordStore;
        try {
	    recordStore = RecordStore.openRecordStore("moonphase", false);
            byte[] bytes = recordStore.getRecord(1);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            DataInputStream inputStream = new DataInputStream(bais);
            timeZoneOffset = inputStream.readShort();
            autoTimeZone = inputStream.readBoolean();
            useDayLightSaving = inputStream.readBoolean();
            inputStream.close();
            recordStore.closeRecordStore();
        }
        catch (Exception e) {
            timeZoneOffset = 0;
            autoTimeZone = true;
            useDayLightSaving = true;
            return;
        }
    }
}
