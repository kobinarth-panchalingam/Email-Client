// 200307C
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class EmailClient {
    private static HashSet<Recipient> recipients;
    private static ArrayList<Wishable> birthdayPersons;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static void main(String[] args) {
        //==============================================
        Scanner scanner = new Scanner(System.in);
        recipients = new HashSet<>(Serializer.deSerialize("recipientObjects.ser", Recipient.class)); //loading the previously created recipient objects into the application
        birthdayPersons = Serializer.deSerialize("birthDayPersons.ser", Wishable.class);    //to store birthday wishable persons
        //=============================================
        sendBirthDayWishes();
        //==============================================
        System.out.println("=====================================================");
        System.out.println("Available option types: \n"
                + "\t     1    - Adding a new recipient\n"
                + "\t     2    - Sending an email\n"
                + "\t     3    - Printing out all the recipients who have birthdays\n"
                + "\t     4    - Printing out details of all the emails sent\n"
                + "\t     5    - Printing out the number of recipient objects in the application\n"
                + "\t     6    - Exit the application\n"
                + "\tEnter key - To reset the input\n");

        boolean isExit = false;
        while (!isExit) {
            System.out.print("Enter your option number: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Enter integer only !!!");
                scanner.next();
                continue;
            }
            int option = scanner.nextInt();
            scanner.nextLine(); //to avoid taking newline as the next string input
            switch (option) {
                case 1:
                    // input format - Official: nimal,nimal@gmail.com,ceo
                    // code to add a new recipient
                    System.out.print("Enter recipient details: \n" +
                            "\tOfficial: name,email,designation\n" +
                            "\tOffice_friend: name,email,designation,dob\n" +
                            "\tPersonal: name,<nickname>,email,dob\n\t");
                    String record = scanner.nextLine();
                    if (InputValidator.recordValidator(record)) {
                        addRecipient(record);
                    }
                    break;

                case 2:
                    // input format - email, subject, content
                    // code to send an email
                    System.out.print("Enter email details <email, subject, content>: \n\t");
                    String emailInput = scanner.nextLine();
                    if (InputValidator.emailDetailsValiator(emailInput)) {
                        String[] emailDetails = emailInput.trim().split("\\s*,\\s*|\\s");
                        //creating an email object
                        Email email = new Email(emailDetails[0], emailDetails[1], emailDetails[2]);
                        //sending email through emailSender
                        EmailSender.sendEmail(email);
                    }
                    break;
                case 3:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print recipients who have birthdays on the given date
                    System.out.print("Enter the date <yyyy/MM/dd> : ");
                    String inputBirthDay = scanner.nextLine();
                    if (InputValidator.dateValidator(inputBirthDay)) {
                        LocalDate birthDay = LocalDate.parse(inputBirthDay, formatter);
                        boolean isZeroMails = true;
                        for (Wishable person : birthdayPersons) {
                            if (person.checkBirthDay(birthDay)) {
                                isZeroMails = false;
                                System.out.println("\t" + person.getWisherName() + " has birthday on " + inputBirthDay);
                            }
                        }
                        if (isZeroMails)
                            System.out.println("\tNo one has birthday on " + inputBirthDay);
                    }
                    break;
                case 4:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print the details of all the emails sent on the input date
                    System.out.print("Enter the date <yyyy/MM/dd> : ");
                    String inputDate = scanner.nextLine();
                    if (InputValidator.dateValidator(inputDate)) {
                        LocalDate date = LocalDate.parse(inputDate, formatter);
                        ArrayList<Email> sentMails = Serializer.deSerialize("outbox.ser", Email.class);
                        boolean zeroMails = true;
                        for (Email sentMail : sentMails) {
                            if (sentMail.getSendDate().compareTo(date) == 0) {
                                zeroMails = false;
                                System.out.println(sentMail);
                            }
                        }
                        if (zeroMails) {
                            System.out.println("\tNo sent mails on " + inputDate);
                        }
                    }

                    break;
                case 5:
                    // code to print the number of recipient objects in the application
                    System.out.println("\tNo of recipient object is " + recipients.size());
                    break;
                case 6:
                    isExit = true;
                    break;
            }
            System.out.println("=====================================================");
        }
        sendBirthDayWishes();
        System.out.println("Closing application...");
    }

    private static void addRecipient(String record) {
        Recipient recipient;
        LocalDate birthDayDate;
        String[] details = record.trim().split("\\s*:\\s*|\\s*,\\s*|\\s");
        details[0] = details[0].toLowerCase();
        switch (details[0]) {
            case "official":
                recipient = new Official(details[1], details[2], details[3]);
                break;
            case "office_friend":
                birthDayDate = LocalDate.parse(details[4], formatter);
                recipient = new OfficeFriend(details[1], details[2], details[3], birthDayDate);
                break;
            default:
                if (details.length == 4) {
                    //creating perosnal instance
                    birthDayDate = LocalDate.parse(details[3], formatter);
                    recipient = new Personal(details[1], details[2], birthDayDate);
                } else {
                    //creating personal instance with nickname as addition field
                    birthDayDate = LocalDate.parse(details[4], formatter);
                    recipient = new Personal(details[1], details[2], details[3], birthDayDate);
                }
                break;
        }

        //following method assures that there will be no duplicate objects
        if (recipients.add(recipient)) {         //if we add a duplicate object add method will return false
            FileHandler.write(record, "clientList.txt");
            Serializer.serialize(recipient, "recipientObjects.ser");
            if (recipient instanceof Wishable) {
                Wishable person = (Wishable) recipient;
                birthdayPersons.add(person);
                Serializer.serialize(person, "birthDayPersons.ser");
            }
            System.out.println("\tSuccessfully added...");
        } else {
            System.out.println("\tRecord already present !!!");
        }
    }

    private static void sendBirthDayWishes() {
        //for each year we are creating new textfile to store the emails of the person to which birthday wish has been sent
        String fileName = "birthDayWished_" + LocalDate.now().getYear() + ".txt";
        for (Wishable person : birthdayPersons) {
            LocalDate currentDate = LocalDate.now();
            if (person.checkBirthDay(currentDate) && !FileHandler.read(fileName).contains(person.getWisherEmail())) {
                System.out.println("\tSending birth day wish for " + person.getWisherName() + ".......");
                Email birthDayWish = new Email(person.getWisherEmail(), "Birth-Day-Wish", person.getBirthDayWishFormat());
                EmailSender.sendEmail(birthDayWish);
                FileHandler.write(person.getWisherEmail(), fileName);
            }
        }
    }


}
