package org.sid.secuservice.secu.service;

import org.sid.secuservice.secu.entities.AppRole;
import org.sid.secuservice.secu.entities.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    //affecter un role a un user
    void addRoleToUser(String username,String roleName);
    //charger un user sachant le username
    AppUser loadUserByUsername(String username);
    //consulter list users
    List<AppUser> listUsers();
}
