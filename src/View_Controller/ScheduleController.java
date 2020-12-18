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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleController {
    public Button CancelButton;

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
        // ObservableList
        ObservableList<Appointment> todays_appointments = FXCollections.observableArrayList();

        // GET all appointments from DB
        try {
            Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
            String queryAllAppointments = "SELECT * FROM appointment";
            ResultSet rs = dbConnectionStatement.executeQuery(queryAllAppointments);

            LocalDate today = LocalDate.now();

            // Compare all appointments to Todays localdate, if it falls on today put in Observablelist
            while(rs.next()) {
                String appointment_start = rs.getString("start").substring(0, 10);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate start_ld = LocalDate.parse(appointment_start, formatter);

                if(today.isEqual(start_ld)) {
                    Appointment new_appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getInt("userId"), rs.getString("title"), rs.getString("description"), rs.getString("location"), rs.getString("contact"), rs.getString("type"), rs.getString("url"), rs.getString("start"), rs.getString("end"), rs.getString("createDate"), rs.getString("createdBy"), rs.getString("lastUpdate"), rs.getString("lastUpdateBy"));
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
}
