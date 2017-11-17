package com.cooksys.tweetapi.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Profile {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    public Profile() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean firstNameIsNull() {
        return this.firstName == null;
    }

    public boolean lastNameIsNull() {
        return this.lastName == null;
    }

    public boolean emailIsNull() {
        return this.email == null;
    }

    public boolean phoneIsNull() {
        return this.phone == null;
    }

    public void merge(Profile that) {
        if (!that.firstNameIsNull())
            this.firstName = that.getFirstName();
        if (!that.lastNameIsNull())
            this.lastName = that.getLastName();
        if (!that.emailIsNull())
            this.email = that.getEmail();
        if (!that.phoneIsNull())
            this.phone = that.getPhone();
    }

    public boolean isValid() {
        return email != null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Profile other = (Profile) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Profile [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone
                + "]";
    }


}
