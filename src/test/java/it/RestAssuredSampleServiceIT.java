package it;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.matcher.RestAssuredMatchers.matchesXsd;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class RestAssuredSampleServiceIT {

	/**
	 * Tutorial note: The JSON returned is this
	 * <code>{"email":"test@hascode.com","firstName":"Tim","id":"1","lastName":"Testerman"}</code>
	 */
	@Ignore
	@Test
	public void testGetSingleUser() {
		expect().statusCode(equalTo(200))
				.body("email", equalTo("test@hascode.com"), "firstName",
						equalTo("Tim"), "lastName", equalTo("Testerman"), "id",
						equalTo("1")).when()
				.get("/rest-assured-examples-webapp/raexample/single-user");
	}

	/**
	 * Tutorial note: The JSON returned is this
	 * <code>{"email":"test@hascode.com","firstName":"Tim","id":"1","lastName":"Testerman"}</code>
	 */
	@Ignore
	@Test
	public void testGetSingleUserProgrammatic() {
		Response res = get("/rest-assured-examples-webapp/raexample/single-user");
		assertEquals(200, res.getStatusCode());
		String json = res.asString();
		JsonPath jp = new JsonPath(json);
		assertEquals("test@hascode.com", jp.get("email"));
		assertEquals("Tim", jp.get("firstName"));
		assertEquals("Testerman", jp.get("lastName"));
		assertEquals("1", jp.get("id"));
	}

	@Ignore
	@Test
	public void testGetSingleUserAsXml() {
		expect().statusCode(equalTo(200))
				.body("user.email", equalTo("test@hascode.com"),
						"user.firstName", equalTo("Tim"), "user.lastName",
						equalTo("Testerman"), "user.id", equalTo("1")).when()
				.get("/rest-assured-examples-webapp/raexample/single-user/xml");
	}

	@Ignore
	@Test
	public void testGetPersons() {
		expect().statusCode(equalTo(200))
				.body(hasXPath("//person[@id='1']/email[.='test@hascode.com'] and firstName='Tim' and lastName='Testerman'"))
				.body(hasXPath("//person[@id='20']/email[.='dev@hascode.com'] and firstName='Sara' and lastName='Stevens'"))
				.body(hasXPath("//person[@id='1']/email[.='devnull@hascode.com'] and firstName='Mark' and lastName='Mustache'"))
				.when()
				.get("/rest-assured-examples-webapp/raexample/persons/xml");
	}

	@Test
	public void testGetSingleUserAgainstSchema() {
		InputStream xsd = getClass().getResourceAsStream("/user.xsd");
		assertNotNull(xsd);
		expect().statusCode(equalTo(200)).body(matchesXsd(xsd)).when()
				.get("/rest-assured-examples-webapp/raexample/single-user/xml");
	}
}
