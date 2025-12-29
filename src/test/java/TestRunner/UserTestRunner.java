package TestRunner;

import Config.Setup;
import Config.UserModel;
import Controller.UserController;
import Utils.Utils;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTestRunner extends Setup {
    @Test(priority = 1, description = "user login")
    public void doLogin() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@dmoney.com");
        userModel.setPassword("1234");
        Response res = userController.doLogin(userModel);

        //Extract value from json object -- using jsonPath
        JsonPath jsonObj = res.jsonPath();
        String token = jsonObj.get("token");
        System.out.println(token);

        //value and key save
        Utils.setEnv("token", token);
    }

    @Test(priority = 2, description = "Create User")
    public void createUser() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        Faker faker = new Faker();
        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress().toString().toLowerCase());
        userModel.setPassword("1234");
        userModel.setPhone_number("0130"+Utils.generateRandomNumber(1000000, 9999999));
        userModel.setNid("123456789");
        userModel.setRole("Customer");
        Response res = userController.createUser(userModel);

        //Extract value from json object -- using jsonPath
        JsonPath jsonObj = res.jsonPath();
        int userId = jsonObj.get("user.id");
        String name = jsonObj.get("user.name");
        String email = jsonObj.get("user.email");
        String password = jsonObj.get("user.password");
        String phoneNumber = jsonObj.get("user.phone_number");

        //value and key save
        Utils.setEnv("userId", String.valueOf(userId)); // as user.id value comes as int but need to save as String that's why uses String.valueof(userId)
        Utils.setEnv("name", name);
        Utils.setEnv("email", email);
        Utils.setEnv("password", password);
        Utils.setEnv("phoneNumber", phoneNumber);

        System.out.println(res.asString());

        Assert.assertEquals(jsonObj.get("message"), "User created");
    }

    @Test(priority = 3, description = "Search User By Id")
    public void searchUserById() throws ConfigurationException {
        UserController userController = new UserController(prop);
        Response res = userController.searchUsers(prop.getProperty("userId"));
        System.out.println(res.asString());

        // Assertion for Search User. As JsonObj isn't used here to we need to add the next line extra for assertion
        JsonPath jsonObj = res.jsonPath();
        Assert.assertEquals(jsonObj.get("message"), "User found");
    }
}
