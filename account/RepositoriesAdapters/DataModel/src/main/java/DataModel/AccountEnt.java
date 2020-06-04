package DataModel;

import java.util.List;
import java.util.Objects;

public class AccountEnt implements IsIdentified {

    private String id;
    private String login;
    private String password;
    private String fullName;
    private boolean active;
    private List<RoleEnt> roles;

    public AccountEnt(String id, String login, String password, String fullName, boolean active, List<RoleEnt> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.active = active;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<RoleEnt> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEnt> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        AccountEnt account = (AccountEnt) o;
        return this.id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
