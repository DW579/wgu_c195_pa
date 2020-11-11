package View_Controller;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class CalendarMonthController {
    public Button WeekViewButton;
    public Label PreviousMonthArrow;
    public Label NextMonthArrow;

    //    Menu options
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

    public void weekHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CalendarWeek.fxml"));
        Parent rootAddPart = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Calendar Week");
        stage.setScene(new Scene(rootAddPart));
        stage.show();

        Stage currentStage = (Stage) WeekViewButton.getScene().getWindow();
        currentStage.close();
    }

    //    Arrows to change the months

    public void perviousMonthClickHandler(MouseEvent mouseEvent) {
    }

    public void perviousMonthEnterHandler(MouseEvent mouseEvent) {
        PreviousMonthArrow.setTextFill(Color.web("#0076a3"));
    }

    public void perviousMonthExitHandler(MouseEvent mouseEvent) {
        PreviousMonthArrow.setTextFill(Color.web("#000000"));
    }

    public void nextMonthClickHandler(MouseEvent mouseEvent) {
    }

    public void nextMonthEnterHandler(MouseEvent mouseEvent) {
        NextMonthArrow.setTextFill(Color.web("#0076a3"));
    }

    public void nextMonthExitHandler(MouseEvent mouseEvent) {
        NextMonthArrow.setTextFill(Color.web("#000000"));
    }

    public void monthBox00(MouseEvent mouseEvent) {
        System.out.println("Clicked on monthBox00");
    }

    public void monthLabel00(MouseEvent mouseEvent) {
        System.out.println("Clicked on monthLabel00");

    }

    public void monthList00(MouseEvent mouseEvent) {
        System.out.println("Clicked on monthList00");
    }


}
