package kh.edu.cstad.mbapi.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

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
                .anyRequest()
                .authenticated()
        );

//        # === DISABLE FORM LOGIN === #
//        http.formLogin(form -> form.disable());
        http.formLogin(AbstractHttpConfigurer::disable);

//        # === SET SECURITY MECHANISM === #
//        # === USE HTTP BASIC AUTHENTICATION === #
        http.httpBasic(Customizer.withDefaults());

//        # === DISABLE CSRF TOKEN === #
        http.csrf(csrf -> csrf.disable());

//        # === MAKE STATELESS === #
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("qwer"))
//                .roles(ROLE_ADMIN)
//                .build();
//        manager.createUser(admin);
//        UserDetails staff = User.builder()
//                .username("staff")
////                There are two ways to encode password
////                .password("{noop}qwer")
//                .password(passwordEncoder.encode("qwer"))
//                .roles(ROLE_STAFF)
//                .build();
//        manager.createUser(staff);
//        UserDetails customer = User.builder()
//                .username("customer")
//                .password(passwordEncoder.encode("qwer"))
//                .roles(ROLE_CUSTOMER)
//                .build();
//        manager.createUser(customer);
//        return manager;
//    }

}
