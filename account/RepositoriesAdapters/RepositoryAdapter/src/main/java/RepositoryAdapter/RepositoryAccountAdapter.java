package RepositoryAdapter;

import exceptions.RepositoryException;
import DomainModel.Account;
import DomainModel.Role;
import ApplicationPorts.AccountPort;
import DataModel.RoleEnt;
import DataModel.AccountEnt;
import Repository.AccountRepository;
import Repository.RoleRepository;
import exceptions.RepositoryConverterException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named("RepositoryAccountAdapter")
@ApplicationScoped
public class RepositoryAccountAdapter
        implements AccountPort {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private RoleRepository roleRepository;

    @Override
    public Account getAccount(UUID id) throws RepositoryConverterException {
        return RepositoryAccountConverter.convertTo(accountRepository.get(id.toString()));
    }

    @Override
    public List<Account> getAllAccounts() throws RepositoryConverterException {
        List<AccountEnt> toConvert = accountRepository.getAll();
        List<Account> result = new ArrayList<Account>();

        for (AccountEnt accountEnt : toConvert) {
            result.add(RepositoryAccountConverter.convertTo(accountEnt));
        }
        return result;
    }

    @Override
    public void removeAccount(Account arg) throws RepositoryException {
        accountRepository.remove(arg.getAccountId().toString());
    }

    @Override
    public void removeAccount(UUID id) throws RepositoryException {
        accountRepository.remove(id.toString());
    }

    @Override
    public void addAccount(Account arg) throws RepositoryException, RepositoryConverterException {
        List<RoleEnt> list = new ArrayList<>();
        for (Role item : arg.getRoles()) {
            list.add(this.roleRepository.getByBusinessId(item.getName()));
        }
        accountRepository.add(RepositoryAccountConverter.convertFrom(arg, list));
    }

    @Override
    public void updateAccount(Account arg) throws RepositoryException {
        try {
            List<RoleEnt> list = new ArrayList<>();
            for (Role item : arg.getRoles()) {
                list.add(this.roleRepository.getByBusinessId(item.getName()));
            }
            accountRepository.update(RepositoryAccountConverter.convertFrom(arg, list));
        } catch (RepositoryConverterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> getFilteredAccount(Predicate<Account> predicate) throws RepositoryConverterException {
        return this.getAllAccounts().stream().filter(predicate).collect(Collectors.toList());
    }
}
