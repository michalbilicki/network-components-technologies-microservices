package DataModel;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BasketballFacilityEnt extends SportsFacilityEnt {

    @NotNull
    @Min(1)
    private int numberOfBasket;

    @NotNull
    @DecimalMin("1.5")
    private double minHeightOfBasket;

    @DecimalMin("1.5")
    private double maxHeightOfBasket;

    public BasketballFacilityEnt() {

    }

    public BasketballFacilityEnt(String id, double pricePerHours, boolean access, FieldEnt field, String name, int numberOfBasket, double minHeightOfBasket, double maxHeightOfBasket) {
        super(id, pricePerHours, access, field, name);
        this.numberOfBasket = numberOfBasket;
        this.minHeightOfBasket = minHeightOfBasket;
        this.maxHeightOfBasket = maxHeightOfBasket;
    }

    public int getNumberOfBasket() {
        return numberOfBasket;
    }

    public void setNumberOfBasket(int numberOfBasket) {
        this.numberOfBasket = numberOfBasket;
    }

    public double getMinHeightOfBasket() {
        return minHeightOfBasket;
    }

    public void setMinHeightOfBasket(double minHeightOfBasket) {
        this.minHeightOfBasket = minHeightOfBasket;
    }

    public double getMaxHeightOfBasket() {
        return maxHeightOfBasket;
    }

    public void setMaxHeightOfBasket(double maxHeightOfBasket) {
        this.maxHeightOfBasket = maxHeightOfBasket;
    }

    @Override
    public void copyAttributionsWithoutId(SportsFacilityEnt sportsFacility) {
        super.copyAttributionsWithoutId(sportsFacility);
        BasketballFacilityEnt basketballFacility = (BasketballFacilityEnt) sportsFacility;
        this.maxHeightOfBasket = basketballFacility.maxHeightOfBasket;
        this.minHeightOfBasket = basketballFacility.minHeightOfBasket;
        this.numberOfBasket = basketballFacility.numberOfBasket;
    }
}
