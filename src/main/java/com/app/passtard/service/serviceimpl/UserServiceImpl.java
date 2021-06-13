package com.app.passtard.service.serviceimpl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.passtard.data.dao.UserRepository;
import com.app.passtard.data.dto.Userdto;
import com.app.passtard.data.entity.UserEntity;
import com.app.passtard.service.UserService;



@Service
public class UserServiceImpl implements UserService {

	UserRepository userRespository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
    
    
	@Autowired
	UserServiceImpl(UserRepository userRespository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRespository = userRespository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public Userdto createUser(Userdto userdto) {
		userdto.setUserId(UUID.randomUUID().toString());
		userdto.setPassword(bCryptPasswordEncoder.encode(userdto.getPassword()));
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userentity = mapper.map(userdto, UserEntity.class);
		userRespository.save(userentity);
		Userdto returnValue = mapper.map(userentity, Userdto.class);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional <UserEntity> user=userRespository.findByEmail(username);
		if(!user.isPresent()) 
		throw new UsernameNotFoundException(username);
	    return new User(user.get().getUserId(),user.get().getPassword(),true,true,true,true,new ArrayList());
	}

	@Override
	public Userdto getUserDetailByEmail(String email)
	{
		Optional <UserEntity> user=userRespository.findByEmail(email);
		if(!user.isPresent()) 
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not Registered");
		return new ModelMapper().map(user.get(),Userdto.class);
	}

	@Override
	public Userdto getUserDetailsByUserId(String userid) {
		Optional <UserEntity> user=userRespository.findByUserId(userid);
		if(!user.isPresent()) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not Registered");
		return new ModelMapper().map(user.get(),Userdto.class);
	}

}