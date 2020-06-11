package pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure;

import java.util.List;
import java.util.UUID;

public interface GetPort<T> {
    public T get(UUID id);

    public List<T> getAll();
}
