package View_Controller;

import Model.CalendarData;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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

            String startDate = rs.getString("start");
            String endDate = rs.getString("end");

            int startYear = Integer.parseInt(startDate.substring(0,4));
            int startMonth = Integer.parseInt(startDate.substring(5,7));
            int startDay = Integer.parseInt(startDate.substring(8,10));
            String startHour = startDate.substring(11,13);
            String startMin = startDate.substring(14,16);

            int endYear = Integer.parseInt(endDate.substring(0,4));
            int endMonth = Integer.parseInt(endDate.substring(5,7));
            int endDay = Integer.parseInt(endDate.substring(8,10));
            String endHour = endDate.substring(11,13);
            String endMin = endDate.substring(14,16);

            LocalDate localStartDate = LocalDate.of(startYear, startMonth, startDay);
            LocalDate localEndDate = LocalDate.of(endYear, endMonth, endDay);

            StartDateField.setValue(localStartDate);
            StartHourField.setText(startHour);
            StartMinuteField.setText(startMin);

            EndDateField.setValue(localEndDate);
            EndHourField.setText(endHour);
            EndMinuteField.setText(endMin);

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

        int appointmentId = Integer.parseInt(AppointmentIdField.getText());
        int customerId = Integer.parseInt(CustomerIdField.getText());
        String title = TitleField.getText();
        String type = TypeField.getText();
        String start = StartDateField.getValue().toString() + " " + StartHourField.getText() + ":" + StartMinuteField.getText() + ":00";
        String end = EndDateField.getValue().toString() + " " + EndHourField.getText() + ":" + EndMinuteField.getText() + ":00";

        // Convert start and end times to user's timezone
        String db_start = start;
        String db_end = end;

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

        CalendarData.updateAppointment(appointmentId, customerId, title, type, "'" + sdf_start.format(calendar_start.getTime()) + "'", "'" + sdf_end.format(calendar_end.getTime()) + "'");

        Stage stage = (Stage) SaveButton.getScene().getWindow();
        stage.close();

    }

    public void CancelHandler(ActionEvent actionEvent) {

        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();

    }

}
