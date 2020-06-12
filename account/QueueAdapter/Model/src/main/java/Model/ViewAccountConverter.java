package Model;

import DomainModel.Account;
import DomainModel.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ViewAccountConverter implements ViewTwoWayConverter<AccountDto, Account> {

    @Override
    public AccountDto convertTo(Account arg) {
        if (arg == null) {
            return null;
        }

        //String id, String login, String password, String fullName, boolean active, List<String> roles
        return new AccountDto(
                arg.getAccountId().toString(),
                arg.getLogin(),
                arg.getPassword(),
                arg.getName() + " " + arg.getSurname(),
                arg.isActive(),
                Collections.singletonList(
                        arg.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.joining()))
        );
    }

    @Override
    public Account convertFrom(AccountDto arg) {
        List<Role> roles = new ArrayList<Role>();

        for (String item : arg.getRoles()) {
            roles.add(new Role(item));
        }

        //UUID accountId, String login, String password, String name, String surname, boolean active, List<DomainModel.Role> roles
        return new Account(
                UUID.fromString(arg.getId()),
                arg.getLogin(),
                arg.getPassword(),
                arg.getFullName().split(" ")[0],
                arg.getFullName().split(" ")[1],
                arg.isActive(),
                roles);
    }
}
