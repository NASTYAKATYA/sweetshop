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
    public List<Product> getAllProductsByTypesId(int typesId) {
        return ipr.findAllByTypesId(typesId);
    }
    public List<Product> getAllProductsByTypesIdAndCountriesId(int typesId, int countriesId) {
        return ipr.findAllByTypesIdAndCountriesId(typesId, countriesId);
    }
    public Product getProduct(int id){
        return ipr.findById(id);
    }

}
