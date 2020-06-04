package pl.lapciakbilicki.RepositoriesAdapter.Repository.repository;


import pl.lapciakbilicki.DataModel.reservation.ReservationEnt;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

@ApplicationScoped
public class ReservationRepository extends AbstractRepository<ReservationEnt> implements Serializable {

    public ReservationRepository() {
        this.setListOfItems(Collections.synchronizedList(new ArrayList<>()));
    }

    @Override
    public void update(ReservationEnt item) throws RepositoryException {
        ReservationEnt reservation = this.get(item.getId());
        if (reservation != null) {
            reservation.setClient(item.getClient());
            reservation.setFacility(item.getFacility());
            reservation.setStartDate(item.getStartDate());
            reservation.setEndDate(item.getEndDate());
        } else {
            throw new RepositoryException("Operation 'update(item)' in " + this.getClass().getSimpleName() + " failed!");
        }
    }
}
