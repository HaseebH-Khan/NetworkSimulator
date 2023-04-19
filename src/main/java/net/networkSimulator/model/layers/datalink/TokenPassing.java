package main.java.net.networkSimulator.model.layers.datalink;
import main.java.net.networkSimulator.controller.*;
import main.java.net.networkSimulator.model.devices.*;

public class TokenPassing implements Runnable {
    static public int tokenPosition = 1;
    final static public int tokenTimeout = 10000;
    static public int endDevicesCount = 0;
    static boolean keepRunning = true;
    public void run() {
        while(keepRunning==true) {
            startTokenPassing();
        }
    }
    private void startTokenPassing() {
        Control control = new Control();
        EndDevice dev = control.getEndDevice(tokenPosition);
        try {
            dev.tokenAvailable = true;
            Thread.sleep(tokenTimeout);
            // notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(endDevicesCount == 0)
            return;
        dev.tokenAvailable = false;
        tokenPosition = ((tokenPosition + 1) % (endDevicesCount)) + 1;
        System.out.println("Token passed to " + tokenPosition);
    }
    public static void stop() {
        keepRunning = false;
    }
}
