package Model.dto;

import lombok.Data;

import javax.json.bind.annotation.JsonbNillable;
import java.io.Serializable;

@Data
@JsonbNillable
public class SportsFacilityDto implements Serializable {

    private String id;
    private double pricePerHours;
    private boolean access;
    private FieldDto field;
    private String name;
    private String sportsFacilityType = this.getClass().getSimpleName();

    public SportsFacilityDto() {

    }

    public SportsFacilityDto(String id, double pricePerHours, boolean access, FieldDto field, String name) {
        this.id = id;
        this.pricePerHours = pricePerHours;
        this.access = access;
        this.field = field;
        this.name = name;
    }
}
