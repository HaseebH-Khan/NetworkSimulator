package main.java.net.networkSimulator.model.devices;

import java.util.*;

public class Bridge implements Device {
    public Map<Integer, Integer> cache; // end device id, port id
    public int portN;
    public int bridge_id;
    static int bridge_count = 0;
    public Port[] ports;
    public Bridge(int portN) {
        cache = new HashMap<Integer, Integer>();
        this.portN = portN;
        ports = new Port[portN];
        bridge_count++;
        this.bridge_id = bridge_count;
        for (int i = 0; i < this.portN; i++) {
            ports[i] = new Port();
            ports[i].port_id = i;
            ports[i].dev = this;
        }
    }

    public void read(String message, int rec_id, int sen_id, int port_id, int seqNo) {
        System.out.println("Port " + port_id + " of Bridge " + this.bridge_id + " received the message");
        cache.put(sen_id, port_id);
        send(message, rec_id, sen_id, seqNo);
    }

    public void send(String message, int rec_id, int sen_id, int seqNo) {
        if(cache.containsKey(rec_id)) {
            int port_id = cache.get(rec_id);
            System.out.println("Port " + sen_id + " of Bridge " + this.bridge_id + " directed the message.");
            ports[cache.get(rec_id)].send(message, rec_id, sen_id, seqNo);
        } else {
            for(int i=0; i<this.portN; i++) {
                if(ports[i].connected == true) {
                    ports[i].send(message, rec_id, sen_id, seqNo);
                }
            }
        }
    }

    public void sendAck(int rec_id, int sen_id, int seqNo) {
        if(cache.containsKey(rec_id)) {
            int port_id = cache.get(rec_id);
            System.out.println("Port " + sen_id + " of Bridge " + this.bridge_id + " directed the ACK.");
            ports[cache.get(rec_id)].sendAck(rec_id, sen_id, seqNo);
        } else {
            for(int i=0; i<this.portN; i++) {
                if(ports[i].connected == true) {
                    ports[i].sendAck(rec_id, sen_id, seqNo);
                }
            }
        }
    }
    public int readAck(int rec_id, int sen_id, int port_id, int seqNo) {
        cache.put(sen_id, port_id);
        sendAck(rec_id, sen_id, seqNo);
        return seqNo;
    }
}
