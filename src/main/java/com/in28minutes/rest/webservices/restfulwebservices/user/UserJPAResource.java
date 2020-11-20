package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping(path = "/jpa/users")
	public List<User> retriveAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(path="/jpa/users/{id}")
	public EntityModel<User> retriveUser(@PathVariable int id) {
		 Optional<User> findOne = userRepository.findById(id);
		 if(!findOne.isPresent()) {
			 throw new UserNotFoundException("id - "+id);
		 }
		 else {
			 EntityModel<User> resource = EntityModel.of(findOne.get());
			 WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllUsers());
			 resource.add(linkTo.withRel("all-users"));
			 return resource;
		 }
	}
	
	@PostMapping(path = "/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	@GetMapping(path = "/jpa/users/{id}/posts")
	public List<Post> retrivePosts(@PathVariable int id) {
		Optional<User> findById = userRepository.findById(id);
		if(!findById.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		else {
			return findById.get().getPosts();
		}
	}
	
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> findById = userRepository.findById(id);
		if(!findById.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		User savedUser = findById.get();
		post.setUser(savedUser);
		postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
}
