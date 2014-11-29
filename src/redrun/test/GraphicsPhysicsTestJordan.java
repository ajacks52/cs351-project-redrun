package redrun.test;

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
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.world.Cube;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.Timing;
import static org.lwjgl.opengl.GL11.*;

/**
 * This class is for testing OpenGL scenes.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class GraphicsPhysicsTestJordan
{
  /** The camera associated with the client. */
  private static Player player = null;

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
      Logger.getLogger(GraphicsPhysicsTestJordan.class.getName()).log(Level.SEVERE, null, ex);
    }
    
//    camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f, 1.0f, 0.0f);
    player = new Player(0.0f, 10.0f, 0.0f, "Jordan", "24", null);
    
    
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
    
    // Add the starting point...
    worldMap.add(new Start(0.0f, 0.0f, 0.0f, "brickwall5", Direction.WEST, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(0.0f, 0.0f, 15.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(0.0f, 0.0f, 30.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(0.0f, 0.0f, 45.0f, "brickwall5", Direction.EAST, null));
    
    // Add a corner...
    worldMap.add(new Corner(0.0f, 0.0f, 60.0f, "brickwall5", Direction.SOUTH, null));
    
    // Add a staircase...
    worldMap.add(new Staircase(15.0f, 0.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(30.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(45.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add a field...
    worldMap.add(new Field(75.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(105.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(120.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add a tunnel...
    worldMap.add(new Tunnel(135.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Tunnel(150.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(165.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add a pit...
    worldMap.add(new Pit(180.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(195.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add a platform...
    worldMap.add(new Platform(210.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add the ending point...
    worldMap.add(new End(225.0f, 15.0f, 60.0f, "brickwall5", Direction.NORTH, null));
    
    // Add game objects to test physics...
    LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();

    // Add cubes at start...
    gameObjects.add(new Cube(0.0f, 10.0f, 0.0f, "wood"));
    gameObjects.add(new Cube(0.0f, 20.0f, 0.0f, "wood"));
    gameObjects.add(new Cube(1.0f, 30.0f, 0.0f, "wood"));
    gameObjects.add(new Cube(-0.75f, 40.0f, 0.0f, "wood"));
    
    gameObjects.add(new Cube(7.75f, 50.0f, 0.0f, "wood"));

    
    // Add cubes at staircase...
    gameObjects.add(new Cube(15.0f, 50.0f, 60.0f, "wood"));
    
    // Add balls at staircase...
//    for (int i = 0; i < 10; i++)
//    {
//    	gameObjects.add(new Ball(15.0f, 10.0f + i, 60.0f, "wood", 1.5f));
//    }
  
//    //TODO - Testing for map object orientations...
//    worldMap.add(new Corridor(0.0f, 0.0f, 0.0f, "wood", Direction.NORTH, null));
//    worldMap.add(new Corridor(20.0f, 0.0f, 0.0f, "wood", Direction.EAST, null));
//    worldMap.add(new Corridor(40.0f, 0.0f, 0.0f, "wood", Direction.SOUTH, null));
//    worldMap.add(new Corridor(60.0f, 0.0f, 0.0f, "wood", Direction.WEST, null));
//    
//    worldMap.add(new Corner(0.0f, 0.0f, 20.0f, "wood", Direction.NORTH, null));
//    worldMap.add(new Corner(20.0f, 0.0f, 20.0f, "wood", Direction.EAST, null));
//    worldMap.add(new Corner(40.0f, 0.0f, 20.0f, "wood", Direction.SOUTH, null));
//    worldMap.add(new Corner(60.0f, 0.0f, 20.0f, "wood", Direction.WEST, null));
//
//    worldMap.add(new End(0.0f, 0.0f, 40.0f, "wood", Direction.NORTH, null));
//    worldMap.add(new End(20.0f, 0.0f, 40.0f, "wood", Direction.EAST, null));
//    worldMap.add(new End(40.0f, 0.0f, 40.0f, "wood", Direction.SOUTH, null));
//    worldMap.add(new End(60.0f, 0.0f, 40.0f, "wood", Direction.WEST, null));
//    
//    worldMap.add(new Start(0.0f, 0.0f, 60.0f, "wood", Direction.NORTH, null));
//    worldMap.add(new Start(20.0f, 0.0f, 60.0f, "wood", Direction.EAST, null));
//    worldMap.add(new Start(40.0f, 0.0f, 60.0f, "wood", Direction.SOUTH, null));
//    worldMap.add(new Start(60.0f, 0.0f, 60.0f, "wood", Direction.WEST, null));
//    
//    worldMap.add(new Pit(0.0f, 0.0f, 80.0f, "wood", Direction.NORTH, null));
//    worldMap.add(new Pit(20.0f, 0.0f, 80.0f, "wood", Direction.EAST, null));
//    worldMap.add(new Pit(40.0f, 0.0f, 80.0f, "wood", Direction.SOUTH, null));
//    worldMap.add(new Pit(60.0f, 0.0f, 80.0f, "wood", Direction.WEST, null));
//    
//    worldMap.add(new Tunnel(0.0f, 0.0f, 100.0f, "wood", Direction.NORTH, null));
//    worldMap.add(new Tunnel(20.0f, 0.0f, 100.0f, "wood", Direction.EAST, null));
//    worldMap.add(new Tunnel(40.0f, 0.0f, 100.0f, "wood", Direction.SOUTH, null));
//    worldMap.add(new Tunnel(60.0f, 0.0f, 100.0f, "wood", Direction.WEST, null));
//    
//    worldMap.add(new Staircase(0.0f, 0.0f, 120.0f, "wood", Direction.NORTH, null));
//    worldMap.add(new Staircase(20.0f, 0.0f, 120.0f, "wood", Direction.EAST, null));
//    worldMap.add(new Staircase(40.0f, 0.0f, 120.0f, "wood", Direction.SOUTH, null));
//    worldMap.add(new Staircase(60.0f, 0.0f, 120.0f, "wood", Direction.WEST, null));
//     
//    worldMap.add(new Platform(0.0f, 0.0f, 140.0f, "wood", null, null));
//    
//    worldMap.add(new Field(0.0f, 0.0f, 180.0f, "wood", Direction.NORTH, null));
//    worldMap.add(new Field(50.0f, 0.0f, 180.0f, "wood", Direction.EAST, null));
//    worldMap.add(new Field(100.0f, 0.0f, 180.0f, "wood", Direction.SOUTH, null));
//    worldMap.add(new Field(150.0f, 0.0f, 180.0f, "wood", Direction.WEST, null));
    

    
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
        glRotatef(player.getBody().getPitch(), 1.0f, 0.0f, 0.0f);
        glRotatef(player.getBody().getYaw(), 0.0f, 1.0f, 0.0f);
        skybox.draw();
        glDepthMask(true);
      }
      glPopMatrix();
      
      player.lookThrough();

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
      FontTools.renderText("Position: (" + player.getX() + ", " + player.getY() + ", " + player.getZ() + ")", 10, 10, Color.orange, 1);
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
    dt = Timing.getDelta();
    
    player.yaw(dx * mouseSensitivity);
    player.pitch(-dy * mouseSensitivity);
    
    // Move at different speeds...
    if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) player.walkForward(movementSpeed * dt * 2);
    else if (Keyboard.isKeyDown(Keyboard.KEY_W)) player.walkForward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_S)) player.walkBackward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_A)) player.walkLeft(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_D)) player.walkRight(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) player.jump();
//    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) camera.moveDown(movementSpeed * dt);
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
    GraphicsPhysicsTestJordan.createOpenGL();
    GraphicsPhysicsTestJordan.gameLoop();
    GraphicsPhysicsTestJordan.destroyOpenGL();
  }
}
