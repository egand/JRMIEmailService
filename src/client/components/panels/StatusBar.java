package client.components.panels;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by Eric on 01/07/2017.
 */
public class StatusBar extends JPanel {
    private JLabel status;

    public StatusBar() {
        super(new FlowLayout(FlowLayout.LEFT));
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        status = new JLabel();
        add(status);
        status.setText("Not connected");
    }

    public void changeStatus() {
        status.setText("Connected");
    }
}
