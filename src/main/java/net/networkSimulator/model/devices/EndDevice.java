package main.java.net.networkSimulator.model.devices;

public class EndDevice {
    static int counter = 0;
    int dev_id; // ensure it is unique and not garbage ... not done yet
    int portN;
    Port[] ports;
    public static void main(String[] args) {
        EndDevice dev1 = new EndDevice(2);
        EndDevice dev2 = new EndDevice(2);
        Wire wire1 = new Wire(dev1.ports[0], dev2.ports[0]);
        dev1.send("Hello");
        Wire wire2 = new Wire(dev1.ports[1], dev2.ports[0]);
        dev2.send("Hell");
        wire2.end1 = dev1.ports[1]; // needed to do this because a wire 2 is a stranded wire ... fix needed ==> wire to be destroyed
        dev2.ports[1].connect(wire2, dev1.ports[1]);
        dev2.send("Hi");
    }
    EndDevice(int portN) {
        this.portN = portN;
        ports = new Port[portN];
        counter++;
        this.dev_id = counter;
        for(int i=0; i<portN; i++) {
            ports[i] = new Port();
            ports[i].port_id = i;
            ports[i].dev = this;
        }
    }
    public void send(String message) {
        System.out.println("Device " + this.dev_id + " broadcasted message: " + message);
        for(int i=0; i<portN; i++) {
            if(ports[i].connected == true) {
                ports[i].send(message);
            }
        }
        
    }
    public void read(String message) {
        System.out.println("Device " + this.dev_id + " received message: " + message);
    }
}
