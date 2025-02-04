package com.cts.ppstores.services.interfaces;

import com.cts.ppstores.dtos.LoginRequest;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.dtos.RoleDto;
import com.cts.ppstores.dtos.UserDto;
import com.cts.ppstores.models.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
	Response alterRole(Long id, RoleDto roleDto);
	Response loadUserById(Long id);
	Response eraseUser(Long id);
}