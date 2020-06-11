package Model;

import DomainModel.BasketballType;
import DomainModel.Field;
import DomainModel.FootballType;
import DomainModel.SportsFacility;
import Model.dto.BasketballFacilityDto;
import Model.dto.FieldDto;
import Model.dto.FootballFacilityDto;
import Model.dto.SportsFacilityDto;
import exceptions.ViewConverterException;

import java.util.UUID;

public class ViewFacilityConverter {

    public static SportsFacilityDto convertToDto(SportsFacility arg) throws ViewConverterException {
        if (arg.getSportsFacilityType().getTypeName().equals(FootballType.class.getSimpleName())) {
            //String id, double pricePerHours, boolean access, FieldDTO field, String name, boolean fullSize, double widthOfGoal, double heightOfGoal
            return new FootballFacilityDto(
                    arg.getId().toString(),
                    arg.getPrice(),
                    arg.isAccess(),
                    new FieldDto(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    Boolean.parseBoolean(arg.getSportsFacilityType().getProperties().get("fullSize")),
                    Double.parseDouble(arg.getSportsFacilityType().getProperties().get("widthOfGoal")),
                    Double.parseDouble(arg.getSportsFacilityType().getProperties().get("heightOfGoal"))
            );
        } else if (arg.getSportsFacilityType().getTypeName().equals(BasketballType.class.getSimpleName())) {
            //String id, double pricePerHours, boolean access, FieldDTO field, String name, int numberOfBasket, double minHeightOfBasket, double maxHeightOfBasket
            return new BasketballFacilityDto(
                    arg.getId().toString(),
                    arg.getPrice(),
                    arg.isAccess(),
                    new FieldDto(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    Integer.parseInt(arg.getSportsFacilityType().getProperties().get("numberOfBasket")),
                    Double.parseDouble(arg.getSportsFacilityType().getProperties().get("minHeightOfBasket")),
                    Double.parseDouble(arg.getSportsFacilityType().getProperties().get("maxHeightOfBasket"))
            );
        } else {
            throw new ViewConverterException();
        }
    }

    public static SportsFacility convertFromDto(SportsFacilityDto arg) throws ViewConverterException {
        if (arg.getSportsFacilityType().equals("FootballFacilityDto")) {
            //UUID id, SportsFacilityType sportsFacilityType, boolean access, Field field, String name, double price
            return new SportsFacility(
                    UUID.fromString(arg.getId()),
                    new FootballType(
                            ((FootballFacilityDto) arg).isFullSize(),
                            ((FootballFacilityDto) arg).getWidthOfGoal(),
                            ((FootballFacilityDto) arg).getHeightOfGoal()
                    ),
                    arg.isAccess(),
                    new Field(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    arg.getPricePerHours()
            );
        } else if (arg.getSportsFacilityType().equals("BasketballFacilityDto")) {
            //UUID id, SportsFacilityType sportsFacilityType, boolean access, Field field, String name, double price
            return new SportsFacility(
                    UUID.fromString(arg.getId()),
                    new BasketballType(
                            ((BasketballFacilityDto) arg).getNumberOfBasket(),
                            ((BasketballFacilityDto) arg).getMinHeightOfBasket(),
                            ((BasketballFacilityDto) arg).getMaxHeightOfBasket()
                    ),
                    arg.isAccess(),
                    new Field(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    arg.getPricePerHours()
            );
        } else {
            throw new ViewConverterException();
        }
    }

    public static SportsFacility convertFrom(SportsFacilityDto arg) throws ViewConverterException {
        if (arg instanceof FootballFacilityDto) {
            //UUID id, SportsFacilityType sportsFacilityType, boolean access, Field field, String name, double price
            return new SportsFacility(
                    UUID.fromString(arg.getId()),
                    new FootballType(
                            ((FootballFacilityDto) arg).isFullSize(),
                            ((FootballFacilityDto) arg).getWidthOfGoal(),
                            ((FootballFacilityDto) arg).getHeightOfGoal()
                    ),
                    arg.isAccess(),
                    new Field(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    arg.getPricePerHours()
            );
        } else if (arg instanceof BasketballFacilityDto) {
            //UUID id, SportsFacilityType sportsFacilityType, boolean access, Field field, String name, double price
            return new SportsFacility(
                    UUID.fromString(arg.getId()),
                    new BasketballType(
                            ((BasketballFacilityDto) arg).getNumberOfBasket(),
                            ((BasketballFacilityDto) arg).getMinHeightOfBasket(),
                            ((BasketballFacilityDto) arg).getMaxHeightOfBasket()
                    ),
                    arg.isAccess(),
                    new Field(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    arg.getPricePerHours()
            );
        } else {
            throw new ViewConverterException();
        }
    }
}
