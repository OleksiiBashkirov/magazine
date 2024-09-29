package bashkirov.store.Controller;

import bashkirov.store.dao.StoreDao;
import bashkirov.store.validation.StoreValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreDao storeDao;
    private final StoreValidator storeValidator;

    @GetMapping("/{id}")
    public String getById(
            @PathVariable("id") int storeId,
            Model model
    ) {
        model.addAttribute("storeGetById", storeDao.getById(storeId));
        return "store-new-page";
    }

    @GetMapping()
    public String getAll(
            Model model
    ) {
        model.addAttribute("storeList", storeDao.getAll());
        return "stores-page";
    }


}
