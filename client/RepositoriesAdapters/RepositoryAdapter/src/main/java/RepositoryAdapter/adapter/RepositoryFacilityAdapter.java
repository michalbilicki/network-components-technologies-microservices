package RepositoryAdapter.adapter;

import ApplicationPorts.Infrastructure.*;
import DomainModel.SportsFacility;
import DataModel.SportsFacilityEnt;
import Repository.SportsFacilityRepository;
import RepositoryAdapter.converter.RepositoryFacilityConverter;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class RepositoryFacilityAdapter implements FacilityServicePort {

    @Inject
    private SportsFacilityRepository sportsFacilityRepository;

    @Override
    public SportsFacility get(UUID id) throws RepositoryConverterException, RepositoryException {
        return RepositoryFacilityConverter.convertTo(sportsFacilityRepository.get(id.toString()));
    }

    @Override
    public List<SportsFacility> getAll() throws RepositoryConverterException {
        List<SportsFacilityEnt> toConvert = sportsFacilityRepository.getAll();
        List<SportsFacility> result = new ArrayList<SportsFacility>();
        for(SportsFacilityEnt item : toConvert){
            result.add(RepositoryFacilityConverter.convertTo(item));
        }
        return result;
    }

    @Override
    public void add(SportsFacility arg) throws RepositoryConverterException, RepositoryException {
        sportsFacilityRepository.add(RepositoryFacilityConverter.convertFrom(arg));
    }

    @Override
    public List<SportsFacility> getFiltered(Predicate<SportsFacility> predicate) throws RepositoryConverterException {
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
    public void update(SportsFacility arg) throws RepositoryConverterException, RepositoryException {
        sportsFacilityRepository.update(RepositoryFacilityConverter.convertFrom(arg));
    }
}
