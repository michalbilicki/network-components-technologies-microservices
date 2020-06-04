package pl.lapciakbilicki.DataModel.client;

import pl.lapciakbilicki.DataModel.Entity;
import pl.lapciakbilicki.DataModel.IsIdentified;

import java.util.Objects;
import java.util.UUID;

public class ClientEnt implements IsIdentified, Entity {

    private String id;
    private String login;
    private String fullName;
    private boolean active;

    public ClientEnt(String login, String fullName, boolean active) {
        this.id = UUID.randomUUID().toString();
        this.login = login;
        this.fullName = fullName;
        this.active = active;
    }

    public ClientEnt(String id, String login, String fullName, boolean active) {
        this.id = id;
        this.login = login;
        this.fullName = fullName;
        this.active = active;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        ClientEnt account = (ClientEnt) o;
        return this.id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
