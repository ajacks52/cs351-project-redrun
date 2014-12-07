package redrun.main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_LOCAL_VIEWER;
import static org.lwjgl.opengl.GL11.GL_NORMALIZE;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLightModeli;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPopName;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPushName;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Timer;

import redrun.database.Map;
import redrun.graphics.camera.Camera;
import redrun.graphics.camera.CameraManager;
import redrun.graphics.camera.HUD_Manager;
import redrun.graphics.selection.Picker;
import redrun.main.Menu.MenuState;
import redrun.model.constants.CameraType;
import redrun.model.constants.GameState;
import redrun.model.constants.Team;
import redrun.model.game.GameData;
import redrun.model.game.ObjectFromDB;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.world.Ball;
import redrun.model.gameobject.world.Cube;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.Timing;
import redrun.network.Client;
import redrun.network.UserInput;

/**
 * This class is where RedRun begins execution.
 * 
 * @author Troy Squillaci, Jake Nichol, Jayson Grace, Adam Michell, Jordan
 *         Medlock
 * @version 1.0
 * @since 2014-11-09
 */
public class Main
{
  private static boolean running = true;

  /** The game menu. */
  private static Menu menu = new Menu();

  /** The current game state for this client and its player. */
  private static GameState state = GameState.WAIT;

  /** The current game state for this client and its player. */
  private static GameState previousState = GameState.WAIT;

  /** The active camera manager. */
  private static CameraManager cameraManager = null;

  /** The active camera. */
  private static Camera camera = null;

  /** The player associated with the client. */
  private static Player player = null;

  /** Used to interface with the network client. */
  private static Client client = null;

  /** Used to access the database. */
  private static Map map = null;

  private static UserInput input = null;

  /**
   * Performs OpenGL initialization.
   */
  private static void createResources()
  {
    // Connect to the server...
    client = new Client("127.0.0.1", 7777);

    // Set up the user input...
    input = new UserInput();

    // Set up OpenGL...
    try
    {
      Display.setDisplayMode(new DisplayMode(1280, 720));
      // TODO - Need to have the name of the active map be in the title...
      Display.setTitle("RedRun Ice World");
      Display.create();
      Display.setVSyncEnabled(true);
    }
    catch (LWJGLException ex)
    {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Set OpenGL states...
    glEnable(GL_DEPTH_TEST);
    glDisable(GL_COLOR_MATERIAL);
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_NORMALIZE);
    glShadeModel(GL_SMOOTH);

    // TODO Need to get the player data from the server...
    // Set up the cameras...
    player = new Player(0.0f, 1.0f, 0.0f, "Linvala, Keeper of Silence", null, Team.BLUE);

    Camera spectatorCam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f,
        1.0f, 0.0f, CameraType.SPECTATOR);
    Camera playerCam = player.getCamera();

    cameraManager = new CameraManager(spectatorCam, playerCam);

