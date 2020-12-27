package View_Controller;

import Model.Appointment;
import Model.CalendarData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBConnection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

public class CalendarController {
    public Button CustomerButton;
    public Button ViewButton;
    public Button ExitButton;

    public Label Month;
    public Label Year;
    public Label PreviousMonthArrow;
    public Label NextMonthArrow;

    public GridPane MonthGridPane;
    public GridPane WeekGridPane;
    public Button ScheduleButton;
    public Label AppointmentTypesQauntity;
    public Label AppointmentCustomerQauntity;

    public Label TimeZoneLabelMonth;

    private boolean month_view = true;

    @FXML
    private void initialize() {
        String year = CalendarData.getSelectedYear();
        String month = CalendarData.getSelectedMonth();
        int monthInt = CalendarData.getSelectedMonthInt();
        int appointment_type_amounts = 0;
        int appointment_customer_amounts = 0;

        // Update Month and Year text to reflect today's date
        Month.setText(month);
        Year.setText(year);

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_month_anchor_panes = MonthGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 40; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_month_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));

            ObservableList<String> appointments_today = FXCollections.observableArrayList();
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);

            // If the Anchor Pane number value is not an empty string
            if(CalendarData.getAnchorPaneValue(i) != "") {

                // Set Today's day in Orange
                if (Integer.parseInt(CalendarData.getAnchorPaneValue(i)) == LocalDate.now().getDayOfMonth()) {
                    anchor_label.setTextFill(Color.web("#ffa500"));
                }

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i);
                }
                else {
                    day = CalendarData.getAnchorPaneValue(i);
                }

                // Start here for the Time Zone edits
                String month_int_string = null;

                if(monthInt < 10) {
                    month_int_string = "0" + Integer.toString(monthInt);
                }
                else {
                    month_int_string = Integer.toString(monthInt);
                }

                String startFullDate = year + "-" + month_int_string + "-" + day + " 00:00:00";
                String endFullDate = year + "-" + month_int_string + "-" + day + " 23:59:59";

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Convert start of day and end of day to LocalDateTimes
                LocalDateTime start_of_day = LocalDateTime.parse(startFullDate, formatter);
                LocalDateTime end_of_day = LocalDateTime.parse(endFullDate, formatter);

                try {
                    Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                    String queryForDaysAppointments = "SELECT * FROM appointment";
                    ResultSet rs = dbConnectionStatement.executeQuery(queryForDaysAppointments);

                    while(rs.next()) {

                        // String from DB is in UTC time zone
                        String db_start = rs.getString("start").substring(0,19);

                        // Convert db_start to user time zone from UTC, UTC to user time zone
                        DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        LocalDateTime utc_start_dt = LocalDateTime.parse(db_start, dt_formatter);

                        ZoneId utc_zone = ZoneId.of("UTC");
                        ZoneId user_zone = ZoneId.systemDefault();

                        // Convert LocalDateTime utc_start_dt into a ZonedDateTime
                        ZonedDateTime utc_start_zdt = utc_start_dt.atZone(utc_zone);

                        // Convert UTC of time to user time zone
                        ZonedDateTime user_start_zdt = utc_start_zdt.withZoneSameInstant(user_zone);

                        // Convert ZonedDateTime of user time zone to LocalDateTime
                        LocalDateTime user_start_dt = user_start_zdt.toLocalDateTime();


                        // See if start of appointment falls between start of day and end of day
                        if(user_start_dt.isAfter(start_of_day) && user_start_dt.isBefore(end_of_day)) {
                            appointments_today.add(rs.getString("title"));
                        }

                    }

                    anchor_list_view.setItems(appointments_today);
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                }
                // End here for Time Zone edits
            }

        }

        // Find and set the amount of different appointment types in the month
        try {
            LocalDate whole_month = LocalDate.of(Integer.parseInt(year), monthInt , 1);

            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryTypeQauntity = "SELECT DISTINCT type FROM appointment WHERE start BETWEEN '" + year + "-" + Integer.toString(monthInt) + "-1 00:00:00' AND '" + year + "-" + Integer.toString(monthInt) + "-" + Integer.toString(whole_month.lengthOfMonth()) + " 23:59:59'";
            ResultSet rs = dbConnectionStatement.executeQuery(queryTypeQauntity);

            while(rs.next()) {
                appointment_type_amounts += 1;
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        AppointmentTypesQauntity.setText(Integer.toString(appointment_type_amounts) + " appointment types this month");

        // Get and set the amount of different customers the appointments will be in a given month
        try {
            LocalDate whole_month = LocalDate.of(Integer.parseInt(year), monthInt , 1);

            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryCustomerQauntity = "SELECT DISTINCT customerId FROM appointment WHERE start BETWEEN '" + year + "-" + Integer.toString(monthInt) + "-1 00:00:00' AND '" + year + "-" + Integer.toString(monthInt) + "-" + Integer.toString(whole_month.lengthOfMonth()) + " 23:59:59'";
            ResultSet rs = dbConnectionStatement.executeQuery(queryCustomerQauntity);

            while(rs.next()) {
                appointment_customer_amounts += 1;
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        AppointmentCustomerQauntity.setText("Appointments with " + Integer.toString(appointment_customer_amounts) + " different customers this month");

        // Check to see if an appointment will start in 15 min. of user log in. If so, show an alert
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Get LocalDateTime.now()
            LocalDateTime login_time = LocalDateTime.now();
            LocalDateTime login_time_plus_fifteen = LocalDateTime.now().plusMinutes(15);

            ZoneId utc_zone = ZoneId.of("UTC");
            ZoneId user_zone = ZoneId.systemDefault();

            // Convert LocalDateTime login_time & login_time_plus_fifteen into a ZonedDateTime
            ZonedDateTime login_time_zdt = login_time.atZone(user_zone);
            ZonedDateTime login_time_plus_fifteen_zdt = login_time_plus_fifteen.atZone(user_zone);

            // Convert login time ZDT to UTC
            ZonedDateTime utc_login_time_zdt = login_time_zdt.withZoneSameInstant(utc_zone);
            ZonedDateTime utc_login_time_plus_fifteen_zdt = login_time_plus_fifteen_zdt.withZoneSameInstant(utc_zone);

            String utc_login_time_string = utc_login_time_zdt.toLocalDateTime().format(formatter);
            String utc_login_time_plus_fifteen_string = utc_login_time_plus_fifteen_zdt.toLocalDateTime().format(formatter);

            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryAllAppointments = "SELECT * FROM appointment WHERE start BETWEEN '" + utc_login_time_string + "' AND '" + utc_login_time_plus_fifteen_string + "'";
            ResultSet rs = dbConnectionStatement.executeQuery(queryAllAppointments);

            rs.last();

            if(rs.getRow() > 0) {
                Alert withinFifteen = new Alert(Alert.AlertType.CONFIRMATION);
                withinFifteen.initModality(Modality.NONE);
                withinFifteen.setTitle("Appointment within 15 min.");
                withinFifteen.setHeaderText("Appointment within 15 min.");
                withinFifteen.setContentText("You have an appointment within 15 min. Appointment title: " + rs.getString("title"));
                Optional<ButtonType> userChoice = withinFifteen.showAndWait();
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        // Log user login times in logins.txt
        try {
            File logins_file = new File("logins.txt");

            LocalDateTime login_time = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String login_time_formatted = login_time.format(formatter);
            String user_info = "\n test logged in successfully at " + TimeZone.getDefault().getID() + " ";

            if(logins_file.exists()) {
                Files.write(Paths.get("logins.txt"), user_info.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get("logins.txt"), login_time_formatted.getBytes(), StandardOpenOption.APPEND);
            }
            else {
                FileWriter user_login = new FileWriter("logins.txt");
                user_login.write(user_info);
                user_login.write(login_time_formatted);
                user_login.close();
            }

        }
        catch (IOException e) {
            System.out.println("IOException error: " + e.getMessage());
        }

        // Set the label to display to the user what time zone they are in
        TimeZoneLabelMonth.setText("* Your time zone: " + TimeZone.getDefault().getID());
        
    }

    // Open Week view
    public void changeViewHandler(ActionEvent actionEvent) throws IOException {

        // Close calendar stage
        Stage stageWeek = (Stage) ViewButton.getScene().getWindow();
        stageWeek.close();

        // Open Week stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Week.fxml"));
        Parent rootWeek = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Week View");
        stage.setScene(new Scene(rootWeek));
        stage.show();

    }

    // Open Customer window
    public void customerButtonHandler(ActionEvent actionEvent) throws IOException {
        // Close calendar stage
        Stage stageCalendar = (Stage) CustomerButton.getScene().getWindow();
        stageCalendar.close();

        // Open Customer stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent rootCustomer = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Customer");
        stage.setScene(new Scene(rootCustomer));
        stage.show();
    }

    // Previous month arrow
    public void previousMonthClicked(MouseEvent mouseEvent) {
        Month.setText(CalendarData.updatePreviousMonth());
        Year.setText(CalendarData.getSelectedYear());
        CalendarData.updateDaysNums();
        int appointment_type_amounts = 0;
        int appointment_customer_amounts = 0;
        int monthIntType = CalendarData.getSelectedMonthInt();

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_month_anchor_panes = MonthGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 40; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_month_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            // Color label orange if today
            if(CalendarData.getAnchorPaneValue(i) != "" && CalendarData.isToday() && Integer.parseInt(CalendarData.getAnchorPaneValue(i)) == LocalDate.now().getDayOfMonth()) {
                anchor_label.setTextFill(Color.web("#ffa500"));
            }

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if(CalendarData.getAnchorPaneValue(i) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i);
                }
                else {
                    day = CalendarData.getAnchorPaneValue(i);
                }

                String month_int_string = null;

                if(monthInt < 10) {
                    month_int_string = "0" + Integer.toString(monthInt);
                }
                else {
                    month_int_string = Integer.toString(monthInt);
                }

                String startFullDate = year + "-" + month_int_string + "-" + day + " 00:00:00";
                String endFullDate = year + "-" + month_int_string + "-" + day + " 23:59:59";

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Convert start of day and end of day to LocalDateTimes
                LocalDateTime start_of_day = LocalDateTime.parse(startFullDate, formatter);
                LocalDateTime end_of_day = LocalDateTime.parse(endFullDate, formatter);

                try {
                    Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                    String queryForDaysAppointments = "SELECT * FROM appointment";
                    ResultSet rs = dbConnectionStatement.executeQuery(queryForDaysAppointments);

                    while(rs.next()) {

                        // String from DB is in UTC time zone
                        String db_start = rs.getString("start").substring(0,19);

                        // Convert db_start to user time zone from UTC, UTC to user time zone
                        DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        LocalDateTime utc_start_dt = LocalDateTime.parse(db_start, dt_formatter);

                        ZoneId utc_zone = ZoneId.of("UTC");
                        ZoneId user_zone = ZoneId.systemDefault();

                        // Convert LocalDateTime utc_start_dt into a ZonedDateTime
                        ZonedDateTime utc_start_zdt = utc_start_dt.atZone(utc_zone);

                        // Convert UTC of time to user time zone
                        ZonedDateTime user_start_zdt = utc_start_zdt.withZoneSameInstant(user_zone);

                        // Convert ZonedDateTime of user time zone to LocalDateTime
                        LocalDateTime user_start_dt = user_start_zdt.toLocalDateTime();

                        // See if start of appointment falls between start of day and end of day
                        if(user_start_dt.isAfter(start_of_day) && user_start_dt.isBefore(end_of_day)) {
                            appointments_today.add(rs.getString("title"));
                        }

                    }

                    anchor_list_view.setItems(appointments_today);
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                }

            }
        }

        // Find and set the amount of different appointment types in the month
        try {
            LocalDate whole_month = LocalDate.of(Integer.parseInt(CalendarData.getSelectedYear()), monthIntType , 1);

            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryTypeQauntity = "SELECT DISTINCT type FROM appointment WHERE start BETWEEN '" + Integer.parseInt(CalendarData.getSelectedYear()) + "-" + Integer.toString(monthIntType) + "-1 00:00:00' AND '" + Integer.parseInt(CalendarData.getSelectedYear()) + "-" + Integer.toString(monthIntType) + "-" + Integer.toString(whole_month.lengthOfMonth()) + " 23:59:59'";
            ResultSet rs = dbConnectionStatement.executeQuery(queryTypeQauntity);

            while(rs.next()) {
                appointment_type_amounts += 1;
            }

            System.out.println(appointment_type_amounts);
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        AppointmentTypesQauntity.setText(Integer.toString(appointment_type_amounts) + " appointment types this month");

        // Get and set the amount of different customers the appointments will be in a given month
        try {
            LocalDate whole_month = LocalDate.of(Integer.parseInt(CalendarData.getSelectedYear()), monthIntType , 1);

            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryCustomerQauntity = "SELECT DISTINCT customerId FROM appointment WHERE start BETWEEN '" + CalendarData.getSelectedYear() + "-" + Integer.toString(monthIntType) + "-1 00:00:00' AND '" + CalendarData.getSelectedYear() + "-" + Integer.toString(monthIntType) + "-" + Integer.toString(whole_month.lengthOfMonth()) + " 23:59:59'";
            ResultSet rs = dbConnectionStatement.executeQuery(queryCustomerQauntity);

            while(rs.next()) {
                appointment_customer_amounts += 1;
            }

            System.out.println(appointment_customer_amounts);
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        AppointmentCustomerQauntity.setText("Appointments with " + Integer.toString(appointment_customer_amounts) + " different customers this month");
    }

    public void previousMonthEntered(MouseEvent mouseEvent) {
        PreviousMonthArrow.setTextFill(Color.web("#0076a3"));
    }

    public void previousMonthExited(MouseEvent mouseEvent) {
        PreviousMonthArrow.setTextFill(Color.web("#000000"));
    }

    // Next month arrow
    public void nextMonthClicked(MouseEvent mouseEvent) {
        Month.setText(CalendarData.updateNextMonth());
        Year.setText(CalendarData.getSelectedYear());
        CalendarData.updateDaysNums();
        int appointment_type_amounts = 0;
        int appointment_customer_amounts = 0;
        int monthIntType = CalendarData.getSelectedMonthInt();

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_month_anchor_panes = MonthGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 40; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_month_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            if(CalendarData.getAnchorPaneValue(i) != "" && CalendarData.isToday() && Integer.parseInt(CalendarData.getAnchorPaneValue(i)) == LocalDate.now().getDayOfMonth()) {
                anchor_label.setTextFill(Color.web("#ffa500"));
            }

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if(CalendarData.getAnchorPaneValue(i) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i);
                }
                else {
                    day = CalendarData.getAnchorPaneValue(i);
                }

                String month_int_string = null;

                if(monthInt < 10) {
                    month_int_string = "0" + Integer.toString(monthInt);
                }
                else {
                    month_int_string = Integer.toString(monthInt);
                }

                String startFullDate = year + "-" + month_int_string + "-" + day + " 00:00:00";
                String endFullDate = year + "-" + month_int_string + "-" + day + " 23:59:59";

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Convert start of day and end of day to LocalDateTimes
                LocalDateTime start_of_day = LocalDateTime.parse(startFullDate, formatter);
                LocalDateTime end_of_day = LocalDateTime.parse(endFullDate, formatter);

                try {
                    Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                    String queryForDaysAppointments = "SELECT * FROM appointment";
                    ResultSet rs = dbConnectionStatement.executeQuery(queryForDaysAppointments);

                    while(rs.next()) {

                        // String from DB is in UTC time zone
                        String db_start = rs.getString("start").substring(0,19);

                        // Convert db_start to user time zone from UTC, UTC to user time zone
                        DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        LocalDateTime utc_start_dt = LocalDateTime.parse(db_start, dt_formatter);

                        ZoneId utc_zone = ZoneId.of("UTC");
                        ZoneId user_zone = ZoneId.systemDefault();

                        // Convert LocalDateTime utc_start_dt into a ZonedDateTime
                        ZonedDateTime utc_start_zdt = utc_start_dt.atZone(utc_zone);

                        // Convert UTC of time to user time zone
                        ZonedDateTime user_start_zdt = utc_start_zdt.withZoneSameInstant(user_zone);

                        // Convert ZonedDateTime of user time zone to LocalDateTime
                        LocalDateTime user_start_dt = user_start_zdt.toLocalDateTime();

                        // See if start of appointment falls between start of day and end of day
                        if(user_start_dt.isAfter(start_of_day) && user_start_dt.isBefore(end_of_day)) {
                            appointments_today.add(rs.getString("title"));
                        }

                    }

                    anchor_list_view.setItems(appointments_today);
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                }

            }
        }

        // Find and set the amount of different appointment types in the month
        try {
            LocalDate whole_month = LocalDate.of(Integer.parseInt(CalendarData.getSelectedYear()), monthIntType , 1);

            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryTypeQauntity = "SELECT DISTINCT type FROM appointment WHERE start BETWEEN '" + Integer.parseInt(CalendarData.getSelectedYear()) + "-" + Integer.toString(monthIntType) + "-1 00:00:00' AND '" + Integer.parseInt(CalendarData.getSelectedYear()) + "-" + Integer.toString(monthIntType) + "-" + Integer.toString(whole_month.lengthOfMonth()) + " 23:59:59'";
            ResultSet rs = dbConnectionStatement.executeQuery(queryTypeQauntity);

            while(rs.next()) {
                appointment_type_amounts += 1;
            }

            System.out.println(appointment_type_amounts);
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        AppointmentTypesQauntity.setText(Integer.toString(appointment_type_amounts) + " appointment types this month");

        // Get and set the amount of different customers the appointments will be in a given month
        try {
            LocalDate whole_month = LocalDate.of(Integer.parseInt(CalendarData.getSelectedYear()), monthIntType , 1);

            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryCustomerQauntity = "SELECT DISTINCT customerId FROM appointment WHERE start BETWEEN '" + CalendarData.getSelectedYear() + "-" + Integer.toString(monthIntType) + "-1 00:00:00' AND '" + CalendarData.getSelectedYear() + "-" + Integer.toString(monthIntType) + "-" + Integer.toString(whole_month.lengthOfMonth()) + " 23:59:59'";
            ResultSet rs = dbConnectionStatement.executeQuery(queryCustomerQauntity);

            while(rs.next()) {
                appointment_customer_amounts += 1;
            }

            System.out.println(appointment_customer_amounts);
        }
        catch (SQLException e) {
            System.out.println("SQLException error: " + e.getMessage());
        }

        AppointmentCustomerQauntity.setText("Appointments with " + Integer.toString(appointment_customer_amounts) + " different customers this month");
    }

    public void nextMonthEntered(MouseEvent mouseEvent) {
        NextMonthArrow.setTextFill(Color.web("#0076a3"));
    }

    public void nextMonthExited(MouseEvent mouseEvent) {
        NextMonthArrow.setTextFill(Color.web("#000000"));
    }

    // Exit out of program
    public void exitHandler(ActionEvent actionEvent) {
        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDelete.initModality(Modality.NONE);
        confirmDelete.setTitle("Exit?");
        confirmDelete.setHeaderText("Exit?");
        confirmDelete.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> userChoice = confirmDelete.showAndWait();

        if(userChoice.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void ScheduleHandler(ActionEvent actionEvent) throws IOException {
        // Close calendar stage
        Stage stageCalendar = (Stage) ScheduleButton.getScene().getWindow();
        stageCalendar.close();

        // Open Schedule stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Schedule.fxml"));
        Parent rootSchedule = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Schedule");
        stage.setScene(new Scene(rootSchedule));
        stage.show();
    }
}
