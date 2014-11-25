package redrun.test;

import java.nio.FloatBuffer;
import java.util.ArrayList;
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
import redrun.model.gameobject.world.Ball;
import redrun.model.gameobject.world.Cube;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.Tools;
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
  //TODO Move this shit.
  private static long lastFrame;
  
  /** The camera associated with the client. */
  private static Camera camera = null;

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
    
    FontTools.loadFonts(15);
  }

  /**
   * The main loop where the logic occurs. Stopped when the escape key is pressed or the window is closed.
   */
  private static void gameLoop()
  {    
    // Create the skybox...
    SkyBox skybox = new SkyBox(0, 0, 0, "blood_sport", camera);
    
    // Create the ground...
    Plane plane = new Plane(0, 0, 0, "flopy12", 1000);
    
    // Create the map...
    LinkedList<MapObject> worldMap = new LinkedList<MapObject>();
    
    //TODO Tests...
    worldMap.add(new Staircase(0.0f, 0.0f, 0.0f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Staircase(0.0f, 0.0f, 15.0f, "brickwall5", Direction.EAST, null));
    worldMap.add(new Staircase(0.0f, 0.0f, 30.0f, "brickwall5", Direction.SOUTH, null));
    worldMap.add(new Staircase(0.0f, 0.0f, 45.0f, "brickwall5", Direction.WEST, null));
    
    // Add the starting point...
    worldMap.add(new Start(20, 0.0f, 20, "brickwall5", Direction.NORTH, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(20, 0.0f, 27.5f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(20, 0.0f, 35, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(20, 0.0f, 42.5f, "brickwall5", Direction.NORTH, null));
    
    // Add a corner...
    worldMap.add(new Corner(20, 0.0f, 50, "brickwall5", Direction.NORTH, null));
    
    // Add a staircase...
    worldMap.add(new Staircase(27.5f, 0.0f, 50, "brickwall5", Direction.EAST, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(35, 7.5f, 50, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(42.5f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    // Add a field...
    worldMap.add(new Field(57.5f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(72.5f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(80, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    // Add a tunnel...
    worldMap.add(new Tunnel(87.5f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    worldMap.add(new Tunnel(95, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(102.5f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    // Add a pit...
    worldMap.add(new Pit(110.0f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    // Add a walkway...
    worldMap.add(new Corridor(117.5f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    // Add a platform...
    worldMap.add(new Platform(125.0f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    // Add the ending point...
    worldMap.add(new End(132.5f, 7.5f, 50, "brickwall5", Direction.EAST, null));
    
    ArrayList<Ball> balls = new ArrayList<Ball>();
    
    for (int i = 0; i < 10; i++)
    {
      balls.add(new Ball(0.0f, i + 1000.0f, 15.0f + (i * 0.1f), null, 0.5f, null));
    }
    
    Cube testCube = new Cube(0.0f, 50.0f, -8.0f, null);
    
//    Corridor corridor1 = new Corridor(20, 0.5f, 20, "wood", Direction.NORTH, null);
//    Corridor corridor2 = new Corridor(30, 0.5f, 20, "wood", Direction.EAST, null);
//    Corridor corridor3 = new Corridor(40, 0.5f, 20, "wood", Direction.SOUTH, null);
//    Corridor corridor4 = new Corridor(50, 0.5f, 20, "wood", Direction.WEST, null);
//    
//    Corner corner1 = new Corner(20, 0.5f, 30, "wood", Direction.NORTH, null);
//    Corner corner2 = new Corner(30, 0.5f, 30, "wood", Direction.EAST, null);
//    Corner corner3 = new Corner(40, 0.5f, 30, "wood", Direction.SOUTH, null);
//    Corner corner4 = new Corner(50, 0.5f, 30, "wood", Direction.WEST, null);
//
//    End end1 = new End(20, 0.5f, 40, "wood", Direction.NORTH, null);
//    End end2 = new End(30, 0.5f, 40, "wood", Direction.EAST, null);
//    End end3 = new End(40, 0.5f, 40, "wood", Direction.SOUTH, null);
//    End end4 = new End(50, 0.5f, 40, "wood", Direction.WEST, null);
//    
//    Start start1 = new Start(20, 0.5f, 50, "wood", Direction.NORTH, null);
//    Start start2 = new Start(30, 0.5f, 50, "wood", Direction.EAST, null);
//    Start start3 = new Start(40, 0.5f, 50, "wood", Direction.SOUTH, null);
//    Start start4 = new Start(50, 0.5f, 50, "wood", Direction.WEST, null);
//    
//    Pit pit1 = new Pit(20, 0.5f, 60, "wood", Direction.NORTH, null);
//    Pit pit2 = new Pit(30, 0.5f, 60, "wood", Direction.EAST, null);
//    Pit pit3 = new Pit(40, 0.5f, 60, "wood", Direction.SOUTH, null);
//    Pit pit4 = new Pit(50, 0.5f, 60, "wood", Direction.WEST, null);
//    
//    Tunnel tunnel1 = new Tunnel(20, 0.5f, 70, "wood", Direction.NORTH, null);
//    Tunnel tunnel2 = new Tunnel(30, 0.5f, 70, "wood", Direction.EAST, null);
//    Tunnel tunnel3 = new Tunnel(40, 0.5f, 70, "wood", Direction.SOUTH, null);
//    Tunnel tunnel4 = new Tunnel(50, 0.5f, 70, "wood", Direction.WEST, null);
//    
//    Staircase stair1 = new Staircase(20, 0.5f, 80, "wood", Direction.NORTH, null);
//    Staircase stair2 = new Staircase(30, 0.5f, 80, "wood", Direction.EAST, null);
//    Staircase stair3 = new Staircase(40, 0.5f, 80, "wood", Direction.SOUTH, null);
//    Staircase stair4 = new Staircase(50, 0.5f, 80, "wood", Direction.WEST, null);
//    
//    Platform plat1 = new Platform(20, 0.5f, 90, "wood", null, null);
//    
//    Field field1 = new Field(20, 0.5f, 120, "wood", Direction.NORTH, null);
//    Field field2 = new Field(50, 0.5f, 120, "wood", Direction.EAST, null);
//    Field field3 = new Field(80, 0.5f, 120, "wood", Direction.SOUTH, null);
//    Field field4 = new Field(110, 0.5f, 120, "wood", Direction.WEST, null);
    
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
        //TODO Fix rotations along X and Z axis.
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
      FloatBuffer lightPosition0 = BufferConverter.asFloatBuffer(new float[] { 15.0f, 0.0f, 5.0f, 1.0f });
      glLight(GL_LIGHT0, GL_DIFFUSE, lightColor0);
      glLight(GL_LIGHT0, GL_SPECULAR, lightColor0);
      glLight(GL_LIGHT0, GL_POSITION, lightPosition0);
      
      // Add directional light...
      FloatBuffer lightColor1 = BufferConverter.asFloatBuffer(new float[] { 0.5f, 0.5f, 0.5f, 1.0f });
      FloatBuffer lightPosition1 = BufferConverter.asFloatBuffer(new float[] { 0.0f, 15.0f, 0.0f, 0.0f });
      glLight(GL_LIGHT1, GL_DIFFUSE, lightColor1);
      glLight(GL_LIGHT1, GL_POSITION, lightPosition1);
      
      if (Keyboard.isKeyDown(Keyboard.KEY_F))
      {
        Picker.startPicking();
        {
        
        }
        Picker.stopPicking();
      }

      // Draw the checker-board...
      //board.draw();
      
      plane.draw();
      
      // Draw the world map...
      for (MapObject mapObject : worldMap)
      {
        mapObject.draw();
      }
      
      for (Ball ball : balls)
      {
        ball.draw();
      }
      testCube.draw();
      
      // Draw text to the screen...
      FontTools.draw2D();
      FontTools.renderText("Position: (" + camera.getX() + ", " + camera.getY() + ", " + camera.getZ() + ")", 10, 10, Color.orange, 0);
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
    
    if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) camera.moveForward(movementSpeed * dt * 2);
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
    FontTools.cleanUpFonts();
    Display.destroy();
  }

  public static void main(String[] args)
  {
    GraphicsTestTroy.createOpenGL();
    GraphicsTestTroy.gameLoop();
    GraphicsTestTroy.destroyOpenGL();
  }
}
