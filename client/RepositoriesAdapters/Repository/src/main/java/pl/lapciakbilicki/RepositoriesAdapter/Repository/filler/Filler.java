package pl.lapciakbilicki.RepositoriesAdapter.Repository.filler;

import java.util.List;

public interface Filler<T> {
    void autoFill(List<T> destination);
}
