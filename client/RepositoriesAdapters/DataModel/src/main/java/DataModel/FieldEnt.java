package DataModel;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FieldEnt {

    @DecimalMin("10")
    private double surfaceArea;

    @Min(2)
    private int maxAmountOfPeople;

    @NotNull
    @Size(min = 1, max = 20)
    private String typeOfGround;

    public FieldEnt() {
    }

    public FieldEnt(double surfaceArea, int maxAmountOfPeople, String typeOfGround) {
        this.surfaceArea = surfaceArea;
        this.maxAmountOfPeople = maxAmountOfPeople;
        this.typeOfGround = typeOfGround;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public int getMaxAmountOfPeople() {
        return maxAmountOfPeople;
    }

    public void setMaxAmountOfPeople(int maxAmountOfPeople) {
        this.maxAmountOfPeople = maxAmountOfPeople;
    }

    public String getTypeOfGround() {
        return typeOfGround;
    }

    public void setTypeOfGround(String typeOfGround) {
        this.typeOfGround = typeOfGround;
    }
}
