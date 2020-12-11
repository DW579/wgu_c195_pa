package Model;

import javafx.beans.property.*;

public class Customer {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty addressId;
    private final IntegerProperty active;
    private final StringProperty createDate;
    private final StringProperty createdBy;
    private final StringProperty lastUpdate;
    private final StringProperty lastUpdateBy;

    public Customer(int id, String name, int addressId, int active, String createDate, String createdBy, String lastUpdate, String lastUpdateBy) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.addressId = new SimpleIntegerProperty(addressId);
        this.active = new SimpleIntegerProperty(active);
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

    // Get/Set/Property name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Get/Set/Property addressId
    public int getAddressId() {
        return addressId.get();
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }

    public IntegerProperty addressIdProperty() {
        return addressId;
    }

    // Get/Set/Property active
    public int getActive() {
        return active.get();
    }

    public void setActive(int active) {
        this.active.set(active);
    }

    public IntegerProperty activeProperty() {
        return active;
    }

    // Get/Set/Property createDate
    public String getCreateDate() {
        return createDate.get();
    }

    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
    }

    public StringProperty nameCreateDate() {
        return createDate;
    }

    // Get/Set/Property createdBy
    public String getCreatedBy() {
        return createdBy.get();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public StringProperty nameCreatedBy() {
        return createdBy;
    }

    // Get/Set/Property lastUpdate
    public String getLastUpdate() {
        return lastUpdate.get();
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    public StringProperty nameLastUpdate() {
        return lastUpdate;
    }

    // Get/Set/Property lastUpdateBy
    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
    }

    public StringProperty nameLastUpdateBy() {
        return lastUpdateBy;
    }

}
