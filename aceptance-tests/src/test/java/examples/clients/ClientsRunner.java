package examples.clients;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ClientsRunner {

    @Karate.Test
    Karate testClients() {
        return Karate.run("clients").relativeTo(getClass());
    }

}
