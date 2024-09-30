package bashkirov.store.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private int id;

    @NotBlank(message = "This field cannot be blank")
    @Size(min = 1, max = 128, message = "This field cannot be less 1 and more 128 characters")
    private String name;

    @NotBlank(message = "This field cannot be blank")
    @Size(min = 5, max = 128, message = "This field cannot be less 5 and more 128 characters")
    @Pattern(regexp = "^\\d{5}, [A-Za-z]{2,25}, [A-Za-z0-9,. '-]{2,50}$", message = "Should be in format `01023, Ukraine, Kyiv, vul. Ivanova, 128-e`")
    private String address;

    @NotBlank(message = "This field cannot be blank")
    @Size(min = 8, max = 8, message = "This field should have exactly 8 digits")
    @Pattern(regexp = "^\\d{8}$")
    private String edrpou;

    @NotBlank(message = "This field cannot be blank")
    @Pattern(regexp = "^[A-Z]{2}-\\d{6}$")  // KV-123456
    @Size(min = 9, max = 9)
    private String specialNumber;

    @Size(max = 256, message = "This field cannot be more 256 characters")
    private String description;
}
