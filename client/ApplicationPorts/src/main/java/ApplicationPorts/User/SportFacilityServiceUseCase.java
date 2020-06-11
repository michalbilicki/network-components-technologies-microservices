package ApplicationPorts.User;

import DomainModel.SportsFacility;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SportFacilityServiceUseCase {

    void addSportsFacility(SportsFacility sportsFacility) throws RepositoryConverterException, RepositoryException;

    SportsFacility getSportsFacility(UUID id) throws RepositoryConverterException, RepositoryException;

    List<SportsFacility> getAllSportsFacilities() throws RepositoryConverterException;

    void removeSportsFacility(UUID sportsFacilityId) throws RepositoryException;

    void updateSportsFacility(SportsFacility sportsFacility) throws RepositoryConverterException, RepositoryException;

    boolean canReserve(LocalDateTime start, LocalDateTime end, SportsFacility sportsFacility);
}
