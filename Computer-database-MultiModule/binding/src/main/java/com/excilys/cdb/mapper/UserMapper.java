package com.excilys.cdb.mapper;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.UserDto;
import com.excilys.cdb.model.User;

@Scope
@Component
public class UserMapper {
	
	public UserMapper() {}
	
	public UserDto modelToDto(User elem){
		return new UserDto(Integer.toString(elem.getId()),elem.getLogin(),elem.getMdp()); 
	}
	
	public User dtoToModel(UserDto elem) {
		return new User(elem.getLogin(),elem.getMdp());
	}
}
