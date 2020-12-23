package View_Controller;

import Model.Appointment;
import Model.CalendarData;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginScreenController {
    public Button LoginButton;

    // Input field IDs
    @FXML
    private TextField Username;
    @FXML
    private TextField Password;

    // Error text IDs
    @FXML
    private Label Error;

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
            Error.setVisible(true);
        }
    }

    @FXML
    private void initialize() {
        // Error texts initial appear hidden
        Error.setVisible(false);

        ResourceBundle rb = ResourceBundle.getBundle("Languages/login", Locale.getDefault());
        Username.setPromptText(rb.getString("username"));
        Password.setPromptText(rb.getString("password"));
        LoginButton.setText(rb.getString("login"));
        Error.setText(rb.getString("errortext"));

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

        // If no initial user, add one user
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryAllUsers = "SELECT * FROM user";
            ResultSet rs = dbConnectionStatement.executeQuery(queryAllUsers);

            rs.last();

            if(rs.getRow() == 0) {
                try {
                    Statement dbCS = DBConnection.getConnection().createStatement();
                    String queryInsertUser = "INSERT INTO user VALUES (1,'test','test',1,'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test')";
                    dbCS.execute(queryInsertUser);
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        // Get all appointment data from DB and insert into Appointment ObservableList
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryAllAppointments = "SELECT * FROM appointment";
            ResultSet rs = dbConnectionStatement.executeQuery(queryAllAppointments);

            while(rs.next()) {
                int appointmentId = rs.getInt("appointmentId");
                int customerId = rs.getInt("customerId");
                int userId = rs.getInt("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String contact = rs.getString("contact");
                String type = rs.getString("type");
                String url = rs.getString("url");
                String createDate = rs.getString("createDate");
                String createdBy = rs.getString("createdBy");
                String lastUpdate = rs.getString("lastUpdate");
                String lastUpdateBy = rs.getString("lastUpdateBy");

                // Convert start and end times to user's timezone
                String db_start = rs.getString("start");
                String db_end = rs.getString("end");

                int db_start_year = Integer.parseInt(db_start.substring(0, 4));
                int db_start_month = Integer.parseInt(db_start.substring(5, 7));
                int db_start_day = Integer.parseInt(db_start.substring(8, 10));
                int db_start_hour = Integer.parseInt(db_start.substring(11, 13));
                int db_start_min = Integer.parseInt(db_start.substring(14, 16));
                int db_end_year = Integer.parseInt(db_end.substring(0, 4));
                int db_end_month = Integer.parseInt(db_end.substring(5, 7));
                int db_end_day = Integer.parseInt(db_end.substring(8, 10));
                int db_end_hour = Integer.parseInt(db_end.substring(11, 13));
                int db_end_min = Integer.parseInt(db_end.substring(14, 16));

                // Determine user location and timezone
                Calendar calendar_start = Calendar.getInstance();
                Calendar calendar_end = Calendar.getInstance();

                calendar_start.set(db_start_year,db_start_month - 1,db_start_day,db_start_hour,db_start_min,0); // Unsure why I need to subtract 11 from the month
                calendar_end.set(db_end_year,db_end_month - 1,db_end_day,db_end_hour,db_end_min,0); // Unsure why I need to subtract 11 from the month

                SimpleDateFormat sdf_start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf_end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                sdf_start.setTimeZone(TimeZone.getTimeZone("UTC"));
                sdf_end.setTimeZone(TimeZone.getTimeZone("UTC"));

                sdf_start.setTimeZone(TimeZone.getDefault());
                sdf_end.setTimeZone(TimeZone.getDefault());

                Appointment newAppointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, sdf_start.format(calendar_start.getTime()), sdf_end.format(calendar_end.getTime()), createDate, createdBy, lastUpdate, lastUpdateBy);

                CalendarData.addAppointment(newAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }
    }
}
