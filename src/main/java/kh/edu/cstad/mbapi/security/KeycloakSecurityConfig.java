package kh.edu.cstad.mbapi.security;

import com.fasterxml.jackson.databind.type.CollectionLikeType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class KeycloakSecurityConfig {

    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_STAFF = "STAFF";
    private final String ROLE_CUSTOMER = "CUSTOMER";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//       # === MAKE ALL ENDPOINTS SECURE === #
        http.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers(HttpMethod.POST,"/api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF)
                .requestMatchers(HttpMethod.PUT,"/api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF)
                .requestMatchers(HttpMethod.DELETE,"/api/v1/customers/**").hasAnyRole(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET,"/api/v1/customers/**").permitAll()
                .requestMatchers("/api/v1/accounts/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER)
                .requestMatchers("/media/**").permitAll()
                .anyRequest()
                .authenticated()
        );

//        # === DISABLE FORM LOGIN === #
//        http.formLogin(form -> form.disable());
        http.formLogin(AbstractHttpConfigurer::disable);

//        # === SET SECURITY MECHANISM === #
//        # === USE JWT AUTHENTICATION === #
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

//        # === DISABLE CSRF TOKEN === #
        http.csrf(csrf -> csrf.disable());

//        # === MAKE STATELESS === #
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

//    # === CONVERT REALM ROLE ACCESS === #
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtCollectionConverter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");

            return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        };
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtCollectionConverter);
        return jwtAuthenticationConverter;
    }



}
