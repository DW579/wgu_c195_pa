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
import java.time.LocalDate;

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
        String start = "'" + StartDateField.getValue().toString() + " " + StartHourField.getText() + ":" + StartMinuteField.getText() + ":00'";
        String end = "'" + EndDateField.getValue().toString() + " " + EndHourField.getText() + ":" + EndMinuteField.getText() + ":00'";

        CalendarData.updateAppointment(appointmentId, customerId, title, type, start, end);

        Stage stage = (Stage) SaveButton.getScene().getWindow();
        stage.close();

    }

    public void CancelHandler(ActionEvent actionEvent) {

        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();

    }

}
