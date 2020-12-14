package View_Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointmentController {
    public TextField AppointmentIdField;
    public TextField CustomerIdField;
    public TextField TitleField;
    public TextField TypeField;
    public DatePicker StartField;
    public DatePicker EndField;
    public Button CancelButton;
    public Button SaveButton;

    public void selectedCustomer(int customerId) throws IOException {
        CustomerIdField.setText(Integer.toString(customerId));
    }

    public void SaveHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) SaveButton.getScene().getWindow();
        stage.close();
    }

    public void CancelHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }
}
