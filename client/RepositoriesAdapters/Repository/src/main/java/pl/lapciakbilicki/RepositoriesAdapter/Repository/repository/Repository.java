package pl.lapciakbilicki.RepositoriesAdapter.Repository.repository;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {

    void add(T item) throws RepositoryException;

    T get(String id);

    List<T> getAll();

    void update(T item) throws RepositoryException;

    void remove(T item) throws RepositoryException;

    void remove(String id) throws RepositoryException;

    List<T> getByCondition(Predicate<T> predicate);
}
