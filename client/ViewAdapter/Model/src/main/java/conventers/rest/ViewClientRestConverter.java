package conventers.rest;

import conventers.ViewTwoWayConverter;
import pl.lapciakbilicki.ApplicationCore.DomainModel.Client;
import rest.ClientRestDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
@Named
public class ViewClientRestConverter implements ViewTwoWayConverter<ClientRestDTO, Client> {

    @Override
    public ClientRestDTO convertTo(Client arg) {
        return new ClientRestDTO(
                arg.getId(),
                arg.getLogin(),
                arg.getName() + " " + arg.getSurname(),
                arg.isActive()
        );
    }

    @Override
    public Client convertFrom(ClientRestDTO arg) {
        return new Client(
                arg.getId(),
                arg.getLogin(),
                arg.getFullName().split(" ")[0],
                arg.getFullName().split(" ")[1],
                arg.isActive()
        );
    }
}
