package pl.lapciakbilicki.RepositoriesAdapter.Repository.repository;

import pl.lapciakbilicki.DataModel.client.ClientEnt;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.filler.ClientFiller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

@ApplicationScoped
public class ClientRepository extends AbstractRepository<ClientEnt> implements Serializable {

    @Inject
    private ClientFiller clientFiller;

    public ClientRepository() {
        this.setListOfItems(Collections.synchronizedList(new ArrayList<>()));
    }

    @PostConstruct
    public void init() {
        this.setFiller(clientFiller);
        this.getFiller().autoFill(this.getAll());
    }

    @Override
    public void update(ClientEnt item) throws RepositoryException {
        ClientEnt accountToUpdate = this.get(item.getId());
        if (accountToUpdate != null) {
            accountToUpdate.setActive(item.isActive());
            accountToUpdate.setFullName(item.getFullName());
            accountToUpdate.setLogin(item.getLogin());
        } else {
            throw new RepositoryException("Operation 'update(item)' in " + this.getClass().getSimpleName() + " failed!");
        }
    }
}
