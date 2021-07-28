package instances;

import javax.annotation.Nonnull;

/**
 * Class which contains data about one applicant
 */
public class Applicant {

    //constructors
    public Applicant() {
        this.status = Status.ON_HOLD;
    }

    public Applicant(@Nonnull String name, @Nonnull String email,
                     @Nonnull String phoneNumber, @Nonnull String address,
                     @Nonnull Status status, @Nonnull String course) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.courseName = course;
    }

    //getters,setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String number) { this.phoneNumber = number; }   //TO DO: check for currect phone number format

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public String getCourse() {
        return courseName;
    }

    public void setCourse(String course) {
        this.courseName = course;
    }

    //members
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Status status;
    private String courseName;       //applicant can be assigned to only one course
}
