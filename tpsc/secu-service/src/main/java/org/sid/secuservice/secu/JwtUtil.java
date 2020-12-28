package org.sid.secuservice.secu;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtil {
    public static final String SECRET="myHMACPrivateKey";
    public static final String AUTH_HEADER="Authorization";
    public static final String HEADER_PREFIX="Bearer ";
    public static final long ACCESS_TOKEN_TIME_OUT= 1*60*1000;
    public static final long REFRESH_TOKEN_TIME_OUT=10*60*1000;
    public static final String ROLE_CLAIM_NAME="roles";}

   /* public static String generateAccessToken(String username, String issuer, List<String> roles){

        Algorithm algorithm=Algorithm.HMAC256(JwtUtil.SECRET);
        String jwtAccessToken= JWT
                .create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtUtil.ACCESS_TOKEN_TIME_OUT))//10 min
                .withIssuer(issuer)
                //authenticatedUser.getAuthorities().stream().map((a)->a.getAuthority()).collect(Collectors.toList())
                .withClaim(JwtUtil.ROLE_CLAIM_NAME,roles)
                .sign(algorithm);
        return jwtAccessToken;

    }*/













  /*  public static String generateAccessToken(HttpServletRequest request, Authentication authResult, List<String> roles){
        User authenticatedUser= (User) authResult.getPrincipal();

        Algorithm algorithm=Algorithm.HMAC256(JwtUtil.SECRET);
        String jwtAccessToken= JWT
                .create()
                .withSubject(authenticatedUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtUtil.ACCESS_TOKEN_TIME_OUT))//10 min
                .withIssuer(request.getRequestURL().toString())
                //authenticatedUser.getAuthorities().stream().map((a)->a.getAuthority()).collect(Collectors.toList())
                .withClaim(JwtUtil.ROLE_CLAIM_NAME,roles)
                .sign(algorithm);
        return jwtAccessToken;
*/
