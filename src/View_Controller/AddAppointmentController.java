package View_Controller;

import Model.Appointment;
import Model.CalendarData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import utils.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

public class AddAppointmentController {
    public TextField AppointmentIdField;
    public TextField CustomerIdField;
    public TextField TitleField;
    public TextField TypeField;
    public DatePicker StartDateField;
    public TextField StartHourField;
    public TextField StartMinuteField;
    public DatePicker EndDateField;
    public TextField EndHourField;
    public TextField EndMinuteField;
    public Button CancelButton;
    public Button SaveButton;
    public Label TimeZoneLabel;
    public ChoiceBox UserBox;

    @FXML
    private void initialize() {
        // Set the label to display to the user what time zone they are in
        TimeZoneLabel.setText("* Your time zone: " + TimeZone.getDefault().getID());

        // Set items in ChoiceBox of users in DB
        ObservableList<String> user_options = FXCollections.observableArrayList();

        user_options.add("1");
        user_options.add("2");

        UserBox.setItems(user_options);
    }

    public void selectedCustomer(int customerId) throws IOException {
        CustomerIdField.setText(Integer.toString(customerId));
    }

    public void SaveHandler(ActionEvent actionEvent) {
        // GET Date field data

        // Check Hour and Min fields are valid numbers

        // Check Duration Drop down that a selection was made

        // Store this data into DB and a new Object

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

        // Check to make sure all fields are filled out
        if(TitleField.getText().isEmpty() || TypeField.getText().isEmpty() || StartDateField.getValue().toString().isEmpty() || StartHourField.getText().isEmpty() || StartMinuteField.getText().isEmpty() || EndDateField.getValue().toString().isEmpty() || EndHourField.getText().isEmpty() || EndMinuteField.getText().isEmpty()) {
            Alert fillAllRequired = new Alert(Alert.AlertType.CONFIRMATION);
            fillAllRequired.initModality(Modality.NONE);
            fillAllRequired.setTitle("Fill All Required Fields");
            fillAllRequired.setHeaderText("Fill All Required Fields");
            fillAllRequired.setContentText("Please fill in all required fields");
            Optional<ButtonType> userChoice = fillAllRequired.showAndWait();
        }
        else {
            String start_date_time_string = StartDateField.getValue().toString() + " " + start_hour_string + ":" + start_minute_string + ":00";
            String end_date_time_string =  EndDateField.getValue().toString() + " " + end_hour_string + ":" + end_minute_string + ":00";
            String start_date_string = StartDateField.getValue().toString();
            String end_date_string = EndDateField.getValue().toString();

            DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter d_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDateTime start_dt = LocalDateTime.parse(start_date_time_string, dt_formatter);
            LocalDateTime end_dt = LocalDateTime.parse(end_date_time_string, dt_formatter);
            LocalDate start_d = LocalDate.parse(start_date_string, d_formatter);
            LocalDate end_d = LocalDate.parse(end_date_string, d_formatter);

            try {
                // Check to see if start time is not after end time
                if(start_dt.isAfter(end_dt)) {
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

                        String queryStartDate = "SELECT * FROM appointment WHERE start BETWEEN '" + start_date_time_string + "' AND ' " + end_date_time_string + "'";
                        String queryEndDate = "SELECT * FROM appointment WHERE end BETWEEN '" + start_date_time_string + "' AND ' " + end_date_time_string + "'";


                        ResultSet rs_start = dbc_start.executeQuery(queryStartDate);
                        ResultSet rs_end = dbc_end.executeQuery(queryEndDate);

                        rs_start.last();
                        rs_end.last();

                        if(rs_start.getRow() > 0 || rs_end.getRow() > 0) {
                            throw new ArithmeticException("Appointment exists during this time frame. Please choose another time frame for your appointment");
                        }
                        else {
                            // If all of the ifs check out above, add appointment to DB and ObservableList
                            // Set Dynamic Appointment ID
                            int dynamicAppointmentId = 1;

                            Statement dbc_all_appointments = DBConnection.getConnection().createStatement();
                            String queryAllAppointments = "SELECT * FROM appointment";
                            ResultSet rs_all_appointments = dbc_all_appointments.executeQuery(queryAllAppointments);

                            rs_all_appointments.last();

                            if(rs_all_appointments.getRow() > 0) {
                                dynamicAppointmentId = rs_all_appointments.getInt("appointmentId") + 1;
                            }

                            // Input new Appointment data into DB
                            Statement dbc_insert_db = DBConnection.getConnection().createStatement();
                            String queryInsertNewAppt = "INSERT INTO appointment VALUES (" + Integer.toString(dynamicAppointmentId) + "," + CustomerIdField.getText() + ",1,'" + TitleField.getText() + "','test','test','test','" + TypeField.getText() + "','test','" + start_date_time_string + "','" + end_date_time_string + "','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test')";
                            dbc_insert_db.execute(queryInsertNewAppt);


                            // Convert start and end times to user's timezone
                            String db_start = start_date_time_string;
                            String db_end = end_date_time_string;

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



                            // Input new Appointment data into ObservableList
                            Appointment newAppointment = new Appointment(dynamicAppointmentId, Integer.parseInt(CustomerIdField.getText()), Integer.parseInt(UserBox.getValue().toString()), TitleField.getText(), "test", "test", "test", TypeField.getText(), "test", "'" + sdf_start.format(calendar_start.getTime()) + "'", "'" + sdf_end.format(calendar_end.getTime()) + "'", "2019-01-01 00:00:00","test","2019-01-01 00:00:00","test");

                            CalendarData.addAppointment(newAppointment);

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
