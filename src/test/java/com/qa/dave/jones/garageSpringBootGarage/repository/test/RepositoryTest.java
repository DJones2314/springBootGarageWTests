package com.qa.dave.jones.garageSpringBootGarage.repository.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.jones.dave.garageSpringBootGarage.SpringBootGarageApplication;
import com.qa.jones.dave.garageSpringBootGarage.model.DataModel;
import com.qa.jones.dave.garageSpringBootGarage.repository.VehicleRepository;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {SpringBootGarageApplication.class})
@ContextConfiguration(classes=SpringBootGarageApplication.class)
@DataJpaTest
public class RepositoryTest {  

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private VehicleRepository MyRepo;
	
	
//	@Test
//	public void retrieveByIdTest() {
//		DataModel model1 = new DataModel("ford", "red", 1990);
//		entityManager.persist(model1);
//		entityManager.flush();
//		assertTrue(MyRepo.findById(model1.getId()).isPresent()); 
//	} 
	
	@Test
	public void retrieveByTypeTest() {
		DataModel model2 = new DataModel("ford", "red", 1990);
		entityManager.persist(model2);
		entityManager.flush();
		assertFalse(MyRepo.findByType("ford").isEmpty());  
	} 
	
}
