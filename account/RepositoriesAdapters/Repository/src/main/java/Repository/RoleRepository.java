package Repository;

import Repository.filler.RoleFiller;
import exceptions.RepositoryException;
import DataModel.RoleEnt;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

@ApplicationScoped
public class RoleRepository extends AbstractRepository<RoleEnt> implements Serializable {

    @Inject
    private RoleFiller filler;

    public RoleRepository() {
        this.setListOfItems(Collections.synchronizedList(new ArrayList<>()));
    }

    @PostConstruct
    public void init() {
        this.setFiller(filler);
        this.getFiller().autoFill(this.getListOfItems());
    }

    @Override
    public void update(RoleEnt item) throws RepositoryException {
        RoleEnt roleToUpdate = this.get(item.getId());
        if (roleToUpdate != null) {
            roleToUpdate.setName(item.getName());
        } else {
            throw new RepositoryException("Operation 'update(item)' in " + this.getClass().getSimpleName() + " failed!");
        }
    }

    @Override
    public RoleEnt getByBusinessId(String businessId) {
        return this.listOfItems.stream()
                .filter(item -> item.getName().equals(businessId))
                .findAny()
                .orElse(null);
    }
}
