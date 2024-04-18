package com.everybuy.routing.model.dto;

import com.everybuy.database.entity.Address;

public record AddressDTO(String streetName, int houseNumber, String city) {
    public String getFullAddress(){
        return "м. " + city + ", вул. " + streetName + ", буд. " + houseNumber;
    }

    public static AddressDTO getAddressDTO(Address address){
        return new AddressDTO(address.getStreetName(),
                address.getHouseNumber(),
                address.getCity().getCityName());
    }
}
