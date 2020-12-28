package org.sid.secuservice.secu.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.sid.secuservice.secu.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        //a VOIRRR
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        UsernamePasswordAuthenticationToken user=
                new UsernamePasswordAuthenticationToken(username,password);
               return authenticationManager.authenticate(user);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication");

        User authenticatedUser= (User) authResult.getPrincipal();
        Algorithm algorithm=Algorithm.HMAC256(JwtUtil.SECRET);
        String jwtAccessToken= JWT
                .create()
                .withSubject(authenticatedUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtUtil.ACCESS_TOKEN_TIME_OUT))//10 min
                .withIssuer(request.getRequestURL().toString())
                .withClaim(JwtUtil.ROLE_CLAIM_NAME,authenticatedUser.getAuthorities().stream().map((a)->a.getAuthority()).collect(Collectors.toList())
                )
                .sign(algorithm);

        String jwtRefreshToken= JWT
                .create()
                .withSubject(authenticatedUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtUtil.REFRESH_TOKEN_TIME_OUT))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String,String> accessToken=new HashMap<>();
        accessToken.put("Access_Token",jwtAccessToken);
        accessToken.put("Refresh_Token",jwtRefreshToken);
        response.setContentType("application/json");
        new JsonMapper().writeValue(response.getOutputStream(),accessToken);

    }
}
