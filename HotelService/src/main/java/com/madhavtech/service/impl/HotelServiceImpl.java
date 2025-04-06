package com.madhavtech.service.impl;

import com.madhavtech.*;
import com.madhavtech.entities.Hotel;
import com.madhavtech.exceptions.ResourceNotFoundException;
import com.madhavtech.repository.HotelRepository;
import com.madhavtech.service.HotelService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel startHotel(Hotel hotel) {
        String hotelRandomId = UUID.randomUUID().toString();
        hotel.setHotelId(hotelRandomId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getSingleHotel(String hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found..."+hotelId));
    }

    @Override
    public void deleteHotel(String hotelId) {

    }
}
