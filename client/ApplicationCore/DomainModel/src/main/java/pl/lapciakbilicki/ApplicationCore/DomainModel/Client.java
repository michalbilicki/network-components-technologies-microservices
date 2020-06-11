package pl.lapciakbilicki.ApplicationCore.DomainModel;

import java.util.Objects;

public class Client {

    public static final int MAX_RESERVATIONS = 5;

    private String id;
    private String login;
    private String name;
    private String surname;
    private boolean active;

    public Client(String id, String login, String name, String surname, boolean active) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return login.equals(client.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
