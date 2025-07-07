package examples.clients;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        classes = com.devsu.microservices.bankingmicroservice.clientservice.ClientServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("test")
public class ClientsRunner {

    @Karate.Test
    Karate testClients() {
        return Karate.run("clients").relativeTo(getClass());
    }

}
