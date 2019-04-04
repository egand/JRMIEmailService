package shared;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eric on 09/05/2017.
 */
public class Email implements Serializable {
    private static int count = 0;
    private int id;
    private String sender;
    private String recipient;
    private String subject;
    private String mailText;
    private String priority;
    private Date date;

    public Email(String sender, String recipient, String subject, String mailText, String priority) {
        id = ++count;
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.mailText = mailText;
        this.priority = priority;
        date = new Date();
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getMailText() {
        return mailText;
    }

    public String getPriority() {
        return priority;
    }

    public String getSmallDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M, HH:mm");
        return dateFormat.format(date);
    }

    public String getFullDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy, HH:mm");
        return dateFormat.format(date);
    }
}
