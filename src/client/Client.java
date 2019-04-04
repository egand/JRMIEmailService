package client;

import client.controller.Controller;

import java.rmi.RemoteException;

/**
 * Created by Eric on 06/05/2017.
 */
public class Client {
    private EmailClientFrame clientFrame;
    private Controller controller;

    private Client() {
        controller = new Controller();
        clientFrame = new EmailClientFrame(controller);
    }

    public static void main(String[] args) throws Exception {
        Client c = new Client();
    }

}
