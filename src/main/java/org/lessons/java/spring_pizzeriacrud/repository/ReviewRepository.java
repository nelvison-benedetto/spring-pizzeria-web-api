package org.lessons.java.spring_pizzeriacrud.repository;

import org.lessons.java.spring_pizzeriacrud.models.Pizza;
import org.lessons.java.spring_pizzeriacrud.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
    //!You have now automatically inherited CRUD methods like save(), findAll(), etc., through Spring JPA Query Methods

}
