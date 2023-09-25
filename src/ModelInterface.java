import java.rmi.Remote;
import java.rmi.RemoteException;

//interface for RMI. extends remote
public interface ModelInterface extends Remote {

    //getters & setters for title, author for the RMI lookup
    public String getTitle()throws RemoteException;
    public String getAuthor()throws RemoteException;
}
