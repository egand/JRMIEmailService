package client.components.buttons;

import client.components.ClientConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eric on 11/05/2017.
 */
public class ColoredButton extends JButton {

    public ColoredButton(String text) {
        super(text);
        setForeground(Color.white);
        setBackground(ClientConstants.COLOR_PRIMARY);
        setFocusPainted(false);
        setBorderPainted(false);
    }
}
