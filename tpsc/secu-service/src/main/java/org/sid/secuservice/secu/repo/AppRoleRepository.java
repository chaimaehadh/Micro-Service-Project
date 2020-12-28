package org.sid.secuservice.secu.repo;

import org.sid.secuservice.secu.entities.AppRole;
import org.sid.secuservice.secu.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
AppRole findByRoleName(String roleName);

}
