package main.java.net.networkSimulator.model.devices;
import java.util.*;

public class Switch implements Device {
    public Map<Integer, Integer> cache; // end device id, port id
    public int portN;
    public int switch_id;
    static int switch_count = 0;
    public Port[] ports;
    public Switch(int portN) {
        cache = new HashMap<Integer, Integer>();
        this.portN = portN;
        ports = new Port[portN];
        for(int i = 0; i < portN; i++) {
            ports[i] = new Port();
            ports[i].port_id = i;
            ports[i].dev = this;
        }
        switch_count++;
        this.switch_id = switch_count;
    }
    public void send(String message, int rec_id, int sen_id) {
        if(cache.containsKey(rec_id)) {
            int port_id = cache.get(rec_id);
            System.out.println("Switch " + this.switch_id + " directed message: " + message  + " on port " + port_id);
            ports[port_id].send(message, rec_id, sen_id);
        } else {
            for(int i=0; i<portN; i++) {
                if(ports[i].connected == true) {
                    System.out.println("Switch " + this.switch_id + " broadcasted message: " + message  + " on port " + i);
                    ports[i].send(message, rec_id, sen_id);
                }
            }
        }        
    }
    public void read(String message, int rec_id, int sen_id, int port_id) {
        System.out.println("Switch " + this.switch_id + " received message: " + message);
        cache.put(sen_id, port_id);
        send(message, rec_id, sen_id);
    }
}
