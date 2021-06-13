package com.app.passtard.data.dao;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.passtard.data.entity.UserEntity;
@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> 
{

	Optional <UserEntity> findByEmail(String email);
	
	Optional <UserEntity>  findByUserId(String userid);
}