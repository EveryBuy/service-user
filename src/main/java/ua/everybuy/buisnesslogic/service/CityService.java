package ua.everybuy.buisnesslogic.service;

import ua.everybuy.database.entity.City;
import ua.everybuy.database.repository.CityRepository;
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
