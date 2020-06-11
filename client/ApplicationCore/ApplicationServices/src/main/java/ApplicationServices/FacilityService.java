package ApplicationServices;

import ApplicationPorts.Infrastructure.FacilityPort;
import ApplicationPorts.User.SportFacilityServiceUseCase;
import DomainModel.Reservation;
import DomainModel.SportsFacility;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FacilityService implements SportFacilityServiceUseCase {

    @Inject
    private ReservationService reservationService;

    @Inject
    private FacilityPort facilityPort;

    public FacilityService() {

    }

    @Override
    public void addSportsFacility(SportsFacility sportsFacility) throws RepositoryConverterException, RepositoryException {
        facilityPort.add(sportsFacility);
    }

    @Override
    public SportsFacility getSportsFacility(UUID id) throws RepositoryConverterException, RepositoryException {
        return facilityPort.get(id);
    }

    @Override
    public List<SportsFacility> getAllSportsFacilities() throws RepositoryConverterException {
        return facilityPort.getAll();
    }


    @Override
    public void removeSportsFacility(UUID id) throws RepositoryException {
        facilityPort.remove(id);
    }

    @Override
    public boolean canReserve(LocalDateTime start, LocalDateTime end, SportsFacility sportsFacility) {
        for (Reservation reservation : reservationService.getSportsFacilityReservations(sportsFacility)) {
            LocalDateTime reservationStartDate = reservation.getStart();
            LocalDateTime reservationEndDate = reservation.getEnd();
            if (reservationStartDate.compareTo(start) >= 0 && reservationEndDate.compareTo(start) < 0) {
                return false;
            }
            if (reservationStartDate.compareTo(end) > 0 && reservationEndDate.compareTo(end) <= 0) {
                return false;
            }
            if (start.compareTo(reservationStartDate) <= 0 && end.compareTo(reservationEndDate) >= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateSportsFacility(SportsFacility sportsFacility) throws RepositoryConverterException, RepositoryException {
        facilityPort.update(sportsFacility);
    }
}
