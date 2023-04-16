package main.java.net.networkSimulator.model.devices;

public class Wire {
    Port end1 = new Port();
    Port end2 = new Port();

    public Wire(Port end1, Port end2) {
        if (end1.connected == true || end2.connected == true) {
            System.out.println("Port is already connected. Cannot establish connection.");
            this.end1 = null;
            this.end2 = null;
            return;
        }
        this.end1 = end1;
        this.end2 = end2;
        end1.wire = this;
        end2.wire = this;
        end1.connected = true;
        end2.connected = true;
    }

    public void disconnect() {
        this.end1.connected = false;
        this.end2.connected = false;
        this.end1.idle = true;
        this.end2.idle = true;
        this.end1.wire = null;
        this.end2.wire = null;
        this.end1 = null;
        this.end2 = null;
    }

    public void send(String message, int rec_id, int sen_id, Port port) {
        if (port.idle == true) {
            if (port == end1) {
                if (end2.idle == true) {
                    end2.idle = false;
                    end1.idle = false;
                    end2.read(message, rec_id, sen_id);
                } else {
                    System.out.println("Destination port is busy");
                }
            } else if (port == end2) {
                if (end1.idle == true) {
                    end1.idle = false;
                    end2.idle = false;
                    end1.read(message, rec_id, sen_id);
                } else {
                    System.out.println("Destination port is busy");
                }
            } else {
                System.out.println("Port not found");
            }
        } else {
            System.out.println("Sender port is busy");
        }
    }
}
