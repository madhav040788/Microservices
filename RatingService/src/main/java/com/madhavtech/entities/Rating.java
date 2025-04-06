package com.madhavtech.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Principal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("rating-dtls")
public class Rating {
    @Id
    private String ratingId;
    private String userId;
    private String hotelId;
    private String feedback;
    private int rating;
}
