package com.example.serialtesting;
import static android.content.Context.USB_SERVICE;
import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbAccessory;
import com.hoho.android.usbserial.driver.*;

import java.io.IOException;
import java.util.List;
import android.app


/**
 * This is a new device, primarly the DB9/CANBUS port.
 */
public class db9SerialPort
{
    // manager acts as an accessor of usbs, allowing
    // an easy way to manage the state of all usbs
    // connected to the device

    public void openDevices() throws IOException {
        // Find all available drivers from attached devices.
        UsbManager manager = null; //(UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (((List<?>) availableDrivers).isEmpty()) {
            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
            return;
        }

        UsbSerialPort port = driver.getPorts().get(0); // Most devices have just one port (port 0)
        port.open(connection);
        port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
    }



    /**
     * printAccesories() prints all current accessories
     * current connected to the tablet.
     * @param //takes manager of usbs
     * @return true if accessories are connected,
     * null if nothing is connected
     */
   public boolean printAccesorys(UsbManager manager)
   {
       //gets list of usb connections
       UsbAccessory[] accesoryList = manager.getAccessoryList();
       //counter for counting accesories connected
       int counter = 0;

       for(UsbAccessory p : accesoryList)
       {
           counter++;
           p.getDescription(); //gets description of usbAccessory
       }

       //if counter == 0, no devices are connected;
       if(counter == 0)
       {
           return false;
       }
       return true;

   }

}
