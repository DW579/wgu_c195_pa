package View_Controller;

import Model.Appointment;
import Model.CalendarData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CalendarTestController {
    @FXML
    private TableView<Appointment> TableView1;
    @FXML
    private TableColumn<Appointment, String> Column1;
//    @FXML
//    private TableColumn<Appointment, String> DayColumn2;
//    @FXML
//    private TableColumn<Appointment, String> DayColumn8;
//    @FXML
//    private TableColumn<Appointment, String> DayColumn9;

    @FXML
    private void initialize() {
        Column1.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
//        DayColumn2.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
//        DayColumn8.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
//        DayColumn9.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        TableView1.setItems(CalendarData.getAllAppointments());
    }

    public void changeViewHandler(ActionEvent actionEvent) {
    }

    public void exitHandler(ActionEvent actionEvent) {
    }

    public void previousMonthClicked(MouseEvent mouseEvent) {
    }

    public void previousMonthEntered(MouseEvent mouseEvent) {
    }

    public void previousMonthExited(MouseEvent mouseEvent) {
    }

    public void nextMonthClicked(MouseEvent mouseEvent) {
    }

    public void nextMonthEntered(MouseEvent mouseEvent) {
    }

    public void nextMonthExited(MouseEvent mouseEvent) {
    }

    public void customerButtonHandler(ActionEvent actionEvent) {
    }
}
