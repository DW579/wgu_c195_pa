package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginScreenController {
    // Input field IDs
    @FXML
    private TextField Username;
    @FXML
    private TextField Password;

    // Error text IDs
    @FXML
    private Label ErrorEng;
    @FXML
    private Label ErrorUkr;

    public void loginHandler(ActionEvent actionEvent) throws IOException {
        // If both Username and Password have "test", open calendar page. Else, show error messages
        if(Username.getText().equals("test") && Password.getText().equals("test")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
            Parent rootAddPart = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Calendar");
            stage.setScene(new Scene(rootAddPart));
            stage.show();
            
        }
        else {
            ErrorEng.setVisible(true);
            ErrorUkr.setVisible(true);
        }
    }

    @FXML
    private void initialize() {
        // Error texts initial appear hidden
        ErrorEng.setVisible(false);
        ErrorUkr.setVisible(false);
    }
}
