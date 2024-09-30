package bashkirov.store.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;

    @NotBlank(message = "Cannot be empty")
    @Size(min = 1, max = 64, message = "Cannot be less 1 and more 64 characters")
    private String name;

    @NotBlank(message = "Cannot be empty")
    @Size(min = 9, max = 9, message = "Must be exactly 9 symbols")
    @Pattern(regexp = "^\\d{4}-\\d{4}(-\\d{1,3})?$", message = "Must be like `1234-1234` and optional `-123`")
    private String article;

    @Min(value = 0, message = "Cannot be less 0")
    @Max(value = 100_000, message = "Cannot be more 100 000")
    private double price;

    @Min(value = 0, message = "Cannot be less 0")
    @Max(value = 10_000, message = "Cannot be more 10 000")
    private double quantity;

    @Size(max = 256, message = "Cannot be more 256 characters")
    private String description;
}
