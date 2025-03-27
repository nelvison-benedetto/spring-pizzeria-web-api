package org.lessons.java.spring_pizzeriacrudrelationships.repository;

import org.lessons.java.spring_pizzeriacrudrelationships.models.Pizza;
import org.lessons.java.spring_pizzeriacrudrelationships.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
    //!You have now automatically inherited CRUD methods like save(), findAll(), etc., through Spring JPA Query Methods

}
