package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Eric on 20/06/2017.
 */
public interface Subscriber extends Remote {
    void update(Model pub, Object extra_arg) throws RemoteException;
}
