package pl.lapciakbilicki.RepositoryAdapter.adapter;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure.*;
import pl.lapciakbilicki.DataModel.client.ClientEnt;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.ClientRepository;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;
import pl.lapciakbilicki.RepositoryAdapter.conventer.RepositoryClientConverter;
import pl.lapciakbilicki.RepositoryAdapter.conventer.exception.RepositoryConverterException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named("RepositoryClientAdapter")
@RequestScoped
public class RepositoryClientAdapter
        implements GetPort<Client>,
        UpdatePort<Client>,
        FilterPort<Client>,
        AddPort<Client> {

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private RepositoryClientConverter repositoryClientConverter;

    @Override
    public Client get(UUID id) {
        try {
            return repositoryClientConverter.convertTo(clientRepository.get(id.toString()));
        } catch (RepositoryConverterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<ClientEnt> toConvert = clientRepository.getAll();
        List<Client> result = new ArrayList<>();

        for (ClientEnt clientEnt : toConvert) {
            try {
                result.add(repositoryClientConverter.convertTo(clientEnt));
            } catch (RepositoryConverterException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void update(Client arg) throws RepositoryException {
        try {
            clientRepository.update(repositoryClientConverter.convertFrom(arg));
        } catch (RepositoryConverterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getFiltered(Predicate<Client> predicate) {
        return this.getAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public void add(Client arg) throws RepositoryException {
        this.add(arg);
    }
}
