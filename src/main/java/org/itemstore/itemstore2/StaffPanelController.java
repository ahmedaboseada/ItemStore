package org.itemstore.itemstore2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class StaffPanelController {
    private static HelloApplication mainController = new HelloApplication();
    protected static Staff staff;
    private static final ProfilePanelController ppc = new ProfilePanelController();
    private static final ProfilePanelController cpc = new ProfilePanelController();

    public void setStaff(Staff staff) {
        StaffPanelController.staff = staff;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label WelcomeLabel;

    @FXML
    private Button addItemButton;

    @FXML
    private TextField addItemNameText;

    @FXML
    private TextField addItemPriceText;

    @FXML
    private Button addItemShowAttributesBUtton;

    @FXML
    private TextField addItemdesText;

    @FXML
    private ChoiceBox<Item> chooseItemChoicebox;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nameItemLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Button profileButton;

    @FXML
    private Button removeButton;
    private final ObservableList<Item> observableList = FXCollections.observableArrayList();
    private int num = 0;
    private boolean checkName(){
        if (addItemNameText.getText().matches("^[a-zA-Z0-9]{2,}$")){
            return true;
        } else if (addItemNameText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a name for the item");
            alert.showAndWait();
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid item name");
        alert.showAndWait();
        return false;
    }
    private boolean checkDescription(){
        if (addItemdesText.getText().matches("^[a-zA-Z0-9'.,]{2,}$")){
            return true;
        } else if (addItemdesText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a description for the item");
            alert.showAndWait();
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid description");
        alert.showAndWait();
        return false;
    }
    private boolean checkPrice(){
        if (addItemPriceText.getText().matches("^[0-9]+$")){
            return true;
        } else if (addItemPriceText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a price for the item");
            alert.showAndWait();
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid price");
        alert.showAndWait();
        return false;
    }
    @FXML
    void addItem(ActionEvent event) throws NumberFormatException, IOException, NullPointerException {
        if (!checkName()||!checkDescription()||!checkPrice()) return;
        num++;
        Item item = new Item(addItemNameText.getText(), Integer.parseInt(addItemPriceText.getText()), addItemdesText.getText());
        HelloApplication.items.add(item);
        int i = HelloApplication.items.indexOf(item);
        chooseItemChoicebox.getItems().add(HelloApplication.items.get(i));
        observableList.add(HelloApplication.items.get(i));
        BufferedWriter bw = new BufferedWriter(new FileWriter("items.txt", true));
        bw.write(String.format("%s,%d,%s", item.getName(), item.getPrice(), item.getDescription()));
        bw.newLine();
        bw.close();
        chooseItemChoicebox.getSelectionModel().select(i);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("Item added");
        alert.showAndWait();
    }


    @FXML
    void addItemShowAttributes(ActionEvent event) {
        nameItemLabel.setVisible(true);
        priceLabel.setVisible(true);
        descriptionLabel.setVisible(true);
        addItemNameText.setVisible(true);
        addItemPriceText.setVisible(true);
        addItemdesText.setVisible(true);
        addItemButton.setVisible(true);
    }

    @FXML
    private Label chooseItemLabel;

    @FXML
    void removeItemShowAttributes(ActionEvent event) {
        chooseItemChoicebox.setVisible(true);
        chooseItemLabel.setVisible(true);
        removeButton.setVisible(true);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        staff = null;
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root2 = loader2.load();
        Stage stage2 = (Stage) logoutButton.getScene().getWindow();
        stage2.setScene(new Scene(root2));
    }

    @FXML
    void removeItem(ActionEvent event) throws IOException {
        HelloApplication.items.remove(chooseItemChoicebox.getSelectionModel().getSelectedItem());
        chooseItemChoicebox.getItems().remove(chooseItemChoicebox.getSelectionModel().getSelectedItem());
        BufferedWriter bw = new BufferedWriter(new FileWriter("items.txt"));
        for (Item item : HelloApplication.items) {
            bw.write(String.format("%s,%d,%s", item.getName(), item.getPrice(), item.getDescription()));
            bw.newLine();
        }
        bw.close();
        chooseItemChoicebox.getSelectionModel().clearSelection();
    }

    @FXML
    private Button removeItemsShowAttributes;

    @FXML
    void toProfile(ActionEvent event) throws IOException{
        cpc.setStaff(staff);
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("profilePanel.fxml"));
        Parent root2 = loader2.load();
        Stage stage2 = (Stage) profileButton.getScene().getWindow();
        stage2.setScene(new Scene(root2));
    }

    @FXML
    void initialize() {
        assert WelcomeLabel != null : "fx:id=\"WelcomeLabel\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert addItemButton != null : "fx:id=\"addItemButton\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert addItemNameText != null : "fx:id=\"addItemNameText\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert addItemPriceText != null : "fx:id=\"addItemPriceText\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert addItemShowAttributesBUtton != null : "fx:id=\"addItemShowAttributesBUtton\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert addItemdesText != null : "fx:id=\"addItemdesText\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert chooseItemChoicebox != null : "fx:id=\"chooseItemChoicebox\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert chooseItemLabel != null : "fx:id=\"chooseItemLabel\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert nameItemLabel != null : "fx:id=\"nameItemLabel\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert priceLabel != null : "fx:id=\"priceLabel\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert profileButton != null : "fx:id=\"profileButton\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'staffPanel.fxml'.";
        assert removeItemsShowAttributes != null : "fx:id=\"removeItemsShowAttributes\" was not injected: check your FXML file 'staffPanel.fxml'.";
        WelcomeLabel.setText(String.format("Welcome to the Staff Panel, %s!", staff.getName()));
        for (Item i : HelloApplication.items) {
            chooseItemChoicebox.getItems().add(i);
        }
    }

}

