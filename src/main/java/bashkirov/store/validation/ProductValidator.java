package bashkirov.store.validation;

import bashkirov.store.dao.ProductDao;
import bashkirov.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductValidator implements Validator {
    private final ProductDao productDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return Objects.equals(clazz, Product.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        Optional<Product> optionalProduct = productDao.findByArticle(product.getArticle());
        if (optionalProduct.isPresent()) {
            Product existedProduct = optionalProduct.get();
            if (product.getId() == 0 || existedProduct.getId() != product.getId()) {
                errors.rejectValue(
                        "article",
                        "",
                        String.format("Product with such article= %s is already exists", product.getArticle())
                );
            }
        }
    }
}
