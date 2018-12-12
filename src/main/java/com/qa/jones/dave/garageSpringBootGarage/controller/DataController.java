package com.qa.jones.dave.garageSpringBootGarage.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.jones.dave.garageSpringBootGarage.repository.VehicleRepository;
import com.qa.jones.dave.garageSpringBootGarage.exception.ResourceNotFoundException;
import com.qa.jones.dave.garageSpringBootGarage.model.DataModel;

@RestController
@RequestMapping("/api")

public class DataController {

	@Autowired
	VehicleRepository myRepository;

	// method to create a vehicle

	@PostMapping("/vehicle")
	public DataModel createVehicle(@Valid @RequestBody DataModel mSDM) {
		return myRepository.save(mSDM); 
	}

	// Method to get a vehicle
	@GetMapping("vehicle/{id}")
	public DataModel getVehicleByID(@PathVariable(value = "id") Long vehicleID) {
		return myRepository.findById(vehicleID)
				.orElseThrow(() -> new ResourceNotFoundException("DataModel", "id", vehicleID));
	}
	
	@GetMapping("vehicle/model/{type}")
	public List<DataModel> findByVehicleType(@PathVariable(value = "type") String type) {
		return myRepository.findByType(type);
				//.orElseThrow(() -> new ResourceNotFoundException("DataModel", "type", vehicleType));
	}

	// Method to get all vehicles
	@GetMapping("/vehicle")
	public List<DataModel> getAllVehicles() {
		return myRepository.findAll();

	}

	// Method to update a vehicle
	@PutMapping("/vehicle/{id}")
	public DataModel updateVehicle(@PathVariable(value = "id") Long vehicleID,
			@Valid @RequestBody DataModel vehicleDetails) {
		DataModel mSDM = myRepository.findById(vehicleID)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", vehicleID));

		mSDM.setType(vehicleDetails.getType());
		mSDM.setColour(vehicleDetails.getColour());
		mSDM.setYear(vehicleDetails.getYear());

		DataModel updateData = myRepository.save(mSDM);
		return updateData;

	}

	// Method to remove a vehicle
	@DeleteMapping("/vehicle/{id}")
	public ResponseEntity<?> deleteVehicle(@PathVariable(value = "id") Long vehicleID) {
		DataModel mSDM = myRepository.findById(vehicleID)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", vehicleID));

		myRepository.delete(mSDM);
		return ResponseEntity.ok().build();

	}

}
