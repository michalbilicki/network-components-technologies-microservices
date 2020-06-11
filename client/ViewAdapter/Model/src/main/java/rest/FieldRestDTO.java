package rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbNillable;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@JsonbNillable
@AllArgsConstructor
@NoArgsConstructor
public class FieldRestDTO implements Serializable {

    private double surfaceArea;
    private int maxAmountOfPeople;
    private String typeOfGround;

}
