package main.java.net.networkSimulator.view;
import main.java.net.networkSimulator.controller.*;
import main.java.net.networkSimulator.model.devices.*;
import main.java.net.networkSimulator.model.layers.datalink.*;
import java.util.Scanner;

public class Main { 
    public static void fetchCommands() {
        System.out.print(">> ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            Control control = new Control();
            int portN;
            switch (choice) {
                case 1:
                    System.out.println("Enter the number of ports for the End Device: ");
                    portN = sc.nextInt();
                    control.createEndDevice(portN);
                    break;
                case 2:
                    System.out.println("Enter the number of ports for the Hub: ");
                    portN = sc.nextInt();
                    control.createHub(portN);
                    break;
                case 3:
                    control.createWire();
                    break;
                case 4:
                    System.out.println("Enter the number of ports for the Switch: ");
                    portN = sc.nextInt();
                    control.createSwitch(portN);
                    break;
                case 5:
                    control.createBridge();
                    break;
                case 6:
                    // Clear/consume the buffer 
                    System.out.println("Enter the message to be sent: ");
                    String consume = sc.next();
                    String message = sc.nextLine();
                    System.out.println("Enter the receiver ID: ");
                    int rec_id = sc.nextInt();
                    System.out.println("Enter the sender ID: ");
                    int sen_id = sc.nextInt();
                    control.sendMessage(message, rec_id, sen_id);
                    break;
                case 7:
                    control.disconnectWire();
                    break;
                case 8:
                    control.initializeNetwork();
                case 9:
                    // TokenPassing.stop();
                    // Control.thread.join();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
    }
    public static void main(String[] args) {
        System.out.println("Network Simulator");
        System.out.println("==================================");
        System.out.println("Enter the operation you want to perform: ");
        System.out.println("1. Create an End Device");
        System.out.println("2. Create a Hub");
        System.out.println("3. Create a Wire");
        System.out.println("4. Create a Switch");
        System.out.println("5. Create a Bridge");
        System.out.println("6. Send a message");
        System.out.println("7. Disconnect a Wire");
        System.out.println("8. Initialize the network");
        System.out.println("9. Exit");
        System.out.println("==================================");
        while(true) {
            fetchCommands();
        }
    }
}
