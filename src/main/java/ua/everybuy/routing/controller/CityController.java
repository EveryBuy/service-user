package ua.everybuy.routing.controller;

import ua.everybuy.buisnesslogic.service.CityService;
import ua.everybuy.routing.model.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping("/get-all")
    public List<CityDTO> getAllCountries(){
        return cityService.getAllCities().stream()
                .map(city -> new CityDTO(city.getCityName()))
                .toList();
    }

//    @ExceptionHandler(HttpClientErrorException.class)
//    public ResponseEntity<AccessDeniedAnswer> handleAccessDeniedException(HttpClientErrorException ex) {
//        return ResponseEntity
//                .status(401) // Получаем статус код из вашего исключения
//                .body(new AccessDeniedAnswer(HttpStatusCode.valueOf(401)));
//    }
}
