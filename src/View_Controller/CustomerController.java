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

    // Customer Table View
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

    public void addAppointmentHandler(ActionEvent actionEvent) throws IOException {

        if(CustomerTable.getSelectionModel().isEmpty()) {
            Alert noSelection = new Alert(Alert.AlertType.CONFIRMATION);
            noSelection.initModality(Modality.NONE);
            noSelection.setTitle("Please Select");
            noSelection.setHeaderText("Please Select");
            noSelection.setContentText("Please select a customer from the table");
            Optional<ButtonType> userChoice = noSelection.showAndWait();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
            Parent rootAddAppointment = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Add Appointment");
            stage.setScene(new Scene(rootAddAppointment));
            stage.show();

            // Get ID of selected table row
            int customerId = CustomerTable.getSelectionModel().getSelectedItem().getId();

            // Pass customer id to UpdateCustomerController
            AddAppointmentController addAppointmentController = fxmlLoader.getController();
            addAppointmentController.selectedCustomer(customerId);
        }


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
        // Initialize and update Customer table
        CustomerIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        CustomerNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        CustomerAddressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        CustomerCityCol.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        CustomerStateCol.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        CustomerPhoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        CustomerTable.setItems(CalendarData.getAllCustomers());
    }
}
