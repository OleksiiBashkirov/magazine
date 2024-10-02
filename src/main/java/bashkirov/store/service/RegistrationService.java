package bashkirov.store.service;

import bashkirov.store.enumaration.Role;
import bashkirov.store.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.ROLE_USER);

        jdbcTemplate.update(
                "insert into person(name, lastname, username, password, role) values (?,?,?,?,?)",
                person.getName(),
                person.getLastname(),
                person.getUsername(),
                person.getPassword(),
                person.getRole().toString()
        );
    }
}
