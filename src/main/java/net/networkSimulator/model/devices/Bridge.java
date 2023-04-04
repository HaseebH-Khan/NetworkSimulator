package main.java.net.networkSimulator.model.devices;

import java.util.HashMap;

public class Bridge implements Device {
    final int portN = 2;
    int bridge_id;
    static int bridge_count = 0;
    Port[] ports;
    static HashMap<Integer, Integer> data_table;// To maintain the dev_id to port_id mapping

    Bridge() {
        ports = new Port[this.portN];
        bridge_count++;
        this.bridge_id = bridge_count;
        for (int i = 0; i < this.portN; i++) {
            ports[i] = new Port();
            ports[i].port_id = i;
            ports[i].dev = this;
        }
        data_table = new HashMap<>();
    }

    public void read(String message, int rec_id, int sen_id) {

        Integer port_id_rec = data_table.get(rec_id);
        Integer port_id_sen = data_table.get(sen_id);

        if (port_id_rec != null) {
            if (port_id_rec == port_id_sen) {
                System.out.println("Receiver and sender are on the same side of bridge. Aborting transfer...");
                return;
            }
            // If the recipient is mapped to a port, send the message to that port
            System.out.println(
                    "Port " + port_id_sen + " of Bridge " + this.bridge_id
                            + " received the message and sent it to the device");
            ports[port_id_rec].send(message, rec_id, sen_id);
        } else {
            // If the recipient is not mapped, broadcast the message to all ports except the
            // sender's port
            learn(sen_id);
            learn(rec_id);
            System.out.println(
                    "Port " + port_id_sen + " of Bridge " + this.bridge_id
                            + " received the message and broadcasted it. Device id and respective port id were mapped.");
            for (int i = 0; i < this.portN; i++) {
                if (ports[i].port_id != sen_id) {
                    ports[i].send(message, rec_id, sen_id);
                }
            }
        }

    }

    public void send(String message, int rec_id, int sen_id) {

    }

    public void learn(int dev_id) { // * not sure about this one, I am unable to get which port of bridge should be
        // * mapped to the dev_id. Haseeb check it
        for (int i = 0; i < this.portN; i++) {
            if (ports[i].dev != null && ports[i].dev != this && data_table.get(dev_id) == null && !ports[i].idle) {
                data_table.put(dev_id, ports[i].port_id);
            }
        }
    }
}
