package redrun.network.json;

import java.io.File;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONToJavaObjectJacksonExample {

    private static final String jsonFilePath= "/Users/l/programs/java/redrun/src/redrun/network/json/student.json";

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            File jsonFile = new File(jsonFilePath);

            Student student = mapper.readValue(jsonFile, Student.class);

            System.out.println(student);

        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (JsonMappingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}