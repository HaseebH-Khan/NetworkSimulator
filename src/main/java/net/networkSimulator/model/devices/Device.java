package main.java.net.networkSimulator.model.devices;

public interface Device {
    public void read(String message, int rec_id, int sen_id, int port_id);
    public void send(String message, int rec_id, int sen_id);
}