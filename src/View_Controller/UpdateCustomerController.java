package View_Controller;

import Model.CalendarData;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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

    public void selectedCustomer(int customerId) throws IOException {
        int addressId = 0;
        int cityId = 0;
        int countryId = 0;

        IdField.setText(Integer.toString(customerId));

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
//        try {
//            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
//
//            // Update Customer Name
//            String queryUpdateCustomerName = "UPDATE customer SET customerName='" + NameField.getText() + "' WHERE customerId=" + IdField.getText();
//            Boolean updateCustomer = dbConnectionStatement.execute(queryUpdateCustomerName);
//        }
//        catch (SQLException e) {
//            System.out.println("SQLException error: " + e.getMessage());
//        }

        CalendarData.updateCustomer(Integer.parseInt(IdField.getText()), NameField.getText(), AddressField.getText(), Address2Field.getText(), CityField.getText(), ZipCodeField.getText(), CountryField.getText(), PhoneField.getText());

        Stage stage = (Stage) SaveButton.getScene().getWindow();
        stage.close();
    }

    public void closeButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }
}
