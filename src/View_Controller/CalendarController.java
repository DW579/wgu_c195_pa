package View_Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import javax.swing.*;
import javax.swing.border.Border;
import java.time.LocalDate;
import java.time.YearMonth;
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

    private boolean month_view = true;
    LocalDate current_date = LocalDate.now();
    private int current_month = current_date.getMonthValue();
    private int current_year = current_date.getYear();
    AnchorPane current_anchor = (AnchorPane) AnchorPane03;

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

    // Previous month arrow
    public void previousMonthClicked(MouseEvent mouseEvent) {
        // If month_view == true then we are viewing the calendar as a month and we change the
        // month and year by months, else we are viewing it as a week
        if(month_view) {
            // Decrease current_mont int
            if(current_month == 1) {
                current_month = 12;

                current_year -= 1;
                Year.setText(Integer.toString(current_year));
            }
            else {
                current_month -= 1;
            }

            // Change the text of Month to reflect current_month int
            if(current_month == 1) {
                Month.setText("January");
            }
            else if(current_month == 2) {
                Month.setText("February");
            }
            else if(current_month == 3) {
                Month.setText("March");
            }
            else if(current_month == 4) {
                Month.setText("April");
            }
            else if(current_month == 5) {
                Month.setText("May");
            }
            else if(current_month == 6) {
                Month.setText("June");
            }
            else if(current_month == 7) {
                Month.setText("July");
            }
            else if(current_month == 8) {
                Month.setText("August");
            }
            else if(current_month == 9) {
                Month.setText("September");
            }
            else if(current_month == 10) {
                Month.setText("October");
            }
            else if(current_month == 11) {
                Month.setText("November");
            }
            else if(current_month == 12) {
                Month.setText("December");
            }

            // Change the numbers on the days of the weeks in month
            // Find the number of days in the month
            YearMonth yearMonthObject = YearMonth.of(current_year, current_month);
            int daysInMonth = yearMonthObject.lengthOfMonth();
            LocalDate localDate = LocalDate.of(current_year, current_month, 1);
            int first_day_of_week = localDate.getDayOfWeek().getValue();
            int i = 0;
            int day_num = 1;

            // The int for Sunday is 7, switch it to 0 for the start of the week
            if(first_day_of_week == 7) {
                first_day_of_week = 0;
            }

            // Add the days of the week that are skip at the beginning of the month
            daysInMonth = daysInMonth + first_day_of_week;

            // Loop through and change each number
            ObservableList month_grid_children = MonthGridPane.getChildren();

            // Set labels to no number for the days before the first day of the month
            while(i < first_day_of_week) {
                AnchorPane anchor_pane = (AnchorPane) month_grid_children.get(i);
                ObservableList anchor_pane_children = anchor_pane.getChildren();
                Label label = (Label) anchor_pane_children.get(0);
                label.setText("");
                i += 1;
            }

            // Set labels to the numbers of each day
            while(i < daysInMonth) {
                AnchorPane anchor_pane = (AnchorPane) month_grid_children.get(i);
                ObservableList anchor_pane_children = anchor_pane.getChildren();
                Label label = (Label) anchor_pane_children.get(0);
                label.setText(Integer.toString(day_num));
                i += 1;
                day_num += 1;
            }

            // Set labels to empty for the days that not included for the current month
            while(i < 40) {
                AnchorPane anchor_pane = (AnchorPane) month_grid_children.get(i);
                ObservableList anchor_pane_children = anchor_pane.getChildren();
                Label label = (Label) anchor_pane_children.get(0);
                label.setText("");
                i += 1;
            }
        }
        else {

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
        // If month_view == true then we are viewing the calendar as a month and we change the
        // month and year by months, else we are viewing it as a week
        if(month_view) {
            // Increase current_mont int
            if(current_month == 12) {
                current_month = 1;

                current_year += 1;
                Year.setText(Integer.toString(current_year));
            }
            else {
                current_month += 1;
            }

            // Change the text of Month to reflect current_month int
            if(current_month == 1) {
                Month.setText("January");
            }
            else if(current_month == 2) {
                Month.setText("February");
            }
            else if(current_month == 3) {
                Month.setText("March");
            }
            else if(current_month == 4) {
                Month.setText("April");
            }
            else if(current_month == 5) {
                Month.setText("May");
            }
            else if(current_month == 6) {
                Month.setText("June");
            }
            else if(current_month == 7) {
                Month.setText("July");
            }
            else if(current_month == 8) {
                Month.setText("August");
            }
            else if(current_month == 9) {
                Month.setText("September");
            }
            else if(current_month == 10) {
                Month.setText("October");
            }
            else if(current_month == 11) {
                Month.setText("November");
            }
            else if(current_month == 12) {
                Month.setText("December");
            }

            // Change the numbers on the days of the weeks in month
            // Find the number of days in the month
            YearMonth yearMonthObject = YearMonth.of(current_year, current_month);
            int daysInMonth = yearMonthObject.lengthOfMonth();
            LocalDate localDate = LocalDate.of(current_year, current_month, 1);
            int first_day_of_week = localDate.getDayOfWeek().getValue();
            int i = 0;
            int day_num = 1;

            // The int for Sunday is 7, switch it to 0 for the start of the week
            if(first_day_of_week == 7) {
                first_day_of_week = 0;
            }

            // Add the days of the week that are skip at the beginning of the month
            daysInMonth = daysInMonth + first_day_of_week;

            // Loop through and change each number
            ObservableList month_grid_children = MonthGridPane.getChildren();

            // Set labels to no number for the days before the first day of the month
            while(i < first_day_of_week) {
                AnchorPane anchor_pane = (AnchorPane) month_grid_children.get(i);
                ObservableList anchor_pane_children = anchor_pane.getChildren();
                Label label = (Label) anchor_pane_children.get(0);
                label.setText("");
                i += 1;
            }

            // Set labels to the numbers of each day
            while(i < daysInMonth) {
                AnchorPane anchor_pane = (AnchorPane) month_grid_children.get(i);
                ObservableList anchor_pane_children = anchor_pane.getChildren();
                Label label = (Label) anchor_pane_children.get(0);
                label.setText(Integer.toString(day_num));
                i += 1;
                day_num += 1;
            }

            // Set labels to empty for the days that not included for the current month
            while(i < 40) {
                AnchorPane anchor_pane = (AnchorPane) month_grid_children.get(i);
                ObservableList anchor_pane_children = anchor_pane.getChildren();
                Label label = (Label) anchor_pane_children.get(0);
                label.setText("");
                i += 1;
            }
        }
        else {

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
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane00.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane00;
    }

    public void label00Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane00.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane00;
    }

    public void listView00Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane00.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane00;
    }

    // OnClick functions for anchorPane10 and children
    public void anchorPane10Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane10.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane10;
    }

    public void label10Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane10.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane10;
    }

    public void listView10Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane10.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane10;
    }

    // OnClick functions for anchorPane20 and children
    public void anchorPane20Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane20.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane20;
    }

    public void label20Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane20.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane20;
    }

    public void listView20Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane20.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane20;
    }

    // OnClick functions for anchorPane30 and children
    public void anchorPane30Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane30.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane30;
    }

    public void label30Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane30.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane30;
    }

    public void listView30Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane30.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane30;
    }

    // OnClick functions for anchorPane40 and children
    public void anchorPane40Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane40.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane40;
    }

    public void label40Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane40.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane40;
    }

    public void listView40Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane40.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane40;
    }

    // OnClick functions for anchorPane50 and children
    public void anchorPane50Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane50.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane50;
    }

    public void label50Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane50.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane50;
    }

    public void listView50Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane50.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane50;
    }

    // OnClick functions for anchorPane60 and children
    public void anchorPane60Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane60.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane60;
    }

    public void label60Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane60.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane60;
    }

    public void listView60Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane60.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane60;
    }

    // OnClick functions for anchorPane01 and children
    public void anchorPane01Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane01.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane01;
    }

    public void label01Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane01.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane01;
    }

    public void listView01Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane01.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane01;
    }

    // OnClick functions for anchorPane11 and children
    public void anchorPane11Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane11.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane11;
    }

    public void label11Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane11.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane11;
    }

    public void listView11Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane11.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane11;
    }

    // OnClick functions for anchorPane21 and children
    public void anchorPane21Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane21.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane21;
    }

    public void label21Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane21.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane21;
    }

    public void listView21Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane21.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane21;
    }

    // OnClick functions for anchorPane31 and children
    public void anchorPane31Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane31.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane31;
    }

    public void label31Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane31.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane31;
    }

    public void listView31Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane31.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane31;
    }

    // OnClick functions for anchorPane41 and children
    public void anchorPane41Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane41.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane41;
    }

    public void label41Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane41.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane41;
    }

    public void listView41Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane41.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane41;
    }

    // OnClick functions for anchorPane51 and children
    public void anchorPane51Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane51.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane51;
    }

    public void label51Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane51.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane51;
    }

    public void listView51Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane51.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane51;
    }

    // OnClick functions for anchorPane61 and children
    public void anchorPane61Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane61.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane61;
    }

    public void label61Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane61.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane61;
    }

    public void listView61Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane61.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane61;
    }

    // OnClick functions for anchorPane02 and children
    public void anchorPane02Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane02.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane02;
    }

    public void label02Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane02.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane02;
    }

    public void listView02Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane02.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane02;
    }

    // OnClick functions for anchorPane12 and children
    public void anchorPane12Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane12.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane12;
    }

    public void label12Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane12.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane12;
    }

    public void listView12Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane12.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane12;
    }

    // OnClick functions for anchorPane22 and children
    public void anchorPane22Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane22.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane22;
    }

    public void label22Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane22.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane22;
    }

    public void listView22Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane22.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane22;
    }

    // OnClick functions for anchorPane32 and children
    public void anchorPane32Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane32.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane32;
    }

    public void label32Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane32.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane32;
    }

    public void listView32Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane32.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane32;
    }

    // OnClick functions for anchorPane42 and children
    public void anchorPane42Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane42.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane42;
    }

    public void label42Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane42.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane42;
    }

    public void listView42Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane42.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane42;
    }

    // OnClick functions for anchorPane52 and children
    public void anchorPane52Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane52.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane52;
    }

    public void label52Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane52.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane52;
    }

    public void listView52Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane52.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane52;
    }

    // OnClick functions for anchorPane62 and children
    public void anchorPane62Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane62.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane62;
    }

    public void label62Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane62.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane62;
    }

    public void listView62Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane62.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane62;
    }

    // OnClick functions for anchorPane03 and children
    public void anchorPane03Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane03.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane03;
    }

    public void label03Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane03.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane03;
    }

    public void listView03Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane03.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane03;
    }

    // OnClick functions for anchorPane13 and children
    public void anchorPane13Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane13.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane13;
    }

    public void label13Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane13.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane13;
    }

    public void listView13Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane13.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane13;
    }

    // OnClick functions for anchorPane23 and children
    public void anchorPane23Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane23.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane23;
    }

    public void label23Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane23.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane23;
    }

    public void listView23Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane23.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane23;
    }

    // OnClick functions for anchorPane33 and children
    public void anchorPane33Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane33.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane33;
    }

    public void label33Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane33.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane33;
    }

    public void listView33Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane33.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane33;
    }

    // OnClick functions for anchorPane43 and children
    public void anchorPane43Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane43.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane43;
    }

    public void label43Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane43.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane43;
    }

    public void listView43Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane43.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane43;
    }

    // OnClick functions for anchorPane53 and children
    public void anchorPane53Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane53.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane53;
    }

    public void label53Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane53.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane53;
    }

    public void listView53Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane53.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane53;
    }

    // OnClick functions for anchorPane63 and children
    public void anchorPane63Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane63.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane63;
    }

    public void label63Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane63.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane63;
    }

    public void listView63Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane63.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane63;
    }

    // OnClick functions for anchorPane04 and children
    public void anchorPane04Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane04.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane04;
    }

    public void label04Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane04.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane04;
    }

    public void listView04Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane04.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane04;
    }

    // OnClick functions for anchorPane14 and children
    public void anchorPane14Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane14.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane14;
    }

    public void label14Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane14.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane14;
    }

    public void listView14Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane14.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane14;
    }

    // OnClick functions for anchorPane24 and children
    public void anchorPane24Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane24.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane24;
    }

    public void label24Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane24.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane24;
    }

    public void listView24Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane24.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane24;
    }

    // OnClick functions for anchorPane34 and children
    public void anchorPane34Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane34.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane34;
    }

    public void label34Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane34.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane34;
    }

    public void listView34Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane34.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane34;
    }

    // OnClick functions for anchorPane44 and children
    public void anchorPane44Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane44.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane44;
    }

    public void label44Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane44.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane44;
    }

    public void listView44Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane44.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane44;
    }

    // OnClick functions for anchorPane54 and children
    public void anchorPane54Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane54.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane54;
    }

    public void label54Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane54.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane54;
    }

    public void listView54Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane54.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane54;
    }

    // OnClick functions for anchorPane64 and children
    public void anchorPane64Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane64.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane64;
    }

    public void label64Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane64.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane64;
    }

    public void listView64Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane64.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane64;
    }

    // OnClick functions for anchorPane05 and children
    public void anchorPane05Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane05.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane05;
    }

    public void label05Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane05.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane05;
    }

    public void listView05Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane05.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane05;
    }

    // OnClick functions for anchorPane15 and children
    public void anchorPane15Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane15.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane15;
    }

    public void label15Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane15.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane15;
    }

    public void listView15Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane15.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane15;
    }

    // OnClick functions for anchorPane25 and children
    public void anchorPane25Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane25.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane25;
    }

    public void label25Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane25.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane25;
    }

    public void listView25Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane25.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane25;
    }

    // OnClick functions for anchorPane35 and children
    public void anchorPane35Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane35.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane35;
    }

    public void label35Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane35.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane35;
    }

    public void listView35Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane35.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane35;
    }

    // OnClick functions for anchorPane45 and children
    public void anchorPane45Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane45.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane45;
    }

    public void label45Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane45.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane45;
    }

    public void listView45Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane45.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane45;
    }

    // OnClick functions for anchorPane55 and children
    public void anchorPane55Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane55.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane55;
    }

    public void label55Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane55.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane55;
    }

    public void listView55Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane55.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane55;
    }

    // OnClick functions for anchorPane65 and children
    public void anchorPane65Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane65.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane65;
    }

    public void label65Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane65.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane65;
    }

    public void listView65Clicked(MouseEvent mouseEvent) {
        // Set all labels to color #000000
        ObservableList month_grid_children = MonthGridPane.getChildren();
        for (int i = 0; i < 42; i++) {
            AnchorPane current_anchor = (AnchorPane) month_grid_children.get(i);
            ObservableList current_anchor_children = current_anchor.getChildren();
            Label label = (Label) current_anchor_children.get(0);
            label.setTextFill(Color.web("#000000"));
        }

        // Set select anchor pane label color to #ffa500
        ObservableList current_anchor_pane_children = AnchorPane65.getChildren();
        Label current_label = (Label) current_anchor_pane_children.get(0);
        current_label.setTextFill(Color.web("#ffa500"));

        // Update current_anchor
        current_anchor = (AnchorPane) AnchorPane65;
    }
}
