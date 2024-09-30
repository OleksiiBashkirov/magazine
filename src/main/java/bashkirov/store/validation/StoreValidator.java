package bashkirov.store.validation;

import bashkirov.store.dao.StoreDao;
import bashkirov.store.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StoreValidator implements Validator {
    private final StoreDao storeDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return Objects.equals(clazz, Store.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Store store = (Store) target;
        Optional<Store> optionalStore = storeDao.findByEdrpou(store.getEdrpou());
        if (optionalStore.isPresent()) {
            Store existedStore = optionalStore.get();
            if (store.getId() == 0 || store.getId() != existedStore.getId()) {
                errors.rejectValue(
                        "edrpou",
                        "",
                        String.format("Store with such EDRPOU= %s already exists", store.getEdrpou())
                );
            }
        }
        optionalStore = storeDao.findBySpecialNumber(store.getSpecialNumber());
        if (optionalStore.isPresent()) {
            Store existedStore = optionalStore.get();
            if (store.getId() == 0 || store.getId() != existedStore.getId()) {
                errors.rejectValue(
                        "specialNumber",
                        "",
                        String.format("Store with such specialNumber = %s already exists", store.getSpecialNumber())
                );
            }
        }
    }
}
