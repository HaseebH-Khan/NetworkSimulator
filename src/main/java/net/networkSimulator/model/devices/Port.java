package main.java.net.networkSimulator.model.devices;

public class Port {
    int port_id; // loc
    boolean connected = false;
    Port port; 
    // port to port filhal
    // public void send(String message) {
        
    // }
    // public void read() {

    // }
    public void connect(Port port2) {
        Port port2 = new Port();
        // this.port = port2;
        this.connected = true;
        port2.port = this;
        port2.connected = true;
    }
    // public void disconnect() {
    //     // has to disconnect and dissolve wire
    // }
}
