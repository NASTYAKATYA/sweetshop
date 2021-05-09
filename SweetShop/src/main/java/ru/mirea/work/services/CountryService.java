package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Country;
import ru.mirea.work.models.CountryType;
import ru.mirea.work.repositories.ICountryRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CountryService {
    private ICountryRepository icr;

    @Autowired
    public CountryService(ICountryRepository icr){
        this.icr=icr;
    }
    public List<Country> getAllCountries() {
        return icr.findAll();
    }
    public Country getCountryById(int id) {
        return icr.findById(id);
    }
    public  void saveCountry(Country country){icr.save(country);}
    public void deleteById(int id){icr.deleteById(id);}
}
