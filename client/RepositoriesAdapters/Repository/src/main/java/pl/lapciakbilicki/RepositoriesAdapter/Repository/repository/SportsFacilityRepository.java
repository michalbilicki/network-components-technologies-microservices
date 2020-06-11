package pl.lapciakbilicki.RepositoriesAdapter.Repository.repository;


import pl.lapciakbilicki.DataModel.sportsfacility.SportsFacilityEnt;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.filler.SportsFacilityFiller;

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
            throw new RepositoryException("Operation 'update(item)' in " + this.getClass().getSimpleName() + " failed!");
        }
    }
}
