package com.nit.saif.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.indus.entity.User;
import com.indus.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRespositoryTest {

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindByUsername_UserExists() {
		User user = new User();
		user.setUsername("saifuddin");
		user.setEmail("saifuddin575@gmail.com");
		when(userRepository.findByUsername("saifuddin")).thenReturn(Optional.of(user));

		Optional<User> foundUser = userRepository.findByUsername("saifuddin");

		assertTrue(foundUser.isPresent());
		assertEquals("saifuddin", foundUser.get().getUsername());
		assertEquals("saifuddin575@gmail.com", foundUser.get().getEmail());
	}

	@Test
	    public void testFindByUsername_UserDoesNotExist() {
	        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

	        Optional<User> foundUser = userRepository.findByUsername("nonExistentUser");

	        assertFalse(foundUser.isPresent());
	    }

	@Test
	    public void testExistsByUsername_UserExists() {
	        when(userRepository.existsByUsername("existingUser")).thenReturn(true);
	        Boolean exists = userRepository.existsByUsername("existingUser");
	        assertTrue(exists);
	    }

	@Test
	    public void testExistsByUsername_UserDoesNotExist() {
	        when(userRepository.existsByUsername("nonExistentUser")).thenReturn(false);
	        Boolean exists = userRepository.existsByUsername("nonExistentUser");
	        assertFalse(exists);
	    }

	@Test
	    public void testExistsByEmail_EmailExists() {
	        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
	        Boolean exists = userRepository.existsByEmail("test@example.com");
	        assertTrue(exists);
	    }
	
	@Test
    public void testExistsByEmail_EmailDoesNotExist() {
        when(userRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);
        Boolean exists = userRepository.existsByEmail("nonexistent@example.com");
        assertFalse(exists);
    }

}
