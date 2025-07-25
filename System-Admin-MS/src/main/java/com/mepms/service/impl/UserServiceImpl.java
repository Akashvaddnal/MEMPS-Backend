package com.mepms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mepms.entity.UserEO;
import com.mepms.repository.UserRepository;
import com.mepms.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Override
	public UserEO createUser(UserEO user) {
		UserEO existingUser=userRepository.findByEmail(user.getEmail()).orElse(null);
	    if (existingUser != null) {
	    	if(existingUser.getEmail().equals(user.getEmail())) {
	    		throw new RuntimeException("Email already exists");
	    	}
	        
	    }
	    // Generate next sequence value for employeeId
	    long nextEmpNumber = sequenceGeneratorService.getNextSequence("employeeId");
	    String employeeId = String.format("EMP%05d", nextEmpNumber);
	    user.setEmployeeId(employeeId);
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    java.util.Date now = new java.util.Date();
	    user.setCreatedAt(now);
	    user.setUpdatedAt(now);
	    return userRepository.save(user);
	}


    @Override
    public List<UserEO> getAllUsers() {
        List<UserEO> users = userRepository.findAll();
//        users.forEach(u -> u.setPassword(null)); // Hide password on get
        return users;
    }

    @Override
    public Optional<UserEO> getUserById(String id) {
        Optional<UserEO> userOpt = userRepository.findById(id);
        userOpt.ifPresent(u -> u.setPassword(null)); // Hide password on get
        return userOpt;
    }
    
    @Override
    public Optional<UserEO> getUserByEmail(String email) {
    	Optional<UserEO> userOpt = userRepository.findByEmail(email);
    	userOpt.ifPresent(u -> u.setPassword(null)); // Hide password on get
    	return userOpt;
    }

    @Override
    public UserEO updateUser(String id, UserEO newUserData) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(newUserData.getUsername());
            if(!(newUserData.getPassword().equals(existingUser.getPassword()))) {
            	existingUser.setPassword(passwordEncoder.encode(newUserData.getPassword()));
			}
            existingUser.setEmail(newUserData.getEmail());
            existingUser.setDepartment(newUserData.getDepartment());
            existingUser.setRoleName(newUserData.getRoleName());
            existingUser.setUpdatedAt(new java.util.Date());
            existingUser.setProfilePic(newUserData.getProfilePic());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}