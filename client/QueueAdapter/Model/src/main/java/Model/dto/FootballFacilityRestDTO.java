package Model.dto;

import lombok.Data;

import javax.json.bind.annotation.JsonbNillable;
import javax.xml.bind.annotation.*;

@JsonbNillable
@Data
public class FootballFacilityRestDTO extends SportsFacilityRestDTO {

    @XmlElement
    private boolean fullSize;

    @XmlElement
    private double widthOfGoal;

    @XmlElement
    private double heightOfGoal;

    public FootballFacilityRestDTO() {

    }

    public FootballFacilityRestDTO(String id, double pricePerHours, boolean access, FieldRestDTO field, String name, boolean fullSize, double widthOfGoal, double heightOfGoal) {
        super(id, pricePerHours, access, field, name, "");
        this.fullSize = fullSize;
        this.widthOfGoal = widthOfGoal;
        this.heightOfGoal = heightOfGoal;
    }

    public FootballFacilityRestDTO(String id, double pricePerHours, boolean access, FieldRestDTO field, String name, String type, boolean fullSize, double widthOfGoal, double heightOfGoal) {
        super(id, pricePerHours, access, field, name, type);
        this.fullSize = fullSize;
        this.widthOfGoal = widthOfGoal;
        this.heightOfGoal = heightOfGoal;
    }

}
