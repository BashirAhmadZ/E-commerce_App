package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.assertj.core.api.Assertions;

public class PasswordEncoderTest {

    @Test
    public void testEncoderPassword() {
        
        // Initialize PasswordEncoder (Spring Security provides BCryptPasswordEncoder)
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        // Raw password for testing
        String rawPassword = "ahmad2024";
        
        // Encoding the raw password
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Print the encoded password to verify
        System.out.println(encodedPassword);
        
        // Verifying if the raw password matches the encoded one
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        
        // Assert that the raw password and encoded password match
        Assertions.assertThat(matches).isTrue();
    }
}

//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//public class PasswordEncoderTest {
//
//	@Test
//	public void testEncoderPassword() {
//		
//		  BCryptPasswordEncoder pazzwordEncoder = new BCryptPasswordEncoder();
//		  String rawPazzword = "ahmad2024";
//		  String encodedPazzword = pazzwordEncoder.encode(rawPazzword);
//		  
//		  System.out.println(encodedPazzword);
//		  
//		  boolean matches = pazzwordEncoder.matches(rawPazzword, encodedPazzword);
//		  
//		  assertThat(matches).isTrue();
//		 
//	}
//}