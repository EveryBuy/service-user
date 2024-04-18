package com.everybuy.buisnesslogic.service;

import com.everybuy.database.entity.City;
import com.everybuy.database.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }
}
