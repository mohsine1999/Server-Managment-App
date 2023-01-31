package io.server.servermanager.repository;

import io.server.servermanager.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
  Server findByIpAddress(String ipAddress);

}
