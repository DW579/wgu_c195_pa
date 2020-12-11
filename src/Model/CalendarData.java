package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

public class CalendarData {
    // Calendar static
    public static LocalDate todays_date = LocalDate.now();
    public static int selected_month = todays_date.getMonthValue();
    public static int selected_year = todays_date.getYear();
    public static HashMap<Integer, String> all_months = new HashMap<>();
    public static HashMap<Integer, String> anchor_pane_values_map = new HashMap<>();
    // Customer static
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    static {
        // Input all months in all_months HashMap
        all_months.put(1, "January");
        all_months.put(2, "February");
        all_months.put(3, "March");
        all_months.put(4, "April");
        all_months.put(5, "May");
        all_months.put(6, "June");
        all_months.put(7, "July");
        all_months.put(8, "August");
        all_months.put(9, "September");
        all_months.put(10, "October");
        all_months.put(11, "November");
        all_months.put(12, "December");

        // Input initial nums of days into anchor_pane_values_map HashMap
        YearMonth yearMonthObject = YearMonth.of(selected_year, selected_month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        LocalDate localDate = LocalDate.of(selected_year, selected_month, 1);
        int first_day_of_week = localDate.getDayOfWeek().getValue();
        int i = 0;
        int day_num = 1;

        // The int for Sunday is 7, switch it to 0 for the start of the week
        if(first_day_of_week == 7) {
            first_day_of_week = 0;
        }

        // Add the days of the week that are skip at the beginning of the month
        daysInMonth = daysInMonth + first_day_of_week;

        // Put ""
        while(i < first_day_of_week) {
            anchor_pane_values_map.put(i, "");
            i += 1;
        }

        // Put days
        while(i < daysInMonth) {
            anchor_pane_values_map.put(i, Integer.toString(day_num));
            i += 1;
            day_num += 1;
        }

        // Put ""
        while(i < 40) {
            anchor_pane_values_map.put(i, "");
            i += 1;
        }
    }

    public static String getSelectedMonth() {
        return all_months.get(selected_month);
    }

    public static String getSelectedYear() {
        return Integer.toString(selected_year);
    }

    public static String updateNextMonth() {
        if(selected_month < 12) {
            selected_month += 1;
        }
        else {
            selected_month = 1;
            selected_year += 1;
        }

        return all_months.get(selected_month);
    }

    public static String updatePreviousMonth() {
        if(selected_month > 1) {
            selected_month -= 1;
        }
        else {
            selected_month = 12;
            selected_year -= 1;
        }

        return all_months.get(selected_month);
    }

    public static String getAnchorPaneValue(int pos_num) {
        return anchor_pane_values_map.get(pos_num);
    }

    public static void updateDaysNums() {
        YearMonth yearMonthObject = YearMonth.of(selected_year, selected_month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        LocalDate localDate = LocalDate.of(selected_year, selected_month, 1);
        int first_day_of_week = localDate.getDayOfWeek().getValue();
        int i = 0;
        int day_num = 1;

        // The int for Sunday is 7, switch it to 0 for the start of the week
        if(first_day_of_week == 7) {
            first_day_of_week = 0;
        }

        // Add the days of the week that are skip at the beginning of the month
        daysInMonth = daysInMonth + first_day_of_week;

        // Put ""
        while(i < first_day_of_week) {
            anchor_pane_values_map.put(i, "");
            i += 1;
        }

        // Put days
        while(i < daysInMonth) {
            anchor_pane_values_map.put(i, Integer.toString(day_num));
            i += 1;
            day_num += 1;
        }

        // Put ""
        while(i < 40) {
            anchor_pane_values_map.put(i, "");
            i += 1;
        }
    }

    public static Boolean isToday() {

        if(selected_month == LocalDate.now().getMonthValue() && selected_year == LocalDate.now().getYear()) {
            return true;
        }
        else {
            return false;
        }

    }

    // Customer calls
    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }
}
