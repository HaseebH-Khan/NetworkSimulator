package main.java.net.networkSimulator.model.devices;

public class EndDevice implements Device {
    static int counter = 0; // for unique device id
    public int dev_id;
    public int portN;
    public int seqNo = 0;
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
    public static Object lock = new Object();
    public synchronized void waitForBoolean() throws InterruptedException {
        synchronized (lock) {
            while (this.tokenAvailable == false) {
                lock.wait(); // wait until notified
                
            }
        }
    }
    public void send(String message, int rec_id, int sen_id, int seqNo) {
        if(this.tokenAvailable == false) {
            try {
                System.out.println("Device " + this.dev_id + " is waiting for token");
                waitForBoolean();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < portN; i++) {
            if (ports[i].connected == true) {
                System.out.println("Device " + this.dev_id + " broadcasted message: " + message + " on port " + i);
                ports[i].send(message, rec_id, sen_id, seqNo);
            }
        }

    }

    public void read(String message, int rec_id, int sen_id, int port_id, int seqNo) {
        System.out.println("Device " + this.dev_id + " received message: " + message);
        if (this.dev_id == rec_id) {
            System.out.println("Message deciphered by destination device");
            if (seqNo == this.seqNo) {
                System.out.println("Message sent out of order");
            } else {
                System.out.println("Message is sent for the last message sent");
                seqNo ^= 1;
                this.seqNo = seqNo;
                sendAck(rec_id, sen_id, seqNo);
            }
        } else if (this.dev_id == sen_id) {
            System.out.println("Loop detected in broadcast");
        } else {
            send(message, rec_id, sen_id, seqNo);
        }
    }

    public void sendAck(int rec_id, int sen_id, int seqNo) {
        for (int i = 0; i < portN; i++) {
            if (ports[i].connected == true) {
                System.out.println("Device " + this.dev_id + " broadcasted ACK on port " + i);
                ports[i].sendAck(rec_id, sen_id, seqNo);
            }
        }
    }
    public int readAck(int rec_id, int sen_id, int port_id, int seqNo) {
        System.out.println("Device " + this.dev_id + " received ACK");
        if(this.dev_id == rec_id) {
            System.out.println("ACK deciphered by destination device");
            if(seqNo == this.seqNo) {
                System.out.println("ACK sent out of order");
                // RESEND PACKET HERE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            } else {
                System.out.println("ACK is sent for the last message sent");
                this.seqNo = seqNo;
                return 0;
            }
        } else if(this.dev_id == sen_id) {
            System.out.println("Loop detected in broadcast");
        } else {
            sendAck(rec_id, sen_id, seqNo);
        }
        return -1;
    }
}
