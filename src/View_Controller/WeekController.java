package View_Controller;

import Model.CalendarData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class WeekController {
    public Label WeekMonth;
    public Label WeekYear;

    public Label WeekBack;
    public Label WeekNext;

    public Button ExitButton;

    public GridPane WeekGridPane;

    public Button Week1Button;
    public Button Week2Button;
    public Button Week3Button;
    public Button Week4Button;
    public Button Week5Button;
    public Button Week6Button;
    public Label TimeZoneLabel;

    @FXML
    private void initialize() {

        String year = CalendarData.getSelectedYear();
        String month = CalendarData.getSelectedMonth();
        int monthInt = CalendarData.getSelectedMonthInt();

        // Update Month and Year text to reflect today's date
        WeekMonth.setText(month);
        WeekYear.setText(year);

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));

            ObservableList<String> appointments_today = FXCollections.observableArrayList();
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);

            // If the Anchor Pane
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

        // Set the label to display to the user what time zone they are in
        TimeZoneLabel.setText("* Your time zone: " + TimeZone.getDefault().getID());
    }

    public void BackClick(MouseEvent mouseEvent) {
        WeekMonth.setText(CalendarData.updatePreviousMonth());
        WeekYear.setText(CalendarData.getSelectedYear());
        CalendarData.updateDaysNums();

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if (CalendarData.getAnchorPaneValue(i) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i);
                } else {
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
    }

    public void BackEnter(MouseEvent mouseEvent) {
        WeekBack.setTextFill(Color.web("#0076a3"));
    }

    public void BackExit(MouseEvent mouseEvent) {
        WeekBack.setTextFill(Color.web("#000000"));
    }

    public void NextClick(MouseEvent mouseEvent) {
        WeekMonth.setText(CalendarData.updateNextMonth());
        WeekYear.setText(CalendarData.getSelectedYear());
        CalendarData.updateDaysNums();

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

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
    }

    public void NextEnter(MouseEvent mouseEvent) {
        WeekNext.setTextFill(Color.web("#0076a3"));
    }

    public void NextExit(MouseEvent mouseEvent) {
        WeekNext.setTextFill(Color.web("#000000"));
    }

    public void ExitHandler(ActionEvent actionEvent) throws IOException {
        // Close Week stage
        Stage stageWeek = (Stage) ExitButton.getScene().getWindow();
        stageWeek.close();

        // Open Calendar stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
        Parent rootCalendar = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Calendar");
        stage.setScene(new Scene(rootCalendar));
        stage.show();
    }

    public void Week1Handler(ActionEvent actionEvent) {
        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if (CalendarData.getAnchorPaneValue(i) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i);
                } else {
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
    }

    public void Week2Handler(ActionEvent actionEvent) {
        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i + 7));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if (CalendarData.getAnchorPaneValue(i + 7) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i + 7).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i + 7);
                } else {
                    day = CalendarData.getAnchorPaneValue(i + 7);
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
    }

    public void Week3Handler(ActionEvent actionEvent) {
        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i + 14));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if (CalendarData.getAnchorPaneValue(i + 14) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i + 14).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i + 14);
                } else {
                    day = CalendarData.getAnchorPaneValue(i + 14);
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
    }

    public void Week4Handler(ActionEvent actionEvent) {
        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i + 21));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if (CalendarData.getAnchorPaneValue(i + 21) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i + 21).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i + 21);
                } else {
                    day = CalendarData.getAnchorPaneValue(i + 21);
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
    }

    public void Week5Handler(ActionEvent actionEvent) {
        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i + 28));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if (CalendarData.getAnchorPaneValue(i + 28) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i + 28).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i + 28);
                } else {
                    day = CalendarData.getAnchorPaneValue(i + 28);
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
    }

    public void Week6Handler(ActionEvent actionEvent) {
        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_week_anchor_panes = WeekGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 7; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_week_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i + 34));
            anchor_label.setTextFill(Color.web("#000000"));

            // ObservableList that will hold the titles of appointments
            ObservableList<String> appointments_today = FXCollections.observableArrayList();

            // Clear the ListView of other appointments
            ListView anchor_list_view = (ListView) all_anchor_children.get(1);
            anchor_list_view.getItems().clear();

            // Add appointments to the day if any
            String year = CalendarData.getSelectedYear();
            int monthInt = CalendarData.getSelectedMonthInt();

            // If the Anchor Pane has a number
            if (CalendarData.getAnchorPaneValue(i + 34) != "") {

                String day = null;

                // Add a 0 in front of 1 - 9, else no need for 0
                if (CalendarData.getAnchorPaneValue(i + 34).length() < 2) {
                    day = "0" + CalendarData.getAnchorPaneValue(i + 34);
                } else {
                    day = CalendarData.getAnchorPaneValue(i + 34);
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
    }

}
