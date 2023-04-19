package main.java.net.networkSimulator.model.devices;

public class Hub implements Device {
    public int portN;
    public int hub_id;
    static int hub_count = 0;
    public Port[] ports;
    public Hub(int portN) {
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
    public void send(String message, int rec_id, int sen_id, int seqNo) {
        for(int i=0; i<portN; i++) {
            if(ports[i].connected == true) {
                System.out.println("Hub " + this.hub_id + " broadcasted message: " + message  + " on port " + i);
                ports[i].send(message, rec_id, sen_id, seqNo);
            }
        }
        
    }
    public void read(String message, int rec_id, int sen_id, int port_id, int seqNo) {
        System.out.println("Hub " + this.hub_id + " received message: " + message);
            send(message, rec_id, sen_id, seqNo);
    }
    public void sendAck(int rec_id, int sen_id, int seqNo) {
         for(int i=0; i<portN; i++) {
            if(ports[i].connected == true) {
                System.out.println("Hub " + this.hub_id + " broadcasted ACK: " + seqNo  + " on port " + i);
                ports[i].sendAck(rec_id, sen_id, seqNo);
            }
        }
    }
    public int readAck(int rec_id, int sen_id, int port_id, int seqNo) {
        sendAck(rec_id, sen_id, seqNo);
        return seqNo;
    }
}
