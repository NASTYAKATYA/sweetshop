package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Product;
import ru.mirea.work.repositories.IProductRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private IProductRepository ipr;

    @Autowired
    public ProductService(IProductRepository ipr){
        this.ipr=ipr;
    }
    public Product getProductById(int id){
        return ipr.findById(id);
    }
    public List<Product> getProductByTypeId(int typeId) {
        return ipr.findAllByTypeId(typeId);
    }
    public List<Product> getAllProduct() {
        return ipr.findAll();
    }
}
