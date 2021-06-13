package com.app.passtard.data.dto;
import java.io.Serializable;

import lombok.Data;

@Data
public class Userdto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8730810971891150192L;

	String  userId;
	String	firstName; 
	String	lastName; 
	String	password; 
	
}