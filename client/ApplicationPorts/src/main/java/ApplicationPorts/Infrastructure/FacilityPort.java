package ApplicationPorts.Infrastructure;

import DomainModel.SportsFacility;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface FacilityPort {

    public void add(SportsFacility arg) throws RepositoryConverterException, RepositoryException;

    public SportsFacility get(UUID id) throws RepositoryConverterException, RepositoryException;

    public List<SportsFacility> getAll() throws RepositoryConverterException;

    public void remove(SportsFacility arg) throws RepositoryException;

    public void remove(UUID id) throws RepositoryException;

    public void update(SportsFacility arg) throws RepositoryConverterException, RepositoryException;

    List<SportsFacility> getFiltered(Predicate<SportsFacility> predicate) throws RepositoryConverterException;


}
