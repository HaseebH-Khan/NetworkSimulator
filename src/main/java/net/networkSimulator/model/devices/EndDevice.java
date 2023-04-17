package main.java.net.networkSimulator.model.devices;

public class EndDevice implements Device {
    static int counter = 0; // for unique device id
    public int dev_id;
    public int portN;
    public boolean tokenAvailable = false;
    public Port[] ports;

    public EndDevice(int portN) {
        this.portN = portN;
        ports = new Port[portN];
        counter++;
        this.dev_id = counter;
        for (int i = 0; i < portN; i++) {
            ports[i] = new Port();
            ports[i].port_id = i;
            ports[i].dev = this;
        }
    }

    public void send(String message, int rec_id, int sen_id) {
        if(tokenAvailable==false) {
            System.out.println("Token not available");
            return;
        }
        for (int i = 0; i < portN; i++) {
            if (ports[i].connected == true) {
                System.out.println("Device " + this.dev_id + " broadcasted message: " + message + " on port " + i);
                ports[i].send(message, rec_id, sen_id);
            }
        }

    }

    public void read(String message, int rec_id, int sen_id, int port_id) {
        System.out.println("Device " + this.dev_id + " received message: " + message);
        if (this.dev_id == rec_id) {
            System.out.println("Message deciphered by destination device");
        } else if (this.dev_id == sen_id) {
            System.out.println("Loop detected in broadcast");
        } else {
            send(message, rec_id, sen_id);
        }
    }
}
