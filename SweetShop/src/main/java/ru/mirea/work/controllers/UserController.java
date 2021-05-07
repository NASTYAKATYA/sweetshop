package ru.mirea.work.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.work.models.Product;
import ru.mirea.work.models.Type;
import ru.mirea.work.services.ProductService;
import ru.mirea.work.services.TypeService;

import java.util.Comparator;

@Controller
@RequestMapping("/")
public class UserController {
    private final TypeService typeService;
    private final ProductService productService;
    @Autowired
    public UserController(TypeService typeService, ProductService productService) {
        this.typeService = typeService;
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(@RequestParam(name = "ItypeId", required = false) Integer ItypeId,
                           @RequestParam(name = "IproductId", required = false) Integer IproductId,
                           Model model) {
        int typeId = (ItypeId == null) ? 0: ItypeId;
        int productId = (IproductId == null) ? 0: IproductId;

        model.addAttribute("sortTypesById", Comparator.comparing(Type::getId));
        model.addAttribute("types", TypeService.getAllTypes());
        model.addAttribute("tId", typeId);
        model.addAttribute("pId", productId);

        String typeTitle = "";
        if (typeService.getTypeById(typeId) != null)
            typeTitle = TypeService.getTypeById(typeId).getName();
        model.addAttribute("petTitle", typeTitle);
        model.addAttribute("productTypes", productService.getProductByTypeId(typeId));
        model.addAttribute("sortProductTypesById", Comparator.comparing(Product::getId));
        model.addAttribute("sortTypessById", Comparator.comparing(Type::getId));
        return "UserController/types";
    }
}

