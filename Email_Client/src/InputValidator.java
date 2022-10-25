import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    public static boolean dateValidator(String strDate) {
        if (strDate.equals("")) {
            System.out.println("\tReturning to menu .....");
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            LocalDate.parse(strDate, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("\t"+ strDate + " is Invalid Date format");
            return false;
        }
        return true;
    }

    public static boolean emailDetailsValiator(String emailInput) {
        if (emailInput.equals("")) {
            System.out.println("\tReturning to menu .....");
            return false;
        }

        String[] emailDetails = emailInput.trim().split("\\s*,\\s*|\\s");
        if (emailDetails.length != 3) {
            System.out.println("\tNo of input arguments in incorrect");
            return false;
        }
        return emailValidator(emailDetails[0]);

    }

    public static boolean emailValidator(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        //searching for occurrences of regex
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("\tYour input has invalid email format");
            return false;
        }
    }

    public static boolean recordValidator(String record) {
        if (record.equals("")) {
            System.out.println("\tReturning to menu .....");
            return false;
        }
        String[] details = record.trim().split("\\s*:\\s*|\\s*,\\s*|\\s");
        String[] types = {"official","personal","office_friend"};

        if (!Arrays.stream(types).anyMatch(x -> x.equals(details[0].toLowerCase()))) {
            System.out.println("\tfirst argument didn't match the format correctly");
            return false;
        }

        if (details[0].equalsIgnoreCase("official")) {
            if (details.length != 4) {
                System.out.println("\tNo of input arguments is incorrect");
                return false;
            }
            return emailValidator(details[2]);
        }

        if (details[0].equalsIgnoreCase("office_friend")) {
            if (details.length != 5) {
                System.out.println("\tNo of input arguments is incorrect");
                return false;
            }
            return (emailValidator(details[2]) && dateValidator(details[4]));

        }

        //if type of record is personal
        if (details.length != 4 && details.length != 5) {
            System.out.println("\tNo of input arguments is incorrect");
            System.out.println(details.length);
            return false;
        } else {
            int n = details.length;
            return (emailValidator(details[n - 2]) && dateValidator(details[n - 1]));
        }
    }
}
