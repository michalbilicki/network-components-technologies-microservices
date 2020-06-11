package Repository.filler;

import DataModel.ClientEnt;
import com.github.javafaker.Faker;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@ApplicationScoped
public class ClientFiller implements Filler<ClientEnt>, Serializable {

    @Override
    public void autoFill(List<ClientEnt> destination) {
        Faker faker = new Faker(Locale.ENGLISH);
        Random random = new Random();
        String login;
        String fullName;

        for (int i = 0; i < 20; i++) {
            login = faker.witcher().character();
            fullName = faker.name().name() + " " + faker.name().lastName();
            destination.add(new ClientEnt(UUID.randomUUID().toString(), login, fullName, true));
        }

        destination.add(new ClientEnt(
                UUID.randomUUID().toString(),
                "resource-admin",
                "resource admin",
                true
        ));

        destination.add(new ClientEnt(
                UUID.randomUUID().toString(),
                "user-admin",
                "user admin",
                true
        ));

        destination.add(new ClientEnt(
                UUID.randomUUID().toString(),
                "client",
                "user client",
                true
        ));

        destination.add(new ClientEnt(
                UUID.randomUUID().toString(),
                "client2",
                "user client2",
                true
        ));

    }
}
