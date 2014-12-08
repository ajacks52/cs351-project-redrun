package redrun.model.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Map;
import redrun.model.constants.Direction;
import redrun.model.constants.NetworkType;
import redrun.model.constants.Team;
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
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;

public class ObjectFromDB
{
  // Regex Patterns...
  /** The map pattern. */
  private static Pattern mapPattern = Pattern
      .compile("(===\\sMap\\s===)\\sID:(\\d+)\\sName:(.*?)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\sLight Position:(.*?)\\s===");

  /** The map objects pattern. */
  private static Pattern mapObjectPattern = Pattern
      .compile("(===\\sMap\\sObject\\s===)\\sID:(\\d+)\\sName:(\\w+)\\sLocation:(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\sGround Texture:(\\w+)\\sWall Texture:(\\w+)\\sDirection:(\\w+)\\sTrap Type:(.*?)\\sMap\\sID:(\\d+)\\s===");

  /** The player pattern. */
  private static Pattern playerPattern = Pattern
      .compile("===\\sPlayer\\s===\\sLocation:(.*?),\\s(.*?),\\s(.*?)\\sRotation:(.*?)\\sName:(.*?)\\sTeam\\sName:(\\w+)\\sHealth:(\\d+)\\sLives\\sleft:(\\d+)\\sAlive:(\\w+)\\s===");

  // Regex Matchers...
  /** The map matcher. */
  private static Matcher mapMatcher = null;

  /** The map objects matcher. */
  private static Matcher mapObjectMatcher = null;

  /** The player matcher. */
  private static Matcher playerMatcher = null;

  public static NetworkType parseNetworkType(String networkData)
  {
    mapMatcher = mapPattern.matcher(networkData);
    mapObjectMatcher = mapObjectPattern.matcher(networkData);
    playerMatcher = playerPattern.matcher(networkData);

    if (playerMatcher.find())
    {
      return NetworkType.PLAYER;
    }
    else if (mapObjectMatcher.find())
    {
      return NetworkType.MAP_OBJECT;
    }
    else if (mapMatcher.find())
    {
      return NetworkType.MAP;
    }
    else
    {
      return null;
    }
  }

  public static Map createMap(String networkItem)
  {
    mapMatcher = mapPattern.matcher(networkItem);

    if (mapMatcher.find())
    {
      return new Map(Integer.parseInt(mapMatcher.group(2)), mapMatcher.group(3), mapMatcher.group(4),
          mapMatcher.group(5), mapMatcher.group(6));
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
  public static MapObject createMapObject(String networkData)
  {
    mapObjectMatcher = mapObjectPattern.matcher(networkData);

    if (mapObjectMatcher.find())
    {
      // The position of the map object...
      float x = Float.parseFloat(mapObjectMatcher.group(4));
      float y = Float.parseFloat(mapObjectMatcher.group(5));
      float z = Float.parseFloat(mapObjectMatcher.group(6));

      // The textures to apply to the map object...
      final String groundTexture = mapObjectMatcher.group(7);
      final String wallTexture = mapObjectMatcher.group(8);

      // The orientation of the map object...
      Direction orientation = null;
      switch (mapObjectMatcher.group(9))
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
      switch (mapObjectMatcher.group(10))
      {
        case "PIT":
        {
          type = TrapType.PIT;
          break;
        }
        case "EXPLODING_BOX_FIELD":
        {
          type = TrapType.EXPLODING_BOX_FIELD;
          break;
        }
        case "JAIL":
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
        case "TRAP_DOOR":
        {
          type = TrapType.TRAP_DOOR;
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
      switch (mapObjectMatcher.group(3))
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

  public static void updatePlayer(String networkData)
  {
    playerMatcher = playerPattern.matcher(networkData);

    if (playerMatcher.find())
    {
      float x = Float.parseFloat(playerMatcher.group(1));
      float y = Float.parseFloat(playerMatcher.group(2));
      float z = Float.parseFloat(playerMatcher.group(3));
      float rotation = Float.parseFloat(playerMatcher.group(4));
      // TODO Is this right?
      float[] buffer = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, x, y, z };

      String name = playerMatcher.group(5);

      int health = Integer.parseInt(playerMatcher.group(7));
      int lives = Integer.parseInt(playerMatcher.group(8));
      boolean alive = Boolean.parseBoolean(playerMatcher.group(9));

      boolean isFound = false;

      for (Player player : GameData.players)
      {
        if (name.equals(player.getName()))
        {
          if (!name.equals(GameData.players.get(0).getName()))
          {
            player.getBody().setFromOpenGLTransformMatrix(buffer);
            player.getCamera().setYaw(rotation);
            player.setHealth(health);
            player.setLives(lives);
            player.setAlive(alive);
          }
          isFound = true;
        }
      }

      if (!isFound)
      {
        GameData.players.add(createPlayer(networkData));
      }
    }
  }

  public static Player createPlayer(String networkData)
  {
    playerMatcher = playerPattern.matcher(networkData);

    if (playerMatcher.find())
    {
      String name = playerMatcher.group(5);
      Team team = null;

      switch (playerMatcher.group(6))
      {
        case "RED":
        {
          team = Team.RED;
          break;
        }
        case "BLUE":
        {
          team = Team.BLUE;
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

      return new Player(0.0f, 1.0f, 0.0f, name, team);
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
   * Get the title from the map.
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
