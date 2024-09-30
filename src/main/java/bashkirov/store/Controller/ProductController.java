package bashkirov.store.Controller;

import bashkirov.store.dao.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductDao productDao;

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

}
