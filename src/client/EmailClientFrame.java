package client;

import client.components.ClientConstants;
import client.components.panels.EmailFullPanel;
import client.components.panels.MailboxesPanel;
import client.components.panels.StatusBar;
import client.controller.Controller;
import client.view.*;
import client.components.buttons.ColoredButton;
import client.components.buttons.FormattedLabel;
import client.components.buttons.InverseColoredButton;
import shared.Email;
import utility.EmailServiceUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

/**
 * Created by Eric on 06/05/2017.
 */
public class EmailClientFrame extends JFrame {

    private LeftPanel leftPanel;
    private CenterPanel centerPanel;
    private RightPanel rightPanel;
    private EmailFullPanel emailFullPanel;
    private EmailListWrapper emailListWrapper;
    private Controller controller;
    private StatusBar status;



    public EmailClientFrame(Controller controller) {
        super("Email client");
        setSize(1100,600);
        EmailServiceUtility.centerWindow(this);
        this.controller = controller;

        // close action
        // if the client had a connection to the server, it send a message to it before closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(controller.getStatus()) controller.disconnect(emailListWrapper);
                System.exit(0);
            }
        });

        // ------ MENU -----
        initializeMenu();


        emailFullPanel = new EmailFullPanel();
        try {
            emailListWrapper = new EmailListWrapper();
        } catch (RemoteException e) {
            e.printStackTrace();
        }



        leftPanel = new LeftPanel();
        centerPanel = new CenterPanel();
        rightPanel = new RightPanel();
        status = new StatusBar();
        rightPanel.setVisible(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(centerPanel,BorderLayout.WEST);
        panel.add(rightPanel,BorderLayout.CENTER);

        add(leftPanel,BorderLayout.WEST);
        add(panel,BorderLayout.CENTER);
        add(status,BorderLayout.SOUTH);

        setVisible(true);
    }


    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu connectionsMenu = new JMenu("Connections");
        JMenuItem connectMenuItem = new JMenuItem("Connect");
        connectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(null,"Insert your email: ","");
                if(controller.connect(username)) {
                    initializeClient();
                    status.changeStatus();
                }
                else setError("Connection Error","The server you're trying to connect is unavailable or busy, please try again later");
            }
        });
        connectionsMenu.add(connectMenuItem);
        menuBar.add(connectionsMenu);
        setJMenuBar(menuBar);
    }

    /**
     * used to give a feedback to the user if some error occurred
     * @param title the title of the JDialog
     * @param message message error
     */
    private void setError(String title, String message) {
        JOptionPane.showMessageDialog(EmailClientFrame.this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * When successfully connected, this method initialize the client with the information
     * provided by the server to the controller.
     */
    private void initializeClient() {
        leftPanel.username.setText(controller.getUsername());
        ListModel<Email> list = controller.listInitialization();
        if(list != null) emailListWrapper.getEmailJList().setModel(list);
        else setError("Can't retrieve MailList", "There was a problem retrieving the mail list, please try to reconnect again");
        controller.addSubscriber(emailListWrapper);
    }





    private class LeftPanel extends JPanel {
        private JPanel bodyPanel;
        private ColoredButton newMessage;
        private FormattedLabel username;

        private LeftPanel() {
            // --- Initialization Left Panel ---
            super(new GridBagLayout());
            setBackground(new Color(249,248,248));
            setPreferredSize(new Dimension(175,600));
            setBorder(new EmptyBorder(10,20,10,20));
            GridBagConstraints gbc = new GridBagConstraints();

            // --- Left Panel Header ---
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weighty = 0.05;
            gbc.weightx = 1;
            gbc.gridy = 0;
            gbc.gridx = 0;
            username = new FormattedLabel("",Font.PLAIN,12);
            add(username,gbc);


            // --- Body panel ---
            bodyPanel = new JPanel(new GridBagLayout());
            bodyPanel.setBorder(new MatteBorder(1,0,0,0, ClientConstants.COLOR_BORDER));
            bodyPanel.setBackground(ClientConstants.COLOR_BACKGROUND);

            newMessage = new ColoredButton("NEW MESSAGE");
            newMessage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(controller.getStatus()) new SendMessageFrame(controller);
                    else setError("Client not connected","You need to connect before sending messages");
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.ipady = 10;
            gbc.insets = new Insets(20,0,30,0);
            bodyPanel.add(newMessage,gbc);

            gbc.gridy = 1;
            gbc.ipady = 0;
            gbc.insets = new Insets(0,0,5,0);
            bodyPanel.add(new JLabel("MAILBOXES"),gbc);


            // --- Mailboxes Panel ---
            MailboxesPanel mailboxesPanel = new MailboxesPanel(controller);
            gbc.gridy = 2;
            gbc.insets = new Insets(5,0,0,0);
            bodyPanel.add(mailboxesPanel,gbc);

            gbc.weighty = 0.95;
            gbc.weightx = 1;
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            add(bodyPanel,gbc);


        }
    }



    private class CenterPanel extends JPanel {

        private CenterPanel() {
            super();
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(351,90));
            setMaximumSize(new Dimension(351,90));

            emailListWrapper.getEmailJList().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(emailListWrapper.getEmailJList().isSelectionEmpty()) {
                        rightPanel.setVisible(false);
                    }
                    else {
                        Email selectedItem = emailListWrapper.getEmailJList().getSelectedValue();
                        emailFullPanel.setLabels(selectedItem);
                        rightPanel.setVisible(true);
                    }
                }
            });
            JScrollPane jScrollPane = new JScrollPane(emailListWrapper.getEmailJList());
            jScrollPane.setBorder(new MatteBorder(1,1,0,1,ClientConstants.COLOR_BORDER));
            add(jScrollPane);


        }
    }




    /**
     * In this class the details of the email are shown (bodyPanel)
     * with several buttons (headerPanel) that interact with the selected email (like reply, forward, delete, etc.)
     */
    private class RightPanel extends JPanel {
        private JPanel headerPanel, bodyPanel;

        private class RightPanelListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                Email email = emailListWrapper.getEmailJList().getSelectedValue();
                switch(command) {
                    case "Reply":
                        if(email.getSender().equals(controller.getUsername())) { // client is in sent state
                            new SendMessageFrame(controller,email.getRecipient(), "RE: " + email.getSubject());
                        }
                        else new SendMessageFrame(controller,email.getSender(), "RE: " + email.getSubject());
                        break;
                    case "Reply All":
                        String[] recipient = email.getRecipient().split(",");
                        String strToDelete = controller.getUsername();
                        StringBuilder finalRecipient = new StringBuilder();
                        for(String r: recipient ) {
                            if(!r.equals(strToDelete)) finalRecipient.append("," + r);
                        }
                        String multiRecipients;
                        if(email.getSender().equals(strToDelete)) multiRecipients = finalRecipient.toString(); // client is in sent state
                        else multiRecipients = email.getSender() + finalRecipient.toString();
                        new SendMessageFrame(controller,multiRecipients, "RE: " + email.getSubject());
                        break;
                    case "Forward":
                        String fwdMessage = "--- Forwarded Message ---\nFrom: " + email.getSender() + "\nTo: " + email.getRecipient() + "\nDate: " + email.getFullDate() + "\n\n";
                        new SendMessageFrame(controller,null,"Fwd: " + email.getSubject(),fwdMessage + email.getMailText());
                        break;
                    case "Delete":
                        controller.deleteSelectedMessage(emailListWrapper.getEmailJList().getSelectedIndex());
                        break;
                }
            }

        }

        private RightPanel() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            // Initialize header buttons
            headerPanel = new JPanel(new GridLayout(0, ClientConstants.textBtn.length));
            headerPanel.setPreferredSize(new Dimension(headerPanel.getPreferredSize().width, 50));
            headerPanel.setMaximumSize(new Dimension(headerPanel.getMaximumSize().width, 50));

            RightPanelListener rightPanelListener = new RightPanelListener();

            for (String str : ClientConstants.textBtn) {
                InverseColoredButton e = new InverseColoredButton(str);
                e.setActionCommand(str);
                e.addActionListener(rightPanelListener);
                headerPanel.add(e);
            }
            add(headerPanel);


            // Initialize email details
            bodyPanel = new JPanel(new GridLayout());
            bodyPanel.add(emailFullPanel);
            add(bodyPanel);
        }


    }
}
