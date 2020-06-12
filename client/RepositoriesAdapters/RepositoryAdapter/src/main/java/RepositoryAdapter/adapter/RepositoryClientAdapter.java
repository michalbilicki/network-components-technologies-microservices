package RepositoryAdapter.adapter;

import ApplicationPorts.Infrastructure.ClientPort;
import DataModel.ClientEnt;
import DomainModel.Client;
import Repository.ClientRepository;
import RepositoryAdapter.converter.RepositoryClientConverter;
import exceptions.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class RepositoryClientAdapter implements ClientPort {

    @Inject
    private ClientRepository clientRepository;

    @Override
    public Client get(UUID id) throws RepositoryException {
        return RepositoryClientConverter.convertTo(clientRepository.get(id.toString()));
    }

    @Override
    public void remove(UUID id) throws RepositoryException {
        clientRepository.remove(id.toString());
    }

    @Override
    public List<Client> getAll() {
        List<ClientEnt> toConvert = clientRepository.getAll();
        List<Client> result = new ArrayList<>();

        for (ClientEnt clientEnt : toConvert) {
            result.add(RepositoryClientConverter.convertTo(clientEnt));
        }
        return result;
    }

    @Override
    public void update(Client arg) throws RepositoryException {
        clientRepository.update(RepositoryClientConverter.convertFrom(arg));
    }

    @Override
    public List<Client> getFiltered(Predicate<Client> predicate) {
        return this.getAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public void add(Client arg) throws RepositoryException {
        clientRepository.add(RepositoryClientConverter.convertFrom(arg));
    }
}
