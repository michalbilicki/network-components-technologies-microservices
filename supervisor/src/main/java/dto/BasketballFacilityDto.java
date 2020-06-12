package dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.json.bind.annotation.JsonbNillable;

@EqualsAndHashCode(callSuper = true)
@JsonbNillable
@Data
public class BasketballFacilityDto extends SportsFacilityDto {

    private int numberOfBasket;

    private double minHeightOfBasket;

    private double maxHeightOfBasket;

    public BasketballFacilityDto() {
    }

    public BasketballFacilityDto(String id, double pricePerHours, boolean access, FieldDto field, String name, int numberOfBasket, double minHeightOfBasket, double maxHeightOfBasket) {
        super(id, pricePerHours, access, field, name);
        this.numberOfBasket = numberOfBasket;
        this.minHeightOfBasket = minHeightOfBasket;
        this.maxHeightOfBasket = maxHeightOfBasket;
    }

}
