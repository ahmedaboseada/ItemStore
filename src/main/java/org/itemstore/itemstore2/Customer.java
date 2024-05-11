package org.itemstore.itemstore2;

import java.util.ArrayList;
import java.util.Objects;

public class Customer extends Person {
    protected User user;
    private ArrayList<Item> items;
    private double balance;

    public Customer(String name, String email, String phone, String ID,double balance) {
        super(name, email, phone, ID);
        user = new User();
        items = new ArrayList<>();
        this.balance = balance;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public double getBalance() {
        return balance;
    }
    public void rechargeCredits(double balance) {
        this.balance += balance;
    }
    public boolean buyItems(){
        int sum=0;
        for(Item item : items){
            sum+=item.getPrice();
        }
        if(sum>balance){
            //insufficient balance message to Label
            return false;
        }else{
            //payment accepted
            //Thank message
            balance-=sum;
            return true;
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", ID='" + ID + '\'' +
                ", balance=" + balance +
                ", user=" + user.getUsername() +
                '}';
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean resetPassword(String ID, String newPassword, String username) {

        if (ID.equals(this.getID()) && Objects.equals(username, this.user.getUsername())) {
            user.setPasswordUniversity(newPassword);
            return true;
        }
        return false;
    }
}
