package com.everybuy.buisnesslogic.service;

import com.everybuy.database.entity.Address;
import com.everybuy.database.entity.City;
import com.everybuy.database.repository.AddressRepository;
import com.everybuy.routing.model.dto.AddressDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address getAddressById(long id){
        return addressRepository.findById(id).orElseThrow();
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    public List<Address> getAllAddressesByCity(City city){
        return city.getAddresses();
    }

    public List<Address> getAllAddressesStreetName(String streetName){
        return addressRepository.findAddressesByStreetName(streetName);
    }



    public AddressDTO getAddressDTO(Address address){
        return new AddressDTO(
                address.getStreetName(),
                address.getHouseNumber(),
                address.getCity().getCityName()
        );
    }



    public String getFullAddress(AddressDTO addressDTO){
        return addressDTO.getFullAddress();
    }
}
