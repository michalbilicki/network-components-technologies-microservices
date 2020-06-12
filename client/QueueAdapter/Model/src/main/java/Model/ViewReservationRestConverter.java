package Model;

import DomainModel.Reservation;
import Model.dto.ReservationDto;

public class ViewReservationRestConverter {

    public static ReservationDto convertTo(Reservation arg) {
        if (arg == null) {
            return null;
        }

        return new ReservationDto(
                arg.getId(),
                arg.getClientId(),
                arg.getSportsFacilityId(),
                arg.getStart(),
                arg.getEnd(),
                arg.isActive()
        );
    }

    public static Reservation convertFrom(ReservationDto arg) {
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
