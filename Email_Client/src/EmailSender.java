import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(Email email) {    //this method accepts a email object
        final String username = "kobinarth22@gmail.com";
        final String password = "chujjqtngrqivcks";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kobinarth22@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email.getReceiver())
            );
            message.setSubject(email.getSubject());
            message.setText(email.getContent());

            Transport.send(message);
            System.out.println("a\tEmail has been sent");
            //Saving of object in a file
            Serializer.serialize(email, "outbox.ser");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
