package org.itemstore.itemstore2;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProfilePanelController {
    private static Customer customer;
    private static Staff staff;
    private static final ProfilePanelController cpc = new ProfilePanelController();

    public void setCustomer(Customer customer) {
        ProfilePanelController.customer = customer;
    }

    public void setStaff(Staff staff) {
        ProfilePanelController.staff = staff;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField balanceText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField newBalanceTextBox;

    @FXML
    private TextField phoneText;

    @FXML
    private Label rechargeAmountLabel;

    @FXML
    private Button rechargeButton;

    @FXML
    private Button rechargeShowAttributesBUtton;

    @FXML
    private Button returnToMainButton;

    @FXML
    private Label paymentMessageLabel;

    @FXML
    private Label welcomeLabel;
    private boolean checkRechargeBalance(){
        if (newBalanceTextBox.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter an amount to recharge your balance");
            alert.showAndWait();
            return false;
        } else if (newBalanceTextBox.getText().matches("^[0-9]+$")) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid amount to recharge your balance");
        alert.showAndWait();
        return false;
    }
    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        if (staff == null) {
            customer=null;
            staff=null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerPanel.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) returnToMainButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            return;
        } else if (customer==null){
            staff=null;
            customer=null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffPanel.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) returnToMainButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            return;
        }
    }

    @FXML
    void setNewBalance(ActionEvent event) throws IOException {
        if (staff == null) {
            if (!checkRechargeBalance()) return;
            customer.rechargeCredits(Double.parseDouble(newBalanceTextBox.getText()));
            balanceText.setText(String.valueOf(customer.getBalance()));
            paymentMessageLabel.setVisible(true);
            paymentMessageLabel.setText("Payment Successful");
            rechargeAmountLabel.setVisible(false);
            newBalanceTextBox.setVisible(false);
            rechargeButton.setVisible(false);
            newBalanceTextBox.setText("");
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("customers.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                BufferedWriter writer;
                writer = new BufferedWriter(new FileWriter("customers.txt", true));
//                    writer.flush();
                writer.append(String.format("%s,%s,%s,%s,%s", customer.getName(), customer.getEmail(), customer.getPhone(), customer.getID(), customer.getBalance()));
                writer.newLine();
                writer.close();
                reader.close();
                break;
            }
        }

    }

    @FXML
    void showRechargeAttributes(ActionEvent event) throws IOException {
        if (staff == null) {
            rechargeShowAttributesBUtton.setVisible(false);
            rechargeAmountLabel.setVisible(true);
            rechargeButton.setVisible(true);
            newBalanceTextBox.setVisible(true);
        }

    }

    @FXML
    private Label purchasedLabel;

    @FXML
    private ListView<Item> purchasedList;
    @FXML
    private TableColumn<Item, Integer> price;

    @FXML
    private TableView<Item> purchaseTable;
    @FXML
    private TableColumn<Item, String> Name;
    @FXML
    private Label roleLabel;

    @FXML
    private TextField roleText;
    @FXML
    private Label balanceOrSalaryLabel;

    @FXML
    void initialize() {
        assert Name != null : "fx:id=\"Name\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert balanceOrSalaryLabel != null : "fx:id=\"balanceOrSalaryLabel\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert balanceText != null : "fx:id=\"balanceText\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert emailText != null : "fx:id=\"emailText\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert idText != null : "fx:id=\"idText\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert nameText != null : "fx:id=\"nameText\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert newBalanceTextBox != null : "fx:id=\"newBalanceTextBox\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert paymentMessageLabel != null : "fx:id=\"paymentMessageLabel\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert phoneText != null : "fx:id=\"phoneText\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert purchaseTable != null : "fx:id=\"purchaseTable\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert purchasedLabel != null : "fx:id=\"purchasedLabel\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert purchasedList != null : "fx:id=\"purchasedList\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert rechargeAmountLabel != null : "fx:id=\"rechargeAmountLabel\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert rechargeButton != null : "fx:id=\"rechargeButton\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert rechargeShowAttributesBUtton != null : "fx:id=\"rechargeShowAttributesBUtton\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert returnToMainButton != null : "fx:id=\"returnToMainButton\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert roleLabel != null : "fx:id=\"roleLabel\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert roleText != null : "fx:id=\"roleText\" was not injected: check your FXML file 'profilePanel.fxml'.";
        assert welcomeLabel != null : "fx:id=\"welcomeLabel\" was not injected: check your FXML file 'profilePanel.fxml'.";

        if (staff == null) {
            purchasedLabel.setVisible(true);
            balanceText.setText(String.valueOf(customer.getBalance()));
            emailText.setText(customer.getEmail());
            idText.setText(customer.getID());
            nameText.setText(customer.getName());
            phoneText.setText(customer.getPhone());
            welcomeLabel.setText("Welcome " + customer.getName());
            Name.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
            price.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Price"));
            ObservableList<Item> observableList;
            observableList = FXCollections.observableArrayList();
            for (Item item : customer.getItems()) {
                purchasedList.getItems().add(item);
                observableList.add(item);
            }
            purchaseTable.setItems(observableList);
            rechargeShowAttributesBUtton.setVisible(true);
            purchaseTable.setVisible(true);
        } else if (customer==null){
            balanceText.setText(String.valueOf(staff.getSalary()));
            emailText.setText(staff.getEmail());
            idText.setText(staff.getID());
            nameText.setText(staff.getName());
            phoneText.setText(staff.getPhone());
            welcomeLabel.setText("Welcome " + staff.getName());
            balanceOrSalaryLabel.setText("Salary:");
            roleLabel.setVisible(true);
            roleText.setVisible(true);
            roleText.setText(staff.getRole());
        }
    }

}
