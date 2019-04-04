package server;

import shared.Email;
import shared.Model;
import shared.ServerInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Eric on 13/05/2017.
 */
public class Server extends UnicastRemoteObject implements ServerInterface {
    private EmailServerFrame server;
    private Map<String,Mailbox> mailboxList;

    private Server() throws RemoteException {
        super();
        server = new EmailServerFrame("Email Server");
        mailboxList = new ConcurrentHashMap<>();
    }



    @Override
    public void sendMessage(Email email) throws RemoteException {
        server.addLog("User " + email.getSender() + " sent a message to " + email.getRecipient());
        mailboxList.get(email.getSender()).setSentMessage(email);
        String[] recipient = email.getRecipient().split(",");
        for(String r : recipient) {
            r = r.trim();
            if(mailboxList.containsKey(r)) {
                server.addLog("Message from " + email.getSender() + " sent to " + r + " inbox");
                Mailbox mailbox = mailboxList.get(r);
                mailbox.setNewMessage(email);
            }
            else {
                if(mailboxList.containsKey(email.getSender())) {
                    server.addLog("Recipient " + r + " not found. Forward failure notice to " + email.getSender());
                    Mailbox mailbox = mailboxList.get(email.getSender());
                    mailbox.setNewMessage(new Email(
                            "server@info.com",
                            email.getSender(),
                            "Failure notice: recipient not found",
                            "This message was created automatically by mail delivery software.\n" +
                                    "A message that you sent has not yet been delivered to one or more of its\n" +
                                    "recipients because the recipient is not registered in our mail system.\n" +
                                    "***RECIPIENT NOT FOUND: " + r +
                                    " ***\n\nMessage info:\nEmail ID: " + email.getId() +"\nSender: " + email.getSender() +"\nRecipient: " + email.getRecipient() +
                                    "\nSubject: " + email.getSubject() + "\nMessage body: " + email.getMailText() + "\n Date: " +
                                    email.getFullDate(),"Important"
                    ));
                }
            }
        }
    }

    @Override
    public Model getMailbox(String username) throws RemoteException {
        if(!mailboxList.containsKey(username))
            mailboxList.put(username,new Mailbox(username));
        server.addLog("User " + username + " connected to the server and retrieved his Mailbox");
        return mailboxList.get(username);
    }

    @Override
    public void notifyDisconnection(String username) throws RemoteException {
        server.addLog("User " + username + " disconnected from the Server");
    }

    @Override
    public void removeMessage(String username, int index) throws RemoteException {
        Mailbox mailbox = mailboxList.get(username);
        mailbox.removeMessage(index);
        server.addLog("User " + username + "deleted a message at index " + index);
    }


    private static void runRMIRegistry() {
        try {
            LocateRegistry.createRegistry(2048);
            System.out.println("Java RMI registry created");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists");
        }
    }


    public static void main(String[] args) throws Exception {
        runRMIRegistry();
        Server s = new Server();

        try {
            Naming.rebind("rmi://127.0.0.1:2048/Server", s);
            System.out.println("Server: " + s);
            System.out.println("PeerServer bounded in registry");
        } catch (Exception e) {
            System.err.println("PeerServer not bounded");
            e.printStackTrace();
        }

    }
}
