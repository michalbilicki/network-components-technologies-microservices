package pl.lapciakbilicki.ApplicationCore.DomainModel;

import java.util.HashMap;

public abstract class SportsFacilityType {

    private String typeName;
    private HashMap<String, String> properties;

    public HashMap<String, String> getProperties() {
        return properties;
    }

    protected void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public String getTypeName() {
        return typeName;
    }

    protected void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
