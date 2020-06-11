package pl.lapciakbilicki.RepositoryAdapter.adapter;

import pl.lapciakbilicki.ApplicationCore.DomainModel.SportsFacility;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure.*;
import pl.lapciakbilicki.DataModel.sportsfacility.SportsFacilityEnt;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.SportsFacilityRepository;
import pl.lapciakbilicki.RepositoryAdapter.conventer.RepositoryFacilityConverter;
import pl.lapciakbilicki.RepositoryAdapter.conventer.exception.RepositoryConverterException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named("RepositoryFacilityAdapter")
@RequestScoped
public class RepositoryFacilityAdapter
        implements AddPort<SportsFacility>,
        GetPort<SportsFacility>,
        RemovePort<SportsFacility>,
        FilterPort<SportsFacility>,
        UpdatePort<SportsFacility>{

    @Inject
    private SportsFacilityRepository sportsFacilityRepository;

    @Inject
    private RepositoryFacilityConverter converter;

    @Override
    public SportsFacility get(UUID id) {
        try {
            return converter.convertTo(sportsFacilityRepository.get(id.toString()));
        } catch (RepositoryConverterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SportsFacility> getAll() {
        List<SportsFacilityEnt> toConvert = sportsFacilityRepository.getAll();
        List<SportsFacility> result = new ArrayList<SportsFacility>();

        toConvert.forEach(
                item -> {
                    try {
                        result.add(converter.convertTo(item));
                    } catch (RepositoryConverterException e) {
                        e.printStackTrace();
                    }
                }
        );
        return result;
    }

    @Override
    public void add(SportsFacility arg) throws RepositoryException {
        try {
            sportsFacilityRepository.add(converter.convertFrom(arg));
        } catch (RepositoryConverterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SportsFacility> getFiltered(Predicate<SportsFacility> predicate) {
        return this.getAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public void remove(SportsFacility arg) throws RepositoryException {
        sportsFacilityRepository.remove(arg.getId().toString());
    }

    @Override
    public void remove(UUID id) throws RepositoryException {
        sportsFacilityRepository.remove(id.toString());
    }

    @Override
    public void update(SportsFacility arg) throws RepositoryException {
        try {
            sportsFacilityRepository.update(converter.convertFrom(arg));
        } catch (RepositoryConverterException e) {
            throw new RepositoryException("Wrong data");
        }
    }
}
