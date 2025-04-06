package com.madhavtech.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hotel-dtls")
public class Hotel {
    @Id
    private String hotelId;
    private String name;
    private String location;
    private String about;
}
