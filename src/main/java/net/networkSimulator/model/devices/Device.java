package main.java.net.networkSimulator.model.devices;

public interface Device {
    public void read(String message, int rec_id, int sen_id, int port_id, int seqNo);
    public void send(String message, int rec_id, int sen_id, int seqNo);
    public void sendAck(int rec_id, int sen_id, int seqNo);
    public int readAck(int rec_id, int sen_id, int port_id, int seqNo);
}