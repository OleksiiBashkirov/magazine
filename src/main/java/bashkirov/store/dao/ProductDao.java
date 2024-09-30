package bashkirov.store.dao;

import bashkirov.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public Product getById(int productId) {
        return jdbcTemplate.query(
                "select * from product where id = ?",
                new Object[]{productId},
                new BeanPropertyRowMapper<>(Product.class)
        ).stream().findAny().orElseThrow(
                () -> new NoSuchElementException("Failed to find product with id=" + productId)
        );
    }

    public List<Product> getAll() {
        return jdbcTemplate.query(
                "select * from product",
                new BeanPropertyRowMapper<>(Product.class)
        );
    }

    public void save(Product product) {
        jdbcTemplate.update(
                "insert into product(name, article, price, quantity, description) values (?,?,?,?,?)",
                product.getName(),
                product.getArticle(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription()
        );
    }

    public void update(int productId, Product product) {
        jdbcTemplate.update(
                "update product set name=?, article=?,price=?,quantity=?,description=? where id = ?",
                product.getName(),
                product.getArticle(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                productId
        );
    }

    public void delete(int productId) {
        jdbcTemplate.update(
                "delete from product where id = ?",
                productId
        );
    }

    public Optional<Product> findByArticle(String article) {
        return jdbcTemplate.query(
                "select * from product where article = ?",
                new Object[]{article},
                new BeanPropertyRowMapper<>(Product.class)
        ).stream().findAny();
    }

    public List<Product> getAllProductsInStoreByStoreId(int storeId) {
        return jdbcTemplate.query(
                "select * from product where store_id = ?",
                new Object[]{storeId},
                new BeanPropertyRowMapper<>(Product.class)
        );
    }
}
