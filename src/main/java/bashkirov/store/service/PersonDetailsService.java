package bashkirov.store.service;

import bashkirov.store.model.Person;
import bashkirov.store.security.PersonDetails;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = getOptionalPerson(username);
        if (optionalPerson.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with such username= %s not found", username));
        }

        return new PersonDetails(optionalPerson.get());
    }

    public Optional<Person> getOptionalPerson(String username) {
        return jdbcTemplate.query(
                "select * from person where username = ?",
                new Object[]{username},
                new BeanPropertyRowMapper<>(Person.class)
        ).stream().findAny();
    }
}
