package bashkirov.store.Controller;

import bashkirov.store.dao.StoreDao;
import bashkirov.store.model.Store;
import bashkirov.store.validation.StoreValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "store-page";
    }

    @GetMapping()
    public String getAll(
            Model model
    ) {
        model.addAttribute("storeList", storeDao.getAll());
        return "stores-page";
    }

    @GetMapping("/new")
    public String storeNewPage(
            @ModelAttribute("newStore") Store newStore
    ) {
        return "store-new-page";
    }

    @PostMapping()
    public String save(
            @Valid @ModelAttribute("newStore") Store newStore,
            BindingResult bindingResult
    ) {
        storeValidator.validate(newStore, bindingResult);
        if (bindingResult.hasErrors()) {
            return "store-new-page";
        }
        storeDao.save(newStore);
        return "redirect:/store";
    }


}
