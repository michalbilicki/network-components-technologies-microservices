package RepositoryAdapter.converter;

import DomainModel.Client;
import DataModel.ClientEnt;

public class RepositoryClientConverter {

    public static Client convertTo(ClientEnt obj) {
        if (obj == null)
            return null;

        return new Client(
                obj.getId(),
                obj.getLogin(),
                obj.getFullName().split(" ")[0],
                obj.getFullName().split(" ")[1],
                obj.isActive()
        );
    }

    public static ClientEnt convertFrom(Client obj) {
        if (obj == null)
            return null;

        return new ClientEnt(
                obj.getId(),
                obj.getLogin(),
                obj.getName() + " " + obj.getSurname(),
                obj.isActive()
        );
    }
}
