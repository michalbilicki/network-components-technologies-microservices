package pl.lapciakbilicki.ApplicationCore.ApplicationServices.services;

import pl.lapciakbilicki.ApplicationCore.DomainModel.*;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure.*;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.User.sportsfacility.*;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Named("FacilityService")
public class FacilityService implements AddSportsFacilityUseCase, RemoveSportsFacilityUseCase, GetSportsFacilityUseCase, UpdateSportsFacilityUseCase {

    @Inject
    private ReservationService reservationService;

    @Inject
    private @Named("RepositoryFacilityAdapter")
    AddPort<SportsFacility> sportsFacilityAddPort;

    @Inject
    private @Named("RepositoryFacilityAdapter")
    RemovePort<SportsFacility> sportsFacilityRemovePort;

    @Inject
    private @Named("RepositoryFacilityAdapter")
    GetPort<SportsFacility> sportsFacilityGetPort;

    @Inject
    private @Named("RepositoryFacilityAdapter")
    FilterPort<SportsFacility> sportsFacilityFilterPort;

    @Inject
    private @Named("RepositoryFacilityAdapter")
    UpdatePort<SportsFacility> sportsFacilityUpdatePort;

    public FacilityService() {

    }

    @Override
    public boolean addSportsFacility(SportsFacility sportsFacility) {
        try {
            sportsFacilityAddPort.add(sportsFacility);
            return true;
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SportsFacility getSportsFacility(UUID id) {
        return sportsFacilityGetPort.get(id);
    }

    @Override
    public List<SportsFacility> getAllSportsFacilities() {
        return sportsFacilityGetPort.getAll();
    }


    @Override
    public boolean removeSportsFacility(UUID id) {
        try {
            sportsFacilityRemovePort.remove(id);
            return true;
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean canReserve(LocalDateTime start, LocalDateTime end, SportsFacility sportsFacility){
        for(Reservation reservation : reservationService.getSportsFacilityReservations(sportsFacility)){
            LocalDateTime  reservationStartDate = reservation.getStart();
            LocalDateTime reservationEndDate = reservation.getEnd();
            if (reservationStartDate.compareTo(start) >= 0 && reservationEndDate.compareTo(start) < 0) {
                return false;
            }
            if(reservationStartDate.compareTo(end) > 0 && reservationEndDate.compareTo(end) <= 0){
                return false;
            }
            if(start.compareTo(reservationStartDate) <= 0 && end.compareTo(reservationEndDate) >= 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateSportsFacility(SportsFacility sportsFacility) {
        try {
            sportsFacilityUpdatePort.update(sportsFacility);
            return true;
        } catch (RepositoryException e) {
            e.printStackTrace();
            return false;
        }
    }
}
