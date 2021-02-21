package redes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {

    public String idRoom;
    private List<String> users;

    public Room(String idRoom, String[] users) {
        this.idRoom = idRoom;
        this.users = Arrays.asList(users);
    }

    public Room(String idRoom) {
        this.idRoom = idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public void setUsers(String[] users) {
        this.users = Arrays.asList(users);
    }

    public String getIdRoom() {
        return idRoom;
    }

    public List<String> getUsers() {
        return users;
    }
}
