package Repository;

import com.github.javafaker.Faker;

import DataModel.RoleEnt;
import DataModel.AccountEnt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class AccountFiller implements Filler<AccountEnt>, Serializable {

    @Inject
    RoleRepository roleRepository;

    @Override
    public void autoFill(List<AccountEnt> destination) {
        Faker faker = new Faker(Locale.ENGLISH);
        Random random = new Random();
        String login;
        String fullName;

        for (int i = 0; i < 20; i++) {
            login = faker.witcher().character();
            fullName = faker.name().name() + " " + faker.name().lastName();
            int roleType = random.nextInt(3);
            List<RoleEnt> roles = new ArrayList<>();
            roles.add(roleRepository.getAll().get(roleType));

            //String id, String login, String password, String fullName, boolean active, List<RoleEnt> roles
            destination.add(new AccountEnt(UUID.randomUUID().toString(), login, "fill", fullName, true, roles));
        }

        destination.add(new AccountEnt(
                UUID.randomUUID().toString(),
                "resource-admin",
                "fill",
                "resource admin",
                true,
                roleRepository.getAll().stream()
                        .filter(role -> role.getName().equals("Resources_Admin"))
                        .collect(Collectors.toList())
        ));

        destination.add(new AccountEnt(
                UUID.randomUUID().toString(),
                "user-admin",
                "fill",
                "user admin",
                true,
                roleRepository.getAll().stream()
                        .filter(role -> role.getName().equals("User_Admin"))
                        .collect(Collectors.toList())
        ));

        destination.add(new AccountEnt(
                UUID.randomUUID().toString(),
                "client",
                "fill",
                "user client",
                true,
                roleRepository.getAll().stream()
                        .filter(role -> role.getName().equals("Client"))
                        .collect(Collectors.toList())
        ));
    }
}
