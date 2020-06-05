package Repository;


import Repository.filler.AccountFiller;
import exceptions.RepositoryException;
import DataModel.AccountEnt;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

@ApplicationScoped
public class AccountRepository extends AbstractRepository<AccountEnt> implements Serializable {

    @Inject
    private AccountFiller accountFiller;

    public AccountRepository() {
        this.setListOfItems(Collections.synchronizedList(new ArrayList<>()));
    }

    @PostConstruct
    public void init() {
        this.setFiller(accountFiller);
        this.getFiller().autoFill(this.getAll());
    }

    @Override
    public void update(AccountEnt item) throws RepositoryException {
        AccountEnt accountToUpdate = this.get(item.getId());
        if (accountToUpdate != null) {
            accountToUpdate.setActive(item.isActive());
            accountToUpdate.setFullName(item.getFullName());
            accountToUpdate.setLogin(item.getLogin());
            accountToUpdate.setPassword(item.getPassword());
            accountToUpdate.setRoles(item.getRoles());
        } else {
            throw new RepositoryException("Operation 'update(item)' in " + this.getClass().getSimpleName() + " failed!");
        }
    }

    @Override
    public AccountEnt getByBusinessId(String businessId) {
        return this.listOfItems.stream()
                .filter(item -> item.getLogin().equals(businessId))
                .findAny()
                .orElse(null);
    }
}
