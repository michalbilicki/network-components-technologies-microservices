package Model;

import exceptions.ViewConverterException;

public interface ViewTwoWayConverter<T1, T2> {

    public T1 convertTo(T2 arg) throws ViewConverterException;

    public T2 convertFrom(T1 arg) throws ViewConverterException;
}
