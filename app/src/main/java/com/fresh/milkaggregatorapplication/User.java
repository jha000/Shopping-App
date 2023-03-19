package com.fresh.milkaggregatorapplication;


public class User {

    String courseName;
    String courseDescription;
    String courseDuration;
    String namett;
    String phonett;
    String addresstt;


    User ()
    {

    }

    public User(String courseName, String courseDescription, String courseDuration, String namett, String phonett, String addresstt) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration;
        this.namett = namett;
        this.phonett = phonett;
        this.addresstt = addresstt;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getNamett() {
        return namett;
    }

    public void setNamett(String namett) {
        this.namett = namett;
    }

    public String getPhonett() {
        return phonett;
    }

    public void setPhonett(String phonett) {
        this.phonett = phonett;
    }

    public String getAddresstt() {
        return addresstt;
    }

    public void setAddresstt(String addresstt) {
        this.addresstt = addresstt;
    }
}
