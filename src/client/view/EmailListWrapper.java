package client.view;

import shared.Email;
import shared.Model;
import shared.Publisher;
import shared.Subscriber;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Eric on 14/05/2017.
 */
public class EmailListWrapper extends UnicastRemoteObject implements Subscriber {
    private JList<Email> emailJList;
    private Executor exec;

    public EmailListWrapper() throws RemoteException {
        exec = Executors.newSingleThreadExecutor();
        emailJList = new JList<>();
        emailJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        emailJList.setCellRenderer(new EmailListRenderer());
    }

    public JList<Email> getEmailJList() {
        return emailJList;
    }

    @Override
    public void update(Model pub, Object extra_arg) {
        try {
            emailJList.setModel(pub.getList());
            if(extra_arg != null) {
                Email email = (Email) extra_arg;
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(null,"You received a new message!\nFrom: " + email.getSender() + "\nSubject: " + email.getSubject());
                    }
                });

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
