package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {
    private final IntegerProperty id;
    private final IntegerProperty customerId;
    private final IntegerProperty userId;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty location;
    private final StringProperty contact;
    private final StringProperty type;
    private final StringProperty url;
    private final StringProperty start;
    private final StringProperty end;
    private final StringProperty createDate;
    private final StringProperty createdBy;
    private final StringProperty lastUpdate;
    private final StringProperty lastUpdateBy;

    public Appointment(int id, int customerId, int userId, String title, String description, String location, String contact, String type, String url, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdateBy) {
        this.id = new SimpleIntegerProperty(id);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.userId = new SimpleIntegerProperty(userId);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.type = new SimpleStringProperty(type);
        this.url = new SimpleStringProperty(url);
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
        this.createDate = new SimpleStringProperty(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = new SimpleStringProperty(lastUpdate);
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }

    // Get/Set/Property id
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Get/Set/Property customerId
    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int id) {
        this.customerId.set(id);
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    // Get/Set/Property title
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    // Get/Set/Property type
    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    // Get/Set/Property start
    public String getStart() {
        return start.get();
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public StringProperty startProperty() {
        return start;
    }

    // Get/Set/Property end
    public String getEnd() {
        return end.get();
    }

    public void setEnd(String end) {
        this.end.set(end);
    }

    public StringProperty endProperty() {
        return end;
    }
}
