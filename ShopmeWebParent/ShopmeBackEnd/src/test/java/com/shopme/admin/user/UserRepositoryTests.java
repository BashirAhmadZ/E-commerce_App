package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userNamBA = new User("bashir@gmail.com", "ahmad2024", "Bashir", "Ziaee");
		userNamBA.addRole(roleAdmin);
		
		User savedUser = repo.save(userNamBA);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userAhmd = new User("suliman@gmail.com", "ahmd2024", "Suliman", "Saleh");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userAhmd.addRole(roleEditor);
		userAhmd.addRole(roleAssistant);
		
		User savedUser = repo.save(userAhmd);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUser = repo.findAll();
		listUser.forEach(user -> System.out.println(user));
		
	}
	
	@Test
	public void testGetUserById() {
		User userBA = repo.findById(1).get();
		System.out.println(userBA);
		assertThat(userBA).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userBA = (User) repo.findById(1).get();
		userBA.setEnabled(true);
		//userBA.setEmail("bashirahmad@gmail.com");
		
		repo.save(userBA);
	}
	
	@Test
	public void testUpdateUserRole() {
		User userSu = (User) repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesPerson = new Role(2);
		
		userSu.getRoles().remove(roleEditor);
		userSu.addRole(roleSalesPerson);
		
		repo.save(userSu);
	}
	
//	@Test
//	public void testDeleteUser() {
//		Integer userId = 2;
//		repo.deleteById(userId);
//	}
//	
	
	@Test
	public void testGetUserByEmail() {
		String email = "ahmad555@gmail.com";
		User user = repo.getUserByEmail(email);

		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 2;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 2;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 2;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testSearchUser() {
		String keyword = "bruce";
		
		int pageNumber = 0;
		int pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
}
