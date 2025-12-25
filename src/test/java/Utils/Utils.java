package Utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Utils {
    public static void setEnv(String key, String value) throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key, value);
        config.save();
    }

    //for phone number
    public static int generateRandomNumber(int min, int max){
        double randomNumber = Math.random()*(max-min)+min;
        return (int) randomNumber;
    }
}
