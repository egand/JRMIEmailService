package client.components.panels;

import client.components.buttons.FormattedLabel;
import shared.Email;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Created by Eric on 09/05/2017.
 */
public class EmailFullPanel extends JPanel {
    private FormattedLabel subject, sender, date, recipient;
    private JTextArea textMessage;
    private Border panelBorder;

    public EmailFullPanel() {
        super(new GridBagLayout());
        setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();

        panelBorder = new EmptyBorder(30, 30, 30, 30);
        setBorder(panelBorder);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        subject = new FormattedLabel("", Font.PLAIN, 27);
        add(subject, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        sender = new FormattedLabel("", Font.PLAIN, 15);
        add(sender, gbc);

        gbc.gridy = 2;
        recipient = new FormattedLabel("", Font.PLAIN, 15);
        add(recipient, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.anchor = GridBagConstraints.LINE_END;
        date = new FormattedLabel("", Font.PLAIN, 15);
        date.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)), new EmptyBorder(0, 0, 20, 0)));
        add(date, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        textMessage = new JTextArea("");
        textMessage.setWrapStyleWord(true);
        textMessage.setLineWrap(true);
        textMessage.setEditable(false);
        textMessage.setBackground(Color.white);
        JScrollPane jScrollPane = new JScrollPane(textMessage);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(jScrollPane, gbc);


    }

    /**
     * Set the parameters of the class with the attributes of email
     * @param email
     */
    public void setLabels(Email email) {
        subject.setText(email.getSubject());
        sender.setText("From: " + email.getSender());
        recipient.setText("To: " + email.getRecipient());
        date.setText("Date: " + email.getFullDate());
        textMessage.setText(email.getMailText());
    }
}
