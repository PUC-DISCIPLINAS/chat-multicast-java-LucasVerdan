package redes;
import java.io.Serializable;


public class User implements Serializable{
    public String message;
    public String name;

    public User(String message, String name) {
        this.message = message;
        this.name = name;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }
}
