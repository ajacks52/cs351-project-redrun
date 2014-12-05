package redrun.model.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.model.constants.Direction;
import redrun.model.constants.TrapType;
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
  //TODO Fix the .group shit
  /**
   * Creates and returns a new map object from the string representation of the database object.
   * 
   * @param mapDBForm the database representation of the map object
   * @return the new map object
   */
  public static MapObject createMapObject(String mapDBForm)
  {
    // System.out.println(mapDBForm);
    Pattern getMapObject = Pattern
        .compile("===\\sMap\\sObject\\s===\\sID:(\\d+)\\sName:(\\w+)\\sLocation:\\((\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\)\\sTexture:(\\w+)\\sDirection:(\\w+\\.\\w+)\\sMap\\sID:(\\d+)\\s===");

    Matcher matchMapObject = getMapObject.matcher(mapDBForm);

    if (matchMapObject.find())
    {
      // The position of the map object...
      float x = Float.parseFloat(matchMapObject.group(3));
      float y = Float.parseFloat(matchMapObject.group(4));
      float z = Float.parseFloat(matchMapObject.group(5));
      
      // The textures to apply to the map object...
      final String groundTexture = matchMapObject.group(6);
      final String wallTexture = matchMapObject.group(7);

      // The orientation of the map object...
      Direction orientation = null;
      switch (matchMapObject.group(8))
      {
        case "Direction.NORTH":
        {
          orientation = Direction.NORTH;
          break;
        }
        case "Direction.EAST":
        {
          orientation = Direction.EAST;
          break;
        }
        case "Direction.SOUTH":
        {
          orientation = Direction.SOUTH;
          break;
        }
        case "Direction.WEST":
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
      switch (matchMapObject.group(9))
      {
        case "JAIL_DOOR":
        {
          type = TrapType.JAIL_DOOR;
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
        case "DEATH_PILLAR":
        {
          type = TrapType.DEATH_PILLAR;
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
      switch (matchMapObject.group(2))
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
   * Creates and returns a new skybox with the specified texture from the database.
   * 
   * @param skyboxTexture the texture to apply to the skybox, received from the database
   * @return a new skybox
   */
  public static SkyBox createSkyboxFromDB(String skyboxTexture)
  {
    return new SkyBox(0, 0, 0, skyboxTexture);
  }

  /**
   * Creates and returns a new plane with the specified texture from the database.
   * 
   * @param groundTexture the texture to apply to the floor, received from the database
   * @return a new plane
   */
  public static Plane createFloor(String groundTexture)
  {
    return new Plane(0, 0, 0, groundTexture, Direction.NORTH, 1000);
  }

  //TODO need to do this later
  public static String getTitleFromDB(String map, int mapID)
  {
    Pattern getGameObject = Pattern
        .compile("===\\sMap\\s===\\sID:(\\d+)\\sName:(\\w+\\s\\w+)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\s===");

    Matcher matchGameObject = getGameObject.matcher(map);

    if (matchGameObject.find())
    {
      if (Integer.parseInt(matchGameObject.group(1)) == mapID) return matchGameObject.group(2);
    }
    return null;
  }
}
