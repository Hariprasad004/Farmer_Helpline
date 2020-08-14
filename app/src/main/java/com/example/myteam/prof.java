package com.example.myteam;

public class prof {
    private String name;
    private String age;
    private String contact;
    private  String addres;
   private  String PROFILE_PIC;

    public prof() {
    }

    public prof(String name, String age, String contact, String addres, String PROFILE_PIC) {
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.addres = addres;
        this.PROFILE_PIC = PROFILE_PIC;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getAge() {

        return age;
    }

    public void setAge(String age) {

        this.age = age;
    }

    public String getContact() {

        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddres() {

        return addres;
    }

    public void setAddres(String addres) {

        this.addres = addres;
    }

    public String getPROFILE_PIC() {
        return PROFILE_PIC;
    }

    public void setPROFILE_PIC(String PROFILE_PIC) {
        this.PROFILE_PIC = PROFILE_PIC;
    }
}
