package ApplicationPorts;

import DomainModel.Account;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface AccountServicePort {
    void addAccount(Account arg) throws RepositoryException, RepositoryException, RepositoryConverterException, RepositoryConverterException;

    List<Account> getFilteredAccount(Predicate<Account> predicate) throws RepositoryConverterException;

    Account getAccount(UUID id) throws RepositoryConverterException;

    List<Account> getAllAccounts() throws RepositoryConverterException;

    void removeAccount(Account arg) throws RepositoryException, RepositoryException, RepositoryException;

    void removeAccount(UUID id) throws RepositoryException;

    void updateAccount(Account arg) throws RepositoryException, RepositoryException;
}
