package org.itemstore.itemstore2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class Person {
    //    protected Gender
    protected String name;
    protected String email;
    protected String phone;
    protected String ID;


    public Person(String name, String email, String phone, String ID) {
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) >= 65 && name.charAt(i) <= 90 || name.charAt(i) >= 97 && name.charAt(i) <= 122) {
                this.name = name;
            } else {

            }
        }

        if ((!(phone.startsWith("010", 0)) || phone.length() != 11) || (!(phone.startsWith("011", 0)) || phone.length() != 11) || (!(phone.startsWith("012", 0)) || phone.length() != 11) || (!(phone.startsWith("015", 0)) || phone.length() != 11)) {
            if (phone.length() != 11) {

            } else if ((phone.startsWith("010"))) {
                this.phone = phone;
            } else if ((phone.startsWith("011"))) {
                this.phone = phone;
            } else if ((phone.startsWith("012"))) {
                this.phone = phone;
            } else if ((phone.startsWith("015"))) {
                this.phone = phone;
            }
        }


        //check id at users file

        final String IDRegex = "\\d+"; //
        if (!ID.matches(IDRegex) || ID.length() != 14) {
            if (ID.length() != 14) {

            } else if (!ID.matches(IDRegex)) {

            }
        } else {
            this.ID = ID;
        }



        int indexOfDot = email.length() - 4;
//"1ahmed.@#$"
        if (!(email.contains("@")) || !(email.charAt(indexOfDot) == '.')) {
            if (!email.contains("@")) {

            } else if (!(email.charAt(indexOfDot) == '.')) { //.com // aa@gmail.com

            }
        } else {
            this.email = email;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Person{"
                + "name='" + name + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", National ID='" + ID + '\''
                + '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    abstract boolean authenticate(String username, String password);
}
