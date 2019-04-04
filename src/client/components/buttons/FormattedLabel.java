package client.components.buttons;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eric on 09/05/2017.
 */
public class FormattedLabel extends JLabel {
    public FormattedLabel(String text, int style, int fontSize) {
        super(text);
        setFont(new Font("Tahoma",style,fontSize));
    }

    public static String abbreviate(String str, int maxWidth) {
        if (null == str) {
            return null;
        }

        if (str.length() <= maxWidth) {
            return str;
        }

        return str.substring(0, maxWidth) + "...";
    }
}
