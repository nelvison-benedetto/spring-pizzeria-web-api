package org.lessons.java.spring_pizzeriacrud.seeders;

import java.util.ArrayList;
import java.util.List;

import org.lessons.java.spring_pizzeriacrud.models.Review;
import org.lessons.java.spring_pizzeriacrud.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

@Component  //!recognize this as Spring Bean
public class ReviewSeeder {
    
    private final ReviewRepository reviewRepository;
    private final Faker faker = new Faker();

    @Autowired  //!Inject an instance of ReviewRepository into this ReviewSeeder instance. From Spring v4.3+, explicit @... annotation is not necessary if there is only ONE constructor.
    public ReviewSeeder(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public void seed(int num){
        List<Review> reviews = new ArrayList<>();
        for(int i=0; i<num; i++){
            reviews.add( new Review(
                null,
                faker.name().fullName(),
                faker.lorem().paragraph()
            ));
        }
        reviewRepository.saveAll(reviews);
    }
}
