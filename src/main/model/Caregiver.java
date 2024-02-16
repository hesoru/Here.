package model;

// contains information pertaining to caregiver of child on attendance sheet
public class Caregiver {

    private final String fullName;
    private final Long phoneNum;
    private final String email;

    // REQUIRES: fullName is first and last name separated by a space, phoneNum is 10 digits
    //           without spaces, and email is not an empty string.
    // EFFECTS: Creates a caregiver with a full name, phone number, and email.
    public Caregiver(String fullName, Long phoneNum, String email) {
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public String getCaregiverFullName() {
        return fullName;
    }

    public Long getCaregiverPhoneNum() {
        return phoneNum;
    }

    public String getCaregiverEmail() {
        return email;
    }

}
