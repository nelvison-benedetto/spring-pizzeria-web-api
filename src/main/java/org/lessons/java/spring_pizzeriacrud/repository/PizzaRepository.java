package org.lessons.java.spring_pizzeriacrud.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.lessons.java.spring_pizzeriacrud.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer>{

    public List<Pizza> findByTitleContainingAndContentContainingAndRestrictionsIn(String title, String content, Set<String> restrictions);
    public List<Pizza> findByTitleContainingOrContentContaining(String title, String content);  //using OR to find not only 'Diavola' with content 'fontina' but all!
    
    public List<Pizza> findByPriceBetween(BigDecimal min, BigDecimal max);
    
}
