package com.everybuy.database.repository;

import com.everybuy.database.entity.Address;
import com.everybuy.database.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressesByCity(City city);
    List<Address> findAddressesByStreetName(String streetName);
}
