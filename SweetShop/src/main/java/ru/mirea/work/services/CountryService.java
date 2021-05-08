package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Country;
import ru.mirea.work.repositories.ICountryRepository;


@Service
@RequiredArgsConstructor
public class CountryService {
    private ICountryRepository icr;

    @Autowired
    public CountryService(ICountryRepository icr){
        this.icr=icr;
    }
    public Country getAllCountries(int id){
        return icr.findById(id);
    }
}
