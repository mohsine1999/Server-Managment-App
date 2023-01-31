package io.server.servermanager.service;

import io.server.servermanager.model.Server;
import io.server.servermanager.repository.ServerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static io.server.servermanager.enumeration.Status.SERVER_DOWN;
import static io.server.servermanager.enumeration.Status.SERVER_UP;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImplement implements ServerService{

  private final ServerRepository serverRepository;
  @Override
  public Server create(Server server) {
    log.info("Saving a new server:{}", server.getName());
    server.setImageUrl(setServerImageUrl());
    return serverRepository.save(server);
  }


  @Override
  public Server ping(String ipAddress) throws IOException {
    log.info("Pinging a new server IP:{}", ipAddress);
    Server server = serverRepository.findByIpAddress(ipAddress);
    InetAddress inetAddress = InetAddress.getByName(ipAddress);
    server.setStatus(inetAddress.isReachable(20000) ? SERVER_UP : SERVER_DOWN);
    serverRepository.save(server);
    return server;
  }

  @Override
  public Collection<Server> List(int limit) {
    log.info("fetching all servers");
    return serverRepository.findAll(PageRequest.of(0,limit)).toList();
  }

  @Override
  public Server getServer(Long id) {
    log.info("Fetching a new server by Id:{}");
    return serverRepository.findById(id).get();
  }

  @Override
  public Server update(Server server) {
    log.info("Updating a server:{}", server.getName());
    return serverRepository.save(server);
  }

  @Override
  public boolean delete(Long id) {
    log.info("Deleting  server bi Id:{}");
    serverRepository.deleteById(id);
    return true;
  }

  private String setServerImageUrl() {
    String[] imageNames = {"server1.png","server2.png","server3.png","server4.png"};
    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
  }
}
