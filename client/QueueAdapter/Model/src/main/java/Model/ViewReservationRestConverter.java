package Model;

import DomainModel.Reservation;
import Model.dto.ReservationRestDTO;

public class ViewReservationRestConverter {

    public static ReservationRestDTO convertTo(Reservation arg) {
        return new ReservationRestDTO(
                arg.getId(),
                arg.getClientId(),
                arg.getSportsFacilityId(),
                arg.getStart(),
                arg.getEnd(),
                arg.isActive()
        );
    }

    public static Reservation convertFrom(ReservationRestDTO arg) {
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
