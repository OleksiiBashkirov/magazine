package bashkirov.store.Controller;

import bashkirov.store.dao.ProductDao;
import bashkirov.store.model.Product;
import bashkirov.store.validation.ProductValidator;
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
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductDao productDao;
    private final ProductValidator productValidator;

    @GetMapping("/{id}")
    public String getById(
            @PathVariable("id") int productId,
            Model model
    ) {
        model.addAttribute("productGetById", productDao.getById(productId));
        return "product-page";
    }

    @GetMapping
    public String getAll(
            Model model
    ) {
        model.addAttribute("productList", productDao.getAll());
        return "products-page";
    }

    @GetMapping("/new")
    public String productNewPage(
            @ModelAttribute("newProduct") Product newProduct
    ) {
        return "product-new-page";
    }

    @PostMapping()
    public String save(
            @Valid @ModelAttribute("newProduct") Product newProduct,
            BindingResult bindingResult

    ) {
        productValidator.validate(newProduct, bindingResult);
        if (bindingResult.hasErrors()) {
            return "product-new-page";
        }
        productDao.save(newProduct);
        return "redirect:/product";
    }

}
