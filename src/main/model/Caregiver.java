package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Contains information pertaining to caregiver of child on child registry/attendance sheet
public class Caregiver implements Writable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Caregiver caregiver = (Caregiver) o;
        return Objects.equals(fullName, caregiver.fullName) && Objects.equals(phoneNum, caregiver.phoneNum) && Objects.equals(email, caregiver.email);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("fullName", fullName);
        json.put("phoneNum", phoneNum);
        json.put("email", email);
        return json;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getPhoneNum() {
        return phoneNum;
    }

    public String getEmail() {
        return email;
    }

}
