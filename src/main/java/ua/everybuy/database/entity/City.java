package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "city")
@Data
public class City {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "city_name")
    private String cityName;


}
