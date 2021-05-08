package ru.mirea.work.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.work.models.Product;
import ru.mirea.work.models.User;
import ru.mirea.work.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class UserController {
    private final TypeService typeService;
    private final ProductService productService;
    private final CountryTypeService countryTypeService;
    private final CountryService countryService;
    private final UserService userService;

    @Autowired
    public UserController(TypeService typeService,
                          ProductService productService,
                          CountryTypeService countryTypeService,
                          CountryService countryService,
                          UserService userService) {
        this.typeService = typeService;
        this.productService = productService;
        this.countryTypeService = countryTypeService;
        this.countryService = countryService;
        this.userService = userService;
    }

    private String getUserRole(Authentication authentication) {
        if (authentication == null)
            return "GUEST";
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getRole();
    }

    private int getUserId(Authentication authentication) {
        if (authentication == null)
            return 0;
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getId();
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        return "UserController/index";
    }
    @GetMapping("/products")
    public String products(@RequestParam(name = "typesId") int typesId,
                           @RequestParam(name = "countryId", required = false) Integer countryId,
                           Model model, Authentication authentication) {
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
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
                          Model model, Authentication authentication){
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        model.addAttribute("country",countryService.getCountryById(product.getCountriesId()));

        return "UserController/product";
    }
    @GetMapping("/feedback")
    public String feedback(Model model, Authentication authentication){
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        return "UserController/feedback";
    }
    @GetMapping("/sign")
    public String sign() {
        return "UserController/sign";
    }

    @PostMapping("/sign")
    public String signCreate(HttpServletRequest request,
                             @RequestParam(name = "email") String email,
                             @RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password,
                             Model model) {
        if (userService.loadUserByUsername(username) != null) {
            model.addAttribute("Status", "user_exists");
            return "UserController/sign";
        }
        else {
            userService.create(email,username,password);
            authWithHttpServletRequest(request, username, password);
            return "redirect:/";
        }
    }
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }
}

