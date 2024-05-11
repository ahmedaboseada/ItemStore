package org.itemstore.itemstore2;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerPanelController {
    protected static Customer customer;
    private static final ProfilePanelController cpc = new ProfilePanelController();

    public void setCustomer(Customer customer) {
        CustomerPanelController.customer = customer;
    }

    @FXML
    private Button logoutButton;

    @FXML
    void logout(ActionEvent event) throws IOException {
        customer = null;
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root2 = loader2.load();
        Stage stage2 = (Stage) logoutButton.getScene().getWindow();
        stage2.setScene(new Scene(root2));
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    protected static ListView<Item> menuList = new ListView<>();

    @FXML
    protected TableView<Item> itemsTable = new TableView<>();
    //    @FXML
//    protected TableColumn<Item, Integer> numCol = new TableColumn<>();
    @FXML
    protected TableColumn<Item, String> nameCol = new TableColumn<>();


    @FXML
    protected TableColumn<Item, Integer> priceCol = new TableColumn<>();
    @FXML
    protected TableColumn<Item, String> descriptionCol = new TableColumn<>();

    @FXML
    private Button profileButton;

    @FXML
    private Label welcomeLabelMessage;

    @FXML
    void showProfileMenu(ActionEvent event) throws IOException, NullPointerException, IndexOutOfBoundsException {
        cpc.setCustomer(customer);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profilePanel.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        return;
    }

    @FXML
    private Button addToCartButton;

    @FXML
    private Button buyButton;

    @FXML
    private ChoiceBox<Item> buyingChoiceBox;

    @FXML
    private ListView<Item> cart;

    @FXML
    void addItemToItemsList(ActionEvent event) {
        if (buyingChoiceBox.getSelectionModel().getSelectedItem() != null) {
            cart.getItems().add(buyingChoiceBox.getSelectionModel().getSelectedItem());
            removeButton.setVisible(true);
            removeLabel.setVisible(true);
            removeItemChoiceBox.setVisible(true);
            ObservableList<Item> items = FXCollections.observableArrayList();
            items.add(buyingChoiceBox.getSelectionModel().getSelectedItem());
            cartTable.setItems(cart.getItems());
            int sum = 0;
            for (Item i : cart.getItems()) {
                sum += i.getPrice();
            }
            totalPriceLabel.setText("Total: $" + sum);
        }
    }

    @FXML
    void buyAction(ActionEvent event) throws IOException {
        if (cart.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("No items in your cart");
            alert.showAndWait();
            return;
        }
        int sum = 0;
        for (Item i : cart.getItems()) {
            sum += i.getPrice();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to buy this item?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (sum > customer.getBalance()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Warning");
                alert2.setHeaderText(null);
                alert2.setContentText("You have not enough funds");
                alert2.showAndWait();
            } else {
                for (Item i : cart.getItems()) {
                    customer.addItem(i);
                }
                customer.setBalance(customer.getBalance() - sum);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Information");
                alert1.setHeaderText(null);
                alert1.setContentText("You have successfully buy this item");
                alert1.showAndWait();
            }
            cart.getItems().clear();
            removeButton.setVisible(false);
            removeLabel.setVisible(false);
            removeItemChoiceBox.setVisible(false);
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("customers.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                BufferedWriter writer;
                writer = new BufferedWriter(new FileWriter("customers.txt", true));
//                    writer.flush();
                writer.newLine();
                writer.append(String.format("%s,%s,%s,%s,%s", customer.getName(), customer.getEmail(), customer.getPhone(), customer.getID(), customer.getBalance()));
                writer.close();
                reader.close();
                break;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter("customerItems.txt", true));
            for (Item item : customer.getItems()) {
                bw.write(String.format("%s,%d,%s,%s", item.getName(), item.getPrice(), item.getDescription(), customer.getID()));
                bw.newLine();
            }
            bw.close();
            buyingChoiceBox.getSelectionModel().clearSelection();
            totalPriceLabel.setText("Total: $0");

        } else {
            return;
        }

    }

    @FXML
    private Button removeButton;

    @FXML
    private ChoiceBox<Item> removeItemChoiceBox;

    @FXML
    private Label removeLabel;

    @FXML
    void removeItemFromCart(ActionEvent event) {
        if (removeItemChoiceBox.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to remove this item from cart?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                cart.getItems().remove(removeItemChoiceBox.getSelectionModel().getSelectedItem());
                if (cart.getItems().size() == 0) {
                    removeButton.setVisible(false);
                    removeLabel.setVisible(false);
                    removeItemChoiceBox.setVisible(false);
                    removeItemChoiceBox.getSelectionModel().clearSelection();
                }
                int sum=0;
                for (Item item : cartTable.getItems()){
                    sum+= item.getPrice();
                }
                totalPriceLabel.setText("Total: $"+sum);
                removeItemChoiceBox.getSelectionModel().select(0);
            }
        }

    }

    @FXML
    private Label totalPriceLabel;
    @FXML
    private TableColumn<Item, String> productNameCol;

    @FXML
    private TableColumn<Item, Integer> productPriceCol;
    @FXML
    private TableView<Item> cartTable;

    @FXML
    void initialize() {
        assert addToCartButton != null : "fx:id=\"addToCartButton\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert buyButton != null : "fx:id=\"buyButton\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert buyingChoiceBox != null : "fx:id=\"buyingChoiceBox\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert cart != null : "fx:id=\"cart\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert cartTable != null : "fx:id=\"cartTable\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert descriptionCol != null : "fx:id=\"descriptionCol\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert itemsTable != null : "fx:id=\"itemsTable\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert menuList != null : "fx:id=\"menuList\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert priceCol != null : "fx:id=\"priceCol\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert productNameCol != null : "fx:id=\"productNameCol\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert productPriceCol != null : "fx:id=\"productPriceCol\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert profileButton != null : "fx:id=\"profileButton\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert removeItemChoiceBox != null : "fx:id=\"removeItemChoiceBox\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert removeLabel != null : "fx:id=\"removeLabel\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert totalPriceLabel != null : "fx:id=\"totalPriceLabel\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        assert welcomeLabelMessage != null : "fx:id=\"welcomeLabelMessage\" was not injected: check your FXML file 'CustomerPanel.fxml'.";
        welcomeLabelMessage.setText("Welcome " + customer.getName() + "!");
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Price"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
        ObservableList<Item> observableList;
        observableList = FXCollections.observableArrayList();
        for (Item item : HelloApplication.items) {
            observableList.add(item);
        }
        itemsTable.setItems(observableList);
        for (Item item : HelloApplication.items) {
            buyingChoiceBox.getItems().add(item);
        }
        removeItemChoiceBox.setItems(cart.getItems());
        productNameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Price"));
        int sum = 0;
        for (Item item : cart.getItems()) {
            sum += item.getPrice();
        }
        totalPriceLabel.setText("Total: $0");

    }
}
