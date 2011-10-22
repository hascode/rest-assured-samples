package com.hascode.ra_samples;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/service")
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/persons/json")
	public List<Person> allUsersAsJson() {
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/create")
	public User createUser(@QueryParam("email") final String email,
			@QueryParam("firstName") final String firstName,
			@QueryParam("lastName") final String lastName) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setId(1L);

		return user;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/status/notfound")
	public Response statusNotFound() {
		return Response.status(404).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/secure/person")
	public Response secureGetPerson() {
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/header/print")
	public String printHeader(@HeaderParam("myparam") final String headerParam) {
		return headerParam;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/header/multiple")
	public Response multipleHeader() {
		return Response.ok().header("customHeader1", "foo")
				.header("anotherHeader", "bar").build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/access/cookie-token-secured")
	public Response accessSecuredByCookie(
			@CookieParam("authtoken") final String authToken) {
		if ("abcdef".equals(authToken)) {
			return Response.ok().build();
		}

		return Response.status(Status.FORBIDDEN).build();

	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/cookie/modify")
	public Response modifyCookie(@Context final HttpHeaders httpHeaders,
			@QueryParam("name") final String name) {
		NewCookie cookie = new NewCookie("userName", name);
		return Response.ok().cookie(cookie).build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/file/upload")
	public String contentFromFileupload(
			@FormDataParam("file") final InputStream file,
			@FormDataParam("file") final FormDataContentDisposition fileDisposition) {
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(file, writer);
			return writer.toString();
		} catch (IOException e) {
		}
		return "";
	}

	@GET
	@Produces("text/json")
	@Path("/detail/json")
	public String getDetailsAsJson() {
		try {
			JSONObject obj;
			obj = new JSONObject("{\"test\":true}");
			return obj.toString();
		} catch (JSONException e) {
		}
		return "";
	}
}
