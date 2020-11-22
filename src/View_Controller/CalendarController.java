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

import javax.swing.text.View;
import java.time.DayOfWeek;
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

    private boolean month_view = true;
    LocalDate current_date = LocalDate.now();

    private int current_month = current_date.getMonthValue();
    private int current_year = current_date.getYear();

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

    public void previousMonthEntered(MouseEvent mouseEvent) {
        PreviousMonthArrow.setTextFill(Color.web("#0076a3"));
    }

    public void previousMonthExited(MouseEvent mouseEvent) {
        PreviousMonthArrow.setTextFill(Color.web("#000000"));
    }

    // Next month arrow
    public void nextMonthClicked(MouseEvent mouseEvent) {
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
}
