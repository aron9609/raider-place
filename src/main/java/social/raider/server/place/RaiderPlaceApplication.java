package social.raider.server.place;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RaiderPlaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RaiderPlaceApplication.class, args);
    }

}
