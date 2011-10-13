package it;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.matcher.RestAssuredMatchers.matchesXsd;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class RestAssuredSampleServiceIT {

	/**
	 * Tutorial note: The JSON returned is this
	 * <code>{"email":"test@hascode.com","firstName":"Tim","id":"1","lastName":"Testerman"}</code>
	 */
	@Test
	public void testGetSingleUser() {
		expect().statusCode(200)
				.body("email", equalTo("test@hascode.com"), "firstName",
						equalTo("Tim"), "lastName", equalTo("Testerman"), "id",
						equalTo("1")).when().get("/ra/service/single-user");
	}

	/**
	 * Tutorial note: The JSON returned is this
	 * <code>{"email":"test@hascode.com","firstName":"Tim","id":"1","lastName":"Testerman"}</code>
	 */
	@Test
	public void testGetSingleUserProgrammatic() {
		Response res = get("/ra/service/single-user");
		assertEquals(200, res.getStatusCode());
		String json = res.asString();
		JsonPath jp = new JsonPath(json);
		assertEquals("test@hascode.com", jp.get("email"));
		assertEquals("Tim", jp.get("firstName"));
		assertEquals("Testerman", jp.get("lastName"));
		assertEquals("1", jp.get("id"));
	}

	@Test
	public void testGetSingleUserAsXml() {
		expect().statusCode(200)
				.body("user.email", equalTo("test@hascode.com"),
						"user.firstName", equalTo("Tim"), "user.lastName",
						equalTo("Testerman"), "user.id", equalTo("1")).when()
				.get("/ra/service/single-user/xml");
	}

	@Test
	public void testGetPersons() {
		expect().statusCode(200)
				.body(hasXPath("//person[@id='1']/email[.='test@hascode.com'] and firstName='Tim' and lastName='Testerman'"))
				.body(hasXPath("//person[@id='20']/email[.='dev@hascode.com'] and firstName='Sara' and lastName='Stevens'"))
				.body(hasXPath("//person[@id='1']/email[.='devnull@hascode.com'] and firstName='Mark' and lastName='Mustache'"))
				.when().get("/ra/service/persons/xml");
	}

	@Test
	public void testGetSingleUserAgainstSchema() {
		InputStream xsd = getClass().getResourceAsStream("/user.xsd");
		assertNotNull(xsd);
		expect().statusCode(200).body(matchesXsd(xsd)).when()
				.get("/ra/service/single-user/xml");
	}

	@Test
	public void testCreateuser() {
		final String email = "test@hascode.com";
		final String firstName = "Tim";
		final String lastName = "Tester";

		given().parameters("email", email, "firstName", firstName, "lastName",
				lastName).expect().body("email", equalTo(email))
				.body("firstName", equalTo(firstName))
				.body("lastName", equalTo(lastName)).when()
				.get("/ra/service/user/create");
	}

	@Test
	public void testStatusNotFound() {
		expect().statusCode(404).when().get("/ra/service/status/notfound");
	}

	@Test
	public void testAuthenticationWorking() {
		// we're not authenticated, service returns "401 Unauthorized"
		expect().statusCode(401).when().get("/ra/service/secure/person");

		// with authentication it is working
		expect().statusCode(200).body(equalTo("Ok")).when().with()
				.authentication().basic("admin", "admin")
				.get("/ra/service/secure/person");
	}

	@Test
	public void testSetRequestHeaders() {
		expect().body(equalTo("TEST")).when().with().header("myparam", "TEST")
				.get("/ra/service/header/print");
		expect().body(equalTo("foo")).when().with().header("myparam", "foo")
				.get("/ra/service/header/print");
	}
}
