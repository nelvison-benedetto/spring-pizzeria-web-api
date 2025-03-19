package org.lessons.java.spring_pizzeriacrud.repository;

import org.lessons.java.spring_pizzeriacrud.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer>{
    
}
