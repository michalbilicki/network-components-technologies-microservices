package Repository.filler;

import DataModel.RoleEnt;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RoleFiller implements Filler<RoleEnt>, Serializable {

    @Override
    public void autoFill(List<RoleEnt> destination) {
        destination.add(new RoleEnt(UUID.randomUUID().toString(), "Client"));
        destination.add(new RoleEnt(UUID.randomUUID().toString(), "Resources_Admin"));
        destination.add(new RoleEnt(UUID.randomUUID().toString(), "User_Admin"));
    }
}
