import endpoints.ClientEndpoint;
import endpoints.FacilityEndpoint;
import endpoints.ReservationEndpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("restapi")
public class ApplicationRestConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> h = new HashSet<Class<?>>();
        h.add(ClientEndpoint.class);
        h.add(ReservationEndpoint.class);
        h.add(FacilityEndpoint.class);
        return h;
    }
}
