package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbNillable;
import java.io.Serializable;

@Data
@JsonbNillable
@AllArgsConstructor
@NoArgsConstructor
public class FieldDto implements Serializable {

    private double surfaceArea;

    private int maxAmountOfPeople;

    private String typeOfGround;

}
