import Utils.Utils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class MyRestAssure {
    Properties prop;
    public MyRestAssure() throws IOException { //created a constructor for token load / write
        // Need to load / write token first to access
        prop = new Properties();
        FileInputStream fs = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
    }

    @Test
    public void Login() throws ConfigurationException {
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json").body("{\n" +
                "  \"email\": \"admin@roadtocareer.net\",\n" +
                "  \"password\": \"1234\"\n" +
                "}").when().post("/user/login");
        System.out.println(res.asString());

        //Extract value from json object -- using jsonPath
        JsonPath jsonObj = res.jsonPath();
        String token = jsonObj.get("token");
        System.out.println(token);

        //value and key save
        Utils.setEnv("token", token);
    }


    @Test
    //search user
    public void searchUsers() throws IOException {

        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").header("Authorization", "bearer "+prop.getProperty("token"))
                .when().get("/user/search/id/98821");
        System.out.println(res.asString());

    }

    @Test
    //create user
    public void createUser(){
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json")
                .header("Authorization", "bearer "+prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY",prop.getProperty("partnerKey"))
                .body("{\n" +
                        "  \"name\": \"b162 user 1\",\n" +
                        "  \"email\": \"b162user2@gmail.com\",\n" +
                        "  \"password\": \"1234\",\n" +
                        "  \"phone_number\": \"01934444444\",\n" +
                        "  \"nid\": \"123756789\",\n" +
                        "  \"role\": \"customer\"\n" +
                        "}")
                .when().post("/user/create");
        System.out.println(res.asString());
    }
}
