package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public static void deleteCustomer(Customer selectedCustomer) {
        allCustomers.remove(selectedCustomer);
    }

    public static void updateCustomer(int customerId, String customerName, String address, String address2, String city, String postalCode, String country, String phone) {
        allCustomers.forEach((customer) -> {
            if(customer.getId() == customerId) {
                Boolean error = false;
                int idCountry = 1;
                int idCity = 1;
                int idAddress = 1;

                // Check against Countries in DB
                // If Country already in DB, use it's countryId. If not, then Insert a new entry and use that new countryId
                try {
                    Statement dbConnectionStatement = DBConnection.getConnection().createStatement();
                    String queryForCountry = "SELECT * FROM country WHERE country='" + country + "'";
                    ResultSet rs = dbConnectionStatement.executeQuery(queryForCountry);

                    if(rs.next()) {
                        idCountry = rs.getInt("countryId");
                    }
                    else {
                        String queryAllCountries = "SELECT * FROM country";
                        ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCountries);

                        rs2.last();

                        // If there are entries in country table, set idCountry to be +1 of last entry's id
                        if(rs2.getRow() > 0) {
                            idCountry = rs2.getInt("countryId") + 1;
                        }

                        String insertNewCountry = "INSERT INTO country VALUES (" + idCountry + ",'" + country + "','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                        dbConnectionStatement.executeUpdate(insertNewCountry);
                    }
                }
                catch (SQLException e) {
                    System.out.println("SQLException error: " + e.getMessage());
                    error = true;
                }

                // Check against Cities in DB. If error from Country try/catch do not run
                if(!error) {
                    try {
                        Statement dbConnectionStatement = DBConnection.getConnection().createStatement();

                        String queryForCityAndCountry = "SELECT * FROM city WHERE city='" + city + "' AND countryId='" + idCountry + "'";
                        ResultSet rs = dbConnectionStatement.executeQuery(queryForCityAndCountry);

                        // If statement returns an entry then no need to INSERT another entry but grab the cityId
                        if(rs.next()) {
                            idCity = rs.getInt("cityId");
                        }
                        //Else, INSERT a new entry
                        else {
                            String queryAllCities = "SELECT * FROM city";
                            ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllCities);

                            rs2.last();

                            // If there are entries in city table, set idCity to be +1 of last entry's id
                            if(rs2.getRow() > 0) {
                                idCity = rs2.getInt("cityId") + 1;
                            }

                            String insertNewCity = "INSERT INTO city VALUES (" + idCity + ",'" + city + "'," + idCountry + ",'2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                            dbConnectionStatement.executeUpdate(insertNewCity);
                        }
                    }
                    catch (SQLException e) {
                        System.out.println("SQLException error: " + e.getMessage());
                        error = true;
                    }
                }

                // Check against Addresses in DB. If error from Country or City try/catch do not run
                if(!error) {
                    try {
                        Statement dbConnectionStatement = DBConnection.getConnection().createStatement();

                        String queryForAddressAndCity = "SELECT * FROM address WHERE address='" + address + "' AND address2='" + address2 + "' AND cityId='" + idCity + "'";
                        ResultSet rs = dbConnectionStatement.executeQuery(queryForAddressAndCity);

                        if(rs.next()) {
                            System.out.println("There is a Address/City entry");

                            idAddress = rs.getInt("addressId");

                            System.out.println("idAddress = " + Integer.toString(idAddress));
                        }
                        else {
                            System.out.println("No Address/City entry");

                            String queryAllAddresses = "SELECT * FROM address";
                            ResultSet rs2 = dbConnectionStatement.executeQuery(queryAllAddresses);

                            rs2.last();

                            // If there are entries in address table, set idAddress to be +1 of last entry's id
                            if(rs2.getRow() > 0) {
                                idAddress = rs2.getInt("addressId") + 1;
                            }

                            System.out.println("idAddress = " + Integer.toString(idAddress));

                            String insertNewAddress = "INSERT INTO address VALUES (" + idAddress + ",'" + address + "','" + address2 + "'," + idCity + ",'" + postalCode + "','" + phone + "','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test');";
                            dbConnectionStatement.executeUpdate(insertNewAddress);

                            customer.setAddress(address);
                        }
                    }
                    catch (SQLException e) {
                        System.out.println("SQLException error: " + e.getMessage());
                        error = true;
                    }
                }

                // Set update name in DB and ObservableList
                if(!error) {
                    try {
                        Statement dbConnectionStatement = DBConnection.getConnection().createStatement();

                        // Update Customer Name
                        String queryUpdateCustomerName = "UPDATE customer SET customerName='" + customerName + "', addressId=" + Integer.toString(idAddress) + " WHERE customerId=" + Integer.toString(customerId);
                        dbConnectionStatement.execute(queryUpdateCustomerName);
                    }
                    catch (SQLException e) {
                        System.out.println("SQLException error: " + e.getMessage());
                    }

                    // Set update name in Object
                    customer.setName(customerName);
                    customer.setAddressId(idAddress);
                }

            }
        });

    }
}
