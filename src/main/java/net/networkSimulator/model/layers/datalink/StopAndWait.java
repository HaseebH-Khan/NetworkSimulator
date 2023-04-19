package main.java.net.networkSimulator.model.layers.datalink;

import main.java.net.networkSimulator.model.devices.*;
import java.util.*;

public class StopAndWait {
    // private static final int TIMEOUT = 5000; 
    EndDevice sender;
    EndDevice receiver;
    // List<String> packets;

    public StopAndWait(EndDevice sender, EndDevice receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    Object lock = EndDevice.lock;
    public void sendPacket(List<String> packets) {
        int seqNum = 1;
        for(String packet: packets) {
            // seqNum ^= 1; // toggle seqNum
            // sender.seqNo = seqNum;
            // executeWithTimeout(() -> {
            //     sender.send(packet, receiver.dev_id, sender.dev_id, seqNum);
            // }, TIMEOUT);
            synchronized (lock) {
                sender.tokenAvailable = true;
                lock.notifyAll();
            }
            
            sender.send(packet, receiver.dev_id, sender.dev_id, seqNum);
        }
        

    }

    // private static void executeWithTimeout(Runnable task, int timeout) {
    //     Thread thread = new Thread(task);
    //     thread.start();
    //     try {
    //         thread.join(timeout);
    //         if (thread.isAlive()) {
    //             thread.interrupt(); // Terminate the thread if it takes too long
    //             System.out.println("Function timed out, resending...");
    //             executeWithTimeout(task, timeout); // Resend the function
    //         }
    //     } catch (InterruptedException e) {
    //         // Thread was interrupted, do nothing
    //     }
    // }
}
