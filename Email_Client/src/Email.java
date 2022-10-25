import java.io.*;
import java.time.LocalDate;
//email class
public class Email implements Serializable {
    private String receiver, subject, content;
    private LocalDate sendDate;
    public Email(String receiver, String subject, String content) {
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.sendDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return  "\treceiver: " + receiver + '\n' +
                "\tsubject: " + subject + '\n' +
                "\tcontent: " +
                "\n\t" + content + '\n';
    }

    public String getSubject() {
        return subject;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getSendDate() {
        return sendDate;
    }
}
