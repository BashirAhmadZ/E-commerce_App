package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

	public static final int USER_PER_PAGE = 5;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}

	public List<User> listAll() {
		return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
	}

	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, USER_PER_PAGE, userRepo);
		
//		String sortField, String sortDir, String keyword
//		Sort sort = Sort.by(helper.getSortField());//sortField
//		sort = helper.getSortDir().equals("asc") ? sort.ascending() : sort.descending(); //sortDir
//		Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE, sort);
//		Page<User> page = null;
//		
//		if (helper.getKeyword() != null) { //keyword
//			page = userRepo.findAll(helper.getKeyword(), pageable);
//		} else {
//			page = userRepo.findAll(pageable);
//		}
//		helper.updateModelAttributes(pageNum, page);
	}

	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);

		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();

			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}

		} else {
			encodePassword(user);
		}
		return userRepo.save(user);
	}

	public User updateAccount(User userInform) {
		User userInDB = (User) userRepo.findById(userInform.getId()).get();
		if (!userInform.getPassword().isEmpty()) {
			userInDB.setPassword(userInform.getPassword());
			encodePassword(userInDB);
		}
		if (userInform.getPhotos() != null) {
			userInDB.setPhotos(userInform.getPhotos());
		}

		userInDB.setFirstName(userInform.getFirstName());
		userInDB.setLastName(userInform.getLastName());

		return userRepo.save(userInDB);
	}

	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if (userByEmail == null)
			return true;
		boolean isCreatingNew = (id == null);
		if (isCreatingNew) {
			if (userByEmail != null)
				return false;
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Clould not find any user with ID: " + id);
		}
	}

	public void delete(Integer id) throws UserNotFoundException {
		Long countById = (userRepo.countById(id));
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Clould not find any user with ID: " + id);
		}
		userRepo.deleteById(id);
	}

	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}
}
