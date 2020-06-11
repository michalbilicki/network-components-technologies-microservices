package rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Client")
@XmlType(namespace = "http://lapciakbilicki.pl/clients", name = "client", propOrder = {})
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientRestDTO implements Serializable {

    @XmlAttribute
    private String id;

    @XmlElement
    private String login;

    @XmlElement
    private String fullName;

    @XmlAttribute
    private boolean active;
}

