package org.lessons.java.spring_pizzeriacrudrelationships.repository;

import org.lessons.java.spring_pizzeriacrudrelationships.models.Review;
import org.lessons.java.spring_pizzeriacrudrelationships.models.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Integer>{
    //!You have now automatically inherited CRUD methods like save(), findAll(), etc., through Spring JPA Query Methods
    
}
