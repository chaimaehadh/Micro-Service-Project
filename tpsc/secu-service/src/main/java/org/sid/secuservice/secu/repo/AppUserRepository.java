package org.sid.secuservice.secu.repo;

import org.sid.secuservice.secu.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
AppUser findByUsername(String username);

}
