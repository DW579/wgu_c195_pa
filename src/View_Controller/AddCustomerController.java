package View_Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
            System.out.println("Not all required fields are filled out");
        }
        else {
            // Check against Countries in DB
            // If Country already in DB, use it's countryId. If not, then Insert a new entry and use that new countryId
            try {
                Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                String queryForCountry = "SELECT * FROM country WHERE country='" + CountryField.getText() + "'";
                ResultSet rs = dbConnectionStatement.executeQuery(queryForCountry);

                if(rs.next()) {
                    System.out.println("There is a Country entry");

                    idCountry = rs.getInt("countryId");

                    System.out.println("idCountry = " + Integer.toString(idCountry));
                }
                else {
                    System.out.println("No Country entry");

                    String queryAllCountries = "SELECT * FROM country";
                    ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCountries);

                    rs2.last();

                    // If there are entries in country table, set idCountry to be +1 of last entry's id
                    if(rs2.getRow() > 0) {
                        idCountry = rs2.getInt("countryId") + 1;
                    }

                    System.out.println("idCountry = " + Integer.toString(idCountry));

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
                        System.out.println("There is a City/Country entry");

                        idCity = rs.getInt("cityId");

                        System.out.println("idCity = " + Integer.toString(idCity));
                    }
                    //Else, INSERT a new entry
                    else {
                        System.out.println("No City/Country entry");

                        String queryAllCities = "SELECT * FROM city";
                        ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCities);

                        rs2.last();

                        // If there are entries in city table, set idCity to be +1 of last entry's id
                        if(rs2.getRow() > 0) {
                            idCity = rs2.getInt("cityId") + 1;
                        }

                        System.out.println("idCity = " + Integer.toString(idCity));

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
                        System.out.println("There is a Address/City entry");

                        idAddress = rs.getInt("addressId");

                        System.out.println("idAddress = " + Integer.toString(idAddress));
                    }
                    else {
                        System.out.println("No Address/City entry");

                        String queryAllAddresses = "SELECT * FROM address";
                        ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllAddresses);

                        rs2.last();

                        // If there are entries in address table, set idAddress to be +1 of last entry's id
                        if(rs2.getRow() > 0) {
                            idAddress = rs2.getInt("addressId") + 1;
                        }

                        System.out.println("idAddress = " + Integer.toString(idAddress));

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
                        System.out.println("There is a Customer/Address entry");

                        idCustomer = rs.getInt("customerId");

                        System.out.println("idCustomer = " + Integer.toString(idCustomer));
                    }
                    else {
                        System.out.println("No Customer/Address entry");

                        String queryAllCustomer = "SELECT * FROM customer";
                        ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCustomer);

                        rs2.last();

                        // If there are entries in customer table, set idCustomer to be +1 of last entry's id
                        if(rs2.getRow() > 0) {
                            idCustomer = rs2.getInt("customerId") + 1;
                        }

                        System.out.println("idCustomer = " + Integer.toString(idCustomer));

                        String insertNewCustomer = "INSERT INTO customer VALUES (" + idCustomer + ",'" + NameField.getText() + "','" + idAddress + "',1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                        dbConnectionStatement.executeUpdate(insertNewCustomer);
                    }
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                    error = true;
                }
            }

            // Close Add Customer Window after save
            Stage stage = (Stage) SaveButton.getScene().getWindow();
            stage.close();
        }
    }
}
