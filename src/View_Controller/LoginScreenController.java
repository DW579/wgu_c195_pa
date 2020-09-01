package View_Controller;

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

    @FXML
    private void initialize() {
        // Error texts initial appear hidden
        ErrorEng.setVisible(false);
        ErrorUkr.setVisible(false);
    }
}
