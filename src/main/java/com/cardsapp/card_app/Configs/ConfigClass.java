package com.cardsapp.card_app.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigClass {

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(certs.publicKey()).privateKey(certs.privateKey()).build();
        JWKSource<SecurityContext> source = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(source);
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(certs.publicKey()).build();
    }
    
    @Bean
    public JWTService jwtService(){
        return new JWTService(this.jwtEncoder(), this.jwtDecoder());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
        .csrf((csrf)->csrf.disable())
        .authorizeHttpRequests((auth)->auth.requestMatchers(  "/v1/open/**",
        "/v2/**",
                        "/v3/**",
                        "/v3/api-docs/**",
                        "/sticky/api/v1/all/**",
                        "/cards-app/v3/api-docs",
                        "/cards-app/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html").permitAll())
        .authorizeHttpRequests((auth)->auth.anyRequest().authenticated())
        .sessionManagement((sessionManagement)-> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new AuthorizationFilter(jwtService()), UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Autowired
    private RSACerts certs;

}
