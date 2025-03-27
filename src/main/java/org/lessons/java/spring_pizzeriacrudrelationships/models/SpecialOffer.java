package org.lessons.java.spring_pizzeriacrudrelationships.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "special_offer")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class SpecialOffer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "title cannot be blank.")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "startDate cannot be null.")
    private LocalDate startDate;

    @Column(nullable = false)
    @NotNull(message = "endDate cannot be null.")
    private LocalDate endDate;
    //@PastOrPresent (u cannot add future dates)

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    @JsonBackReference  //used on the child side
    private Pizza pizza;

    @Override
    public String toString(){
        return String.format( "%s %s %s %s", id, title, startDate, endDate);
    }
}
