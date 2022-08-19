package test.java.pojo;
import java.util.Objects;

// this class is used for deserialization
//posts{};
public class posts {
    public String firstName;
    public String lastName;
    public String city;
    public int zip;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        posts posts = (posts) o;

        if (zip != posts.zip) return false;
        if (!Objects.equals(firstName, posts.firstName)) return false;
        if (!Objects.equals(lastName, posts.lastName)) return false;
        return Objects.equals(city, posts.city);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + zip;
        return result;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public int getZip() {
        return zip;
    }

    public posts(String firstName, String lastName, String city, int zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.zip = zip;
    }


}
