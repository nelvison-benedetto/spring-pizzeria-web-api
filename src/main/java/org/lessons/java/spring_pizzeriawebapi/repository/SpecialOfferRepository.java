package org.lessons.java.spring_pizzeriawebapi.repository;

import org.lessons.java.spring_pizzeriawebapi.models.Review;
import org.lessons.java.spring_pizzeriawebapi.models.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Integer>{
    //!You have now automatically inherited CRUD methods like save(), findAll(), etc., through Spring JPA Query Methods
    
}
