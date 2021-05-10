package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Purchase;
import ru.mirea.work.repositories.IPurchaseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private IPurchaseRepository ipr;

    @Autowired
    public PurchaseService(IPurchaseRepository ipr) {
        this.ipr = ipr;
    }
    public Purchase getPurchaseByUserIdAndProductId(int userId, int productId) {
        return ipr.findByUserIdAndProductId(userId, productId);
    }
    public void savePurchase(Purchase purchase) {
        ipr.save(purchase);
    }
    public List<Purchase> getPurchasesByUserId(int userId) {
        return ipr.findAllByUserId(userId);
    }
    public Purchase getPurchaseById (int id) {
        return ipr.findById(id);
    }
    public void deletePurchaseById(int id) {
        ipr.deleteById(id);
    }
    public void deleteAllByUserId(int userId) {
        ipr.deleteAllByUserId(userId);
    }
}
