package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    public void loginHandler(ActionEvent actionEvent) {
        // If both Username and Password have "test", open calendar page. Else, show error messages
        if(Username.getText().equals("test") && Password.getText().equals("test")) {
            System.out.println("Successful");
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
