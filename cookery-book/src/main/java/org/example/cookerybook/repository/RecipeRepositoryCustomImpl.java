package org.example.cookerybook.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.cookerybook.model.Recipe;

import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom<Recipe>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override

    public Recipe getRandomRecipe() {
        Long max =  entityManager.createQuery("SELECT MAX(id) FROM Recipe", Long.class).getSingleResult();
        Long min =  entityManager.createQuery("SELECT MIN(id) FROM Recipe", Long.class).getSingleResult();
        Random rndGen = new Random();
        long randomId = rndGen.nextLong(min, max);
        TypedQuery<Recipe> recipes = entityManager.createQuery("SELECT TOP(1) FROM Recipe WHERE id < :id", Recipe.class);
        recipes.setParameter("id", randomId + 1);
        return recipes.getSingleResult();
    }
}
