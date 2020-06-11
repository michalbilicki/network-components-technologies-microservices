package Repository;

import DataModel.ClientEnt;
import Repository.filler.ClientFiller;
import exceptions.RepositoryException;

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
            throw new RepositoryException();
        }
    }

    @Override
    public ClientEnt getByBusinessId(String businessId) throws RepositoryException {
        ClientEnt result = this.listOfItems.stream()
                .filter(item -> item.getLogin().equals(businessId))
                .findAny()
                .orElse(null);

        if (result != null) {
            return result;
        } else {
            throw new RepositoryException();
        }
    }
}
