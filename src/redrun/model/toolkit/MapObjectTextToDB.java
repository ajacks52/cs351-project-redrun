package redrun.model.toolkit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Character;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;

/**
 * Read in text file with map information, and parse it into the RedRun database
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-24
 *
 */
public class MapObjectTextToDB
{
  private static final boolean DEBUG = false;

  public static void main(String[] args) throws IOException
  {
    int mapId;
    // Create Maps Table for database stuff
    // return foreign key, to be used with our parsing of the text file
    
    
    // System.out.println("Working Directory = " +
    // System.getProperty("user.dir"));
    Pattern getMapObject = Pattern
        .compile("(\\w+),\\s(\\(\\d+\\.\\d+f,\\s\\d+\\.\\d+f,\\s\\d+\\.\\d+f\\)),\\s(\\w+),\\s(\\w+\\.\\w+),\\s(\\d+)");

    BufferedReader br = new BufferedReader(new FileReader("src/redrun/test/database/Parseable.txt"));

    String line = br.readLine();
    try
    {
      while (null != (line = br.readLine()))
      {
        // System.out.println(line);
        Matcher matchMapObject = getMapObject.matcher(line);
        if (matchMapObject.find())
        {
          RedRunDAO.insertMapObject(matchMapObject.group(1), matchMapObject.group(2), matchMapObject.group(3),
              matchMapObject.group(4), 1);
          if (DEBUG)
          {
            for (int i = 1; i <= matchMapObject.groupCount(); i++)
              System.out.println(i + " " + matchMapObject.group(i));
          }
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      br.close();
    }
    for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
    {
      System.out.println(mo.toString());
    }
  }
}
