package main.java.net.networkSimulator.model.devices;

public class EndDevice implements Device {
    static int counter = 0; // for unique device id
    int dev_id;
    int portN;
    Port[] ports;

    EndDevice(int portN) {
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
        for (int i = 0; i < portN; i++) {
            if (ports[i].connected == true) {
                System.out.println("Device " + this.dev_id + " broadcasted message: " + message + " on port " + i);
                ports[i].send(message, rec_id, sen_id);
            }
        }

    }

    public void read(String message, int rec_id, int sen_id) {
        System.out.println("Device " + this.dev_id + " received message: " + message);
        if (this.dev_id == rec_id) {
            System.out.println("Message deciphered by destination device");
        } else if (this.dev_id == sen_id) {
            System.out.println("Loop detected in broadcast");
        } else {
            send(message, rec_id, sen_id);
        }
    }

    public static void main(String[] args) {
        EndDevice dev1 = new EndDevice(2);
        EndDevice dev2 = new EndDevice(2);
        EndDevice dev3 = new EndDevice(3);
        Bridge bridge1 = new Bridge();
        EndDevice dev4 = new EndDevice(3);
        EndDevice dev5 = new EndDevice(2);
        EndDevice dev6 = new EndDevice(2);
        Wire wire1 = new Wire(dev1.ports[0], dev2.ports[1]);
        Wire wire2 = new Wire(dev2.ports[0], dev3.ports[1]);
        Wire wire3 = new Wire(dev3.ports[0], dev1.ports[1]);
        Wire wireBridge1 = new Wire(dev3.ports[2], bridge1.ports[0]);
        Wire wireBridge2 = new Wire(dev4.ports[2], bridge1.ports[1]);
        Wire wire4 = new Wire(dev4.ports[0], dev5.ports[1]);
        Wire wire5 = new Wire(dev5.ports[0], dev6.ports[1]);
        Wire wire6 = new Wire(dev6.ports[0], dev4.ports[1]);

        dev1.send("Salamalaikum", 5, 1);

    }
}
