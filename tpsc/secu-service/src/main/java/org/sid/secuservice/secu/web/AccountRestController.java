package org.sid.secuservice.secu.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.sid.secuservice.secu.JwtUtil;
import org.sid.secuservice.secu.entities.AppRole;
import org.sid.secuservice.secu.entities.AppUser;
import org.sid.secuservice.secu.service.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {
    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping(path = "/users")
    // @enableclobalmethodsecurity
    @PreAuthorize("hasAuthority('USER')")

    public List<AppUser> appUsers() {
        return accountService.listUsers();
    }
    @PostMapping(path = "/users")
    @PreAuthorize("hasAuthority('ADMIN')")

    public AppUser saveUser(@RequestBody AppUser appUser) {
        return accountService.addNewUser(appUser);
    }
    @PostMapping(path = "/roles")
    @PreAuthorize("hasAuthority('ADMIN')")

    public AppRole saveRole(@RequestBody AppRole appRole) {
        return accountService.addNewRole(appRole);
    }
    @PostMapping(path = "/addRoleToUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(),
                roleUserForm.getRoleName());
    }
    @GetMapping("/refreshToken")
    public Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String token = request.getHeader(JwtUtil.AUTH_HEADER);
        if (token != null && token.startsWith(JwtUtil.HEADER_PREFIX)) {
            try {
                String jwtRefreshToken = token.substring(JwtUtil.HEADER_PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
                String username = decodedJWT.getSubject();
                //Test revocation de tkrn=>Black  list
                AppUser appUser = accountService.loadUserByUsername(username);
                String jwtAccessToken = JWT
                        .create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.ACCESS_TOKEN_TIME_OUT))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(JwtUtil.ROLE_CLAIM_NAME, appUser.getAppRoles().stream().map(e -> e.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> accessToken = new HashMap<>();
                accessToken.put("Access_Token", jwtAccessToken);
                accessToken.put("Refresh_Token", jwtRefreshToken);
                return accessToken;
            } catch (TokenExpiredException e) {
                response.setHeader("Error-Message", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        throw new RuntimeException("Bad Refresh Token");
    }


}

@Data
class RoleUserForm{
    private String username;
    private String roleName;
}
