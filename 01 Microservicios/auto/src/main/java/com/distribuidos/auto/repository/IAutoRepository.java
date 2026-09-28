package com.distribuidos.auto.repository;

import com.distribuidos.auto.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAutoRepository extends JpaRepository<Auto,Long> {
}
