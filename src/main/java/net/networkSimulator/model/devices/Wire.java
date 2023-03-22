package main.java.net.networkSimulator.model.devices;

public class Wire {
    Port end1 = new Port();
    Port end2 = new Port();

    public static void main(String[] args) {
        
    }
    Wire(Port end1, Port end2) {
        end1.connected = true;
        end2.connected = true;
        this.end1 = end1;
        this.end2 = end2;
    }
    public void disconnect() {
        end1.connected = false;
        end2.connected = false;
        end1.idle = true;
        end2.idle = true;

    }
}
