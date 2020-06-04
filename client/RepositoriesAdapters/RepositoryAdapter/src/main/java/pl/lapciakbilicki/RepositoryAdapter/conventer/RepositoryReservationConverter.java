package pl.lapciakbilicki.RepositoryAdapter.conventer;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;
import pl.lapciakbilicki.DataModel.client.ClientEnt;
import pl.lapciakbilicki.DataModel.reservation.ReservationEnt;
import pl.lapciakbilicki.DataModel.sportsfacility.SportsFacilityEnt;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.ClientRepository;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.SportsFacilityRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@RequestScoped
@Named
public class RepositoryReservationConverter implements RepositoryTwoWayConverter<Reservation, ReservationEnt> {

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private SportsFacilityRepository sportsFacilityRepository;

    @Override
    public Reservation convertTo(ReservationEnt obj) {
        if(obj == null)
            return null;
        LocalDateTime start = convertDateToLocalDateTime(obj.getStartDate());
        LocalDateTime end = convertDateToLocalDateTime(obj.getEndDate());
        return new Reservation(obj.getId(), obj.getClient().getId(), start, end, obj.getFacility().getId(), obj.isActive());
    }

    @Override
    public ReservationEnt convertFrom(Reservation obj) {
        if(obj == null)
            return null;
        ClientEnt clientEnt = clientRepository.get(obj.getClientId());
        SportsFacilityEnt sportsFacilityEnt = sportsFacilityRepository.get(obj.getSportsFacilityId());
        return new ReservationEnt(
                obj.getId(),
                clientEnt,
                sportsFacilityEnt,
                convertLocalDateTimeToDate(obj.getStart()),
                convertLocalDateTimeToDate(obj.getEnd()),
                obj.isActive()
        );
    }

    private LocalDateTime convertDateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
    }

    private Date convertLocalDateTimeToDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

}
