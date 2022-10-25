import java.io.Serializable;
import java.util.Objects;

public class Recipient implements Serializable {
    private String name, email;

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return email.equals(recipient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
