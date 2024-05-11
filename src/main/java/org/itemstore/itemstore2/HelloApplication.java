package org.itemstore.itemstore2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class HelloApplication extends Application {
    protected static ArrayList<Customer> customers = new ArrayList<Customer>();
    protected static ArrayList<Staff> staffmembers = new ArrayList<Staff>();
    protected static ArrayList<Item> items = new ArrayList<Item>();
    protected Customer customer;
    protected Staff staff;

    @Override
    public void start(Stage stage) throws IOException {
        setCustomer();
        setStaffmembers();
        setItems();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ItemStore");
        Image icon = new Image(getClass().getResourceAsStream("/shop.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
    public void stop() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("customers.txt"));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter("users.txt"));
        for (Customer customer : customers) {
            bw.write(String.format("%s,%s,%s,%s,%s", customer.getName(), customer.getEmail(), customer.getPhone(), customer.getID(), customer.getBalance()));
            bw.newLine();
            bw2.write(String.format("%s,%s,%s",customer.user.getUsername(),customer.user.getPassword(),customer.getID()));
            bw2.newLine();
        }
        bw.close();
        BufferedWriter bw3 = new BufferedWriter(new FileWriter("staffmembers.txt"));
        for (Staff staff2 : staffmembers) {
            bw3.write(String.format("%s,%s,%s,%s,%d,%s", staff2.getName(), staff2.getEmail(), staff2.getPhone(), staff2.getID(),staff2.getSalary(),staff2.getRole()));
            bw3.newLine();
            bw2.write(String.format("%s,%s,%s",staff2.user.getUsername(),staff2.user.getPassword(),staff2.getID()));
            bw2.newLine();
        }
        bw3.close();
        bw2.close();
    }

    public void setCustomer() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            customer = new Customer(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
            for (Customer customer2 : customers) {
                if (this.customer.getID().equals(customer2.getID())) {
                    this.customer = customer2;
                    customers.set(customers.indexOf(customer2), customer);
                }
            }
            customer.setName(parts[0]);
            customer.setEmail(parts[1]);
            customer.setPhone(parts[2]);
            customer.setID(parts[3]);
            customer.setBalance(Double.parseDouble(parts[4])); //1 //4
            BufferedReader reader2 = new BufferedReader(new FileReader("users.txt"));
            String line2;
            boolean hasAUser = false;
            while ((line2 = reader2.readLine()) != null) {
                String[] parts2 = line2.split(",");
                if (parts2[2].equals(customer.getID())) {
                    customer.user.setUsernameUniversity(parts2[0]);
                    customer.user.setPasswordUniversity(parts2[1]);
                    hasAUser = true;
                }
            }
            if (!(customers.contains(customer))) {
                customers.add(customer);
            }

        }
    }

    public void setStaffmembers() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("staffmembers.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            staff = new Staff("", "", "", "", 0, "");
            staff.setName(parts[0]);
            staff.setEmail(parts[1]);
            staff.setPhone(parts[2]);
            staff.setID(parts[3]);
            staff.setSalary(Integer.parseInt(parts[4]));
            staff.setRole(parts[5]);
            BufferedReader reader2 = new BufferedReader(new FileReader("users.txt"));
            String line2;
//            boolean hasAUser = false;
            while ((line2 = reader2.readLine()) != null) {
                String[] parts2 = line2.split(",");
                if (parts2[2].equals(staff.getID())) {
                    staff.user.setUsernameUniversity(parts2[0]);
                    staff.user.setPasswordUniversity(parts2[1]);
//                    hasAUser = true;
                }
            }
            staffmembers.add(staff);
        }
    }

    public void setItems() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("items.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            Item item = new Item(parts[0], Integer.parseInt(parts[1]), parts[2]);
            items.add(item);
        }
        BufferedReader reader2 = new BufferedReader(new FileReader("customerItems.txt"));
        String line2;
        while ((line2 = reader2.readLine()) != null) {
            String[] parts2 = line2.split(",");
            for (Customer customer : customers) {
                if (parts2[3].equals(customer.getID())) {
                    Item item = new Item(parts2[0], Integer.parseInt(parts2[1]), parts2[2]);
                    customer.addItem(item);
                }

            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}