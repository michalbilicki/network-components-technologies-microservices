package pl.lapciakbilicki.RepositoryAdapter.conventer;

import pl.lapciakbilicki.ApplicationCore.DomainModel.*;
import pl.lapciakbilicki.DataModel.sportsfacility.BasketballFacilityEnt;
import pl.lapciakbilicki.DataModel.sportsfacility.FieldEnt;
import pl.lapciakbilicki.DataModel.sportsfacility.FootballFacilityEnt;
import pl.lapciakbilicki.DataModel.sportsfacility.SportsFacilityEnt;
import pl.lapciakbilicki.RepositoryAdapter.conventer.exception.RepositoryConverterException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.UUID;

@RequestScoped
@Named
public class RepositoryFacilityConverter implements RepositoryTwoWayConverter<SportsFacility, SportsFacilityEnt> {

    @Override
    public SportsFacility convertTo(SportsFacilityEnt obj) throws RepositoryConverterException {
        if(obj == null)
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
            throw new RepositoryConverterException("SportFacilityEnt is neither BasketballFacilityEnt nor FootballFacilityEnt");
        }
    }

    @Override
    public SportsFacilityEnt convertFrom(SportsFacility obj) throws RepositoryConverterException {
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
            throw new RepositoryConverterException("SportFacility is neither BasketballFacility nor FootballFacility");
        }
    }
}
