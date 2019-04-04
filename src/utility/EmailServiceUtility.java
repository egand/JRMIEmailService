package utility;

import java.awt.*;

/**
 * Created by Eric on 13/05/2017.
 */
public class EmailServiceUtility {

    private EmailServiceUtility(){}

    public static void centerWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
