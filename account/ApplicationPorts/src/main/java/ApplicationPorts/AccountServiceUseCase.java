package ApplicationPorts;

import DomainModel.Account;
import exceptions.RepositoryConverterException;
import exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface AccountServiceUseCase {

    void addAccount(Account account) throws RepositoryConverterException, RepositoryException;

    void blockAccount(UUID id) throws RepositoryException, RepositoryConverterException;

    void unblockAccount(UUID accountID) throws RepositoryException, RepositoryConverterException;

    void updateAccount(Account account) throws RepositoryException;

    Account getAccount(UUID id) throws RepositoryConverterException;

    List<Account> getAllAccount();

    void removeAccount(UUID id) throws RepositoryException;

    void removeAccount(Account account) throws RepositoryException;
}
