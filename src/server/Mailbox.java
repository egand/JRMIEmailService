package server;

import shared.Email;
import shared.Model;
import shared.Subscriber;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by Eric on 18/06/2017.
 */
public class Mailbox extends UnicastRemoteObject implements Model {
    private DefaultListModel<Email> inbox;
    private DefaultListModel<Email> important;
    private DefaultListModel<Email> sent;
    private DefaultListModel<Email> current;
    private String username;
    private Vector<Subscriber> subscribers = new Vector<>(2);

    public Mailbox(String username) throws RemoteException {
        this.username = username;
        inbox = new DefaultListModel<>();
        important = new DefaultListModel<>();
        sent = new DefaultListModel<>();
        current = inbox;
    }


    public void setNewMessage(Email email) {
        if(email.getPriority().equals("Important")) important.add(0,email);
        inbox.add(0,email);
        try {
            notifySubscribers(this, email);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setSentMessage(Email email) {
        sent.add(0,email);
        notifySubscribers(this);
    }

    @Override
    public DefaultListModel<Email> getList() {
        return current;
    }


    public void removeMessage(int index) {
        current.removeElementAt(index);
        notifySubscribers(this);
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }

    @Override
    public void changeCategory(String command) {
        switch (command) {
            case "Sent":
                current = sent;
                break;
            case "Important":
                current = important;
                break;
            default:
                current = inbox;
                break;
        }
        notifySubscribers(this);
    }

    @Override
    public void addSubscriber(Subscriber s) throws RemoteException {
        if(s == null) throw new NullPointerException();
        if(!subscribers.contains(s))
        subscribers.addElement(s);
    }

    @Override
    public void removeSubscriber(Subscriber s) throws RemoteException {
        subscribers.removeElement(s);
    }

    @Override
    public void removeAllSubscriber() throws RemoteException {
        subscribers.removeAllElements();
    }

    @Override
    public void notifySubscribers(Model pub, Object extra_arg) throws RemoteException {
        Enumeration<Subscriber> e = subscribers.elements();
        while(e.hasMoreElements()) {
            Subscriber s = e.nextElement();
            try {
                s.update(pub, extra_arg);
            } catch(Exception exc) {
                exc.getStackTrace();
            }
        }
    }

    public void notifySubscribers(Model pub) {
        try {
            notifySubscribers(pub,null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
