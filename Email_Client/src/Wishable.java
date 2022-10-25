import java.time.LocalDate;

public interface Wishable {
    boolean checkBirthDay(LocalDate date);
    String getBirthDayWishFormat();
    String getWisherName();
    String getWisherEmail();
}
