package org.itemstore.itemstore2;

import java.util.Objects;

public class Staff extends Person {
    protected User user;
    private int salary;
    private String role;


    public Staff(String name, String email, String phone, String ID,int salary, String role) {
        super(name, email, phone, ID);
        user = new User();
        this.salary = salary;
        this.role = role;
    }

    public int getSalary() {
        return salary;
    }

    public String getRole() {
        return role;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean resetPassword(String ID,String newPassword,String username) {
//        String username;
//        username = in.next();
//        if (username.equals(user.getUsername())) {

        if (ID.equals(this.getID())&& Objects.equals(username, this.user.getUsername())) {
            user.setPasswordUniversity(newPassword);
            return true;
        }else {
            return false;
        }
    }
}
//}

