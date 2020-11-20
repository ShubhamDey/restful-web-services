package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository		//Spring data repository
public interface PostRepository extends JpaRepository<Post, Integer> {	//name of entity(Post), Primary Key(Integer)
	
}
