package Model;

import DomainModel.Client;
import Model.dto.ClientRestDTO;

public class ViewClientRestConverter {

    public static ClientRestDTO convertTo(Client arg) {
        return new ClientRestDTO(
                arg.getId(),
                arg.getLogin(),
                arg.getName() + " " + arg.getSurname(),
                arg.isActive()
        );
    }

    public static Client convertFrom(ClientRestDTO arg) {
        return new Client(
                arg.getId(),
                arg.getLogin(),
                arg.getFullName().split(" ")[0],
                arg.getFullName().split(" ")[1],
                arg.isActive()
        );
    }
}
