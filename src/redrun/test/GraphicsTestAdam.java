package redrun.test;

import java.awt.Dimension;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;

import redrun.graphics.camera.Camera;
import redrun.graphics.camera.CameraManager;
import redrun.graphics.selection.Picker;
import redrun.model.constants.Direction;
import redrun.model.constants.Team;
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
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.trap.ExplodingBox;
import redrun.model.gameobject.trap.JailDoor;
import redrun.model.gameobject.trap.RockSmash;
import redrun.model.gameobject.trap.SpikeField;
import redrun.model.gameobject.trap.SpikeTrapDoor;
import redrun.model.gameobject.world.Ball;
import redrun.model.gameobject.world.Cube;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.Timing;
import redrun.model.constants.CameraType;
import redrun.model.gameobject.trap.*;
import redrun.model.gameobject.world.*;
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
  /** The camera associated with the client. */
  private static Camera camera = null;
  static CameraManager cameraManager = null;
  
  static Player player = null;

  /**
   * Performs OpenGL initialization.
   */
  
   
  private static void createOpenGL()
  {
    try
    {
      Display.setDisplayMode(new DisplayMode(1280, 720));
      //TODO - Need to have the name of the active map be in the title...
      Display.setTitle("RedRun Ice World");
      Display.create();
      Display.setVSyncEnabled(true);
    }
    catch (LWJGLException ex)
    {
      Logger.getLogger(GraphicsTestTroy.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    player = new Player(0.0f, 1.0f, 0.0f, "Linvala, Keeper of Silence", null, Team.BLUE);
    
    Camera spectatorCam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f, 1.0f, 0.0f, CameraType.SPECTATOR);
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
   * The main loop where the logic occurs. Stopped when the escape key is pressed or the window is closed.
   */
  private static void gameLoop()
  {    
    // Create the skybox...
    SkyBox skybox = new SkyBox(0, 0, 0, "blood_sport");
    
    // Create the ground...
    Plane plane = new Plane(0, -1.0f, 0, "flopyflopy2", Direction.EAST, 1000);
    
    // Create the map...
    LinkedList<MapObject> worldMap = new LinkedList<MapObject>();  
    
    worldMap.add(new Corridor(0.0f, 0f, 0.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(20.0f, 0.0f, 0.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(40.0f, 0.0f, 0.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Corridor(60.0f, 0.0f, 0.0f, "brickwall5", Direction.WEST, null));
    
    worldMap.add(new Corridor(0.0f, 0f, 30.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(20.0f, 0.0f, 30.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(40.0f, 0.0f, 30.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Corridor(60.0f, 0.0f, 30.0f, "brickwall5", Direction.WEST, null));
    
    worldMap.add(new Corridor(0.0f, 0f, 60.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(20.0f, 0.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(40.0f, 0.0f, 60.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Corridor(60.0f, 0.0f, 60.0f, "brickwall5", Direction.WEST, null));
    
    worldMap.add(new Corridor(0.0f, 0f, 90.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(20.0f, 0.0f, 90.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(40.0f, 0.0f, 90.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Corridor(60.0f, 0.0f, 90.0f, "brickwall5", Direction.WEST, null));   
    
    worldMap.add(new Pit(0.0f, 15.0f, 120.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Pit(20.0f, 15.0f, 120.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Pit(40.0f, 15.0f, 120.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Pit(60.0f, 15.0f, 120.0f, "brickwall5", Direction.WEST, null));
    
    worldMap.add(new Pit(0.0f, 15.0f, 150.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Pit(20.0f, 15.0f, 150.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Pit(40.0f, 15.0f, 150.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Pit(60.0f, 15.0f, 150.0f, "brickwall5", Direction.WEST, null));
    
    worldMap.add(new Pit(0.0f, 15.0f, 180.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Pit(20.0f, 15.0f, 180.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Pit(40.0f, 15.0f, 180.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Pit(60.0f, 15.0f, 180.0f, "brickwall5", Direction.WEST, null));
    
    
    worldMap.add(new Tunnel(0.0f, 0f, 210.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Tunnel(20.0f, 0.0f, 210.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Tunnel(40.0f, 0.0f, 210.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Tunnel(60.0f, 0.0f, 210.0f, "brickwall5", Direction.WEST, null));
    
    
    

    // Add game objects to test physics...
    LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
    
    gameObjects.add(new JailDoor(0.0f, 0.0f, 0.0f, Direction.EAST, null));
    gameObjects.add(new JailDoor(20.0f, 0.0f, 0.0f, Direction.NORTH, null));
    gameObjects.add(new JailDoor(40.0f, 0.0f, 0.0f, Direction.SOUTH, null));
    gameObjects.add(new JailDoor(60.0f, 0.0f, 0.0f, Direction.WEST, null));
    
    gameObjects.add(new RockSmash(0.0f, 2.0f, 30.0f, "rock1"));
    gameObjects.add(new RockSmash(20.0f, 2.0f, 30.0f, "rock2"));
    gameObjects.add(new RockSmash(40.0f, 2.0f, 30.0f,  "rock3"));
    gameObjects.add(new RockSmash(60.0f, 2.0f, 30.0f, "rock1"));
    
    gameObjects.add(new SpikeField(0.0f, 0.0f, 60.0f, "s11", new Dimension(10, 15), true));
    gameObjects.add(new SpikeField(20.0f, 0.0f, 60.0f, "s11", new Dimension(10, 15), true));
    gameObjects.add(new SpikeField(40.0f, 0.0f, 60.0f, "s11", new Dimension(10, 15), true));
    gameObjects.add(new SpikeField(60.0f, 0.0f, 60.0f, "s11", new Dimension(10, 15), true));
    
    gameObjects.add(new JailDoor(0.0f, 0.0f, 90.0f, Direction.EAST, null));
    gameObjects.add(new JailDoor(20.0f, 0.0f, 90.0f, Direction.NORTH, null));
    gameObjects.add(new JailDoor(40.0f, 0.0f, 90.0f, Direction.SOUTH, null));
    gameObjects.add(new JailDoor(60.0f, 0.0f, 90.0f, Direction.WEST, null));
    
    gameObjects.add(new SpikeField(0.0f, 15.0f, 120.0f, "s11", new Dimension(10, 15), false));
    gameObjects.add(new SpikeField(20.0f, 15.0f, 120.0f, "s11", new Dimension(10, 15), false));
    gameObjects.add(new SpikeField(40.0f, 15.0f, 120.0f, "s11", new Dimension(10, 15), false));
    gameObjects.add(new SpikeField(60.0f, 15.0f, 120.0f, "s11", new Dimension(10, 15), false));
    
    gameObjects.add(new JailDoor(0.0f, 0.0f, 150.0f, Direction.EAST, null));
    gameObjects.add(new JailDoor(20.0f, 0.0f, 150.0f, Direction.NORTH, null));
    gameObjects.add(new JailDoor(40.0f, 0.0f, 150.0f, Direction.SOUTH, null));
    gameObjects.add(new JailDoor(60.0f, 0.0f, 150.0f, Direction.WEST, null));
    
    gameObjects.add(new ExplodingBox(0.0f, 0.0f, 180.0f, Direction.EAST, null));
    gameObjects.add(new ExplodingBox(20.0f, 0.0f, 180.0f, Direction.NORTH, null));
    gameObjects.add(new ExplodingBox(40.0f, 0.0f, 180.0f, Direction.SOUTH, null));
    gameObjects.add(new ExplodingBox(60.0f, 0.0f, 180.0f, Direction.WEST, null));
    
    gameObjects.add(new JailDoor(0.0f, 0.0f, 210.0f, Direction.EAST, null));
    gameObjects.add(new JailDoor(20.0f, 0.0f, 210.0f, Direction.NORTH, null));
    gameObjects.add(new JailDoor(40.0f, 0.0f, 210.0f, Direction.SOUTH, null));
    gameObjects.add(new JailDoor(60.0f, 0.0f, 210.0f, Direction.WEST, null));
    
    
    // Hide the mouse cursor...
    Mouse.setGrabbed(true);
    
    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      camera = cameraManager.getActiveCamera();
      getInput();
      
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glLoadIdentity();
      
      glPushMatrix();
      {
        glDepthMask(false);
        glRotatef(camera.getPitch(), 1.0f, 0.0f, 0.0f);
        glRotatef(camera.getYaw(), 0.0f, 1.0f, 0.0f);
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
      
      // Draw the floor...
      plane.draw();
      
      // Draw the map objects...
      for (MapObject mapObject : worldMap)
      {
        mapObject.draw();
      }
      
      // Draw the game objects...
      for (GameObject gameObject : gameObjects)
      {
      	gameObject.draw();
      }
            
      // Draw text to the screen...
      FontTools.draw2D();
      FontTools.renderText("Position: (" + camera.getX() + ", " + camera.getY() + ", " + camera.getZ() + ")", 10, 10, Color.orange, 1);
      FontTools.draw3D();
      
      PhysicsWorld.stepSimulation(1 / 60.0f);
      cameraManager.update();
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
    
    // Move at different speeds...
    if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) camera.moveForward(movementSpeed * dt * 2);
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
