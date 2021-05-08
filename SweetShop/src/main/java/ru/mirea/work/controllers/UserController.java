package ru.mirea.work.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.work.models.Product;
import ru.mirea.work.services.CountryService;
import ru.mirea.work.services.CountryTypeService;
import ru.mirea.work.services.ProductService;
import ru.mirea.work.services.TypeService;

@Controller
@RequestMapping("/")
public class UserController {
    private final TypeService typeService;
    private final ProductService productService;
    private final CountryTypeService countryTypeService;
    private final CountryService countryService;

    @Autowired
    public UserController(TypeService typeService,
                          ProductService productService,
                          CountryTypeService countryTypeService,
                          CountryService countryService) {
        this.typeService = typeService;
        this.productService = productService;
        this.countryTypeService = countryTypeService;
        this.countryService = countryService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("types", typeService.getAllTypes());
        return "UserController/index";
    }
    @GetMapping("/products")
    public String products(@RequestParam(name = "typesId") int typesId,
                           @RequestParam(name = "countryId", required = false) Integer countryId,
                           Model model) {

        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("typesId", typesId);
        model.addAttribute("countryService", countryService);
        model.addAttribute("countryTypes", countryTypeService.getCountriesByTypeId(typesId));
        if (countryId == null)
            model.addAttribute("products", productService.getAllProductsByTypesId(typesId));
        else
            model.addAttribute("products", productService.getAllProductsByTypesIdAndCountriesId(typesId, countryId));
        return "UserController/products";
    }
    @GetMapping("/product")
    public String product(@RequestParam(name ="productId") int productId,
                          Model model){
        model.addAttribute("types", typeService.getAllTypes());
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        model.addAttribute("country",countryService.getCountryById(product.getCountriesId()));

        return "UserController/product";
    }
    @GetMapping("/feedback")
    public String feedback(Model model){
        model.addAttribute("types", typeService.getAllTypes());
        return "UserController/feedback";
    }
}

