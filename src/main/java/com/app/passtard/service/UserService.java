package com.app.passtard.service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.passtard.data.dto.Userdto;



public interface UserService extends UserDetailsService {
public Userdto createUser (Userdto user);
public Userdto getUserDetailByEmail(String email);
public Userdto getUserDetailsByUserId(String userid);

}