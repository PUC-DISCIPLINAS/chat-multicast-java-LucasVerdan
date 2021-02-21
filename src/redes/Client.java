package redes;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String args[]) {

        Scanner reader = new Scanner(System.in);
        Scanner reader2 = new Scanner(System.in);
        Scanner reader3 = new Scanner(System.in);
        String lastCreatedServer = new String("228.5.6.9");
//        User user = new User("", "username");
//
//        System.out.print("Write your name:");
//         String username = reader3.nextLine();



        System.out.println("1 - Create room");
        System.out.println("2 - Enter room");
        System.out.println("3 - List rooms");
        System.out.println("4 - List users on room");
        System.out.println("5 - Leave application");
        int switcher = reader.nextInt();
        do {
            switch (switcher) {

                case 1: {
                    DatagramSocket aSocket = null;
                    String message;
                    try {
                        aSocket = new DatagramSocket();
                        String chosenOption = "1";
                        byte[] m = chosenOption.getBytes();

                        InetAddress aHost = InetAddress.getByName("127.0.0.1");
                        DatagramPacket request = new DatagramPacket(m, m.length, aHost, 7070);
                        aSocket.send(request);

                        byte[] buffer = new byte[1000];

                        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                        aSocket.receive(reply);
                        message = new String(reply.getData()).trim();
                        lastCreatedServer = message;
                        System.out.println("Servidor criado com o ID: " + message);
                    } catch (SocketException e) {
                        System.out.println("Socket: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("IO: " + e.getMessage());
                    } finally {
                        if (aSocket != null)
                            aSocket.close();
                    }
                }
                case 2: {
                    MulticastSocket mSocket = null;
                    String userMessage = reader2.nextLine();

//                    user.setMessage(userMessage);
                    try {

                        InetAddress groupIp;
                        mSocket = new MulticastSocket(6785);
                        groupIp = InetAddress.getByName(lastCreatedServer);
                        mSocket.joinGroup(groupIp);


                        System.out.println();

                        byte[] message1 = userMessage.getBytes(StandardCharsets.UTF_8);
                        DatagramPacket messageOut = new DatagramPacket(message1, message1.length, groupIp, 6785);
                        mSocket.send(messageOut);

                        byte[] buffer = new byte[1000];
                        new Thread(() -> listenServer(groupIp)).start();


                    } catch (SocketException e) {
                        System.out.println("Socket: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("IO: " + e.getMessage());
                    } finally {

                        if (mSocket != null)
                            mSocket.close();
                    }
                }
                case 3: {

                }

                case 4: {


                }
            }

        } while (switcher != 5);
    }

    public static void listenServer(InetAddress ip) {
        byte[] buffer = new byte[1000];
        MulticastSocket mSocket = null;

        try {
            mSocket = new MulticastSocket(6785);
            mSocket.joinGroup(ip);
            while (true){ // get messages from others in group
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                mSocket.receive(messageIn);
                System.out.println("Recebido:" + new String(messageIn.getData()).trim());
//                            mSocket.leaveGroup(groupIp);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }  finally {
            if (mSocket != null)
                mSocket.close();
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

}

