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
        ObservableList<String> all_users = FXCollections.observableArrayList();

        all_users.add("1");
        all_users.add("2");

        UserSelectBox.setItems(all_users);

//        // ObservableList
//        ObservableList<Appointment> todays_appointments = FXCollections.observableArrayList();
//
//        // GET all appointments from DB
//        try {
//            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
//            String queryAllAppointments = "SELECT * FROM appointment";
//            ResultSet rs = dbConnectionStatement.executeQuery(queryAllAppointments);
//
//            LocalDate today = LocalDate.now();
//            System.out.println(today);
//
//            // Compare all appointments to Todays localdate, if it falls on today put in Observablelist
//            while(rs.next()) {
////                String appointment_start = rs.getString("start").substring(0, 10);
////
////                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
////
////                LocalDate start_ld = LocalDate.parse(appointment_start, formatter);
//
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                DateTimeFormatter formatter_time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//                String db_start = rs.getString("start");
//                String db_end = rs.getString("end");
//
//                int db_start_year = Integer.parseInt(db_start.substring(0, 4));
//                int db_start_month = Integer.parseInt(db_start.substring(5, 7));
//                int db_start_day = Integer.parseInt(db_start.substring(8, 10));
//                int db_start_hour = Integer.parseInt(db_start.substring(11, 13));
//                int db_start_min = Integer.parseInt(db_start.substring(14, 16));
//                int db_end_year = Integer.parseInt(db_end.substring(0, 4));
//                int db_end_month = Integer.parseInt(db_end.substring(5, 7));
//                int db_end_day = Integer.parseInt(db_end.substring(8, 10));
//                int db_end_hour = Integer.parseInt(db_end.substring(11, 13));
//                int db_end_min = Integer.parseInt(db_end.substring(14, 16));
//
//                // Determine user location and timezone
//                Calendar calendar_start = Calendar.getInstance();
//                Calendar calendar_start_time = Calendar.getInstance();
//                Calendar calendar_end_time = Calendar.getInstance();
//
//                calendar_start.set(db_start_year,db_start_month - 1,db_start_day); // Unsure why I need to subtract 11 from the month
//                calendar_start_time.set(db_start_year,db_start_month - 1,db_start_day, db_start_hour, db_start_min); // Unsure why I need to subtract 11 from the month
//                calendar_end_time.set(db_end_year,db_end_month - 1,db_end_day, db_end_hour, db_end_min); // Unsure why I need to subtract 11 from the month
//
//                SimpleDateFormat sdf_start = new SimpleDateFormat("yyyy-MM-dd");
//                SimpleDateFormat sdf_start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                SimpleDateFormat sdf_end_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//                sdf_start.setTimeZone(TimeZone.getTimeZone("UTC"));
//                sdf_start_time.setTimeZone(TimeZone.getTimeZone("UTC"));
//                sdf_end_time.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//                sdf_start.setTimeZone(TimeZone.getDefault());
//                sdf_start_time.setTimeZone(TimeZone.getDefault());
//                sdf_end_time.setTimeZone(TimeZone.getDefault());
//
//                // Convert appointment start time into LocalDateTime
//                LocalDate appointment_start = LocalDate.parse(sdf_start.format(calendar_start.getTime()), formatter);
//                LocalDateTime appointment_start_time = LocalDateTime.parse(sdf_start_time.format(calendar_start_time.getTime()), formatter_time);
//                LocalDateTime appointment_end_time = LocalDateTime.parse(sdf_end_time.format(calendar_end_time.getTime()), formatter_time);
//
//                if(today.isEqual(appointment_start)) {
//                    Appointment new_appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getInt("userId"), rs.getString("title"), rs.getString("description"), rs.getString("location"), rs.getString("contact"), rs.getString("type"), rs.getString("url"), "'" + sdf_start_time.format(calendar_start_time.getTime()) + "'", "'" + sdf_end_time.format(calendar_end_time.getTime()) + "'", rs.getString("createDate"), rs.getString("createdBy"), rs.getString("lastUpdate"), rs.getString("lastUpdateBy"));
//                    todays_appointments.add(new_appointment);
//                }
//            }
//
//            // Initialize and update Customer table
//            AppointmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
//            TitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
//            TypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
//            StartDateColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
//            EndDateColumn.setCellValueFactory(cellData -> cellData.getValue().endProperty());
//            ScheduleTableView.setItems(todays_appointments);
//        }
//        catch (SQLException e) {
//            System.out.println("SQLException error: " + e.getMessage());
//        }

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

        // ObservableList
        ObservableList<Appointment> todays_appointments = FXCollections.observableArrayList();

        // GET all appointments from DB
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryAllAppointments = "SELECT * FROM appointment WHERE userId=" + selected_user;
            ResultSet rs = dbConnectionStatement.executeQuery(queryAllAppointments);

            LocalDate today = LocalDate.now();

            // Compare all appointments to Todays localdate, if it falls on today put in Observablelist
            while(rs.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter formatter_time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
                Calendar calendar_start_time = Calendar.getInstance();
                Calendar calendar_end_time = Calendar.getInstance();

                calendar_start.set(db_start_year,db_start_month - 1,db_start_day); // Unsure why I need to subtract 11 from the month
                calendar_start_time.set(db_start_year,db_start_month - 1,db_start_day, db_start_hour, db_start_min); // Unsure why I need to subtract 11 from the month
                calendar_end_time.set(db_end_year,db_end_month - 1,db_end_day, db_end_hour, db_end_min); // Unsure why I need to subtract 11 from the month

                SimpleDateFormat sdf_start = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf_start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf_end_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                sdf_start.setTimeZone(TimeZone.getTimeZone("UTC"));
                sdf_start_time.setTimeZone(TimeZone.getTimeZone("UTC"));
                sdf_end_time.setTimeZone(TimeZone.getTimeZone("UTC"));

                sdf_start.setTimeZone(TimeZone.getDefault());
                sdf_start_time.setTimeZone(TimeZone.getDefault());
                sdf_end_time.setTimeZone(TimeZone.getDefault());

                // Convert appointment start time into LocalDateTime
                LocalDate appointment_start = LocalDate.parse(sdf_start.format(calendar_start.getTime()), formatter);
                LocalDateTime appointment_start_time = LocalDateTime.parse(sdf_start_time.format(calendar_start_time.getTime()), formatter_time);
                LocalDateTime appointment_end_time = LocalDateTime.parse(sdf_end_time.format(calendar_end_time.getTime()), formatter_time);

                if(today.isEqual(appointment_start)) {
                    Appointment new_appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getInt("userId"), rs.getString("title"), rs.getString("description"), rs.getString("location"), rs.getString("contact"), rs.getString("type"), rs.getString("url"), "'" + sdf_start_time.format(calendar_start_time.getTime()) + "'", "'" + sdf_end_time.format(calendar_end_time.getTime()) + "'", rs.getString("createDate"), rs.getString("createdBy"), rs.getString("lastUpdate"), rs.getString("lastUpdateBy"));
                    todays_appointments.add(new_appointment);
                }
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