    // Show loading screen...
    LoadingScreen.loadingScreen();
  }

  /**
   * The main loop where the logic occurs. Stopped when the escape key is
   * pressed or the window is closed.
   */
  private static void gameLoop()
  {
    // Create the skybox...
    SkyBox skybox = null;

    // Create the floor...
    Plane floor = null;

    while (!GameData.networkData.isEmpty())
    {
      for (String networkItem : GameData.networkData)
      {
        if (!ObjectFromDB.mapDrawn)
        {
          map = ObjectFromDB.createMap(networkItem);
          if (!(map == null))
          {
            skybox = ObjectFromDB.createSkybox(map.getSkyBox());
            floor = ObjectFromDB.createFloor(map.getFloor());
          }
        }
        else
        {
          MapObject object = ObjectFromDB.createMapObject(networkItem);
          if (!GameData.mapObjects.contains(object))
          {
            GameData.mapObjects.add(ObjectFromDB.createMapObject(networkItem));
          }
        }
      }
      break;
    }

    GameData.bindConnections();

    // Create cubes above the staircase...
    for (int i = 0; i < 500; i++)
    {
      GameData.addGameObject(new Cube(45.0f, 50.0f + (2 * i), 45.0f, "crate1"));
    }

    // Create balls above the staircase...
    for (int i = 0; i < 10; i++)
    {
      // GameData.addGameObject(new Ball(45.0f, 50.0f + (5 * i), 15.0f,
      // "crate1", 1.5f));
    }

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);

    while (!Display.isCloseRequested() && running)
    {
      camera = cameraManager.getActiveCamera();

      // Get input from the user...
      getInput();

      // Prepare for rendering...
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glLoadIdentity();

      // Draw the skybox...
      glPushMatrix();
      {
        glDepthMask(false);
        glRotatef(camera.getPitch(), 1.0f, 0.0f, 0.0f);
        glRotatef(camera.getYaw(), 0.0f, 1.0f, 0.0f);
        skybox.draw();
        glDepthMask(true);
      }
      glPopMatrix();

      // Orient the camera...
      camera.lookThrough();

      // Global Ambient Light Model...
      FloatBuffer ambientColor = BufferConverter.asFloatBuffer(new float[] { 0.2f, 0.2f, 0.2f, 1.0f });
      glLightModel(GL_LIGHT_MODEL_AMBIENT, ambientColor);

      // Local Viewport Model...
      glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);

      // Add positional light...
      FloatBuffer lightDiffuse = BufferConverter.asFloatBuffer(new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
      FloatBuffer lightSpecular = BufferConverter.asFloatBuffer(new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
      FloatBuffer lightPosition = BufferConverter.asFloatBuffer(new float[] { -100.0f, 750.0f, 1000.0f, 1.0f });

      // glLight(GL_LIGHT0, GL_AMBIENT, lightAmibent);
      glLight(GL_LIGHT0, GL_DIFFUSE, lightDiffuse);
      glLight(GL_LIGHT0, GL_SPECULAR, lightSpecular);
      glLight(GL_LIGHT0, GL_POSITION, lightPosition);

      // Picking code for 3D selection of game objects...
      if (Keyboard.isKeyDown(Keyboard.KEY_F))
      {
        Picker.startPicking();
        {
          // Draw the game objects...
          for (GameObject gameObject : GameData.getGameObjects())
          {
            glPushName(gameObject.id);
            {
              gameObject.draw();
            }
            glPopName();
          }
        }
        Picker.stopPicking();
      }

      // Draw the player...
      player.draw();

      // Draw the floor...
      floor.draw();

      // Draw the map objects...
      for (MapObject mapObject : GameData.getMapObjects())
      {
        mapObject.draw();
      }

      // Draw the game objects...
      for (GameObject gameObject : GameData.getGameObjects())
      {
        gameObject.draw();
      }

      // Show the HUD...
      if (state != GameState.MAIN_MENU) HUD_Manager.huds(camera, player);
      // Show the menu...
      if (state == GameState.MAIN_MENU) menu.stateControl();
      cameraManager.update();
      PhysicsWorld.stepSimulation(1 / 60.0f);
      Timer.tick();
      Display.update();
      Display.sync(60);

      if (ObjectFromDB.mapDrawn == true)
      {
        GameData.networkData.clear();
        client.sendPlayer(player);
      }
    }
  }

  /**
   * Gets user input from the keyboard and mouse.
   */
  private static void getInput()
  {
    // Menu control...
    if (menu.getState() == MenuState.OFF) state = previousState;
    if (state == GameState.MAIN_MENU) return; // Take no input if menu is up.
    if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      if (state != GameState.MAIN_MENU) previousState = state;
      state = GameState.MAIN_MENU;
      menu.setState();
    }

    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;

    // Set the mouse sensitivity...
    float mouseSensitivity = 0.08f;
    float movementSpeed = 0.02f;

    dx = Mouse.getDX();
    dy = Mouse.getDY();
    dt = Timing.getDelta();

    camera.yaw(dx * mouseSensitivity);
    camera.pitch(-dy * mouseSensitivity);

    // Camera related input...
    if (Keyboard.isKeyDown(Keyboard.KEY_R))
    {
      cameraManager.chooseNextCamera();
    }

    // Movement related input...
    if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
    {
      camera.moveForward(movementSpeed * dt * 2);
    }
    else if (Keyboard.isKeyDown(Keyboard.KEY_W)) camera.moveForward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_S)) camera.moveBackward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_A)) camera.moveLeft(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_D)) camera.moveRight(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) camera.moveUp(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) camera.moveDown(movementSpeed * dt);
  }

  /**
   * Cleans up resources.
   */
  private static void destroyResources()
  {
    Display.destroy();
    client.requestDisconnect();
  }

  /**
   * Breaks out of the game loop which leads to the program exiting.
   */
  public static void requestClose()
  {
    running = false;
  }

  public static void main(String[] args)
  {
    createResources();
    gameLoop();
    destroyResources();
  }
}