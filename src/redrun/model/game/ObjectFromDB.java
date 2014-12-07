package redrun.model.game;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Map;
import redrun.model.constants.Direction;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.map.Corner;
import redrun.model.gameobject.map.Corridor;
import redrun.model.gameobject.map.End;
import redrun.model.gameobject.map.Field;
import redrun.model.gameobject.map.Kiosk;
import redrun.model.gameobject.map.Pit;
import redrun.model.gameobject.map.Platform;
import redrun.model.gameobject.map.Staircase;
import redrun.model.gameobject.map.Start;
import redrun.model.gameobject.map.Tunnel;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;

public class ObjectFromDB
{
  /** The map objects that make up the level. */
  public static LinkedList<MapObject> mapObjects = new LinkedList<MapObject>();

  /** The game objects that are in the level. */
  public static LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();

  public Map map;
  public static boolean mapDrawn = false;

  public static Map createMap(String networkItem)
  {
    Pattern getMap = Pattern
        .compile("(===\\sMap\\s===)\\sID:(\\d+)\\sName:(.*?)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\sLight Position:(.*?)\\s===");
    Matcher matchMap = getMap.matcher(networkItem);
    if (matchMap.find())
    {
      mapDrawn = true;
      return new Map(Integer.parseInt(matchMap.group(2)), matchMap.group(3), matchMap.group(4), matchMap.group(5),
          matchMap.group(6));
    }
    return null;
  }

  /**
   * Creates and returns a new map object from the string representation of the
   * database object.
   * 
   * @param mapDBForm the database representation of the map object
   * @return the new map object
   */
  public static MapObject createMapObject(String mapDBForm)
  {
    // System.out.println(mapDBForm);
    Pattern getMapObject = Pattern
        .compile("(===\\sMap\\sObject\\s===)\\sID:(\\d+)\\sName:(\\w+)\\sLocation:(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\sGround Texture:(\\w+)\\sWall Texture:(\\w+)\\sDirection:(\\w+)\\sTrap Type:(.*?)\\sMap\\sID:(\\d+)\\s===");

    Matcher matchMapObject = getMapObject.matcher(mapDBForm);

    if (matchMapObject.find())
    {
      // The position of the map object...
      float x = Float.parseFloat(matchMapObject.group(4));
      float y = Float.parseFloat(matchMapObject.group(5));
      float z = Float.parseFloat(matchMapObject.group(6));

      // The textures to apply to the map object...
      final String groundTexture = matchMapObject.group(7);
      final String wallTexture = matchMapObject.group(8);

      // The orientation of the map object...
      Direction orientation = null;
      switch (matchMapObject.group(9))
      {
        case "NORTH":
        {
          orientation = Direction.NORTH;
          break;
        }
        case "EAST":
        {
          orientation = Direction.EAST;
          break;
        }
        case "SOUTH":
        {
          orientation = Direction.SOUTH;
          break;
        }
        case "WEST":
        {
          orientation = Direction.WEST;
          break;
        }
        default:
        {
          try
          {
            throw new IllegalArgumentException();
          }
          catch (IllegalArgumentException e)
          {
            e.printStackTrace();
          }
        }
      }

      // The trap placed on the map object...
      TrapType type = null;
      switch (matchMapObject.group(10))
      {
        case "JAIL_DOOR":
        {
          type = TrapType.JAIL;
          break;
        }
        case "SPIKE_FIELD":
        {
          type = TrapType.SPIKE_FIELD;
          break;
        }
        case "SPIKE_TRAP_DOOR":
        {
          type = TrapType.SPIKE_TRAP_DOOR;
          break;
        }
        case "POLE_WALL":
        {
          type = TrapType.POLE_WALL;
          break;
        }
        case "POLE_DANCE":
        {
          type = TrapType.POLE_DANCE;
          break;
        }
        case "ROCK_SMASH":
        {
          type = TrapType.ROCK_SMASH;
          break;
        }
        case "EMPTY":
        {
          type = TrapType.EMPTY;
          break;
        }
        default:
        {
          try
          {
            throw new IllegalArgumentException();
          }
          catch (IllegalArgumentException e)
          {
            e.printStackTrace();
          }
        }
      }

      // Make and return the map object...
      switch (matchMapObject.group(3))
      {
        case "Corner":
        {
          return new Corner(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Corridor":
        {
          return new Corridor(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "End":
        {
          return new End(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Field":
        {
          return new Field(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Kiosk":
        {
          return new Kiosk(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Pit":
        {
          return new Pit(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Platform":
        {
          return new Platform(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Staircase":
        {
          return new Staircase(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Start":
        {
          return new Start(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Tunnel":
        {
          return new Tunnel(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        default:
        {
          try
          {
            throw new IllegalArgumentException();
          }
          catch (IllegalArgumentException e)
          {
            e.printStackTrace();
          }
        }
      }
    }
    return null;
  }

  /**
   * Creates and returns a new skybox with the specified texture from the
   * database.
   * 
   * @param skyboxTexture the texture to apply to the skybox, received from the
   *          database
   * @return a new skybox
   */
  public static SkyBox createSkybox(String skyboxTexture)
  {
    return new SkyBox(0, 0, 0, skyboxTexture);
  }

  /**
   * Creates and returns a new plane with the specified texture from the
   * database.
   * 
   * @param groundTexture the texture to apply to the floor, received from the
   *          database
   * @return a new plane
   */
  public static Plane createFloor(String groundTexture)
  {
    return new Plane(0, -0.5f, 0, groundTexture, Direction.NORTH, 1000);
  }

  /**
   * Parse title from map
   * 
   * @param map name of map
   * @param mapID id for map
   * @return title of map
   */
  public static String getTitle(String map, int mapID)
  {
    Pattern getGameObject = Pattern
        .compile("(===\\sMap\\s===)\\sID:(\\d+)\\sName:(.*?)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\sLight Position:(.*?)\\s===");

    Matcher matchGameObject = getGameObject.matcher(map);

    if (matchGameObject.find()) return matchGameObject.group(3);
    else return null;
  }
}
