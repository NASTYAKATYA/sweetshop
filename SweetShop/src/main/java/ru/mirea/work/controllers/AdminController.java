package ru.mirea.work.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.work.models.*;
import ru.mirea.work.services.*;

import javax.persistence.Tuple;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final TypeService typeService;
    private final ProductService productService;
    private final CountryTypeService countryTypeService;
    private final CountryService countryService;
    private final UserService userService;

    @Autowired
    public AdminController(TypeService typeService,
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

    @GetMapping
    public String index(Authentication authentication, Model model) {
        model.addAttribute("userName", authentication.getName());
        return "AdminController/admin";
    }
    @GetMapping("/types")
    public String types(Model model) {
        model.addAttribute("types", typeService.getAllTypes());
        return "AdminController/admin-types";
    }
    @PostMapping("/types/create")
    public String typesCreate(@RequestParam(name = "name") String name) {
        Type newType = new Type();
        newType.setName(name);
        typeService.saveType(newType);
        return "redirect:/admin/types";
    }
    @PostMapping("/types/delete")
    public String typesDelete(@RequestParam(name = "id") int id) {
        typeService.deleteById(id);
        return "redirect:/admin/types";
    }
    @GetMapping("/countries")
    public String countries(Model model){
        model.addAttribute("countries", countryService.getAllCountries());
        return "AdminController/admin-countries";
    }
    @PostMapping("/countries/create")
    public String countriesCreate(@RequestParam(name = "name") String name){
        Country newCountry = new Country();
        newCountry.setName(name);
        countryService.saveCountry(newCountry);
        return "redirect:/admin/countries";
    }
    @PostMapping("/countries/delete")
    public String countriesDelete(@RequestParam(name = "id") int id){
        countryService.deleteById(id);
        return "redirect:/admin/countries";
    }
    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("products", productService.getAll());
        return "AdminController/admin-products";
    }
    @PostMapping("/products/create")
    public String createProduct(@RequestParam(name = "typesId") int typesId,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "price") int price,
                                @RequestParam(name = "weight") int weight,
                                @RequestParam(name =  "description") String description,
                                @RequestParam(name = "countriesId") int countriesId){
        Product newProduct = new Product();
        newProduct.setTypesId(typesId);
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setWeight(weight);
        newProduct.setDescription(description);
        newProduct.setCountriesId(countriesId);
        productService.saveProduct(newProduct);
        return "redirect:/admin/products";
    }
    @PostMapping("/products/delete")
    private String deleteProduct(@RequestParam(name = "id")int id){
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", userService.getAll());
        return "AdminController/admin-users";
    }
    @PostMapping("/users/create")
    public String createUser(@RequestParam(name = "email")String email,
                             @RequestParam(name = "username")String username,
                             @RequestParam(name = "password")String password,
                             @RequestParam(name = "role")String role){
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        userService.saveUser(newUser);
        return "redirect:/admin/users";
    }
    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam(name = "id")int id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/typesCountries")
    public String typesCountries(Model model){
        model.addAttribute("typesCountries", countryTypeService.getAll());
        return "AdminController/admin-typesCountries";
    }
    @PostMapping("/typesCountries/create")
    public String typesCountriesCreate(@RequestParam(name = "typesId") int typesId,
                                       @RequestParam(name = "countriesId") int countriesId){
        CountryType newCountryType = new CountryType();
        newCountryType.setTypesId(typesId);
        newCountryType.setCountriesId(countriesId);
        countryTypeService.saveCountryType(newCountryType);
        return "redirect:/admin/typesCountries";
    }
    @PostMapping("/typesCountries/delete")
    public String typesCountriesDelete(@RequestParam(name = "id") int id){
        countryTypeService.deleteById(id);
        return "redirect:/admin/typesCountries";
    }
}
