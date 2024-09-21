package com.blog.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.Dto.UserDto;
import com.blog.configurations.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exception.ResourceNotfoundException;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;

import ch.qos.logback.core.model.Model;

/* JpaRepository Interface: The UserRepo interface extends JpaRepository, which is a part of Spring Data JPA. 
 * When Spring Boot starts up, it automatically scans for repositories extending JpaRepository and creates implementations for them.
    Automatic Bean Creation: Spring automatically creates a proxy class that implements the UserRepo interface. 
    This proxy is registered as a bean in the application context.
   @Autowired: When you use @Autowired, Spring finds the appropriate bean (the proxy class) and injects it into your component. 
 * */
@Service
public class UserServiceImplementation implements UserService{

	@Autowired
	private UserRepo ur;
	
	@Autowired
	private ModelMapper mm;
	
	@Autowired
	private PasswordEncoder pass;
	
	@Autowired
	private RoleRepo rolerepo;
	
	//below conversion can be done through MODELMAPPER
	//dtoTouser
	public User dtoTouser(UserDto userdto) {
		
			//		User u=new User();
			//		u.setId(user.getId());
			//		u.setName(user.getName());
			//		u.setPassword(user.getPassword());
			//		u.setEmail(user.getEmail());
			//		u.setAbout(user.getAbout());
		
		User u = mm.map(userdto, User.class);
		return u;
	}
	
	//Usertodto
	public UserDto Usertodto(User user) {

		UserDto u = mm.map(user, UserDto.class);
		return u;
	}
	
	
	@Override
	public UserDto createuser(UserDto user) {
		User userentity = dtoTouser(user);
		User save = ur.save(userentity);
		return Usertodto(save);
	}

	@Override
	public UserDto updateuser(UserDto user, int id) {
		
		User u=ur.findById(id).orElseThrow( () -> new ResourceNotfoundException("User","id",id));

		u.setName(user.getName());
		u.setPassword(user.getPassword());
		u.setEmail(user.getEmail());
		u.setAbout(user.getAbout());
		
		return  Usertodto(ur.save(u));
	}

	@Override
	public UserDto getuserById( int id) {
		
		User u=ur.findById(id).orElseThrow( () -> new ResourceNotfoundException("User","id",id));
		
		return Usertodto(u);
	}

	@Override
	public List<UserDto> getAlluser() {
		List<User> all = ur.findAll();
		if (all==null) {
		    	throw  new ResourceNotfoundException("No users found");
		}
		List<UserDto> userdtos = all.stream().map(
				u->Usertodto(u)
				).collect(Collectors.toList());
		
		return userdtos;
	}

	@Override
	public void deleteUser(int id) {
	
		User u=ur.findById(id).orElseThrow( () -> new ResourceNotfoundException("User","id",id));
		ur.delete(u);
	}

	//REGISTER USER
	@Override
	public UserDto registeruser(UserDto userdto) {
		User user = mm.map(userdto, User.class);
		//set password
		user.setPassword(pass.encode(userdto.getPassword()));
		Role role = rolerepo.findById(AppConstants.NORMAL_USER).get();	
		user.getRole().add(role);
		ur.save(user);
		return mm.map(ur.save(user), UserDto.class);
	}

}
