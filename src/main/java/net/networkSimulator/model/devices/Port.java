package main.java.net.networkSimulator.model.devices;

public class Port {
    EndDevice dev;
    int port_id; // port id wrt device
    boolean connected = false;
    Wire wire;
    boolean idle = true;
    public void send(String message) {
        this.wire.send(message, this);
    }
    public void read(String message) {
        System.out.println("Port " + this.port_id + " received the message and sent it to the device");
        this.dev.read(message);
    }
    // this method exchanges the wire reference between the two ports
    public void connect(Wire wire, Port port2) {
        this.wire = wire;
        this.connected = true;
        port2.wire = wire; // redundant
        port2.connected = true; // redundant
        if(wire.end1 == port2) {
            wire.end2.connected = false;
            wire.end2.idle = true;
            wire.end2 = this;
        }
        else if(wire.end2 == port2) {
            wire.end1.connected = false;
            wire.end1.idle = true;
            wire.end1 = this;
        }
        else {
            System.out.println("Port not found");
        }
    }
    public void disconnect() {
        connected = false;
        wire.disconnect();
        // destoy wire reference
    }
}
