package View_Controller;

import Model.Appointment;
import Model.CalendarData;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class ScheduleController {
    public Button CancelButton;
    public ComboBox UserSelectBox;

    // Schedule Table View
    @FXML
    private TableView<Appointment> ScheduleTableView;
    @FXML
    private TableColumn<Appointment, Integer> AppointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> TitleColumn;
    @FXML
    private TableColumn<Appointment, String> TypeColumn;
    @FXML
    private TableColumn<Appointment, String> StartDateColumn;
    @FXML
    private TableColumn<Appointment, String> EndDateColumn;

    @FXML
    private void initialize() {
        // Initialize the combo box with user IDs
        ObservableList<String> all_users = FXCollections.observableArrayList();

        all_users.add("1");
        all_users.add("2");

        UserSelectBox.setItems(all_users);

    }

    public void CancelHandler(ActionEvent actionEvent) throws IOException {
        // Close Schedule stage
        Stage scheduleStage = (Stage) CancelButton.getScene().getWindow();
        scheduleStage.close();

        // Open calendar stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
        Parent rootCalendar = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Calendar");
        stage.setScene(new Scene(rootCalendar));
        stage.show();
    }

    public void UserSelectHandler(ActionEvent actionEvent) {
        String selected_user = UserSelectBox.getSelectionModel().getSelectedItem().toString();

        // ObservableList of todays appointments
        ObservableList<Appointment> todays_appointments = FXCollections.observableArrayList();

        // GET all appointments from DB
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryAllAppointments = "SELECT * FROM appointment WHERE userId=" + selected_user;
            ResultSet rs = dbConnectionStatement.executeQuery(queryAllAppointments);

            int today_year = LocalDateTime.now().getYear();
            int today_month = LocalDateTime.now().getMonthValue();
            int today_day = LocalDateTime.now().getDayOfMonth();

            // Compare all appointments to Todays localdate, if it falls on today put in Observablelist
            while(rs.next()) {
                // String from DB is in UTC time zone
                String db_start = rs.getString("start").substring(0,19);
                String db_end = rs.getString("end").substring(0,19);
                String db_date = rs.getString("start").substring(0,10);

                // Format to convert user string for LocalDateTime
                DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter d_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Convert user date time string into a LocalDateTime variable
                LocalDateTime utc_start_dt = LocalDateTime.parse(db_start, dt_formatter);
                LocalDateTime utc_end_dt = LocalDateTime.parse(db_end, dt_formatter);
                LocalDate start_d = LocalDate.parse(db_date, d_formatter);

                // Two ZoneIds, origin_zone = user current zone, utc_zone = UTC zone
                ZoneId origin_zone = ZoneId.systemDefault();
                ZoneId utc_zone = ZoneId.of("UTC");

                // Convert user LocalDateTime variable into the user's time zone, this will be stored into the ObservableList of app
                ZonedDateTime utc_start_zdt = utc_start_dt.atZone(utc_zone);
                ZonedDateTime utc_end_zdt = utc_end_dt.atZone(utc_zone);


                // Convert user LocalDateTime variable into UTC zone for storage in DB
                ZonedDateTime origin_start_zdt = utc_start_zdt.withZoneSameInstant(origin_zone);
                ZonedDateTime origin_end_zdt = utc_end_zdt.withZoneSameInstant(origin_zone);

                // Convert both ZonedDateTimes to Strings to be storage in their respective locations
                String origin_start_string = origin_start_zdt.toLocalDateTime().format(dt_formatter);
                String origin_end_string = origin_end_zdt.toLocalDateTime().format(dt_formatter);

                if(origin_start_zdt.getYear() == today_year && origin_start_zdt.getMonthValue() == today_month && origin_start_zdt.getDayOfMonth() == today_day) {
                    Appointment new_appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getInt("userId"), rs.getString("title"), rs.getString("description"), rs.getString("location"), rs.getString("contact"), rs.getString("type"), rs.getString("url"), "'" + origin_start_string + "'", "'" + origin_end_string + "'", rs.getString("createDate"), rs.getString("createdBy"), rs.getString("lastUpdate"), rs.getString("lastUpdateBy"));
                    todays_appointments.add(new_appointment);
                }

//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//                String db_start = rs.getString("start");
//
//                int db_start_year = Integer.parseInt(db_start.substring(0, 4));
//                int db_start_month = Integer.parseInt(db_start.substring(5, 7));
//                int db_start_day = Integer.parseInt(db_start.substring(8, 10));
//
//                // Determine user location and timezone
//                Calendar calendar_start = Calendar.getInstance();
//
//                calendar_start.set(db_start_year,db_start_month - 1,db_start_day); // Unsure why I need to subtract 11 from the month
//
//                SimpleDateFormat sdf_start = new SimpleDateFormat("yyyy-MM-dd");
//
//                sdf_start.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//                sdf_start.setTimeZone(TimeZone.getDefault());
//
//                // Convert appointment start time into LocalDateTime
//                LocalDate appointment_start = LocalDate.parse(sdf_start.format(calendar_start.getTime()), formatter);
//
//                if(today.isEqual(appointment_start)) {
//                    Appointment new_appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getInt("userId"), rs.getString("title"), rs.getString("description"), rs.getString("location"), rs.getString("contact"), rs.getString("type"), rs.getString("url"), "'" + sdf_start_time.format(calendar_start_time.getTime()) + "'", "'" + sdf_end_time.format(calendar_end_time.getTime()) + "'", rs.getString("createDate"), rs.getString("createdBy"), rs.getString("lastUpdate"), rs.getString("lastUpdateBy"));
//                    todays_appointments.add(new_appointment);
//                }
            }

            // Initialize and update Customer table
            AppointmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            TitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            TypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
            StartDateColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
            EndDateColumn.setCellValueFactory(cellData -> cellData.getValue().endProperty());
            ScheduleTableView.setItems(todays_appointments);
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

    }
}
