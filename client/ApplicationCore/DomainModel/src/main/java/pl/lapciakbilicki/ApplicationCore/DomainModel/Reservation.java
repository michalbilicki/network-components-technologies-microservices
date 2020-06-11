package pl.lapciakbilicki.ApplicationCore.DomainModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Reservation {

    //maximal time of reservation in hours
    public static final int MAX_DURATION = 5;

    private String id;
    private String clientId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String sportsFacilityId;
    private boolean active;

    public Reservation(String id, String clientId, LocalDateTime start, LocalDateTime end, String sportsFacilityId, boolean active) {
        this.id = id;
        this.clientId = clientId;
        this.start = start;
        this.end = end;
        this.sportsFacilityId = sportsFacilityId;
        this.active = active;
    }

    public Reservation(String clientId, LocalDateTime start, LocalDateTime end, String sportsFacilityId) {
        this.id = UUID.randomUUID().toString();
        this.clientId = clientId;
        this.start = start;
        this.end = end;
        this.sportsFacilityId = sportsFacilityId;
        this.active = true;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSportsFacilityId() {
        return sportsFacilityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
