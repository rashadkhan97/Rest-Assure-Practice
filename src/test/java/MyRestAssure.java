import Utils.Utils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class MyRestAssure {
    @Test
    public void Login() throws ConfigurationException {
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response response = given().contentType("application/json").body("{\n" +
                "  \"email\": \"admin@roadtocareer.net\",\n" +
                "  \"password\": \"1234\"\n" +
                "}").when().post("/user/login");
        System.out.println(response.asString());

        //Extract value from json object -- using jsonPath
        JsonPath jsonObj = response.jsonPath();
        String token = jsonObj.get("token");
        System.out.println(token);

        //value and key save
        Utils.setEnv("token", token);
    }
}
