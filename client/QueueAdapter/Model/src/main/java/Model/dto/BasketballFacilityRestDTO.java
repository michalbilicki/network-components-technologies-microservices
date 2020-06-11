package Model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.json.bind.annotation.JsonbNillable;
import javax.xml.bind.annotation.*;

@EqualsAndHashCode(callSuper = true)
@JsonbNillable
@Data
public class BasketballFacilityRestDTO extends SportsFacilityRestDTO {


    @XmlElement
    private int numberOfBasket;

    @XmlElement
    private double minHeightOfBasket;

    @XmlElement
    private double maxHeightOfBasket;

    public BasketballFacilityRestDTO() {

    }

    public BasketballFacilityRestDTO(String id, double pricePerHours, boolean access, FieldRestDTO field, String name, int numberOfBasket, double minHeightOfBasket, double maxHeightOfBasket) {
        super(id, pricePerHours, access, field, name, "");
        this.numberOfBasket = numberOfBasket;
        this.minHeightOfBasket = minHeightOfBasket;
        this.maxHeightOfBasket = maxHeightOfBasket;
    }

    public BasketballFacilityRestDTO(String id, double pricePerHours, boolean access, FieldRestDTO field, String name, String type, int numberOfBasket, double minHeightOfBasket, double maxHeightOfBasket) {
        super(id, pricePerHours, access, field, name, type);
        this.numberOfBasket = numberOfBasket;
        this.minHeightOfBasket = minHeightOfBasket;
        this.maxHeightOfBasket = maxHeightOfBasket;
    }

}
