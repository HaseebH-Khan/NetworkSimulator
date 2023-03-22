package main.java.net.networkSimulator.model.devices;

public class EndDevice {
    int end_id;
    int portN;
    Port ports[] = new Port[portN];
    public static void main(String[] args) {

    }
    EndDevice(int portN) {
        for(int i=0; i<portN; i++) {
            ports[i].port_id = i;
        }
    }
    public void send(String message) {
        for(int i=0; i<portN; i++) {
            if(ports[i].connected == true) {
                ports[i].msg = message;
                ports[i].send();
            }
        }
        
    }
    public void read() {

    }
}
