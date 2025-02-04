package com.cts.ppstores.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.ppstores.controllers.mappers.EntityDtoMapper;
import com.cts.ppstores.dtos.LoginRequest;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.dtos.RoleDto;
import com.cts.ppstores.dtos.UserDto;
import com.cts.ppstores.enums.UserRole;
import com.cts.ppstores.exceptions.EmailAlreadyUsedException;
import com.cts.ppstores.exceptions.InvalidCredentialsException;
import com.cts.ppstores.exceptions.NotFoundException;
import com.cts.ppstores.models.User;
import com.cts.ppstores.repositories.UserRepository;
import com.cts.ppstores.security.JwtUtils;
import com.cts.ppstores.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public Response registerUser(UserDto registrationRequest) {
    	
    	log.info("Registering user: {}", registrationRequest);
    	
    	Optional<User> checkUser = userRepo.findByEmail(registrationRequest.getEmail());

    	if (checkUser.isPresent()) {
    		log.warn("Email already taken: {}", registrationRequest.getEmail());
    	    throw new EmailAlreadyUsedException("Email Id Already Taken");
    	}
    	
        UserRole role = UserRole.ROLE_USER;
        
        Optional<User> checkAdmin = userRepo.findByRole(UserRole.ROLE_ADMIN);
        
        if (!checkAdmin.isPresent()) {
            role = UserRole.ROLE_ADMIN;
        }

        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .build();

        User savedUser = userRepo.save(user);

        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);
        
        log.info("User registered successfully: {}", userDto);
        
        return Response.builder()
                .status(200)
                .message("User registration completed successfully.")
                .user(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {

    	log.info("Logging in user: {}", loginRequest.getEmail());
    	
//        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new NotFoundException("Email not found"));
//        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
//        	log.warn("Invalid credentials for user: {}", loginRequest.getEmail());
//            throw new InvalidCredentialsException("Password does not match");
//        }
    	
    	Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    	
//    	if(!authentication.isAuthenticated()) {
//        	log.warn("Invalid credentials for user: {}", loginRequest.getEmail());
//        	throw new InvalidCredentialsException("Invalid Credentials");
//    	}
    	
    	User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new InvalidCredentialsException("Invalid Credentials"));
    	
        String token = jwtUtils.generateToken(user);
        
        log.info("User logged in successfully: {}", user);

        return Response.builder()
                .status(200)
                .message("Welcome! You are now logged in.")
                .token(token)
                .expirationTime("3 Hours")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Response getAllUsers() {
    	
    	log.info("Fetching all users");
    	
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = entityDtoMapper.mapUserToDtoBasic(user);
            userDtos.add(userDto);
        }

        log.info("Fetched all users: {}", userDtos);
        
        return Response.builder()
                .status(200)
                .userList(userDtos)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String  email = authentication.getName();
        
        log.info("Fetching logged-in user by email: {}", email);
        
        return userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not found"));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
    	
    	log.info("Fetching user info and order history");
    	
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);
        
        log.info("Fetched user info and order history: {}", userDto);

        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }

	@Override
	public Response alterRole(Long id, RoleDto roleDto) {
		
		log.info("Altering role for user with id: {}", id);
		
		User user = userRepo.findById(id).orElseThrow(()-> new NotFoundException("User Not found with the id: " + id));
		
		UserRole role = UserRole.ROLE_USER;
		
		if(roleDto.getRole().equals("1") || roleDto.getRole().equals("ROLE_ADMIN")) {
			role = UserRole.ROLE_ADMIN;
		}
		
		user.setRole(role);
		userRepo.save(user);
		log.info("Role updated to {} for user with id: {}", role, id);
		
		return Response.builder()
				.status(200)
				.message("Role has been updated.")
				.build();
	}

	@Override
	public Response loadUserById(Long id) {
		
		log.info("Loading user by id: {}", id);
		
		User user = userRepo.findById(id).orElseThrow(()-> new NotFoundException("User Not found with the id: " + id));
		
		UserDto userDto = entityDtoMapper.mapUserToDtoBasic(user);
		log.info("User feteched successfully: {}", userDto);
		
		return Response.builder()
				.status(200)
				.user(userDto)
				.message("User fetched successfully")
				.build();
	}

	@Override
	public Response eraseUser(Long id) {
		
		log.info("Erasing user with id: {}", id);
		
		User user = userRepo.findById(id).orElseThrow(()-> new NotFoundException("User Not found with the id: " + id));
		
		userRepo.delete(user);
		log.info("User with id: {} has been removed", id);
		
		return Response.builder()
				.status(200)
				.message("User " + id + " has been removed.")
				.build();
	}
}