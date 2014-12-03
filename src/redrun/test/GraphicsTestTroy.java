package redrun.test;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;

import redrun.database.GameObjectDB;
import redrun.database.Map;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;
import redrun.graphics.camera.Camera;
import redrun.graphics.selection.Picker;
import redrun.model.constants.Direction;
import redrun.model.constants.MapConstants;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.map.Corner;
import redrun.model.gameobject.map.Corridor;
import redrun.model.gameobject.map.End;
import redrun.model.gameobject.map.Field;
import redrun.model.gameobject.map.Pit;
import redrun.model.gameobject.map.Platform;
import redrun.model.gameobject.map.Staircase;
import redrun.model.gameobject.map.Start;
import redrun.model.gameobject.map.Tunnel;
import redrun.model.gameobject.world.Cube;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;
import redrun.model.gameobject.world.WorldObject;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.Tools;
import redrun.network.Client;
import static org.lwjgl.opengl.GL11.*;

/**
 * This class is for testing OpenGL scenes.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class GraphicsTestTroy
{
  private static long lastFrame;

  /** Used to interface with the network client. */
  private static Client client = null;

  /** The camera associated with the client. */
  private static Camera camera = null;

  /** The network data that is to be turned into map objects or game objects. */
  // public static volatile LinkedList<ArrayList<String>> networkData = new
  // LinkedList<ArrayList<String>>();
  public static volatile ArrayList<String> networkData = new ArrayList<String>();

  /** Used to keep track of the current map. */
  // formerly: RedRunDAO.getMap(1);
  private static Map map = null;

  /** The map objects that make up the level. */
  private static LinkedList<MapObject> mapObjects = new LinkedList<MapObject>();

  /** The game objects that are in the level. */
  private static LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();

  /**
   * Performs OpenGL initialization.
   */
  private static void createOpenGL()
  {
    // Connect to the server...
    client = new Client("127.0.0.1", 7777, mapObjects);
    // map defined
    int mapID = 1;
    // this will need to be removed in favor of sending info from the server:
    // map = RedRunDAO.getMap(mapID);
    // map = getMapOutline();

    try
    {
      Display.setDisplayMode(new DisplayMode(1280, 720));
      // fix this later:
      // Display.setTitle(getTitleFromDB(map.toString(), mapID));
      Display.create();
      Display.setVSyncEnabled(true);
    }
    catch (LWJGLException ex)
    {
      Logger.getLogger(GraphicsTestTroy.class.getName()).log(Level.SEVERE, null, ex);
    }

    camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f, 0.0f, 0.0f);

    glEnable(GL_DEPTH_TEST);
    glDisable(GL_COLOR_MATERIAL);
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_LIGHT1);
    glEnable(GL_NORMALIZE);
    glShadeModel(GL_SMOOTH);

    // Load the fonts...
    FontTools.loadFonts();
  }

  public static MapObject createMapObjectFromDB(String mapDBForm)
  {
    // System.out.println(mapDBForm);
    Pattern getMapObject = Pattern
        .compile("===\\sMap\\sObject\\s===\\sID:(\\d+)\\sName:(\\w+)\\sLocation:\\((\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\)\\sTexture:(\\w+)\\sDirection:(\\w+\\.\\w+)\\sMap\\sID:(\\d+)\\s===");

    Matcher matchMapObject = getMapObject.matcher(mapDBForm);

    if (matchMapObject.find())
    {
      float x = Float.parseFloat(matchMapObject.group(3));
      float y = Float.parseFloat(matchMapObject.group(4));
      float z = Float.parseFloat(matchMapObject.group(5));
      String textureName = matchMapObject.group(6);

      Direction orientation = null;
      switch (matchMapObject.group(7))
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

      switch (matchMapObject.group(2))
      {
        case "Corner":
        {
          return new Corner(x, y, z, textureName, orientation, null);
        }
        case "Corridor":
        {
          return new Corridor(x, y, z, textureName, orientation, null);
        }
        case "End":
        {
          return new End(x, y, z, textureName, orientation, null);
        }
        case "Field":
        {
          return new Field(x, y, z, textureName, orientation, null);
        }
        case "Pit":
        {
          return new Pit(x, y, z, textureName, orientation, null);
        }
        case "Platform":
        {
          return new Platform(x, y, z, textureName, orientation, null);
        }
        case "Staircase":
        {
          return new Staircase(x, y, z, textureName, orientation, null);
        }
        case "Start":
        {
          return new Start(x, y, z, textureName, orientation, null);
        }
        case "Tunnel":
        {
          return new Tunnel(x, y, z, textureName, orientation, null);
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

  // May not need to match on as much stuff
  public static SkyBox createSkyBoxFromDB(String skybox)
  {
    return new SkyBox(0, 0, 0, skybox, camera);
  }

  public static Plane createFloorFromDB(String map)
  {
    return new Plane(0, 0, 0, map, 1000);
  }

  // May not need to match on as much stuff
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

  /**
   * The main loop where the logic occurs. Stopped when the escape key is
   * pressed or the window is closed.
   */
  private static void gameLoop()
  {
    // Create the skybox
    SkyBox skybox = null;
    // Create the floor
    GameObject floor = null;
    // Draw map once
    boolean mapDrawn = false;

    Cube testCube = new Cube(0.0f, 50.0f, 0f, "wood");

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);

    Pattern getMap = Pattern
        .compile("(===\\sMap\\s===)\\sid:(\\d+)\\sName:(.*?)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\s(===\\sMap\\sEnd\\s===)");

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      System.out.println(networkData.isEmpty());
      while (!networkData.isEmpty())
      {
        for (String networkItem : networkData)
        {
          Matcher matchMap = getMap.matcher(networkItem);
          // Matcher matchMapObject = getMapObject.matcher(networkItem);
          if (matchMap.find() && mapDrawn == false)
          {
            map = new Map(Integer.parseInt(matchMap.group(2)), matchMap.group(3), matchMap.group(4), matchMap.group(5));
            skybox = createSkyBoxFromDB(matchMap.group(4));
            floor = createFloorFromDB(matchMap.group(5));
            mapDrawn = true;
          }
          else
          {
            MapObject object = createMapObjectFromDB(networkItem);
            if (!mapObjects.contains(object))
            {
              mapObjects.add(createMapObjectFromDB(networkItem));
            }
          }
        }
        break;
      }

      getInput();

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glLoadIdentity();

      glPushMatrix();
      {
        glDepthMask(false);
        // TODO Fix rotations along X and Z axis.
        glRotatef(camera.getYaw(), 0, 1, 0);
        skybox.draw();
        glDepthMask(true);
      }
      glPopMatrix();

      camera.lookThrough();

      // Add ambient light...
      FloatBuffer ambientColor = BufferConverter.asFloatBuffer(new float[] { 0.2f, 0.2f, 0.2f, 1.0f });
      glLightModel(GL_LIGHT_MODEL_AMBIENT, ambientColor);

      // Add positional light...
      FloatBuffer lightColor0 = BufferConverter.asFloatBuffer(new float[] { 1.5f, 1.5f, 1.5f, 1.0f });
      FloatBuffer lightPosition0 = BufferConverter.asFloatBuffer(new float[] { -15.0f, 10.0f, 5.0f, 1.0f });
      glLight(GL_LIGHT0, GL_DIFFUSE, lightColor0);
      glLight(GL_LIGHT0, GL_SPECULAR, lightColor0);
      glLight(GL_LIGHT0, GL_POSITION, lightPosition0);

      // Add directional light...
      FloatBuffer lightColor1 = BufferConverter.asFloatBuffer(new float[] { 0.5f, 0.5f, 0.5f, 1.0f });
      FloatBuffer lightPosition1 = BufferConverter.asFloatBuffer(new float[] { -15.0f, 10.0f, 5.0f, 0.0f });
      glLight(GL_LIGHT1, GL_DIFFUSE, lightColor1);
      glLight(GL_LIGHT1, GL_POSITION, lightPosition1);

      if (Keyboard.isKeyDown(Keyboard.KEY_F))
      {
        Picker.startPicking();
        {

        }
        Picker.stopPicking();
      }
      floor.draw();

      // Draw the world map...
      if (!mapObjects.isEmpty())
      {
        for (MapObject mapObject : mapObjects)
        {
          mapObject.draw();
        }
      }

      // Draw the world map...
      // for (GameObject gameObject : gameObjects)
      // {
      // gameObject.draw();
      // }

      testCube.draw();

      // Draw text to the screen...
      FontTools.draw2D();
      FontTools.renderText("Position: (" + camera.getX() + ", " + camera.getY() + ", " + camera.getZ() + ")", 10, 10,
          Color.orange, 1);
      FontTools.draw3D();

      PhysicsWorld.stepSimulation(1 / 60.0f);
      Timer.tick();
      Display.update();
      Display.sync(60);
      if (mapDrawn == true)
      {
        // mapObjects.clear();
        networkData.clear();
        // client.requestMapObjects();
      }
    }
  }

  private static void createMapObject(ArrayList<String> data)
  {
    Pattern getMapObject = Pattern
        .compile("===\\sMap\\sObject\\s===\\sID:(\\d+)\\sName:(\\w+)\\sLocation:\\((\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\)\\sTexture:(\\w+)\\sDirection:(\\w+\\.\\w+)\\sMap\\sID:(\\d+)\\s===");

    for (String item : data)
    {
      System.out.println(item);
      Matcher matchMapObject = getMapObject.matcher(item);
      float x = Float.parseFloat(matchMapObject.group(3));
      float y = Float.parseFloat(matchMapObject.group(4));
      float z = Float.parseFloat(matchMapObject.group(5));
      String textureName = matchMapObject.group(6);

      Direction orientation = null;
      switch (matchMapObject.group(7))
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

      switch (matchMapObject.group(2))
      {
        case "Corner":
        {
          mapObjects.add(new Corner(x, y, z, textureName, orientation, null));
        }
        case "Corridor":
        {
          mapObjects.add(new Corridor(x, y, z, textureName, orientation, null));
        }
        case "End":
        {
          mapObjects.add(new End(x, y, z, textureName, orientation, null));
        }
        case "Field":
        {
          mapObjects.add(new Field(x, y, z, textureName, orientation, null));
        }
        case "Pit":
        {
          mapObjects.add(new Pit(x, y, z, textureName, orientation, null));
        }
        case "Platform":
        {
          mapObjects.add(new Platform(x, y, z, textureName, orientation, null));
        }
        case "Staircase":
        {
          mapObjects.add(new Staircase(x, y, z, textureName, orientation, null));
        }
        case "Start":
        {
          mapObjects.add(new Start(x, y, z, textureName, orientation, null));
        }
        case "Tunnel":
        {
          mapObjects.add(new Tunnel(x, y, z, textureName, orientation, null));
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
  }

  /**
   * Gets user input from the keyboard and mouse.
   */
  private static void getInput()
  {
    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;

    // Set the mouse sensitivity...
    float mouseSensitivity = 0.08f;
    float movementSpeed = 0.02f;

    dx = Mouse.getDX();
    dy = Mouse.getDY();
    dt = getDelta();

    camera.yaw(dx * mouseSensitivity);
    camera.pitch(-dy * mouseSensitivity);

    if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) camera.moveForward(movementSpeed
        * dt * 2);
    else if (Keyboard.isKeyDown(Keyboard.KEY_W)) camera.moveForward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_S)) camera.moveBackward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_A)) camera.moveLeft(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_D)) camera.moveRight(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_UP)) camera.moveUp(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) camera.moveDown(movementSpeed * dt);
  }

  /**
   * Calculate how many milliseconds have passed since last frame.
   * 
   * @return milliseconds passed since last frame
   */
  public static int getDelta()
  {
    long time = Tools.getTime();
    int delta = (int) (time - lastFrame);
    lastFrame = time;

    return delta;
  }

  /**
   * Cleans up resources.
   */
  private static void destroyOpenGL()
  {
    Display.destroy();
  }

  public static void main(String[] args)
  {
    GraphicsTestTroy.createOpenGL();
    GraphicsTestTroy.gameLoop();
    GraphicsTestTroy.destroyOpenGL();
  }
}
