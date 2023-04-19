package main.java.net.networkSimulator.model.layers.datalink;

import main.java.net.networkSimulator.model.devices.*;
import java.util.*;
import java.util.concurrent.*;

public class GoBackN {
    int windowSize;
    int Sn;
    int Sf;
    EndDevice sender;
    EndDevice receiver;
    List<String> packets;
    boolean[] ackList;


    public GoBackN(EndDevice sender, EndDevice receiver, int windowSize, int frames) {
        this.sender = sender;
        this.receiver = receiver;
        this.windowSize = windowSize;
        this.Sn = 0;
        this.Sf = 0;
        this.packets = new ArrayList<String>();
        this.ackList = new boolean[frames];
    }

    public void sendPacket(List<String> packets) {
        if(Sn < Sf + windowSize) {
            if(ackList[Sn] == true) {
                System.out.println("Packet already sent. Packet dropped.");
                return;
            }
            if(Sn >= packets.size()) {
                System.out.println("No more packets to send.");
                return;
            }
            int numThreads = windowSize;
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
            for (String packet : packets) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        sender.send(packet, receiver.dev_id, sender.dev_id, Sn);
                        Sn++;
                        if(Sn == Sf + windowSize) {
                            while(ackList[Sf] == true) {
                                Sf++;
                            }
                        }
                        else {
                            System.out.println("Window is full. Packet dropped.");
                        }
                    }
                });
            }
            executor.shutdown();
        }            
    }
}
