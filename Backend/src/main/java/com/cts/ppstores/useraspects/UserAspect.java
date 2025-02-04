package com.cts.ppstores.useraspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.cts.ppstores.dtos.RoleDto;

@Aspect
@Component
public class UserAspect {
	
    @Before("execution(* com.pani.ecommerce.services.implementations.UserServiceImpl.alterRole(..)) && args(id, roleDto)")
    public void validateRole(Long id, RoleDto roleDto) {
        String role = roleDto.getRole();
        if (!role.equals("0") && !role.equals("1") && !role.equals("ROLE_USER") && !role.equals("ROLE_ADMIN")) {
            throw new IllegalArgumentException("Invalid role value: " + role);
        }
    }
}
