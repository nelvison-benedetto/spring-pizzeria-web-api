package org.lessons.java.spring_pizzeriacrudrelationships.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.lessons.java.spring_pizzeriacrudrelationships.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer>{
    //!You have now automatically inherited CRUD methods like save(), findAll(), etc., through Spring JPA Query Methods

    //combinations w no Restrictions
    public List<Pizza> findByTitleContaining(String title);  // run this when field Content isn't utilized!
    public List<Pizza> findByContentContaining(String content);
    // public List<Pizza> findByTitleContainingOrContentContaining(String title, String content);  //not so good, focus not on pizzas TitleX, among these only those that contain ContentX!, but all pizzas with TitleX and also those with ContentX
    public List<Pizza> findByTitleContainingAndContentContaining(String title, String content);

    //combinations Restrictions+...
    public List<Pizza> findByContentContainingAndRestrictionsIn(String content, Set<String> restrictions);
    public List<Pizza> findByTitleContainingAndRestrictionsIn(String title, Set<String> restrictions);
    public List<Pizza> findByTitleContainingAndContentContainingAndRestrictionsIn(String title, String content, Set<String> restrictions);
    
    public List<Pizza> findByPriceBetween(BigDecimal min, BigDecimal max);
    
}
