package DataModel;

import java.util.Date;
import java.util.Objects;

public class ReservationEnt implements IsIdentified {

    private String id;
    private ClientEnt client;
    private SportsFacilityEnt facility;
    private Date startDate;
    private Date endDate;
    private boolean active;

    public ReservationEnt() {

    }

    public ReservationEnt(String id, ClientEnt client, SportsFacilityEnt facility, Date startDate, Date endDate, boolean active) {
        this.id = id;
        this.client = client;
        this.facility = facility;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationEnt that = (ReservationEnt) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //<editor-fold desc="getters and setters">
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ClientEnt getClient() {
        return client;
    }

    public SportsFacilityEnt getFacility() {
        return facility;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setClient(ClientEnt client) {
        this.client = client;
    }

    public void setFacility(SportsFacilityEnt facility) {
        this.facility = facility;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    //</editor-fold>
}
