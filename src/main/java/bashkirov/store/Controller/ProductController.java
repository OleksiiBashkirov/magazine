package bashkirov.store.Controller;

import bashkirov.store.config.ProductPageConfig;
import bashkirov.store.dao.ProductDao;
import bashkirov.store.dao.StoreDao;
import bashkirov.store.model.Product;
import bashkirov.store.model.Store;
import bashkirov.store.validation.ProductValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductDao productDao;
    private final ProductValidator productValidator;
    private final StoreDao storeDao;
    private final ProductPageConfig productPageConfig;

    @GetMapping("/{id}")
    public String getById(
            @PathVariable("id") int productId,
            Model model,
            @ModelAttribute("productOwner") Store productOwner
    ) {
        Optional<Store> optionalStore = productDao.getStoreWhereIsProduct(productId);
        if (optionalStore.isPresent()) {
            model.addAttribute("productStoreOwner", optionalStore.get());
        } else {
            model.addAttribute("storeList", storeDao.getAll());
        }

        model.addAttribute("productGetById", productDao.getById(productId));
        return "product-page";
    }

    @GetMapping
    public String getAll(
            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        int totalProducts = productDao.countProducts();
        int size = productPageConfig.getSize();
        int totalPages = (int) Math.ceil((double) totalProducts / size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("productList", productDao.getProductsPaginated(page, size));
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

    @GetMapping("/edit/{id}")
    public String productUpdatePage(
            @PathVariable("id") int productId,
            Model model
    ) {
        model.addAttribute("productUpdate", productDao.getById(productId));
        return "product-update-page";
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable("id") int productId,
            @Valid @ModelAttribute("productUpdate") Product productUpdate,
            BindingResult bindingResult
    ) {
        productValidator.validate(productUpdate, bindingResult);
        if (bindingResult.hasErrors()) {
            return "product-update-page";
        }
        productDao.update(productId, productUpdate);
        return "redirect:/product/" + productId;
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("id") int productId
    ) {
        productDao.delete(productId);
        return "redirect:/product";
    }

    @PutMapping("/release/{id}")
    public String release(
            @PathVariable("id") int productId
    ) {
        productDao.releaseProductFromStore(productId);
        return "redirect:/product/" + productId;
    }

    @PutMapping("/assign/{id}")
    public String assign(
            @PathVariable("id") int productId,
            @ModelAttribute("productOwner") Store storeWhereAssignProduct
    ) {
        productDao.assignStoreForProduct(productId, storeWhereAssignProduct.getId());
        return "redirect:/product/" + productId;
    }

    @GetMapping("/search")
    public String searchProduct(
            @RequestParam("query") String query,
            Model model
    ) {
        List<Product> productListSearch = productDao.searchByNameOrArticle(query);
        if (!productListSearch.isEmpty()) {
            model.addAttribute("productSearchList", productListSearch);
        } else {
            model.addAttribute("emptySearchList", List.of());
        }
        return "product-search-page";
    }
}
