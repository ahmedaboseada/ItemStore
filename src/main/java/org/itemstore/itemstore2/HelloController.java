package org.itemstore.itemstore2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button customerLogin;

    @FXML
    private Button staffLogin;

    @FXML
    private Label welcomelabel;

    @FXML
    void toCustomerLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customerLogin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) customerLogin.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    void toRegisterPanel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registerPanel.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) customerLogin.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void toStaffLogin(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("staffLogin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) customerLogin.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void initialize() {
        assert customerLogin != null : "fx:id=\"customerLogin\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert staffLogin != null : "fx:id=\"staffLogin\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert welcomelabel != null : "fx:id=\"welcomelabel\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

}
