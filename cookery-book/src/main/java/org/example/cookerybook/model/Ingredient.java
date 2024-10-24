package org.example.cookerybook.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, targetEntity = RecipeIngredient.class)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();
}
