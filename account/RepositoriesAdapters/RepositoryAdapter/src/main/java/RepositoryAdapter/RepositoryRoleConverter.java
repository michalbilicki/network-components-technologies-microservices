package RepositoryAdapter;

import DomainModel.Role;
import DataModel.RoleEnt;
import exceptions.RepositoryConverterException;

public class RepositoryRoleConverter {

    public static Role convertTo(RoleEnt obj) throws RepositoryConverterException {
        if(obj == null)
            return null;
        return new Role(obj.getName());
    }
}
