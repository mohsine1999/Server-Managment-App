package io.server.servermanager.resource;

import io.server.servermanager.model.Response;
import io.server.servermanager.model.Server;
import io.server.servermanager.service.ServerServiceImplement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.server.servermanager.enumeration.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
  private final ServerServiceImplement serverServiceImplement;

  @GetMapping("/list")
  public ResponseEntity<Response> getServers(){
  return ResponseEntity.ok(
    Response.builder()
      .timeStamp(now())
      .data(of("servers",serverServiceImplement.List(30)))
      .message("servers retrieved")
      .status(OK)
      .statusCode(OK.value())
      .build()
  );
  }

  @GetMapping("/ping/{ipAddress}")
  public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
    Server server = serverServiceImplement.ping(ipAddress);
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(of("server",server))
        .message(server.getStatus() == SERVER_UP ? "Ping success" : "Ping failed")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }

  @PostMapping("/save")
  public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(of("server",serverServiceImplement.create(server)))
        .message("Server Created")
        .status(CREATED)
        .statusCode(CREATED.value())
        .build()
    );
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(of("server",serverServiceImplement.getServer(id)))
        .message("Server retrieved")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }

  @DeleteMapping ("/delete/{id}")
  public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(of("deleted",serverServiceImplement.delete(id)))
        .message("Server deleted")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }

  @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
  public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
    return Files.readAllBytes(Paths.get(System.getProperty("user.home" ) + "/Downloads/images/" +fileName));
  }
}
