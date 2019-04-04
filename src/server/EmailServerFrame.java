package server;

import utility.EmailServiceUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eric on 13/05/2017.
 */
public class EmailServerFrame extends JFrame {
    private JPanel mainPanel;
    private JTextArea textArea;
    public EmailServerFrame(String title) {
        super(title);
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EmailServiceUtility.centerWindow(this);

        mainPanel = new JPanel(new GridLayout());
        mainPanel.setBorder(new EmptyBorder(30,10,10,10));
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane);
        add(mainPanel);

        setVisible(true);
    }

    /**
     * add a well formatted log row in Server jframe
     * @param s string to format
     */
    public synchronized void addLog(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        textArea.append(sdf.format(new Date()) + " - " + s + "\n");
    }
}
