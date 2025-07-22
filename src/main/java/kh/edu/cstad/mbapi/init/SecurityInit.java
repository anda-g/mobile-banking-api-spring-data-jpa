package kh.edu.cstad.mbapi.init;

import jakarta.annotation.PostConstruct;
import kh.edu.cstad.mbapi.domain.Role;
import kh.edu.cstad.mbapi.domain.User;
import kh.edu.cstad.mbapi.repository.RoleRepository;
import kh.edu.cstad.mbapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SecurityInit {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        Role admin = Role.builder()
                .role("ADMIN")
                .build();
        Role staff = Role.builder()
                .role("STAFF")
                .build();
        Role customer = Role.builder()
                .role("CUSTOMER")
                .build();
        if(roleRepository.count() == 0) roleRepository.saveAll(List.of(admin, staff, customer));
        if(userRepository.count() == 0){
            User userAdmin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("qwer"))
                    .roles(List.of(admin))
                    .isEnabled(true)
                    .build();

            User userStaff = User.builder()
                    .username("staff")
                    .password(passwordEncoder.encode("qwer"))
                    .roles(List.of(staff))
                    .isEnabled(true)
                    .build();

            User userCustomer = User.builder()
                    .username("customer")
                    .password(passwordEncoder.encode("qwer"))
                    .roles(List.of(customer))
                    .isEnabled(true)
                    .build();

            userRepository.saveAll(List.of(userAdmin, userStaff, userCustomer));
        }

    }
}
