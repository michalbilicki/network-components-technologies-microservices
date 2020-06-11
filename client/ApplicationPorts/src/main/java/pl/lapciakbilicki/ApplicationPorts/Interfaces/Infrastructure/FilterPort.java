package pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure;

import java.util.List;
import java.util.function.Predicate;

public interface FilterPort<T> {

    List<T> getFiltered(Predicate<T> predicate);

}
