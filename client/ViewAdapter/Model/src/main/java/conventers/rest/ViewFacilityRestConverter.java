package conventers.rest;

import conventers.ViewTwoWayConverter;
import conventers.exceptions.ViewConverterException;
import pl.lapciakbilicki.ApplicationCore.DomainModel.BasketballType;
import pl.lapciakbilicki.ApplicationCore.DomainModel.Field;
import pl.lapciakbilicki.ApplicationCore.DomainModel.FootballType;
import pl.lapciakbilicki.ApplicationCore.DomainModel.SportsFacility;
import rest.BasketballFacilityRestDTO;
import rest.FieldRestDTO;
import rest.FootballFacilityRestDTO;
import rest.SportsFacilityRestDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.UUID;

@RequestScoped
@Named
public class ViewFacilityRestConverter implements ViewTwoWayConverter<SportsFacilityRestDTO, SportsFacility> {

    @Override
    public SportsFacilityRestDTO convertTo(SportsFacility arg) throws ViewConverterException {
        if (arg.getSportsFacilityType().getTypeName().equals(FootballType.class.getSimpleName())) {
            //String id, double pricePerHours, boolean access, FieldDTO field, String name, boolean fullSize, double widthOfGoal, double heightOfGoal
            return new FootballFacilityRestDTO(
                    arg.getId().toString(),
                    arg.getPrice(),
                    arg.isAccess(),
                    new FieldRestDTO(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    Boolean.parseBoolean(arg.getSportsFacilityType().getProperties().get("fullSize")),
                    Double.parseDouble(arg.getSportsFacilityType().getProperties().get("widthOfGoal")),
                    Double.parseDouble(arg.getSportsFacilityType().getProperties().get("heightOfGoal"))
            );
        } else if (arg.getSportsFacilityType().getTypeName().equals(BasketballType.class.getSimpleName())) {
            //String id, double pricePerHours, boolean access, FieldDTO field, String name, int numberOfBasket, double minHeightOfBasket, double maxHeightOfBasket
            return new BasketballFacilityRestDTO(
                    arg.getId().toString(),
                    arg.getPrice(),
                    arg.isAccess(),
                    new FieldRestDTO(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    Integer.parseInt(arg.getSportsFacilityType().getProperties().get("numberOfBasket")),
                    Double.parseDouble(arg.getSportsFacilityType().getProperties().get("minHeightOfBasket")),
                    Double.parseDouble(arg.getSportsFacilityType().getProperties().get("maxHeightOfBasket"))
            );
        } else {
            throw new ViewConverterException("Lack of such sports facility type!");
        }
    }

    @Override
    public SportsFacility convertFrom(SportsFacilityRestDTO arg) throws ViewConverterException {
        if (arg instanceof FootballFacilityRestDTO) {
            //UUID id, SportsFacilityType sportsFacilityType, boolean access, Field field, String name, double price
            return new SportsFacility(
                    UUID.fromString(arg.getId()),
                    new FootballType(
                            ((FootballFacilityRestDTO) arg).isFullSize(),
                            ((FootballFacilityRestDTO) arg).getWidthOfGoal(),
                            ((FootballFacilityRestDTO) arg).getHeightOfGoal()
                    ),
                    arg.isAccess(),
                    new Field(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    arg.getPricePerHours()
            );
        } else if (arg instanceof BasketballFacilityRestDTO) {
            //UUID id, SportsFacilityType sportsFacilityType, boolean access, Field field, String name, double price
            return new SportsFacility(
                    UUID.fromString(arg.getId()),
                    new BasketballType(
                            ((BasketballFacilityRestDTO) arg).getNumberOfBasket(),
                            ((BasketballFacilityRestDTO) arg).getMinHeightOfBasket(),
                            ((BasketballFacilityRestDTO) arg).getMaxHeightOfBasket()
                    ),
                    arg.isAccess(),
                    new Field(arg.getField().getSurfaceArea(), arg.getField().getMaxAmountOfPeople(), arg.getField().getTypeOfGround()),
                    arg.getName(),
                    arg.getPricePerHours()
            );
        } else {
            throw new ViewConverterException("Lack of such sports facility type!");
        }
    }
}
