package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository		//Spring data repository
public interface UserRepository extends JpaRepository<User, Integer> {	//name of entity(User), Primary Key(Integer)
	
}
