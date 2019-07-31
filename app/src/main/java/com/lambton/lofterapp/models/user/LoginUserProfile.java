package com.lambton.lofterapp.models.user;

public class LoginUserProfile {
    private int _id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String imgPath;

    public LoginUserProfile() {
    }

    public LoginUserProfile(String firstName, String lastName, String imgPath,
                            String email, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgPath = imgPath;
        this.email = email;
        this.contact = contact;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
