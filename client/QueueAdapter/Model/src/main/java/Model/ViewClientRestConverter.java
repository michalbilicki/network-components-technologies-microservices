package Model;

import DomainModel.Client;
import Model.dto.ClientDto;

public class ViewClientRestConverter {

    public static ClientDto convertTo(Client arg) {
        return new ClientDto(
                arg.getId(),
                arg.getLogin(),
                arg.getName() + " " + arg.getSurname(),
                arg.isActive()
        );
    }

    public static Client convertFrom(ClientDto arg) {
        return new Client(
                arg.getId(),
                arg.getLogin(),
                arg.getFullName().split(" ")[0],
                arg.getFullName().split(" ")[1],
                arg.isActive()
        );
    }
}
