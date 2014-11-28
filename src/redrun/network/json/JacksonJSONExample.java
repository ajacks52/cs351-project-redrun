package redrun.network.json;

import java.io.File;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonJSONExample
{

  private static final String jsonFilePath = "/Users/l/programs/java/redrun/src/redrun/network/json/student.json";

  public static void main(String[] args)
  {

    Student student = new Student("Jacl", "Freeman", 32, 100);

    ObjectMapper mapper = new ObjectMapper();

    try
    {

      File jsonFile = new File(jsonFilePath);

      mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, student);

      // System.out.println(mapper.writeValueAsString(student));

    }
    catch (JsonGenerationException ex)
    {

      ex.printStackTrace();

    }
    catch (JsonMappingException ex)
    {

      ex.printStackTrace();

    }
    catch (IOException ex)
    {

      ex.printStackTrace();

    }
  }
}