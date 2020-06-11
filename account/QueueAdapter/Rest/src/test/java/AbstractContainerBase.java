import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.nio.file.Paths;

public abstract class AbstractContainerBase {

    static final GenericContainer PAYARA = new GenericContainer(
            new ImageFromDockerfile()
                    .withDockerfileFromBuilder(builder ->
                            builder.from("sorcuss/tks-docker:1.0.0")
                                    .copy("AppWar.war", "/opt/payara/deployments")
                                    .build())
                    .withFileFromPath("AppWar.war", Paths.get("../../out/artifacts/AppWar")))
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/AppWar/restapi/account/accounts")
                    .forStatusCode(200));
    static Client client = ClientBuilder
            .newBuilder()
            .register(MoxyJsonFeature.class)
            .build();
    static String BASE_URL;
    Jsonb jsonb = JsonbBuilder.create(new JsonbConfig()
            .withDateFormat(JsonbDateFormat.DEFAULT_FORMAT, null));

    @BeforeAll
    public static void init() {
        PAYARA.start();
        BASE_URL = "http://localhost:" + PAYARA.getMappedPort(8080) + "/AppWar/restapi";
    }

    @AfterAll
    public static void destroy() {
        PAYARA.stop();
    }

}
