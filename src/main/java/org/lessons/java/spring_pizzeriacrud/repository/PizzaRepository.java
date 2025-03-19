package org.lessons.java.spring_pizzeriacrud.repository;

import java.math.BigDecimal;
import java.util.List;

import org.lessons.java.spring_pizzeriacrud.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer>{

    public List<Pizza> findByNameContaining(String name); 
    public List<Pizza> findByPriceBetween(BigDecimal min, BigDecimal max);
    
}
