package pl.lapciakbilicki.RepositoryAdapter.conventer;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;
import pl.lapciakbilicki.DataModel.client.ClientEnt;
import pl.lapciakbilicki.RepositoryAdapter.conventer.exception.RepositoryConverterException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class RepositoryClientConverter implements RepositoryTwoWayConverter<Client, ClientEnt> {

    @Override
    public Client convertTo(ClientEnt obj) throws RepositoryConverterException {
       if(obj == null)
           return null;

        return new Client(
                obj.getId(),
                obj.getLogin(),
                obj.getFullName().split(" ")[0],
                obj.getFullName().split(" ")[1],
                obj.isActive()
        );
    }

    @Override
    public ClientEnt convertFrom(Client obj) throws RepositoryConverterException {
        if(obj == null)
            return null;

        return new ClientEnt(
                obj.getId(),
                obj.getLogin(),
                obj.getName() + " " + obj.getSurname(),
                obj.isActive()
        );
    }
}
