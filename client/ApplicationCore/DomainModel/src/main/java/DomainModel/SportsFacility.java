package DomainModel;

import java.util.Objects;
import java.util.UUID;

public class SportsFacility {

    private UUID id;
    private SportsFacilityType sportsFacilityType;
    private boolean access;
    private Field field;
    private String name;
    private double price;

    public SportsFacility(UUID id, SportsFacilityType sportsFacilityType, boolean access, Field field, String name, double price) {
        this.id = id;
        this.sportsFacilityType = sportsFacilityType;
        this.access = access;
        this.field = field;
        this.name = name;
        this.price = price;
    }

    public SportsFacility(SportsFacilityType sportsFacilityType, boolean access, Field field, String name, double price) {
        this.id = UUID.randomUUID();
        this.sportsFacilityType = sportsFacilityType;
        this.access = access;
        this.field = field;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportsFacility that = (SportsFacility) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isAccess() {
        return access;
    }

    public UUID getId() {
        return id;
    }

    public SportsFacilityType getSportsFacilityType() {
        return sportsFacilityType;
    }

    public Field getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
