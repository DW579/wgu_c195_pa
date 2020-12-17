package View_Controller;

import Model.Appointment;
import Model.CalendarData;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import utils.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    public void selectedCustomer(int customerId) throws IOException {
        CustomerIdField.setText(Integer.toString(customerId));
    }

    public void SaveHandler(ActionEvent actionEvent) {

        if(TitleField.getText().isEmpty() || TypeField.getText().isEmpty() || StartDateField.getValue().toString().isEmpty() || StartHourField.getText().isEmpty() || StartMinuteField.getText().isEmpty() || EndDateField.getValue().toString().isEmpty() || EndHourField.getText().isEmpty() || EndMinuteField.getText().isEmpty()) {
            Alert fillAllRequired = new Alert(Alert.AlertType.CONFIRMATION);
            fillAllRequired.initModality(Modality.NONE);
            fillAllRequired.setTitle("Fill All Required Fields");
            fillAllRequired.setHeaderText("Fill All Required Fields");
            fillAllRequired.setContentText("Please fill in all required fields");
            Optional<ButtonType> userChoice = fillAllRequired.showAndWait();
        }
        else {
            // '2019-01-01 00:00:00'
            String startDateTime = "'" + StartDateField.getValue().toString() + " " + StartHourField.getText() + ":" + StartMinuteField.getText() + ":00'";
            String endDateTime = "'" + EndDateField.getValue().toString() + " " + EndHourField.getText() + ":" + EndMinuteField.getText() + ":00'";
            String calendarEntry = TitleField.getText() + " - " + startDateTime + " " + endDateTime;

            // Set Dynamic Appointment ID
            int dynamicAppointmentId = 1;

            try {
                Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                String queryAllAppointments = "SELECT * FROM appointment";
                ResultSet rs = dbConnectionStatement.executeQuery(queryAllAppointments);

                rs.last();

                if(rs.getRow() > 0) {
                    dynamicAppointmentId = rs.getInt("appointmentId") + 1;
                }

                System.out.println(dynamicAppointmentId);

            }
            catch (SQLException e) {
                System.out.println("SQLException error: " + e.getMessage());
            }

            // Input new Appointment data into DB
            try {
                Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                String queryInsertNewAppt = "INSERT INTO appointment VALUES (" + Integer.toString(dynamicAppointmentId) + "," + CustomerIdField.getText() + ",1,'" + TitleField.getText() + "','test','test','test','" + TypeField.getText() + "','test'," + startDateTime + "," + endDateTime + ",'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test')";
                dbConnectionStatement.execute(queryInsertNewAppt);

            }
            catch (SQLException e) {
                System.out.println("SQLException error: " + e.getMessage());
            }

            // Input new Appointment data into ObservableList
            Appointment newAppointment = new Appointment(dynamicAppointmentId, Integer.parseInt(CustomerIdField.getText()), 1, TitleField.getText(), "test", "test", "test", TypeField.getText(), "test", startDateTime, endDateTime, "2019-01-01 00:00:00","test","2019-01-01 00:00:00","test");

            CalendarData.addAppointment(newAppointment);

            CalendarData.addAppointmentForCalendar(calendarEntry);

            Stage stage = (Stage) SaveButton.getScene().getWindow();
            stage.close();
        }
    }

    public void CancelHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }
}
