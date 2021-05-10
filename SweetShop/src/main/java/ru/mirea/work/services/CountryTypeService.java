package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.CountryType;
import ru.mirea.work.repositories.ICountryTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryTypeService {
    private ICountryTypeRepository ictr;

    @Autowired
    public CountryTypeService(ICountryTypeRepository ictr) {
        this.ictr = ictr;
    }
    public List<CountryType> getCountriesByTypeId(int typeId) {
        return ictr.findAllByTypesId(typeId);
    }
    public List<CountryType> getAll() {
        return ictr.findAll();
    }
    public void saveCountryType(CountryType countryType){ ictr.save(countryType);}
    public void deleteById(int id){ ictr.deleteById(id);}
}
