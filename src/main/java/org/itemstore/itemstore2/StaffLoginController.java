package org.itemstore.itemstore2;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StaffLoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label changedPasswordAlert;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private Label forgotPasswordLabel;

    @FXML
    private PasswordField hiddenPassword;

    @FXML
    private Label labelpassword;

    @FXML
    private Label labelusername;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginTitleCustomer;

    @FXML
    private Label nationalIDLabel;

    @FXML
    private TextField nationalIDTextBox;

    @FXML
    private Button newPasswordButtonSubmit;

    @FXML
    private Label newPasswordLabel;

    @FXML
    private TextField newPasswordTextBox;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private TextField shownPassword;

    @FXML
    private TextField username;

    private static HelloApplication mainController = new HelloApplication();
    private static StaffPanelController cpc = new StaffPanelController();
    private Staff staff;
//    private Staff staff;

    @FXML
    void changePassword(ActionEvent event) throws IOException {
        for (Staff staff : mainController.staffmembers) {
            if (staff.resetPassword(nationalIDTextBox.getText(), newPasswordTextBox.getText(), username.getText())) {
                BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    String[] parts = currentLine.split(",");
                    if (parts[2].equals(nationalIDTextBox.getText())) {
                        BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true));
                        bw.write(parts[0] + "," + newPasswordTextBox.getText() + "," + parts[2]);
                        bw.newLine();
                        bw.close();
                        br.close();
                        break;
                    }
                }
                changedPasswordAlert.textProperty().setValue("Password changed");
                changedPasswordAlert.setVisible(true);
                username.setVisible(true);
                hiddenPassword.setVisible(true);
                forgotPasswordLabel.setVisible(true);
                forgotPasswordButton.setVisible(true);
                labelusername.setVisible(true);
                labelpassword.setVisible(true);
                showPasswordCheckBox.setVisible(true);
                loginButton.setVisible(true);
                newPasswordButtonSubmit.setVisible(false);
                nationalIDLabel.setVisible(false);
                nationalIDTextBox.setVisible(false);
                newPasswordTextBox.setVisible(false);
                newPasswordLabel.setVisible(false);
                newPasswordButtonSubmit.setVisible(false);
                loginTitleCustomer.setVisible(true);
                forgotPasswordLabel.setVisible(false);
                forgotPasswordButton.setVisible(false);
            } else {
                changedPasswordAlert.setVisible(true);
                changedPasswordAlert.textProperty().setValue("Incorrect ID, Please try again!");
            }
        }
    }

    @FXML
    void forgotPasswordAttributesShow(ActionEvent event) {
        username.setVisible(false);
        hiddenPassword.setVisible(false);
        forgotPasswordLabel.setVisible(false);
        forgotPasswordButton.setVisible(false);
        labelusername.setVisible(false);
        labelpassword.setVisible(false);
        showPasswordCheckBox.setVisible(false);
        loginButton.setVisible(false);
        newPasswordButtonSubmit.setVisible(true);
        nationalIDLabel.setVisible(true);
        nationalIDTextBox.setVisible(true);
        newPasswordTextBox.setVisible(true);
        newPasswordLabel.setVisible(true);
        newPasswordButtonSubmit.setVisible(true);
        loginTitleCustomer.setVisible(false);
    }

    @FXML
    void loginProcess(ActionEvent event) throws IOException, NullPointerException, IndexOutOfBoundsException {
        for (Staff staff : mainController.staffmembers) {
            if (staff.authenticate(username.getText(), hiddenPassword.getText()) || staff.authenticate(username.getText(), shownPassword.getText())) {
                cpc.setStaff(staff);
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("staffPanel.fxml"));
                Parent root2 = loader2.load();
                Stage stage2 = (Stage) loginButton.getScene().getWindow();
                stage2.setScene(new Scene(root2));
                return;
            } else if (username.getText().equals(staff.user.getUsername())) {
                forgotPasswordLabel.setVisible(true);
                forgotPasswordButton.setVisible(true);
                changedPasswordAlert.setVisible(false);
            } else {
                changedPasswordAlert.setVisible(true);
                changedPasswordAlert.textProperty().setValue("User doesn't exist!");
            }
        }
    }

    @FXML
    void passwordVisibility(ActionEvent event) {
        if (showPasswordCheckBox.isSelected()) {
            shownPassword.setText(hiddenPassword.getText());
            shownPassword.setVisible(true);
            hiddenPassword.setVisible(false);
            return;
        }
        hiddenPassword.setText(shownPassword.getText());
        hiddenPassword.setVisible(true);
        shownPassword.setVisible(false);
    }
    @FXML
    private Button backButton;

    @FXML
    void backToMainPanel(ActionEvent event) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root2 = loader2.load();
        Stage stage2 = (Stage) backButton.getScene().getWindow();
        stage2.setScene(new Scene(root2));
    }

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'registerPanel.fxml'.";
        assert changedPasswordAlert != null : "fx:id=\"changedPasswordAlert\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert forgotPasswordButton != null : "fx:id=\"forgotPasswordButton\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert forgotPasswordLabel != null : "fx:id=\"forgotPasswordLabel\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert hiddenPassword != null : "fx:id=\"hiddenPassword\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert labelpassword != null : "fx:id=\"labelpassword\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert labelusername != null : "fx:id=\"labelusername\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert loginTitleCustomer != null : "fx:id=\"loginTitleCustomer\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert nationalIDLabel != null : "fx:id=\"nationalIDLabel\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert nationalIDTextBox != null : "fx:id=\"nationalIDTextBox\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert newPasswordButtonSubmit != null : "fx:id=\"newPasswordButtonSubmit\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert newPasswordLabel != null : "fx:id=\"newPasswordLabel\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert newPasswordTextBox != null : "fx:id=\"newPasswordTextBox\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert showPasswordCheckBox != null : "fx:id=\"showPasswordCheckBox\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert shownPassword != null : "fx:id=\"shownPassword\" was not injected: check your FXML file 'staffLogin.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'staffLogin.fxml'.";

    }

}
