package bashkirov.store.dao;

import bashkirov.store.model.Product;
import bashkirov.store.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional
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

    public Optional<Store> getStoreWhereIsProduct(int productId) {
        return jdbcTemplate.query(
                "select s.* from store s join product p on s.id = p.store_id where p.id = ?",
                new Object[]{productId},
                new BeanPropertyRowMapper<>(Store.class)
        ).stream().findAny();
    }

    public void releaseProductFromStore(int productId) {
        jdbcTemplate.update(
                "update product set store_id = null where id = ?",
                productId
        );
    }

    public void assignStoreForProduct(int productId, int storeId) {
        jdbcTemplate.update(
                "update product set store_id = ? where id = ?",
                storeId,
                productId
        );
    }

    public List<Product> getProductsPaginated(int page, int size) {
        return jdbcTemplate.query(
                "select * from product order by id LIMIT ? OFFSET ?",
                new Object[]{size, (page * size)},
                new BeanPropertyRowMapper<>(Product.class)
        );
    }

    public int countProducts() {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from product",
                Integer.class
        );
        return (count != null) ? count : 0;
    }

    public List<Product> searchByNameOrArticle(String query) {
        String sql = "select * from product where name ILIKE ? OR article ILIKE ?";
        String likeQuery = "%" + query + "%";
        return jdbcTemplate.query(
                sql,
                new Object[]{likeQuery, likeQuery},
                new BeanPropertyRowMapper<>(Product.class)
        );
    }

    public List<Product> filterProducts(
            Double minPrice,
            Double maxPrice,
            Integer minQuantity,
            Integer maxQuantity,
            String name,
            String article
    ) {
        StringBuilder sql = new StringBuilder("select * from product where 1 = 1");
        List<Object> params = new ArrayList<>();

        if (minPrice != null) {
            sql.append(" AND price >= ?");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql.append(" AND price <= ?");
            params.add(maxPrice);
        }

        if (minQuantity != null) {
            sql.append(" AND quantity >= ?");
            params.add(minQuantity);
        }

        if (maxQuantity != null) {
            sql.append(" AND quantity <= ?");
            params.add(maxQuantity);
        }

        if (name != null && !name.isEmpty()) {
            sql.append(" AND name ILIKE ?");
            params.add("%" + name + "%");
        }

        if (article != null && !article.isEmpty()) {
            sql.append(" AND article ILIKE ?");
            params.add("%" + article + "%");
        }
        return jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                new BeanPropertyRowMapper<>(Product.class)
        );
    }
}
