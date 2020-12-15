package View_Controller;

import Model.Appointment;
import Model.CalendarData;
import Model.Customer;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateCustomerController {
    public TextField IdField;
    public TextField NameField;
    public TextField AddressField;
    public TextField CityField;
    public TextField PhoneField;
    public TextField CountryField;
    public TextField Address2Field;
    public TextField ZipCodeField;

    public Button SaveButton;
    public Button CloseButton;

    // Appointment Table View
    @FXML
    private TableView<Appointment> AppointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> AppointmentIDColumn;
    @FXML
    private TableColumn<Appointment, String> TitleColumn;
    @FXML
    private TableColumn<Appointment, String> TypeColumn;
    @FXML
    private TableColumn<Appointment, String> StartDateColumn;
    @FXML
    private TableColumn<Appointment, String> EndDateColumn;

    FilteredList<Appointment> filteredAppointmentsData = new FilteredList<>(CalendarData.getAllAppointments(), p -> true);

    public void selectedCustomer(int customerId) throws IOException {
        int addressId = 0;
        int cityId = 0;
        int countryId = 0;

        IdField.setText(Integer.toString(customerId));

        // Initialize and update Appointment table
        AppointmentIDColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        TitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        TypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        StartDateColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        EndDateColumn.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        // *** Lamda expression. This is for to filter the table view of appointments associated with the selected customer. ***
        AppointmentTableView.setItems(CalendarData.getAllAppointments().filtered(a -> a.getCustomerId() == customerId));

        // Look up customer data in customer table
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryForSelectedCustomer = "SELECT * FROM customer WHERE customerId=" + Integer.toString(customerId);
            ResultSet rs = dbConnectionStatement.executeQuery(queryForSelectedCustomer);

            rs.next();

            NameField.setText(rs.getString("customerName"));

            addressId = rs.getInt("addressId");
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        // Look up address data in address table
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryForSelectedAddress = "SELECT * FROM address WHERE addressId=" + Integer.toString(addressId);
            ResultSet rs = dbConnectionStatement.executeQuery(queryForSelectedAddress);

            rs.next();

            AddressField.setText(rs.getString("address"));
            Address2Field.setText(rs.getString("address2"));
            ZipCodeField.setText(rs.getString("postalCode"));
            PhoneField.setText(rs.getString("phone"));

            cityId = rs.getInt("cityId");
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        // Look up city data in city table
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryForSelectedCity = "SELECT * FROM city WHERE cityId=" + Integer.toString(cityId);
            ResultSet rs = dbConnectionStatement.executeQuery(queryForSelectedCity);

            rs.next();

            CityField.setText(rs.getString("city"));

            countryId = rs.getInt("countryId");
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        // Look up country data in country table
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryForSelectedCountry = "SELECT * FROM country WHERE countryId=" + Integer.toString(countryId);
            ResultSet rs = dbConnectionStatement.executeQuery(queryForSelectedCountry);

            rs.next();

            CountryField.setText(rs.getString("country"));
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
    }

    public void IdHandler(ActionEvent actionEvent) {
    }

    public void saveButtonHandler(ActionEvent actionEvent) {

        CalendarData.updateCustomer(Integer.parseInt(IdField.getText()), NameField.getText(), AddressField.getText(), Address2Field.getText(), CityField.getText(), ZipCodeField.getText(), CountryField.getText(), PhoneField.getText());

        Stage stage = (Stage) SaveButton.getScene().getWindow();
        stage.close();
    }

    public void closeButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
//        // Initialize and update Appointment table
//        AppointmentIDColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
//        TitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
//        TypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
//        StartDateColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
//        EndDateColumn.setCellValueFactory(cellData -> cellData.getValue().endProperty());
//        // *** Lamda expression. This is for to filter the table view of appointments associated with the selected customer. ***
//        AppointmentTableView.setItems(CalendarData.getAllAppointments().filtered(a -> a.getCustomerId() == Integer.parseInt(IdField.getText())));
    }
}
