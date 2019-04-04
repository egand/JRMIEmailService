package client;

import client.components.ClientConstants;
import client.components.buttons.ColoredButton;
import client.controller.Controller;
import utility.EmailServiceUtility;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Eric on 06/05/2017.
 */
public class SendMessageFrame extends JFrame {
    private FieldsPanel fieldsPanel;
    private JTextArea textMessage;
    private JScrollPane body;
    private ColoredButton send;

    public SendMessageFrame(Controller controller) {
        this(controller,null,null,null);
    }

    public SendMessageFrame(Controller controller, String recipient, String subject) {
        this(controller,recipient,subject,null);
    }

    public SendMessageFrame(Controller controller, String recipient, String subject, String msg) {
        super("New Message");
        setSize(500,430);
        setLayout(new GridBagLayout());
        setResizable(false);
        EmailServiceUtility.centerWindow(this);
        System.out.println(Thread.currentThread().getName());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        fieldsPanel = new FieldsPanel(recipient,subject);

        textMessage = new JTextArea(msg);
        textMessage.setWrapStyleWord(true);
        textMessage.setLineWrap(true);
        body = new JScrollPane(textMessage);
        send = new ColoredButton("SEND");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(inputValidate(fieldsPanel.recipientField,fieldsPanel.subjectField,textMessage)) {
                    try {
                        controller.sendMessage(fieldsPanel.recipientField.getText(),fieldsPanel.subjectField.getText(),textMessage.getText(),fieldsPanel.priority.getSelectedItem().toString());
                    } catch (Exception exc) {
                        System.out.println("PROBLEMA: " + exc.getMessage());
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Please fill in the red fields");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0,0,0,40);
        add(fieldsPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0.85;
        gbc.insets = new Insets(10,10,0,10);
        add(body,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weighty = 0.05;
        gbc.fill = 0;
        gbc.insets = new Insets(0,0,10,0);
        gbc.ipadx = 30;
        gbc.ipady = 2;
        add(send,gbc);
        setVisible(true);




    }

    private class FieldsPanel extends JPanel {

        private JTextField recipientField, subjectField;
        private JComboBox<String> priority;
        private final String[] priorityList = {"Normal", "Important"};

        private FieldsPanel(String recipient,String subject) {
            super(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5,3,0,3);



            recipientField = new JTextField(recipient);
            subjectField = new JTextField(subject);
            priority = new JComboBox<>(priorityList);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.1;
            add(new JLabel("To: ",SwingConstants.RIGHT),gbc);

            gbc.gridx = 1;
            gbc.gridwidth = 3;
            gbc.weightx = 0.9;
            add(recipientField,gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 0.1;
            add(new JLabel("Subject: ",SwingConstants.RIGHT),gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.9;
            add(subjectField,gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.1;
            add(new JLabel("Priority: ",SwingConstants.RIGHT), gbc);

            gbc.gridx = 3;
            gbc.weightx = 0.1;
            add(priority,gbc);

        }
    }

    private static boolean inputValidate(JTextComponent... inputs) {
        boolean isValidatePassed = true;
        for (JTextComponent input : inputs) {
            if (input.getText() == null && input.getText().trim().equals("")) {
                input.setBackground(ClientConstants.COLOR_INVALID_INPUT);
                isValidatePassed = false;
            }
        }
        return isValidatePassed;
    }


}
//TODO - add comments!!!
//TODO - guardare i bordi se se ne possono applicare di meno
