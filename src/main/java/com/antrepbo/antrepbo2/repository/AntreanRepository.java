package com.antrepbo.antrepbo2.repository;

import com.antrepbo.antrepbo2.model.Antrean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntreanRepository extends JpaRepository<Antrean, Integer> {
    
}