package Repository;

import DataModel.SportsFacilityEnt;
import Repository.filler.SportsFacilityFiller;
import exceptions.RepositoryException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

@ApplicationScoped
@Named
public class SportsFacilityRepository extends AbstractRepository<SportsFacilityEnt> implements Serializable {

    @Inject
    private SportsFacilityFiller filler;

    public SportsFacilityRepository() {
        this.setListOfItems(Collections.synchronizedList(new ArrayList<>()));
    }

    @PostConstruct
    public void init() {
        this.setFiller(filler);
        this.getFiller().autoFill(this.getListOfItems());
    }

    @Override
    public void update(SportsFacilityEnt item) throws RepositoryException {
        SportsFacilityEnt sportsFacility = this.get(item.getId());
        if (sportsFacility != null) {
            sportsFacility.copyAttributionsWithoutId(item);
        } else {
            throw new RepositoryException();
        }
    }

    @Override
    public SportsFacilityEnt getByBusinessId(String businessId) throws RepositoryException {
        SportsFacilityEnt result = this.listOfItems.stream()
                .filter(item -> item.getName().equals(businessId))
                .findAny()
                .orElse(null);

        if (result != null) {
            return result;
        } else {
            throw new RepositoryException();
        }
    }
}
