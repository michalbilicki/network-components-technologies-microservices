package pl.lapciakbilicki.RepositoryAdapter.conventer;

import pl.lapciakbilicki.DataModel.Entity;
import pl.lapciakbilicki.RepositoryAdapter.conventer.exception.RepositoryConverterException;

public interface RepositoryReconverter<T1, T2 extends Entity> {
        T2 convertFrom(T1 obj) throws RepositoryConverterException;
}
