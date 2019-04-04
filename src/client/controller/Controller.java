package client.controller;

import shared.*;

import javax.swing.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Eric on 25/05/2017.
 */
public class Controller {
    private Model model;
    private ServerInterface server;
    private boolean isConnected = false;
    private Executor executor;

    public Controller() {
        executor = Executors.newSingleThreadExecutor();
    }

    public boolean connect(String username) {
        try {
            server = (ServerInterface) Naming.lookup("rmi://127.0.0.1:2048/Server");
            model = server.getMailbox(username);
            isConnected = true;
            return true;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
    }

    public String getUsername() {
        try {
            return model.getUsername();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DefaultListModel<Email> listInitialization() {
        try {
            return model.getList();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendMessage(String recipient, String subject, String msg, String priority) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    server.sendMessage(new Email(model.getUsername(),recipient,subject,msg,priority));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteSelectedMessage(int index) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    server.removeMessage(model.getUsername(),index);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void switchClientState(String command) {
        try {
            model.changeCategory(command);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addSubscriber(Subscriber s) {
        try {
            model.addSubscriber(s);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(Subscriber s) {
        try {
            model.removeSubscriber(s);
            server.notifyDisconnection(model.getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean getStatus() {
        return isConnected;
    }



}
