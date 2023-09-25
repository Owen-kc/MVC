import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//ModelD, same as Model class, just keeping them seperate as issues with server was happening when using the same model
public class ModelD extends UnicastRemoteObject implements ModelInterface {

    //init variables
    private String title;
    private String author;

    //constructor for variables
    public ModelD(String title, String author) throws RemoteException {
        this.title = title;
        this.author = author;
    }

    //getters and setters
    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
