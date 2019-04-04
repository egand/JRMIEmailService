package client.view;

import client.components.buttons.FormattedLabel;
import shared.Email;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Created by Eric on 10/05/2017.
 */
public class EmailListRenderer extends JPanel implements ListCellRenderer<Email> {

    private FormattedLabel sender;
    private FormattedLabel subject;
    private FormattedLabel date;
    private FormattedLabel textMsg;
    private Border panelBorder;

    public EmailListRenderer() {
        setLayout(new GridBagLayout());
        panelBorder = new CompoundBorder(new MatteBorder(0,0,1,0,new Color(240,239,239)),new EmptyBorder(10,20,15,15));
        setBorder(panelBorder);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0,0,5,0);
        sender = new FormattedLabel("", Font.BOLD,14);
        add(sender,gbc);


        gbc.gridy = 1;
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0,0,0,0);
        subject = new FormattedLabel("",Font.BOLD,12);
        add(subject,gbc);

        gbc.gridy = 2;
        textMsg = new FormattedLabel("",Font.PLAIN,11);
        add(textMsg,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        date = new FormattedLabel("",Font.PLAIN,12);
        add(date,gbc);




    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Email> list, Email value, int index, boolean isSelected, boolean cellHasFocus) {
        sender.setText(value.getSender());
        subject.setText(value.getSubject());
        date.setText(value.getSmallDate());
        textMsg.setText(FormattedLabel.abbreviate(value.getMailText(),35));
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            Border redBorder = BorderFactory.createCompoundBorder(new MatteBorder(0,3,0,0,new Color(255,90,96)),panelBorder);
            setBorder(redBorder);
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            Border bgBorder = BorderFactory.createCompoundBorder(new MatteBorder(0,0,0,0,list.getBackground()),panelBorder);
            setBorder(bgBorder);
        }

        setFont(list.getFont());
        setEnabled(list.isEnabled());

        return this;
    }
}
