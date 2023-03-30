package main.java.net.networkSimulator.model.devices;

public class Wire {
    Port end1 = new Port();
    Port end2 = new Port();

    Wire(Port end1, Port end2) {
        if(end1.connected == true || end2.connected == true) {
            System.out.println("Port is already connected. Cannot establish connection.");
            // destroy wire
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
        end1.connected = false;
        end2.connected = false;
        end1.idle = true;
        end2.idle = true;
        // destroy wire

    }
    public void send(String message, Port port) {
        if(port.idle == true) {
            if(port == end1) {
                if(end2.idle == true) {
                    end2.idle = false;
                    end1.idle = false;
                    end2.read(message);
                }
                else {
                    System.out.println("Destination port is busy");
                }
            }
            else if(port == end2) {
                if(end1.idle == true) {
                    end1.idle = false;
                    end2.idle = false;
                    end1.read(message);
                }
                else {
                    System.out.println("Destination port is busy");
                }
            }
            else {
                System.out.println("Port not found");
            }
        }
        else {
            System.out.println("Sender port is busy");
        }
    }
}
