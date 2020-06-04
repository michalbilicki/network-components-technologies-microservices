package pl.lapciakbilicki.ApplicationPorts.Interfaces.User.sportsfacility;

import pl.lapciakbilicki.ApplicationCore.DomainModel.SportsFacility;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;

public interface AddSportsFacilityUseCase {

    boolean addSportsFacility(SportsFacility sportsFacility);
}
