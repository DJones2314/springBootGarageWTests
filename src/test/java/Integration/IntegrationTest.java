package Integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;
import com.qa.jones.dave.garageSpringBootGarage.SpringBootGarageApplication;
import com.qa.jones.dave.garageSpringBootGarage.exception.ResourceNotFoundException;
import com.qa.jones.dave.garageSpringBootGarage.model.DataModel;
import com.qa.jones.dave.garageSpringBootGarage.repository.VehicleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootGarageApplication.class })
@AutoConfigureMockMvc
@WebAppConfiguration
public class IntegrationTest {

	@Autowired
	private MockMvc mvc;
	private WebApplicationContext wac;

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

	// testing the Get method
	@Test
	public void getTest() throws Exception {
		DataModel testVehicle = new DataModel("nissan", "black", 2006);
		repository.save(testVehicle);
		mvc.perform(get("/api/vehicle").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].type", is("nissan")));
	}

	// testing the post method
	@Test
	public void postingTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/vehicle").contentType(MediaType.APPLICATION_JSON)
				.content("{\"type\" : \"lambo\",\"colour\" : \"orange\", \"year\" : 2017}")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.type", is("lambo")));
	}

	// testing the put method

	@Test
	public void updateTest() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		DataModel testVehicle1 = new DataModel("zonda", "shiny", 2020);
		repository.save(testVehicle1);
		DataModel changedTestVehicle = new DataModel("newish", "notShiny", 2000);
		String jsonString = mapper.writeValueAsString(changedTestVehicle);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/vehicle/" + testVehicle1.getId())
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonString);
		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.type", is("newish"))).andExpect(jsonPath("$.colour", is("notShiny")))
				.andExpect(jsonPath("$.year", is(2000))).andDo(MockMvcResultHandlers.print());
	}

	@Test
		public void deleteTest() throws Exception {
		DataModel testVehicle2 = new DataModel("honda", "green", 2001);
		repository.save(testVehicle2);

		mvc.perform(MockMvcRequestBuilders.delete("/api/vehicle/1").contentType(MediaType.APPLICATION_JSON))
				// .accept(MediaType.APPLICATION_JSON)
				.andExpect(status().isNotFound());
	}

}