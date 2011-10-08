package com.hascode.ra_samples;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("raexample")
public class RestAssuredSampleService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/single-user")
	public User getSingleUser() {
		User user = new User();
		user.setEmail("test@hascode.com");
		user.setFirstName("Tim");
		user.setLastName("Testerman");
		user.setId(1L);
		return user;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/single-user/xml")
	public User getSingleUserAsXml() {
		User user = new User();
		user.setEmail("test@hascode.com");
		user.setFirstName("Tim");
		user.setLastName("Testerman");
		user.setId(1L);
		return user;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/persons/xml")
	public List<Person> allUsers() {
		List<Person> users = new ArrayList<Person>();
		Person person1 = new Person();
		person1.setEmail("test@hascode.com");
		person1.setFirstName("Tim");
		person1.setLastName("Testerman");
		person1.setId(1L);
		users.add(person1);

		Person person2 = new Person();
		person2.setEmail("dev@hascode.com");
		person2.setFirstName("Sara");
		person2.setLastName("Stevens");
		person2.setId(20L);
		users.add(person2);

		Person person3 = new Person();
		person3.setEmail("devnull@hascode.com");
		person3.setFirstName("Mark");
		person3.setLastName("Mustache");
		person3.setId(11L);
		users.add(person3);
		return users;
	}
}
