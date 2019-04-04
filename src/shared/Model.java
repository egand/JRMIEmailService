package shared;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observer;

/**
 * Created by Eric on 18/06/2017.
 */
public interface Model extends Publisher, Remote {
    void changeCategory(String command) throws RemoteException;
    DefaultListModel<Email> getList() throws RemoteException;
    String getUsername() throws RemoteException;

}
