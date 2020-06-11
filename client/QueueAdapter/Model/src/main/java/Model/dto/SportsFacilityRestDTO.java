package Model.dto;

import lombok.Data;

import javax.json.bind.annotation.JsonbNillable;
import java.io.Serializable;

@Data
@JsonbNillable
public abstract class SportsFacilityRestDTO implements Serializable {

    private String id;
    private double pricePerHours;
    private boolean access;
    private FieldRestDTO field;
    private String name;
    private String sportsFacilityType = this.getClass().getSimpleName();
    
    public SportsFacilityRestDTO() {

    }

    public SportsFacilityRestDTO(String id, double pricePerHours, boolean access, FieldRestDTO field, String name, String type) {
        this.id = id;
        this.pricePerHours = pricePerHours;
        this.access = access;
        this.field = field;
        this.name = name;
    }

}
