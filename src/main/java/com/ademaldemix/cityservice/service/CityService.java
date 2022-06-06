package com.ademaldemix.cityservice.service;

import com.ademaldemix.cityservice.exception.CityAlreadyExistException;
import com.ademaldemix.cityservice.exception.CityNotFoundException;
import com.ademaldemix.cityservice.model.City;
import com.ademaldemix.cityservice.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    public List<City> getCities(String name) {

        if (name == null){
            return cityRepository.findAll();
        } else {
            return cityRepository.findAllByName(name);
        }

    }

    public City createCity(City newCity) {
        Optional<City> city = cityRepository.findByName(newCity.getName());
        if (city.isPresent()){
            throw new CityAlreadyExistException("City already exists with name " + newCity.getName());
        }
        return  cityRepository.save(newCity);
    }

    public City getCityById(String id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City is not found with id :  " + id));
    }

    public void updateCity(String id, City newCity) {
        City oldCity = getCityById(id);
        oldCity.setName(newCity.getName());
        cityRepository.save(oldCity);
    }

    public void deleteCity(String id) {
        cityRepository.deleteById(id);
    }
}
