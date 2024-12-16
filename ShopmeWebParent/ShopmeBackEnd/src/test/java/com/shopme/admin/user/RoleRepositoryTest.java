package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	RoleRepository repo;
	
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		Role savedRole = repo.save(roleAdmin);

		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSalesperson = new Role("Sales", "manage com.shopme.admin.product price, "
				+ "customers, shipping, orders and sales report");
		Role roleEditor = new Role("Editor", "manage categories, brands, products "
				+ "articles and minus");
		Role roleShipper = new Role("Shipper", "view com.shopme.admin.product, view orders, "
				+ "and update order status");
		Role roleAssistant = new Role("Assistant", "manage question and reviews");
		
		//repo.saveAll(Collections.unmodifiableList(new ArrayList<>(Arrays.asList(roleSalesperson, roleEditor, roleShipper, roleAssistant))));
		/*In Java 9v instead of .unmodifiableList(new ArrayList<>(Arrays.asList(...))) we can use .of() method.*/
		repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));

	}
}
