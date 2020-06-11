package conventers.rest;

import conventers.ViewTwoWayConverter;
import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;
import rest.ReservationRestDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class ViewReservationRestConverter implements ViewTwoWayConverter<ReservationRestDTO, Reservation> {

    @Override
    public ReservationRestDTO convertTo(Reservation arg) {
        return new ReservationRestDTO(
                arg.getId(),
                arg.getClientId(),
                arg.getSportsFacilityId(),
                arg.getStart(),
                arg.getEnd(),
                arg.isActive()
        );
    }

    @Override
    public Reservation convertFrom(ReservationRestDTO arg) {
        return new Reservation(
                arg.getId(),
                arg.getClientId(),
                arg.getStartDate(),
                arg.getEndDate(),
                arg.getSportsFacilityId(),
                arg.isActive()
        );
    }

}
