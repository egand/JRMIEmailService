package client.components.buttons;

import client.components.ClientConstants;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by Eric on 09/05/2017.
 */
public class HyperLink extends JToggleButton {
    public HyperLink(String title) {
        super(title);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(isSelected()) setForeground(ClientConstants.COLOR_PRIMARY);
                else setForeground(Color.black);
            }
        });
    }
}
