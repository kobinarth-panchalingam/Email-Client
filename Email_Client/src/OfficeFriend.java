import java.time.LocalDate;

public class OfficeFriend extends Recipient implements Wishable {
    private LocalDate dob;
    private String designation;

    public OfficeFriend(String name, String email, String designation, LocalDate dob) {
        super(name, email);
        this.dob = dob;
        this.designation = designation;
    }


    @Override
    public boolean checkBirthDay(LocalDate date) {
        return (this.dob.getDayOfMonth() == date.getDayOfMonth() && this.dob.getMonth() == date.getMonth());
    }

    @Override
    public String getBirthDayWishFormat() {
        return "Wish you a happy birthday \n\t-Kobi";
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
