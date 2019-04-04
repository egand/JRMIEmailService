package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Eric on 20/06/2017.
 */
public interface Publisher extends Remote {
    void addSubscriber(Subscriber s) throws RemoteException;
    void removeSubscriber(Subscriber s) throws RemoteException;
    void removeAllSubscriber() throws RemoteException;
    void notifySubscribers(Model pub, Object extra_arg) throws RemoteException;
}
