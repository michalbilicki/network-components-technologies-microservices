package RepositoryAdapter.converter;

import DataModel.BasketballFacilityEnt;
import DataModel.FieldEnt;
import DataModel.FootballFacilityEnt;
import DataModel.SportsFacilityEnt;
import DomainModel.BasketballType;
import DomainModel.Field;
import DomainModel.FootballType;
import DomainModel.SportsFacility;
import exceptions.RepositoryConverterException;

import java.util.UUID;

public class RepositoryFacilityConverter {

    public static SportsFacility convertTo(SportsFacilityEnt obj) throws RepositoryConverterException {
        if (obj == null)
            return null;

        if (obj instanceof BasketballFacilityEnt) {
            return new SportsFacility(
                    UUID.fromString(obj.getId()),
                    new BasketballType(((BasketballFacilityEnt) obj).getNumberOfBasket(), ((BasketballFacilityEnt) obj).getMinHeightOfBasket(), ((BasketballFacilityEnt) obj).getMaxHeightOfBasket()),
                    obj.isAccess(),
                    new Field(obj.getField().getSurfaceArea(), obj.getField().getMaxAmountOfPeople(), obj.getField().getTypeOfGround()),
                    obj.getName(),
                    obj.getPricePerHours()
            );
        } else if (obj instanceof FootballFacilityEnt) {
            return new SportsFacility(
                    UUID.fromString(obj.getId()),
                    new FootballType(((FootballFacilityEnt) obj).isFullSize(), ((FootballFacilityEnt) obj).getWidthOfGoal(), ((FootballFacilityEnt) obj).getHeightOfGoal()),
                    obj.isAccess(),
                    new Field(obj.getField().getSurfaceArea(), obj.getField().getMaxAmountOfPeople(), obj.getField().getTypeOfGround()),
                    obj.getName(),
                    obj.getPricePerHours()
            );
        } else {
            throw new RepositoryConverterException();
        }
    }

    public static SportsFacilityEnt convertFrom(SportsFacility obj) throws RepositoryConverterException {
        if (obj.getSportsFacilityType() instanceof BasketballType) {
            // String id, double pricePerHours, boolean access, FieldEnt field, String name, int numberOfBasket, double minHeightOfBasket, double maxHeightOfBasket
            return new BasketballFacilityEnt(
                    obj.getId().toString(),
                    obj.getPrice(),
                    obj.isAccess(),
                    new FieldEnt(obj.getField().getSurfaceArea(), obj.getField().getMaxAmountOfPeople(), obj.getField().getTypeOfGround()),
                    obj.getName(),
                    Integer.parseInt(obj.getSportsFacilityType().getProperties().get("numberOfBasket")),
                    Double.parseDouble(obj.getSportsFacilityType().getProperties().get("minHeightOfBasket")),
                    Double.parseDouble(obj.getSportsFacilityType().getProperties().get("maxHeightOfBasket"))
            );
        } else if (obj.getSportsFacilityType() instanceof FootballType) {
            // String id, double pricePerHours, boolean access, FieldEnt field, String name, boolean fullSize, double widthOfGoal, double heightOfGoal
            return new FootballFacilityEnt(
                    obj.getId().toString(),
                    obj.getPrice(),
                    obj.isAccess(),
                    new FieldEnt(obj.getField().getSurfaceArea(), obj.getField().getMaxAmountOfPeople(), obj.getField().getTypeOfGround()),
                    obj.getName(),
                    Boolean.parseBoolean(obj.getSportsFacilityType().getProperties().get("fullSize")),
                    Double.parseDouble(obj.getSportsFacilityType().getProperties().get("widthOfGoal")),
                    Double.parseDouble(obj.getSportsFacilityType().getProperties().get("heightOfGoal"))
            );
        } else {
            throw new RepositoryConverterException();
        }
    }
}
