package io.server.servermanager;

import io.server.servermanager.model.Server;
import io.server.servermanager.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static io.server.servermanager.enumeration.Status.SERVER_UP;

@SpringBootApplication
public class ServerManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerManagerApplication.class, args);
  }
  @Bean
  CommandLineRunner run(ServerRepository serverRepository){
    return args -> {


      serverRepository.save(new Server(null,"192.168.1.302","AWS","64 GB","Dell",
        "http://localhost:8080/server/image/server2.png", SERVER_UP));
    };
  }
}
