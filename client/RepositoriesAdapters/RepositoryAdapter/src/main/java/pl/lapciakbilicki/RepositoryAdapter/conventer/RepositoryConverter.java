package pl.lapciakbilicki.RepositoryAdapter.conventer;

import pl.lapciakbilicki.DataModel.Entity;
import pl.lapciakbilicki.RepositoryAdapter.conventer.exception.RepositoryConverterException;

public interface RepositoryConverter<T1, T2 extends Entity> {

    T1 convertTo(T2 obj) throws RepositoryConverterException;

}
