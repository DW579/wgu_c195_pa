package View_Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

public class CalendarMonthController {
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
}
