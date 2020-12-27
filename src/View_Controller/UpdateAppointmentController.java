package View_Controller;

import Model.CalendarData;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
import java.util.Optional;
import java.util.TimeZone;

public class UpdateAppointmentController {

    public Button CancelButton;
    public Button SaveButton;
    public TextField AppointmentIdField;
    public TextField CustomerIdField;
    public TextField TitleField;
    public TextField TypeField;
    public DatePicker StartDateField;
    public DatePicker EndDateField;
    public TextField StartHourField;
    public TextField StartMinuteField;
    public TextField EndHourField;
    public TextField EndMinuteField;

    public void selectedAppointment(int appointmentId) throws IOException {

        // GET data from DB on associated appointmentId
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryForSelectedAppointment = "SELECT * FROM appointment WHERE appointmentId=" + Integer.toString(appointmentId);
            ResultSet rs = dbConnectionStatement.executeQuery(queryForSelectedAppointment);

            rs.next();

            String db_start = rs.getString("start").substring(0,19);
            String db_end = rs.getString("end").substring(0,19);

            // Convert db_start and db_end to user time zone from UTC, UTC to user time zone
            DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Convert DB time strings into LocalDateTime variables
            LocalDateTime utc_start_dt = LocalDateTime.parse(db_start, dt_formatter);
            LocalDateTime utc_end_dt = LocalDateTime.parse(db_end, dt_formatter);

            // UTC and user time zone ZoneIds
            ZoneId utc_zone = ZoneId.of("UTC");
            ZoneId user_zone = ZoneId.systemDefault();

            // Convert LocalDateTime utc_start_dt into a ZonedDateTime
            ZonedDateTime utc_start_zdt = utc_start_dt.atZone(utc_zone);
            ZonedDateTime utc_end_zdt = utc_end_dt.atZone(utc_zone);

            // Convert UTC of time to user time zone
            ZonedDateTime user_start_zdt = utc_start_zdt.withZoneSameInstant(user_zone);
            ZonedDateTime user_end_zdt = utc_end_zdt.withZoneSameInstant(user_zone);

            // Convert ZonedDateTime to LocalDateTime to parse out data for input into fields
            LocalDateTime user_start_string = user_start_zdt.toLocalDateTime();
            LocalDateTime user_end_string = user_end_zdt.toLocalDateTime();

            LocalDate localStartDate = user_start_string.toLocalDate();
            LocalDate localEndDate = user_end_string.toLocalDate();

            StartDateField.setValue(localStartDate);
            StartHourField.setText(Integer.toString(user_start_string.getHour()));
            StartMinuteField.setText(Integer.toString(user_start_string.getMinute()));

            EndDateField.setValue(localEndDate);
            EndHourField.setText(Integer.toString(user_end_string.getHour()));
            EndMinuteField.setText(Integer.toString(user_end_string.getMinute()));

            AppointmentIdField.setText(Integer.toString(appointmentId));
            CustomerIdField.setText(Integer.toString(rs.getInt("customerId")));
            TitleField.setText(rs.getString("title"));
            TypeField.setText(rs.getString("type"));


        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

    }

