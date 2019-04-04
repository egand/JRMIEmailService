package client.components.panels;

import client.components.ClientConstants;
import client.components.buttons.HyperLink;
import client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Eric on 14/05/2017.
 */
public class MailboxesPanel extends JPanel {
    private Controller controller;

    private class MailboxesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            controller.switchClientState(command);
        }
    }

    public MailboxesPanel(Controller controller) {
        super();
        this.controller = controller;
        MailboxesListener mailboxesListener = new MailboxesListener();

        setLayout(new GridLayout(ClientConstants.MAILBOXES_BTN.length, 0));
        setBackground(ClientConstants.COLOR_BACKGROUND);
        ButtonGroup group = new ButtonGroup();
        for (String str : ClientConstants.MAILBOXES_BTN) {
            HyperLink e = new HyperLink(str);
            e.setActionCommand(str);
            group.add(e);
            if (str.equals("Inbox")) group.setSelected(e.getModel(), true);
            e.addActionListener(mailboxesListener);
            add(e);
        }
    }

}
