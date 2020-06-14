package managers;

import dto.AccountDto;
import dto.ClientDto;
import endpoints.ClientEndpoint;
import queue.Receiver;
import queue.Sender;
import utils.Consts;
import utils.exception.ManagerException;
import utils.exception.SenderException;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountManager {

    //region AddAccount
    public void addAccount(AccountDto accountDto) throws ManagerException, SenderException {
        String corrId = accountDto.getId();
        Sender<AccountDto> accountSender = new Sender<AccountDto>(Consts.ADD_ACCOUNT_QUEUE);
        accountSender.send(accountDto, corrId);
        Receiver accountReceiver = new Receiver(Consts.ADD_ACCOUNT_QUEUE);
        if (Boolean.parseBoolean(accountReceiver.receive(corrId))) {
            if (accountDto.getRoles().contains("Client")) {
                addClient(accountDto);
            }
        } else {
            throw new ManagerException();
        }
    }

    private void addClient(AccountDto accountDto) throws ManagerException, SenderException {
        String corrId = accountDto.getId();
        ClientDto clientDto = ClientDto.convertFrom(accountDto);
        Sender<ClientDto> clientSender = new Sender<>(Consts.ADD_CLIENT_QUEUE);
        clientSender.send(clientDto, corrId);
        Receiver clientReceiver = new Receiver(Consts.ADD_CLIENT_QUEUE);
        if (!Boolean.parseBoolean(clientReceiver.receive(corrId))) {
            addAccountRepair(accountDto);
            throw new ManagerException();
        }
    }

    private void addAccountRepair(AccountDto accountDto) throws SenderException {
        Sender<String> repairSender = new Sender<>(Consts.ADD_ACCOUNT_REPAIR);
        repairSender.asyncSend(accountDto.getId());
    }
    //endregion

    //region GetAccount
    public AccountDto getAccount(String id) throws ManagerException {
        try {
            String corrId = id;
            Jsonb jsonb = JsonbBuilder.create();
            Sender<String> sender = new Sender<>(Consts.GET_ACCOUNT_QUEUE);
            sender.send(id, corrId);
            Receiver receiver = new Receiver(Consts.GET_ACCOUNT_QUEUE);
            String json = receiver.receive(corrId);
            AccountDto accountDto = jsonb.fromJson(json, AccountDto.class);
            if (accountDto != null) {
                return accountDto;
            } else {
                throw new ManagerException();
            }
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }

    public List<AccountDto> getAllAccounts() throws ManagerException {
        try {
            String corrId = UUID.randomUUID().toString();
            Jsonb jsonb = JsonbBuilder.create();

            Sender<String> sender = new Sender<String>(Consts.GET_ALL_ACCOUNT_QUEUE);
            sender.send(corrId, corrId);

            Receiver receiver = new Receiver(Consts.GET_ALL_ACCOUNT_QUEUE);
            String json = receiver.receive(corrId);
            return Arrays.asList(jsonb.fromJson(json, AccountDto[].class));
        } catch (JsonbException e) {
            throw new ManagerException();
        }
    }
    //endregion

    //region UpdateAccount
    public void updateAccount(AccountDto accountDto) throws ManagerException, SenderException {
        String corrId = accountDto.getId();
        AccountDto checkAccountDto = getAccount(accountDto.getId());
        Sender<AccountDto> sender = new Sender<AccountDto>(Consts.UPDATE_ACCOUNT_QUEUE);
        sender.send(accountDto, corrId);
        Receiver updateReceiver = new Receiver(Consts.UPDATE_ACCOUNT_QUEUE);
        if (Boolean.parseBoolean(updateReceiver.receive(corrId))) {
            if (accountDto.getRoles().contains("Client") && checkAccountDto.getRoles().contains("Client")) {
                updateClient(accountDto, checkAccountDto);
            } else if (!accountDto.getRoles().contains("Client") && checkAccountDto.getRoles().contains("Client")) {
                deleteClient(checkAccountDto);
            }
        } else {
            throw new ManagerException();
        }
    }

    private void updateClient(AccountDto accountDto, AccountDto checkAccountDto) throws SenderException, ManagerException {
        String corrId = accountDto.getId();
//        Sender<ClientDto> updateSender = new Sender<>(Consts.UPDATE_CLIENT_QUEUE);
//        updateSender.send(ClientDto.convertFrom(accountDto), corrId);
//        Receiver updateReceiver = new Receiver(Consts.UPDATE_CLIENT_QUEUE);
//        if (!Boolean.parseBoolean(updateReceiver.receive(corrId))) {
            updateAccountRepair(checkAccountDto);
            throw new ManagerException();
//        }
    }

    private void updateAccountRepair(AccountDto checkAccountDto) throws SenderException {
        Sender<AccountDto> repairSender = new Sender<>(Consts.UPDATE_ACCOUNT_REPAIR);
        repairSender.asyncSend(checkAccountDto);
    }
    //endregion

    //region DeleteAccount
    public void deleteAccount(String id) throws ManagerException, SenderException {
        String corrId = id;
        AccountDto accountDto = getAccount(id);
        Sender<String> deleteSender = new Sender<>(Consts.REMOVE_ACCOUNT_QUEUE);
        deleteSender.send(id, corrId);
        Receiver deleteReceiver = new Receiver(Consts.REMOVE_ACCOUNT_QUEUE);
        if (Boolean.parseBoolean(deleteReceiver.receive(corrId))) {
            if (accountDto.getRoles().contains("Client")) {
                deleteClient(accountDto);
            }
        } else {
            throw new ManagerException();
        }
    }

    private void deleteClient(AccountDto accountDto) throws ManagerException, SenderException {
        String corrId = accountDto.getId();
//        Sender<String> deleteSender = new Sender<>(Consts.REMOVE_CLIENT_QUEUE);
//        deleteSender.send(accountDto.getId(), corrId);
//        Receiver deleteReceiver = new Receiver(Consts.REMOVE_CLIENT_QUEUE);
//        if (!Boolean.parseBoolean(deleteReceiver.receive(corrId))) {
            deleteAccountRepair(accountDto);
            throw new ManagerException();
//        }
    }

    private void deleteAccountRepair(AccountDto accountDto) throws SenderException {
        Sender<AccountDto> repairSender = new Sender<>(Consts.REMOVE_ACCOUNT_REPAIR);
        repairSender.asyncSend(accountDto);
    }
    //endregion

    //region Block Account
    public void blockAccount(String id) throws ManagerException, SenderException {
        String corrId = id;
        AccountDto accountDto = getAccount(id);
        Sender<String> blockSender = new Sender<String>(Consts.BLOCK_ACCOUNT_QUEUE);
        blockSender.send(id, corrId);
        Receiver receiver = new Receiver(Consts.BLOCK_ACCOUNT_QUEUE);
        if (Boolean.parseBoolean(receiver.receive(corrId))) {
            if (accountDto.getRoles().contains("Client")) {
                blockClient(accountDto);
            }
        } else {
            throw new ManagerException();
        }
    }

    private void blockClient(AccountDto accountDto) throws SenderException, ManagerException {
        String corrId = accountDto.getId();
//        Sender<String> blockSender = new Sender<>(Consts.BLOCK_CLIENT_QUEUE);
//        blockSender.send(accountDto.getId(), corrId);
//        Receiver deleteReceiver = new Receiver(Consts.BLOCK_CLIENT_QUEUE);
//        if (!Boolean.parseBoolean(deleteReceiver.receive(corrId))) {
            blockAccountRepair(accountDto);
            throw new ManagerException();
//        }
    }

    private void blockAccountRepair(AccountDto accountDto) throws SenderException {
        Sender<String> repairSender = new Sender<>(Consts.BLOCK_ACCOUNT_REPAIR);
        repairSender.asyncSend(accountDto.getId());
    }
    //endregion

    //region Unblock Account
    public void unblockAccount(String id) throws ManagerException, SenderException {
        String corrId = id;
        AccountDto accountDto = getAccount(id);
        Sender<String> unblockSender = new Sender<String>(Consts.UNBLOCK_ACCOUNT_QUEUE);
        unblockSender.send(id, corrId);
        Receiver receiver = new Receiver(Consts.UNBLOCK_ACCOUNT_QUEUE);
        if (Boolean.parseBoolean(receiver.receive(corrId))) {
            if (accountDto.getRoles().contains("Client")) {
                unblockClient(accountDto);
            }
        } else {
            throw new ManagerException();
        }
    }

    private void unblockClient(AccountDto accountDto) throws SenderException, ManagerException {
        String corrId = accountDto.getId();
//        Sender<String> unblockSender = new Sender<>(Consts.UNBLOCK_CLIENT_QUEUE);
//        unblockSender.send(accountDto.getId(), corrId);
//        Receiver deleteReceiver = new Receiver(Consts.UNBLOCK_CLIENT_QUEUE);
//        if (!Boolean.parseBoolean(deleteReceiver.receive(corrId))) {
            unblockAccountRepair(accountDto);
            throw new ManagerException();
//        }
    }

    private void unblockAccountRepair(AccountDto accountDto) throws SenderException {
        Sender<String> repairSender = new Sender<>(Consts.UNBLOCK_ACCOUNT_REPAIR);
        repairSender.asyncSend(accountDto.getId());
    }
    //endregion
}
