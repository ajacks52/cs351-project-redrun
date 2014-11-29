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
  // TODO Move this shit.
  private static long lastFrame;

  /** Used to interface with the network client. */
  private static Client client = null;

  /** The camera associated with the client. */
  private static Camera camera = null;

  /** The map objects that make up the level. */
  private static LinkedList<MapObject> mapObjects = new LinkedList<MapObject>();

  /** The game objects that are in the level. */
  private static LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();

  /**
   * Performs OpenGL initialization.
   */
  private static void createOpenGL()
  {
    try
    {
      Display.setDisplayMode(new DisplayMode(1280, 720));
      Display.setTitle("An Awesome OpenGL Scene");
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

    // Connect to the server...
    // client = new Client("127.0.0.1", 7777, mapObjects);
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

  public static SkyBox createSkyBoxGameObjectFromDB(String map, int mapID)
  {
    System.out.println(map);
    // System.out.println(mapDBForm);
    Pattern getGameObject = Pattern
        .compile("===\\sMap\\s===\\sID:(\\d+)\\sName:(\\w+\\s\\w+)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\s===");

    Matcher matchGameObject = getGameObject.matcher(map);

    if (matchGameObject.find())
    {
      if (Integer.parseInt(matchGameObject.group(1)) == mapID)
      {
        for (int i = 1; i <= matchGameObject.groupCount(); i++)
        {
          System.out.println(matchGameObject.group(i));
        }
        String mapName = matchGameObject.group(2);
        String skyBox = matchGameObject.group(3);
        String floor = matchGameObject.group(4);
        return new SkyBox(0, 0, 0, skyBox, camera);
      }
    }
    return null;
  }

  /**
   * The main loop where the logic occurs. Stopped when the escape key is
   * pressed or the window is closed.
   */
  private static void gameLoop()
  {
    // Create the skybox...
    SkyBox skybox = null;

    ArrayList<MapObjectDB> mapStuff = RedRunDAO.getAllMapObjects();

    for (MapObjectDB object : mapStuff)
    {
      mapObjects.add(createMapObjectFromDB(object.toString()));
    }

//    ArrayList<Map> gameDB = RedRunDAO.getAllMaps();

    Map map = RedRunDAO.getMap(1);

    skybox = createSkyBoxGameObjectFromDB(map.toString(), 1);
    // gameObjects.add(createGameObjectFromDB(map.toString()));

    // // Add the starting point...
    // worldMap.add(new Start(0.0f, 0.0f, 0.0f, "brickwall5", Direction.NORTH,
    // null));
    //
    // // Add a walkway...
    // worldMap.add(new Corridor(0.0f, 0.0f, 15.0f, "brickwall5",
    // Direction.NORTH, null));
    // worldMap.add(new Corridor(0.0f, 0.0f, 30.0f, "brickwall5",
    // Direction.NORTH, null));
    // worldMap.add(new Corridor(0.0f, 0.0f, 45.0f, "brickwall5",
    // Direction.NORTH, null));
    //
    // // Add a corner...
    // worldMap.add(new Corner(0.0f, 0.0f, 60.0f, "brickwall5", Direction.NORTH,
    // null));
    //
    // // Add a staircase...
    // worldMap.add(new Staircase(15.0f, 0.0f, 60.0f, "brickwall5",
    // Direction.NORTH, null));
    //
    // // Add a walkway...
    // worldMap.add(new Corridor(30.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    // worldMap.add(new Corridor(45.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    //
    // // Add a field...
    // worldMap.add(new Field(75.0f, 15.0f, 60.0f, "brickwall5", Direction.EAST,
    // null));
    //
    // // Add a walkway...
    // worldMap.add(new Corridor(105.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    // worldMap.add(new Corridor(120.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    //
    // // Add a tunnel...
    // worldMap.add(new Tunnel(135.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    // worldMap.add(new Tunnel(150.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    //
    // // Add a walkway...
    // worldMap.add(new Corridor(165.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    //
    // // Add a pit...
    // worldMap.add(new Pit(180.0f, 15.0f, 60.0f, "brickwall5", Direction.EAST,
    // null));
    //
    // // Add a walkway...
    // worldMap.add(new Corridor(195.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    //
    // // Add a platform...
    // worldMap.add(new Platform(210.0f, 15.0f, 60.0f, "brickwall5",
    // Direction.EAST, null));
    //
    // // Add the ending point...
    // worldMap.add(new End(225.0f, 15.0f, 60.0f, "brickwall5", Direction.EAST,
    // null));

    Cube testCube = new Cube(0.0f, 50.0f, 0f, "wood");

    // Corridor corridor1 = new Corridor(20, 0.5f, 20, "wood", Direction.NORTH,
    // null);
    // Corridor corridor2 = new Corridor(30, 0.5f, 20, "wood", Direction.EAST,
    // null);
    // Corridor corridor3 = new Corridor(40, 0.5f, 20, "wood", Direction.SOUTH,
    // null);
    // Corridor corridor4 = new Corridor(50, 0.5f, 20, "wood", Direction.WEST,
    // null);
    //
    // Corner corner1 = new Corner(20, 0.5f, 30, "wood", Direction.NORTH, null);
    // Corner corner2 = new Corner(30, 0.5f, 30, "wood", Direction.EAST, null);
    // Corner corner3 = new Corner(40, 0.5f, 30, "wood", Direction.SOUTH, null);
    // Corner corner4 = new Corner(50, 0.5f, 30, "wood", Direction.WEST, null);
    //
    // End end1 = new End(20, 0.5f, 40, "wood", Direction.NORTH, null);
    // End end2 = new End(30, 0.5f, 40, "wood", Direction.EAST, null);
    // End end3 = new End(40, 0.5f, 40, "wood", Direction.SOUTH, null);
    // End end4 = new End(50, 0.5f, 40, "wood", Direction.WEST, null);
    //
    // Start start1 = new Start(20, 0.5f, 50, "wood", Direction.NORTH, null);
    // Start start2 = new Start(30, 0.5f, 50, "wood", Direction.EAST, null);
    // Start start3 = new Start(40, 0.5f, 50, "wood", Direction.SOUTH, null);
    // Start start4 = new Start(50, 0.5f, 50, "wood", Direction.WEST, null);
    //
    // Pit pit1 = new Pit(20, 0.5f, 60, "wood", Direction.NORTH, null);
    // Pit pit2 = new Pit(30, 0.5f, 60, "wood", Direction.EAST, null);
    // Pit pit3 = new Pit(40, 0.5f, 60, "wood", Direction.SOUTH, null);
    // Pit pit4 = new Pit(50, 0.5f, 60, "wood", Direction.WEST, null);
    //
    // Tunnel tunnel1 = new Tunnel(20, 0.5f, 70, "wood", Direction.NORTH, null);
    // Tunnel tunnel2 = new Tunnel(30, 0.5f, 70, "wood", Direction.EAST, null);
    // Tunnel tunnel3 = new Tunnel(40, 0.5f, 70, "wood", Direction.SOUTH, null);
    // Tunnel tunnel4 = new Tunnel(50, 0.5f, 70, "wood", Direction.WEST, null);
    //
    // Staircase stair1 = new Staircase(20, 0.5f, 80, "wood", Direction.NORTH,
    // null);
    // Staircase stair2 = new Staircase(30, 0.5f, 80, "wood", Direction.EAST,
    // null);
    // Staircase stair3 = new Staircase(40, 0.5f, 80, "wood", Direction.SOUTH,
    // null);
    // Staircase stair4 = new Staircase(50, 0.5f, 80, "wood", Direction.WEST,
    // null);
    //
    // Platform plat1 = new Platform(20, 0.5f, 90, "wood", null, null);
    //
    // Field field1 = new Field(20, 0.5f, 120, "wood", Direction.NORTH, null);
    // Field field2 = new Field(50, 0.5f, 120, "wood", Direction.EAST, null);
    // Field field3 = new Field(80, 0.5f, 120, "wood", Direction.SOUTH, null);
    // Field field4 = new Field(110, 0.5f, 120, "wood", Direction.WEST, null);

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
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

      // Draw the world map...
      for (MapObject mapObject : mapObjects)
      {
        mapObject.draw();
      }

      // Draw the world map...
      for (GameObject gameObject : gameObjects)
      {
        gameObject.draw();
      }

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
