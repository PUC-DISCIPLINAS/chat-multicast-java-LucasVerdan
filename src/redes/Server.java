package redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<Room> rooms = new ArrayList<Room>();
    public static void main(String args[]) {
        DatagramSocket aSocket = null;

        String message;

        try {
            InetAddress aHost = InetAddress.getByName("127.0.0.1");
            aSocket = new DatagramSocket(7070, aHost);

            System.out.println("Servidor: ouvindo porta UDP/7070.");
            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                message = new String(request.getData()).trim();
                System.out.println("Servidor: recebido \'" + message  + "\'.");

                switch (Integer.parseInt(message)) {
                    case 1: {
                        String ipCreated = generateRoom();
                        DatagramPacket reply = new DatagramPacket(ipCreated.getBytes(StandardCharsets.UTF_8), request.getLength(), request.getAddress(),
                                7070);
                        aSocket.send(reply);
                    }

                    case 3: {
                        getRoomsList();
                    }


                }



            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }

    public static String generateRoom() {
        String roomIp = "228.5.6." + Integer.toString(rooms.size());
        Room newRoom = new Room(roomIp);
        rooms.add(newRoom);

        return roomIp;
    }

//    public static void enterRoom(String roomIp) {
//        rooms.indexOf(roomIp !== -1);
//    }

    public static void getRoomsList(){
        if(rooms.size() > 0) {
//            for (int i = 0; i <= rooms.size(); i++) {
//                System.out.println("Room: " + rooms.get(i).idRoom);
//            }
        } else {
            System.out.println("No rooms");
        }
    }
}
