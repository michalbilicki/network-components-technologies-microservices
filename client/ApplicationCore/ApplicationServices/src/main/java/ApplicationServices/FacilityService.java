package ApplicationServices;

import ApplicationPorts.Infrastructure.FacilityServicePort;
import ApplicationPorts.User.SportFacilityUseCase;
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
public class FacilityService implements SportFacilityUseCase {

    @Inject
    private ReservationService reservationService;

    @Inject
    private FacilityServicePort facilityServicePort;

    public FacilityService() {

    }

    @Override
    public void addSportsFacility(SportsFacility sportsFacility) throws RepositoryConverterException, RepositoryException {
        facilityServicePort.add(sportsFacility);
    }

    @Override
    public SportsFacility getSportsFacility(UUID id) throws RepositoryConverterException, RepositoryException {
        return facilityServicePort.get(id);
    }

    @Override
    public List<SportsFacility> getAllSportsFacilities() throws RepositoryConverterException {
        return facilityServicePort.getAll();
    }


    @Override
    public void removeSportsFacility(UUID id) throws RepositoryException {
        facilityServicePort.remove(id);
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
        facilityServicePort.update(sportsFacility);
    }
}
