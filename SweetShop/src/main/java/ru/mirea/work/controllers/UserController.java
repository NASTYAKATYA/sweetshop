package ru.mirea.work.controllers;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.work.models.Product;
import ru.mirea.work.models.Purchase;
import ru.mirea.work.models.User;
import ru.mirea.work.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    private final TypeService typeService;
    private final ProductService productService;
    private final CountryTypeService countryTypeService;
    private final CountryService countryService;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final EmailService emailService;
    private final CriteriaService criteriaService;

    private String addPurchaseStatus = "";

    @Autowired
    public UserController(TypeService typeService,
                          ProductService productService,
                          CountryTypeService countryTypeService,
                          CountryService countryService,
                          UserService userService,
                          PurchaseService purchaseService, EmailService emailService,
                          CriteriaService criteriaService) {
        this.typeService = typeService;
        this.productService = productService;
        this.countryTypeService = countryTypeService;
        this.countryService = countryService;
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.emailService = emailService;
        this.criteriaService=criteriaService;
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
    private void reloadAddPurchaseStatus() {
        addPurchaseStatus = "";
    }
    private void setAddPurchaseStatus(String status) {
        addPurchaseStatus = status;
    }

    private int getTotalPrice(List<Purchase> purchases) {
        int result = 0;
        for (Purchase purchase: purchases) {
            result += productService.getProduct(purchase.getProductId()).getPrice() * purchase.getProductCount();
        }
        return result;
    }

    private String createMessageForUser(List<Purchase> userPurchases) {
        List<Product> userProducts = new ArrayList<>();
        for (Purchase purchase: userPurchases) {
            userProducts.add(productService.getProduct(purchase.getProductId()));
        }
        String result = "????????????????????????, ?????? ??????????:<br>";
        for (int i = 0; i < userProducts.size(); i++) {
            result += (i + 1) + ") " + userProducts.get(i).getName() + " (????????????????????: " + userPurchases.get(i).getProductCount() + ", ??????????????????: " + (userProducts.get(i).getPrice()*userPurchases.get(i).getProductCount()) + " ??.)<br>";
        }
        result += "?????????? ??????????????????: " + getTotalPrice(userPurchases) + " ??.<br>";
        return result;
    }

    private String createMessageForManager(User user, String address, String telephone, List<Purchase> userPurchases) {
        List<Product> userProducts = new ArrayList<>();
        for (Purchase purchase: userPurchases) {
            userProducts.add(productService.getProduct(purchase.getProductId()));
        }
        String result = "???????????????????? ?? ??????????????????:<br>";
        result += "??????????: " + user.getEmail() + "<br>";
        result += "?????? ????????????????????????: " + user.getUsername() + "<br>";
        result += "??????????????: " + telephone + "<br>";
        result += "??????????: " + address + "<br>";
        result += "??????????:<br>";
        for (int i = 0; i < userProducts.size(); i++) {
            result += (i + 1) + ") " + userProducts.get(i).getName() + " (????????????????????: " + userPurchases.get(i).getProductCount() + ", ??????????????????: " + (userProducts.get(i).getPrice()*userPurchases.get(i).getProductCount()) + " ??.)<br>";
        }
        result += "?????????? ??????????????????: " + getTotalPrice(userPurchases) + " ??.<br>";
        return result;
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
        model.addAttribute("Status", addPurchaseStatus);
        reloadAddPurchaseStatus();
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
    @PostMapping("/product")
    public String addToBasket(Authentication authentication,
                              @RequestParam(name = "productId") int productId,
                              @RequestParam(name = "productCount") int productCount) {
        String userRole = getUserRole(authentication);
        String redirectString = "redirect:/product?productId=" + productId;
        if (userRole == "GUEST") {
            setAddPurchaseStatus("user_guest");
            return redirectString;
        }
        else {
            int userId = getUserId(authentication);
            Purchase purchase = purchaseService.getPurchaseByUserIdAndProductId(userId, productId);
            if (purchase == null) {
                Purchase newPurchase = new Purchase();
                newPurchase.setUserId(userId);
                newPurchase.setProductId(productId);
                newPurchase.setProductCount(productCount);
                purchaseService.savePurchase(newPurchase);
                setAddPurchaseStatus("OK");
                return redirectString;
            }
            else {
                if (purchase.getProductCount() + productCount > 10)
                {
                    setAddPurchaseStatus("count_overflow");
                    return redirectString;
                }
                else
                {
                    purchase.setProductCount(purchase.getProductCount() + productCount);
                    purchaseService.savePurchase(purchase);
                    setAddPurchaseStatus("OK");
                    return redirectString;
                }
            }
        }
    }
    @GetMapping("/basket")
    public String basket(Model model, Authentication authentication){
        String userRole = getUserRole(authentication);
        int userId = getUserId(authentication);
        model.addAttribute("totalPrice", getTotalPrice(purchaseService.getPurchasesByUserId(userId)));
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        List<Purchase> purchases = purchaseService.getPurchasesByUserId(userId);
        Collections.sort(purchases, Comparator.comparing(Purchase::getId));
        model.addAttribute("basket", purchases);
        model.addAttribute("productService", productService);

        return "UserController/basket";
    }
    @PostMapping(value = "/basketOperation", params = "change")
    public String changeBasket(@RequestParam(name = "purchaseId[]") int[] purchaseIds,
                               @RequestParam(name = "productCount[]") int[] productCounts) {
        for (int i = 0; i < purchaseIds.length; i++) {
            Purchase purchase = purchaseService.getPurchaseById(purchaseIds[i]);
            purchase.setProductCount(productCounts[i]);
            purchaseService.savePurchase(purchase);
        }
        return "redirect:/basket";
    }
    @PostMapping(value = "/basketOperation", params = "delete")
    public String deleteBasket(@RequestParam(name = "purchaseToDeleteId") int purchaseToDeleteId) {
        purchaseService.deletePurchaseById(purchaseToDeleteId);
        return "redirect:/basket";
    }
    @SneakyThrows
    @PostMapping(value = "/sendBasket")
    public String sendBasket(Authentication authentication,
                             @RequestParam(name = "address") String address,
                             @RequestParam(name = "telephone") String telephone) {
        User user = (User)userService.loadUserByUsername(authentication.getName());
        String userMessage = createMessageForUser(purchaseService.getPurchasesByUserId(user.getId()));
        String managerMessage = createMessageForManager(user, address, telephone, purchaseService.getPurchasesByUserId(user.getId()));
        emailService.sendmail(user.getEmail(), userMessage, false);
        emailService.sendmail("katyabi2010@gmail.com", managerMessage, true);
        purchaseService.deleteAllByUserId(user.getId());
        return "redirect:/basket";
    }
    @GetMapping("/search")
    public String searchProduct(@RequestParam(name ="name") String name,
                                       Model model, Authentication authentication){
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("products",criteriaService.getAllByName(name));
        return "UserController/search";
    }
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }
}

