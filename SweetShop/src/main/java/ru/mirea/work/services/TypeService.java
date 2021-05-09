package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Type;
import ru.mirea.work.repositories.ITypeRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {
    private ITypeRepository itr;

    @Autowired
    public TypeService( ITypeRepository itr){
        this.itr=itr;
    }
    public List<Type> getAllTypes() {
        return itr.findAll();
    }
    public void saveType(Type type) {
        itr.save(type);
    }
    public void deleteById(int id) {
        itr.deleteById(id);
    }
}