    public void SaveHandler(ActionEvent actionEvent) {

        int start_hour = 0;
        int start_minute = 0;
        int end_hour = 0;
        int end_minute = 0;
        String start_hour_string = null;
        String start_minute_string = null;
        String end_hour_string = null;
        String end_minute_string = null;

        // Error control to receive correct integers for hours and minutes from user
        try {
            start_hour = Integer.parseInt(StartHourField.getText());
            start_minute = Integer.parseInt(StartMinuteField.getText());
            end_hour = Integer.parseInt(EndHourField.getText());
            end_minute = Integer.parseInt(EndMinuteField.getText());

            if (start_hour > 16 || start_hour < 9) {
                throw new ArithmeticException("Please fill in the start hour during business hours with a number between 9 and 16");
            }

            if (start_minute > 59 || start_minute < 0) {
                throw new ArithmeticException("Please fill in the start minute with a number between 0 and 59");
            }

            if (end_hour > 16 || end_hour < 9) {
                throw new ArithmeticException("Please fill in the end hour during business hours with a number between 9 and 16");
            }

            if (end_minute > 59 || end_minute < 0) {
                throw new ArithmeticException("Please fill in the end minute with a number between 0 and 59");
            }
        }
        catch(Exception e) {
            System.out.println("Error with hour and min: " + e.getMessage());

            Alert fillAllRequired = new Alert(Alert.AlertType.CONFIRMATION);
            fillAllRequired.initModality(Modality.NONE);
            fillAllRequired.setTitle("Hour and minute fields");
            fillAllRequired.setHeaderText("Hour and minute fields");
            fillAllRequired.setContentText(e.getMessage());
            Optional<ButtonType> userChoice = fillAllRequired.showAndWait();
        }

        // Add an additional zero to the front of singular integers
        if(start_hour < 10) {
            start_hour_string = "0" + Integer.toString(start_hour);
        }
        else {
            start_hour_string = Integer.toString(start_hour);
        }

        if(start_minute < 10) {
            start_minute_string = "0" + Integer.toString(start_minute);
        }
        else {
            start_minute_string = Integer.toString(start_minute);
        }

        if(end_hour < 10) {
            end_hour_string = "0" + Integer.toString(end_hour);
        }
        else {
            end_hour_string = Integer.toString(end_hour);
        }

        if(end_minute < 10) {
            end_minute_string = "0" + Integer.toString(end_minute);
        }
        else {
            end_minute_string = Integer.toString(end_minute);
        }

        // Check to make sure all fields filled out
        if(TitleField.getText().isEmpty() || TypeField.getText().isEmpty() || StartDateField.getValue().toString().isEmpty() || StartHourField.getText().isEmpty() || StartMinuteField.getText().isEmpty() || EndDateField.getValue().toString().isEmpty() || EndHourField.getText().isEmpty() || EndMinuteField.getText().isEmpty()) {
            Alert fillAllRequired = new Alert(Alert.AlertType.CONFIRMATION);
            fillAllRequired.initModality(Modality.NONE);
            fillAllRequired.setTitle("Fill All Required Fields");
            fillAllRequired.setHeaderText("Fill All Required Fields");
            fillAllRequired.setContentText("Please fill in all required fields");
            Optional<ButtonType> userChoice = fillAllRequired.showAndWait();
        }
        else {
            // User input time constructed into String
            String start_date_time_string = StartDateField.getValue().toString() + " " + start_hour_string + ":" + start_minute_string + ":00";
            String end_date_time_string = EndDateField.getValue().toString() + " " + end_hour_string + ":" + end_minute_string + ":00";
            String string_date_start = StartDateField.getValue().toString();
            String string_date_end = EndDateField.getValue().toString();

            // Format to convert user string for LocalDateTime
            DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter d_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Convert user date time string into a LocalDateTime variable
            LocalDateTime origin_start_dt = LocalDateTime.parse(start_date_time_string, dt_formatter);
            LocalDateTime origin_end_dt = LocalDateTime.parse(end_date_time_string, dt_formatter);
            LocalDate start_d = LocalDate.parse(string_date_start, d_formatter);
            LocalDate end_d = LocalDate.parse(string_date_end, d_formatter);

            // Two ZoneIds, origin_zone = user current zone, utc_zone = UTC zone
            ZoneId origin_zone = ZoneId.systemDefault();
            ZoneId utc_zone = ZoneId.of("UTC");

            // Convert user LocalDateTime variable into the user's time zone, this will be stored into the ObservableList of app
            ZonedDateTime origin_start_zdt = origin_start_dt.atZone(origin_zone);
            ZonedDateTime origin_end_zdt = origin_end_dt.atZone(origin_zone);

            // Convert user LocalDateTime variable into UTC zone for storage in DB
            ZonedDateTime utc_start_zdt = origin_start_zdt.withZoneSameInstant(utc_zone);
            ZonedDateTime utc_end_zdt = origin_end_zdt.withZoneSameInstant(utc_zone);

            // Convert both ZonedDateTimes to Strings to be storage in their respective locations
            String origin_start_string = origin_start_zdt.toLocalDateTime().format(dt_formatter);
            String origin_end_string = origin_end_zdt.toLocalDateTime().format(dt_formatter);
            String utc_start_string = utc_start_zdt.toLocalDateTime().format(dt_formatter);
            String utc_end_string = utc_end_zdt.toLocalDateTime().format(dt_formatter);

            try {
                // Check to see if start time is not after end time
                if(origin_start_zdt.isAfter(origin_end_zdt)) {
                    throw new ArithmeticException("Incorrect appointment times. Start time is before end time. Please correct.");
                }
                else {
                    // Check is appointment spans more then one day. If so, throw error
                    if(!start_d.isEqual(end_d)) {
                        throw new ArithmeticException("Appointment too long. Please keep appointment within business hours on the same day.");
                    }
                    else {
                        // Check if appointment overlaps another appointment
                        Statement dbc_start = DBConnection.getConnection().createStatement();
                        Statement dbc_end = DBConnection.getConnection().createStatement();

                        // ------ Start and end strings need the UTC form of the datetime
                        String queryStartDate = "SELECT * FROM appointment WHERE start BETWEEN '" + utc_start_string + "' AND '" + utc_end_string + "'";
                        String queryEndDate = "SELECT * FROM appointment WHERE end BETWEEN '" + utc_start_string + "' AND '" + utc_end_string + "'";

                        ResultSet rs_start = dbc_start.executeQuery(queryStartDate);
                        ResultSet rs_end = dbc_end.executeQuery(queryEndDate);
                        rs_start.last();
                        rs_end.last();

                        if(rs_start.getRow() > 0 || rs_end.getRow() > 0) {
                            throw new ArithmeticException("Appointment exists during this time frame. Please choose another time frame for your appointment");
                        }
                        else {

                            // Update Appointment data into DB
                            Statement dbc_insert_db = DBConnection.getConnection().createStatement();
                            String queryUpdateAppointment = "UPDATE appointment SET title='" + TitleField.getText() + "', type='" + TypeField.getText() + "', start='" + utc_start_string + "', end='" + utc_end_string + "' WHERE appointmentId=" + AppointmentIdField.getText();
                            dbc_insert_db.execute(queryUpdateAppointment);
                            System.out.println("here");

                            CalendarData.updateAppointment(Integer.parseInt(AppointmentIdField.getText()), Integer.parseInt(CustomerIdField.getText()), TitleField.getText(), TypeField.getText(), "'" + origin_start_string + "'", "'" + origin_end_string + "'");

                            Stage stage = (Stage) SaveButton.getScene().getWindow();
                            stage.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Error with hour and min: " + e.getMessage());

                Alert fillAllRequired = new Alert(Alert.AlertType.CONFIRMATION);
                fillAllRequired.initModality(Modality.NONE);
                fillAllRequired.setTitle("Hour and minute fields");
                fillAllRequired.setHeaderText("Hour and minute fields");
                fillAllRequired.setContentText(e.getMessage());
                Optional<ButtonType> userChoice = fillAllRequired.showAndWait();
            }
        }

    }

    public void CancelHandler(ActionEvent actionEvent) {

        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();

    }

}
