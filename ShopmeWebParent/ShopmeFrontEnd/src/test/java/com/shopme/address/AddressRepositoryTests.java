package com.shopme.address;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AddressRepositoryTests {
	
	@Autowired private AddressRepository repo;
	
	@Test
	public void testAddNew() {
		Integer customerId = 1;
		Integer countryId = 234;
		
		Address newAddress = new Address();
		newAddress.setCustomer(new Customer(customerId));
		newAddress.setCountry(new Country(countryId));
		newAddress.setFirstName("Charles");
		newAddress.setLastName("Brugger");
		newAddress.setPhoneNumber("646-232-3902");
		newAddress.setAddressLine1("204 Morningview Lane");
		newAddress.setCity("New York");
		newAddress.setState("New York");
		newAddress.setPostalCode("10013");
		
		Address savedAddress = repo.save(newAddress);
		
		assertThat(savedAddress).isNotNull();
		assertThat(savedAddress.getId()).isGreaterThan(0);	
	}
	
	@Test
	public void testfindByCustomer() {
		Integer customerId = 5;
		List<Address> listAddresses = repo.findByCustomer(new Customer(customerId));
		assertThat(listAddresses.size()).isGreaterThan(0);
		
		listAddresses.forEach(System.out::println);
	}
	
	@Test
	public void testfindByIdAndCustomer() {
		Integer addressId = 1;
		Integer customerId = 5;
		
		Address address = repo.findByIdAndCustomer(addressId, customerId);
		
		assertThat(address).isNotNull();
		System.out.println(address);
	}
	
	@Test
	public void testupdate() {
		Integer addressId = 1;
		String phoneNumber = "646-232-3932";
		
		Address address = repo.findById(addressId).get();
		address.setPhoneNumber(phoneNumber);
		
		Address updatedAddress = repo.save(address);
		
		assertThat(updatedAddress.getPhoneNumber()).isEqualTo(phoneNumber);
	}
	
	@Test
	public void testdeleteByIdAndCustomer() {
		Integer addressId = 1;
		Integer customerId = 5;
		
		repo.deleteByIdAndCustomer(addressId, customerId);
		
		Address address = repo.findByIdAndCustomer(addressId, customerId);
		assertThat(address).isNull();
	}
	
	@Test
	public void testSetDefault() {
		Integer addressId = 3;
		repo.setDefaultAddress(addressId);
		
		Address address = repo.findById(addressId).get();
		assertThat(address.isDefaultForShipping()).isTrue();
	}
	
	@Test
	public void testSetNonDefaultAddresses() {
		Integer addressId = 3;
		Integer customerId = 1;
		repo.setNonDefaultForOthers(addressId, customerId);
	}
}
