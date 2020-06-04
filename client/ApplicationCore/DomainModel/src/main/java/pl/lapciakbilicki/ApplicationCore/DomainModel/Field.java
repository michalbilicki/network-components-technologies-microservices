package pl.lapciakbilicki.ApplicationCore.DomainModel;

public class Field {

    private double surfaceArea;
    private int maxAmountOfPeople;
    private String typeOfGround;

    public Field() {

    }

    public Field(double surfaceArea, int maxAmountOfPeople, String typeOfGround) {
        this.surfaceArea = surfaceArea;
        this.maxAmountOfPeople = maxAmountOfPeople;
        this.typeOfGround = typeOfGround;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    private void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public int getMaxAmountOfPeople() {
        return maxAmountOfPeople;
    }

    private void setMaxAmountOfPeople(int maxAmountOfPeople) {
        this.maxAmountOfPeople = maxAmountOfPeople;
    }

    public String getTypeOfGround() {
        return typeOfGround;
    }

    private void setTypeOfGround(String typeOfGround) {
        this.typeOfGround = typeOfGround;
    }
}
