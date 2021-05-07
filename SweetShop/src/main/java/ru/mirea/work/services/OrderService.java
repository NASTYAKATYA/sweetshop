package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Order;
import ru.mirea.work.repositories.IOrderRepository;


@Service
@RequiredArgsConstructor
public class OrderService {
    private IOrderRepository ior;

    @Autowired
    public OrderService(IOrderRepository ior){
        this.ior=ior;
    }
    public Order show(int id){
        return ior.findById(id);
    }
}
