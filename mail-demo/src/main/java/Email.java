import java.util.Set;

public class Email {

    private String subject;

    private String message;

    private String[] receivers;

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }
}