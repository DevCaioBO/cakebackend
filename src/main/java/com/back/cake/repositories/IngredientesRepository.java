package com.back.cake.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.back.cake.models.IngredientesModel;

public interface IngredientesRepository extends JpaRepository<IngredientesModel,Integer> {

}