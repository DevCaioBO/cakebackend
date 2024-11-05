package com.back.cake.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.back.cake.models.CakeModel;

public interface CakeRepository extends JpaRepository<CakeModel,Integer> {

}
