package Repository;

import DataModel.IsIdentified;
import Repository.filler.Filler;
import exceptions.RepositoryException;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractRepository<T extends IsIdentified> {

    protected List<T> listOfItems;
    private Filler<T> filler;

    public abstract void update(T item) throws RepositoryException;

    public abstract T getByBusinessId(String businessId) throws RepositoryException;

    public void add(T item) throws RepositoryException {
        if (item.getId() != null) {
            if (this.get(item.getId()) == null) {
                this.listOfItems.add(item);
            } else {
                throw new RepositoryException();
            }
        } else {
            throw new RepositoryException();
        }
    }

    public T get(String id) throws RepositoryException {
        return this.listOfItems.stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public List<T> getAll() {
        return listOfItems;
    }

    public void remove(T item) throws RepositoryException {
        if (!this.listOfItems.remove(item)) {
            throw new RepositoryException();
        }
    }

    public void remove(String id) throws RepositoryException {
        if (!listOfItems.remove(listOfItems.stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElse(null))) {
            throw new RepositoryException();
        }
    }

    public List<T> getByCondition(Predicate<T> predicate) {
        return this.listOfItems.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public List<T> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<T> listOfItems) {
        this.listOfItems = listOfItems;
    }

    public Filler<T> getFiller() {
        return filler;
    }

    public void setFiller(Filler<T> filler) {
        this.filler = filler;
    }
}
