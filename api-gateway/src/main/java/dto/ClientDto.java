package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto implements Serializable {

    private String id;

    private String login;

    private String fullName;

    private boolean active;

//    private String corrId;

    public static ClientDto convertFrom(AccountDto accountDto) {
        return new ClientDto(
                accountDto.getId(),
                accountDto.getLogin(),
                accountDto.getFullName(),
                accountDto.isActive()
        );
    }

}

