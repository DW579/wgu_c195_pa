package View_Controller;

import Model.CalendarData;
import Model.Customer;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class CustomerController {

    public Button ExitButton;

    // Product Table View
    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, Integer> CustomerIdCol;
    @FXML
    private TableColumn<Customer, String> CustomerNameCol;
    @FXML
    private TableColumn<Customer, String> CustomerAddressCol;
    @FXML
    private TableColumn<Customer, String> CustomerCityCol;
    @FXML
    private TableColumn<Customer, String> CustomerStateCol;
    @FXML
    private TableColumn<Customer, String> CustomerPhoneCol;

    // Need a FilteredList
    FilteredList<Customer> filteredCustomersData = new FilteredList<>(CalendarData.getAllCustomers(), p -> true);

    // Close Customer Window
    public void exitButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    // Open Add Customer window
    public void addCustomerHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
        Parent rootAddCustomer = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(rootAddCustomer));
        stage.show();
    }

    public void updateCustomerHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
        Parent rootUpdateCustomer = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Update Customer");
        stage.setScene(new Scene(rootUpdateCustomer));
        stage.show();

        // Get ID of selected table row
        int customerId = CustomerTable.getSelectionModel().getSelectedItem().getId();

        // Pass customer id to UpdateCustomerController
        UpdateCustomerController updateCustomerController = fxmlLoader.getController();
        updateCustomerController.selectedCustomer(customerId);
    }

    public void deleteCustomerHandler(ActionEvent actionEvent) {
        int selectedCustomerId = CustomerTable.getSelectionModel().getSelectedItem().getId();

        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDelete.initModality(Modality.NONE);
        confirmDelete.setTitle("Delete?");
        confirmDelete.setHeaderText("Delete?");
        confirmDelete.setContentText("Are you sure you want to delete this Part?");
        Optional<ButtonType> userChoice = confirmDelete.showAndWait();

        // Get ID of selected customer in TableView
        if(userChoice.get() == ButtonType.OK) {
            // Get selected table row
            Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
            // Delete selectedPart
            CalendarData.deleteCustomer(selectedCustomer);
            // Update the Table view
            CustomerTable.setItems(CalendarData.getAllCustomers());

            // Delete from DB
            try {
                Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                String queryDeleteCustomer = "DELETE FROM customer WHERE customerId=" + selectedCustomerId + "";
                dbConnectionStatement.executeUpdate(queryDeleteCustomer);
            }
            catch (SQLException e) {
                System.out.println("SQLException error: " + e.getMessage());
            }
        }

    }

    @FXML
    private void initialize() {
        // Get all customer data from DB and insert into Customer ObservableList
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryAllCustomers = "SELECT * FROM customer";
            ResultSet rs = dbConnectionStatement.executeQuery(queryAllCustomers);

            while(rs.next()) {
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("addressId");
                int active = rs.getInt("active");
                String createDate = rs.getString("createDate");
                String createdBy = rs.getString("createdBy");
                String lastUpdate = rs.getString("lastUpdate");
                String lastUpdateBy = rs.getString("lastUpdateBy");

                Customer newCustomer = new Customer(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy);

                CalendarData.addCustomer(newCustomer);
                
                String address1 = null;

                try {
                    Statement dbCS = DBConnection.getConnection().createStatement();
                    String queryAddress = "SELECT * FROM address WHERE addressId=" + Integer.toString(addressId);
                    ResultSet rsAddress = dbCS.executeQuery(queryAddress);

                    rsAddress.next();

                    address1 = rsAddress.getString("address");

                    newCustomer.setAddress(address1);
                }

                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                }


            }
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        // Initialize and update Customer table
        CustomerIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        CustomerNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        CustomerAddressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        CustomerCityCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        CustomerStateCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        CustomerPhoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        CustomerTable.setItems(CalendarData.getAllCustomers());
    }
}
