package com.madhavtech.service;

import com.madhavtech.entities.Hotel;
import java.util.*;

public interface HotelService {
    //create
    Hotel startHotel(Hotel hotel);

    //getallHotels
    List<Hotel> getAllHotels();

    //getSingleHotel
    Hotel getSingleHotel(String hotelId);

    //deleteHotel
    void deleteHotel(String hotelId);

    //updateHotel
}
