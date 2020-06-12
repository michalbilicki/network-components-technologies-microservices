package Model;

import DomainModel.Client;
import Model.dto.ClientDto;

public class ViewClientConverter {

    public static ClientDto convertTo(Client arg) {
        if (arg == null) {
            return null;
        }

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
