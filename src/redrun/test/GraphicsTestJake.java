package redrun.test;

import static org.lwjgl.opengl.GL11.*;

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
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import redrun.graphics.selection.Picker;
import redrun.model.constants.Direction;
import redrun.model.constants.Team;
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
import redrun.model.gameobject.world.Button;
import redrun.model.gameobject.world.CheckerBoard;
import redrun.model.gameobject.world.Cube;
import redrun.model.gameobject.world.SkyBox;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.Tools;

/**
 * Test class for Jake's work.
 * 
 * @author JakeNichol
 * @since 11-2014
 * @version 1.0
 */
public class GraphicsTestJake
{
  /**
   * Performs OpenGL initialization.
   */
  private void createOpenGL()
  {
    try
    {
      Display.setDisplayMode(new DisplayMode(800, 600));
      Display.setTitle("An Awesome OpenGL Scene");
      Display.create();
      Display.setVSyncEnabled(true);
    }
    catch (LWJGLException ex)
    {
      Logger.getLogger(GraphicsTestJake.class.getName()).log(Level.SEVERE, null, ex);
    }

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
  private void gameLoop()
  {
    // Create the camera...
    // Camera camera = new Camera(70, (float) Display.getWidth() / (float)
    // Display.getHeight(), 0.3f, 1000, 0.0f, 0.0f,
    // 0.0f);

    // Create Player
    Player player1 = new Player(37, 10, 42f, "Player 1", null, Team.BLUE);

    // Create the skybox...
    SkyBox skybox = new SkyBox(0, 0, 0, "blood_sport", player1.getCamera());

    // Create the checker-board floor...
    CheckerBoard board = new CheckerBoard(0, 0, 0, null, new Dimension(50, 50));

    Cube pedestal = new Cube(4, 0, 4, "wood");
    Button button = new Button(4, 0.8f, 4, "pokadots", new Vector3f(1.0f, 0.0f, 0.0f));

    // Create the map...
    LinkedList<MapObject> worldMap = new LinkedList<MapObject>();

    // Add the starting point...
    worldMap.add(new Start(20, 0.5f, 20, "brickwall5", Direction.NORTH, null));

    // Add a walkway...
    worldMap.add(new Corridor(20, 0.5f, 27.5f, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(20, 0.5f, 35, "brickwall5", Direction.NORTH, null));
    worldMap.add(new Corridor(20, 0.5f, 42.5f, "brickwall5", Direction.NORTH, null));

    // Add a corner...
    worldMap.add(new Corner(20, 0.5f, 50, "brickwall5", Direction.NORTH, null));

    // Add a staircase...
    worldMap.add(new Staircase(27.5f, 0.5f, 50, "brickwall5", Direction.EAST, null));

    // Add a walkway...
    worldMap.add(new Corridor(35, 8.0f, 50, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(42.5f, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Add a field...
    worldMap.add(new Field(57.5f, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Add a walkway...
    worldMap.add(new Corridor(72.5f, 8.0f, 50, "brickwall5", Direction.EAST, null));
    worldMap.add(new Corridor(80, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Add a tunnel...
    worldMap.add(new Tunnel(87.5f, 8.0f, 50, "brickwall5", Direction.EAST, null));
    worldMap.add(new Tunnel(95, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Add a walkway...
    worldMap.add(new Corridor(102.5f, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Add a pit...
    worldMap.add(new Pit(110.0f, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Add a walkway...
    worldMap.add(new Corridor(117.5f, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Add a platform...
    worldMap.add(new Platform(125.0f, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Add the ending point...
    worldMap.add(new End(132.5f, 8.0f, 50, "brickwall5", Direction.EAST, null));

    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;

    // Set the mouse sensitivity...
    float mouseSensitivity = 0.08f;
    float movementSpeed = 0.01f;

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      dt = getDelta();

      dx = Mouse.getDX();
      dy = Mouse.getDY();

      // player1.yaw((float) dx * mouseSensitivity);
      // player1.pitch((float) -dy * mouseSensitivity);
      player1.getCamera().yaw(dx * mouseSensitivity);
      player1.getCamera().pitch(-dy * mouseSensitivity);

      if (Keyboard.isKeyDown(Keyboard.KEY_W)) player1.walkForward(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_S)) player1.walkBackward(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_A)) player1.walkLeft(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_D)) player1.walkRight(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) player1.jump();
      if (Keyboard.isKeyDown(Keyboard.KEY_E)) System.out.println(player1.toString());
      // if (Keyboard.isKeyDown(Keyboard.KEY_W))
      // camera.moveForward(movementSpeed * dt);
      // if (Keyboard.isKeyDown(Keyboard.KEY_S))
      // camera.moveBackward(movementSpeed * dt);
      // if (Keyboard.isKeyDown(Keyboard.KEY_A)) camera.moveLeft(movementSpeed *
      // dt);
      // if (Keyboard.isKeyDown(Keyboard.KEY_D)) camera.moveRight(movementSpeed
      // * dt);
      // if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) camera.moveUp(movementSpeed
      // * dt);
      // if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
      // camera.moveDown(movementSpeed * dt);

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glLoadIdentity();

      glPushMatrix();
      {
        glDepthMask(false);
        glRotatef(player1.getBody().getYaw(), 0, 180, 0);
        skybox.draw();
        glDepthMask(true);
      }
      glPopMatrix();

      player1.lookThrough();

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
          // Draw the checker-board...
          glPushName(board.id);
          {
            board.draw();
          }
          glPopName();

          // Draw the button.
          glPushMatrix();
          {
            glPushName(button.id);
            {
              button.draw();
            }
            glPopName();
          }
          glPopMatrix();
          glPushMatrix();
          {
            glPushName(pedestal.id);
            {
              pedestal.draw();
            }
            glPopName();
          }
          glPopMatrix();

        }
        Picker.stopPicking();
      }

      // Draw the checker-board.
      board.draw();

      // Draw the button.
      glPushMatrix();
      {
        button.draw();
      }
      glPopMatrix();
      glPushMatrix();
      {
        pedestal.draw();
      }
      glPopMatrix();

      // Draw the world map...
      for (MapObject mapObject : worldMap)
      {
        mapObject.draw();
      }

      FontTools.draw2D();
      FontTools.renderText("Position: (" + player1.getBody().getX() + ", " + player1.getBody().getY() + ", "
          + player1.getBody().getZ() + ")", 10, 10, Color.orange, 0);
      FontTools.draw3D();

      player1.update();
      updateFPS();
      PhysicsWorld.stepSimulation(1 / 60.0f); // (float) lastFPS

      Timer.tick();
      Display.update();
      Display.sync(60);
    }
  }

  /**
   * Calculate how many milliseconds have passed since last frame.
   * 
   * @return milliseconds passed since last frame
   */
  public int getDelta()
  {
    long time = Tools.getTime();
    int delta = (int) (time - Tools.lastFrame);
    Tools.lastFrame = time;

    return delta;
  }

  /**
   * Calculate the FPS and set it in the title bar
   */
  public void updateFPS()
  {
    if (Tools.getTime() - Tools.lastFPS > 1000)
    {
      Tools.fps = 0;
      Tools.lastFPS += 1000;
    }
    Tools.fps++;
  }

  // Taken from Jordan's test.
  public static javax.vecmath.Vector3f vec(Vector3f vec)
  {
    return new javax.vecmath.Vector3f(vec.x, vec.y, vec.z);
  }

  /**
   * Cleans up resources.
   */
  private void destroyOpenGL()
  {
    Display.destroy();
  }

  public static void main(String[] args)
  {
    GraphicsTestJake test = new GraphicsTestJake();
    test.createOpenGL();
    test.gameLoop();
    test.destroyOpenGL();
  }
}
