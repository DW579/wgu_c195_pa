package View_Controller;

import Model.CalendarData;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginScreenController {
    // Input field IDs
    @FXML
    private TextField Username;
    @FXML
    private TextField Password;

    // Error text IDs
    @FXML
    private Label ErrorEng;
    @FXML
    private Label ErrorUkr;

    public void loginHandler(ActionEvent actionEvent) throws IOException {
        // If both Username and Password have "test", open calendar page. Else, show error messages
        if(Username.getText().equals("test") && Password.getText().equals("test")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
            Parent rootAddPart = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Calendar");
            stage.setScene(new Scene(rootAddPart));
            stage.show();
        }
        else {
            ErrorEng.setVisible(true);
            ErrorUkr.setVisible(true);
        }
    }

    @FXML
    private void initialize() {
        // Error texts initial appear hidden
        ErrorEng.setVisible(false);
        ErrorUkr.setVisible(false);

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
                    int cityId = rsAddress.getInt("cityId");
                    String phone = rsAddress.getString("phone");

                    newCustomer.setAddress(address1);
                    newCustomer.setPhone(phone);

                    try {
                        Statement csDB = DBConnection.getConnection().createStatement();
                        String queryCity = "SELECT * FROM city WHERE cityId=" + Integer.toString(cityId);
                        ResultSet rsCity = csDB.executeQuery(queryCity);

                        rsCity.next();

                        String city = rsCity.getString("city");
                        int countryId = rsCity.getInt("countryId");

                        newCustomer.setCity(city);

                        try {
                            Statement dbCSCountry = DBConnection.getConnection().createStatement();
                            String queryCountry = "SELECT * FROM country WHERE countryId=" + Integer.toString(countryId);
                            ResultSet rsCountry = dbCSCountry.executeQuery(queryCountry);

                            rsCountry.next();

                            String country = rsCountry.getString("country");

                            newCustomer.setCountry(country);
                        }
                        catch (SQLException e) {
                            System.out.println("SQLException error: " + e.getMessage());
                        }
                    }
                    catch (SQLException e) {
                        System.out.println("SQLException error: " + e.getMessage());
                    }
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
    }
}
