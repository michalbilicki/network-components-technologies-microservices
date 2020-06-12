package ApplicationPorts;

import DomainModel.Account;
import exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface AccountPort {
    void addAccount(Account arg) throws RepositoryException;

    List<Account> getFilteredAccount(Predicate<Account> predicate);

    Account getAccount(UUID id);

    List<Account> getAllAccounts();

    void removeAccount(Account arg) throws RepositoryException;

    void removeAccount(UUID id) throws RepositoryException;

    void updateAccount(Account arg) throws RepositoryException;
}
