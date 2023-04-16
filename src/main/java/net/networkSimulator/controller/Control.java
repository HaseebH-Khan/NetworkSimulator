package main.java.net.networkSimulator.controller;
import java.util.Scanner;
import java.util.HashMap;

import main.java.net.networkSimulator.model.devices.*;

public class Control {
    static public HashMap<Integer, EndDevice> endDeviceMapping = new HashMap<Integer, EndDevice>();
    static public HashMap<Integer, Hub> hubMapping = new HashMap<Integer, Hub>();
    static public HashMap<Integer, Switch> switchMapping = new HashMap<Integer, Switch>();
    static public HashMap<Integer, Bridge> bridgeMapping = new HashMap<Integer, Bridge>();

    public EndDevice getEndDevice(int dev_id) {
        return endDeviceMapping.get(dev_id);
    }
    public Hub getHub(int hub_id) {
        return hubMapping.get(hub_id);
    }
    public Switch getSwitch(int switch_id) {
        return switchMapping.get(switch_id);
    }
    public Bridge getBridge(int bridge_id) {
        return bridgeMapping.get(bridge_id);
    }
    public Port getPortEndDevice(EndDevice endDevice) {
        System.out.println("Enter the port number of End Device " + endDevice.dev_id + ": ");
        Scanner sc = new Scanner(System.in);
        int portid = sc.nextInt();
        if(endDevice.portN < portid) throw new IllegalArgumentException("Port number is out of range");
        Port port = endDevice.ports[portid];
        return port;
    }
    public Port getPortHub(Hub hub) {
        System.out.println("Enter the port number of Hub " + hub.hub_id + ": ");
        Scanner sc = new Scanner(System.in);
        int portid = sc.nextInt();
        if(hub.portN < portid) throw new IllegalArgumentException("Port number is out of range");
        Port port = hub.ports[portid];
        return port;
    }
    public Port getPortSwitch(Switch sw) {
        System.out.println("Enter the port number of Switch " + sw.switch_id + ": ");
        Scanner sc = new Scanner(System.in);
        int portid = sc.nextInt();
        if(sw.portN < portid) throw new IllegalArgumentException("Port number is out of range");
        Port port = sw.ports[portid];
        return port;
    }
    public Port getPortBridge(Bridge bridge) {
        System.out.println("Enter the port number of Bridge " + bridge.bridge_id + ": ");
        Scanner sc = new Scanner(System.in);
        int portid = sc.nextInt();
        if(bridge.portN < portid) throw new IllegalArgumentException("Port number is out of range");
        Port port = bridge.ports[portid];
        return port;
    }


    public void createEndDevice(int portN) {
        if(portN < 1) throw new IllegalArgumentException("End Device must have at least 1 port");
        EndDevice endDevice = new EndDevice(portN);
        System.out.println("End Device " + endDevice.dev_id + " created with " + portN + " ports");
        endDeviceMapping.put(endDevice.dev_id, endDevice);
    }
    public void createHub(int portN) {
        if(portN < 2) throw new IllegalArgumentException("Hub must have at least 2 ports");
        Hub hub = new Hub(portN);
        System.out.println("Hub " + hub.hub_id + " created with " + portN + " ports");
        hubMapping.put(hub.hub_id, hub);
    }
    public Wire createWire() {
        System.out.println("1. End Device");
        System.out.println("2. Hub");
        System.out.println("3. Switch");
        System.out.println("4. Bridge");
        System.out.println("Enter the device type of first device: ");
        Scanner sc = new Scanner(System.in);
        int dev_type = sc.nextInt();
        Device dev1;
        Port port1 = null;
        switch(dev_type) {
            case 1:
                System.out.println("Enter the End Device id of first device: ");
                int dev_id1 = sc.nextInt();
                System.out.println("TEST: " + dev_id1 + " and " + getEndDevice(dev_id1));
                port1 = getPortEndDevice(getEndDevice(dev_id1));
                break;
            case 2:
                System.out.println("Enter the Hub id of first device: ");
                int hub_id1 = sc.nextInt();
                dev1 = getHub(hub_id1);
                port1 = getPortHub((Hub)dev1);
                break;
            case 3:
                System.out.println("Enter the Switch id of first device: ");
                int switch_id1 = sc.nextInt();
                dev1 = getSwitch(switch_id1);
                break;
            case 4:
                System.out.println("Enter the Bridge id of first device: ");
                int bridge_id1 = sc.nextInt();
                dev1 = getBridge(bridge_id1);
                port1 = getPortBridge((Bridge)dev1);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        System.out.println("Enter the device type of second device: ");
        dev_type = sc.nextInt();
        Device dev2;
        Port port2 = null;
        switch(dev_type) {
            case 1:
                System.out.println("Enter the End Device id of second device: ");
                int dev_id2 = sc.nextInt();
                dev2 = getEndDevice(dev_id2);
                port2 = getPortEndDevice((EndDevice)dev2);
                break;
            case 2:
                System.out.println("Enter the Hub id of second device: ");
                int hub_id2 = sc.nextInt();
                dev2 = getHub(hub_id2);
                port2 = getPortHub((Hub)dev2);
                break;
            case 3:
                System.out.println("Enter the Switch id of second device: ");
                int switch_id2 = sc.nextInt();
                dev2 = getSwitch(switch_id2);
                port2 = getPortSwitch((Switch)dev2);
                break;
            case 4:
                System.out.println("Enter the Bridge id of second device: ");
                int bridge_id2 = sc.nextInt();
                dev2 = getBridge(bridge_id2);
                port2 = getPortBridge((Bridge)dev2);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        Wire wire = new Wire(port1, port2);
        System.out.println("Wire created");
        return wire;
    }
    public void createBridge() {
        Bridge bridge = new Bridge();
        System.out.println("Bridge " + bridge.bridge_id + " created");
        bridgeMapping.put(bridge.bridge_id, bridge);
    }
    public void createSwitch(int portN) {
        Switch sw = new Switch(portN);
        System.out.println("Switch " + sw.switch_id + " created");
        switchMapping.put(sw.switch_id, sw);
    }
    public void sendMessage(String message, int rec_id, int sen_id) {
        EndDevice dev = getEndDevice(sen_id);
        dev.send(message, rec_id, sen_id);
    }
    public void disconnectWire() {
        System.out.println("Enter the wire id to be disconnected: ");
        Scanner sc = new Scanner(System.in);
        int wire_id = sc.nextInt();
        // Wire wire = getWire(wire_id);
        // wire.disconnect();
    }
}
