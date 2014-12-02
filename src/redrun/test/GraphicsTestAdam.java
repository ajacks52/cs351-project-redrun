package redrun.test;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;

import redrun.graphics.camera.*;
import redrun.graphics.selection.Picker;
import redrun.main.Menu;
import redrun.model.constants.*;
import redrun.model.game.GameData;
import redrun.model.gameobject.*;
import redrun.model.gameobject.map.*;
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.world.*;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * This class is for testing OpenGL scenes.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class GraphicsTestAdam
{
  /** The active camera manager. */
  private static CameraManager cameraManager = null;

  /** The active camera. */
  private static Camera camera = null;

  /** The player associated with the client. */
  private static Player player = null;

  /** The list of map objects and game objects. */
  // public static GameData data = new GameData();

  /**
   * Performs OpenGL initialization.
   */
  private static void createOpenGL()
  {
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
      Logger.getLogger(GraphicsTestAdam.class.getName()).log(Level.SEVERE, null, ex);
    }

    player = new Player(0.0f, 1.0f, 0.0f, "Linvala, Keeper of Silence", null, Team.BLUE);

    Camera spectatorCam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f,
        0.0f, 0.0f, CameraType.SPECTATOR);
    Camera playerCam = player.getCamera();

    cameraManager = new CameraManager(spectatorCam, playerCam);

    glEnable(GL_DEPTH_TEST);
    glDisable(GL_COLOR_MATERIAL);
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_LIGHT1);
    glEnable(GL_NORMALIZE);
    glShadeModel(GL_SMOOTH);

    FontTools.loadFonts();
  }

  /**
   * The main loop where the logic occurs. Stopped when the escape key is
   * pressed or the window is closed.
   */
  private static void gameLoop()
  {
    // Create the map objects...

    GameData.addMapObject(new Corridor(0.0f, 0f, 0.0f, "brickwall5", Direction.EAST, TrapType.POLE_DANCE));
    GameData.addMapObject(new Corridor(20.0f, 0.0f, 0.0f, "brickwall5", Direction.NORTH, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Corridor(40.0f, 0.0f, 0.0f, "brickwall5", Direction.SOUTH, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Corridor(60.0f, 0.0f, 0.0f, "brickwall5", Direction.WEST, TrapType.SPIKE_FIELD));

    GameData.addMapObject(new Corridor(0.0f, 0f, 30.0f, "brickwall5", Direction.EAST, TrapType.ROCK_SMASH));
    GameData.addMapObject(new Corridor(20.0f, 0.0f, 30.0f, "brickwall5", Direction.NORTH, TrapType.ROCK_SMASH));
    GameData.addMapObject(new Corridor(40.0f, 0.0f, 30.0f, "brickwall5", Direction.SOUTH, TrapType.ROCK_SMASH));
    GameData.addMapObject(new Corridor(60.0f, 0.0f, 30.0f, "brickwall5", Direction.WEST, TrapType.ROCK_SMASH));

    GameData.addMapObject(new Corridor(0.0f, 0f, 60.0f, "brickwall5", Direction.EAST, TrapType.EXPLODING_BOX_FIELD));
    GameData
        .addMapObject(new Corridor(20.0f, 0.0f, 60.0f, "brickwall5", Direction.NORTH, TrapType.EXPLODING_BOX_FIELD));
    GameData
        .addMapObject(new Corridor(40.0f, 0.0f, 60.0f, "brickwall5", Direction.SOUTH, TrapType.EXPLODING_BOX_FIELD));
    GameData.addMapObject(new Corridor(60.0f, 0.0f, 60.0f, "brickwall5", Direction.WEST, TrapType.EXPLODING_BOX_FIELD));

    GameData.addMapObject(new Corridor(0.0f, 0f, 90.0f, "brickwall5", Direction.EAST, TrapType.DEATH_PILLAR));
    GameData.addMapObject(new Corridor(20.0f, 0.0f, 90.0f, "brickwall5", Direction.NORTH, TrapType.DEATH_PILLAR));
    GameData.addMapObject(new Corridor(40.0f, 0.0f, 90.0f, "brickwall5", Direction.SOUTH, TrapType.DEATH_PILLAR));
    GameData.addMapObject(new Corridor(60.0f, 0.0f, 90.0f, "brickwall5", Direction.WEST, TrapType.DEATH_PILLAR));

    GameData.addMapObject(new Corridor(0.0f, 15.0f, 120.0f, "brickwall5", Direction.EAST, TrapType.POLE_DANCE));
    GameData.addMapObject(new Corridor(20.0f, 15.0f, 120.0f, "brickwall5", Direction.NORTH, TrapType.POLE_DANCE));
    GameData.addMapObject(new Corridor(40.0f, 15.0f, 120.0f, "brickwall5", Direction.SOUTH, TrapType.POLE_DANCE));
    GameData.addMapObject(new Corridor(60.0f, 15.0f, 120.0f, "brickwall5", Direction.WEST, TrapType.POLE_DANCE));

    GameData.addMapObject(new Corridor(0.0f, 15.0f, 150.0f, "brickwall5", Direction.EAST, TrapType.SPIKE_TRAP_DOOR));
    GameData.addMapObject(new Corridor(20.0f, 15.0f, 150.0f, "brickwall5", Direction.NORTH, TrapType.SPIKE_TRAP_DOOR));
    GameData.addMapObject(new Corridor(40.0f, 15.0f, 150.0f, "brickwall5", Direction.SOUTH, TrapType.SPIKE_TRAP_DOOR));
    GameData.addMapObject(new Corridor(60.0f, 15.0f, 150.0f, "brickwall5", Direction.WEST, TrapType.SPIKE_TRAP_DOOR));

    GameData.addMapObject(new Tunnel(0.0f, 15.0f, 180.0f, "brickwall5", Direction.EAST, TrapType.POLE_WALL));
    GameData.addMapObject(new Tunnel(20.0f, 15.0f, 180.0f, "brickwall5", Direction.NORTH, TrapType.POLE_WALL));
    GameData.addMapObject(new Tunnel(40.0f, 15.0f, 180.0f, "brickwall5", Direction.SOUTH, TrapType.POLE_WALL));
    GameData.addMapObject(new Tunnel(60.0f, 15.0f, 180.0f, "brickwall5", Direction.WEST, TrapType.POLE_WALL));

    GameData.addMapObject(new Pit(0.0f, 15f, 210.0f, "brickwall5", Direction.EAST, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Pit(20.0f, 15.0f, 210.0f, "brickwall5", Direction.NORTH, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Pit(40.0f, 15.0f, 210.0f, "brickwall5", Direction.SOUTH, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Pit(60.0f, 15.0f, 210.0f, "brickwall5", Direction.WEST, TrapType.SPIKE_FIELD));

    // next rows
    // GameData.addMapObject(new Corridor(80.0f, 0f, 0.0f, "brickwall5",
    // Direction.EAST, TrapType.SPIKE_FIELD));
    // GameData.addMapObject(new Corridor(100.0f, 0.0f, 0.0f, "brickwall5",
    // Direction.NORTH, TrapType.SPIKE_FIELD));
    // GameData.addMapObject(new Corridor(120.0f, 0.0f, 0.0f, "brickwall5",
    // Direction.SOUTH, TrapType.SPIKE_FIELD));
    // GameData.addMapObject(new Corridor(140.0f, 0.0f, 0.0f, "brickwall5",
    // Direction.WEST, TrapType.SPIKE_FIELD));
    //
    // GameData.addMapObject(new Corridor(80.0f, 0f, 30.0f, "brickwall5",
    // Direction.EAST, TrapType.ROCK_SMASH));
    // GameData.addMapObject(new Corridor(100.0f, 0.0f, 30.0f, "brickwall5",
    // Direction.NORTH, TrapType.ROCK_SMASH));
    // GameData.addMapObject(new Corridor(120.0f, 0.0f, 30.0f, "brickwall5",
    // Direction.SOUTH, TrapType.ROCK_SMASH));
    // GameData.addMapObject(new Corridor(140.0f, 0.0f, 30.0f, "brickwall5",
    // Direction.WEST, TrapType.ROCK_SMASH));
    //
    // GameData.addMapObject(new Corridor(80.0f, 0f, 60.0f, "brickwall5",
    // Direction.EAST, TrapType.EMPTY));
    // GameData.addMapObject(new Corridor(100.0f, 0.0f, 60.0f, "brickwall5",
    // Direction.NORTH, TrapType.EMPTY));
    // GameData.addMapObject(new Corridor(120.0f, 0.0f, 60.0f, "brickwall5",
    // Direction.SOUTH, TrapType.EMPTY));
    // GameData.addMapObject(new Corridor(140.0f, 0.0f, 60.0f, "brickwall5",
    // Direction.WEST, TrapType.EMPTY));
    //
    // GameData.addMapObject(new Corridor(80.0f, 0f, 90.0f, "brickwall5",
    // Direction.EAST, TrapType.DEATH_PILLAR));
    // GameData.addMapObject(new Corridor(100.0f, 0.0f, 90.0f, "brickwall5",
    // Direction.NORTH, TrapType.DEATH_PILLAR));
    // GameData.addMapObject(new Corridor(120.0f, 0.0f, 90.0f, "brickwall5",
    // Direction.SOUTH, TrapType.DEATH_PILLAR));
    // GameData.addMapObject(new Corridor(140.0f, 0.0f, 90.0f, "brickwall5",
    // Direction.WEST, TrapType.DEATH_PILLAR));
    //
    // GameData.addMapObject(new Corridor(80.0f, 15.0f, 120.0f, "brickwall5",
    // Direction.EAST, TrapType.SPIKE_FIELD));
    // GameData.addMapObject(new Corridor(100.0f, 15.0f, 120.0f, "brickwall5",
    // Direction.NORTH, TrapType.SPIKE_FIELD));
    // GameData.addMapObject(new Corridor(120.0f, 15.0f, 120.0f, "brickwall5",
    // Direction.SOUTH, TrapType.SPIKE_FIELD));
    // GameData.addMapObject(new Pit(140.0f, 15.0f, 120.0f, "brickwall5",
    // Direction.WEST, TrapType.SPIKE_FIELD));
    //
    // GameData.addMapObject(new Corridor(80.0f, 15.0f, 150.0f, "brickwall5",
    // Direction.EAST, TrapType.EMPTY));
    // GameData.addMapObject(new Corridor(100.0f, 15.0f, 150.0f, "brickwall5",
    // Direction.NORTH, TrapType.EMPTY));
    // GameData.addMapObject(new Corridor(120.0f, 15.0f, 150.0f, "brickwall5",
    // Direction.SOUTH, TrapType.EMPTY));
    // GameData.addMapObject(new Corridor(140.0f, 15.0f, 150.0f, "brickwall5",
    // Direction.WEST, TrapType.EMPTY));
    //
    // GameData.addMapObject(new Tunnel(80.0f, 15.0f, 180.0f, "brickwall5",
    // Direction.EAST, TrapType.EMPTY));
    // GameData.addMapObject(new Tunnel(100.0f, 15.0f, 180.0f, "brickwall5",
    // Direction.NORTH, TrapType.EMPTY));
    // GameData.addMapObject(new Tunnel(120.0f, 15.0f, 180.0f, "brickwall5",
    // Direction.SOUTH, TrapType.EMPTY));
    // GameData.addMapObject(new Tunnel(140.0f, 15.0f, 180.0f, "brickwall5",
    // Direction.WEST, TrapType.EMPTY));
    //
    // GameData.addMapObject(new Pit(80.0f, 0f, 210.0f, "brickwall5",
    // Direction.EAST, TrapType.EMPTY));
    // GameData.addMapObject(new Pit(100.0f, 0.0f, 210.0f, "brickwall5",
    // Direction.NORTH, TrapType.EMPTY));
    // GameData.addMapObject(new Pit(120.0f, 0.0f, 210.0f, "brickwall5",
    // Direction.SOUTH, TrapType.EMPTY));
    // GameData.addMapObject(new Pit(140.0f, 0.0f, 210.0f, "brickwall5",
    // Direction.WEST, TrapType.EMPTY));

    // Create the game objects...

    // Create the player...

    // Create the skybox...
    SkyBox skybox = new SkyBox(0, 0, 0, "iceflats");

    // Create the floor...
    Plane floor = new Plane(0, -1.0f, 0, "snow", Direction.EAST, 1000);

    // Create cubes above the staircase...
    for (int i = 0; i < 5; i++)
    {

      GameData.addGameObject(new Cube(20.0f, 50.0f + (2 * i), 0.0f, "crate1"));
      GameData.addGameObject(new Cube(40.0f, 50.0f + (2 * i), 0.0f, "crate1"));
      GameData.addGameObject(new Cube(60.0f, 50.0f + (2 * i), 0.0f, "crate1"));

      GameData.addGameObject(new Cube(0.0f, 50.0f + (2 * i), 210.0f, "crate1"));
      GameData.addGameObject(new Cube(20.0f, 50.0f + (2 * i), 210.0f, "crate1"));
      GameData.addGameObject(new Cube(40.0f, 50.0f + (2 * i), 210.0f, "crate1"));
      GameData.addGameObject(new Cube(60.0f, 50.0f + (2 * i), 210.0f, "crate1"));

      GameData.addGameObject(new Cube(2.0f, 20.0f, 175.0f + (2 * i), "crate1"));
      GameData.addGameObject(new Cube(17.0f + (2 * i), 20.0f, 180.0f, "crate1"));
      GameData.addGameObject(new Cube(37.0f + (2 * i), 20.0f, 180.0f, "crate1"));
      GameData.addGameObject(new Cube(62.0f, 20.0f, 175.0f + (2 * i), "crate1"));

      GameData.addGameObject(new Cube(0.0f, 25.0f + (2 * i), 0.0f, "crate1"));
      GameData.addGameObject(new Cube(3.0f, 50.0f + (2 * i), 3.0f, "crate1"));
      GameData.addGameObject(new Cube(-3.0f, 75.0f + (2 * i), -3.0f, "crate1"));
      GameData.addGameObject(new Cube(-3.0f, 100.0f + (2 * i), 3.0f, "crate1"));
      GameData.addGameObject(new Cube(3.0f, 125.0f + (2 * i), -3.0f, "crate1"));

    }

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);
    
    Menu menu = new Menu();

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
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

      // Draw text to the screen...
      FontTools.draw2D();
      if (camera.getType() == CameraType.SPECTATOR)
      {
        FontTools.renderText("Spectator Camera: (" + camera.getX() + ", " + camera.getY() + ", " + camera.getZ() + ")",
            10, 10, Color.black, 1);
      }
      else
      {
        FontTools.renderText("Player: " + player.getName(), 10, 10, Color.black, 1);
        FontTools.renderText("Team: " + player.getTeam(), 10, 30, Color.black, 1);
        FontTools.renderText("Lives: " + player.getLives(), 10, 50, Color.black, 1);
        FontTools.renderText("Player Camera: (" + player.getCamera().getX() + ", " + player.getCamera().getY() + ", "
            + player.getCamera().getZ() + ")", 10, 70, Color.black, 1);
      }
      FontTools.draw3D();
      
      menu.stateControl();

      // Update...
      cameraManager.update();
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
    dt = Timing.getDelta();

    camera.yaw(dx * mouseSensitivity);
    camera.pitch(-dy * mouseSensitivity);

    // Camera related input...
    if (Keyboard.isKeyDown(Keyboard.KEY_R))
    {
      cameraManager.chooseNextCamera();
    }

    if (Keyboard.isKeyDown(Keyboard.KEY_X))
    {
      // GameData.getGameObjects()
      for (GameObject gameObject : GameData.getGameObjects())
      {
        gameObject.interact();
      }
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
    if (Keyboard.isKeyDown(Keyboard.KEY_UP)) camera.moveUp(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) camera.moveDown(movementSpeed * dt);

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
    GraphicsTestAdam.createOpenGL();
    GraphicsTestAdam.gameLoop();
    GraphicsTestAdam.destroyOpenGL();
  }
}
