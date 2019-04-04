package shared;

import server.Mailbox;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Eric on 24/05/2017.
 */
public interface ServerInterface extends Remote {
    void sendMessage(Email email) throws RemoteException;
    Model getMailbox(String username) throws RemoteException;
    void notifyDisconnection(String username) throws RemoteException;
    void removeMessage(String username, int index) throws RemoteException;
}
