package redrun.model.toolkit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Character;
import redrun.database.Map;
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

  /**
   * Generate map
   * 
   * @param br bufferreader to read map input
   * @return map created
   */
  private static Map generateMap(BufferedReader br)
  {
    Pattern getMap = Pattern.compile("(\\w+\\s\\w+),\\s(\\w+),\\s(\\w+)");
    String mapName = null;
    try
    {
      String firstLine = br.readLine();
      Matcher matchMap = getMap.matcher(firstLine);
      if (matchMap.find())
      {
        mapName = matchMap.group(1);
        RedRunDAO.insertMap(matchMap.group(1), matchMap.group(2), matchMap.group(3));
        if (DEBUG)
        {
          for (int i = 1; i <= matchMap.groupCount(); i++)
            System.out.println(i + " " + matchMap.group(i));
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    for (Map map : RedRunDAO.getAllMaps())
    {
      if (DEBUG) System.out.println(map.toString());
      if (map.toString().contains(mapName)) return map;
    }
    return null;
  }

  private static void generateMapObjects(BufferedReader br, Map currentMap) throws IOException
  {
    int mapId = currentMap.getId();
    // System.out.println("Working Directory = " +
    // System.getProperty("user.dir"));
    Pattern getMapObject = Pattern
        .compile("(\\w+),\\s(\\(\\d+\\.\\d+f,\\s\\d+\\.\\d+f,\\s\\d+\\.\\d+f\\)),\\s(\\w+),\\s(\\w+\\.\\w+),\\s(\\d+)");

    String line = br.readLine();
    try
    {
      while (null != (line = br.readLine()))
      {
        Matcher matchMapObject = getMapObject.matcher(line);
        if (matchMapObject.find())
        {
          RedRunDAO.insertMapObject(matchMapObject.group(1), matchMapObject.group(2), matchMapObject.group(3),
              matchMapObject.group(4), mapId);
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
    if (DEBUG)
    {
      for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
      {
        System.out.println(mo.toString());
      }
    }
  }

  public static void main(String[] args) throws IOException
  {
    BufferedReader br = new BufferedReader(new FileReader("res/maps/IceWorld.txt"));

    Map currentMap = generateMap(br);
    generateMapObjects(br, currentMap);
    System.out.println("Database successfully populated!");
  }
}
