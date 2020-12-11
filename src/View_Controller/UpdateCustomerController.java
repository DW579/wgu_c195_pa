package View_Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateCustomerController {
    public TextField IdField;
    public TextField NameField;
    public TextField AddressField;
    public TextField CityField;
    public TextField PhoneField;
    public TextField CountryField;
    public TextField Address2Field;
    public TextField ZipCodeField;

    public Button SaveButton;
    public Button CloseButton;

    public void selectedCustomer(int customerId) throws IOException {
        System.out.println(customerId);
    }

    public void IdHandler(ActionEvent actionEvent) {
    }

    public void saveButtonHandler(ActionEvent actionEvent) {
    }

    public void closeButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }
}
