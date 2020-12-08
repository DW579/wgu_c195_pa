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

    // Initial Customer ID
    private static int dynamicCustomerId = 1;
    private static int dynamicCountryId = 1;

    // Close Add Customer window
    public void closeButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    public void IdHandler(ActionEvent actionEvent) {
    }

    // Customer data into DB
    public void saveButtonHandler(ActionEvent actionEvent) {
        int idCountry;

        // Check to see if all fields have values
        if(NameField.getText().isEmpty() || AddressField.getText().isEmpty() || CityField.getText().isEmpty() || CountryField.getText().isEmpty()) {
            System.out.println("Not all required fields are filled out");
        }
        else {
            // Maybe need later
            //                ResultSetMetaData rsmd = rs.getMetaData();
            //                System.out.println(rsmd.getColumnName(1));


            // Check against Countries in DB
            // If Country already in DB, use it's countryId. If not, then Insert a new entry and use that new countryId
            try {
                Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                String queryForCountry = "SELECT * FROM country WHERE country='" + CountryField.getText() + "'";
                ResultSet rs = dbConnectionStatement.executeQuery(queryForCountry);

                if(rs.next()) {
                    System.out.println("There is an entry");

                    idCountry = rs.getInt("countryId");

                    System.out.println(idCountry);
                }
                else {
                    System.out.println("No entry");

                    String queryAllCountries = "SELECT * FROM country";
                    ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCountries);

                    rs2.last();

                    idCountry = rs2.getInt("countryId") + 1;

                    String insertNewCountry = "INSERT INTO country VALUES (" + idCountry + ",'" + CountryField.getText() + "','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                    dbConnectionStatement.executeUpdate(insertNewCountry);

                    System.out.println(idCountry);
                }


            }
            catch (SQLException e) {
                System.out.println("SQLException error: " + e.getMessage());
            }

            // Check against Cities in DB

            // Check against Addresses in DB

        }

        // Close Add Customer Window after save
        Stage stage = (Stage) SaveButton.getScene().getWindow();
        stage.close();
    }
}
