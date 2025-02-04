package com.cts.ppstores.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ppstores.dtos.LoginRequest;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.dtos.RoleDto;
import com.cts.ppstores.dtos.UserDto;
import com.cts.ppstores.services.interfaces.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid UserDto registrationRequest){
        Response response = userService.registerUser(registrationRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody @Valid LoginRequest loginRequest){
    	Response response = userService.loginUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/my-info")
    public ResponseEntity<Response> getUserInfoAndOrderHistory(){
        return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());
    }
    
    @PutMapping("/alterRole/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> alterRole(@PathVariable Long id,@RequestBody RoleDto roleDto){
    	return ResponseEntity.ok(userService.alterRole(id, roleDto));
    }
    
    @GetMapping("/loadUserById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> loadUserById(@PathVariable Long id){
    	return ResponseEntity.ok(userService.loadUserById(id));
    }
    
    @DeleteMapping("/eraseUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> eraseUser(@PathVariable Long id){
    	return ResponseEntity.ok(userService.eraseUser(id));
    }
}