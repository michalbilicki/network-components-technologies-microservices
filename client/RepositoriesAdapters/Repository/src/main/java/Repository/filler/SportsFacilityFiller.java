package Repository.filler;

import com.github.javafaker.Faker;
import DataModel.BasketballFacilityEnt;
import DataModel.FieldEnt;
import DataModel.FootballFacilityEnt;
import DataModel.SportsFacilityEnt;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@ApplicationScoped
public class SportsFacilityFiller implements Filler<SportsFacilityEnt>, Serializable {

    @Override
    public void autoFill(List<SportsFacilityEnt> destination) {
        Faker faker = new Faker();
        Random random = new Random();
        FieldEnt field;
        for (int i = 0; i < 20; i++) {
            field = new FieldEnt(random.nextDouble() * 500 + 100, random.nextInt(30) + 10, faker.elderScrolls().city());
            if (random.nextBoolean()) {
                destination.add(new FootballFacilityEnt(UUID.randomUUID().toString(),
                        random.nextDouble() * 50 + 5,
                        true,
                        field,
                        faker.witcher().location(),
                        random.nextBoolean(),
                        random.nextDouble() * 5,
                        random.nextDouble() * 3
                ));
            } else {
                destination.add(new BasketballFacilityEnt(UUID.randomUUID().toString(),
                        random.nextDouble() * 50 + 5,
                        true,
                        field,
                        faker.witcher().location(),
                        random.nextInt(10) + 2,
                        1.5,
                        random.nextDouble() * 5
                ));
            }
        }
    }
}
