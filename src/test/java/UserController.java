import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;

    public void UserController(Properties prop) {
        this.prop = prop;
    }

    public Response doLogin(UserModel userModel) {
        RestAssured.baseURI = "https://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json").body(userModel)
                .when().post("/user/login");
        return res;
    }

    public Response createUser(UserModel userModel){
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json")
                .header("Authorization", "bearer "+prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY",prop.getProperty("partnerKey"))
                .body(userModel).when().post("/user/create");
        return res;
    }

    public Response searchUsers(String userId){
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").header("Authorization", "bearer "+prop.getProperty("token"))
                .when().get("/user/search/id/"+userId);
        return res;

    }
}