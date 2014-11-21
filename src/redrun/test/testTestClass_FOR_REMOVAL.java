package redrun.test;

import java.awt.Dimension;
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

import redrun.graphics.camera.Camera;
import redrun.graphics.selection.Picker;
import redrun.model.gameobject.world.Button;
import redrun.model.gameobject.world.CheckerBoard;
import redrun.model.gameobject.world.Cube;
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
public class testTestClass_FOR_REMOVAL
{
  /** time at last frame */
  long lastFrame;

  /** frames per second */
  int fps;
  
  /** last fps time */
  long lastFPS;

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
      Logger.getLogger(testTestClass_FOR_REMOVAL.class.getName()).log(Level.SEVERE, null, ex);
    }

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
   * The main loop where the logic occurs. Stopped when the escape key is
   * pressed or the window is closed.
   */
  private void gameLoop()
  {
    // Create the camera...
    Camera camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f, 0.0f,
        0.0f);

    // Create the skybox...
    SkyBox skybox = new SkyBox(0, 0, 0, "blood_sport", camera);

    // Create the checker-board floor...
    CheckerBoard board = new CheckerBoard(0, 0, 0, null, new Dimension(50, 50));

    Cube pedestal = new Cube(4, 0, 4, "wood");
    Button button = new Button(4, 0.8f, 4, "pokadots");

    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;

    // Set the mouse sensitivity...
    float mouseSensitivity = 0.08f;
    float movementSpeed = 0.02f;

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      dt = getDelta();

      dx = Mouse.getDX();
      dy = Mouse.getDY();

      camera.yaw(dx * mouseSensitivity);
      camera.pitch(-dy * mouseSensitivity);

      if (Keyboard.isKeyDown(Keyboard.KEY_W)) camera.moveForward(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_S)) camera.moveBackward(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_A)) camera.moveLeft(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_D)) camera.moveRight(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) camera.moveUp(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) camera.moveDown(movementSpeed * dt);

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glLoadIdentity();

      glPushMatrix();
      {
        glDepthMask(false);
        // TODO Fix rotations along X and Z axis.
        glRotatef(camera.getYaw(), 0, 180, 0);
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

      FontTools.draw2D();
      FontTools.renderText("Position: (" + camera.getX() + ", " + camera.getY() + ", " + camera.getZ() + ")", 10, 10,
          Color.orange, 0);
      FontTools.draw3D();

      updateFPS();
      PhysicsWorld.stepSimulation(1 / (float) lastFPS);
      
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
    int delta = (int) (time - lastFrame);
    lastFrame = time;

    return delta;
  }

  /**
   * Calculate the FPS and set it in the title bar
   */
  public void updateFPS()
  {
    if (Tools.getTime() - lastFPS > 1000)
    {
      Display.setTitle("FPS: " + fps);
      fps = 0;
      lastFPS += 1000;
    }
    fps++;
  }

  /**
   * Cleans up resources.
   */
  private void destroyOpenGL()
  {
    FontTools.cleanUpFonts();
    Display.destroy();
  }

  public static void main(String[] args)
  {
    testTestClass_FOR_REMOVAL test = new testTestClass_FOR_REMOVAL();
    test.createOpenGL();
    test.gameLoop();
    test.destroyOpenGL();
  }
}
