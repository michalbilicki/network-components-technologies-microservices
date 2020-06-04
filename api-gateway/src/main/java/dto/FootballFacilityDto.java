package dto;

import lombok.Data;

import javax.json.bind.annotation.JsonbNillable;

@JsonbNillable
@Data
public class FootballFacilityDto extends SportsFacilityDto {

    private boolean fullSize;

    private double widthOfGoal;

    private double heightOfGoal;

    public FootballFacilityDto() {

    }

    public FootballFacilityDto(String id, double pricePerHours, boolean access, FieldDto field, String name, boolean fullSize, double widthOfGoal, double heightOfGoal) {
        super(id, pricePerHours, access, field, name, "");
        this.fullSize = fullSize;
        this.widthOfGoal = widthOfGoal;
        this.heightOfGoal = heightOfGoal;
    }

    public FootballFacilityDto(String id, double pricePerHours, boolean access, FieldDto field, String name, String type, boolean fullSize, double widthOfGoal, double heightOfGoal) {
        super(id, pricePerHours, access, field, name, type);
        this.fullSize = fullSize;
        this.widthOfGoal = widthOfGoal;
        this.heightOfGoal = heightOfGoal;
    }

}
