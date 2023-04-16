package main.java.net.networkSimulator.model.devices;

public class Port {
    Device dev;
    int port_id; // port id wrt device
    boolean connected = false;
    Wire wire;
    boolean idle = true;

    public void send(String message, int rec_id, int sen_id) {
        this.wire.send(message, rec_id, sen_id, this);
    }

    public void read(String message, int rec_id, int sen_id) {
        System.out.println(
                "Port " + this.port_id + " of Device " + rec_id + " received the message and sent it to the device");
        this.dev.read(message, rec_id, sen_id, this.port_id);
    }
    // this method exchanges the wire reference between the two ports
    public void connect(Wire wire, Port port2) {
        this.wire = wire;
        this.connected = true;
        port2.wire = wire; // redundant
        port2.connected = true; // redundant
        if (wire.end1 == port2) {
            wire.end2.connected = false;
            wire.end2.idle = true;
            wire.end2 = this;
        } else if (wire.end2 == port2) {
            wire.end1.connected = false;
            wire.end1.idle = true;
            wire.end1 = this;
        } else {
            System.out.println("Port not found");
        }
    }

    public void disconnect() {
        if (this.connected == false) {
            System.out.println("Port is not connected");
            return;
        }
        wire.disconnect();
    }
}
