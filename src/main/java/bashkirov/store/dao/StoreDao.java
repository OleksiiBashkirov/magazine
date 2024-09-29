package bashkirov.store.dao;

import bashkirov.store.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class StoreDao {
    private final JdbcTemplate jdbcTemplate;

    public Store getById(int magazineId) {
        return jdbcTemplate.query(
                "select * from store where id = ?",
                new Object[]{magazineId},
                new BeanPropertyRowMapper<>(Store.class)

        ).stream().findAny().orElseThrow(
                () -> new NoSuchElementException("Failed to find the magazine with id=" + magazineId)
        );
    }

    public List<Store> getAll() {
        return jdbcTemplate.query(
                "select * from store",
                new BeanPropertyRowMapper<>(Store.class)
        );
    }

    public void save(Store store) {
        jdbcTemplate.update(
                "insert into store(name, address, edrpou, specialnumber, description) values (?,?,?,?,?)",
                store.getName(),
                store.getAddress(),
                store.getEdrpou(),
                store.getSpecialNumber(),
                store.getDescription()
        );
    }

    public void update(int storeId, Store store) {
        jdbcTemplate.update(
                "update store set name = ?, address = ?, edrpou = ?, specialnumber = ?, description = ? where id = ?",
                store.getName(),
                store.getAddress(),
                store.getEdrpou(),
                store.getSpecialNumber(),
                store.getDescription(),
                storeId
        );
    }

    public void delete(int storeId) {
        jdbcTemplate.update(
                "delete from store where id = ?",
                storeId
        );
    }
}
