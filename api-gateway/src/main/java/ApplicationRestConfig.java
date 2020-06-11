import endpoints.AccountEndpoint;
import endpoints.ReservationEndpoint;
import endpoints.FacilityEndpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class ApplicationRestConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> h = new HashSet<Class<?>>();
        h.add(AccountEndpoint.class);
        h.add(ReservationEndpoint.class);
        h.add(FacilityEndpoint.class);
        return h;
    }
}