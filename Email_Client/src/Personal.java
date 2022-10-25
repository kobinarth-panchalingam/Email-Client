import java.time.LocalDate;

public class Personal extends Recipient implements Wishable {
    private String nickName;
    private LocalDate dob;

    public Personal(String name, String nickName, String email, LocalDate dob) {
        super(name, email);
        this.dob = dob;
        this.nickName = nickName;
    }

    public Personal(String name, String email, LocalDate dob) {
        super(name, email);
        this.dob = dob;
    }

    @Override
    public boolean checkBirthDay(LocalDate date) {
        return this.dob.getDayOfMonth() == date.getDayOfMonth() && this.dob.getMonth() == date.getMonth();
    }

    @Override
    public String getBirthDayWishFormat() {
        return "Hugs and love on your birthday\n\t-Kobi";
    }

    @Override
    public String getWisherName() {
        return this.getName();
    }

    @Override
    public String getWisherEmail() {
        return this.getEmail();
    }
}
