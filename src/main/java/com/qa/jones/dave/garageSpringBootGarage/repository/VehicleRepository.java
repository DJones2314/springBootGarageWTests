package com.qa.jones.dave.garageSpringBootGarage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.jones.dave.garageSpringBootGarage.model.DataModel;

@Repository

public interface VehicleRepository extends JpaRepository<DataModel, Long> {

}
