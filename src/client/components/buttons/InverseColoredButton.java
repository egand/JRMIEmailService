package client.components.buttons;

import client.components.ClientConstants;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Created by Eric on 12/05/2017.
 */
public class InverseColoredButton extends JButton {

    public InverseColoredButton(String text) {
        super(text);
        setForeground(ClientConstants.COLOR_PRIMARY);
        setBackground(Color.white);
        setFocusPainted(false);
        setBorder(new MatteBorder(1,0,1,1,ClientConstants.COLOR_BORDER));
    }
}
