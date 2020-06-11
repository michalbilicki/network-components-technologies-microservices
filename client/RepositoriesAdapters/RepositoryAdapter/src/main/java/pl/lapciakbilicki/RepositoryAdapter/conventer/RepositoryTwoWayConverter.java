package pl.lapciakbilicki.RepositoryAdapter.conventer;

import pl.lapciakbilicki.DataModel.Entity;

public interface RepositoryTwoWayConverter<T1, T2 extends Entity> extends RepositoryConverter<T1, T2>, RepositoryReconverter<T1, T2> {
}
