package Integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.validation.Valid;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;
import com.qa.jones.dave.garageSpringBootGarage.SpringBootGarageApplication;
import com.qa.jones.dave.garageSpringBootGarage.exception.ResourceNotFoundException;
import com.qa.jones.dave.garageSpringBootGarage.model.DataModel;
import com.qa.jones.dave.garageSpringBootGarage.repository.VehicleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootGarageApplication.class })
@AutoConfigureMockMvc
public class IntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private VehicleRepository repository;

	@Before
	public void clearDB() {
		repository.deleteAll();
	}

	@Test
	public void findingAndRetrievingVehicleFromDatabase() throws Exception {
		repository.save(new DataModel("ford", "red", 1990));
		mvc.perform(get("/api/vehicle").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].type", is("ford"))); 

	}

	
/*	// Method to update a vehicle
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

		}*/
	@Test
	public void createTest() throws Exception {
		DataModel testVehicle = new DataModel("nissan", "black", 2006);
		repository.save(testVehicle);
		
		mvc.perform(get("/api/vehicle")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].type", is("nissan"))); 
	}
	
	@Test
	public void updateTest() throws Exception {
		DataModel testVehicle = new DataModel("nissan", "black", 2006);
		repository.save(testVehicle);
		
		mvc.perform(put("/api/vehicle/" + testVehicle.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].type", is("ford"))); 
	}
	/*
	@Test
	public void deleteTest() throws Exception {
		DataModel testVehicle2 = new DataModel("honda", "green", 2001);
		repository.save(testVehicle2);
		
		mvc.perform(delete("/api/vehicle/1")
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].type", is("ford")));
	}
	*/
}
