package main.java.net.networkSimulator.model.devices;

public class Hub implements Device {
    int portN;
    int hub_id;
    static int hub_count = 0;
    Port[] ports;
    Hub(int portN) {
        this.portN = portN;
        ports = new Port[portN];
        for(int i = 0; i < portN; i++) {
            ports[i] = new Port();
            ports[i].port_id = i;
            ports[i].dev = this;
        }
        hub_count++;
        this.hub_id = hub_count;
    }
    public void send(String message, int rec_id, int sen_id) {
        for(int i=0; i<portN; i++) {
            if(ports[i].connected == true) {
                System.out.println("Hub " + this.hub_id + " broadcasted message: " + message  + " on port " + i);
                ports[i].send(message, rec_id, sen_id);
            }
        }
        
    }
    public void read(String message, int rec_id, int sen_id) {
        System.out.println("Hub " + this.hub_id + " received message: " + message);
            send(message, rec_id, sen_id);
    }
}
