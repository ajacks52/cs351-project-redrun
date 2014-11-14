package redrun.test;

import java.awt.Dimension;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;

import redrun.graphics.camera.Camera;
import redrun.graphics.selection.Picker;
import redrun.model.gameobject.trap.*;
import redrun.model.gameobject.world.CheckerBoard;
import redrun.model.toolkit.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Class to show how to draw text, also does a few other camera adjustments to
 * mimic a person walking and jumping these are mainly just for testing purposes
 * also shows how traps work.
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-10
 * 
 */
public class GraphicsTestAdam
{
  Camera cam;
  Random rand;

  /**
   * Renders a scene so show how the traps work.
   * 
   * Loads the fonts
   * 
   */
  public void mainLoop()
  {
    // load font takes a about 2 seconds
    redrun.model.toolkit.FontTools.loadFonts(15);

    cam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, -10f, -3f, -10f);
    CheckerBoard board = new CheckerBoard(0, 0, 0, null, new Dimension(50, 50));

    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;
    float previousTime = 0.0f;
    float currentTime = 0.0f;

    // Set the mouse sensitivity...
    float mouseSensitivity = 0.05f;
    float movementSpeed = 10.0f;

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);

    glEnable(GL_DEPTH_TEST);
    Spike ss = new Spike(1, 1, 1, null);
    TrapDoor td = new TrapDoor(1, 1, 1, "wood");

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      currentTime = Sys.getTime();
      dt = (currentTime - previousTime) / 1000.0f;
      previousTime = currentTime;

      dx = Mouse.getDX();
      dy = Mouse.getDY();

      cam.yaw(dx * mouseSensitivity);
      cam.pitch(-dy * mouseSensitivity);

      if (Keyboard.isKeyDown(Keyboard.KEY_W)) cam.moveForward(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_S)) cam.moveBackward(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_A)) cam.moveLeft(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_D)) cam.moveRight(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) || Keyboard.isKeyDown(Keyboard.KEY_UP))
        cam.moveUp(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_X) || Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        cam.moveDown(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_F)) ss.interact(); 
      if (Keyboard.isKeyDown(Keyboard.KEY_R)) td.interact(); 

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      glLoadIdentity();
      // if (Picker.mode == 2) Picker.startPicking();
      cam.lookThrough();

      ss.drawSpikes(10, 0, 0);
      ss.update();
      // ss.drawSpikes(30, 0);
      // ss.update();
      td.drawTrapDoor(30, 0, 0);
      td.update();

      // if (Picker.mode == 2) Picker.stopPicking();

      board.draw();
      
      FontTools.draw2D();
      FontTools.renderText("x: " + cam.getX()  +" y: " + cam.getY() + " z: " + cam.getZ(), 10, 10, Color.white, 0);
      FontTools.draw3D();
      Timer.tick();
      Display.update();
    }
  }

  /**
   * Sets up the screen 800x600
   */
  public void initDisplay()
  {
    try
    { 
      Display.setDisplayMode(new DisplayMode(800, 600));
      Display.create();
    }
    catch (LWJGLException ex)
    {
      ex.printStackTrace();
    }

  }

  /**
   * Cleans everything up, destroys the fonts and the display
   */
  public void exit()
  {
    FontTools.cleanUpFonts();
    Display.destroy();
  }

  /**
   * Gets things started
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    GraphicsTestAdam driver = new GraphicsTestAdam();
    driver.initDisplay();
    driver.mainLoop();
    driver.exit();
  }
}