package com.cardsapp.card_app.Configs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cardsapp.card_app.DTO.ResponseDto;
import com.fasterxml.jackson.databind.json.JsonMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter{
    private final JWTService jwtService;
    private List<String> publicRoutes = Arrays.asList("");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("AuthorizationFilter");
        if(isPathAllowed(request.getServletPath())|| request.getServletPath().contains("docs")||
        request.getServletPath().contains("/swagger-ui")){
            log.info("Public url being accessed: " + request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }

        String bearerToken = request.getHeader("Authorization");

        if(bearerToken == null || !bearerToken.startsWith("Bearer ")){
            errorMessega(new ResponseDto(400, "Invalid token"), response);
            return;
        }
        String token = bearerToken.substring("Bearer ".length());
        try{
            Collection<SimpleGrantedAuthority> authorities = jwtService.getAuthorities(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtService.getUsername(token) , true,authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
            return;
        }catch(Exception e){
            errorMessega(new ResponseDto(400, "error occured while authorizing: "+e.getLocalizedMessage()), response);
            return;
        }
    }

    private JsonMapper jsonMapper = new JsonMapper();
    
    public void errorMessega(ResponseDto responseDto, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        jsonMapper.writeValue(response.getOutputStream(), responseDto);        
    }

    private boolean isPathAllowed(String servletPath){
        for(String path : publicRoutes){
            if(servletPath.startsWith(path)){
                return true;
            }
        }
        return false;
    }
}
