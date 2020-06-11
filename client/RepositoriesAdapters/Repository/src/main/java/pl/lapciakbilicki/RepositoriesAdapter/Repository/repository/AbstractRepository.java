package pl.lapciakbilicki.RepositoriesAdapter.Repository.repository;

import pl.lapciakbilicki.DataModel.IsIdentified;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.filler.Filler;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractRepository<T extends IsIdentified> implements Repository<T> {

    private List<T> listOfItems;
    private Filler<T> filler;

    @Override
    public void add(T item) throws RepositoryException {
        boolean exceptionFlag = true;
        if (item.getId() != null) {
            if (this.get(item.getId()) == null) {
                this.listOfItems.add(item);
                exceptionFlag = false;
            }
        }
        if(exceptionFlag)
            throw new RepositoryException("Operation 'add(item)' in " + this.getClass().getSimpleName() + " failed!");
    }

    @Override
    public T get(String id) {
        return this.listOfItems.stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<T> getAll() {
        return listOfItems;
    }

    @Override
    public abstract void update(T item) throws RepositoryException;

    @Override
    public void remove(T item) throws RepositoryException {
        if (!this.listOfItems.remove(item)) {
            throw new RepositoryException("Operation 'remove(T)' in " + this.getClass().getSimpleName() + " failed!");
        }
    }

    @Override
    public void remove(String id) throws RepositoryException {
        if (!listOfItems.remove(listOfItems.stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElse(null))) {
            throw new RepositoryException("Operation 'remove(id)' in " + this.getClass().getSimpleName() + " failed!");
        }
    }

    @Override
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
