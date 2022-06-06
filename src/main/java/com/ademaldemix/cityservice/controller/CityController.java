package com.ademaldemix.cityservice.controller;

import com.ademaldemix.cityservice.exception.CityAlreadyExistException;
import com.ademaldemix.cityservice.exception.CityNotFoundException;
import com.ademaldemix.cityservice.model.City;
import com.ademaldemix.cityservice.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getCities(@RequestParam(required = false) String name) {

        return new ResponseEntity<>(cityService.getCities(name), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable String id) {

        City result = getCityById(id);

        return new ResponseEntity<>(result, OK);
    }


    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City newCity) {
        return new ResponseEntity<>(cityService.createCity(newCity), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putCity(@PathVariable String id, @RequestBody City newCity) {
        cityService.updateCity(id, newCity);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable String id) {
        cityService.deleteCity(id);
        return new ResponseEntity<>(OK);
    }

    private City getCityById(String id) {
        return cityService.getCityById(id);
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNotFoundExc(CityNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityAlreadyExistException.class)
    public ResponseEntity<String> handleCityAlreadyExistExc(CityAlreadyExistException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
