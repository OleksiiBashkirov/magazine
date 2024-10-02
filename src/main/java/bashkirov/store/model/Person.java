package bashkirov.store.model;

import bashkirov.store.enumaration.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private int id;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 2, max = 32, message = "Cannot be less 2 and more 32 characters")
    private String name;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 2, max = 32, message = "Cannot be less 2 and more 32 characters")
    private String lastname;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 2, max = 64, message = "Cannot be less 2 and more 64 characters")
    private String username;

    @NotBlank(message = "Cannot be empty")
    private String password;

    private Role role;
}
