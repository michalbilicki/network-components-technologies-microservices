package DomainModel;

import java.util.List;
import java.util.UUID;

public class Account {

    private UUID accountId;
    private String login;
    private String password;
    private String name;
    private String surname;
    private boolean active;
    private List<Role> roles;

    public Account() {

    }

    public Account(String login, String password, String name, String surname, boolean active, List<Role> roles) {
        this.accountId = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.active = active;
        this.roles = roles;
    }

    public Account(UUID accountId, String login, String password, String name, String surname, boolean active, List<Role> roles) {
        this.accountId = accountId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.active = active;
        this.roles = roles;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isActive() {
        return active;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void block() {
        if (active)
            active = false;
    }

    public void unblock() {
        if (!active) {
            active = true;
        }
    }

    @Override
    public String toString() {
        return "DomainModel.Account{" +
                "accountId=" + accountId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
