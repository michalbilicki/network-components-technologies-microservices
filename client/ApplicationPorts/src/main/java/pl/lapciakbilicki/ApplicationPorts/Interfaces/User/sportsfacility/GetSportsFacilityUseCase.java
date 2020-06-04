package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.sportsfacility;

import pl.lapciakbilicki.ApplicationCore.DomainModel.SportsFacility;

import java.util.List;
import java.util.UUID;

public interface GetSportsFacilityUseCase {
    SportsFacility getSportsFacility(UUID id);
    List<SportsFacility> getAllSportsFacilities();
}
