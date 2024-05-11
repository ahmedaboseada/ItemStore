package org.itemstore.itemstore2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label WelcomeLabel;

    @FXML
    private Button backButton;

    @FXML
    private TextField email;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField nationalid;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phone;

    @FXML
    private Button registerButton;

    @FXML
    private TextField username;

    @FXML
    void backToMainPanel(ActionEvent event) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root2 = loader2.load();
        Stage stage2 = (Stage) backButton.getScene().getWindow();
        stage2.setScene(new Scene(root2));
    }

    boolean checkerName() {
        return firstname.getText().matches("^[a-zA-Z]+$") && lastname.getText().matches("^[a-zA-Z]+$");
    }

    boolean checkerEmail() {
        return email.getText().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    }

    boolean checkerPhone() {
        return phone.getText().matches("^010[0-9]{8}$") || phone.getText().matches("^011[0-9]{8}$") || phone.getText().matches("^012[0-9]{8}$") || phone.getText().matches("^015[0-9]{8}$");
    }

    boolean checkerPassword() {
        return password.getText().matches("^.{8,}$");
    }

    boolean checkerUsername() {
        return !username.getText().contains(" ");
    }

    boolean checkerNationalid() {
        return nationalid.getText().matches("^[0-9]{14}$");
    }

    boolean checkerNotNull() {
        if (firstname.getText().isEmpty() || lastname.getText().isEmpty() || phone.getText().isEmpty() || nationalid.getText().isEmpty() || password.getText().isEmpty() || username.getText().isEmpty() || email.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean checkerOfCorrectData() {
        if (!checkerName()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, Enter English character only for name");
            alert.showAndWait();
            return false;
        } else if (!checkerEmail()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter your email address correctly");
            alert.showAndWait();
            return false;
        } else if (!checkerPhone()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter your phone number correctly");
            alert.showAndWait();
            return false;
        } else if (!checkerNationalid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter your national id number correctly");
            alert.showAndWait();
            return false;
        } else if (!checkerUsername()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter your username correctly");
            alert.showAndWait();
            return false;
        } else if (!checkerPassword()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter your password correctly");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Customer Registration");
        alert.setHeaderText(null);
        alert.setContentText("Complete registration");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            if (!checkerNotNull()) return;
            if (!checkerOfCorrectData()) return;
//            if (!checkerName() || !checkerEmail() || !checkerPhone() || !checkerPassword() || !checkerUsername() || !checkerNationalid() || !checkerPassword() || !checkerUsername()) {
//                Alert alert3 = new Alert(Alert.AlertType.ERROR);
//                alert3.setTitle("Error");
//                alert3.setHeaderText(null);
//                alert3.setContentText("Please enter correct data");
//                alert3.showAndWait();
//                return;
//            }
            Customer customer = new Customer(String.format("%s %s", firstname.getText(), lastname.getText()), email.getText(), phone.getText(), nationalid.getText(), 0);
            customer.user.setUsernameUniversity(username.getText());
            customer.user.setPasswordUniversity(password.getText());
            HelloApplication.customers.add(customer);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("customers.txt", true));
            bufferedWriter.write(String.format("%s,%s,%s,%s,%s", customer.getName(), customer.getEmail(), customer.getPhone(), customer.getID(), customer.getBalance()));
            bufferedWriter.newLine();
            bufferedWriter.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true));
            bw.write(String.format("%s,%s,%s", customer.user.getUsername(), customer.user.getPassword(), customer.getID()));
            bw.newLine();
            bw.close();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Customer Registration");
            alert2.setHeaderText(null);
            alert2.setContentText("Registered Successfully");
            alert2.showAndWait();
            backToMainPanel(event);
        } else return;
    }

    @FXML
    void initialize() {
        assert WelcomeLabel != null : "fx:id=\"WelcomeLabel\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert firstname != null : "fx:id=\"firstname\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert lastname != null : "fx:id=\"lastname\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert nationalid != null : "fx:id=\"nationalid\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'registerPanel.fxml'.";

    }

}
