package com.app.passtard.data.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class UserEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8659097910360499487L;

	@Id
	@GeneratedValue
	long id;
	
	@Column(nullable=false,length =50)
	String	firstName; 
	@Column(nullable=false,length =50)
	String	lastName; 
	
	@Column(nullable=false,length =50,unique=true)
	String  userId;
	
	@Column(nullable=false)
	String  password;
}