package View_Controller;

import Model.CalendarData;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import utils.DBConnection;

public class AddCustomerController {
    public TextField IdField;
    public TextField NameField;
    public TextField AddressField;
    public TextField Address2Field;
    public TextField CityField;
    public TextField ZipCodeField;
    public TextField CountryField;
    public TextField PhoneField;
    public Button SaveButton;
    public Button CloseButton;

    // Close Add Customer window
    public void closeButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    public void IdHandler(ActionEvent actionEvent) {
    }

    // Customer data into DB
    public void saveButtonHandler(ActionEvent actionEvent) {
        Boolean error = false;
        int idCountry = 1;
        int idCity = 1;
        int idAddress = 1;
        int idCustomer = 1;

        // Check to see if all fields have values
        if(NameField.getText().isEmpty() || AddressField.getText().isEmpty() || Address2Field.getText().isEmpty() || CityField.getText().isEmpty() || ZipCodeField.getText().isEmpty() || CountryField.getText().isEmpty() || PhoneField.getText().isEmpty()) {
            Alert fillAllRequired = new Alert(Alert.AlertType.CONFIRMATION);
            fillAllRequired.initModality(Modality.NONE);
            fillAllRequired.setTitle("Fill All Required Fields");
            fillAllRequired.setHeaderText("Fill All Required Fields");
            fillAllRequired.setContentText("Please fill in all required fields");
            Optional<ButtonType> userChoice = fillAllRequired.showAndWait();
        }
        else {
            // Check against Countries in DB
            // If Country already in DB, use it's countryId. If not, then Insert a new entry and use that new countryId
            try {
                Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                String queryForCountry = "SELECT * FROM country WHERE country='" + CountryField.getText() + "'";
                ResultSet rs = dbConnectionStatement.executeQuery(queryForCountry);

                if(rs.next()) {
                    idCountry = rs.getInt("countryId");
                }
                else {
                    String queryAllCountries = "SELECT * FROM country";
                    ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCountries);

                    rs2.last();

                    // If there are entries in country table, set idCountry to be +1 of last entry's id
                    if(rs2.getRow() > 0) {
                        idCountry = rs2.getInt("countryId") + 1;
                    }

                    String insertNewCountry = "INSERT INTO country VALUES (" + idCountry + ",'" + CountryField.getText() + "','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                    dbConnectionStatement.executeUpdate(insertNewCountry);
                }
            }
            catch (SQLException e) {
                System.out.println("SQLException error: " + e.getMessage());
                error = true;
            }

            // Check against Cities in DB. If error from Country try/catch do not run
            if(!error) {
                try {
                    Statement dbConnectionStatement = DBConnection.getConnection().createStatement();

                    String queryForCityAndCountry = "SELECT * FROM city WHERE city='" + CityField.getText() + "' AND countryId='" + idCountry + "'";
                    ResultSet rs = dbConnectionStatement.executeQuery(queryForCityAndCountry);

                    // If statement returns an entry then no need to INSERT another entry but grab the cityId
                    if(rs.next()) {
                        idCity = rs.getInt("cityId");
                    }
                    //Else, INSERT a new entry
                    else {
                        String queryAllCities = "SELECT * FROM city";
                        ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCities);

                        rs2.last();

                        // If there are entries in city table, set idCity to be +1 of last entry's id
                        if(rs2.getRow() > 0) {
                            idCity = rs2.getInt("cityId") + 1;
                        }

                        String insertNewCity = "INSERT INTO city VALUES (" + idCity + ",'" + CityField.getText() + "'," + idCountry + ",'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                        dbConnectionStatement.executeUpdate(insertNewCity);
                    }
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                    error = true;
                }
            }

            // Check against Addresses in DB. If error from Country or City try/catch do not run
            if(!error) {
                try {
                    Statement dbConnectionStatement = DBConnection.getConnection().createStatement();

                    String queryForAddressAndCity = "SELECT * FROM address WHERE address='" + AddressField.getText() + "' AND address2='" + Address2Field.getText() + "' AND cityId='" + idCity + "'";
                    ResultSet rs = dbConnectionStatement.executeQuery(queryForAddressAndCity);

                    if(rs.next()) {
                        idAddress = rs.getInt("addressId");
                    }
                    else {
                        String queryAllAddresses = "SELECT * FROM address";
                        ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllAddresses);

                        rs2.last();

                        // If there are entries in address table, set idAddress to be +1 of last entry's id
                        if(rs2.getRow() > 0) {
                            idAddress = rs2.getInt("addressId") + 1;
                        }

                        String insertNewAddress = "INSERT INTO address VALUES (" + idAddress + ",'" + AddressField.getText() + "','" + Address2Field.getText() + "'," + idCity + ",'" + ZipCodeField.getText() + "','" + PhoneField.getText() + "','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                        dbConnectionStatement.executeUpdate(insertNewAddress);
                    }
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                    error = true;
                }
            }

            // Check against Customer in DB. If error from Address, Country, or City try/catch do not run
            if(!error) {
                try {
                    Statement dbConnectionStatement = DBConnection.getConnection().createStatement();

                    String queryForCustomerAndAddress = "SELECT * FROM customer WHERE customerName='" + NameField.getText() + "' AND addressId='" + idAddress + "'";
                    ResultSet rs = dbConnectionStatement.executeQuery(queryForCustomerAndAddress);

                    if(rs.next()) {
                        idCustomer = rs.getInt("customerId");
                    }
                    else {
                        String queryAllCustomer = "SELECT * FROM customer";
                        ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCustomer);

                        rs2.last();

                        // If there are entries in customer table, set idCustomer to be +1 of last entry's id
                        if(rs2.getRow() > 0) {
                            idCustomer = rs2.getInt("customerId") + 1;
                        }

                        String insertNewCustomer = "INSERT INTO customer VALUES (" + idCustomer + ",'" + NameField.getText() + "','" + idAddress + "',1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                        dbConnectionStatement.executeUpdate(insertNewCustomer);
                    }
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                    error = true;
                }
            }

            // Add new Customer object into ObservableList in CalendarData
            if(!error) {
                // Search through allCustomers and make sure there is not already a similar customer in DB
                ObservableList<Customer> allCustomers = CalendarData.getAllCustomers();
                Boolean customerExist = false;
                int currentCustomerId = idCustomer;

                for (Customer customer : allCustomers) {
                    if (currentCustomerId == customer.getId()) {
                        customerExist = true;
                    }
                }

                if(!customerExist) {
                    Customer newCustomer = new Customer(idCustomer, NameField.getText(), idAddress, 1, "2019-01-01 00:00:00", "test", "2019-01-01 00:00:00", "test");

                    CalendarData.addCustomer(newCustomer);

                    newCustomer.setAddress(AddressField.getText());
                }
            }

            // Close Add Customer Window after save
            Stage stage = (Stage) SaveButton.getScene().getWindow();
            stage.close();
        }
    }
}
