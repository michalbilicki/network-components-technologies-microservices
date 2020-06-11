package conventers.rest;

import conventers.ViewTwoWayConverter;
import pl.lapciakbilicki.ApplicationCore.DomainModel.Field;
import rest.FieldRestDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class ViewFieldRestConverter implements ViewTwoWayConverter<FieldRestDTO, Field> {

    @Override
    public FieldRestDTO convertTo(Field arg) {
        return new FieldRestDTO(
            arg.getSurfaceArea(),
            arg.getMaxAmountOfPeople(),
            arg.getTypeOfGround()
        );
    }

    @Override
    public Field convertFrom(FieldRestDTO arg) {
        return new Field(
                arg.getSurfaceArea(),
                arg.getMaxAmountOfPeople(),
                arg.getTypeOfGround()
        );
    }

}
