package bashkirov.store.validation;

import bashkirov.store.model.Person;
import bashkirov.store.service.PersonDetailsService;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Objects.equals(clazz, Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> optionalPerson = personDetailsService.getOptionalPerson(person.getUsername());
        if (optionalPerson.isPresent()) {
            Person existedPerson = optionalPerson.get();
            if(person.getId() == 0 || person.getId() != existedPerson.getId()) {
                errors.rejectValue(
                        "username",
                        "",
                        String.format("User with such username= %s already exists", person.getUsername())
                );
            }
        }
    }
}
