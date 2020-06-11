package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.sportsfacility;

import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

import java.util.UUID;

public interface RemoveSportsFacilityUseCase {
    boolean removeSportsFacility(UUID sportsFacilityId);
}
