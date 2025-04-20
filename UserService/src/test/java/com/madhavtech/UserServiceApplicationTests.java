package com.madhavtech;

import com.madhavtech.entities.Rating;
import com.madhavtech.external.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceApplicationTests {

	@MockBean
	private RatingService ratingService;

	@Test
	void contextLoads() {
		// Just checks if Spring context loads successfully
	}

	@Test
	public void createRating() {
		// Arrange: Create a sample rating
		Rating rating = Rating.builder()
				.rating(10)
				.userId("user123")
				.hotelId("hotel456")
				.feedback("====This is created using Feign client : ")
				.build();

		// Mock the response from ratingService
		when(ratingService.createRating(rating)).thenReturn(
				Rating.builder()
						.ratingId("rating789")
						.rating(10)
						.userId("user123")
						.hotelId("hotel456")
						.feedback("====This is created using Feign client : ")
						.build()
		);

		// Act: Call the service
		Rating savedRating = ratingService.createRating(rating);

		// Assert / Print
		System.out.println("New Rating added +++====================== : " + savedRating.getRatingId());
	}
}
