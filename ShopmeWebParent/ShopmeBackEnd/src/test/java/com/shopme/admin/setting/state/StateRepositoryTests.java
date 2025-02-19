package com.shopme.admin.setting.state;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {

	@Autowired
	private StateRepository repo;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateStatesInIndia() {
		Integer countrytId = 4;
		Country country = entityManager.find(Country.class, countrytId);

//		State state = repo.save(new State("Karnataka", country));
//		State state = repo.save(new State("Punjab", country));
//		State state = repo.save(new State("Uttar Pradesh", country));
//		State state = repo.save(new State("Andra Pradesh", country));
//		State state = repo.save(new State("Telengana", country));
//		State state = repo.save(new State("Delhi", country));
//		State state = repo.save(new State("Haryana", country));
		State state = repo.save(new State("West Bangal", country));

		assertThat(state).isNotNull();
		assertThat(state.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateStatesInUS() {
		Integer countrytId = 3;
		Country country = entityManager.find(Country.class, countrytId);

//		State state = repo.save(new State("California", country));
//		State state = repo.save(new State("Texas", country));
//		State state = repo.save(new State("New York", country));
		State state = repo.save(new State("Washington", country));

		assertThat(state).isNotNull();
		assertThat(state.getId()).isGreaterThan(0);
	}

	@Test
	public void listStatesByCountry() {
		Integer countrytId = 4;
		Country country = entityManager.find(Country.class, countrytId);
		List<State> listStates = repo.findByCountryOrderByNameAsc(country);

		listStates.forEach(System.out::println);

		assertThat(listStates.size()).isGreaterThan(0);
	}

	@Test
	public void testUpdateState() {
		Integer stateId = 3;
		String stateName = "Tamil Nadu";
		State state = repo.findById(stateId).get();

		state.setName(stateName);
		State updatedState = repo.save(state);

		assertThat(updatedState.getName()).isEqualTo(stateName);
	}

	@Test
	public void testGatState() {
		Integer stateId = 3;
		Optional<State> findById = repo.findById(stateId);
		assertThat(findById.isPresent());
	}

	@Test
	public void testDeleteState() {
		Integer stateId = 20;
		repo.deleteById(stateId);

		Optional<State> findById = repo.findById(stateId);
		assertThat(findById.isEmpty());
	}
}
