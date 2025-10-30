package aydin.firebasedemo;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccessDataView {

    private final StringProperty personName = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty(); // new phone property
    private final int age = 0;
    private final ReadOnlyBooleanWrapper writePossible = new ReadOnlyBooleanWrapper();

    public AccessDataView() {
        // allow write only when both name and phone are not empty
        writePossible.bind(personName.isNotEmpty().and(phone.isNotEmpty()));
    }

    public StringProperty personNameProperty() {
        return personName;
    }

    public StringProperty phoneProperty() { // getter for phone property
        return phone;
    }

    public ReadOnlyBooleanProperty isWritePossibleProperty() {
        return writePossible.getReadOnlyProperty();
    }
}