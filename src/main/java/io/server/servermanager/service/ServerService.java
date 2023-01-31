package io.server.servermanager.service;

import io.server.servermanager.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
  Server create(Server server);
  Server ping(String ipAddress) throws IOException;
  Collection<Server> List(int limit);
  Server getServer(Long id);
  Server update(Server server);
  boolean delete(Long id);
}
