package View_Controller;

import Model.CalendarData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class CalendarController {
    public Button ViewButton;
    public Button ExitButton;
    public Label Month;
    public Label Year;
    public Label PreviousMonthArrow;
    public Label NextMonthArrow;
    public GridPane MonthGridPane;
    public GridPane WeekGridPane;
    public AnchorPane AnchorPane00;
    public AnchorPane AnchorPane10;
    public AnchorPane AnchorPane20;
    public AnchorPane AnchorPane30;
    public AnchorPane AnchorPane40;
    public AnchorPane AnchorPane50;
    public AnchorPane AnchorPane60;
    public AnchorPane AnchorPane01;
    public AnchorPane AnchorPane11;
    public AnchorPane AnchorPane21;
    public AnchorPane AnchorPane31;
    public AnchorPane AnchorPane41;
    public AnchorPane AnchorPane51;
    public AnchorPane AnchorPane61;
    public AnchorPane AnchorPane02;
    public AnchorPane AnchorPane12;
    public AnchorPane AnchorPane22;
    public AnchorPane AnchorPane32;
    public AnchorPane AnchorPane42;
    public AnchorPane AnchorPane52;
    public AnchorPane AnchorPane62;
    public AnchorPane AnchorPane03;
    public AnchorPane AnchorPane13;
    public AnchorPane AnchorPane23;
    public AnchorPane AnchorPane33;
    public AnchorPane AnchorPane43;
    public AnchorPane AnchorPane53;
    public AnchorPane AnchorPane63;
    public AnchorPane AnchorPane04;
    public AnchorPane AnchorPane14;
    public AnchorPane AnchorPane24;
    public AnchorPane AnchorPane34;
    public AnchorPane AnchorPane44;
    public AnchorPane AnchorPane54;
    public AnchorPane AnchorPane64;
    public AnchorPane AnchorPane05;
    public AnchorPane AnchorPane15;
    public AnchorPane AnchorPane25;
    public AnchorPane AnchorPane35;
    public AnchorPane AnchorPane45;
    public AnchorPane AnchorPane55;
    public AnchorPane AnchorPane65;
    public Button CustomerButton;

    private boolean month_view = true;

    @FXML
    private void initialize() {
        // Update Month and Year text to reflect today's date
        Month.setText(CalendarData.getSelectedMonth());
        Year.setText(CalendarData.getSelectedYear());

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_month_anchor_panes = MonthGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 40; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_month_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));

            if(CalendarData.getAnchorPaneValue(i) != "") {
                if (Integer.parseInt(CalendarData.getAnchorPaneValue(i)) == LocalDate.now().getDayOfMonth()) {
                    anchor_label.setTextFill(Color.web("#ffa500"));
                }
            }
        }
    }

    // Change between Month and Week views
    public void changeViewHandler(ActionEvent actionEvent) {
        if(month_view){
            month_view = false;
            ViewButton.setText("Month View");
            MonthGridPane.setVisible(false);
            WeekGridPane.setVisible(true);
        }
        else {
            month_view = true;
            ViewButton.setText("Week View");
            WeekGridPane.setVisible(false);
            MonthGridPane.setVisible(true);
        }
    }

    // Open Customer window
    public void customerButtonHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent rootCustomer = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Customer");
        stage.setScene(new Scene(rootCustomer));
        stage.show();
    }

    // Previous month arrow
    public void previousMonthClicked(MouseEvent mouseEvent) {
        Month.setText(CalendarData.updatePreviousMonth());
        Year.setText(CalendarData.getSelectedYear());
        CalendarData.updateDaysNums();

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_month_anchor_panes = MonthGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 40; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_month_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));
            anchor_label.setTextFill(Color.web("#000000"));

            if(CalendarData.getAnchorPaneValue(i) != "" && CalendarData.isToday() && Integer.parseInt(CalendarData.getAnchorPaneValue(i)) == LocalDate.now().getDayOfMonth()) {
                anchor_label.setTextFill(Color.web("#ffa500"));
            }
        }
    }

    public void previousMonthEntered(MouseEvent mouseEvent) {
        PreviousMonthArrow.setTextFill(Color.web("#0076a3"));
    }

    public void previousMonthExited(MouseEvent mouseEvent) {
        PreviousMonthArrow.setTextFill(Color.web("#000000"));
    }

    // Next month arrow
    public void nextMonthClicked(MouseEvent mouseEvent) {
        Month.setText(CalendarData.updateNextMonth());
        Year.setText(CalendarData.getSelectedYear());
        CalendarData.updateDaysNums();

        // Set Text of each label in it's anchor pane to it's correct num
        ObservableList all_month_anchor_panes = MonthGridPane.getChildren();

        // Update all anchor pane labels with the respective number of the day
        for (int i = 0; i < 40; i++) {
            AnchorPane anchor_pane = (AnchorPane) all_month_anchor_panes.get(i);
            ObservableList all_anchor_children = anchor_pane.getChildren();
            Label anchor_label = (Label) all_anchor_children.get(0);
            anchor_label.setText(CalendarData.getAnchorPaneValue(i));
            anchor_label.setTextFill(Color.web("#000000"));

            if(CalendarData.getAnchorPaneValue(i) != "" && CalendarData.isToday() && Integer.parseInt(CalendarData.getAnchorPaneValue(i)) == LocalDate.now().getDayOfMonth()) {
                anchor_label.setTextFill(Color.web("#ffa500"));
            }
        }
    }

    public void nextMonthEntered(MouseEvent mouseEvent) {
        NextMonthArrow.setTextFill(Color.web("#0076a3"));
    }

    public void nextMonthExited(MouseEvent mouseEvent) {
        NextMonthArrow.setTextFill(Color.web("#000000"));
    }

    // Exit out of program
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

    // ---- Below are all the OnClick functions for the Month Calendar ----

    // OnClick functions for anchorPane00 and children
    public void anchorPane00Clicked(MouseEvent mouseEvent) {

    }

    public void label00Clicked(MouseEvent mouseEvent) {

    }

    public void listView00Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane10 and children
    public void anchorPane10Clicked(MouseEvent mouseEvent) {

    }

    public void label10Clicked(MouseEvent mouseEvent) {

    }

    public void listView10Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane20 and children
    public void anchorPane20Clicked(MouseEvent mouseEvent) {

    }

    public void label20Clicked(MouseEvent mouseEvent) {

    }

    public void listView20Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane30 and children
    public void anchorPane30Clicked(MouseEvent mouseEvent) {

    }

    public void label30Clicked(MouseEvent mouseEvent) {

    }

    public void listView30Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane40 and children
    public void anchorPane40Clicked(MouseEvent mouseEvent) {

    }

    public void label40Clicked(MouseEvent mouseEvent) {

    }

    public void listView40Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane50 and children
    public void anchorPane50Clicked(MouseEvent mouseEvent) {

    }

    public void label50Clicked(MouseEvent mouseEvent) {

    }

    public void listView50Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane60 and children
    public void anchorPane60Clicked(MouseEvent mouseEvent) {

    }

    public void label60Clicked(MouseEvent mouseEvent) {

    }

    public void listView60Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane01 and children
    public void anchorPane01Clicked(MouseEvent mouseEvent) {

    }

    public void label01Clicked(MouseEvent mouseEvent) {

    }

    public void listView01Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane11 and children
    public void anchorPane11Clicked(MouseEvent mouseEvent) {

    }

    public void label11Clicked(MouseEvent mouseEvent) {

    }

    public void listView11Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane21 and children
    public void anchorPane21Clicked(MouseEvent mouseEvent) {

    }

    public void label21Clicked(MouseEvent mouseEvent) {

    }

    public void listView21Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane31 and children
    public void anchorPane31Clicked(MouseEvent mouseEvent) {

    }

    public void label31Clicked(MouseEvent mouseEvent) {

    }

    public void listView31Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane41 and children
    public void anchorPane41Clicked(MouseEvent mouseEvent) {

    }

    public void label41Clicked(MouseEvent mouseEvent) {

    }

    public void listView41Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane51 and children
    public void anchorPane51Clicked(MouseEvent mouseEvent) {

    }

    public void label51Clicked(MouseEvent mouseEvent) {

    }

    public void listView51Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane61 and children
    public void anchorPane61Clicked(MouseEvent mouseEvent) {

    }

    public void label61Clicked(MouseEvent mouseEvent) {

    }

    public void listView61Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane02 and children
    public void anchorPane02Clicked(MouseEvent mouseEvent) {

    }

    public void label02Clicked(MouseEvent mouseEvent) {

    }

    public void listView02Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane12 and children
    public void anchorPane12Clicked(MouseEvent mouseEvent) {

    }

    public void label12Clicked(MouseEvent mouseEvent) {

    }

    public void listView12Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane22 and children
    public void anchorPane22Clicked(MouseEvent mouseEvent) {

    }

    public void label22Clicked(MouseEvent mouseEvent) {

    }

    public void listView22Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane32 and children
    public void anchorPane32Clicked(MouseEvent mouseEvent) {

    }

    public void label32Clicked(MouseEvent mouseEvent) {

    }

    public void listView32Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane42 and children
    public void anchorPane42Clicked(MouseEvent mouseEvent) {

    }

    public void label42Clicked(MouseEvent mouseEvent) {

    }

    public void listView42Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane52 and children
    public void anchorPane52Clicked(MouseEvent mouseEvent) {

    }

    public void label52Clicked(MouseEvent mouseEvent) {

    }

    public void listView52Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane62 and children
    public void anchorPane62Clicked(MouseEvent mouseEvent) {

    }

    public void label62Clicked(MouseEvent mouseEvent) {

    }

    public void listView62Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane03 and children
    public void anchorPane03Clicked(MouseEvent mouseEvent) {

    }

    public void label03Clicked(MouseEvent mouseEvent) {

    }

    public void listView03Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane13 and children
    public void anchorPane13Clicked(MouseEvent mouseEvent) {

    }

    public void label13Clicked(MouseEvent mouseEvent) {

    }

    public void listView13Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane23 and children
    public void anchorPane23Clicked(MouseEvent mouseEvent) {

    }

    public void label23Clicked(MouseEvent mouseEvent) {

    }

    public void listView23Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane33 and children
    public void anchorPane33Clicked(MouseEvent mouseEvent) {

    }

    public void label33Clicked(MouseEvent mouseEvent) {

    }

    public void listView33Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane43 and children
    public void anchorPane43Clicked(MouseEvent mouseEvent) {

    }

    public void label43Clicked(MouseEvent mouseEvent) {

    }

    public void listView43Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane53 and children
    public void anchorPane53Clicked(MouseEvent mouseEvent) {

    }

    public void label53Clicked(MouseEvent mouseEvent) {

    }

    public void listView53Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane63 and children
    public void anchorPane63Clicked(MouseEvent mouseEvent) {

    }

    public void label63Clicked(MouseEvent mouseEvent) {

    }

    public void listView63Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane04 and children
    public void anchorPane04Clicked(MouseEvent mouseEvent) {

    }

    public void label04Clicked(MouseEvent mouseEvent) {

    }

    public void listView04Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane14 and children
    public void anchorPane14Clicked(MouseEvent mouseEvent) {

    }

    public void label14Clicked(MouseEvent mouseEvent) {

    }

    public void listView14Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane24 and children
    public void anchorPane24Clicked(MouseEvent mouseEvent) {

    }

    public void label24Clicked(MouseEvent mouseEvent) {

    }

    public void listView24Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane34 and children
    public void anchorPane34Clicked(MouseEvent mouseEvent) {

    }

    public void label34Clicked(MouseEvent mouseEvent) {

    }

    public void listView34Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane44 and children
    public void anchorPane44Clicked(MouseEvent mouseEvent) {

    }

    public void label44Clicked(MouseEvent mouseEvent) {

    }

    public void listView44Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane54 and children
    public void anchorPane54Clicked(MouseEvent mouseEvent) {

    }

    public void label54Clicked(MouseEvent mouseEvent) {

    }

    public void listView54Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane64 and children
    public void anchorPane64Clicked(MouseEvent mouseEvent) {

    }

    public void label64Clicked(MouseEvent mouseEvent) {

    }

    public void listView64Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane05 and children
    public void anchorPane05Clicked(MouseEvent mouseEvent) {

    }

    public void label05Clicked(MouseEvent mouseEvent) {

    }

    public void listView05Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane15 and children
    public void anchorPane15Clicked(MouseEvent mouseEvent) {

    }

    public void label15Clicked(MouseEvent mouseEvent) {

    }

    public void listView15Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane25 and children
    public void anchorPane25Clicked(MouseEvent mouseEvent) {

    }

    public void label25Clicked(MouseEvent mouseEvent) {

    }

    public void listView25Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane35 and children
    public void anchorPane35Clicked(MouseEvent mouseEvent) {

    }

    public void label35Clicked(MouseEvent mouseEvent) {

    }

    public void listView35Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane45 and children
    public void anchorPane45Clicked(MouseEvent mouseEvent) {

    }

    public void label45Clicked(MouseEvent mouseEvent) {

    }

    public void listView45Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane55 and children
    public void anchorPane55Clicked(MouseEvent mouseEvent) {

    }

    public void label55Clicked(MouseEvent mouseEvent) {

    }

    public void listView55Clicked(MouseEvent mouseEvent) {

    }

    // OnClick functions for anchorPane65 and children
    public void anchorPane65Clicked(MouseEvent mouseEvent) {

    }

    public void label65Clicked(MouseEvent mouseEvent) {

    }

    public void listView65Clicked(MouseEvent mouseEvent) {

    }
}
